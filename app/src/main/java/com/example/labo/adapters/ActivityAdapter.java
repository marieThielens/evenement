package com.example.labo.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labo.R;
import com.example.labo.bd.ActivityStructureDb;
import com.example.labo.models.Activity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder>{

    @FunctionalInterface
    // Supprimer la tache faites
    public interface ActiviteFinie {
        // models
        void onFinish(Activity activities);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitreItem, tvDateItem;
        private Button btnFait;

        public ViewHolder(View v) {
            super(v);
            tvTitreItem = v.findViewById(R.id.activity_item_titre);
            tvDateItem = v.findViewById(R.id.todo_item_date);
            btnFait = v.findViewById(R.id.todo_item_btn_fini);
        }
        // getters and setters
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
        public Button getBtnFait() { return btnFait; }
        public void setBtnFait(Button btnFait) {this.btnFait = btnFait; }
    }
    private Context context;
    private List<Activity> dataSet;
    private ActiviteFinie activiteFinie; // de addapter ligne 25

    // Context, tableau de models
    public ActivityAdapter(Context context, List<Activity> activities) {
        this.context = context;
        this.dataSet = activities;
    }
    // fonction pour enlever la tache
    public void EnvoyerActiviteFinie( ActiviteFinie activiteFinie){
        this.activiteFinie = activiteFinie;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
        return new ActivityAdapter.ViewHolder(v); // Renvoie la nouvelle vue encapsulée dans un ViewHolder
    }

    // Méthode qui permet de mettre à jour la vue avec les données utilisé par le layoutManager
    @Override
    public void onBindViewHolder(@NonNull  ActivityAdapter.ViewHolder holder, int position) {
    Activity activities = dataSet.get(position); // L'index de ma data , ligne 55
        // Mise à jour de la vue via le ViewHolder
        holder.getTvTitreItem().setText(activities.getTitreTache());


        // Formatage de la date
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM YYYY", locale);

        String dateMessage = "Date : %s"; // La phrase qui affiche la date
        String dateFormatted;

        // Avoir la position
        dateFormatted = activities.getDateCreation().format(formatter);
        // Mise à jour de la vue via le ViewHolder
        holder.getTvDateItem().setText(String.format(dateMessage, dateFormatted));

        // Ecoute de mon bouton "Fait"
        holder.getBtnFait().setOnClickListener(v -> {
            // est null
            this.activiteFinie.onFinish(activities); // ligne 84
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
