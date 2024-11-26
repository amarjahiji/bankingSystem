package org.bankingSystem.queries;

public class CardSqlQueries {

    public static final String GET_CARDS =
            "select id as card_id, " +
                    "card_number, " +
                    "card_expiry_date, " +
                    "card_cvv, " +
                    "card_type_id, " +
                    "account_id " +
                    "from cards " +
                    "order by card_expiry_date asc";

    public static final String GET_CARD_BY_ID =
            "select id as card_id, " +
                    "card_number, " +
                    "card_expiry_date, " +
                    "card_cvv, " +
                    "card_type_id, " +
                    "account_id " +
                    "from cards " +
                    "where id = ?";

    public static final String CREATE_CARD =
            "insert into cards " +
                    "(id, " +
                    "card_number, " +
                    "card_expiry_date, " +
                    "card_cvv, " +
                    "card_type_id, " +
                    "account_id) " +
                    "values (?,?,?,?,?,?)";

    public static final String UPDATE_CARD_EXPIRY_DATE_BY_ID =
            "update cards " +
                    "set card_expiry_date = ? " +
                    "where id = ?";

    public static final String UPDATE_CARD_BY_ID =
            "update cards " +
                    "set card_number = ?, " +
                    "card_expiry_date = ?, " +
                    "card_cvv = ?, " +
                    "card_type_id = ?, " +
                    "account_id = ? " +
                    "where id = ?";

    public static final String DELETE_CARD_BY_ID =
            "delete from cards " +
                    "where id = ?";
}
