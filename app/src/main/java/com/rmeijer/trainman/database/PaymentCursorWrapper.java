package com.rmeijer.trainman.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.rmeijer.trainman.Payment;

import java.util.Date;
import java.util.UUID;

public class PaymentCursorWrapper extends CursorWrapper {
    public PaymentCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Payment getPaymentRow() {
        String uuidString = getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.UUID));
        Date date = new Date(getLong(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.DATE)));
        String customerIdString = getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.CUSTOMERID));
        String sessionIdString = getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.SESSIONID));
        String payMethod =  getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.PAYMETHOD));
        String cardNumber = getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.CARDNUMBER));
        Date expireDate = new Date(getLong(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.EXPIREDATE)));
        String securityCode = getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.SECURITYCODE));
        double amount = getDouble(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.AMOUNT));
        Date payDate = new Date(getLong(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.PAYDATE)));
        String name = getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.NAME));
        String address1 = getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.ADDRESS1));
        String address2 = getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.ADDRESS2));
        String city = getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.CITY));
        String state = getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.STATE));
        String zip = getString(getColumnIndex(PaymentDbSchema.PaymentTable.Cols.ZIP));
        
        Payment payment = new Payment(UUID.fromString(uuidString));
        payment.setDate(date);
        payment.setCustomerId(UUID.fromString(customerIdString));
        payment.setSessionId(UUID.fromString(sessionIdString));
        payment.setPayMethod(payMethod);
        payment.setCardNumber(cardNumber);
        payment.setExpireDate(expireDate);
        payment.setSecurityCode(securityCode);
        payment.setAmount(amount);
        payment.setPayDate(payDate);
        payment.setName(name);
        payment.setAddress1(address1);
        payment.setAddress2(address2);
        payment.setCity(city);
        payment.setState(state);
        payment.setZip(zip);
        
        return payment;
    }

}