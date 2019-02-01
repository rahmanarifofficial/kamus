package com.rahmanarif.kamusinggris_indonesia.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_ENG = "table_eng_to_ind";
    public static String TABLE_IND = "table_ind_to_eng";

    public static final class KamusColumns implements BaseColumns {
        public static final String KATA = "KATA";
        public static final String ARTI = "ARTI";
    }
}
