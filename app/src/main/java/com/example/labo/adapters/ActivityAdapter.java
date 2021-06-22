package com.example.labo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labo.R;
import com.example.labo.bd.ActivityStructureDb;
import com.example.labo.models.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder>{

    //private ArrayList<Activity> activityData;
    // private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitreItem, tvDateItem;

        public ViewHolder(View v) {
            super(v);

            tvTitreItem = v.findViewById(R.id.activity_item_titre);
            tvDateItem = v.findViewById(R.id.todo_item_date);

        }

        public TextView getTvTitreItem() {
            return tvTitreItem;
        }
        public void setTvTitreItem(TextView tvTitreItem) {
            this.tvTitreItem = tvTitreItem;
        }
        public TextView getTvDateItem() {
            return tvDateItem;
        }
        public void setTvDateItem(TextView tvDateItem) {
            this.tvDateItem = tvDateItem;
        }
    }
    private Context context;
    private List<Activity> dataSet;
    // private RecyclerView monRecycler;

    public ActivityAdapter(Context context, List<Activity> activities) {
        this.context = context;
        this.dataSet = activities;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
        return new ActivityAdapter.ViewHolder(v); // Renvoie la nouvelle vue encapsulée dans un ViewHolder
    }

    // Méthode qui permet de mettre à jour la vue avec les données utilisé par le layoutManager
    @Override
    public void onBindViewHolder(@NonNull  ActivityAdapter.ViewHolder holder, int position) {
    Activity cible = dataSet.get(position); // L'index de ma data , ligne 55
    // Mise à jour de la vue via le ViewHolder
        holder.getTvTitreItem().setText(cible.getTitreTache());
        holder.getTvDateItem().setText(cible.getDateCreation());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
