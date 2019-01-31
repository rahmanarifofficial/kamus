package com.rahmanarif.kamusinggris_indonesia.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "table_kamus";

    public static final class KamusColumns implements BaseColumns {
        public static final String KATA = "KATA";
        public static final String ARTI = "ARTI";
    }
}
