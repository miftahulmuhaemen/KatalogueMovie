package com.josjos.aseloe.api19.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import static android.provider.BaseColumns._ID;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.TABLE_FAVORITE;

public class FIlmHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public FIlmHelper(Context context) {
        this.context = context;
    }

    public FIlmHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }
    /**
     * Ambil data dari note berdasarakan parameter id
     * Gunakan method ini untuk ambil data di dalam provider
     *
     * @param id id note yang dicari
     * @return cursor hasil query
     */

    public Cursor queryByIdProvider(String id) {


        return database.query(TABLE_FAVORITE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    /**
     * Ambil data dari semua note yang ada di dalam database
     * Gunakan method ini untuk ambil data di dalam provider
     *
     * @return cursor hasil query
     */
    public Cursor queryProvider() {

        return database.query(TABLE_FAVORITE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    /**
     * Simpan data ke dalam database
     * Gunakan method ini untuk query insert di dalam provider
     *
     * @param values nilai data yang akan di simpan
     * @return long id dari data yang baru saja di masukkan
     */
    public long insertProvider(ContentValues values) {
        return database.insert(TABLE_FAVORITE, null, values);
    }


    /**
     * Delete data dalam database
     *
     * @param id data dengan id berapa yang akan di delete
     * @return int jumlah data yang ter-delete
     */
    public int deleteProvider(String id) {


        return database.delete(TABLE_FAVORITE, _ID + " = ?", new String[]{id});
    }
}
