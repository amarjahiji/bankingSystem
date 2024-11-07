package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Transaction;
import org.bankingSystem.queries.TransactionSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionService {
    public List<Transaction> getTransactions() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        List<Transaction> transactions = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery
                    (TransactionSqlQueries.GET_TRANSACTIONS);
            while (rs.next()) {
                Transaction newTransaction = new Transaction(rs);
                transactions.add(newTransaction);
            }
            System.out.println("Transactions retrieved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get transactions");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return transactions;

    }

    public Transaction getTransactionById(UUID transactionId) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (TransactionSqlQueries.GET_TRANSACTION_BY_ID)) {
            ps.setString(1, transactionId.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Transaction(rs);
            }
            System.out.println("Transaction " + transactionId + " retrieved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to get transaction with id " + transactionId);
            throw e;
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return null;
    }

    public Transaction createTransaction(Transaction transactions) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        try (PreparedStatement ps = connection.prepareStatement
                (TransactionSqlQueries.CREATE_TRANSACTION)) {
            UUID uuid = UUID.randomUUID();
            ps.setString(1, uuid.toString());
            ps.setString(2, transactions.getTransactionType());
            ps.setFloat(3, transactions.getTransactionAmount());
            ps.setString(4, transactions.getTransactionDate());
            ps.setString(5, transactions.getAccountId().toString());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Insert successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create transaction");
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return transactions;
    }
}
