package org.bankingSystem.services;

import org.bankingSystem.model.Account;
import org.bankingSystem.queries.AccountSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    public List<Account> getAccounts(Connection connection) {
        List<Account> accounts = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(AccountSqlQueries.GET_ACCOUNTS)) {
            while (resultSet.next()) {
                Account accountModel = new Account(resultSet);
                accounts.add(accountModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public Account getAccountById(Connection connection, int accountId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_BY_ID)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Account(resultSet);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Account createAccount(Connection connection, Account accountModel) throws SQLException {
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
                return accountModel;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account updateAccountById(Connection connection, int accountId, Account accountModel) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_BY_ID)) {
            preparedStatement.setString(1, accountModel.getAccountNumber());
            preparedStatement.setString(2, accountModel.getAccountType());
            preparedStatement.setDouble(3, accountModel.getAccountCurrentBalance());
            preparedStatement.setString(4, accountModel.getAccountDateOpened());
            preparedStatement.setString(5, accountModel.getAccountDateClosed());
            preparedStatement.setString(6, accountModel.getAccountStatus());
            preparedStatement.setInt(7, accountModel.getCustomerId());
            preparedStatement.setInt(8, accountId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
                return accountModel;
            } else {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Account updateAccountDateClosedById(Connection connection, int accountId, Account accountModel) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_DATE_CLOSED_BY_ID)) {
            preparedStatement.setString(1, accountModel.getAccountDateClosed());
            preparedStatement.setInt(2, accountId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully updated the account date closed");
                return accountModel;
            } else {
                System.out.println("Failed to update the account date closed");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Account updateAccountCurrentBalanceById(Connection connection, int accountId, Account accountModel) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_CURRENT_BALANCE_BY_ID)) {
            preparedStatement.setDouble(1, accountModel.getAccountCurrentBalance());
            preparedStatement.setInt(2, accountId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully updated the account current balance");
                return accountModel;
            } else {
                System.out.println("Failed to update the account current balance");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Account updateAccountStatusById(Connection connection, int accountId, Account accountModel) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(AccountSqlQueries.UPDATE_ACCOUNT_STATUS_BY_ID)) {
            preparedStatement.setString(1, accountModel.getAccountStatus());
            preparedStatement.setInt(2, accountId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully updated the account status");
                return accountModel;
            } else {
                System.out.println("Failed to update the account status");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteAccountById(Connection connection, int accountId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(AccountSqlQueries.DELETE_ACCOUNT_BY_ID)) {
            preparedStatement.setInt(1, accountId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting account with ID: " + accountId);
        }
    }

    //Additional that were not requested
    public String getAccountNumberById(Connection connection, int accountId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_NUMBER_BY_ID)) {
            statement.setInt(1, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("ACCOUNT_NUMBER");
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}