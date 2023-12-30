package com.SEApp.app.model.persist.schemas;

public class UserSchema {
    public static final String TABLE = "users";

    public static final String ID = "user_id";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";

    public static final String PLAN_ID = "plan_id";

    public static final String PAYMENT_TYPE_ID = "payment_type_id";


    public static final String[] COLUMNS = {ID, EMAIL, USERNAME, PASSWORD, ROLE, PLAN_ID, PAYMENT_TYPE_ID};

}
