package org.bankingSystem.queries;

public class AdminSqlQueries {
    public static final String GET_ADMINS =
            "select * from admins " +
                    " where admin_email = ? ";

    public static final String CREATE_ADMIN =
            "insert into admins ( id, " +
                    "admin_first_name, " +
                    "admin_last_name, " +
                    "admin_email, " +
                    "admin_password, " +
                    "admin_security_clearance) " +
                    "values (?, ?, ?, ?, ?, ?)";
}
