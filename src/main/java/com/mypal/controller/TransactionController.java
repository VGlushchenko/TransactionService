package com.mypal.controller;

import com.mypal.dao.CreditCardDAO;
import com.mypal.dao.TransactionDAO;
import com.mypal.dao.UserDAO;
import com.mypal.entity.CreditCard;
import com.mypal.entity.User;
import com.mypal.service.CreditCardService;
import com.mypal.service.TransactionService;
import com.mypal.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping(value = "/transaction")
public class TransactionController {

    @Autowired UserDAO userDAO;

    @Autowired TransactionDAO transactionDAO;

    @Autowired CreditCardDAO creditCardDAO;

    @Autowired CreditCardService creditCardService;

    @Autowired TransactionService transactionService;


    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', ROLE_USER)")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String creationForm(ModelMap model) {

        User user = getCurrentUserDetails();
        model.addAttribute("user", user);

        return "transaction/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam("debit") String debit,
                         @RequestParam("sum") String sum, ModelMap model) throws IOException {

        User user = getCurrentUserDetails();
        model.addAttribute("user", user);

        boolean isTransactionComplete = transactionService.create(user, debit, sum);
        model.addAttribute("transactionComplete", isTransactionComplete);

        if (isTransactionComplete) {
            return "redirect:/transaction/history";
        }

        return "transaction/create";
    }

    @RequestMapping(value = "/history")
    public String history(ModelMap model) throws IOException, SQLException {

        User user = getCurrentUserDetails();

        model.addAttribute("user", user);
        model.addAttribute("transactions", transactionDAO.findAllForUser(user));

        return "transaction/history";
    }

    @RequestMapping(value = "/create/creditfromcard", method = RequestMethod.GET)
    public String creditView(ModelMap model) throws IOException, SQLException {
        User user = getCurrentUserDetails();

        model.addAttribute("user", user);
        model.addAttribute("cards", creditCardDAO.userCards(user));
        return "creditcard/create";
    }

    @RequestMapping(value = "/create/creditfromcard", method = RequestMethod.POST)
    public String getCredit(@RequestParam("card") String cardId,
                            @RequestParam("sum") String sum,
                            ModelMap model) throws IOException, SQLException {

        CreditCard creditCard = creditCardDAO.getById(Integer.parseInt(cardId));
        User user = getCurrentUserDetails();

        boolean credit = creditCardService.getCredit(user, creditCard, Double.parseDouble(sum));

        model.addAttribute("cards", creditCardDAO.userCards(user));
        model.addAttribute("creditCard", creditCard);
        model.addAttribute("credit", credit);
        model.addAttribute("user", user);
        return "creditcard/create";
    }

    public User getCurrentUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.getByEmail(auth.getName());
    }
}
