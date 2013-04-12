package com.mypal.dao;

import com.mypal.entity.TransactionLog;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class LogDAOImpl implements LogDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public TransactionLog save(TransactionLog log) {
        return (TransactionLog) sessionFactory.getCurrentSession().save(log);
    }

    @Override
    public TransactionLog getById(Integer id) {
        return (TransactionLog) sessionFactory.getCurrentSession().get(TransactionLog.class, id);
    }

    @Override
    public TransactionLog getByTransaction(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User WHERE email = ?");
        query.setInteger(0, id);
        return (TransactionLog) query.uniqueResult();
    }

    @Override
    public List<TransactionLog> list() {
        return sessionFactory.getCurrentSession().createQuery("from TransactionLog").list();
    }
}
