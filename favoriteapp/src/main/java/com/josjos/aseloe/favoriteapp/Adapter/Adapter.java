package com.josjos.aseloe.favoriteapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.josjos.aseloe.favoriteapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.josjos.aseloe.favoriteapp.Db.DatabaseContract.NoteColumns.POSTER_PATH;
import static com.josjos.aseloe.favoriteapp.Db.DatabaseContract.NoteColumns.TITLE;
import static com.josjos.aseloe.favoriteapp.Db.DatabaseContract.NoteColumns.VOTE_AVERAGE;
import static com.josjos.aseloe.favoriteapp.Db.DatabaseContract.getColumnString;

/**
 * Created by dicoding on 12/13/2016.
 */

public class Adapter extends CursorAdapter {

    @BindView(R.id.imgv_gambar_film) ImageView ivPoster;
    @BindView(R.id.tv_judul) TextView tvJudul;
    @BindView(R.id.vote_average) TextView vote_avarage;
    @BindView(R.id.items) View view;


    public static String url_poster = "http://image.tmdb.org/t/p/w185";

    public Adapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.film_items, viewGroup, false);
    }


    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if (cursor != null){

            ButterKnife.bind(this,view);

            tvJudul.setText(getColumnString(cursor,TITLE));
            vote_avarage.setText(getColumnString(cursor,VOTE_AVERAGE));
            Glide.with(context)
                    .load(url_poster + getColumnString(cursor,POSTER_PATH))
                    .into(ivPoster);

        }
    }
}