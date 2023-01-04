package com.example.provedorapp.ui.gestionProducto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.provedorapp.R;
import com.example.provedorapp.adapter.VariantesAdapter;
import com.example.provedorapp.clases.Variante;
import com.example.provedorapp.databinding.ActivityGestionClientesBinding;
import com.example.provedorapp.databinding.ActivityGestionProductosBinding;

import java.util.ArrayList;

public class GestionProductos extends AppCompatActivity implements VariantesAdapter.OnVarianteClick {

    private  ActivityGestionProductosBinding binding;
    private  TextView tvTituloOperacionGP;
    private  AutoCompleteTextView autoCtvNomProductoGP;
    private  AutoCompleteTextView autoCtvCategoriaGP;
    private  AutoCompleteTextView autoCtvTipoIVAGP;
    private  Button btnAgregarProductoGP;
    private  Button btnCancelar;
    private ImageButton btnAgregarVarianteGP;

    private  RecyclerView recyclerVariantes;
    private ArrayList<Variante> variantes;
    private VariantesAdapter variantesAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGestionProductosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initUI();


    }

    private void initUI() {

        tvTituloOperacionGP = binding.tvTituloOperacionProductos;
        autoCtvNomProductoGP = binding.autoCtvNomProductoGP;
        autoCtvCategoriaGP = binding.autoCtvCategoriaGP;
        autoCtvTipoIVAGP = binding.autoCtvTipoIVAGP;
        btnAgregarProductoGP = binding.btnAgregarProductoGP;
        btnCancelar = binding.btnCancelarGP;
        btnAgregarVarianteGP = binding.btnAgregarVarianteGP;
        recyclerVariantes = binding.recyclerVariantes;

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerVariantes.setLayoutManager(layoutManager);
        variantesAdapter = new VariantesAdapter(this,this);
        recyclerVariantes.setAdapter(variantesAdapter);

        btnAgregarVarianteGP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                variantes.add(new Variante());
                variantesAdapter.submitList(variantes);
                recyclerVariantes.setAdapter(variantesAdapter);
            }
        });

        initAgregarUI();
    }

    public void initAgregarUI(){

        variantes = new ArrayList<>();
        variantesAdapter.submitList(variantes);

    }

    @Override
    public void onBorrarClick(int posicion, Variante variante) {
            variantes.remove(posicion);
            variantesAdapter.notifyItemRemoved(posicion);
    }
}