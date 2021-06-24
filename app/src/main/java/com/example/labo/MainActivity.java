package com.example.labo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.labo.adapters.ActivityAdapter;
import com.example.labo.bd.ActivityStructureDb;
import com.example.labo.bd.DbRequete;
import com.example.labo.models.Activity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// implements pour mon spinner (comme input select )
public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    String[] periode = {"jour", "semaine", "mois"};

    private List<Activity> activities = new ArrayList<>();
    private ActivityAdapter activityAdapter;

    RecyclerView monRecycler;
    Button btnAdd;
    Spinner choixPeriode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Liaison avec le Layout............................
        btnAdd = findViewById(R.id.btn_add);
        monRecycler = findViewById(R.id.rv_activity);
        choixPeriode = findViewById(R.id.choix_periode);

        // Spinner ...........................................
        // Ecouter mon Spinner (input select )
        choixPeriode.setOnItemSelectedListener(this);

        // Un tableau qui va recevoir mes périodes ( spinner)
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, periode);
        // Envoi d'un layout android pour chaque item
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Lui donner l'adapteur
        choixPeriode.setAdapter(aa);

        // Ecouter mon bouton + ......................
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirActivity();
            }
        });

        // Fragment Meteo ..............................
//        FragmentManager fm = getSupportFragmentManager();
 //       findViewById(R.id.container_fragment);
//        FragmentTransaction transaction = fm.beginTransaction();
 //       transaction.add(R.id.monLayoutFragment, MeteoFragment.getInstance());
//        transaction.commit();


        // Adapter............................................;;
        activityAdapter = new ActivityAdapter(this, activities);

        // Event en liste pour mon boutton delete
        activityAdapter.EnvoyerActiviteFinie(new ActivityAdapter.ActiviteFinie() {
            @Override
            public void onFinish(Activity activities) {
                finirTache(activities);
            }
        });

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

    // Supprimer une activité.................................
    private void finirTache(Activity activity) {
        // Sauvegarde dans la DB
        ActivityStructureDb activityDAO = new ActivityStructureDb(getApplicationContext());
        activityDAO.ouvrirLecture();

        activityDAO.supprimer(activity.getId());
        activityDAO.close();

        // Mise a jours de la RecyclerView
        activityAdapter.notifyDataSetChanged();
}

    // Spinner .......................
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),periode[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}