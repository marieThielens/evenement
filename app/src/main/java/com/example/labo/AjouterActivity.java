package com.example.labo;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.labo.bd.ActivityStructureDb;
import com.example.labo.bd.DbRequete;
import com.example.labo.models.Activity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

    public class AjouterActivity extends AppCompatActivity {

        Button btnValiderTache;
        EditText inputNomTache, inputAjouterDate;
        ImageView btnCalendar;
        LocalDate dateLimite = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ajouter);

            // Liaison
            btnValiderTache = findViewById(R.id.btn_valider_activity);
            inputNomTache = findViewById(R.id.input_nom_ajouter);
            inputAjouterDate = findViewById(R.id.editText_date_limite);
            btnCalendar = findViewById(R.id.btn_calendrier);


            // Ajouter mes taches dans ma db
            btnValiderTache.setOnClickListener(v -> {
                ajouterTodo();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
            });
            // Déactivation de la saisie via le clavier
            inputAjouterDate.setInputType(InputType.TYPE_NULL);


            // le calendrier  .....................................
            Calendar calendar = Calendar.getInstance();
            int jour = calendar.get(Calendar.DAY_OF_MONTH);
            int mois = calendar.get(Calendar.MONTH);
            int annee = calendar.get(Calendar.YEAR);

            btnCalendar.setOnClickListener(v -> {
                DatePickerDialog picker = new DatePickerDialog(AjouterActivity.this,
                        new DatePickerDialog.OnDateSetListener() { // l'utilisateur à cliqué sur une date
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                // je récupère le contexte de l'pp > puis les ressources > la configuration > locale (langue utilisé) > la 1ere
                                Locale locale = AjouterActivity.this.getResources().getConfiguration().getLocales().get(0) ;

                                // mois + 1 pcq datePicker et le calendar vont de 0 à 11, le localdate de 1 jusqu à 12
                                dateLimite = LocalDate.of(year, month + 1, dayOfMonth);
                                // Foramter en string la date pour que l'année soit à la fin
                                String date = dateLimite.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH));
                                inputAjouterDate.setText(date); // Rajouter la date dans mon input
                            }
                        },
                        annee, mois, jour );
                picker.show();
            });
        }

        private void ajouterTodo() {

            String titreTache = inputNomTache.getText().toString();
            LocalDate dateCreation = LocalDate.now();
            // de models. Créer une nouvelle tache
            Activity activity = new Activity( titreTache, dateCreation);

            // Sauver dans la db
            ActivityStructureDb activityDAO = new ActivityStructureDb(getApplicationContext());
            //activityDAO.supprimer(0);

            activityDAO.ouvrirLecture();
                activityDAO.inserer(activity);
                activityDAO.close();
        }
}
