package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Account;
import org.bankingSystem.queries.AccountSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    public List<Account> getAccounts() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Account> accounts = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(AccountSqlQueries.GET_ACCOUNTS)) {
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

    public Account getAccountById(int accountId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_BY_ID)) {
            ps.setInt(1, accountId);
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(AccountSqlQueries.CREATE_ACCOUNT)) {
            preparedStatement.setString(1, accountModel.getAccountNumber());
            preparedStatement.setString(2, accountModel.getAccountType());
            preparedStatement.setDouble(3, accountModel.getAccountCurrentBalance());
            preparedStatement.setString(4, accountModel.getAccountDateOpened());
            preparedStatement.setString(5, accountModel.getAccountDateClosed());
            preparedStatement.setString(6, accountModel.getAccountStatus());
            preparedStatement.setInt(7, accountModel.getCustomerId());
            int rowsAffected = preparedStatement.executeUpdate();
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

    public Account updateAccountById(int accountId, Account accountModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_BY_ID)) {
            ps.setString(1, accountModel.getAccountNumber());
            ps.setString(2, accountModel.getAccountType());
            ps.setDouble(3, accountModel.getAccountCurrentBalance());
            ps.setString(4, accountModel.getAccountDateOpened());
            ps.setString(5, accountModel.getAccountDateClosed());
            ps.setString(6, accountModel.getAccountStatus());
            ps.setInt(7, accountModel.getCustomerId());
            ps.setInt(8, accountId);
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

    public Account updateAccountDateClosedById(int accountId, Account accountModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_DATE_CLOSED_BY_ID)) {
            ps.setString(1, accountModel.getAccountDateClosed());
            ps.setInt(2, accountId);
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

    public Account updateAccountCurrentBalanceById(int accountId, Account accountModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_CURRENT_BALANCE_BY_ID)) {
            ps.setDouble(1, accountModel.getAccountCurrentBalance());
            ps.setInt(2, accountId);
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

    public Account updateAccountStatusById(int accountId, Account accountModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_STATUS_BY_ID)) {
            ps.setString(1, accountModel.getAccountStatus());
            ps.setInt(2, accountId);
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

    public boolean deleteAccountById(int accountId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.DELETE_ACCOUNT_ID_ON_CARDS)) {
                ps.setInt(1, accountId);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.DELETE_ACCOUNT_ID_ON_TRANSACTIONS)) {
                ps.setInt(1, accountId);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.DELETE_ACCOUNT_BY_ID)) {
                ps.setInt(1, accountId);
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
    public String getAccountNumberById(int accountId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_NUMBER_BY_ID)) {
            ps.setInt(1, accountId);
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
