package com.rahmanarif.kamusinggris_indonesia.db;

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
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.TABLE_ENG;
import static com.rahmanarif.kamusinggris_indonesia.db.DatabaseContract.TABLE_IND;

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

    public ArrayList<Kamus> getAllData(boolean isEnglishDict) {
        String table = isEnglishDict ? TABLE_ENG : TABLE_IND;
        Cursor cursor = database.rawQuery("SELECT * FROM " + table + " ORDER BY " + _ID + " ASC", null);
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
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return listKata;
    }

    public ArrayList<Kamus> getDataSearch(String query, boolean isEnglishDict) {
        String table = isEnglishDict ? TABLE_ENG : TABLE_IND;
        Cursor cursor = database.rawQuery("SELECT * FROM " + table + " WHERE " + KATA +
                " LIKE '" + query + "%' ORDER BY " + _ID + " ASC", null);
        cursor.moveToFirst();

        ArrayList<Kamus> listHasilKata = new ArrayList<>();
        Kamus kamus;
        if (cursor.getCount() > 0) {
            do {
                kamus = new Kamus();
                kamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamus.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                kamus.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                listHasilKata.add(kamus);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return listHasilKata;
    }

    public void insertTransaction(ArrayList<Kamus> kamuses, boolean isEnglish) {
        String table = isEnglish ? TABLE_ENG : TABLE_IND;
        String sql = "INSERT INTO " + table + " (" + KATA + ", " + ARTI + ") VALUES (?, ?)";
        database.beginTransaction();

        SQLiteStatement statement = database.compileStatement(sql);
        for (int i = 0; i < kamuses.size(); i++) {
            statement.bindString(1, kamuses.get(i).getKata());
            statement.bindString(2, kamuses.get(i).getArti());
            statement.execute();
            statement.clearBindings();
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

}
