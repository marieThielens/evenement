package com.example.labo.models;

import java.time.LocalDate;

public class Activity {

    private long id;
    private String titreTache;
    private LocalDate dateCreation;
    private boolean estFini;

    public Activity( String titreTache, LocalDate dateCreation) {
        this.id = 0;
        this.titreTache = titreTache;
        this.dateCreation = dateCreation;
    }
    public Activity(long id, String titreTache, LocalDate dateCreation) {
        this(titreTache,dateCreation);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitreTache() {
        return titreTache;
    }

    public void setTitreTache(String titreTache) {
        this.titreTache = titreTache;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
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
