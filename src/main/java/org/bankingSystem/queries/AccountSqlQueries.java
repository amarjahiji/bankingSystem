package org.bankingSystem.queries;

public class AccountSqlQueries {

    public static final String GET_ACCOUNTS =
            "SELECT * FROM ACCOUNT";

    public static final String GET_ACCOUNT_BY_ID =
            "SELECT * FROM ACCOUNT " +
                    "WHERE ACCOUNT_ID = ?";

    public static final String GET_ACCOUNT_NUMBER_BY_ID =
            "SELECT ACCOUNT_NUMBER FROM ACCOUNT " +
                    "WHERE ACCOUNT_ID = ?";

    public static final String GET_ACCOUNT_TRANSACTIONS_BY_ID =
            "SELECT * FROM TRANSACTION " +
                    "WHERE ACCOUNT_ID = ?";

    public static final String GET_ACCOUNT_CARDS_BY_ID =
            "SELECT * FROM CARD " +
                    "WHERE ACCOUNT_ID = ?";

    public static final String CREATE_ACCOUNT =
            "INSERT INTO ACCOUNT " +
                    "(ACCOUNT_NUMBER, " +
                    "ACCOUNT_TYPE, " +
                    "ACCOUNT_CURRENT_BALANCE, " +
                    "ACCOUNT_DATE_OPENED, " +
                    "ACCOUNT_DATE_CLOSED, " +
                    "ACCOUNT_STATUS, " +
                    "CUSTOMER_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_ACCOUNT_DATE_CLOSED_BY_ID =
            "UPDATE ACCOUNT " +
                    "SET ACCOUNT_DATE_CLOSED = ? " +
                    "WHERE ACCOUNT_ID = ?";

    public static final String UPDATE_ACCOUNT_STATUS_BY_ID =
            "UPDATE ACCOUNT " +
                    "SET ACCOUNT_STATUS = ? " +
                    "WHERE ACCOUNT_ID = ?";

    public static final String UPDATE_ACCOUNT_CURRENT_BALANCE_BY_ID =
            "UPDATE ACCOUNT" +
                    "SET ACCOUNT_CURRENT_BALANCE = ? " +
                    "WHERE ACCOUNT_ID = ?";

    public static final String UPDATE_ACCOUNT_BY_ID =
            "UPDATE ACCOUNT " +
                    "SET ACCOUNT_NUMBER = ?, " +
                    "ACCOUNT_TYPE = ?, " +
                    "ACCOUNT_CURRENT_BALANCE = ?, " +
                    "ACCOUNT_DATE_OPENED = ?, " +
                    "ACCOUNT_DATE_CLOSED = ?, " +
                    "ACCOUNT_STATUS = ?, " +
                    "CUSTOMER_ID = ? " +
                    "WHERE ACCOUNT_ID = ?";

    public static final String DELETE_ACCOUNT_BY_ID =
            "DELETE FROM ACCOUNT " +
                    "WHERE ACCOUNT_ID = ?";
}