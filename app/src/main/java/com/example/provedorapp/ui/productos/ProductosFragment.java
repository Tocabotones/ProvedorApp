package com.example.provedorapp.ui.productos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.provedorapp.R;
import com.example.provedorapp.SQLite;
import com.example.provedorapp.adapter.CategoriasAdapter;
import com.example.provedorapp.adapter.MiniProductosAdapter;
import com.example.provedorapp.clases.Categoria;
import com.example.provedorapp.clases.MiniProducto;
import com.example.provedorapp.databinding.FragmentProductosBinding;

import java.util.ArrayList;
import java.util.Locale;

public class ProductosFragment extends Fragment implements MiniProductosAdapter.OnProductoClick,
        CategoriasAdapter.OnCategoriaClick {

    private ArrayList<MiniProducto> productos;
    private FragmentProductosBinding binding;
    private MiniProductosAdapter miniProductosAdapter;
    private RecyclerView recyclerProductos;

    private CategoriasAdapter categoriasAdapter;
    private RecyclerView recyclerCategorias;

    private EditText etxBuscador;

    private Integer categoria;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProductosViewModel productosViewModel =
                new ViewModelProvider(this).get(ProductosViewModel.class);

        binding = FragmentProductosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

//        if (getActivity() != null){
//            System.out.println("TOCABOTONES " + getActivity() );
//        }

        return root;
    }

    private void init() {

        categoria = -1;
        productos = getProductos();

        recyclerProductos = binding.recyclerProductos;
        recyclerProductos.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        miniProductosAdapter = new MiniProductosAdapter(getActivity(), this);
        miniProductosAdapter.submitList(productos);
        recyclerProductos.setAdapter(miniProductosAdapter);


        ArrayList<Categoria> categorias = getCategorias();
        categorias.add(new Categoria(-1,"Todos","https://imgur.com/L8eF4Tm.png"));
        recyclerCategorias = binding.recyclerCategorias;
        recyclerCategorias.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        categoriasAdapter = new CategoriasAdapter(getActivity(), this);
        categoriasAdapter.submitList(categorias);
        recyclerCategorias.setAdapter(categoriasAdapter);

        etxBuscador = binding.etxBuscador;

        etxBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String cadena = String.valueOf(charSequence);

                if (!cadena.isEmpty()){
                    filtrar(cadena);
                } else {
                    filtrarCategoria(categoria);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ArrayList<MiniProducto> getProductos() {

        ArrayList<MiniProducto> miniProductos = new ArrayList<>();

        SQLite sqLite = new SQLite(getActivity());

        SQLiteDatabase db = sqLite.getWritableDatabase();

        Cursor cursorProducto = db.rawQuery("SELECT productos.idProducto, valorVariante, nombreProducto," +
                " idCategoria, precio, precioDescuento, stock, imgPath  " +
                "FROM productos INNER JOIN variantes ON productos.idProducto = variantes.idProducto " +
                "ORDER BY productos.idProducto, valorVariante", null);

        while (cursorProducto.moveToNext()) {
            MiniProducto miniProducto = new MiniProducto();

            miniProducto.setId(cursorProducto.getInt(0));
            miniProducto.setVariacion(cursorProducto.getString(1));
            miniProducto.setNombre(cursorProducto.getString(2));
            miniProducto.setCategoria(cursorProducto.getInt(3));
            miniProducto.setPrecio(cursorProducto.getDouble(4));
            miniProducto.setPrecioDescuento(cursorProducto.getDouble(5));
            miniProducto.setStock(cursorProducto.getInt(6));
            miniProducto.setImg(cursorProducto.getString(7));

            miniProductos.add(miniProducto);
        }
        return miniProductos;
    }

    public void filtrar(String filtro){

        filtro = filtro.toLowerCase(Locale.ROOT);

        ArrayList<MiniProducto> productosFiltrados = new ArrayList<>();

        for (MiniProducto mp : miniProductosAdapter.getCurrentList()){
            String [] nombre = mp.getNombre().split("\\s");
            int i = 0;
            boolean valido = false;

            do {
                String palabra = nombre[i].toLowerCase(Locale.ROOT);

                if (palabra.startsWith(filtro)){
                    productosFiltrados.add(mp);
                    valido = true;
                }
                i++;
            } while (i < nombre.length && !valido);


        }

        miniProductosAdapter.submitList(productosFiltrados);
    }
    public ArrayList<Categoria> getCategorias() {

        ArrayList<Categoria> categorias = new ArrayList<>();

        SQLite sqLite = new SQLite(getActivity());

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

    public void filtrarCategoria(int cat){
        if (categoria == -1){
            miniProductosAdapter.submitList(productos);
        } else {
            ArrayList<MiniProducto> productosFiltrados = new ArrayList<>();

            for (MiniProducto mp : this.productos){
                if (mp.getCategoria() == cat) {
                    productosFiltrados.add(mp);
                }
            }

            miniProductosAdapter.submitList(productosFiltrados);
        }

    }
    @Override
    public boolean onAgregarPedido(MiniProducto producto) {
        return false;
    }

    @Override
    public void onVerProducto(MiniProducto producto) {

    }

    @Override
    public void OnCategoria(Categoria categoria) {
        filtrarCategoria(categoria.getId());
        this.categoria = categoria.getId();
    }
}