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
            System.out.println("Accounts retrieved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve accounts");
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
            System.out.println("Account with id " + accountId + " retrieved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve account with id: " + accountId);
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
            if (rowsAffected > 0) {
                System.out.println("Account created successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create account");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return accountModel;
    }

    public Account updateAccountById(UUID accountId, Account accountModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.UPDATE_ACCOUNT_BY_ID)) {
            ps.setString(1, accountModel.getAccountNumber());
            ps.setString(2, accountModel.getAccountType());
            ps.setDouble(3, accountModel.getAccountCurrentBalance());
            ps.setString(4, accountModel.getAccountDateOpened());
            ps.setString(5, accountModel.getAccountDateClosed());
            ps.setString(6, accountModel.getAccountStatus());
            ps.setString(7, accountModel.getCustomerId().toString());
            ps.setString(8, accountId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update account");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return accountModel;
    }

    public Account updateAccountDateClosedById(UUID accountId, Account accountModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.UPDATE_ACCOUNT_DATE_CLOSED_BY_ID)) {
            ps.setString(1, accountModel.getAccountDateClosed());
            ps.setString(2, accountId.toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully updated the account date closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update the account date closed");
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
            if (rowsAffected > 0) {
                System.out.println("Successfully updated the account current balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update the account current balance");
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
            if (rowsAffected > 0) {
                System.out.println("Successfully updated the account status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update the account status");
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
                ps.executeUpdate();
            }
            try (PreparedStatement ps = connection.prepareStatement
                    (AccountSqlQueries.DELETE_ACCOUNT_ID_ON_TRANSACTIONS)) {
                ps.setString(1, accountId.toString());
                ps.executeUpdate();
            }
            try (PreparedStatement ps = connection.prepareStatement
                    (AccountSqlQueries.DELETE_ACCOUNT_BY_ID)) {
                ps.setString(1, accountId.toString());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Successfully deleted the account with id: " + accountId);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("Failed to delete the account with id: " + accountId);
            return false;
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
            System.out.println("Account number of user with id " + accountId + " retrieved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve account number of user with id " + accountId);
            throw e;
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return null;
    }
}
