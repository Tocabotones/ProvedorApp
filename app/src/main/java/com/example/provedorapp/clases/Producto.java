package com.example.provedorapp.clases;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Producto {

    private int id;
    private String nombre;
    private int categoria;
    private String tipoIva;
    private ArrayList<Variante> variantes;


    public Producto() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public void setTipoIva(String tipoIva) {
        this.tipoIva = tipoIva;
    }

    public void setVariantes(ArrayList<Variante> variantes) {
        this.variantes = variantes;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCategoria() {
        return categoria;
    }

    public String getTipoIva() {
        return tipoIva;
    }

    public ArrayList<Variante> getVariantes() {
        return variantes;
    }
}
