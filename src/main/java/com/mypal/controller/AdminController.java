package com.mypal.controller;

import com.mypal.dao.UserDAO;
import com.mypal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class AdminController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String adminPanel() {
        return "admin/admin";
    }

    @RequestMapping("/admin/page")
    public String showAdminPage() {
        return "/admin/admin";
    }

    @RequestMapping("/users")
    public String showUsers(ModelMap model) {
        model.addAttribute("userlist", userDAO.list());
        return "/user/list";
    }

    @RequestMapping(value = "/user/ban/{id}", method = RequestMethod.GET)
    public void BanUser ( @PathVariable int id, HttpServletResponse response) throws IOException {
        adminService.banUser(id);
        response.sendRedirect("/users");
    }

    @RequestMapping(value = "/user/unban/{id}", method = RequestMethod.GET)
    public void UnBanUser (@PathVariable int id, HttpServletResponse response) throws IOException {
        adminService.unBanUser(id);
        response.sendRedirect("/users");
    }

    @RequestMapping(value = "users/{id}/transactions", method = RequestMethod.GET)
    public String showUserTransactions(ModelMap model, @PathVariable int id) {
        model.addAttribute("transactions", adminService.userTransactions(id));
        return "admin/transactionlist";
    }

    @RequestMapping(value = "/admin/transaction/list")
    public String showAllTransactions(ModelMap model) throws IOException, SQLException {
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