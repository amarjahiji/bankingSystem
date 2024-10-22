package org.bankingSystem.queries;

public class TransactionSqlQueries {
    public static final String GET_TRANSACTIONS =
            "SELECT * FROM TRANSACTION";

    public static final String GET_TRANSACTION_BY_ID =
            "SELECT * FROM TRANSACTION " +
                    "WHERE TRANSACTION_ID = ?";

    public static final String CREATE_TRANSACTION =
            "INSERT INTO TRANSACTION " +
                    "(TRANSACTION_TYPE, " +
                    "TRANSACTION_AMOUNT, " +
                    "TRANSACTION_DATE, " +
                    "ACCOUNT_ID) " +
                    "VALUES (?,?,?,?)";
}
