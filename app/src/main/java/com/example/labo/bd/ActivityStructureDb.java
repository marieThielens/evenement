package com.example.labo.bd;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.labo.models.Activity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ActivityStructureDb {
    //  l'état actuel de l'application / objet.
    //  Il permet aux objets nouvellement créés de comprendre ce qui s'est passé
    // Il donne accès à des choses telles que les bases de données
    private Context context;
    private SQLiteDatabase db; // la representation Java de la base de données
    private DbHelper dbHelper; // mon fichier DbHelper.java

    // Constructeur TodoDAO
    public ActivityStructureDb (Context context){
        this.context = context;
    }

    // Méthode de connexion
    public ActivityStructureDb  ouvrirLecture() {
        dbHelper = new DbHelper(context); // on crée le helper
        db = dbHelper.getWritableDatabase(); // on initialise la db en écriture
        return this;
    }

    // fermeture de notre db
    public void close() {
        db.close();
        dbHelper.close();
    }


    private ContentValues creerCv(Activity activity) {
        // Formater ma LocalDate en string  car sinon pas lu par db
        String date = activity.getDateCreation().format(DateTimeFormatter.ISO_DATE);

        ContentValues cv = new ContentValues();
        cv.put(DbRequete.Activity.COLUMN_TITRE, activity.getTitreTache()); // Tache = constructeur
        cv.put(DbRequete.Activity.COLUMN_DATE, date); // Ma date formatée en ligne 44

        return cv;
    }
    // Centraliser nos interaction
    // curseurs : permet de parcourir les lignes de résultat d'une requete SELECT
    private Activity produireCurseur(Cursor c) {
        long id = c.getLong(c.getColumnIndex(DbRequete.Activity.COLUMN_ID));
        String titre = c.getString(c.getColumnIndex(DbRequete.Activity.COLUMN_TITRE));
        String date = c.getString(c.getColumnIndex(DbRequete.Activity.COLUMN_DATE));

        // formatage
        LocalDate creationDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);

        return new Activity(id, titre, creationDate);
    }

    // Créer
    public  long inserer(Activity activity) {
        ContentValues cv = creerCv(activity);
        return  db.insert(DbRequete.Activity.TABLE_NAME, null, cv);
    }
    // Read
    public Activity getById(long id) {
        Cursor cursor = db.query(DbRequete.Activity.TABLE_NAME,
                null, // Toutes les colonnes
                DbRequete.Activity.COLUMN_ID + " = ?",
                new String[]{ String.valueOf(id)},
                null,
                null,
                null
        );
        // S'il n'y a pas de résultat
        if(cursor.getCount() ==0 ){
            return null;
        }
        cursor.moveToFirst(); // place le curseur sur l'élément trouvé
        return produireCurseur(cursor); // Renvoie le produit extrait du curseur
    }

    public List<Activity> getAll(){
        Cursor cursor = db.query(DbRequete.Activity.TABLE_NAME, null, null, null, null, null, null);

        List<Activity> activities = new ArrayList<>();
        if(cursor.getCount() == 0) { return activities; }

        cursor.moveToFirst(); // Lit la premiere ligne
        while(! cursor.isAfterLast()) {
            Activity t = produireCurseur(cursor);
            activities.add(t);
            cursor.moveToNext();
        }
        cursor.close();
        return activities;
    }

    public boolean supprimer(long id) {
        String idAsupprimer = DbRequete.Activity.COLUMN_ID + " = ?";
        String[] ouArgument = new String[] {
            String.valueOf(id)
        };
        int nbLigne = db.delete(DbRequete.Activity.TABLE_NAME, idAsupprimer, ouArgument);
        return nbLigne == 1;
    }
}
