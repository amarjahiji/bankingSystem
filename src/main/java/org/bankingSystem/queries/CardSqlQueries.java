package org.bankingSystem.queries;

public class CardSqlQueries {

    public static final String GET_CARDS =
            "SELECT * FROM CARD";

    public static final String GET_CARD_BY_ID =
            "SELECT * FROM CARD " +
                    "WHERE CARD_ID = ?";

    public static final String GET_CARDS_CARD_TYPE =
            "SELECT * FROM CARD c " +
                    "LEFT JOIN CARD_TYPE ct " +
                    "ON c.CARD_ID = ct.CARD_TYPE_ID";

    public static final String GET_CARD_CARD_TYPE_BY_ID =
            "SELECT * FROM CARD c " +
                    "LEFT JOIN CARD_TYPE ct " +
                    "ON c.CARD_ID = ct.CARD_TYPE_ID " +
                    "WHERE c.CARD_ID = ?";

    public static final String CREATE_CARD =
            "INSERT INTO CARD " +
                    "(CARD_NUMBER, " +
                    "CARD_EXPIRY_DATE, " +
                    "CARD_HOLDER_NAME, " +
                    "CARD_CVV, " +
                    "CARD_TYPE_ID, " +
                    "ACCOUNT_ID) " +
                    "VALUES (?,?,?,?,?,?)";

    public static final String UPDATE_CARD_EXPIRY_DATE_BY_ID =
            "UPDATE CARD " +
                    "SET CARD_EXPIRY_DATE = ? " +
                    "WHERE ACCOUNT_ID = ?";

    public static final String UPDATE_CARD_BY_ID =
            "UPDATE CARD " +
                    "SET CARD_NUMBER = ?, " +
                    "CARD_EXPIRY_DATE = ?, " +
                    "CARD_HOLDER_NAME= ?, " +
                    "CARD_CVV = ?, " +
                    "CARD_TYPE_ID = ?, " +
                    "ACCOUNT_ID = ? " +
                    "WHERE ACCOUNT_ID = ?";

    public static final String DELETE_CARD_BY_ID =
            "DELETE FROM CARD " +
                    "WHERE CARD_ID = ?";
}
