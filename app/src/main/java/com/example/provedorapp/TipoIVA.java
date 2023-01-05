package com.example.provedorapp;

import androidx.annotation.NonNull;

public enum TipoIVA {

    SUPER_REDUCIDO("Super Reducido","SR"),
    REDUCIDO("Reducido","R"),GENERAL("General","G");

    private String nombre;
    private String ivaSmp;

    TipoIVA(String nombre, String ivaSmp) {
        this.nombre = nombre;
        this.ivaSmp = ivaSmp;
    }

    TipoIVA(String iva) {
        this.ivaSmp = iva;
    }



    public String getIvaSmp() {
        return ivaSmp;
    }

    public void setIvaSmp(String ivaSmp) {
        this.ivaSmp = ivaSmp;
    }

    @NonNull
    @Override
    public String toString() {
        return nombre;
    }
}
