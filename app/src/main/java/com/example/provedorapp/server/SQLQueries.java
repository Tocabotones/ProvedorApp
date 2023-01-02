package com.example.provedorapp.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.provedorapp.clases.Cliente;
import com.example.provedorapp.clases.DatosFactura;

public class SQLQueries {

    public static Cliente getCliente(int idCliente, Context context){
        Cliente cliente = null;

        SQLite helper = new SQLite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT idCliente, nombreContacto," +
                " nombreTienda, nombreFactura, dniPropietario, direccion," +
                " retencion FROM clientes WHERE idCliente='" + idCliente + "'",null);

        if(cursor.moveToNext()){
            cliente = new Cliente();
            DatosFactura datosFactura = new DatosFactura();

            cliente.setIdTienda(cursor.getInt(0));
            cliente.setPersonaContacto(cursor.getString(1));
            cliente.setNombreTienda(cursor.getString(2));
            datosFactura.setNombreFactura(cursor.getString(3));
            datosFactura.setDni(cursor.getString(4));
            datosFactura.setDireccion(cursor.getString(5));
            datosFactura.setRetencion(cursor.getInt(6));
            cliente.setDatosFactura(datosFactura);

        }

        db.close();
        helper.close();

        return cliente;
    }

    public static void insertarCliente(Cliente c,Context context){

        DatosFactura datosFactura = c.getDatosFactura();

        SQLite helper = new SQLite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("nombreContacto",c.getPersonaContacto());
        cv.put("nombreTienda",c.getNombreTienda());
        cv.put("nombreFactura",datosFactura.getNombreFactura());
        cv.put("dniPropietario",datosFactura.getDni());
        cv.put("direccion",datosFactura.getDireccion());
        cv.put("retencion",datosFactura.getRetencion());

        db.insert("clientes",null,cv);

        db.close();
        helper.close();

    }

    public static void reInsertarCliente(Cliente c,Context context){

        DatosFactura datosFactura = c.getDatosFactura();

        SQLite helper = new SQLite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("idCliente",c.getIdTienda());
        cv.put("nombreContacto",c.getPersonaContacto());
        cv.put("nombreTienda",c.getNombreTienda());
        cv.put("nombreFactura",datosFactura.getNombreFactura());
        cv.put("dniPropietario",datosFactura.getDni());
        cv.put("direccion",datosFactura.getDireccion());
        cv.put("retencion",datosFactura.getRetencion());

        db.insert("clientes",null,cv);

        db.close();
        helper.close();

    }

    public static void borrarCliente(int idCliente,Context context){

        SQLite helper = new SQLite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String args [] = {String.valueOf(idCliente)};

        db.delete("clientes","idCliente=?",args);

        db.close();
        helper.close();

    }

    public static void actualizarCliente(Cliente c,Context context){

        DatosFactura datosFactura = c.getDatosFactura();

        SQLite helper = new SQLite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String args[] = {String.valueOf(c.getIdTienda())};

        ContentValues cv = new ContentValues();
        cv.put("nombreContacto",c.getPersonaContacto());
        cv.put("nombreTienda",c.getNombreTienda());
        cv.put("nombreFactura",datosFactura.getNombreFactura());
        cv.put("dniPropietario",datosFactura.getDni());
        cv.put("direccion",datosFactura.getDireccion());
        cv.put("retencion",datosFactura.getRetencion());

        int rows = db.update("clientes",cv,"idCliente=?",args);

        db.close();
        helper.close();

    }
}
