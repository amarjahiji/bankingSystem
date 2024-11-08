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
    public List<Customer> getCustomers(String query) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Customer> customers = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Customer customer = new Customer(rs);
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get customers" +
                    e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
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

    public Integer getTotalNumberOfCustomers(String query) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get total number of customers" +
                    e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return 0;
    }

    public Integer getTotalNumberOfAllCustomers() throws SQLException {
        return getTotalNumberOfCustomers(CustomerSqlQueries.GET_TOTAL_NUMBER_OF_CUSTOMERS);
    }

    public Integer getTotalNumberOfOldCustomers() throws SQLException {
        return getTotalNumberOfCustomers(CustomerSqlQueries.GET_TOTAL_NUMBER_OF_OLD_CUSTOMERS);
    }

    public Integer getTotalNumberOfYoungCustomers() throws SQLException {
        return getTotalNumberOfCustomers(CustomerSqlQueries.GET_TOTAL_NUMBER_OF_YOUNG_CUSTOMERS);
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
        } catch (SQLException e) {
            throw new SQLException("Failed to get customer by id :" + customerId +
                    e.getMessage());
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
        } catch (SQLException e) {
            throw new SQLException("Failed to get customer accounts: " + e.getMessage());
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
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to create a customer");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to create customer: " + e.getMessage());
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
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update  customer by id: " + customerId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update customer with id: " + customerId + e.getMessage());
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
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update address of customer with id: " + customerId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update customer address with id: " + customerId + e.getMessage());
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
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update email of customer with id: " + customerId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update customer email with id: " + customerId + e.getMessage());
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
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to update phone number of customer with id: " + customerId);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to update customer phone number with id: " + customerId + e.getMessage());
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
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID)) {
            ps.setString(1, customerId.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String customerIdStr = rs.getString("customer_id");
                String accountIdStr = rs.getString("account_id");
                if (customerIdStr != null && !customerIdStr.isEmpty()) {
                    UUID customerUuid = UUID.fromString(customerIdStr);
                    customerMap.putIfAbsent(customerUuid, new Customer(rs));
                    if (accountIdStr != null && !accountIdStr.isEmpty() && !accountMap.containsKey(UUID.fromString(accountIdStr))) {
                        UUID accountUuid = UUID.fromString(accountIdStr);
                        Account accountModel = new Account(rs);
                        customerMap.get(customerUuid).addAccount(accountModel);
                        accountMap.putIfAbsent(accountUuid, accountModel);
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
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(customerMap.values());
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
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Transaction> getTransactionsForAccount(Connection connection, UUID accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement
                (AccountSqlQueries.GET_ACCOUNT_TRANSACTIONS_BY_ID)) {
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
        try (PreparedStatement ps = connection.prepareStatement(CustomerSqlQueries.GET_CUSTOMER_ACCOUNTS_BY_ID)) {
            ps.setString(1, customerId.toString());
            ResultSet rs = ps.executeQuery();
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
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<Customer> getCustomersAccountsCards() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Map<UUID, Customer> customerMap = new HashMap<>();
        Map<UUID, Account> accountMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.GET_CUSTOMERS_ACCOUNTS)) {
            ResultSet rs = ps.executeQuery();
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
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return new ArrayList<>(customerMap.values());
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
        try (PreparedStatement ps = connection.prepareStatement
                (CustomerSqlQueries.GET_CUSTOMER_FIRST_NAME_BY_ID)) {
            ps.setString(1, customerId.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve customer first name by id: " + customerId + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return null;
    }
}