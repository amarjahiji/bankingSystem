package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Account;
import org.bankingSystem.model.Card;
import org.bankingSystem.model.Customer;
import org.bankingSystem.model.Transaction;
import org.bankingSystem.queries.AccountSqlQueries;
import org.bankingSystem.queries.CustomerSqlQueries;

import java.sql.*;
import java.util.*;

public class CustomerService {
    public List<Customer> getCustomers() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Customer> customers = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery
                     (CustomerSqlQueries.GET_CUSTOMERS)) {
            while (rs.next()) {
                Customer customer = new Customer(rs);
                customers.add(customer);
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

    public Integer getTotalNumberOfCustomers() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery
                     (CustomerSqlQueries.GET_TOTAL_NUMBER_OF_CUSTOMERS)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public List<Customer> getOldCustomers() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Customer> customers = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery
                     (CustomerSqlQueries.GET_OLD_CUSTOMERS)) {
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

    public Integer getTotalNumberOfOldCustomers() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery
                     (CustomerSqlQueries.GET_TOTAL_NUMBER_OF_OLD_CUSTOMERS)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public List<Customer> getYoungCustomers() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Customer> customers = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery
                     (CustomerSqlQueries.GET_YOUNG_CUSTOMERS)) {
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

    public Integer getTotalNumberOfYoungCustomers() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery
                     (CustomerSqlQueries.GET_TOTAL_NUMBER_OF_YOUNG_CUSTOMERS)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public Customer getCustomerById(UUID customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.GET_CUSTOMER_BY_ID)) {
            ps.setString(1, customerId.toString());
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

    public List<Customer> getCustomerAccountsById(UUID customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<UUID, Customer> customerMap = new HashMap<>();
        try (PreparedStatement st = connection.prepareStatement
                (CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID)) {
            st.setString(1, customerId.toString());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                UUID customerIdd = UUID.fromString(rs.getString("customer_id"));
                customerMap.putIfAbsent(customerIdd, new Customer(rs));
                if (rs.getString("account_id") != null) {
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
        Map<UUID, Customer> customerMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UUID customerId = UUID.fromString(rs.getString("customer_id"));
                customerMap.putIfAbsent(customerId, new Customer(rs));
                if (rs.getString("account_id") != null) {
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
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.CREATE_CUSTOMER)) {
            UUID uuid = UUID.randomUUID();
            ps.setString(1, uuid.toString());
            ps.setString(2, customerModel.getCustomerFirstName());
            ps.setString(3, customerModel.getCustomerLastName());
            ps.setString(4, customerModel.getCustomerDateOfBirth());
            ps.setString(5, customerModel.getCustomerEmail());
            ps.setString(6, customerModel.getCustomerPhoneNumber());
            ps.setString(7, customerModel.getCustomerAddress());
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

    public Customer updateCustomerById(UUID customerId, Customer customerModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.UPDATE_CUSTOMER_BY_ID)) {
            ps.setString(1, customerModel.getCustomerFirstName());
            ps.setString(2, customerModel.getCustomerLastName());
            ps.setString(3, customerModel.getCustomerDateOfBirth());
            ps.setString(4, customerModel.getCustomerEmail());
            ps.setString(5, customerModel.getCustomerPhoneNumber());
            ps.setString(6, customerModel.getCustomerAddress());
            ps.setString(7, customerId.toString());
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

    public Customer updateCustomerAddressById(UUID customerId, Customer customerModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.UPDATE_CUSTOMER_ADDRESS_BY_ID)) {
            ps.setString(1, customerModel.getCustomerAddress());
            ps.setString(2, customerId.toString());
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

    public Customer updateCustomerEmailById(UUID customerId, Customer customerModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.UPDATE_CUSTOMER_EMAIL_BY_ID)) {
            ps.setString(1, customerModel.getCustomerEmail());
            ps.setString(2, customerId.toString());
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

    public Customer updateCustomerPhoneNumberById(UUID customerId, Customer customerModel) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.UPDATE_CUSTOMER_PHONE_NUMBER_BY_ID)) {
            ps.setString(1, customerModel.getCustomerPhoneNumber());
            ps.setString(2, customerId.toString());
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

    public boolean deleteCustomerById(UUID customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement
                    (CustomerSqlQueries.DELETE_CUSTOMER_FROM_ACCOUNTS)) {
                ps.setString(1, customerId.toString());
                ps.executeUpdate();
            }
            try (PreparedStatement ps = connection.prepareStatement
                    (CustomerSqlQueries.DELETE_CUSTOMER_BY_ID)) {
                ps.setString(1, customerId.toString());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Successfully deleted the account with id: " + customerId);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("Failed to delete the account with id: " + customerId);
            return false;
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return true;
    }

    //Additional services that were not required
    public List<Customer> getCustomerAccountsTransactionsById(UUID customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<UUID, Customer> customerMap = new HashMap<>();
        Map<UUID, Account> accountMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID)) {
            ps.setString(1, customerId.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID customerIdd = UUID.fromString(rs.getString("customer_id"));
                UUID accountId = UUID.fromString(rs.getString("account_id"));
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

    public List<Transaction> getTransactionsForAccount(UUID accountId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.GET_ACCOUNT_TRANSACTIONS_BY_ID)) {
            ps.setString(1, accountId.toString());
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
        Map<UUID, Customer> customerMap = new HashMap<>();
        Map<UUID, Account> accountMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String customerIdStr = rs.getString("customer_id");
                String accountIdStr = rs.getString("account_id");
                if (customerIdStr != null && !customerIdStr.isEmpty()) {
                    UUID customerIdd = UUID.fromString(customerIdStr);
                    customerMap.putIfAbsent(customerIdd, new Customer(rs));
                    if (accountIdStr != null && !accountIdStr.isEmpty() && !accountMap.containsKey(UUID.fromString(accountIdStr))) {
                        UUID accountId = UUID.fromString(accountIdStr);
                        Account accountModel = new Account(rs);
                        customerMap.get(customerIdd).addAccount(accountModel);
                        accountMap.putIfAbsent(accountId, accountModel);
                    }
                } else {
                    System.out.println("Null or empty customer ID found in the ResultSet");
                }
            }
            System.out.println("Accounts of customers retrieved successfully.");

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

    public List<Customer> getCustomerAccountsCardsById(UUID customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<UUID, Customer> customerMap = new HashMap<>();
        Map<UUID, Account> accountMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID)) {
            ps.setString(1, customerId.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String customerIdStr = rs.getString("customer_id");
                String accountIdStr = rs.getString("account_id");
                if (customerIdStr != null && !customerIdStr.isEmpty()) {
                    UUID customerIdd = UUID.fromString(customerIdStr);
                    customerMap.putIfAbsent(customerIdd, new Customer(rs));
                    if (accountIdStr != null && !accountIdStr.isEmpty() && !accountMap.containsKey(UUID.fromString(accountIdStr))) {
                        UUID accountId = UUID.fromString(accountIdStr);
                        Account accountModel = new Account(rs);
                        customerMap.get(customerIdd).addAccount(accountModel);
                        accountMap.putIfAbsent(accountId, accountModel);
                    }
                } else {
                    System.out.println("Null or empty customer ID found in the ResultSet");
                }
            }
            System.out.println("Accounts of customers retrieved successfully.");

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

    public List<Card> getCardsForAccountsById(UUID accountId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Card> cards = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.GET_ACCOUNT_CARDS_BY_ID)) {
            ps.setString(1, accountId.toString());
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
        Map<UUID, Customer> customerMap = new HashMap<>();
        Map<UUID, Account> accountMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String customerIdStr = rs.getString("customer_id");
                String accountIdStr = rs.getString("account_id");
                if (customerIdStr != null && !customerIdStr.isEmpty()) {
                    UUID customerIdd = UUID.fromString(customerIdStr);
                    customerMap.putIfAbsent(customerIdd, new Customer(rs));
                    if (accountIdStr != null && !accountIdStr.isEmpty() && !accountMap.containsKey(UUID.fromString(accountIdStr))) {
                        UUID accountId = UUID.fromString(accountIdStr);
                        Account accountModel = new Account(rs);
                        customerMap.get(customerIdd).addAccount(accountModel);
                        accountMap.putIfAbsent(accountId, accountModel);
                    }
                } else {
                    System.out.println("Null or empty customer ID found");
                }
            }
            System.out.println("Accounts of customers retrieved successfully.");

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

    public String getCustomerFirstNameById(UUID customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.GET_CUSTOMER_FIRST_NAME_BY_ID)) {
            ps.setString(1, customerId.toString());
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
