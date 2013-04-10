package com.mypal.dao;

import com.mypal.entity.CreditCard;
import com.mypal.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CreditCardDAO {

    public List<CreditCard> userCards(int id);
    public void save(CreditCard creditCard) throws IOException;
    public CreditCard getById(Integer id) throws SQLException;
    public double getCardBalance(Integer id);
}
