package org.bankingSystem.services;

import com.auth0.jwt.exceptions.JWTCreationException;
import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.JwtUtil;
import org.bankingSystem.model.Admin;
import org.bankingSystem.queries.AdminSqlQueries;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AdminService extends CommonService {
    public Admin login(String email, String password)throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
            preparedStatement = connection.prepareStatement(AdminSqlQueries.GET_ADMINS);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String storedPasswordHash = rs.getString("admin_password");
                if (BCrypt.checkpw(password, storedPasswordHash)) {
                    return new Admin(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to log in" + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(preparedStatement);
            closeResultSet(rs);
        }
        return null;
    }

    public Admin signup(Admin admin) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(AdminSqlQueries.CREATE_ADMIN);
            UUID uuid = UUID.randomUUID();
            String hashedPassword = BCrypt.hashpw(admin.getAdminPassword(), BCrypt.gensalt());

            ps.setString(1, uuid.toString());
            ps.setString(2, admin.getAdminFirstName());
            ps.setString(3, admin.getAdminLastName());
            ps.setString(4, admin.getAdminEmail());
            ps.setString(5, hashedPassword);
            ps.setString(6, admin.getAdminSecurityClearance());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                throw new SQLException("No rows affected trying to create a admin");
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to create admin: " + e.getMessage());
        } finally {
            closeConnection(connection);
            closeStatements(ps);
        }
        return admin;
    }

    public String generateJwtToken(Admin admin) throws JWTCreationException {
        return JwtUtil.generateToken(admin.getAdminEmail());
    }
}
