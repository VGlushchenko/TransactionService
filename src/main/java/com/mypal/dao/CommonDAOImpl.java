package com.mypal.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CommonDAOImpl implements CommonDAO {

    @Override
    public List executeQuery(String queryString) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void clearTable(String tableName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
