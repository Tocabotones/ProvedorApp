package com.example.provedorapp.clases;

import androidx.annotation.NonNull;

public class Cliente {

    private int idTienda;
    private String nombreTienda;
    private String personaContacto;
    private DatosFactura datosFactura;

    public Cliente() {
    }

    @NonNull
    @Override
    public String toString() {
        return nombreTienda;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public DatosFactura getDatosFactura() {
        return datosFactura;
    }

    public void setDatosFactura(DatosFactura datosFactura) {
        this.datosFactura = datosFactura;
    }
}
