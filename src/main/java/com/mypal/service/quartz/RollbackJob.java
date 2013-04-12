package com.mypal.service.quartz;

import com.mypal.service.TransactionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RollbackJob implements Job {

    @Autowired
    TransactionService transactionService;

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        transactionService.rollbackUncompletedTransactions();
    }

}
