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

public class CustomerService extends AbstractService {
    public List<Customer> getCustomers(String query) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Customer> customers = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Customer customer = new Customer(rs);
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get customers" +
                    e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closeStatements(st);
        }
        return customers;
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return getCustomers(CustomerSqlQueries.GET_CUSTOMERS);
    }

    public List<Customer> getOldCustomers() throws SQLException {
        return getCustomers(CustomerSqlQueries.GET_OLD_CUSTOMERS);
    }

    public List<Customer> getYoungCustomers() throws SQLException {
        return getCustomers(CustomerSqlQueries.GET_YOUNG_CUSTOMERS);
    }

    public List<Customer> getCustomersOfCertainAge(String firstValue, String secondValue) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Customer> customers = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.GET_CERTAIN_AGE_CUSTOMERS);
            ps.setString(1, firstValue);
            ps.setString(2, secondValue);
            rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer(rs);
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get customers" +
                    e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closeStatements(ps);
        }
        return customers;
    }

    public Customer getCustomerById(UUID customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_BY_ID);
            ps.setString(1, customerId.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get customer by id :" + customerId +
                    e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closeStatements(ps);
        }
        return null;
    }

    public List<Customer> getCustomerAccountsById(UUID customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<UUID, Customer> customerMap = new HashMap<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps =connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID);
            ps.setString(1, customerId.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                UUID customerUuid = UUID.fromString(rs.getString("customer_id"));
                customerMap.putIfAbsent(customerUuid, new Customer(rs));
                if (rs.getString("account_id") != null) {
                    Account account = new Account(rs);
                    customerMap.get(customerUuid).addAccount(account);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get customer accounts by id: " + customerId + e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closeStatements(ps);
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Customer> getCustomersAccounts() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<UUID, Customer> customerMap = new HashMap<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS);
             rs = ps.executeQuery();
            while (rs.next()) {
                UUID customerId = UUID.fromString(rs.getString("customer_id"));
                customerMap.putIfAbsent(customerId, new Customer(rs));
                if (rs.getString("account_id") != null) {
                    Account account = new Account(rs);
                    customerMap.get(customerId).addAccount(account);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get customer accounts: " + e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closeStatements(ps);
        }

        List<Customer> sortedCustomers = new ArrayList<>(customerMap.values());
        sortedCustomers.sort(Comparator.comparing(Customer::getCustomerFirstName));

        return sortedCustomers;
    }

    public Customer createCustomer(Customer customer) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement ps = null;
        int rowsAffected = 0;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.CREATE_CUSTOMER);
            UUID uuid = UUID.randomUUID();
            ps.setString(1, uuid.toString());
            ps.setString(2, customer.getCustomerFirstName());
            ps.setString(3, customer.getCustomerLastName());
            ps.setString(4, customer.getCustomerDateOfBirth());
            ps.setString(5, customer.getCustomerEmail());
            ps.setString(6, customer.getCustomerPhoneNumber());
            ps.setString(7, customer.getCustomerAddress());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to create a customer");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to create customer: " + e.getMessage());
        } finally {
            closeConnection(connection);
            closeStatements(ps);
        }
        return customer;
    }

    public Customer updateCustomerById(UUID customerId, Customer customer) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement ps = null;
        int rowsAffected = 0;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_BY_ID);
            ps.setString(1, customer.getCustomerFirstName());
            ps.setString(2, customer.getCustomerLastName());
            ps.setString(3, customer.getCustomerDateOfBirth());
            ps.setString(4, customer.getCustomerEmail());
            ps.setString(5, customer.getCustomerPhoneNumber());
            ps.setString(6, customer.getCustomerAddress());
            ps.setString(7, customerId.toString());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update  customer by id: " + customerId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update customer with id: " + customerId + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
        }
        return customer;
    }

    public Customer updateCustomerAddressById(UUID customerId, Customer customer) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement ps = null;
        int rowsAffected = 0;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_ADDRESS_BY_ID);
            ps.setString(1, customer.getCustomerAddress());
            ps.setString(2, customerId.toString());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update address of customer with id: " + customerId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update customer address with id: " + customerId + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
            }
        return customer;
    }

    public Customer updateCustomerEmailById(UUID customerId, Customer customer) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement ps = null;
        int rowsAffected = 0;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_EMAIL_BY_ID);
            ps.setString(1, customer.getCustomerEmail());
            ps.setString(2, customerId.toString());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update email of customer with id: " + customerId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update customer email with id: " + customerId + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
        }
        return customer;
    }

    public Customer updateCustomerPhoneNumberById(UUID customerId, Customer customer) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement ps = null;
        int rowsAffected = 0;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER_PHONE_NUMBER_BY_ID);
            ps.setString(1, customer.getCustomerPhoneNumber());
            ps.setString(2, customerId.toString());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update phone number of customer with id: " + customerId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update customer phone number with id: " + customerId + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
        }
        return customer;
    }

    public boolean deleteCustomerById(UUID customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement
                    (CustomerSqlQueries.DELETE_CUSTOMER_FROM_ACCOUNTS)) {
                ps.setString(1, customerId.toString());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected < 1) {
                    throw new SQLException("No rows affected when trying to delete account ");
                }
            }
            try (PreparedStatement ps = connection.prepareStatement
                    (CustomerSqlQueries.DELETE_CUSTOMER_BY_ID)) {
                ps.setString(1, customerId.toString());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected < 1) {
                    throw new SQLException("No rows affected when trying tp delete customer");
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException("Failed to delete customer with id: " + customerId + e.getMessage());
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID);
            ps.setString(1, customerId.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                String customerIdStr = rs.getString("customer_id");
                String accountIdStr = rs.getString("account_id");
                if (customerIdStr != null && !customerIdStr.isEmpty()) {
                    UUID customerUuid = UUID.fromString(customerIdStr);
                    customerMap.putIfAbsent(customerUuid, new Customer(rs));
                    if (accountIdStr != null && !accountIdStr.isEmpty() && !accountMap.containsKey(UUID.fromString(accountIdStr))) {
                        UUID accountUuid = UUID.fromString(accountIdStr);
                        Account account = new Account(rs);
                        customerMap.get(customerUuid).addAccount(account);
                        accountMap.putIfAbsent(accountUuid, account);
                    }
                } else {
                    throw new SQLException("Null or empty customer ID found");
                }
            }
            for (Account account : accountMap.values()) {
                List<Transaction> transactions = getTransactionsForAccount(connection, account.getAccountId());
                if (!transactions.isEmpty()) {
                    account.setTransaction(transactions);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve customers accounts and transactions");
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
            closeResultSet(rs);
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Customer> getCustomersAccountsTransactions() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<UUID, Customer> customerMap = new HashMap<>();
        Map<UUID, Account> accountMap = new HashMap<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS);
            rs = ps.executeQuery();
            while (rs.next()) {
                String customerIdStr = rs.getString("customer_id");
                String accountIdStr = rs.getString("account_id");
                if (customerIdStr != null && !customerIdStr.isEmpty()) {
                    UUID customerUuid = UUID.fromString(customerIdStr);
                    customerMap.putIfAbsent(customerUuid, new Customer(rs));
                    if (accountIdStr != null && !accountIdStr.isEmpty() && !accountMap.containsKey(UUID.fromString(accountIdStr))) {
                        UUID accountUuid = UUID.fromString(accountIdStr);
                        Account account = new Account(rs);
                        customerMap.get(customerUuid).addAccount(account);
                        accountMap.putIfAbsent(accountUuid, account);
                    }
                } else {
                    throw new SQLException("Null or empty customer ID found");
                }
            }
            for (Account account : accountMap.values()) {
                List<Transaction> transactions = getTransactionsForAccount(connection, account.getAccountId());
                if (!transactions.isEmpty()) {
                    account.setTransaction(transactions);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve customers accounts and transactions");
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
            closeResultSet(rs);
        }
        List<Customer> sortedCustomers = new ArrayList<>(customerMap.values());
        sortedCustomers.sort(Comparator.comparing(Customer::getCustomerFirstName));

        return sortedCustomers;
    }

    public List<Transaction> getTransactionsForAccount(Connection connection, UUID accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(AccountSqlQueries.GET_ACCOUNT_TRANSACTIONS_BY_ID)) {
            ps.setString(1, accountId.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve transactions for account with id: " + accountId + e.getMessage());
        }
        return transactions;
    }

    public List<Customer> getCustomerAccountsCardsById(UUID customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<UUID, Customer> customerMap = new HashMap<>();
        Map<UUID, Account> accountMap = new HashMap<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID);
            ps.setString(1, customerId.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                String customerIdStr = rs.getString("customer_id");
                String accountIdStr = rs.getString("account_id");
                if (customerIdStr != null && !customerIdStr.isEmpty()) {
                    UUID customerUuid = UUID.fromString(customerIdStr);
                    customerMap.putIfAbsent(customerUuid, new Customer(rs));
                    if (accountIdStr != null && !accountIdStr.isEmpty() && !accountMap.containsKey(UUID.fromString(accountIdStr))) {
                        UUID accountUuid = UUID.fromString(accountIdStr);
                        Account account = new Account(rs);
                        customerMap.get(customerUuid).addAccount(account);
                        accountMap.putIfAbsent(accountUuid, account);
                    }
                } else {
                    throw new SQLException("Null or empty customer ID found");
                }
            }
            for (Account account : accountMap.values()) {
                List<Card> cards = getCardsForAccounts(connection, account.getAccountId());
                if (!cards.isEmpty()) {
                    account.setCards(cards);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve customers cards and transactions");
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
            closeResultSet(rs);
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Customer> getCustomersAccountsCards() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<UUID, Customer> customerMap = new HashMap<>();
        Map<UUID, Account> accountMap = new HashMap<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS);
            rs = ps.executeQuery();
            while (rs.next()) {
                String customerIdStr = rs.getString("customer_id");
                String accountIdStr = rs.getString("account_id");
                if (customerIdStr != null && !customerIdStr.isEmpty()) {
                    UUID customerUuid = UUID.fromString(customerIdStr);
                    customerMap.putIfAbsent(customerUuid, new Customer(rs));
                    if (accountIdStr != null && !accountIdStr.isEmpty() && !accountMap.containsKey(UUID.fromString(accountIdStr))) {
                        UUID accountUuid = UUID.fromString(accountIdStr);
                        Account account = new Account(rs);
                        customerMap.get(customerUuid).addAccount(account);
                        accountMap.putIfAbsent(accountUuid, account);
                    }
                } else {
                    throw new SQLException("Null or empty customer ID found");
                }
            }
            for (Account account : accountMap.values()) {
                List<Card> cards = getCardsForAccounts(connection, account.getAccountId());
                if (!cards.isEmpty()) {
                    account.setCards(cards);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve customers cards and transactions");
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
            closeResultSet(rs);
        }
        List<Customer> sortedCustomers = new ArrayList<>(customerMap.values());
        sortedCustomers.sort(Comparator.comparing(Customer::getCustomerFirstName));

        return sortedCustomers;
    }

    public List<Card> getCardsForAccounts(Connection connection, UUID accountId) throws SQLException {
        List<Card> cards = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.GET_ACCOUNT_CARDS_BY_ID)) {
            ps.setString(1, accountId.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cards.add(new Card(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve cards for account with id: " + accountId + e.getMessage());
        }
        return cards;
    }

    public String getCustomerFirstNameById(UUID customerId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_FIRST_NAME_BY_ID);
            ps.setString(1, customerId.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve customer first name by id: " + customerId + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
            closeResultSet(rs);
        }
        return null;
    }
}