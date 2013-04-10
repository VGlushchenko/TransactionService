package com.mypal.controller;

import com.mypal.dao.CreditCardDAO;
import com.mypal.dao.TransactionDAO;
import com.mypal.dao.UserDAO;
import com.mypal.entity.User;
import com.mypal.entity.UserSecurity;
import com.mypal.service.CreditCardService;
import com.mypal.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    CreditCardDAO creditCardDAO;

    @Autowired
    CreditCardService creditCardService;

    @Autowired
    TransactionService transactionService;

    public UserSecurity getCurrentUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserSecurity)principal;
    }

}
