package com.example.provedorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.provedorapp.clases.Categoria;
import com.example.provedorapp.databinding.ItemCategoriaBinding;


public class CategoriasAdapter extends ListAdapter<Categoria
        , CategoriasAdapter.CategoriaView> {

    private OnCategoriaClick listener;
    private Context context;

    public CategoriasAdapter(Context context, OnCategoriaClick listener) {
        super(Categoria.itemCallback);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoriaView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCategoriaBinding itemMiniProductoBinding = ItemCategoriaBinding.inflate(layoutInflater, parent, false);
//        itemProductBinding.setProductoInterface(productoInterface);
        return new CategoriaView(itemMiniProductoBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaView holder, int position) {
        Categoria categoria = getItem(position);

        holder.tvCategoria.setText(categoria.getCategoria());
        Glide.with(context)
                .load(categoria.getImg())
                .into(holder.imgCategoria);

        holder.imgCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnCategoria(categoria);
            }
        });


    }

    // CLASES Y INTERFACES

    public class CategoriaView extends RecyclerView.ViewHolder {

        private ImageView imgCategoria;
        private TextView tvCategoria;

        public CategoriaView(@NonNull ItemCategoriaBinding binding) {
            super(binding.getRoot());
            imgCategoria = binding.imgCategoria;
            tvCategoria = binding.tvCategoria;
        }
    }

    public interface OnCategoriaClick {
        public void OnCategoria(Categoria categoria);
    }


}
