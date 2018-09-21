package com.josjos.aseloe.favoriteapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.josjos.aseloe.favoriteapp.Adapter.Adapter;

import static com.josjos.aseloe.favoriteapp.Db.DatabaseContract.NoteColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity  implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private Adapter adapter;
    ListView lvNotes;

    private final int LOAD_NOTES_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNotes = findViewById(R.id.lv_notes);
        adapter = new Adapter(this, null, true);
        lvNotes.setAdapter(adapter);

        Log.d("CHECK1", "STRINGES");

        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d("NULL", "STRINGES");
        getSupportLoaderManager().restartLoader(LOAD_NOTES_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.d("CHECK2", "STRINGES");
        return new CursorLoader(this, CONTENT_URI,  null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Log.d("CHECK3", "STRINGES");
        adapter.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
