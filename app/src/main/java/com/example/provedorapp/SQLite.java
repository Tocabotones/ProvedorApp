package com.example.provedorapp;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.FileUtils;

import androidx.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {

    static final String NOMBRE_ARCHIVO_BD = "provedor.db";
    static final int VERSION_BD = 1;
    Context context;

    public SQLite(@Nullable Context context) {
        super(context, NOMBRE_ARCHIVO_BD, null, VERSION_BD);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE categorias ( idCategoria INTEGER PRIMARY KEY AUTOINCREMENT  " +
                " ,nombreCategoria INTEGER NOT NULL, imgPath TEXT)");

        db.execSQL("CREATE TABLE clientes( idCliente INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                ", tienda INTEGER, nombreCompletoPr TEXT, dniPropietario INTEGER)");

        db.execSQL("CREATE TABLE pedidos (idPedido INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " idCliente INTEGER, retencion INTEGER NOT NULL," +
                " FOREIGN KEY(idCliente) REFERENCES clientes(idCliente))");

        db.execSQL("CREATE TABLE productos (idProducto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "nombreProducto TEXT NOT NULL,idCategoria INTEGER NOT NULL," +
                "tipoIVA INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE variantes (idProducto INTEGER NOT NULL,valorVariante TEXT NOT NULL, " +
                "precio REAL NOT NULL, precioDescuento REAL,stock INTEGER NOT NULL DEFAULT 0, " +
                "imgPath TEXT, PRIMARY KEY(idProducto,valorVariante)," +
                "FOREIGN KEY(idProducto) REFERENCES productos(idProducto))");

        db.execSQL("CREATE TABLE productos_pedido (idPedido INTEGER NOT NULL" +
                ", idProducto INTEGER NOT NULL, variante TEXT NOT NULL," +
                " precioVendido REAL NOT NULL, cantidad INTEGER NOT NULL," +
                " FOREIGN KEY(idProducto,variante) REFERENCES variantes(idProducto,valorVariante), " +
                "FOREIGN KEY(idPedido) REFERENCES pedidos(idPedido), " +
                "PRIMARY KEY (idPedido,idProducto,variante))");

        exportarCategorias(db);
        exportarProductos(db);
        exportarVariantes(db);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    public void exportarCategorias(SQLiteDatabase db){

        InputStream is =  context.getResources().openRawResource(R.raw.categorias);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

        try {
            String fila;
            while ((fila = buffer.readLine()) != null){
                String [] columnas = fila.split(";");
                ContentValues cv = new ContentValues();
                cv.put("idCategoria",columnas[0]);
                cv.put("nombreCategoria",columnas[1]);
                cv.put("imgPath",columnas[2]);
                db.insert("categorias",null,cv);
            }
            buffer.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportarProductos(SQLiteDatabase db){

        InputStream is =  context.getResources().openRawResource(R.raw.productos);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

        try {
            String fila;
            while ((fila = buffer.readLine()) != null){
                String [] columnas = fila.split(";");
                ContentValues cv = new ContentValues();
                cv.put("idProducto",columnas[0]);
                cv.put("nombreProducto",columnas[1]);
                cv.put("idCategoria",columnas[2]);
                cv.put("tipoIVA",columnas[3]);
                db.insert("productos",null,cv);
            }
            buffer.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportarVariantes(SQLiteDatabase db){

        InputStream is =  context.getResources().openRawResource(R.raw.variantes);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

        try {
            String fila;
            while ((fila = buffer.readLine()) != null){
                String [] columnas = fila.split(";");
                ContentValues cv = new ContentValues();
                cv.put("idProducto",columnas[0]);
                cv.put("valorVariante",columnas[1]);
                cv.put("precio",columnas[2]);
                cv.put("precioDescuento",columnas[3]);
                cv.put("stock",columnas[4]);
                cv.put("imgPath",columnas[5]);
                db.insert("variantes",null,cv);
            }
            buffer.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
