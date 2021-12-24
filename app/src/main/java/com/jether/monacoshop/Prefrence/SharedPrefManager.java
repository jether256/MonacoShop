package com.jether.monacoshop.Prefrence;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SharedPrefManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private Context mCtx;
    int PRIVATE_MODE = 0;

    private static final String SHARED_PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = " NAME ";
    public static final String EMAIL = "EMAIL";
    public static final String MOBILE = "MOBILE";
    public static final String PASSWORD = "PASSWORD";
    public static final String LOCATION = "LOCATION";
    public static final String ID = "ID";


    public static final String id = "id";
    public static final String Cost = "Cost";
    public static final String oderid = "oderid";





    public SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }



    public void createSession(String user_id,String user_namee,String user_emaill,String user_moo,String user_loo,String user_passs) {
        editor.putBoolean(LOGIN, true);
        editor.putString(ID, user_id);
       editor.putString(NAME, user_namee);
        editor.putString(EMAIL, user_emaill);
       editor.putString(MOBILE, user_moo);
       editor.putString(LOCATION,user_loo);
        editor.putString(PASSWORD, user_passs);
        editor.apply();
    }


    public void createPro(String pro_iddd,String pro_costtt,String pro_orderrr){
        editor.putBoolean(LOGIN, true);
        editor.putString(id,pro_iddd );
        editor.putString(Cost, pro_costtt);
        editor.putString(oderid, pro_orderrr);
        editor.apply();

    }

    public  boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }


    public HashMap<String, String> getUserDetail() {

    HashMap<String, String> user = new HashMap<>();
    user.put(ID,sharedPreferences.getString(ID,null));
    user.put(NAME,sharedPreferences.getString(NAME,null));
    user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
    user.put(MOBILE,sharedPreferences.getString(MOBILE,null));
    user.put(LOCATION,sharedPreferences.getString(LOCATION,null));
    user.put(PASSWORD,sharedPreferences.getString(PASSWORD,null));


    return user;
    }



    public HashMap<String,String>  getOrderDetails(){
        HashMap<String, String> jay = new HashMap<>();
        jay.put(id,sharedPreferences.getString(id,null));
        jay.put(Cost,sharedPreferences.getString(Cost,null));
        jay.put(oderid,sharedPreferences.getString(oderid,null));
        return jay;
    }


}
