package com.example.provedorapp.ui.gestionStock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.provedorapp.R;
import com.example.provedorapp.server.SQLite;
import com.example.provedorapp.clases.MiniProducto;
import com.example.provedorapp.databinding.ActivityGestionStockBinding;

import java.util.ArrayList;

public class GestionStock extends AppCompatActivity {

    private final int MAX_NUMEROS_STOCK = 50;
    private final int RESULT_OK = 1;
    private ActivityGestionStockBinding binding;
    private TextView tvNombreProducto;
    private TextView tvPresentacion;
    private TextView tvStock;
    private Button btnAgregar;
    private Button btnDisminuir;
    private AutoCompleteTextView autoCtvStock;


    private ArrayAdapter<Integer> numerosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGestionStockBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setResult(RESULT_CANCELED);


        init();
    }

    private void init() {
        tvNombreProducto = binding.tvNombreProductoGestion;
        tvPresentacion = binding.tvVarianteGestion;
        tvStock = binding.tvStockGestion;
        autoCtvStock = binding.autoCtvStock;
        btnAgregar = binding.btnAgregarStock;
        btnDisminuir = binding.btnDisminuirStock;

        MiniProducto miniProducto;

        int idProducto = getIntent().getIntExtra("idProducto",0);
        String valorVariante = getIntent().getStringExtra("valorVariante");
        miniProducto = getProducto(idProducto,valorVariante);

        tvNombreProducto.setText(miniProducto.getNombre());
        tvPresentacion.setText(miniProducto.getVariacion());
        tvStock.setText(String.valueOf(miniProducto.getStock()));

        inicializarCantidad(miniProducto);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarStock(miniProducto);
            }
        });

        btnDisminuir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disminuirStock(miniProducto);
            }
        });
    }

    private void inicializarCantidad(MiniProducto producto){
        numerosAdapter = new ArrayAdapter<>(this
                , R.layout.spinner_item_cantidad_stock, numerosLlenado(producto
                ,MAX_NUMEROS_STOCK + producto.getStock()));
        autoCtvStock.setAdapter(numerosAdapter);
    }

    private ArrayList<Integer> numerosLlenado(MiniProducto producto,int numeros){
        ArrayList<Integer> numerosList = new ArrayList<>();

        for (int i = 0; i <= numeros; i++) {
            numerosList.add(i);
        }

        return numerosList;
    }

    public MiniProducto getProducto(Integer idProducto,String valorVariante) {

        MiniProducto miniProducto = null;

        SQLite sqLite = new SQLite(this);

        SQLiteDatabase db = sqLite.getWritableDatabase();


        Cursor cursorProducto = db.rawQuery("SELECT productos.idProducto, valorVariante, nombreProducto," +
                " idCategoria, precio, precioDescuento, stock, imgPath  " +
                "FROM productos INNER JOIN variantes ON productos.idProducto = variantes.idProducto " +
                "WHERE productos.idProducto = '" + idProducto
                + "' AND valorVariante = '" + valorVariante + "'", null);

        if (cursorProducto.moveToNext()) {
            miniProducto = new MiniProducto();

            miniProducto.setId(cursorProducto.getInt(0));
            miniProducto.setVariacion(cursorProducto.getString(1));
            miniProducto.setNombre(cursorProducto.getString(2));
            miniProducto.setCategoria(cursorProducto.getInt(3));
            miniProducto.setPrecio(cursorProducto.getDouble(4));
            miniProducto.setPrecioDescuento(cursorProducto.getDouble(5));
            miniProducto.setStock(cursorProducto.getInt(6));
            miniProducto.setImg(cursorProducto.getString(7));

        }

        db.close();
        sqLite.close();

        return miniProducto;
    }

    private void agregarStock(MiniProducto producto){
        SQLite helper = new SQLite(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String args [] = {String.valueOf(producto.getId()),producto.getVariacion()};

        int aumento = Integer.parseInt(autoCtvStock.getText().toString());
        ContentValues cv = new ContentValues();
        cv.put("stock",producto.getStock() + aumento);

        db.update("variantes",cv,"idProducto=? AND valorVariante=?",args);

        db.close();
        helper.close();

        setResult(RESULT_OK);

        finish();
    }

    private void disminuirStock(MiniProducto producto){
        SQLite helper = new SQLite(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String args [] = {String.valueOf(producto.getId()),producto.getVariacion()};

        int resta = Integer.parseInt(autoCtvStock.getText().toString());

        if (producto.getStock() - resta > 0){
            ContentValues cv = new ContentValues();
            cv.put("stock",producto.getStock() - resta);

            db.update("variantes",cv,"idProducto=? AND valorVariante=?",args);
            setResult(RESULT_OK);
        } else setResult(RESULT_CANCELED);

        db.close();
        helper.close();

        finish();
    }


}