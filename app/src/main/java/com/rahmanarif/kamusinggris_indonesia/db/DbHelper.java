package com.rahmanarif.kamusinggris_indonesia.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.KamusColumns.ARTI;
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.KamusColumns.KATA;
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.TABLE_NAME;

public class DbHelper extends SQLiteOpenHelper {
    public static String CREATE_TABLE_KAMUS = "create table " + TABLE_NAME +
            " (" + _ID + " integer primary key autoincrement, " +
            KATA + " text not null, " +
            ARTI + " text not null);";

    public DbHelper(Context ctx) {
        super(ctx, "Kamus.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_KAMUS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
