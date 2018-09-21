package com.josjos.aseloe.api19.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.josjos.aseloe.api19.Database.FIlmHelper;

import static com.josjos.aseloe.api19.Database.DatabaseContract.AUTHORITY;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.CONTENT_URI;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.TABLE_FAVORITE;

public class FilmProvider extends ContentProvider {

    /*
    Integer digunakan sebagai identifier antara select all sama select by id
     */
    private static final int TABLE_FAVORITE_ = 1;
    private static final int TABLE_FAVORITE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /*
    Uri matcher untuk mempermudah identifier dengan menggunakan integer
    misal
    uri com.dicoding.mynotesapp di cocokan dengan integer 1
    uri com.dicoding.mynotesapp/# dicodokan dengan integer 2
     */

    static {
        // content://com.josjos.aseloe.api19/favorite
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE, TABLE_FAVORITE_);

        // content://com.dicoding.mynotesapp/favorite/id
        sUriMatcher.addURI(AUTHORITY,TABLE_FAVORITE + "/#", TABLE_FAVORITE_ID);
    }

    private FIlmHelper noteHelper;

    @Override
    public boolean onCreate() {
        noteHelper = new FIlmHelper(getContext());
        noteHelper.open();
        return true;
    }

    /*
    Method query digunakan ketika ingin menjalankan query Select
    Return cursor
     */

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {

        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case TABLE_FAVORITE_:
                cursor = noteHelper.queryProvider();
                break;
            case TABLE_FAVORITE_ID:
                cursor = noteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added;

        switch (sUriMatcher.match(uri)) {
            case TABLE_FAVORITE_:
                added = noteHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);

    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case TABLE_FAVORITE_ID:
                deleted = noteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }
}
