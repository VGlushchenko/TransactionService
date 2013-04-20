package com.mypal.controller;


import com.mypal.dao.UserDAO;
import com.mypal.form.RegistrationForm;
import com.mypal.service.RegistrationService;
import com.mypal.service.quartz.QuartzTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller
public class SecurityController {

    @Autowired UserDAO userDAO;

    @Autowired QuartzTrigger quartzTrigger;

    @Autowired RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm() {
        return "security/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String processRegistrationForm(@Valid RegistrationForm registrationForm, Model model)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String error = "This email is already in use";

        if (registrationService.hasErrors(registrationForm.getEmail())) {
            model.addAttribute("mailError", error);
            model.addAttribute("form", registrationForm);

            return "security/registration";
        }

        registrationService.register(registrationForm);
        return "redirect:/login";
    }

     @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() throws Exception {
        return "security/login";
    }
}
