package com.example.provedorapp.clases;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ItemProducto {

    private int id;
    private String variacion;
    private String nombre;
    private double precio;
    private Double precioDescuento;
    private int stock;
    private String img;
    private int categoria;


    //GETTER & SETTERS


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getVariacion() {
        return variacion;
    }

    public void setVariacion(String variacion) {
        this.variacion = variacion;
    }

    public void setPrecioDescuento(Double precioDescuento) {
        this.precioDescuento = precioDescuento;
    }

    public int getCategoria() {
        return categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Double getPrecioDescuento() {
        return precioDescuento;
    }

    public void setPrecioDescuento(double precioDescuento) {
        this.precioDescuento = precioDescuento;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static DiffUtil.ItemCallback<ItemProducto> itemCallback = new DiffUtil.ItemCallback<ItemProducto>() {
        @Override
        public boolean areItemsTheSame(@NonNull ItemProducto oldItem, @NonNull ItemProducto newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ItemProducto oldItem, @NonNull ItemProducto newItem) {
            return false;
        }
    };
}
