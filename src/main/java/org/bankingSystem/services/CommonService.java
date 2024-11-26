package org.bankingSystem.services;

import com.google.gson.Gson;

import java.sql.*;

public class CommonService {

    protected void closeStatements(Statement... statements) throws SQLException {
        for (Statement statement : statements) {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        }
    }

    protected void closePreparedStatement(PreparedStatement... preparedStatements) throws SQLException {
        for (PreparedStatement preparedStatement : preparedStatements) {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        }
    }

    protected void closeResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null && !resultSet.isClosed()) {
            resultSet.close();
        }
    }

    protected void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static final Gson GSON = new Gson();

}
