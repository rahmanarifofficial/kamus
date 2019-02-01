package com.rahmanarif.kamusinggris_indonesia;

import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.rahmanarif.kamusinggris_indonesia.db.KamusDbHelper;
import com.rahmanarif.kamusinggris_indonesia.model.Kamus;
import com.rahmanarif.kamusinggris_indonesia.prefs.AppPreferences;
import com.rahmanarif.kamusinggris_indonesia.ui.activity.KamusActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progres_bar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        KamusDbHelper kamusDbHelper;
        AppPreferences appPreferences;
        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {
            kamusDbHelper = new KamusDbHelper(MainActivity.this);
            appPreferences = new AppPreferences(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreferences.getFirstRun();

            if (firstRun) {
                ArrayList<Kamus> kamusesEnglish = preLoadRaw(R.raw.english_indonesia);
                ArrayList<Kamus> kamusesIndonesia = preLoadRaw(R.raw.indonesia_english);
                publishProgress((int) progress);

                try {
                    kamusDbHelper.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) / (kamusesEnglish.size() + kamusesIndonesia.size());

                kamusDbHelper.insertTransaction(kamusesEnglish, true);
                progress += progressDiff;
                publishProgress((int) progress);

                kamusDbHelper.insertTransaction(kamusesIndonesia, false);
                progress += progressDiff;
                publishProgress((int) progress);

                kamusDbHelper.close();
                appPreferences.setFirstRun(false);
                publishProgress((int) maxProgress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(1000);
                        publishProgress(50);
                        this.wait(30);
                        publishProgress((int) maxProgress);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(MainActivity.this, KamusActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<Kamus> preLoadRaw(int data) {
        ArrayList<Kamus> kamuses = new ArrayList<>();
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(data);
            reader = new BufferedReader(new InputStreamReader(raw_dict));
            String line = null;
            int count=0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                Kamus kamus;

                kamus = new Kamus(splitstr[0], splitstr[1]);
                kamuses.add(kamus);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamuses;
    }
}
