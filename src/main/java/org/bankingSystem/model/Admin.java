package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
    private String adminFirstName;
    private String adminLastName;
    private String adminUsername;
    private String adminPassword;

    public Admin(ResultSet resultSet) throws SQLException {
        this.adminFirstName = resultSet.getString("admin_first_name");
        this.adminLastName = resultSet.getString("admin_last_name");
        this.adminUsername = resultSet.getString("username");
        this.adminPassword = resultSet.getString("password");
    }
    public String getAdminFirstName() {
        return adminFirstName;
    }
    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }
    public String getAdminLastName() {
        return adminLastName;
    }
    public void setAdminLastName(String adminLastName) {
        this.adminLastName = adminLastName;
    }
    public String getAdminUsername() {
        return adminUsername;
    }
    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }
    public String getAdminPassword() {
        return adminPassword;
    }
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

}
