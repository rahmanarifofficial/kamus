package com.rahmanarif.kamusinggris_indonesia.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.KamusColumns.ARTI;
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.KamusColumns.KATA;
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.TABLE_ENG;
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.TABLE_IND;

public class DbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "kamus.db";

    public static String CREATE_TABLE_ENG = "create table " + TABLE_ENG +
            " (" + _ID + " integer primary key autoincrement, " +
            KATA + " text not null, " +
            ARTI + " text not null);";

    public static String CREATE_TABLE_IND = "create table " + TABLE_IND +
            " (" + _ID + " integer primary key autoincrement, " +
            KATA + " text not null, " +
            ARTI + " text not null);";

    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENG);
        db.execSQL(CREATE_TABLE_IND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IND);
        onCreate(db);
    }
}
