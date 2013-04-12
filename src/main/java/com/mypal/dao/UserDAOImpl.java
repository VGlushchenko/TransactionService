package com.mypal.dao;

import com.mypal.entity.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void save(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public User getById(Integer id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User getByEmail(String username) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User WHERE email = ?");
        query.setString(0, username);
        return (User) query.uniqueResult();
    }

    @Override
    public double getBalance(Integer id) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT BALANCE FROM USERS WHERE ID = ?");
        query.setInteger(0, id);
        return Double.parseDouble(query.uniqueResult().toString());
    }

    @Override
    public List<User> list() {
        return sessionFactory.getCurrentSession().createQuery("from User").list();
    }

    @Override
    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }
}
