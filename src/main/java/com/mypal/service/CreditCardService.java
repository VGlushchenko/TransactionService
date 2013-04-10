package com.mypal.service;

import com.mypal.dao.CreditCardDAO;
import com.mypal.dao.UserDAO;
import com.mypal.entity.CreditCard;
import com.mypal.entity.User;
import com.mypal.entity.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CreditCardService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CreditCardDAO creditCardDAO;

    public boolean getCredit(UserSecurity user, CreditCard card, double sum) throws IOException {

        boolean credit = validateCreditBalance(card.getCardBalance(), sum);

        User debitUser = userDAO.getById(user.getId());
        if (credit)
            debitUser.setBalance(user.getBalance() + sum);
            card.setCardBalance(card.getCardBalance() - sum);


            userDAO.save(debitUser);
            creditCardDAO.save(card);

        return credit;
    }

    public boolean validateCreditBalance(double creditBalance, double sum) {

        boolean result = false;
        if (creditBalance > sum)
            result = true;

        return result;
    }
}
