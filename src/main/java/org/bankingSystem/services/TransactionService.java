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
        } catch (SQLException e) {
            throw new SQLException("No transactions found" + e.getMessage());
        } finally {
            //TODO even ResultSet and PrepareStatement will result on resource leaks if not closed
            //TODO rs.close() and st.close() (this is closed automatically because of try-with-resources);
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
        } catch (Exception e) {
            throw new SQLException("No transaction with id : " + transactionId + "found" + e.getMessage());
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
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to create a transaction");
            }
        } catch (SQLException e) {
            throw new SQLException("Error while creating a transaction" + e.getMessage());
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return transactions;
    }
}
