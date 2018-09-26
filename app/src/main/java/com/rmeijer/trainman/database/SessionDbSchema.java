package com.rmeijer.trainman.database;

// 14 - Define schema - Create class
public class SessionDbSchema {
    // 14.1 - Defining SessionTable
    public static final class SessionTable {
        // Table
        public static final String TABLE_NAME = "sessions";

        // 14.2 - Defining your table columns
        // Columns
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DATE = "date";
            public static final String CUSTOMERID = "customerId";
            public static final String SERVICE = "service";
            public static final String SESSIONDATE = "sessionDate";
            public static final String DESCR = "descr";
            public static final String COMPLETED = "completed";
            public static final String PAID = "paid";
            public static final String SIGN = "sign";
        }
        // end 14.2
    }
    // end 14.1
}