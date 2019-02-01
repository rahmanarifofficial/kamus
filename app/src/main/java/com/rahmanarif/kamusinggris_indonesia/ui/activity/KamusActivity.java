package com.rahmanarif.kamusinggris_indonesia.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rahmanarif.kamusinggris_indonesia.MainActivity;
import com.rahmanarif.kamusinggris_indonesia.R;
import com.rahmanarif.kamusinggris_indonesia.ui.fragment.IndonesiaInggrisFragment;
import com.rahmanarif.kamusinggris_indonesia.ui.fragment.InggrisIndonesiaFragment;

public class KamusActivity extends AppCompatActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamus);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.kamusinggris:
                        fragment = new InggrisIndonesiaFragment();
                        getSupportActionBar().setTitle(R.string.inggris_indonesia);
                        showFragment(fragment);
                        return true;
                    case R.id.kamusindonesia:
                        fragment = new IndonesiaInggrisFragment();
                        getSupportActionBar().setTitle(R.string.indonesia_inggris);
                        showFragment(fragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
        showFragment(new InggrisIndonesiaFragment());
    }


    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.commit();
    }

}
