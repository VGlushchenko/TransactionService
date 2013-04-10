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

    private static final String VALID_EMAIL_REGEXP =
            "^[A-Za-z0-9](([_\\.\\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$";

    public boolean create(int id, String debitUserEmail, String inputSum) throws IOException {

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
            transaction.setStatus(false);


            userDAO.save(creditUser);

            transactionLog.setStartedAt(new Date());
            transactionLog.setTransaction(transaction);
            transaction.setLog(transactionLog);

            transactionDAO.save(transaction);

            //Exception

            if (transaction.getLog().getCompletedAt() != null) {
                transaction.setStatus(true);
                transactionDAO.save(transaction);
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
        return email.matches(VALID_EMAIL_REGEXP);
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

    public List<Transaction> getLimitResults(int id, int start) {
        int startItem = (start - 1) * 10;
        return transactionDAO.limitTransactionsList(id, startItem);
    }

    public int getUsersTransactionsCount(int id) {
        return transactionDAO.usersTransactionCount(id);
    }
}
