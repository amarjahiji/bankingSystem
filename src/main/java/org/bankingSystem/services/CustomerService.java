package org.bankingSystem.services;

import org.bankingSystem.model.Account;
import org.bankingSystem.model.Card;
import org.bankingSystem.model.Customer;
import org.bankingSystem.model.Transaction;
import org.bankingSystem.queries.AccountSqlQueries;
import org.bankingSystem.queries.CustomerSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {
    public List<Customer> getCustomers(Connection connection) {
        List<Customer> customers = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(CustomerSqlQueries.GET_CUSTOMERS)) {
            while (resultSet.next()) {
                Customer customerModel = new Customer(resultSet);
                customers.add(customerModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static Customer getCustomerById(Connection connection, int customerId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_BY_ID)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Customer(resultSet);
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Customer> getCustomersAccounts(Connection connection) {
        Map<Integer, Customer> customerMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer customerId = resultSet.getInt("CUSTOMER_ID");
                    customerMap.put(customerId, new Customer(resultSet));
                    customerMap.get(customerId).getAccount().add(new Account(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Customer> getCustomerAccountsById(Connection connection, int customerId) throws SQLException {
        Map<Integer, Customer> customerMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID)) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer customerIdd = resultSet.getInt("CUSTOMER_ID");
                    customerMap.putIfAbsent(customerIdd, new Customer(resultSet));
                    customerMap.get(customerIdd).getAccount().add(new Account(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(customerMap.values());
    }

    public static Customer createCustomer(Connection connection, Customer customerModel) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.CREATE_CUSTOMER)) {
            preparedStatement.setString(1, customerModel.getCustomerFirstName());
            preparedStatement.setString(2, customerModel.getCustomerLastName());
            preparedStatement.setString(3, customerModel.getCustomerDateOfBirth());
            preparedStatement.setString(4, customerModel.getCustomerEmail());
            preparedStatement.setString(5, customerModel.getCustomerPhoneNumber());
            preparedStatement.setString(6, customerModel.getCustomerAddress());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Insert successful!");
                return customerModel;
            } else {
                throw new SQLException("Insert failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer updateCustomerById(Connection connection, int customerId, Customer customerModel) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_BY_ID)) {
            preparedStatement.setString(1, customerModel.getCustomerFirstName());
            preparedStatement.setString(2, customerModel.getCustomerLastName());
            preparedStatement.setString(3, customerModel.getCustomerDateOfBirth());
            preparedStatement.setString(4, customerModel.getCustomerEmail());
            preparedStatement.setString(5, customerModel.getCustomerPhoneNumber());
            preparedStatement.setString(6, customerModel.getCustomerAddress());
            preparedStatement.setInt(7, customerId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
                return customerModel;
            } else {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer updateCustomerAddressById(Connection connection, int customerId, Customer customerModel) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_ADDRESS_BY_ID)) {
            preparedStatement.setString(1, customerModel.getCustomerAddress());
            preparedStatement.setInt(2, customerId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
                return customerModel;
            } else {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer updateCustomerEmailById(Connection connection, int customerId, Customer customerModel) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_EMAIL_BY_ID)) {
            preparedStatement.setString(1, customerModel.getCustomerEmail());
            preparedStatement.setInt(2, customerId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
                return customerModel;
            } else {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer updateCustomerPhoneNumberById(Connection connection, int customerId, Customer customerModel) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_PHONE_NUMBER_BY_ID)) {
            preparedStatement.setString(1, customerModel.getCustomerPhoneNumber());
            preparedStatement.setInt(2, customerId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
                return customerModel;
            } else {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteCustomerById(Connection connection, int customerId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.DELETE_CUSTOMER_BY_ID)) {
            preparedStatement.setInt(1, customerId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting customer with ID: " + customerId);
        }
    }


    //Additional services that were not required
    public List<Customer> getCustomerAccountsTransactionsById(Connection connection, int customerId) throws SQLException {
        Map<Integer, Customer> customerMap = new HashMap<>();
        Map<Integer, Account> accountMap = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer customerIdd = resultSet.getInt("CUSTOMER_ID");
                    Integer accountId = resultSet.getInt("ACCOUNT_ID");
                    customerMap.putIfAbsent(customerIdd, new Customer(resultSet));
                    if (!accountMap.containsKey(accountId)) {
                        Account accountModel = new Account(resultSet);
                        customerMap.get(customerIdd).getAccount().add(accountModel);
                        accountMap.putIfAbsent(accountId, accountModel);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Account account : accountMap.values()) {
            List<Transaction> transactions = getTransactionsForAccountById(connection, account.getAccountId());
            account.setTransaction(transactions);
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Transaction> getTransactionsForAccountById(Connection connection, int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_TRANSACTIONS_BY_ID)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(new Transaction(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Customer> getCustomersAccountsTransactions(Connection connection) throws SQLException {
        Map<Integer, Customer> customerMap = new HashMap<>();
        Map<Integer, Account> accountMap = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer customerIdd = resultSet.getInt("CUSTOMER_ID");
                    Integer accountId = resultSet.getInt("ACCOUNT_ID");
                    customerMap.put(customerIdd, new Customer(resultSet));
                    if (!accountMap.containsKey(accountId)) {
                        Account accountModel = new Account(resultSet);
                        customerMap.get(customerIdd).getAccount().add(accountModel);
                        accountMap.put(accountId, accountModel);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Account account : accountMap.values()) {
            List<Transaction> transactions = getTransactionsForAccount(connection, account.getAccountId());
            account.setTransaction(transactions);
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Transaction> getTransactionsForAccount(Connection connection, int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_TRANSACTIONS_BY_ID)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(new Transaction(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Customer> getCustomerAccountsCardsById(Connection connection, int customerId) throws SQLException {
        Map<Integer, Customer> customerMap = new HashMap<>();
        Map<Integer, Account> accountMap = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer customerIdd = resultSet.getInt("CUSTOMER_ID");
                    Integer accountId = resultSet.getInt("ACCOUNT_ID");
                    customerMap.putIfAbsent(customerIdd, new Customer(resultSet));
                    if (!accountMap.containsKey(accountId)) {
                        Account accountModel = new Account(resultSet);
                        customerMap.get(customerIdd).getAccount().add(accountModel);
                        accountMap.put(accountId, accountModel);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Account account : accountMap.values()) {
            List<Card> cards = getAccountCardsForAccountsById(connection, account.getAccountId());
            account.setCards(cards);
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Card> getAccountCardsForAccountsById(Connection connection, int accountId) throws SQLException {
        List<Card> card = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_CARDS_BY_ID)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    card.add(new Card(resultSet));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return card;
    }

    public List<Customer> getCustomersAccountsCards(Connection connection) throws SQLException {
        Map<Integer, Customer> customerMap = new HashMap<>();
        Map<Integer, Account> accountMap = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer customerIdd = resultSet.getInt("CUSTOMER_ID");
                    Integer accountId = resultSet.getInt("ACCOUNT_ID");
                    customerMap.putIfAbsent(customerIdd, new Customer(resultSet));
                    if (!accountMap.containsKey(accountId)) {
                        Account accountModel = new Account(resultSet);
                        customerMap.get(customerIdd).getAccount().add(accountModel);
                        accountMap.put(accountId, accountModel);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Account account : accountMap.values()) {
            List<Card> cards = getAccountCardsForAccountsById(connection, account.getAccountId());
            account.setCards(cards);
        }
        return new ArrayList<>(customerMap.values());
    }

    public static String getCustomerFirstNameById(Connection connection, int customerId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_FIRST_NAME_BY_ID)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(1);
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
