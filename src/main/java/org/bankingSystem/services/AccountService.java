package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Account;
import org.bankingSystem.queries.AccountSqlQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountService {
    public List<Account> getAccounts() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.GET_ACCOUNTS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Account accountModel = new Account(rs);
                accounts.add(accountModel);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve accounts" + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return accounts;
    }

    public Account getAccountById(UUID accountId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.GET_ACCOUNT_BY_ID)) {
            ps.setString(1, accountId.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve account by id" + accountId + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return null;
    }

    public Account createAccount(Account accountModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.CREATE_ACCOUNT)) {
            UUID uuid = UUID.randomUUID();
            ps.setString(1, uuid.toString());
            ps.setString(2, accountModel.getAccountNumber());
            ps.setString(3, accountModel.getAccountType());
            ps.setDouble(4, accountModel.getAccountCurrentBalance());
            ps.setString(5, accountModel.getAccountDateOpened());
            ps.setString(6, accountModel.getAccountDateClosed());
            ps.setString(7, accountModel.getAccountStatus());
            ps.setString(8, accountModel.getCustomerId().toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to create account");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to create account" + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return accountModel;
    }

    public Account updateAccountById(UUID accountId, Account account) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.UPDATE_ACCOUNT_BY_ID)) {
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
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return account;
    }

    public Account updateAccountDateClosedById(UUID accountId, Account accountModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.UPDATE_ACCOUNT_DATE_CLOSED_BY_ID)) {
            ps.setString(1, accountModel.getAccountDateClosed());
            ps.setString(2, accountId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update date closed of account with id : " + accountId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update account date closed  of account : " + accountId + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return accountModel;
    }

    public Account updateAccountCurrentBalanceById(UUID accountId, Account accountModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.UPDATE_ACCOUNT_CURRENT_BALANCE_BY_ID)) {
            ps.setDouble(1, accountModel.getAccountCurrentBalance());
            ps.setString(2, accountId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update current balance of account with id : " + accountId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update account current balance of account : " + accountId + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return accountModel;
    }

    public Account updateAccountStatusById(UUID accountId, Account accountModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.UPDATE_ACCOUNT_STATUS_BY_ID)) {
            ps.setString(1, accountModel.getAccountStatus());
            ps.setString(2, accountId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update status of account with id : " + accountId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update account status of account : " + accountId + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return accountModel;
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

    //Additional that were not requested
    public String getAccountNumberById(UUID accountId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.GET_ACCOUNT_NUMBER_BY_ID)) {
            ps.setString(1, accountId.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("account_number");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve account number of account with id " + accountId + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return null;
    }
}
