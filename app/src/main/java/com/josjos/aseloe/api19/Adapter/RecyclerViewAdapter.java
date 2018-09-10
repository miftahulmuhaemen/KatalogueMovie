package com.josjos.aseloe.api19.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.josjos.aseloe.api19.FilmDetil;
import com.josjos.aseloe.api19.FilmItems;
import com.josjos.aseloe.api19.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

import static com.josjos.aseloe.api19.FilmDetil.EXTRA_DETIL_Deskripsi;
import static com.josjos.aseloe.api19.FilmDetil.EXTRA_DETIL_Gambar;
import static com.josjos.aseloe.api19.FilmDetil.EXTRA_DETIL_Judul;
import static com.josjos.aseloe.api19.FilmDetil.EXTRA_DETIL_Tanggal;

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecylerViewAdapterHolder>  {

    private Context context;
    @Getter @Setter  private ArrayList<FilmItems> listFilm;
    private String url_poster = "http://image.tmdb.org/t/p/w185";

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
        holder.tvDeskripsi.setText(data.getOverview());
        holder.tanggal_rilis.setText(data.getRelease_date());
        Glide.with(context)
                .load(url_poster + data.getPoster_path())
                .into(holder.ivPoster);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context,FilmDetil.class);

                intent.putExtra(EXTRA_DETIL_Judul,data.getTitle());
                intent.putExtra(EXTRA_DETIL_Deskripsi,data.getOverview());
                intent.putExtra(EXTRA_DETIL_Tanggal,data.getRelease_date());
                intent.putExtra(EXTRA_DETIL_Gambar,url_poster + data.getPoster_path());
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
        @BindView(R.id.tv_deskripsi) TextView tvDeskripsi;
        @BindView(R.id.tv_tanggal_rilis) TextView tanggal_rilis;
        @BindView(R.id.items) View view;

        RecylerViewAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
