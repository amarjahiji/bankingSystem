package org.bankingSystem.queries;

public class AccountSqlQueries {

    public static final String GET_ACCOUNTS =
            "select id as account_id, " +
                    "account_number, " +
                    "account_type, " +
                    "account_current_balance, " +
                    "account_date_opened, " +
                    "account_date_closed, " +
                    "account_status, " +
                    "customer_id " +
                    "from accounts " +
                    "order by account_date_opened asc";

    public static final String GET_ACCOUNT_BY_ID =
            "select id as account_id, " +
                    "account_number, " +
                    "account_type, " +
                    "account_current_balance, " +
                    "account_date_opened, " +
                    "account_date_closed, " +
                    "account_status, " +
                    "customer_id " +
                    "from accounts " +
                    "where id = ? " +
                    "order by account_date_opened asc";


    public static final String GET_ACCOUNT_NUMBER_BY_ID =
            "select account_number " +
                    "from accounts " +
                    "where id = ?";

    public static final String GET_ACCOUNT_TRANSACTIONS_BY_ID =
            "select id as transaction_id,  " +
                    "transaction_type, " +
                    "transaction_amount, " +
                    "transaction_date, " +
                    "account_id " +
                    "from transactions " +
                    "where account_id = ?";

    public static final String GET_ACCOUNT_CARDS_BY_ID =
            "select id as card_id, " +
                    "card_number, " +
                    "card_expiry_date, " +
                    "card_cvv, " +
                    "card_type_id, " +
                    "account_id " +
                    "from cards " +
                    "where account_id = ?";

    public static final String CREATE_ACCOUNT =
            "insert into accounts " +
                    "(id ," +
                    "account_number, " +
                    "account_type, " +
                    "account_current_balance, " +
                    "account_date_opened, " +
                    "account_date_closed, " +
                    "account_status, " +
                    "customer_id) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_ACCOUNT_DATE_CLOSED_BY_ID =
            "update accounts " +
                    "set account_date_closed = ? " +
                    "where id = ?";

    public static final String UPDATE_ACCOUNT_STATUS_BY_ID =
            "update accounts " +
                    "set account_status = ? " +
                    "where id = ?";

    public static final String UPDATE_ACCOUNT_CURRENT_BALANCE_BY_ID =
            "update accounts " +
                    "set account_current_balance = ? " +
                    "where id = ?";

    public static final String UPDATE_ACCOUNT_BY_ID =
            "update accounts " +
                    "set account_number = ?, " +
                    "account_type = ?, " +
                    "account_current_balance = ?, " +
                    "account_date_opened = ?, " +
                    "account_date_closed = ?, " +
                    "account_status = ?, " +
                    "customer_id = ? " +
                    "where id = ?";

    public static final String DELETE_ACCOUNT_ID_ON_CARDS =
            "delete from cards " +
                    "where account_id = ?; ";

    public static final String DELETE_ACCOUNT_ID_ON_TRANSACTIONS =
            "delete from transactions " +
                    "where account_id = ?; ";

    public static final String DELETE_ACCOUNT_BY_ID =
            "delete from accounts " +
                    "where id = ?; ";
}