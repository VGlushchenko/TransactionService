package com.mypal.service;

import com.mypal.dao.LogDAO;
import com.mypal.dao.TransactionDAO;
import com.mypal.dao.UserDAO;
import com.mypal.entity.Transaction;
import com.mypal.entity.TransactionLog;
import com.mypal.entity.User;
import com.mypal.entity.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    LogDAO log;

    private static final String EMAIL_REGEXP =
            "^[A-Za-z0-9](([_\\.\\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$";

    public boolean create(int id, String debitUserEmail, String inputSum) throws Exception {

        User creditUser = userDAO.getById(id);
        User debitUser = userDAO.getByEmail(debitUserEmail);

        TransactionLog transactionLog = new TransactionLog();
        double sum = validateSum(inputSum);

        Transaction transaction = new Transaction();

        if (sum != 0 && isEmailValid(debitUserEmail)) {
            if (debitUser == null) {
                InviteService.sendEmail(creditUser.getFirstName(), debitUserEmail, sum);

                User user = getDefaultUser(debitUserEmail);
                userDAO.save(user);

                transaction.setDebit(user);

            } else {
                transaction.setDebit(debitUser);
            }

            double resultCreditBalance;

            resultCreditBalance = creditUser.getBalance() - sum;
            creditUser.setBalance(resultCreditBalance);

            transaction.setCredit(creditUser);
            transaction.setSum(sum);
            transaction.setStatus("in progress");

            try {
                userDAO.save(creditUser);

                transactionLog.setStartedAt(new Date());
                transactionLog.setTransaction(transaction);
                transaction.setLog(transactionLog);

                transactionDAO.save(transaction);
                userDAO.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            transaction.getLog().setCompletedAt(new Date());

            /*if(true)
                throw new Exception();*/

            if (transaction.getLog().getCompletedAt() != null) {
                transaction.setStatus("completed");
                try {
                    userDAO.save(debitUser);
                    transactionDAO.save(transaction);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
        return false;
    }

    private double validateSum(String inputSum) {

        UserSecurity userSecurity = (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        double sum = Double.parseDouble(inputSum);
        if (sum > userSecurity.getBalance()) {
            return 0;
        }

        return sum;
    }

    private static boolean isEmailValid(String email) {
        return email.matches(EMAIL_REGEXP);
    }

    public User getDefaultUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName("default");
        user.setPassword("default");
        user.setEnabled(false);
        user.setAuthorities("ROLE_USER");

        return user;
    }

    public void rollback(Transaction transaction) {
        User creditUser = transaction.getCredit();

        Double sum = transaction.getSum();
        Double userBalance = creditUser.getBalance();

        creditUser.setBalance(userBalance + sum);
        transaction.setStatus("failed");

        try {
            userDAO.save(creditUser);
            transactionDAO.save(transaction);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void rollbackUncompletedTransactions() {
        List<Transaction> transactionList = transactionDAO.getUncompletedTransactions();

        for (Transaction transaction : transactionList) {
            rollback(transaction);
        }
    }

    public List<Transaction> getLimitResults(int id, int start) {
        int startItem = (start - 1) * 10;
        return transactionDAO.limitTransactionsList(id, startItem);
    }

    public int getUsersTransactionsCount(int id) {
        return transactionDAO.usersTransactionCount(id);
    }
}
