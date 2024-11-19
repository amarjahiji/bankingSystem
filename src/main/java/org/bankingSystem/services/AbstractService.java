package org.bankingSystem.services;

import java.sql.*;

public class AbstractService {

    protected void closeStatements(Statement... statements) throws SQLException {
        for (Statement statement : statements) {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        }
    }
    protected void closeResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null && !resultSet.isClosed()) {
            resultSet.close();
        }
    }
    protected void closePreparedStatement(PreparedStatement preparedStatements) throws SQLException {
        if (preparedStatements != null && !preparedStatements.isClosed()) {
            preparedStatements.close();
        }
    }
    protected void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
