package org.bankingSystem.queries;

public class TransferSqlQueries {
    public static final String GET_TRANSFERS =
            "select id as transfer_id, " +
                    "sender_account_id, " +
                    "receiver_account_id, " +
                    "transfer_amount, " +
                    "transfer_date, " +
                    "from transfers " +
                    "order by transfer_date desc";

    public static final String GET_TRANSFER_BY_ID =
            "select id as transfer_id, " +
                    "sender_account_id, " +
                    "receiver_account_id, " +
                    "transfer_amount, " +
                    "transfer_date, " +
                    "from transfers " +
                    "where id = ?" +
                    "order by transfer_date desc";

    public static final String GET_CURRENT_MONTHS_TRANSFERS =
            "select id as transfer_id, " +
                    "sender_account_id, " +
                    "receiver_account_id, " +
                    "transfer_amount, " +
                    "transfer_date " +
                    "from transfers " +
                    "where MONTH(transfer_date) = MONTH(CURDATE()) " +
                    "and YEAR(transfer_date) = YEAR(CURDATE()) " +
                    "order by transfer_date desc";

    public static final String GET_TRANSFERS_FILTERED_BY_MONTH =
            "select id as transfer_id, " +
                    "sender_account_id, " +
                    "receiver_account_id, " +
                    "transfer_amount, " +
                    "transfer_date " +
                    "from transfers " +
                    "where MONTH(transfer_date) = ? " +
                    "and YEAR(transfer_date) = ? " +
                    "order by transfer_date desc";

    public static final String GET_TRANSFER_BY_SENDER_ID =
            "select id as transfer_id, " +
                    "sender_account_id, " +
                    "receiver_account_id, " +
                    "transfer_amount, " +
                    "transfer_date, " +
                    "from transfers " +
                    "where sender_account_id = ?" +
                    "order by transfer_date desc";

    public static final String GET_TRANSFER_BY_RECEIVER_ID =
            "select id as transfer_id, " +
                    "sender_account_id, " +
                    "receiver_account_id, " +
                    "transfer_amount, " +
                    "transfer_date, " +
                    "from transfers " +
                    "where receiver_account_id = ?" +
                    "order by transfer_date desc";

    public static final String CREATE_TRANSFER =
            "insert into transfers " +
                    "(id, " +
                    "sender_account_id, " +
                    "receiver_account_id, " +
                    "transfer_amount, " +
                    "transfer_date) " +
                    "VALUES (?, ?, ?, ?, ?)";

    public static final String UPDATE_SENDER_BALANCE =
            "UPDATE accounts " +
                "SET account_current_balance = account_current_balance - ? " +
                "WHERE id = ? ";

    public static final String UPDATE_RECEIVER_BALANCE =
            "UPDATE accounts " +
                    "SET account_current_balance = account_current_balance + ? " +
                    "WHERE id = ? ";

    public static final String CHECK_BALANCE =
            "SELECT account_current_balance " +
            "FROM accounts " +
            "WHERE id = ? ";

}



