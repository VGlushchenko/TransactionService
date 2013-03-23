package com.mypal.dao;

import com.mypal.entity.Transaction;
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
public class TransactionDAOImpl implements TransactionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Transaction> findAllForUser(User user) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Transaction WHERE debit = ? or credit = ? ");
        query.setInteger(0, user.getId());
        query.setInteger(1, user.getId());
        return query.list();
    }

    @Override
    public void save(Transaction transaction) throws IOException {
        sessionFactory.getCurrentSession().saveOrUpdate(transaction);
    }

    @Override
    public List<Transaction> list() throws IOException, SQLException {
        return sessionFactory.getCurrentSession().createQuery("from Transaction ").list();
    }

    @Override
    public Transaction getById(Integer id) throws SQLException {
        return (Transaction) sessionFactory.getCurrentSession().get(Transaction.class, id);
    }
}
