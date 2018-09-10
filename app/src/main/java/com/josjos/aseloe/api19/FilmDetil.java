package com.josjos.aseloe.api19;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmDetil extends AppCompatActivity {

    @BindView(R.id.imgv_gambar_film_detil) ImageView img_poster;
    @BindView(R.id.textDeskripsi_detil) TextView tv_deskripsi;
    @BindView(R.id.textTanggalRilis_detil) TextView tv_tanggalRilis;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;

    public  static String EXTRA_DETIL_Judul = "001";
    public  static String EXTRA_DETIL_Deskripsi = "002";
    public  static String EXTRA_DETIL_Tanggal = "003";
    public  static String EXTRA_DETIL_Gambar = "004";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detil);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(this)
                .load(getIntent().getStringExtra(EXTRA_DETIL_Gambar))
                .into(img_poster);
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra(EXTRA_DETIL_Judul));
        tv_tanggalRilis.setText(getIntent().getStringExtra(EXTRA_DETIL_Tanggal));
        tv_deskripsi.setText(getIntent().getStringExtra(EXTRA_DETIL_Deskripsi));
    }
}
