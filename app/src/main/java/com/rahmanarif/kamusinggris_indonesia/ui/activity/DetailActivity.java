package com.rahmanarif.kamusinggris_indonesia.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.rahmanarif.kamusinggris_indonesia.R;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_KATA = "extra_kata";
    public static String EXTRA_ARTI = "extra_arti";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle("Deskripsi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvKata = findViewById(R.id.detailKata);
        TextView tvArti = findViewById(R.id.detailArti);

        tvKata.setText(getIntent().getStringExtra(EXTRA_KATA));
        tvArti.setText(getIntent().getStringExtra(EXTRA_ARTI));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
