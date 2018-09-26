package com.rmeijer.trainman.database;

public class PaymentDbSchema {
    public static final class PaymentTable {
        public static final String TABLE_NAME = "payment";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DATE = "date";
            public static final String CUSTOMERID = "customerid";
            public static final String SESSIONID = "SessionId";
            public static final String PAYMETHOD = "payMethod";
            public static final String CARDNUMBER = "cardNumber";
            public static final String EXPIREDATE = "expireDate";
            public static final String SECURITYCODE = "securityCode";
            public static final String AMOUNT = "amount";
            public static final String PAYDATE = "payDate";
            public static final String NAME = "name";
            public static final String ADDRESS1 = "address1";
            public static final String ADDRESS2 = "address2";
            public static final String CITY = "city";
            public static final String STATE = "state";
            public static final String ZIP = "zip";
        }
    }
}