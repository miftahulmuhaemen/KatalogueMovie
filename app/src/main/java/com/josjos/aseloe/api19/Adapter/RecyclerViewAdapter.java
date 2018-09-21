package com.josjos.aseloe.api19.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.josjos.aseloe.api19.FilmDetil;
import com.josjos.aseloe.api19.Entity.FilmItems;
import com.josjos.aseloe.api19.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.CONTENT_URI;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.OVERVIEW;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.POSTER_PATH;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.RELEASE_DATE;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.TITLE;
import static com.josjos.aseloe.api19.Database.DatabaseContract.NoteColumns.VOTE_AVERAGE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecylerViewAdapterHolder>  {

    private Context context;
    @Getter @Setter  private ArrayList<FilmItems> listFilm;
    public static String url_poster = "http://image.tmdb.org/t/p/w185";

    public RecyclerViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecylerViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_items, parent, false);
        return new RecylerViewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(RecylerViewAdapterHolder holder, int position) {
        final FilmItems data = getListFilm().get(position);
        holder.tvJudul.setText(data.getTitle());
        holder.vote_avarage.setText(data.getVote_average());
        Glide.with(context)
                .load(url_poster + data.getPoster_path())
                .into(holder.ivPoster);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context,FilmDetil.class);

                intent.putExtra(TITLE,data.getTitle());
                intent.putExtra(OVERVIEW,data.getOverview());
                intent.putExtra(RELEASE_DATE,data.getRelease_date());
                intent.putExtra(POSTER_PATH,data.getPoster_path());
                intent.putExtra(VOTE_AVERAGE,data.getVote_average());

                Uri uri = Uri.parse(CONTENT_URI + "/" + data.getId());
                intent.setData(uri);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  getListFilm().size() == 0 ? 0 : getListFilm().size();
    }

    class RecylerViewAdapterHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.imgv_gambar_film) ImageView ivPoster;
        @BindView(R.id.tv_judul) TextView tvJudul;
        @BindView(R.id.vote_average) TextView vote_avarage;
        @BindView(R.id.items) View view;

        RecylerViewAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
