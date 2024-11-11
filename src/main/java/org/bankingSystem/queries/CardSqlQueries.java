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

    public static final String GET_CARDS_CARD_TYPE =
            "select c.id as card_id, " +
                    "c.card_number, " +
                    "c.card_cvv, " +
                    "ct.card_type_name, " +
                    "c.card_expiry_date, " +
                    "c.card_type_id, " +
                    "c.account_id, " +
                    "ct.id as card_type_id " +
                    "from cards c " +
                    "left join card_types ct " +
                    "on c.card_type_id = ct.id";

    public static final String GET_CARD_CARD_TYPE_BY_ID =
            "select c.id as card_id, " +
                    "c.card_number, " +
                    "c.card_cvv, " +
                    "ct.card_type_name, " +
                    "c.card_expiry_date, " +
                    "c.card_type_id, " +
                    "c.account_id, " +
                    "ct.id as card_type_id " +
                    "from cards c " +
                    "left join card_types ct " +
                    "on c.card_type_id = ct.id " +
                    "where c.id = ?";

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
