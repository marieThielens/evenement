package com.example.labo.bd;

public class DbRequete {
    public static final String DB_NAME = "Activity"; // le nom de la db
    public static final int DB_VERSION = 1; // la version de la db

    public static class Activity {
        // La classe static qui contient les infos de la table (le nom des colonnes .. )
        public static final String TABLE_NAME = "activity"; // nom du tableau
        public static final String COLUMN_ID = "_id"; // nom des colonnes
        public static final String COLUMN_TITRE = "titre";
        public static final String COLUMN_DATE = "date";


        // requêtes DDL pour créer le tableau et ses colonnes
        public static final String MES_REQUETES =
                "CREATE TABLE " + Activity .TABLE_NAME + " ( "
                        + Activity .COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + Activity .COLUMN_TITRE + " TEXT, "
                        + Activity .COLUMN_DATE + " TEXT "
                        + ");";
        // insert
        public static final String INSERT = "INSERT INTO" + TABLE_NAME + " (" + COLUMN_TITRE + ") VALUES (?) ;";
        // delete
        public static final String REQUEST_DELETE = "DROP TABLE " + Activity .TABLE_NAME + ";";


    }
}
