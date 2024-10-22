package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int customerId;
    private String customerFirstName;
    private String customerLastName;
    private String customerDateOfBirth;
    private String customerEmail;
    private String customerPhoneNumber;
    private String customerAddress;
    private List<Account> account = new ArrayList<>();

    public Customer() {
    }

    public Customer(ResultSet resultSet) throws SQLException {
        this.customerId = resultSet.getInt("CUSTOMER_ID");
        this.customerFirstName = resultSet.getString("CUSTOMER_FIRST_NAME");
        this.customerLastName = resultSet.getString("CUSTOMER_LAST_NAME");
        this.customerDateOfBirth = resultSet.getString("CUSTOMER_DATE_OF_BIRTH");
        this.customerEmail = resultSet.getString("CUSTOMER_EMAIL");
        this.customerPhoneNumber = resultSet.getString("CUSTOMER_PHONE_NUMBER");
        this.customerAddress = resultSet.getString("CUSTOMER_ADDRESS");
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
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
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }
}