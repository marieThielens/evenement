package com.example.labo;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.labo.bd.ActivityStructureDb;
import com.example.labo.models.Activity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

    public class AjouterActivity extends AppCompatActivity {

        private Button btnValiderTache;
        private EditText inputNomTache, inputAjouterDate;
        private ImageView btnCalendar;
        private LocalDate dateLimite = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ajouter);

            // Liaison
            btnValiderTache = findViewById(R.id.btn_valider_activity);
            inputNomTache = findViewById(R.id.input_nom_ajouter);
            inputAjouterDate = findViewById(R.id.editText_date_limite);
            btnCalendar = findViewById(R.id.btn_calendrier);

            // le calendrier  ..............
            // Désactivation de la saisie via le clavier
            inputAjouterDate.setInputType(InputType.TYPE_NULL);

            Calendar calendar = Calendar.getInstance();
            int jour = calendar.get(Calendar.DAY_OF_MONTH);
            int mois = calendar.get(Calendar.MONTH);
            int annee = calendar.get(Calendar.YEAR);

            // Ajouter mes taches dans ma db
            btnValiderTache.setOnClickListener(v -> {
                ajouterTodo();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
            });
        }

        private void ajouterTodo() {
            String titreTache = inputNomTache.getText().toString();
            String dateCreation = "LocalDate.now()";
            // de models. Créer une nouvelle tache
            Activity activity = new Activity( titreTache, dateCreation);

            // Sauver dans la db
            ActivityStructureDb activityDAO = new ActivityStructureDb(getApplicationContext());
                activityDAO.openWritable();
                activityDAO.inserer(activity);
                activityDAO.close();
        }
}
