package org.bankingSystem.model;

import java.util.List;

public class CountCustomer {
    private Integer total;
    private List<Customer> customers;


    public CountCustomer(Integer total, List<Customer> customers) {
        this.total = total;
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
