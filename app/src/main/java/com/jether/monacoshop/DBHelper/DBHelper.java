package com.jether.monacoshop.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String CREATE_TABLE="CREATE TABLE"+DBContact.TABLE_NAME+
            "("
            +DBContact.ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"
            +DBContact.CATEGORY+ "TEXT,"
            +DBContact.PRODUCT+ "TEXT,"
            + DBContact.DESCC+ "TEXT,"
            +DBContact.PRICE+ "TEXT,"
            +DBContact.QUANT+ "TEXT,"
            +DBContact.IMAGE+ "TEXT,"
            +DBContact.DATE+ "TEXT,"
            +DBContact.SYNC_STATUS+ "INTEGER,"
          +")";

    private static final String DROP_TABLE="DROP TABLE IF EXISTS"+DBContact.TABLE_NAME;

    public DBHelper(Context context){
         super(context,DBContact.DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void saveToLocalDatabase(String category,String product,String des,String price,String quant,String image,String date,int sta,SQLiteDatabase database ){
        ContentValues contentValues= new ContentValues();
        contentValues.put(DBContact.CATEGORY,category);
        contentValues.put(DBContact.PRODUCT,product);
        contentValues.put(DBContact.DESCC,des);
        contentValues.put(DBContact.PRICE,price);
        contentValues.put(DBContact.QUANT,quant);
        contentValues.put(DBContact.IMAGE,image);
        contentValues.put(DBContact.DATE,date);
        contentValues.put(DBContact.SYNC_STATUS,sta);
       database.insert(DBContact.TABLE_NAME,null,contentValues);

    }


    public Cursor readFromLocalDatabase(SQLiteDatabase database){
        String [] projection={DBContact.CATEGORY,DBContact.PRODUCT,DBContact.DESCC,DBContact.PRICE,DBContact.QUANT,DBContact.IMAGE,DBContact.DATE,DBContact.SYNC_STATUS};

        return (database.query(DBContact.TABLE_NAME,projection,null,null,null,null,null,null));
    }


//    public void updateToLocalDatabase(String category,String product,String des,String price,String quant,String image,String date,int sta,SQLiteDatabase database ){
//        ContentValues contentValues= new ContentValues();
//        contentValues.put(DBContact.SYNC_STATUS,sta);
//        String se=DBContact.CATEGORY+"LIKE?";
//        String se1=DBContact.PRODUCT+"LIKE?";
//        String se2=DBContact.DESCC+"LIKE?";
//        String se3=DBContact.PRICE+"LIKE?";
//        String se4=DBContact.QUANT+"LIKE?";
//        String se5=DBContact.IMAGE+"LIKE?";
//        String se6=DBContact.DATE+"LIKE?";
//
//        String [] selection_args=(category);
//
//
//
//    }

}
