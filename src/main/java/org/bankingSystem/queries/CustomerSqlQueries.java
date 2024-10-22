package org.bankingSystem.queries;

public class CustomerSqlQueries {
    public static final String GET_CUSTOMERS =
            "SELECT * FROM CUSTOMER";

    public static final String GET_CUSTOMER_BY_ID =
            "SELECT * FROM CUSTOMER " +
                    "WHERE CUSTOMER_ID = ?";

    public static final String GET_CUSTOMER_FIRST_NAME_BY_ID =
            "SELECT CUSTOMER_FIRST_NAME FROM CUSTOMER " +
                    "WHERE CUSTOMER_ID = ?";

    public static final String GET_CUSTOMERS_ACCOUNTS =
            "SELECT * FROM CUSTOMER c " +
                    "LEFT JOIN ACCOUNT a " +
                    "ON c.CUSTOMER_ID = a.CUSTOMER_ID";

    public static final String GET_CUSTOMER_ACCOUNTS_BY_ID =
            "SELECT * FROM CUSTOMER c " +
                    "LEFT JOIN ACCOUNT a " +
                    "ON c.CUSTOMER_ID = a.CUSTOMER_ID " +
                    "WHERE c.CUSTOMER_ID = ?";

    public static final String CREATE_CUSTOMER =
            "INSERT INTO CUSTOMER " +
                    "(CUSTOMER_FIRST_NAME, " +
                    "CUSTOMER_LAST_NAME," +
                    "CUSTOMER_DATE_OF_BIRTH, " +
                    "CUSTOMER_EMAIL, " +
                    "CUSTOMER_PHONE_NUMBER, " +
                    "CUSTOMER_ADDRESS) " +
                    "VALUES(?,?,?,?,?,?)";

    public static final String UPDATE_CUSTOMER_ADDRESS_BY_ID =
            "UPDATE CUSTOMER " +
                    "SET CUSTOMER_ADDRESS = ? " +
                    "WHERE CUSTOMER_ID = ?";

    public static final String UPDATE_CUSTOMER_EMAIL_BY_ID =
            "UPDATE CUSTOMER " +
                    "SET CUSTOMER_EMAIL = ? " +
                    "WHERE CUSTOMER_ID = ?";

    public static final String UPDATE_CUSTOMER_PHONE_NUMBER_BY_ID =
            "UPDATE CUSTOMER " +
                    "SET CUSTOMER_PHONE_NUMBER = ? " +
                    "WHERE CUSTOMER_ID = ?";

    public static final String UPDATE_CUSTOMER_BY_ID =
            "UPDATE CUSTOMER " +
                    "SET CUSTOMER_FIRST_NAME = ?, " +
                    "CUSTOMER_LAST_NAME = ?, " +
                    "CUSTOMER_DATE_OF_BIRTH = ?, " +
                    "CUSTOMER_EMAIL = ?, " +
                    "CUSTOMER_PHONE_NUMBER = ?," +
                    "CUSTOMER_ADDRESS = ? " +
                    "WHERE CUSTOMER_ID = ? ";

    public static final String DELETE_CUSTOMER_BY_ID =
            "DELETE FROM CUSTOMER " +
                    "WHERE CUSTOMER_ID = ?";
}


