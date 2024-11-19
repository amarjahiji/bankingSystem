package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
    private String adminFirstName;
    private String adminLastName;
    private String adminEmail;
    private String adminPassword;
    private String adminSecurityClearance;

    public Admin(ResultSet rs) throws SQLException {
        this.adminFirstName = rs.getString("admin_first_name");
        this.adminLastName = rs.getString("admin_last_name");
        this.adminEmail = rs.getString("admin_email");
        this.adminPassword = rs.getString("admin_password");
        this.adminSecurityClearance = rs.getString("admin_security_clearance");
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

    public String getAdminEmail() {
        return adminEmail;
    }
    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
    public String getAdminSecurityClearance() {
        return adminSecurityClearance;
    }
    public void setAdminSecurityClearance(String adminSecurityClearance) {
        this.adminSecurityClearance = adminSecurityClearance;
    }


}
