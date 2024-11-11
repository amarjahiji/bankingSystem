package org.bankingSystem.queries;

public class CustomerSqlQueries {
    public static final String GET_CUSTOMERS =
            "select id as customer_id, " +
                    "customer_first_name, " +
                    "customer_last_name, " +
                    "customer_date_of_birth, " +
                    "customer_email, " +
                    "customer_phone_number, " +
                    "customer_address " +
                    "from customers " +
                    "order by customer_first_name";

    public static final String GET_TOTAL_NUMBER_OF_CUSTOMERS =
            "select count(*) as total from customers";

    public static final String GET_OLD_CUSTOMERS =
            "select id as customer_id, " +
                    "customer_first_name, " +
                    "customer_last_name, " +
                    "customer_date_of_birth, " +
                    "customer_email, " +
                    "customer_phone_number, " +
                    "customer_address " +
                    "from customers " +
                    "where customer_date_of_birth < '1965-01-01' " +
                    "order by customer_first_name";

    public static final String GET_CERTAIN_AGE_CUSTOMERS =
            "select id as customer_id, " +
                    "customer_first_name, " +
                    "customer_last_name, " +
                    "customer_date_of_birth, " +
                    "customer_email, " +
                    "customer_phone_number, " +
                    "customer_address " +
                    "from customers " +
                    "where customer_date_of_birth between ? and ? " +
                    "order by customer_first_name";



    public static final String GET_TOTAL_NUMBER_OF_OLD_CUSTOMERS =
            "select count(*) as total from customers " +
                    "where customer_date_of_birth < '1965-01-01'";

    public static final String GET_TOTAL_NUMBER_OF_CERTAIN_AGE_CUSTOMERS =
            "select count(*) as total from customers " +
                    "where customer_date_of_birth between ? and ?";

    public static final String GET_YOUNG_CUSTOMERS =
            "select id as customer_id, " +
                    "customer_first_name, " +
                    "customer_last_name, " +
                    "customer_date_of_birth, " +
                    "customer_email, " +
                    "customer_phone_number, " +
                    "customer_address " +
                    "from customers " +
                    "where customer_date_of_birth > '2002-01-01' " +
                    "order by customer_first_name";

    public static final String GET_TOTAL_NUMBER_OF_YOUNG_CUSTOMERS =
            "select count(*) as total from customers " +
                    "where customer_date_of_birth > '2002-01-01'";

    public static final String GET_CUSTOMER_BY_ID =
            "select id as customer_id, " +
                    "customer_first_name, " +
                    "customer_last_name, " +
                    "customer_date_of_birth, " +
                    "customer_email, " +
                    "customer_phone_number, " +
                    "customer_address " +
                    "from customers " +
                    "where id = ?";

    public static final String GET_CUSTOMER_FIRST_NAME_BY_ID =
            "select customer_first_name from customers " +
                    "where id = ?";

    public static final String GET_CUSTOMERS_ACCOUNTS =
            "select c.id as customer_id, " +
                    "c.customer_first_name, " +
                    "c.customer_last_name, " +
                    "c.customer_date_of_birth, " +
                    "c.customer_email, " +
                    "c.customer_phone_number, " +
                    "c.customer_address, " +
                    "a.id as account_id, " +
                    "a.account_number, " +
                    "a.account_type, " +
                    "a.account_current_balance, " +
                    "a.account_date_opened, " +
                    "a.account_date_closed, " +
                    "a.account_status, " +
                    "a.customer_id " +
                    "from customers c " +
                    "left join accounts a " +
                    "on c.id = a.customer_id";

    public static final String GET_CUSTOMER_ACCOUNTS_BY_ID =
            "select c.id as customer_id, " +
                    "c.customer_first_name, " +
                    "c.customer_last_name, " +
                    "c.customer_date_of_birth, " +
                    "c.customer_email, " +
                    "c.customer_phone_number, " +
                    "c.customer_address, " +
                    "a.id as account_id, " +
                    "a.account_number, " +
                    "a.account_type, " +
                    "a.account_current_balance, " +
                    "a.account_date_opened, " +
                    "a.account_date_closed, " +
                    "a.account_status, " +
                    "a.customer_id " +
                    "from customers c " +
                    "left join accounts a on c.id = a.customer_id " +
                    "where c.id = ?";

    public static final String CREATE_CUSTOMER =
            "insert into customers " +
                    "(id, " +
                    "customer_first_name, " +
                    "customer_last_name," +
                    "customer_date_of_birth, " +
                    "customer_email, " +
                    "customer_phone_number, " +
                    "customer_address) " +
                    "values(?,?,?,?,?,?, ?)";

    public static final String UPDATE_CUSTOMER_ADDRESS_BY_ID =
            "update customers " +
                    "set customer_address = ? " +
                    "where id = ?";

    public static final String UPDATE_CUSTOMER_EMAIL_BY_ID =
            "update customers " +
                    "set customer_email = ? " +
                    "where id = ?";

    public static final String UPDATE_CUSTOMER_PHONE_NUMBER_BY_ID =
            "update customers " +
                    "set customer_phone_number = ? " +
                    "where id = ?";

    public static final String UPDATE_CUSTOMER_BY_ID =
            "update customers " +
                    "set customer_first_name = ?, " +
                    "customer_last_name = ?, " +
                    "customer_date_of_birth = ?, " +
                    "customer_email = ?, " +
                    "customer_phone_number = ?," +
                    "customer_address = ? " +
                    "where id = ? ";

    public static final String DELETE_CUSTOMER_BY_ID =
            "delete from customer " +
                    "where id = ?";

    public static final String DELETE_CUSTOMER_FROM_ACCOUNTS =
            "delete from accounts" +
                    "where customer_id = ?";
}
