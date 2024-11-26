package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Transaction;
import org.bankingSystem.queries.TransactionSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionService extends CommonService {
    public List<Transaction> getTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(TransactionSqlQueries.GET_TRANSACTIONS);
            while (rs.next()) {
                Transaction newTransaction = new Transaction(rs);
                transactions.add(newTransaction);
            }
        } catch (SQLException e) {
            throw new SQLException("No transactions found" + e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closeStatements(st);
        }
        return transactions;

    }

    public Transaction getTransactionById(UUID transactionId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(TransactionSqlQueries.GET_TRANSACTION_BY_ID);
            ps.setString(1, transactionId.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Transaction(rs);
            }
        } catch (Exception e) {
            throw new SQLException("No transaction with id : " + transactionId + "found" + e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closePreparedStatement(ps);
        }
        return null;
    }

    public Transaction createTransaction(Transaction transactions) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(TransactionSqlQueries.CREATE_TRANSACTION);
            UUID uuid = UUID.randomUUID();
            ps.setString(1, uuid.toString());
            ps.setString(2, transactions.getTransactionType());
            ps.setFloat(3, transactions.getTransactionAmount());
            ps.setString(4, transactions.getTransactionDate());
            ps.setString(5, transactions.getAccountId().toString());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to create a transaction");
            }
        } catch (SQLException e) {
            throw new SQLException("Error while creating a transaction" + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
        }
        return transactions;
    }
}
