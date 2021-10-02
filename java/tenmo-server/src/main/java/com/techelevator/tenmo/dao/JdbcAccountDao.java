package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Component
public class JdbcAccountDao implements AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao() {}

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int userId) {
        String sql = "SELECT balance FROM accounts where user_id = ?";
        SqlRowSet results = null;
        BigDecimal balance = null;

        try {
            results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                balance = results.getBigDecimal("balance");
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing data.");
        }
        return balance;
    }

    @Override
    public void transferFunds(Transfer transfer) {
        BigDecimal fromBalance = new BigDecimal(0);
        BigDecimal toBalance = new BigDecimal(0);
        BigDecimal transferAmount = new BigDecimal(String.valueOf(transfer.getAmount()));

        String sqlTransferSelectFrom = "SELECT * FROM accounts WHERE account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlTransferSelectFrom, transfer.getAccountFrom());
        while (result.next()) {
            fromBalance = (new BigDecimal(result.getDouble("balance")).setScale(2, RoundingMode.HALF_UP));
        }

        String sqlTransferSelectTo = "SELECT * FROM accounts WHERE account_id = ?";
        SqlRowSet result2 = jdbcTemplate.queryForRowSet(sqlTransferSelectTo, transfer.getAccountTo());
        while (result2.next()) {
            toBalance = (new BigDecimal(result2.getDouble("balance")).setScale(2, RoundingMode.HALF_UP));
        }

        fromBalance = fromBalance.subtract(transferAmount);
        toBalance = toBalance.add(transferAmount);

        double fromBalanceD = fromBalance.doubleValue();
        double toBalanceD = toBalance.doubleValue();

        System.out.println(fromBalanceD);
        System.out.println(toBalanceD);

        String sqlTransferFrom = "UPDATE accounts SET balance = ?  WHERE account_id = ?";
        jdbcTemplate.update(sqlTransferFrom, fromBalanceD, transfer.getAccountFrom());

        String sqlTransferTo = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sqlTransferTo, toBalanceD, transfer.getAccountTo());

    }

    @Override
    public Account findUserById(int userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        Account account = null;
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            if (result.next()) {
                account = mapToRowAccount(result);
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing data.");
        }
        return account;
    }

    @Override
    public Account findAccountById(int id) {
        Account account = null;
        String sql = "SELECT * FROM accounts WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            account = mapToRowAccount(results);
        }
        return account;
    }

    private Account mapToRowAccount(SqlRowSet result) {
        Account account = new Account();
        double balanceDbl = result.getDouble("balance");
        account.setBalance(new BigDecimal(balanceDbl));
        account.setAccountId(result.getInt("account_id"));
        account.setUserId(result.getInt("user_id"));
        return account;
    }
}
