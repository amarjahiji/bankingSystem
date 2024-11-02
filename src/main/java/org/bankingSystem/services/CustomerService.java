package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
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
    public List<Customer> getCustomers() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Customer> customers = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(CustomerSqlQueries.GET_CUSTOMERS)) {
            while (rs.next()) {
                Customer customerModel = new Customer(rs);
                customers.add(customerModel);
            }
            System.out.println("Customers retrieved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get customers");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return customers;
    }

    public Customer getCustomerById(int customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_BY_ID)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(rs);
            }
            System.out.println("Customer with id: " + customerId + " retrieved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve customer with id: " + customerId);
            throw e;
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return null;
    }

    public List<Customer> getCustomerAccountsById(int customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<Integer, Customer> customerMap = new HashMap<>();
        try (PreparedStatement st = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID)) {
            st.setInt(1, customerId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Integer customerIdd = rs.getInt("customer_id");
                customerMap.putIfAbsent(customerIdd, new Customer(rs));
                if (rs.getInt("account_id") != 0) {
                    Account account = new Account(rs);
                    customerMap.get(customerIdd).addAccount(account);
                }
            }
            System.out.println("Customer accounts retrieved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get customers' accounts");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Customer> getCustomersAccounts() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<Integer, Customer> customerMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Integer customerId = rs.getInt("customer_id");
                customerMap.putIfAbsent(customerId, new Customer(rs));
                if (rs.getInt("account_id") != 0) {
                    Account account = new Account(rs);
                    customerMap.get(customerId).addAccount(account);
                }
            }
            System.out.println("Customer accounts retrieved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get customers accounts");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(customerMap.values());
    }

    public Customer createCustomer(Customer customerModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.CREATE_CUSTOMER)) {
            ps.setString(1, customerModel.getCustomerFirstName());
            ps.setString(2, customerModel.getCustomerLastName());
            ps.setString(3, customerModel.getCustomerDateOfBirth());
            ps.setString(4, customerModel.getCustomerEmail());
            ps.setString(5, customerModel.getCustomerPhoneNumber());
            ps.setString(6, customerModel.getCustomerAddress());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Insert successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create customer");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return customerModel;
    }

    public Customer updateCustomerById(int customerId, Customer customerModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_BY_ID)) {
            ps.setString(1, customerModel.getCustomerFirstName());
            ps.setString(2, customerModel.getCustomerLastName());
            ps.setString(3, customerModel.getCustomerDateOfBirth());
            ps.setString(4, customerModel.getCustomerEmail());
            ps.setString(5, customerModel.getCustomerPhoneNumber());
            ps.setString(6, customerModel.getCustomerAddress());
            ps.setInt(7, customerId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update failed, no rows affected.");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return customerModel;
    }

    public Customer updateCustomerAddressById(int customerId, Customer customerModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_ADDRESS_BY_ID)) {
            ps.setString(1, customerModel.getCustomerAddress());
            ps.setInt(2, customerId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update failed, no rows affected.");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return customerModel;
    }

    public Customer updateCustomerEmailById(int customerId, Customer customerModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_EMAIL_BY_ID)) {
            ps.setString(1, customerModel.getCustomerEmail());
            ps.setInt(2, customerId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update failed, no rows affected.");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return customerModel;
    }

    public Customer updateCustomerPhoneNumberById(int customerId, Customer customerModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_PHONE_NUMBER_BY_ID)) {
            ps.setString(1, customerModel.getCustomerPhoneNumber());
            ps.setInt(2, customerId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update failed, no rows affected.");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return customerModel;
    }

    public boolean deleteCustomerById(int customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.DELETE_CUSTOMER_BY_ID)) {
            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Delete successful! " + rowsAffected + " rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Delete failed, no rows affected.");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return true;
    }


    //Additional services that were not required
    public List<Customer> getCustomerAccountsTransactionsById(int customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<Integer, Customer> customerMap = new HashMap<>();
        Map<Integer, Account> accountMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer customerIdd = rs.getInt("customer_id");
                Integer accountId = rs.getInt("account_id");
                customerMap.putIfAbsent(customerIdd, new Customer(rs));
                if (!accountMap.containsKey(accountId)) {
                    Account accountModel = new Account(rs);
                    customerMap.get(customerIdd).addAccount(accountModel);
                    accountMap.putIfAbsent(accountId, accountModel);
                }
            }
            System.out.println("Accounts of customer retrieved successfully.");
            for (Account account : accountMap.values()) {
                List<Transaction> transactions = getTransactionsForAccount(account.getAccountId());
                if (!transactions.isEmpty()) {
                    account.setTransaction(transactions);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get customers and accounts and transactions");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Transaction> getTransactionsForAccount(int accountId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_TRANSACTIONS_BY_ID)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(rs));
            }
            System.out.println("Transactions retrieved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving transactions by ID: " + accountId);
        }
        return transactions;
    }

    public List<Customer> getCustomersAccountsTransactions() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<Integer, Customer> customerMap = new HashMap<>();
        Map<Integer, Account> accountMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer customerIdd = rs.getInt("customer_id");
                Integer accountId = rs.getInt("account_id");
                customerMap.putIfAbsent(customerIdd, new Customer(rs));
                if (!accountMap.containsKey(accountId)) {
                    Account accountModel = new Account(rs);
                    customerMap.get(customerIdd).addAccount(accountModel);
                    accountMap.putIfAbsent(accountId, accountModel);
                }
            }
            System.out.println("Accounts of customer retrieved successfully.");
            for (Account account : accountMap.values()) {
                List<Transaction> transactions = getTransactionsForAccount(account.getAccountId());

                if (!transactions.isEmpty()) {
                    account.setTransaction(transactions);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get customers and accounts and transactions");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Customer> getCustomerAccountsCardsById(int customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<Integer, Customer> customerMap = new HashMap<>();
        Map<Integer, Account> accountMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer customerIdd = rs.getInt("customer_id");
                Integer accountId = rs.getInt("account_id");
                customerMap.putIfAbsent(customerIdd, new Customer(rs));
                if (!accountMap.containsKey(accountId)) {
                    Account accountModel = new Account(rs);
                    customerMap.get(customerIdd).addAccount(accountModel);
                    accountMap.putIfAbsent(accountId, accountModel);
                }
            }
            System.out.println("Accounts of customer retrieved successfully.");
            for (Account account : accountMap.values()) {
                List<Card> cards = getCardsForAccountsById(account.getAccountId());

                if (!cards.isEmpty()) {
                    account.setCards(cards);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get customers and accounts and cards");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Card> getCardsForAccountsById(int accountId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Card> cards = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_CARDS_BY_ID)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cards.add(new Card(rs));
            }
            System.out.println("Cards retrieved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving cards by ID: " + accountId);
        }
        return cards;
    }

    public List<Customer> getCustomersAccountsCards() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<Integer, Customer> customerMap = new HashMap<>();
        Map<Integer, Account> accountMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer customerIdd = rs.getInt("customer_id");
                Integer accountId = rs.getInt("account_id");
                customerMap.putIfAbsent(customerIdd, new Customer(rs));
                if (!accountMap.containsKey(accountId)) {
                    Account accountModel = new Account(rs);
                    customerMap.get(customerIdd).addAccount(accountModel);
                    accountMap.putIfAbsent(accountId, accountModel);
                }
            }
            System.out.println("Accounts of customer retrieved successfully.");
            for (Account account : accountMap.values()) {
                List<Card> cards = getCardsForAccountsById(account.getAccountId());

                if (!cards.isEmpty()) {
                    account.setCards(cards);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get customers and accounts and cards");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(customerMap.values());
    }

    public String getCustomerFirstNameById(int customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_FIRST_NAME_BY_ID)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving customers and accounts");
            throw e;
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return null;
    }
}
