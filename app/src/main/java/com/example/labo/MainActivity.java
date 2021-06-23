package com.example.labo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.labo.adapters.ActivityAdapter;
import com.example.labo.bd.ActivityStructureDb;
import com.example.labo.models.Activity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Activity> activities = new ArrayList<>();
    private ActivityAdapter activityAdapter;

    RecyclerView monRecycler;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Liaison avec le Layout
        btnAdd = findViewById(R.id.btn_add);
        monRecycler = findViewById(R.id.rv_activity);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirActivity();
            }
        });

        // Adapter
        activityAdapter = new ActivityAdapter(this, activities);

        // RecyclerView .....................................
        // Creation du type de layout que le RecyclerView utilise (Linear/Grid/StraggeredGrid)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        // va permettre de définir le type de layout de mon recycler
        monRecycler.setLayoutManager(layoutManager);
        monRecycler.setHasFixedSize(false); // Si des éléments sont rajouté ou enlevé, la modification du contenu de l'adapteur ne modifie ni sa hauteur ni sa largeur
        monRecycler.setAdapter(activityAdapter);

        // Récupération des data de la db
        ActivityStructureDb activityDAO = new ActivityStructureDb(getApplicationContext());
        activityDAO.ouvrirLecture();
        List<Activity> data = activityDAO.getAll();

        // Mise à jour de l'adapter de la RecyclerView
        activities.clear();
        activities.addAll(data); // Rajouter les données
        activityAdapter.notifyDataSetChanged(); // prevenir l'adapteur que ça a changé
    }
    private void ouvrirActivity() {
        Intent intent = new Intent(getApplicationContext(), AjouterActivity.class);
        startActivity(intent);
    }


}