package com.example.labo;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.labo.api.RequeteMeteo;
import com.example.labo.tacheAsynchorne.CounterTask;

import java.time.LocalDate;

public class MeteoFragment extends Fragment {
    private LocalDate DateMeteo;
    TextView tvMeteo;
    EditText choixVille;
    Button btnChoixVille;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PARAM_METEO = "DateMeteo";

    public MeteoFragment() {
        // Required empty public constructor
    }
    public static MeteoFragment newInstance(LocalDate dateMeteo) {
        MeteoFragment fragment = new MeteoFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_METEO, dateMeteo.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            DateMeteo = LocalDate.parse(getArguments().getString(PARAM_METEO));

        }
    }

    @Override
    // est appelé lorque la vue  associée au fragment est crée
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meteo, container, false);

        tvMeteo = v.findViewById(R.id.tv_meteo);
        choixVille = v.findViewById(R.id.tv_choix_ville);
        btnChoixVille = v.findViewById(R.id.btn_choix_ville);

        choixVille.getText().toString().trim(); // Récupérer le texte de mon input

        btnChoixVille.setOnClickListener(v1 -> {

            choisirVille();
        });
         obtenirMeteoData(); // Fonction pour ma météo
        return v;
    }

    private void choisirVille() {
        RequeteMeteo requeteMeteoApi = new RequeteMeteo(); // L'appel de l'api

        requeteMeteoApi.setWeatherListener(data -> { // Ecouter ma requete, les datas =
            choixVille.getText().toString().trim();
            // Le texte montrer la ville + la température

            tvMeteo.setText(choixVille + " " + data.getTemp() + "°c");
        });
        // AsynctTask : permet d'effectuer des taches de fond ( courte durée )
        requeteMeteoApi.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,  "${choixVille}" );
    }

    // Meteo .......................................
    private void obtenirMeteoData() {
        RequeteMeteo requeteMeteoApi = new RequeteMeteo(); // L'appel de l'api
        requeteMeteoApi.setWeatherListener(data -> { // Ecouter ma requete, les datas =
            // Le texte montrer la ville + la température
            tvMeteo.setText(data.getCity() + " " + data.getTemp() + "°c");
        });
        // AsynctTask : permet d'effectuer des taches de fond ( courte durée )
        requeteMeteoApi.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Bruxelles, BE");
    }


}