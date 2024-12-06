package org.bankingSystem.queries;

public class TransactionSqlQueries {
    public static final String GET_TRANSACTIONS =
            "select id as transaction_id, " +
                    "transaction_type, " +
                    "transaction_amount, " +
                    "transaction_date, " +
                    "account_id " +
                    "from transactions " +
                    "order by transaction_date desc";

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
                    "(id, " +
                    "transaction_type, " +
                    "transaction_amount, " +
                    "transaction_date, " +
                    "account_id, " +
                    "transactionDescription) " +
                    "values (?,?,?,?,?, ?)";

    public static final String CHECK_BALANCE =
            "SELECT account_current_balance " +
                    "FROM accounts " +
                    "WHERE id = ? ";

    public static final String UPDATE_BALANCE =
            "UPDATE accounts " +
                    "SET account_current_balance = account_current_balance - ? " +
                    "WHERE id = ? ";
}
