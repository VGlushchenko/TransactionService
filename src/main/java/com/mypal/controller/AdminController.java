package com.mypal.controller;

import com.mypal.entity.UserSecurity;
import com.mypal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String adminPanel(Model model) {
        UserSecurity user = getCurrentUserDetails();
        model.addAttribute("user", user);

        return "admin/admin";
    }

    /*@ResponseBody
    @RequestMapping(value = "user/transaction", method = RequestMethod.GET)
    public String usersTransactions(Model model) throws JSONException {
        UserSecurity user = getCurrentUserDetails();

        //List<Transaction> list = transactionDAO.findAllForUser(user.getId());
        JSONArray listTransactionsJSON = new JSONArray();

        *//*for (Transaction transaction: list) {
            listTransactionsJSON.put(toJSONObject(transaction))
        }*//*

        return transactionDAO.findAllForUser(user.getId()).toString();
    }*/

    @RequestMapping("/users")
    public String showUsers(Model model) {
        UserSecurity user = getCurrentUserDetails();

        model.addAttribute("user", user);
        model.addAttribute("userlist", userDAO.list());
        return "admin/list";
    }

    @RequestMapping(value = "/user/ban/{id}", method = RequestMethod.GET)
    public String BanUser (@PathVariable int id, Model model) throws IOException {
        UserSecurity user = getCurrentUserDetails();
        model.addAttribute("user", user);
        adminService.banUser(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "/user/unban/{id}", method = RequestMethod.GET)
    public void UnBanUser (@PathVariable int id, HttpServletResponse response, Model model) throws IOException {
        UserSecurity user = getCurrentUserDetails();

        model.addAttribute("user", user);

        adminService.unBanUser(id);
        response.sendRedirect("/users");
    }

    @RequestMapping(value = "users/{id}/transactions", method = RequestMethod.GET)
    public String showUserTransactions(ModelMap model, @PathVariable int id) {
        UserSecurity user = getCurrentUserDetails();

        model.addAttribute("user", user);
        model.addAttribute("transactions", adminService.userTransactions(id));
        return "admin/transactionlist";
    }

    @RequestMapping(value = "/admin/transaction/list")
    public String showAllTransactions(ModelMap model) throws IOException, SQLException {
        UserSecurity user = getCurrentUserDetails();

        model.addAttribute("user", user);
        model.addAttribute("transactions", adminService.listTransactions());
        return "/admin/transactionlist";
    }

    @RequestMapping(value = "/transaction/{id}/cancel", method = RequestMethod.GET)
    public void cancelTransaction(@PathVariable int id, HttpServletResponse response) throws SQLException, IOException {
        adminService.cancelTransaction(id);
        response.sendRedirect("/admin/transaction/list");
    }

    @RequestMapping(value = "/transaction/{id}/restore", method = RequestMethod.GET)
    public void restoreTransaction(@PathVariable int id, HttpServletResponse response) throws SQLException, IOException {
        adminService.restoreTransaction(id);
        response.sendRedirect("/admin/transaction/list");
    }
}