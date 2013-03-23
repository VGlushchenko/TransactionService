package com.mypal.dao;

import java.util.List;

public interface CommonDAO {
    public List executeQuery(String queryString);
    public void clearTable(String tableName);
}
