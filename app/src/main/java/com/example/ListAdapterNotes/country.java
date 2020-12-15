package com.example.ListAdapterNotes;

public class country {
    private int flagRes;
    private String countryName;

    public country (int flagRes , String countryName){
        this.flagRes=flagRes;
        this.countryName=countryName;
    }
    public int getFlagRes() {
        return flagRes;
    }

    public void setFlagRes(int flagRes) {
        this.flagRes = flagRes;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
