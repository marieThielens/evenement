package com.example.labo.models;

public class Activity {

    private int id;
    private String titreTache;
    private String dateCreation;
    private boolean estFini;

    public Activity(int id, String titreTache, String dateCreation, boolean estFini) {
        this.id = id;
        this.titreTache = titreTache;
        this.dateCreation = dateCreation;
        this.estFini = estFini;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitreTache() {
        return titreTache;
    }

    public void setTitreTache(String titreTache) {
        this.titreTache = titreTache;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean isEstFini() {
        return estFini;
    }

    public void setEstFini(boolean estFini) {
        this.estFini = estFini;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", titreTache='" + titreTache + '\'' +
                ", dateCreation='" + dateCreation + '\'' +
                ", estFini=" + estFini +
                '}';
    }
}
