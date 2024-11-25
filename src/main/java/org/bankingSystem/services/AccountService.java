package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Account;
import org.bankingSystem.queries.AccountSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountService extends AbstractService {
    public List<Account> getAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(AccountSqlQueries.GET_ACCOUNTS);
            while (rs.next()) {
                Account account = new Account(rs);
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve accounts" + e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closeStatements(st);
        }
        return accounts;
    }

    public Account getAccountById(UUID accountId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_BY_ID);
            ps.setString(1, accountId.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve account by id" + accountId + e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closeStatements(ps);
        }
        return null;
    }

    public Account createAccount(Account account) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(AccountSqlQueries.CREATE_ACCOUNT);
            UUID uuid = UUID.randomUUID();
            ps.setString(1, uuid.toString());
            ps.setString(2, account.getAccountNumber());
            ps.setString(3, account.getAccountType());
            ps.setDouble(4, account.getAccountCurrentBalance());
            ps.setString(5, account.getAccountDateOpened());
            ps.setString(6, account.getAccountDateClosed());
            ps.setString(7, account.getAccountStatus());
            ps.setString(8, account.getCustomerId().toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to create account");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to create account" + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
        }
        return account;
    }

    public Account updateAccountById(UUID accountId, Account account) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_BY_ID);
            ps.setString(1, account.getAccountNumber());
            ps.setString(2, account.getAccountType());
            ps.setDouble(3, account.getAccountCurrentBalance());
            ps.setString(4, account.getAccountDateOpened());
            ps.setString(5, account.getAccountDateClosed());
            ps.setString(6, account.getAccountStatus());
            ps.setString(7, account.getCustomerId().toString());
            ps.setString(8, accountId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update account with id : " + accountId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update account with id: " + accountId + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
        }
        return account;
    }

    public Account updateAccountDateClosedById(UUID accountId, Account account) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_DATE_CLOSED_BY_ID);
            ps.setString(1, account.getAccountDateClosed());
            ps.setString(2, accountId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update date closed of account with id : " + accountId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update account date closed  of account : " + accountId + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
        }
        return account;
    }

    public Account updateAccountCurrentBalanceById(UUID accountId, Account account) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_CURRENT_BALANCE_BY_ID);
            ps.setDouble(1, account.getAccountCurrentBalance());
            ps.setString(2, accountId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update current balance of account with id : " + accountId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update account current balance of account : " + accountId + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
        }
        return account;
    }

    public Account updateAccountStatusById(UUID accountId, Account account) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_STATUS_BY_ID);
            ps.setString(1, account.getAccountStatus());
            ps.setString(2, accountId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update status of account with id : " + accountId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update account status of account : " + accountId + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
        }
        return account;
    }

    public boolean deleteAccountById(UUID accountId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement
                    (AccountSqlQueries.DELETE_ACCOUNT_ID_ON_CARDS)) {
                ps.setString(1, accountId.toString());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected < 1) {
                    throw new SQLException("No rows affected trying to delete card");
                }
            }
            try (PreparedStatement ps = connection.prepareStatement
                    (AccountSqlQueries.DELETE_ACCOUNT_ID_ON_TRANSACTIONS)) {
                ps.setString(1, accountId.toString());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected < 1) {
                    throw new SQLException("No rows affected trying to delete transaction");
                }
            }
            try (PreparedStatement ps = connection.prepareStatement
                    (AccountSqlQueries.DELETE_ACCOUNT_BY_ID)) {
                ps.setString(1, accountId.toString());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected < 1) {
                    throw new SQLException("No rows affected trying to delete account");
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException("Failed to delete account with id: " + accountId + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return true;
    }

    public String getAccountNumberById(UUID accountId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_NUMBER_BY_ID);
            ps.setString(1, accountId.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("account_number");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve account number of account with id " + accountId + e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closePreparedStatement(ps);
        }
        return null;
    }
}