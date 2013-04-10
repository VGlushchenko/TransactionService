package com.mypal.service;

import com.mypal.dao.UserDAO;
import com.mypal.entity.User;
import com.mypal.form.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class RegistrationService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    DecodeService decodeService;

    private User user;

    public RegistrationService () {
    }

    public RegistrationService(RegistrationForm registrationForm) throws NoSuchAlgorithmException {
        user = new User();
        user.setFirstName(registrationForm.getFirstName());
        user.setEmail(registrationForm.getEmail());
        user.setPassword(decodeService.decodePassword(registrationForm.getPassword()));
        user.setEnabled(true);
        user.setAuthorities("ROLE_USER");
        user.setBalance(1000.0);


    }

    public boolean hasErrors() {
        return null != userDAO.getByEmail(user.getEmail());
    }

    public void register() {
        userDAO.save(user);
    }
}
