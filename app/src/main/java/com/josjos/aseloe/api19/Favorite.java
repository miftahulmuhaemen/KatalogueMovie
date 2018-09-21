package com.josjos.aseloe.api19;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.josjos.aseloe.api19.Adapter.RecyclerViewAdapter;
import com.josjos.aseloe.api19.Entity.FilmItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.CONTENT_URI;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.OVERVIEW;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.POSTER_PATH;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.RELEASE_DATE;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.TITLE;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.VOTE_AVERAGE;

public class Favorite extends AppCompatActivity{

    @BindView(R.id.rv_fav)
    RecyclerView recyclerView;
    ArrayList<FilmItems> items = new ArrayList<FilmItems>();
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(recyclerView.getContext());

        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);

        FilmItems itemses;
        if (cursor != null) {
            if (cursor.moveToFirst()){
                do {
                    itemses = new FilmItems();
                    itemses.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                    itemses.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                    itemses.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                    itemses.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                    itemses.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                    itemses.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));

                    items.add(itemses);
                    cursor.moveToNext();

                } while (!cursor.isAfterLast());
            }
            cursor.close();
            recyclerViewAdapter.setListFilm(items);
        }

        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
