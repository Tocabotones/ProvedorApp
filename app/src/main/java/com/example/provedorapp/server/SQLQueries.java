package com.example.provedorapp.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.provedorapp.clases.Categoria;
import com.example.provedorapp.clases.Cliente;
import com.example.provedorapp.clases.DatosFactura;
import com.example.provedorapp.clases.ItemProducto;
import com.example.provedorapp.clases.Producto;
import com.example.provedorapp.clases.Variante;

import java.util.ArrayList;

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

    public static ArrayList<Categoria> getCategorias(Context context){
        ArrayList<Categoria> categorias = new ArrayList<>();

        SQLite sqLite = new SQLite(context);

        SQLiteDatabase db = sqLite.getWritableDatabase();

        Cursor cursorProducto = db.rawQuery("SELECT idCategoria, nombreCategoria," +
                " imgPath FROM categorias", null);

        while (cursorProducto.moveToNext()) {
            Categoria categoria = new Categoria();

            categoria.setId(cursorProducto.getInt(0));
            categoria.setCategoria(cursorProducto.getString(1));
            categoria.setImg(cursorProducto.getString(2));

            categorias.add(categoria);
        }
        return categorias;

    }

    public static Producto getProducto(int idProducto, Context context){
        Producto p = new Producto();
        ArrayList<Variante> variantes = new ArrayList<>();

        SQLite helper = new SQLite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT nombreProducto, idCategoria," +
                " tipoIVA FROM productos WHERE idProducto='" + idProducto + "'",null);

        if(cursor.moveToNext()){
            p.setId(idProducto);
            p.setNombre(cursor.getString(0));
            p.setCategoria(cursor.getInt(1));
            p.setTipoIva(cursor.getString(2));

            cursor = db.rawQuery("SELECT valorVariante, precio," +
                    " stock, imgPath FROM variantes WHERE idProducto='" + idProducto + "'",null);

            while (cursor.moveToNext()){
                Variante variante = new Variante();

                variante.setVariante(cursor.getString(0));
                variante.setPrecio(cursor.getDouble(1));
                variante.setStock(cursor.getInt(2));
                variante.setImg(cursor.getString(3));

                variantes.add(variante);
            }

            p.setVariantes(variantes);
        }



        db.close();
        helper.close();
        return p;
    }

    public static void insertarProducto(Producto p, Context context){
        ArrayList<Variante> variantes = p.getVariantes();

        SQLite helper = new SQLite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("nombreProducto",p.getNombre());
        cv.put("idCategoria",p.getCategoria());
        cv.put("tipoIVA",p.getTipoIva());

        long idProducto = db.insert("productos",null,cv);

        if (idProducto > 0){

                for(Variante v : variantes){

                    ContentValues cvVariantes = new ContentValues();
                    cvVariantes.put("idProducto", idProducto);
                    cvVariantes.put("valorVariante",v.getVariante());
                    cvVariantes.put("precio",v.getPrecio());
                    cvVariantes.put("stock",v.getStock());
                    cvVariantes.put("imgPath",v.getImg());

                    db.insert("variantes",null,cvVariantes);

                }
            }

        db.close();
        helper.close();
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

//    public static void reInsertarProducto(ItemProducto itemProducto,Context context){
//        SQLite helper = new SQLite(context);
//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put("nombreProducto",itemProducto.getNombre());
//        cv.put("idCategoria",itemProducto.getCategoria());
//        cv.put("tipoIVA",itemProducto.);
//
//        long idProducto = db.insert("productos",null,cv);
//
//        if (idProducto > 0){
//
//
//
//                ContentValues cvVariantes = new ContentValues();
//                cvVariantes.put("idProducto", idProducto);
//                cvVariantes.put("valorVariante",itemProducto.getVariacion());
//                cvVariantes.put("precio",itemProducto.getPrecio());
//                cvVariantes.put("stock",itemProducto.getStock());
//                cvVariantes.put("imgPath",itemProducto.getImg());
//
//                db.insert("variantes",null,cvVariantes);
//
//        }
//
//        db.close();
//        helper.close();
//    }

    public static void borrarCliente(int idCliente,Context context){

        SQLite helper = new SQLite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String args [] = {String.valueOf(idCliente)};

        db.delete("clientes","idCliente=?",args);

        db.close();
        helper.close();

    }

    public static void actualizarProducto(Producto p, Context context){

        ArrayList<Variante> variantes = p.getVariantes();

        SQLite helper = new SQLite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String argsProducto[] = {String.valueOf(p.getId())};

        ContentValues cvProducto = new ContentValues();
        cvProducto.put("nombreProducto",p.getNombre());
        cvProducto.put("idCategoria",p.getCategoria());
        cvProducto.put("tipoIVA",p.getTipoIva());

        int rowsProducto = db.update("productos",cvProducto,"idProducto=?",argsProducto);

//        for(Variante v : variantes){
//
//            String argsVariante[] = {String.valueOf(p.getId()), v.getVariante()};
//            ContentValues cvVariante = new ContentValues();
//            cvVariante.put("valorVariante",v.getVariante());
//            cvVariante.put("precio",v.getPrecio());
//            cvVariante.put("stock",v.getStock());
//            cvVariante.put("imgPath",v.getImg());
//
//            int rowsVariante = db.update("variantes",cvVariante,"idProducto=? AND valorVariante=?",argsVariante);
//        }

        db.delete("variantes","idProducto=?",argsProducto);

        for(Variante v : variantes){

            ContentValues cvVariantes = new ContentValues();
            cvVariantes.put("idProducto", p.getId());
            cvVariantes.put("valorVariante",v.getVariante());
            cvVariantes.put("precio",v.getPrecio());
            cvVariantes.put("stock",v.getStock());
            cvVariantes.put("imgPath",v.getImg());

            db.insert("variantes",null,cvVariantes);

        }

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

    public static void eliminarProducto(ItemProducto itemProducto,Context context){
        SQLite helper = new SQLite(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String argsVariantes[] = {String.valueOf(itemProducto.getId()), itemProducto.getVariacion()};
        db.delete("variates","idProducto=? AND valorVariante=?",argsVariantes);

        String argsProducto[] = {String.valueOf(itemProducto.getId())};
        db.delete("productos","idProducto=?",argsProducto);

        db.close();
        helper.close();
    }


}
