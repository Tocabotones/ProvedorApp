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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.provedorapp.R;
import com.example.provedorapp.clases.Variante;
import com.example.provedorapp.databinding.ItemVarianteBinding;

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

        if (!variante.getVariante().isEmpty()) {
            holder.autoCtvVariante.setText(variante.getVariante());
        }

        holder.autoCtvPrecio.setText(String.valueOf(variante.getPrecio()));
        holder.autoCtvStock.setText(String.valueOf(variante.getStock()),false);

        holder.autoCtvImgPath.setText(variante.getImg());

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

                boolean escribiendo = false;
                if (!escribiendo) {
                    String cadena = editable.toString();
                    double precio;
                    if (cadena.isEmpty()) {
                        precio = 0.0;
                        variante.setPrecio(precio);
                    } else {
                        precio = Double.parseDouble(cadena);
                        variante.setPrecio(precio);
                    }
                }


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

        holder.autoCtvImgPath.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String cadena = editable.toString();
                variante.setImg(cadena);
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
                                        listener.onBorrarClick(holder.getBindingAdapterPosition(), variante);
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
        private final int MIN_STOCK = 0;

        private final TextView tvTituloVariante;
        private final AutoCompleteTextView autoCtvVariante;
        private final AutoCompleteTextView autoCtvPrecio;
        private final AutoCompleteTextView autoCtvStock;
        private final AutoCompleteTextView autoCtvImgPath;
        private final ImageButton btnBorrarVariante;


        private ArrayAdapter<Integer> numerosAdapter;

        public VarianteView(@NonNull ItemVarianteBinding binding) {
            super(binding.getRoot());
            tvTituloVariante = binding.tvTituloVariante;
            autoCtvVariante = binding.autoCtvVariante;
            autoCtvPrecio = binding.autoCtvPrecio;
            autoCtvStock = binding.autoCtvStockIP;
            autoCtvImgPath = binding.autoCtvImgPathGP;
            btnBorrarVariante = binding.btnBorrarVariante;


            inicializarCantidad();

        }

        private void inicializarCantidad() {
            numerosAdapter = new ArrayAdapter<>(context
                    , R.layout.spinner_item_cantidad_stock, numerosLlenado());
            autoCtvStock.setAdapter(numerosAdapter);
        }

        private ArrayList<Integer> numerosLlenado() {
            ArrayList<Integer> numerosList = new ArrayList<>();

            for (int i = MIN_STOCK; i <= MAX_STOCK; i++) {
                numerosList.add(i);
            }

            return numerosList;
        }
    }

    public ArrayList<Variante> getList() {
        ArrayList<Variante> variantes = new ArrayList<>();

        for (int i = 0; i < getItemCount(); i++) {
            variantes.add(getItem(i));
        }

        return variantes;
    }

    public interface OnVarianteClick {
        void onBorrarClick(int posicion, Variante variante);
    }


}
