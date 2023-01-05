package com.example.provedorapp.clases;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Locale;

public class Variante {

    private String variante;
    private double precio;
    private int stock;
    private Double precioDescuento;
    private String img;

    public Variante() {
        variante = "";
        stock = -1;
        img = "";
        precio = 0.0;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        Variante v2 = (Variante) obj;
        String v1Str = variante.replace(" ","");
        String v2Str = v2.getVariante().replace(" ","");
        return v1Str.toLowerCase(Locale.ROOT).equals(v2Str.toLowerCase(Locale.ROOT));
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

    public Double getPrecioDescuento() {
        return precioDescuento;
    }

    public void setPrecioDescuento(Double precioDescuento) {
        this.precioDescuento = precioDescuento;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
