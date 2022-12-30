package com.example.provedorapp.clases;

import androidx.annotation.NonNull;

public class Cliente {

    private int idTienda;
    private String nombreTienda;
    private String personaContacto;
    private String nombreFactura;
    private String dni;
    private String direccion;
    private int retencion;

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

    public String getNombreFactura() {
        return nombreFactura;
    }

    public void setNombreFactura(String nombreFactura) {
        this.nombreFactura = nombreFactura;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getRetencion() {
        return retencion;
    }

    public void setRetencion(int retencion) {
        this.retencion = retencion;
    }
}
