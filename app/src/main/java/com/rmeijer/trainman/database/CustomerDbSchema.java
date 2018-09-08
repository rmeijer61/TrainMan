package com.rmeijer.trainman.database;

// 14 - Define schema - Create class
public class CustomerDbSchema {
    // 14.1 - Defining CustomerTable
    public static final class CustomerTable {
        // Table
        public static final String NAME = "customers";

        // 14.2 - Defining your table columns
        // Columns
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DATE = "date";
            public static final String NAME = "name";
            public static final String BIRTHDATE = "birthDate";

        }
        // end 14.2
    }
    // end 14.1
}