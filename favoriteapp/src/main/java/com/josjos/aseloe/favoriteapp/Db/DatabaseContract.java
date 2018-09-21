package com.josjos.aseloe.favoriteapp.Db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContract {


    // Authority yang digunakan
    public static final String AUTHORITY = "com.josjos.aseloe.api19";
    public static final String SCHEME = "content";

    //
    private DatabaseContract(){}


    /*
    Penggunaan Base Columns akan memudahkan dalam penggunaan suatu table
    Untuk id yang autoincrement sudah default ada di dalam kelas BaseColumns dengan nama field _ID
     */

    public static final class NoteColumns implements BaseColumns {

        public final static String TITLE = "title";
        public final  static String OVERVIEW = "overview";
        public final  static String POSTER_PATH = "poster_path";
        public final  static String VOTE_AVERAGE = "vote_average";
        public final  static String RELEASE_DATE = "release_date";

        // Movie table name
        public static String TABLE_FAVORITE = "favorite";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();
    }

    /*
    Digunakan untuk mempermudah akses data di dalam cursor dengan parameter nama column
    */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
