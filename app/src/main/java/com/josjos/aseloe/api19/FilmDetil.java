package com.josjos.aseloe.api19;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.josjos.aseloe.api19.Database.FIlmHelper;
import com.josjos.aseloe.api19.Entity.FilmItems;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

import static com.josjos.aseloe.api19.Adapter.RecyclerViewAdapter.url_poster;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.CONTENT_URI;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.OVERVIEW;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.POSTER_PATH;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.RELEASE_DATE;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.TITLE;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.VOTE_AVERAGE;

public class FilmDetil extends AppCompatActivity {

    @BindView(R.id.imgv_gambar_film_detil) ImageView img_poster;
    @BindView(R.id.textDeskripsi_detil) TextView tv_deskripsi;
    @BindView(R.id.textTanggalRilis_detil) TextView tv_tanggalRilis;
    @BindView(R.id.detil_voteAvarage) TextView tv_VoteAvarage;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;

    FilmItems filmItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detil);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setSupportActionBar(toolbar);

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) filmItems = new FilmItems(cursor);
                cursor.close();
            }
        }

            Glide.with(this)
                    .load(url_poster + getIntent().getStringExtra(POSTER_PATH))
                    .into(img_poster);

            collapsingToolbarLayout.setTitle(getIntent().getStringExtra(TITLE));
            tv_tanggalRilis.setText(getIntent().getStringExtra(RELEASE_DATE));
            tv_deskripsi.setText(getIntent().getStringExtra(OVERVIEW));
            tv_VoteAvarage.setText(getIntent().getStringExtra(VOTE_AVERAGE));


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (filmItems != null) {
                    Snackbar.make(view, "Sudah Ada!",
                            Snackbar.LENGTH_LONG).show();
                } else {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(TITLE,getIntent().getStringExtra(TITLE));
                    contentValues.put(RELEASE_DATE,getIntent().getStringExtra(RELEASE_DATE));
                    contentValues.put(OVERVIEW,getIntent().getStringExtra(OVERVIEW));
                    contentValues.put(POSTER_PATH,getIntent().getStringExtra(POSTER_PATH));
                    contentValues.put(VOTE_AVERAGE,getIntent().getStringExtra(VOTE_AVERAGE));

                    getContentResolver().insert(CONTENT_URI,contentValues);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
