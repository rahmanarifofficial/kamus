package com.rahmanarif.kamusinggris_indonesia.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rahmanarif.kamusinggris_indonesia.R;
import com.rahmanarif.kamusinggris_indonesia.adapter.KamusAdapter;
import com.rahmanarif.kamusinggris_indonesia.db.KamusDbHelper;
import com.rahmanarif.kamusinggris_indonesia.model.Kamus;

import java.util.ArrayList;

public class InggrisIndonesiaFragment extends Fragment {

    RecyclerView listWord;
    KamusAdapter adapter;
    KamusDbHelper kamusDbHelper;

    public InggrisIndonesiaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inggris_indonesia, container, false);
        listWord = v.findViewById(R.id.listVocabulary);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadVocab();
    }

    private void loadVocab() {
        kamusDbHelper = new KamusDbHelper(getContext());
        adapter = new KamusAdapter(getContext());
        listWord.setLayoutManager(new LinearLayoutManager(getContext()));
        listWord.setAdapter(adapter);
        kamusDbHelper.open();
        ArrayList<Kamus> kamuses = kamusDbHelper.getAllData();
        kamusDbHelper.close();
        adapter.addItem(kamuses);
    }
}
