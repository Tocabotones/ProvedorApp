package com.example.provedorapp.server;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.provedorapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
                ", nombreContacto TEXT, nombreTienda TEXT, nombreFactura TEXT, dniPropietario TEXT" +
                ", direccion TEXT, retencion Integer)");

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

        importarCategorias(db);
        importarProductos(db);
        importarVariantes(db);
        importarClientes(db);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    public void importarCategorias(SQLiteDatabase db){

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

    public void importarProductos(SQLiteDatabase db){

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

    public void importarVariantes(SQLiteDatabase db){

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

    public void importarClientes(SQLiteDatabase db){

        InputStream is =  context.getResources().openRawResource(R.raw.clientes);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

        try {
            String fila;
            while ((fila = buffer.readLine()) != null){
                String [] columnas = fila.split(";");
                ContentValues cv = new ContentValues();
                cv.put("idCliente",columnas[0]);
                cv.put("nombreContacto",columnas[1]);
                cv.put("nombreTienda",columnas[2]);
                cv.put("nombreFactura",columnas[3]);
                cv.put("dniPropietario",columnas[4]);
                cv.put("direccion",columnas[5]);
                cv.put("retencion",columnas[6]);
                db.insert("clientes",null,cv);
            }
            buffer.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
