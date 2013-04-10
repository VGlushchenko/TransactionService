package com.mypal.service;

import com.mypal.dao.TransactionDAO;
import com.mypal.dao.UserDAO;
import com.mypal.entity.Transaction;
import com.mypal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    TransactionDAO transactionDAO;

    public  void banUser(int id) {
        User user = userDAO.getById(id);
        if (user != null) {
            user.setEnabled(false);
            userDAO.save(user);
        }
    }

    public void unBanUser(int id) {
        User user = userDAO.getById(id);
        if (user != null) {
            user.setEnabled(true);
            userDAO.save(user);
        }
    }

    public List userTransactions(int id) {
        return transactionDAO.findAllForUser(id);
    }

    public List listTransactions() throws IOException, SQLException {
        return transactionDAO.list();
    }

    public void cancelTransaction(int id) throws SQLException, IOException {
        Transaction transaction = transactionDAO.getById(id);
        if (transaction != null) {
            transaction.setStatus(false);
            transactionDAO.save(transaction);
        }
    }

    public void restoreTransaction(int id) throws SQLException, IOException {
        Transaction transaction = transactionDAO.getById(id);
        if (transaction != null) {
            transaction.setStatus(true);
            transactionDAO.save(transaction);
        }
    }
}
