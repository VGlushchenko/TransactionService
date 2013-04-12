package com.mypal.dao;

import com.mypal.entity.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TransactionDAO {

    public List<Transaction> findAllForUser(int id);

    public void save(Transaction transaction) throws IOException;

    public void delete(Transaction transaction);

    public List<Transaction> getUncompletedTransactions();

    public List<Transaction> list() throws IOException, SQLException;

    public Transaction getById(Integer id) throws SQLException;

    public List<Transaction> limitTransactionsList(int id, int startItem);

    public int usersTransactionCount(int id);
}
