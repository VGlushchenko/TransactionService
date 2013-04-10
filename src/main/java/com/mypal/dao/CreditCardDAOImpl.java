package com.mypal.dao;

import com.mypal.entity.CreditCard;
import com.mypal.entity.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class CreditCardDAOImpl implements CreditCardDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<CreditCard> userCards(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from CreditCard WHERE owner = ?");
        query.setInteger(0, id);
        return query.list();
    }

    @Override
    public void save(CreditCard creditCard) throws IOException {
        sessionFactory.getCurrentSession().saveOrUpdate(creditCard);
    }

    @Override
    public CreditCard getById(Integer id) throws SQLException {
        return (CreditCard) sessionFactory.getCurrentSession().get(CreditCard.class, id);
    }

    @Override
    public double getCardBalance(Integer id) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT card_balance FROM credit_cards WHERE ID = ?");
        query.setInteger(0, id);
        return Double.parseDouble(query.uniqueResult().toString());
    }
}
