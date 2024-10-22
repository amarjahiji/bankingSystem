package org.bankingSystem.services;

import org.bankingSystem.model.Transaction;
import org.bankingSystem.queries.TransactionSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    public List<Transaction> getTransactions(Connection connection) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(TransactionSqlQueries.GET_TRANSACTIONS);
            while (resultSet.next()) {
                Transaction newTransaction = new Transaction(resultSet);
                transactions.add(newTransaction);
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Transaction getTransactionById(Connection connection, int transactionId) throws SQLException {
        String query = TransactionSqlQueries.GET_TRANSACTION_BY_ID;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, transactionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Transaction(resultSet);
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

    public static Transaction createTransaction(Connection connection, Transaction transactions) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(TransactionSqlQueries.CREATE_TRANSACTION)) {
            preparedStatement.setString(1, transactions.getTransactionType());
            preparedStatement.setFloat(2, transactions.getTransactionAmount());
            preparedStatement.setString(3, transactions.getTransactionDate());
            preparedStatement.setInt(4, transactions.getAccountId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Insert successful!");
                return transactions;
            } else {
                throw new SQLException("Insert failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
