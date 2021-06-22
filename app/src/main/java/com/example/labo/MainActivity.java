package com.example.labo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private RecyclerView monRecycler;
    private Button btnAdd;

    private List<Activity> activities = new ArrayList<>();
    private ActivityAdapter activityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Base de donnée.......................
        // Initialisation du DAO pour travailler avec des produits
        /*ActivityStructureDb activityDAO = new ActivityStructureDb(getApplicationContext());

       Activity a1 = new Activity(1, "course","26/09/98" );

       // ouvrir en écriture
       activityDAO.openWritable();
       long id = activityDAO.inserer(a1);

       // lecture de la db
        activityDAO.ouvrirLecture();
        List<Activity> activities = activityDAO.getAll(); */


        monRecycler = findViewById(R.id.rv_activity);
        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirActivity();
            }
        });
        // Adapter
        activityAdapter = new ActivityAdapter(this, activities);


        // RecyclerView ...................
        // Liaison avec le Layout
        monRecycler = findViewById((R.id.rv_activity));

        // Creation du type de layout que le RecyclerView utilise (Linear/Grid/StraggeredGrid)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        // va permettre de définir le type de layout de mon recycler
        monRecycler.setLayoutManager(layoutManager);
        monRecycler.setHasFixedSize(false); // Si des éléments sont rajouté ou enlevé, la modification du contenu de l'adapteur ne modifie ni sa hauteur ni sa largeur
        monRecycler.setAdapter(activityAdapter);
    }
    private void ouvrirActivity() {
        Intent intent = new Intent(getApplicationContext(), AjouterActivity.class);
        startActivity(intent);
    }
    private void dbActivite(){
        // Récupération des tâches
        ActivityStructureDb activityDAO = new ActivityStructureDb(getApplicationContext());

        Activity a1 = new Activity(1, "course","26/09/98" );

        // lecture de la db
        activityDAO.ouvrirLecture();
        List<Activity> data = activityDAO.getAll();
        activityDAO.close();

        // Mise à jour de l'adapter et de la RecyclerView
        activities.clear();
        activities.addAll(data);
        activityAdapter.notifyDataSetChanged(); // Prévenir mon adapter
    }

}