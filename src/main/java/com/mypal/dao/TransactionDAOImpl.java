package com.mypal.dao;

import com.mypal.entity.Transaction;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class TransactionDAOImpl implements TransactionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Transaction> findAllForUser(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Transaction WHERE debit = ? or credit = ? ");
        query.setInteger(0, id);
        query.setInteger(1, id);
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

    @Override
    public List<Transaction> limitTransactionsList(int id, int startItem) {

        Query query = sessionFactory.getCurrentSession().createQuery("from Transaction where debit = ? or credit = ? ");
        query.setInteger(0, id);
        query.setInteger(1, id);
        query.setFirstResult(startItem);
        query.setMaxResults(10);
        return query.list();
    }

    @Override
    public int usersTransactionCount(int id) {
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("SELECT COUNT(*) FROM TRANSACTIONS WHERE DEBIT_ID = ? OR CREDIT_ID = ?");
        query.setInteger(0, id);
        query.setInteger(1, id);

        return Integer.parseInt(String.valueOf((query.list().get(0))));
    }
}
