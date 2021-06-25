package com.example.labo;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.labo.api.RequeteMeteo;
import com.example.labo.tacheAsynchorne.CounterTask;

import java.time.LocalDate;

public class MeteoFragment extends Fragment {
    private LocalDate DateMeteo;
    TextView tvMeteo;
    EditText choixVille;
    Button btnChoixVille;
    ImageView soleil;


    public MeteoFragment() {
        // Required empty public constructor
    }



    @Override
    // est appelé lorque la vue  associée au fragment est crée
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meteo, container, false);

        // Animer le soleil ........................
        soleil = v.findViewById(R.id.img_soleil);

        RotateAnimation animation = new RotateAnimation(0.0f, 360.0f,Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(700);

        soleil.startAnimation(animation);

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