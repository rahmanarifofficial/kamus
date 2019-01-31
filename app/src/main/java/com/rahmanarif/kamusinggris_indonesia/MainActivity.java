package com.rahmanarif.kamusinggris_indonesia;

import android.content.Intent;
import android.content.res.Resources;
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
        double maxProgress;

        @Override
        protected void onPreExecute() {
            kamusDbHelper = new KamusDbHelper(MainActivity.this);
            appPreferences = new AppPreferences(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreferences.getFirstRun();

            if (firstRun) {
                ArrayList<Kamus> kamus = preLoadRaw();
                kamusDbHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / kamus.size();

                kamusDbHelper.beginTransaction();
                try {
                    for (Kamus data : kamus) {
                        kamusDbHelper.insertTransaction(data);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    kamusDbHelper.setTransactionSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                kamusDbHelper.endTransaction();
                kamusDbHelper.close();
                appPreferences.setFirstRun(false);
                publishProgress((int) maxProgress);
            } else {
                try {
                    synchronized (this){
                        this.wait(2000);
                        publishProgress(50);
                        this.wait(2000);
                        publishProgress((int)maxProgress);
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

    public ArrayList<Kamus> preLoadRaw() {
        ArrayList<Kamus> kamuses = new ArrayList<>();
        String line = null;
        BufferedReader reader_dict_inggris, reader_dict_indo;
        try {
            Resources res = getResources();
            InputStream raw_dict_inggris = res.openRawResource(R.raw.english_indonesia);
            InputStream raw_dict_indo = res.openRawResource(R.raw.indonesia_english);

            reader_dict_inggris = new BufferedReader(new InputStreamReader(raw_dict_inggris));
            reader_dict_indo = new BufferedReader(new InputStreamReader(raw_dict_indo));

            int count = 0;

            do {
                line = reader_dict_inggris.readLine();
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
