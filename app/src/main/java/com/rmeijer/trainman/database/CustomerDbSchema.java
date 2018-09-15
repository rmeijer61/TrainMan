package com.rmeijer.trainman.database;

// 14 - Define schema - Create class
public class CustomerDbSchema {
    // 14.1 - Defining CustomerTable
    public static final class CustomerTable {
        // Table
        public static final String TABLE_NAME = "customers";

        // 14.2 - Defining your table columns
        // Columns
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DATE = "date";
            public static final String NAME = "name";
            public static final String GENDER = "gender";
            public static final String BIRTHDATE = "birthDate";
            public static final String PHONE1 = "phone1";
            public static final String PHONE2 = "phone2";
            public static final String EMAIL1 = "email1";
            public static final String EMAIL2 = "email2";
            public static final String ADDRESS1 = "address1";
            public static final String ADDRESS2 = "address2";
            public static final String CITY = "city";
            public static final String STATE = "state";
            public static final String ZIP = "zip";
            public static final String NOTE = "note";
        }
        // end 14.2
    }
    // end 14.1
}