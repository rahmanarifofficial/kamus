package com.rahmanarif.kamusinggris_indonesia.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahmanarif.kamusinggris_indonesia.R;
import com.rahmanarif.kamusinggris_indonesia.model.Kamus;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.KamusViewHolder> {
    private ArrayList<Kamus> data = new ArrayList<>();
    private Context context;

    public KamusAdapter(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(ArrayList<Kamus> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KamusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new KamusViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_kata, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KamusViewHolder kamusViewHolder, int i) {
        kamusViewHolder.tvKata.setText(data.get(i).getKata());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class KamusViewHolder extends RecyclerView.ViewHolder {
        private TextView tvKata;

        public KamusViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKata = itemView.findViewById(R.id.kata);
        }
    }
}
