package com.mypal.controller;

import com.mypal.entity.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value = "transaction")
public class TransactionController extends BaseController {

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String creationForm(ModelMap model) {
        UserSecurity user = getCurrentUserDetails();
        model.addAttribute("user", user);
        return "transaction/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam("debit") String debit,
                         @RequestParam("sum") String sum, ModelMap model) throws IOException {
        UserSecurity user = getCurrentUserDetails();
        model.addAttribute("user", user);

        boolean isTransactionComplete = transactionService.create(user.getId(), debit, sum);
        model.addAttribute("transactionComplete", isTransactionComplete);

        if (isTransactionComplete) {
            return "redirect:/transaction/history";
        }

        return "transaction/create";
    }

    @RequestMapping(value = "/history")
    public String history(ModelMap model) throws IOException, SQLException {
        UserSecurity user = getCurrentUserDetails();

        model.addAttribute("user", user);

        model.addAttribute("transactions", transactionDAO.findAllForUser(user.getId()));
        return "transaction/history";
    }

    @ResponseBody
    @RequestMapping(value = "/history/json", method = RequestMethod.GET)
    public String historyJson(@RequestParam("page") int page) throws IOException, SQLException, JSONException {
        UserSecurity user = getCurrentUserDetails();

        List<Transaction> transactions = transactionService.getLimitResults(user.getId(), page);
        int transactionsCount = transactionService.getUsersTransactionsCount(user.getId());

        JSONArray usersTransactions = new JSONArray();

        for(Transaction item : transactions) {
            usersTransactions.put(item.toJSON());
        }

        EntityResult result = new EntityResult();

        result.setEntity(usersTransactions);
        result.setResponseMessage(transactionsCount/10 + 1);

        return result.toJSON().toString();
    }

    @RequestMapping(value = "/create/creditfromcard", method = RequestMethod.GET)
    public String creditView(ModelMap model) throws IOException, SQLException {
        UserSecurity user = getCurrentUserDetails();

        model.addAttribute("user", user);
        model.addAttribute("cards", creditCardDAO.userCards(user.getId()));
        return "creditcard/create";
    }

    @RequestMapping(value = "/create/creditfromcard", method = RequestMethod.POST)
    public String getCredit(@RequestParam("card") String cardId,
                            @RequestParam("sum") String sum,
                            ModelMap model) throws IOException, SQLException {

        CreditCard creditCard = creditCardDAO.getById(Integer.parseInt(cardId));
        UserSecurity user = getCurrentUserDetails();

        boolean credit = creditCardService.getCredit(user, creditCard, Double.parseDouble(sum));

        model.addAttribute("cards", creditCardDAO.userCards(user.getId()));
        model.addAttribute("creditCard", creditCard);
        model.addAttribute("credit", credit);
        model.addAttribute("user", user);

        return "creditcard/create";
    }
}
