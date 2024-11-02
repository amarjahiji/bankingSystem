package org.bankingSystem.queries;

public class TransactionSqlQueries {
    public static final String GET_TRANSACTIONS =
            "select id as transaction_id, " +
                    "transaction_type, " +
                    "transaction_amount, " +
                    "transaction_date, " +
                    "account_id" +
                    " from transactions";

    public static final String GET_TRANSACTION_BY_ID =
            "select id as transaction_id, " +
                    "transaction_type, " +
                    "transaction_amount, " +
                    "transaction_date, " +
                    "account_id " +
                    "from transactions " +
                    "where id = ?";

    public static final String CREATE_TRANSACTION =
            "insert into transactions " +
                    "(transaction_type, " +
                    "transaction_amount, " +
                    "transaction_date, " +
                    "account_id) " +
                    "values (?,?,?,?)";
}
