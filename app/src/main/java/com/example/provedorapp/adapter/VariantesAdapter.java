package com.example.provedorapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.provedorapp.R;
import com.example.provedorapp.clases.MiniProducto;
import com.example.provedorapp.clases.Variante;
import com.example.provedorapp.componentes.PrecioView;
import com.example.provedorapp.databinding.ItemMiniProductoBinding;
import com.example.provedorapp.databinding.ItemVarianteBinding;
import com.example.provedorapp.server.SQLQueries;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class VariantesAdapter extends ListAdapter<Variante
        , VariantesAdapter.VarianteView> {


    private final OnVarianteClick listener;
    private final Context context;

    public VariantesAdapter(Context context, OnVarianteClick listener) {
        super(Variante.itemCallback);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VarianteView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemVarianteBinding itemVarianteBinding = ItemVarianteBinding.inflate(layoutInflater, parent, false);
//        itemProductBinding.setProductoInterface(productoInterface);
        return new VarianteView(itemVarianteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VarianteView holder, int position) {
        Variante variante = getItem(position);

        holder.autoCtvVariante.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String cadena = editable.toString();

                variante.setVariante(cadena);

                holder.tvTituloVariante.setText("Variante " + cadena);
            }
        });

        holder.autoCtvPrecio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String cadena = editable.toString();
                double precio = Double.parseDouble(cadena);

                variante.setPrecio(precio);
            }
        });

        holder.autoCtvStock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String cadena = editable.toString();
                int stock = Integer.parseInt(cadena);

                variante.setStock(stock);
            }
        });

        holder.btnBorrarVariante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialogo = new AlertDialog
                        .Builder(context)
                        .setPositiveButton(context.getResources().getString(R.string.confirmar_afirmativo)
                                , new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        listener.onBorrarClick(holder.getBindingAdapterPosition(),variante);
                                    }
                                })
                        .setNegativeButton(context.getResources().getString(R.string.confirmar_negativo), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle(context.getResources().getString(R.string.confirmar_borrado))
                        .setMessage("¿Deseas eliminar la variante " + variante.getVariante() + " ?")
                        .create();

                dialogo.show();
            }
        });



    }



    // CLASES Y INTERFACES

    public class VarianteView extends RecyclerView.ViewHolder {
        private final int MAX_STOCK = 100;
        private final int MIN_STOCK = 1;

        private final TextView tvTituloVariante;
        private final AutoCompleteTextView autoCtvVariante;
        private final AutoCompleteTextView autoCtvPrecio;
        private final AutoCompleteTextView autoCtvStock;
        private final ImageButton btnBorrarVariante;


        private ArrayAdapter<Integer> numerosAdapter;

        public VarianteView(@NonNull ItemVarianteBinding binding) {
            super(binding.getRoot());
            tvTituloVariante = binding.tvTituloVariante;
            autoCtvVariante = binding.autoCtvVariante;
            autoCtvPrecio = binding.autoCtvPrecio;
            autoCtvStock = binding.autoCtvStockIP;
            btnBorrarVariante = binding.btnBorrarVariante;

            inicializarCantidad();

        }

        private void inicializarCantidad(){
            numerosAdapter = new ArrayAdapter<>(context
                    , R.layout.spinner_item_cantidad_stock,  numerosLlenado());
            autoCtvStock.setAdapter(numerosAdapter);
        }

        private ArrayList<Integer> numerosLlenado(){
            ArrayList<Integer> numerosList = new ArrayList<>();

            for (int i = MIN_STOCK; i <= MAX_STOCK; i++) {
                numerosList.add(i);
            }

            return numerosList;
        }
    }

    public interface OnVarianteClick{
          void onBorrarClick(int posicion, Variante variante);
    }


}
