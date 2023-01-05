package com.example.provedorapp.ui.productos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.provedorapp.R;
import com.example.provedorapp.server.SQLite;
import com.example.provedorapp.adapter.CategoriasAdapter;
import com.example.provedorapp.adapter.ItemProductoAdapter;
import com.example.provedorapp.clases.Categoria;
import com.example.provedorapp.clases.ItemProducto;
import com.example.provedorapp.databinding.FragmentProductosBinding;
import com.example.provedorapp.ui.gestionProducto.GestionProductos;
import com.example.provedorapp.ui.gestionStock.GestionStock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ProductosFragment extends Fragment implements ItemProductoAdapter.OnProductoClick,
        CategoriasAdapter.OnCategoriaClick {

    private ItemTouchHelper itemTouchHelper;
    private ArrayList<ItemProducto> productos;
    private FragmentProductosBinding binding;
    private ItemProductoAdapter itemProductoAdapter;
    private FloatingActionButton fabAgregarProducto;
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

        fabAgregarProducto = binding.fabProductos;
        recyclerProductos = binding.recyclerProductos;
        recyclerProductos.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        itemProductoAdapter = new ItemProductoAdapter(getActivity(), this);
        itemProductoAdapter.submitList(productos);
        recyclerProductos.setAdapter(itemProductoAdapter);


        ArrayList<Categoria> categorias = getCategorias();
        categorias.add(new Categoria(-1, "Todos", "https://imgur.com/L8eF4Tm.png"));
        recyclerCategorias = binding.recyclerCategorias;
        recyclerCategorias.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
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

                if (!cadena.isEmpty()) {
                    filtrar(cadena);
                } else {
                    filtrarCategoria(categoria);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fabAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GestionProductos.class);
                getIntentActivityResultLauncher.launch(intent);
            }
        });
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerProductos);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ArrayList<ItemProducto> getProductos() {

        ArrayList<ItemProducto> miniProductos = new ArrayList<>();

        SQLite sqLite = new SQLite(getActivity());

        SQLiteDatabase db = sqLite.getWritableDatabase();

        Cursor cursorProducto = db.rawQuery("SELECT productos.idProducto, valorVariante, nombreProducto," +
                " idCategoria, precio, precioDescuento, stock, imgPath  " +
                "FROM productos INNER JOIN variantes ON productos.idProducto = variantes.idProducto " +
                "ORDER BY  stock DESC,productos.idProducto, valorVariante ", null);

        while (cursorProducto.moveToNext()) {
            ItemProducto miniProducto = new ItemProducto();

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

    public void filtrar(String filtro) {

        filtro = filtro.toLowerCase(Locale.ROOT);

        ArrayList<ItemProducto> productosFiltrados = new ArrayList<>();

        for (ItemProducto mp : getProductos()) {
            String[] nombre = mp.getNombre().split("\\s");
            int i = 0;
            boolean valido = false;

            do {
                String palabra = nombre[i].toLowerCase(Locale.ROOT);

                if (palabra.startsWith(filtro)) {
                    productosFiltrados.add(mp);
                    valido = true;
                }
                i++;
            } while (i < nombre.length && !valido);


        }
        itemProductoAdapter.submitList(productosFiltrados);
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

    public void filtrarCategoria(int cat) {
        if (cat == -1) {
            itemProductoAdapter.submitList(getProductos());
        } else {
            ArrayList<ItemProducto> productosFiltrados = new ArrayList<>();

            for (ItemProducto mp : getProductos()) {
                if (mp.getCategoria() == cat) {
                    productosFiltrados.add(mp);
                }
            }

            itemProductoAdapter.submitList(productosFiltrados);
        }

    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            float myDx = 0;

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(),R.color.red))
//                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(), R.color.celesteClaro))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_archive_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return super.getSwipeDirs(recyclerView, viewHolder);
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int posicion = viewHolder.getLayoutPosition();
            ItemProducto productoBorrado = productos.get(posicion);

            switch (direction) {
                case ItemTouchHelper.LEFT:

                    productos.remove(posicion);
                    itemProductoAdapter.notifyItemRemoved(posicion);

                    deshacer(posicion, productoBorrado);

////                    Snackbar snackbar = Snackbar.make(binding.fragmentCl,"Producto Eliminado",
////                            BaseTransientBottomBar.LENGTH_SHORT)
////                            .setAction("Deshacer", new View.OnClickListener() {
////                                @Override
////                                public void onClick(View view) {
////                                    deshacer(posicion,productoBorrado);
////                                    Snackbar.make(binding.fragmentCl,
////                                            "El producto se ha vuelto a agregar",
////                                            BaseTransientBottomBar.LENGTH_SHORT);
////                                }
////                            });
////
////                    snackbar.show();
                    break;
                case ItemTouchHelper.RIGHT:
                    Snackbar.make(binding.fragmentCl, "Producto agregado al pedido",
                            BaseTransientBottomBar.LENGTH_SHORT).show();

                    productos.remove(posicion);
                    itemProductoAdapter.notifyItemRemoved(posicion);

                    deshacer(posicion, productoBorrado);

                    break;
            }


        }

        public void deshacer(int posicion, ItemProducto productoBorrado) {
            productos.add(posicion, productoBorrado);
            itemProductoAdapter.notifyItemInserted(posicion);
        }
    };

    @Override
    public boolean onAgregarPedido(ItemProducto producto) {
        return false;
    }

    @Override
    public void onVerProducto(ItemProducto producto) {

    }

    @Override
    public void onModificar(ItemProducto producto) {
        Intent intent = new Intent(getActivity(), GestionProductos.class);
        intent.putExtra("idProducto",producto.getId());
        getIntentActivityResultLauncher.launch(intent);
    }

    @Override
    public void onStockClick(ItemProducto producto) {
        Intent intent = new Intent(getActivity(), GestionStock.class);
        intent.putExtra("idProducto", producto.getId());
        intent.putExtra("valorVariante", producto.getVariacion());
        getsActivityForResultLauncherActualizarProductos.launch(intent);
    }

    @Override
    public void OnCategoria(Categoria categoria) {
        if (this.categoria != categoria.getId()) {
            filtrarCategoria(categoria.getId());
            this.categoria = categoria.getId();
        }
    }

    ActivityResultLauncher<Intent> getsActivityForResultLauncherActualizarProductos = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 1) {
                        productos.clear();
                        productos = getProductos();
                        itemProductoAdapter.submitList(productos);
                        recyclerProductos.setAdapter(itemProductoAdapter);
                        filtrarCategoria(categoria);
                        String cadena = etxBuscador.getText().toString();
                        filtrar(cadena);

                    }
                }
            }
    );

    ActivityResultLauncher<Intent> getIntentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(result.getResultCode() == -1){
                        productos.clear();
                        productos = getProductos();
                        itemProductoAdapter.submitList(productos);
                        recyclerProductos.setAdapter(itemProductoAdapter);
                        filtrarCategoria(categoria);
                        String cadena = etxBuscador.getText().toString();
                        filtrar(cadena);
                    }


                }
            });
}