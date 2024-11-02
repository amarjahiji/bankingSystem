package org.bankingSystem.queries;

public class CardTypeSqlQueries {
    public static final String GET_CARD_TYPES =
            "select id as card_type_id, " +
                    "card_type_name " +
                    "from card_types";
}
