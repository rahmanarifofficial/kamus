package com.rahmanarif.kamusinggris_indonesia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.rahmanarif.kamusinggris_indonesia.model.Kamus;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.KamusColumns.ARTI;
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.KamusColumns.KATA;
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.TABLE_NAME;

public class KamusDbHelper {
    private Context ctx;
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public KamusDbHelper(Context ctx) {
        this.ctx = ctx;
    }

    public KamusDbHelper open() throws SQLException {
        dbHelper = new DbHelper(ctx);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Kamus> getAllData() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null,
                null, _ID + " ASC", "30");
        cursor.moveToFirst();
        ArrayList<Kamus> listKata = new ArrayList<>();
        Kamus kamus;
        if (cursor.getCount() > 0) {
            do {
                kamus = new Kamus();
                kamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamus.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                kamus.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                listKata.add(kamus);
                cursor.moveToFirst();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return listKata;
    }

    public long insert(Kamus kamus){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KATA, kamus.getKata());
        initialValues.put(ARTI, kamus.getArti());

        return database.insert(TABLE_NAME, null, initialValues);
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(Kamus kamus) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + KATA + ", " + ARTI + ") VALUES (?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, kamus.getKata());
        statement.bindString(2, kamus.getArti());
        statement.execute();
        statement.clearBindings();
    }

}
