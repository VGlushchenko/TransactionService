package com.mypal.dao;

import com.mypal.entity.Transaction;
import com.mypal.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TransactionDAO {

    public List<Transaction> findAllForUser(User user);
    public void save(Transaction transaction) throws IOException;
    public List<Transaction> list() throws IOException, SQLException;
    public Transaction getById(Integer id) throws SQLException;
}
