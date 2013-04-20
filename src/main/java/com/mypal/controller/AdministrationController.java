package com.mypal.controller;

import com.mypal.entity.EntityResult;
import com.mypal.entity.Transaction;
import com.mypal.entity.UserSecurity;
import com.mypal.service.AdministrationService;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdministrationController extends BaseController {

    @Autowired
    private AdministrationService adminService;

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String adminPanel(Model model) {
        UserSecurity user = getCurrentUserDetails();
        model.addAttribute("user", user);

        return "admin/admin";
    }

    @RequestMapping("/users")
    public String showUsers(Model model) {
        UserSecurity user = getCurrentUserDetails();
        model.addAttribute("user", user);
        model.addAttribute("userlist", userDAO.list());

        return "admin/user_list";
    }

    @RequestMapping(value = "/user/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable int id, Model model) throws IOException {

        UserSecurity user = getCurrentUserDetails();
        model.addAttribute("user", user);

        adminService.disableUser(id);

        return "redirect:/users";
    }

    @RequestMapping(value = "/user/enable/{id}", method = RequestMethod.GET)
    public String enableUser(@PathVariable int id, Model model) throws IOException {

        UserSecurity user = getCurrentUserDetails();
        model.addAttribute("user", user);

        adminService.enableUser(id);

        return "redirect:/users";
    }

    @RequestMapping(value = "/users/{id}/transactions", method = RequestMethod.GET)
    public String showUserTransactions(ModelMap model, @PathVariable int id) {

        UserSecurity user = getCurrentUserDetails();
        model.addAttribute("user", user);
        model.addAttribute("transactions", adminService.userTransactions(id));

        return "admin/transaction_list";
    }

    @ResponseBody
    @RequestMapping(value = "/admin/users/5/history/json", method = RequestMethod.GET)
    public String historyJson(@RequestParam("page") int page) throws IOException, SQLException, JSONException {
        UserSecurity user = getCurrentUserDetails();

        List<Transaction> transactions = transactionService.getLimitResults(5, page);
        int transactionsCount = transactionService.getUsersTransactionsCount(5);

        JSONArray usersTransactions = new JSONArray();

        for(Transaction item : transactions) {
            usersTransactions.put(item.toJSON());
        }

        EntityResult result = new EntityResult();

        result.setEntity(usersTransactions);
        result.setResponseMessage(transactionsCount/10 + 1);

        return result.toJSON().toString();
    }

    @RequestMapping(value = "/admin/transaction/list")
    public String showAllTransactions(ModelMap model) throws IOException, SQLException {
        UserSecurity user = getCurrentUserDetails();
        model.addAttribute("user", user);
        model.addAttribute("transactions", adminService.listTransactions());

        return "admin/transaction_list";
    }

    @RequestMapping(value = "/transaction/{id}/cancel", method = RequestMethod.GET)
    public String cancelTransaction(@PathVariable int id) throws SQLException, IOException {
        adminService.rollback(id);
        return "redirect:/admin/transaction/list";
    }
}