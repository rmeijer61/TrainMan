package com.rmeijer.trainman;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class CustomerGenerator {

    // For creating a static CustomerGenerator;
    private static CustomerGenerator sCustomerGenerator;

    private Customer mCustomer;
    private CustomerStore sCustomerStore;
    private Calendar calDate;

    public CustomerGenerator(Context context) {
    }

    public static CustomerGenerator get(Context context) {
        if (sCustomerGenerator == null) {
            sCustomerGenerator = new CustomerGenerator(context);
        }
        return sCustomerGenerator;
    }


    public void GenerateCustomers(Context context) {

        final String TAG = "CustomerGenerator: ";
        CustomerStore sCustomerStore = CustomerStore.get(context);

        // 1
        mCustomer = new Customer();
        mCustomer.setName("Lisa Simpson");
        mCustomer.setGender("Female");
        Calendar calDate = Calendar.getInstance();
        calDate.set(1979, 2, 18);
        mCustomer.setBirthDate(calDate.getTime());
        mCustomer.setPhone1("5551234321");
        mCustomer.setPhone2("5559877273");
        mCustomer.setEmail1("lsimpson@gmail.com");
        mCustomer.setEmail1("lsimpson@yahoo.com");
        mCustomer.setAddress1("742 Evergreen Terrace");
        mCustomer.setCity("Springfield");
        mCustomer.setState("CA");
        mCustomer.setZip("90210");
        mCustomer.setNote("Cogito ergo sum. Lorem ipsum dolor sit ris. Te nec mazim vivendo, duo habeo.");
        sCustomerStore.addCustomer(mCustomer);
        Log.v(TAG,"GenerateCustomers: " + mCustomer.getName());


        //2
        mCustomer = new Customer();
        mCustomer.setName("Patricia E. Gottlieb");
        mCustomer.setGender("Female");
        calDate = Calendar.getInstance();
        calDate.set(1954, 5, 12);
        mCustomer.setBirthDate(calDate.getTime());
        mCustomer.setPhone1("4027172994");
        mCustomer.setPhone2("5512347273");
        mCustomer.setEmail1("pgottlieb@gmail.com");
        mCustomer.setEmail1("pgottlieb@yahoo.com");
        mCustomer.setAddress1("452 Snowbird Lane");
        mCustomer.setCity("Omaha");
        mCustomer.setState("NE");
        mCustomer.setZip("68114");
        mCustomer.setNote("Lorem ipsum dolor sit amet, cum labore euismod ei, et maiorum suavitate.");
        sCustomerStore.addCustomer(mCustomer);
        Log.v(TAG,"GenerateCustomers: " + mCustomer.getName());

        //3
        mCustomer = new Customer();
        mCustomer.setName("Tina C. Gutierrez");
        mCustomer.setGender("Female");
        calDate = Calendar.getInstance();
        calDate.set(1983, 1, 22);
        mCustomer.setBirthDate(calDate.getTime());
        mCustomer.setPhone1("6549382194");
        mCustomer.setPhone2("6508787676");
        mCustomer.setEmail1("tgutierrez@gmail.com");
        mCustomer.setEmail1("tgutierre@yahoo.com");
        mCustomer.setAddress1("3572 Elm Drive");
        mCustomer.setCity("New York");
        mCustomer.setState("NY");
        mCustomer.setZip("10011");
        mCustomer.setNote("Lorem ipsum dolor sit amet, has alii aliquam an. Mei minim aliquip et.");
        sCustomerStore.addCustomer(mCustomer);
        Log.v(TAG,"GenerateCustomers: " + mCustomer.getName());

        //4
        mCustomer = new Customer();
        mCustomer.setName("Michael C. Williams");
        mCustomer.setGender("Male");
        calDate = Calendar.getInstance();
        calDate.set(1955, 7, 27);
        mCustomer.setBirthDate(calDate.getTime());
        mCustomer.setPhone1("7779382194");
        mCustomer.setPhone2("7778787676");
        mCustomer.setEmail1("mwilliams@gmail.com");
        mCustomer.setEmail1("mwilliams@yahoo.com");
        mCustomer.setAddress1("3176 Single Street");
        mCustomer.setCity("Lynn");
        mCustomer.setState("MA");
        mCustomer.setZip("01901");
        mCustomer.setNote("Lorem ipsum dolor sit amet, nullam docendi lobortis ex nec, velit virtute ei has, consul melius.");
        sCustomerStore.addCustomer(mCustomer);
        Log.v(TAG,"GenerateCustomers: " + mCustomer.getName());

        //5
        mCustomer = new Customer();
        mCustomer.setName("Joseph D. Womble");
        mCustomer.setGender("Male");
        calDate = Calendar.getInstance();
        calDate.set(1988, 12, 5);
        mCustomer.setBirthDate(calDate.getTime());
        mCustomer.setPhone1("21279382194");
        mCustomer.setPhone2("2128787676");
        mCustomer.setEmail1("jwomble@gmail.com");
        mCustomer.setEmail1("jwomble@yahoo.com");
        mCustomer.setAddress1("3176 Single Street");
        mCustomer.setCity("Grand Rapids");
        mCustomer.setState("MI");
        mCustomer.setZip("49503");
        mCustomer.setNote("Lorem ipsum dolor sit amet, sea.");
        sCustomerStore.addCustomer(mCustomer);
        Log.v(TAG,"GenerateCustomers: " + mCustomer.getName());

        //6
        mCustomer = new Customer();
        mCustomer.setName("Beth C. Haggerty");
        mCustomer.setGender("Female");
        calDate = Calendar.getInstance();
        calDate.set(1967, 3, 30);
        mCustomer.setBirthDate(calDate.getTime());
        mCustomer.setPhone1("62179382194");
        mCustomer.setPhone2("62128787676");
        mCustomer.setEmail1("bhaggerty@gmail.com");
        mCustomer.setEmail1("bhaggerty@yahoo.com");
        mCustomer.setAddress1("3176 Single Street");
        mCustomer.setCity("Knoxville");
        mCustomer.setState("TN");
        mCustomer.setZip("49867");
        mCustomer.setNote("Lorem ipsum dolor sit amet, ex ius modus laboramus, clita patrioque.");
        sCustomerStore.addCustomer(mCustomer);
        Log.v(TAG,"GenerateCustomers: " + mCustomer.getName());

    }


}
