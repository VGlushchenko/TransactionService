package com.mypal.controller;


import com.mypal.dao.UserDAO;
import com.mypal.entity.CreditCard;
import com.mypal.entity.User;
import com.mypal.form.RegistrationForm;
import com.mypal.service.DecodeService;
import com.mypal.service.RegistrationService;
import com.mypal.service.quartz.QuartzTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller
public class SecurityController {

    @Autowired UserDAO userDAO;

    @Autowired
    DecodeService decodeService;

    @Autowired
    QuartzTrigger quartzTrigger;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        RegistrationForm registrationForm = new RegistrationForm();

        model.addAttribute("registrationForm", registrationForm);
        return "security/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String processRegistrationForm(@Valid RegistrationForm registrationForm, BindingResult result, Model model) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        RegistrationService registrationService = new RegistrationService(registrationForm);

        User user = new User();
            user.setFirstName(registrationForm.getFirstName());
            user.setEmail(registrationForm.getEmail());
            user.setPassword(decodeService.decodePassword(registrationForm.getPassword()));
            user.setEnabled(true);
            user.setAuthorities("ROLE_USER");
            user.setBalance(1000.0);

        CreditCard privateBank = new CreditCard();
            privateBank.setName("Private Bank");
            privateBank.setCardBalance(1000.0);
            privateBank.setOwner(user);

        CreditCard raiffeisen = new CreditCard();
            raiffeisen.setName("Raiffeisen Bank Aval");
            raiffeisen.setCardBalance(1000.0);
            raiffeisen.setOwner(user);

        /*if (result.hasErrors() || registrationService.hasErrors()) {
            if (result.hasErrors()) {
                model.addAttribute("registrationForm", registrationForm);
            }
            if (registrationService.hasErrors()) {
                model.addAttribute("email_error", "*User with this email exists");
            }
            return "security/registration";
        }*/
        userDAO.save(user);
        //registrationService.register();

        return "redirect:/login";
    }

     @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() throws Exception {
        quartzTrigger.startJob(1);
        return "security/login";
    }

}
