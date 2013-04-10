package com.mypal.dao;

import com.mypal.entity.TransactionLog;

import java.util.List;

public interface LogDAO {

    public void save(TransactionLog log);
    public TransactionLog getById(Integer id);
    public TransactionLog getByTransaction(int id);
    public List<TransactionLog> list();
}

