package com.mypal.service.quartz;

import com.mypal.service.TransactionService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzTask {

    @Autowired
    TransactionService transactionService;

    public void rollback() {
        System.out.println("1");
        transactionService.rollbackUncompletedTransactions();
        System.out.println("Complete job");
    }

}
