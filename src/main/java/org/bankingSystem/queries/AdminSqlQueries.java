package org.bankingSystem.queries;

public class AdminSqlQueries {
    public static final String GET_ADMINS =
            "select * from admins" +
                    " where username = ? " +
                    "and password = ?";
}
