package com.example.labo;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.labo.api.RequeteMeteo;
import com.example.labo.tacheAsynchorne.CounterTask;

public class MeteoFragment extends Fragment {

    TextView tvMeteo;
    EditText choixVille;
    Button btnChoixVille;

    /*
    private static MeteoFragment instance;
    public static MeteoFragment getInstance() {
        if (instance == null ) {
            instance = new MeteoFragment();
        }
    }
    */


    public MeteoFragment() {
        // Required empty public constructor
    }

    @Override
    // est appelé lorque la vue  associée au fragment est crée
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meteo, container, false);

        tvMeteo = v.findViewById(R.id.tv_meteo);
        choixVille = v.findViewById(R.id.tv_choix_ville);
        btnChoixVille = v.findViewById(R.id.btn_choix_ville);

         obtenirMeteoData(); // Fonction pour ma météo
        return v;
    }

    // Meteo .......................................
    private void obtenirMeteoData() {
        RequeteMeteo requeteMeteoApi = new RequeteMeteo(); // L'appel de l'api
        requeteMeteoApi.setWeatherListener(data -> { // Ecouter ma requete, les datas =
            // Le texte montrer la ville + la température
            tvMeteo.setText(data.getCity() + " " + data.getTemp() + "°c");
        });
        // AsynctTask : permet d'effectuer des taches de fond ( courte durée )
        requeteMeteoApi.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Bruxelles, CA");
    }
}