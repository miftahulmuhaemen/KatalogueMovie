package com.josjos.aseloe.api19;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.josjos.aseloe.api19.Adapter.FragementAdapter;
import com.josjos.aseloe.api19.Fragements.FilmPlayingnow;
import com.josjos.aseloe.api19.Fragements.FilmUpcoming;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    Locale current;

    static final String EXTRA_UPCOMING = "EXTRA_UPCOMING";
    static final String EXTRA_CURRENT = "EXTRA_CURRENT";

    static final String PlayingNow = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.API_KEY +"&language=";
    static final String Upcoming = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + BuildConfig.API_KEY +"&language=";
    static final String Cari = "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.API_KEY +"&language=";

    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.drawer) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);

                        int id = menuItem.getItemId();
                        if (id == R.id.nav_search) {
                            Intent moveDataIntent = new Intent(MainActivity.this,SearchLayout.class);
                            moveDataIntent.putExtra(SearchLayout.EXTRA_CARI,Cari);
                            startActivity(moveDataIntent);
                        }
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }

    private void setupViewPager(ViewPager viewPager) {
        FragementAdapter adapter = new FragementAdapter(getSupportFragmentManager());
        adapter.addFragment(new FilmPlayingnow(), getString(R.string.now_playing));
        adapter.addFragment(new FilmUpcoming(), getString(R.string.upcoming));
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_header,menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryCari) {
                Intent moveDataIntent = new Intent(MainActivity.this,SearchLayout.class);
                moveDataIntent.putExtra(SearchLayout.EXTRA_CARI,Cari +  current.getDefault().getCountry().toLowerCase() + "&query=" +queryCari);
                startActivity(moveDataIntent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
