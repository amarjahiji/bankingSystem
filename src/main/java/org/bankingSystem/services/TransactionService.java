package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Transaction;
import org.bankingSystem.model.Transfer;
import org.bankingSystem.queries.TransactionSqlQueries;
import org.bankingSystem.queries.TransferSqlQueries;

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

    public Transaction createTransaction(Transaction transaction) throws SQLException {
        Connection connection = null;
        PreparedStatement psCheckBalance = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdateBalance = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
            connection.setAutoCommit(false);

            psCheckBalance = connection.prepareStatement(TransactionSqlQueries.CHECK_BALANCE);
            psCheckBalance.setString(1, transaction.getAccountId().toString());
            rs = psCheckBalance.executeQuery();
            if (rs.next()) {
                double senderBalance = rs.getDouble("account_current_balance");
                if (senderBalance < transaction.getTransactionAmount() && transaction.getTransactionType().equalsIgnoreCase("Withdraw")) {
                    throw new SQLException("Insufficient funds for withdrawal");
                }
            } else {
                throw new SQLException("Account not found");
            }

            psInsert = connection.prepareStatement(TransactionSqlQueries.CREATE_TRANSACTION);
            UUID uuid = UUID.randomUUID();
            psInsert.setString(1, uuid.toString());
            psInsert.setString(2, transaction.getTransactionType());
            psInsert.setFloat(3, transaction.getTransactionAmount());
            psInsert.setString(4, transaction.getTransactionDate());
            psInsert.setString(5, transaction.getAccountId().toString());
            psInsert.setString(6, transaction.getTransactionDescription());
            psInsert.executeUpdate();

            psUpdateBalance = connection.prepareStatement(TransactionSqlQueries.UPDATE_BALANCE);
            psUpdateBalance.setDouble(1, transaction.getTransactionAmount());
            psUpdateBalance.setString(2, transaction.getAccountId().toString());
            psUpdateBalance.executeUpdate();


            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw new SQLException("Error while creating a transaction" + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(psCheckBalance, psInsert, psUpdateBalance);
        }
        return transaction;
    }
}
