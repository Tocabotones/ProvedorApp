package com.example.provedorapp.ui.gestionProducto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.provedorapp.R;
import com.example.provedorapp.TipoIVA;
import com.example.provedorapp.adapter.VariantesAdapter;
import com.example.provedorapp.clases.Categoria;
import com.example.provedorapp.clases.Producto;
import com.example.provedorapp.clases.Variante;
import com.example.provedorapp.databinding.ActivityGestionProductosBinding;
import com.example.provedorapp.server.SQLQueries;

import java.util.ArrayList;

public class GestionProductos extends AppCompatActivity implements VariantesAdapter.OnVarianteClick {


    private ActivityGestionProductosBinding binding;
    private TextView tvTituloOperacionGP;
    private AutoCompleteTextView autoCtvNomProductoGP;
    private AutoCompleteTextView autoCtvCategoriaGP;
    private AutoCompleteTextView autoCtvTipoIVAGP;
    private Button btnCancelar;
    private Button btnAgregarProducto;
    private ImageButton btnAgregarVarianteGP;

    private RecyclerView recyclerVariantes;
    private ArrayList<Variante> variantes;
    private VariantesAdapter variantesAdapter;

    ArrayAdapter<Categoria> categoriasAdapter;
    ArrayAdapter<TipoIVA> tipoIVAAdapter;

    private Categoria categoriaSeleccionada;
    private TipoIVA tipoIVASelecionado;

    private Integer idProductoModificar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGestionProductosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initUI();


    }

    private void initUI() {
        setResult(RESULT_CANCELED);
        tvTituloOperacionGP = binding.tvTituloOperacionProductos;
        autoCtvNomProductoGP = binding.autoCtvNomProductoGP;
        autoCtvCategoriaGP = binding.autoCtvCategoriaGP;
        autoCtvTipoIVAGP = binding.autoCtvTipoIVAGP;
        btnCancelar = binding.btnCancelarGP;
        btnAgregarProducto = binding.btnAgregarProductoGP;
        btnAgregarVarianteGP = binding.btnAgregarVarianteGP;
        recyclerVariantes = binding.recyclerVariantes;

        tipoIVASelecionado = null;
        categoriaSeleccionada =null;
        idProductoModificar = null;

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerVariantes.setLayoutManager(layoutManager);
        variantesAdapter = new VariantesAdapter(this, this);
        recyclerVariantes.setAdapter(variantesAdapter);

        btnAgregarVarianteGP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                variantes.add(new Variante());
                variantesAdapter.submitList(variantes);
                recyclerVariantes.setAdapter(variantesAdapter);
            }
        });

        if (getIntent().hasExtra("idProducto")){
            initModificarUI();
        } else {
            initAgregarUI();
        }

    }

    private void initModificarUI() {
        tvTituloOperacionGP.setText(getResources().getString(R.string.modificar_producto));
        inicializarCategoria();
        inicializarTipoIVA();
        idProductoModificar = getIntent().getIntExtra("idProducto",0);
        actualizarInfoProducto(idProductoModificar);

        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarOperacion();
            }
        });


    }

    public void initAgregarUI() {

        variantes = new ArrayList<>();
        variantesAdapter.submitList(variantes);


        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarOperacion();
            }
        });

        inicializarCategoria();
        inicializarTipoIVA();
    }


    private void inicializarCategoria() {
        categoriasAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_cantidad_stock, SQLQueries.getCategorias(this));
        autoCtvCategoriaGP.setAdapter(categoriasAdapter);
        autoCtvCategoriaGP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoriaSeleccionada = categoriasAdapter.getItem(i);
            }
        });
    }

    private void inicializarTipoIVA() {
        ArrayList<TipoIVA> tipoIVAS = new ArrayList<>();
        tipoIVAS.add(TipoIVA.REDUCIDO);
        tipoIVAS.add(TipoIVA.SUPER_REDUCIDO);
        tipoIVAS.add(TipoIVA.GENERAL);
        tipoIVAAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_cantidad_stock, tipoIVAS);

        autoCtvTipoIVAGP.setAdapter(tipoIVAAdapter);
        autoCtvTipoIVAGP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TipoIVA iva = tipoIVAAdapter.getItem(i);
                tipoIVASelecionado = iva;
            }
        });
    }

    @Override

    public void onBorrarClick(int posicion, Variante variante) {
        variantes.remove(posicion);
        variantesAdapter.notifyItemRemoved(posicion);
    }

    private boolean realizarOperacion() {

        boolean valido = true;
        Producto producto = new Producto();
        String nombreProducto = autoCtvNomProductoGP.getText().toString();

        if (nombreProducto.equals("")){
            valido = false;
        } else producto.setNombre(nombreProducto);
        if (categoriaSeleccionada == null) {
            valido = false;
//            autoCtvCategoriaGP.setError(getResources().getString(R.string.error_categoria));
        } else producto.setCategoria(categoriaSeleccionada.getId());


        if (tipoIVASelecionado == null) {
            valido = false;
            autoCtvTipoIVAGP.setError(getResources().getString(R.string.error_iva));
        } else producto.setTipoIva(tipoIVASelecionado.getIvaSmp());

        ArrayList<Variante> variantes = getVariantes();

        if (variantes == null){
            valido = false;
        } else {
            producto.setVariantes(variantes);
        }

        if (idProductoModificar == null){
            if (valido) {
                SQLQueries.insertarProducto(producto,this);
                setResult(RESULT_OK);
                finish();
            }
        } else {
            producto.setId(idProductoModificar);
            SQLQueries.actualizarProducto(producto,this);
            setResult(RESULT_OK);
            finish();
        }


        return valido;
    }

    private ArrayList<Variante> getVariantes(){
        ArrayList<Variante> variantes = variantesAdapter.getList();

        if (variantes.isEmpty()) return null;
        if (contieneRepetidos(variantes)) return null;

        for (Variante v : variantes){
            if (v.getVariante().equals("")) return null;
            if(v.getStock() == -1) return null;
        }

        return variantes;
    }

    private boolean contieneRepetidos(ArrayList<Variante> variantes) {
        boolean tieneRepetidos = false;
        int i = 0;
        do {
            Variante v1 = variantes.get(i);
            boolean repetido = false;

            int j = i + 1;

            while (!repetido && j < variantes.size()){
                Variante v2 = variantes.get(j);
                if (v1.equals(v2)){
                    repetido = true;
                    tieneRepetidos = true;
                }
                j++;
            }

            i++;
        } while (!tieneRepetidos && i < variantes.size());

        return tieneRepetidos;
    }

    private void actualizarInfoProducto(int idProducto){
        Producto producto = SQLQueries.getProducto(idProducto, this);
        autoCtvNomProductoGP.setText(producto.getNombre());

        int catIndex = categoriasAdapter.getPosition(new Categoria(producto.getCategoria()));
        Categoria cat = categoriasAdapter.getItem(catIndex);
        TipoIVA iva;
        switch (producto.getTipoIva()){
            case "R":
                iva = TipoIVA.REDUCIDO;
                break;
            case "SR":
                iva = TipoIVA.SUPER_REDUCIDO;
                break;
            default:
                iva = TipoIVA.GENERAL;
                break;
        }

        autoCtvCategoriaGP.setText(cat.getCategoria());
        autoCtvTipoIVAGP.setText(iva.toString());

        categoriaSeleccionada = cat;
        tipoIVASelecionado = iva;

        variantes = producto.getVariantes();
        variantesAdapter.submitList(variantes);


    }
}