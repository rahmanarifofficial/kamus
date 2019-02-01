package com.rahmanarif.kamusinggris_indonesia.ui.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rahmanarif.kamusinggris_indonesia.R;
import com.rahmanarif.kamusinggris_indonesia.adapter.KamusAdapter;
import com.rahmanarif.kamusinggris_indonesia.db.KamusDbHelper;
import com.rahmanarif.kamusinggris_indonesia.model.Kamus;

import java.util.ArrayList;

public class InggrisIndonesiaFragment extends Fragment {

    private RecyclerView listWord;
    private KamusAdapter adapter;
    private KamusDbHelper kamusDbHelper;
    private ArrayList<Kamus> kamuses = new ArrayList<>();
    private SearchView searchView = null;

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
        kamusDbHelper = new KamusDbHelper(getContext());
        loadVocab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    loadVocab(newText);
                    return false;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadVocab() {
        adapter = new KamusAdapter();
        listWord.setLayoutManager(new LinearLayoutManager(getContext()));
        listWord.setAdapter(adapter);
        try {
            kamusDbHelper.open();
            kamuses = kamusDbHelper.getAllData(true);
            Log.d("listKamus", kamuses.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            kamusDbHelper.close();
        }
        adapter.addItem(kamuses);
    }

    private void loadVocab(String newText) {
        adapter = new KamusAdapter();
        listWord.setLayoutManager(new LinearLayoutManager(getContext()));
        listWord.setAdapter(adapter);
        try {
            kamusDbHelper.open();
            kamuses = kamusDbHelper.getDataSearch(newText, true);
            Log.d("listKamus", kamuses.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            kamusDbHelper.close();
        }
        adapter.addItem(kamuses);
    }
}
