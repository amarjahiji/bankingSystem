package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer {
    private UUID customerId;
    private String customerFirstName;
    private String customerLastName;
    private String customerDateOfBirth;
    private String customerEmail;
    private String customerPhoneNumber;
    private String customerAddress;
    private List<Account> accounts;


    public Customer(ResultSet rs) throws SQLException {
        this.customerId = UUID.fromString(rs.getString("customer_id"));
        this.customerFirstName = rs.getString("customer_first_name");
        this.customerLastName = rs.getString("customer_last_name");
        this.customerDateOfBirth = rs.getString("customer_date_of_birth");
        this.customerEmail = rs.getString("customer_email");
        this.customerPhoneNumber = rs.getString("customer_phone_number");
        this.customerAddress = rs.getString("customer_address");
        this.accounts = null;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerDateOfBirth() {
        return customerDateOfBirth;
    }

    public void setCustomerDateOfBirth(String customerDateOfBirth) {
        this.customerDateOfBirth = customerDateOfBirth;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public List<Account> getAccount() {
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        return accounts;
    }

    public void addAccount(Account account) {
        getAccount().add(account);
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}