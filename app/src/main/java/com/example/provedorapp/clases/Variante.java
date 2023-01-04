package com.example.provedorapp.clases;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Variante {

    private String variante;
    private double precio;
    private int stock;

    public Variante() {
    }


    public String getVariante() {
        return variante;
    }

    public void setVariante(String variante) {
        this.variante = variante;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public static DiffUtil.ItemCallback<Variante> itemCallback = new DiffUtil.ItemCallback<Variante>() {
        // TO FINISH
        @Override
        public boolean areItemsTheSame(@NonNull Variante oldItem, @NonNull Variante newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Variante oldItem, @NonNull Variante newItem) {
            return false;
        }
    };
}
