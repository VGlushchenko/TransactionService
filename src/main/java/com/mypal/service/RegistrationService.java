package com.mypal.service;

import com.mypal.dao.UserDAO;
import com.mypal.entity.CreditCard;
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

    public boolean hasErrors(String email) {
        return null != userDAO.getByEmail(email);
    }

    public void register(RegistrationForm rf) throws NoSuchAlgorithmException {
        User user = new User();
        user.setFirstName(rf.getFirstName());
        user.setEmail(rf.getEmail());
        user.setPassword(decodeService.decodePassword(rf.getPassword()));
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

        userDAO.save(user);
    }



}
