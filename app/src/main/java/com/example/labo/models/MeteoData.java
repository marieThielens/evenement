package com.example.labo.models;

import android.os.AsyncTask;

public class MeteoData  {
    private String city;
    private double temp;

    public MeteoData(String city, double temp) {
        this.city = city;
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
