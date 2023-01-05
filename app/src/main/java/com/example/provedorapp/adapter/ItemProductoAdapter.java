package com.example.provedorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.provedorapp.R;
import com.example.provedorapp.clases.ItemProducto;
import com.example.provedorapp.componentes.PrecioView;
import com.example.provedorapp.databinding.ItemMiniProductoBinding;


public class ItemProductoAdapter extends ListAdapter<ItemProducto
        , ItemProductoAdapter.MiniProductoView> {

    private OnProductoClick listener;
    private Context context;

    public ItemProductoAdapter(Context context, OnProductoClick listener) {
        super(ItemProducto.itemCallback);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MiniProductoView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMiniProductoBinding itemMiniProductoBinding = ItemMiniProductoBinding.inflate(layoutInflater, parent, false);
//        itemProductBinding.setProductoInterface(productoInterface);
        return new MiniProductoView(itemMiniProductoBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniProductoView holder, int position) {
        ItemProducto itemProducto = getItem(position);


        holder.tvNombreProducto.setText(itemProducto.getNombre());
        holder.tvVariacion.setText(itemProducto.getVariacion());
        if(!holder.imgProducto.equals("")){
            Glide.with(context)
                    .load(itemProducto.getImg())
                    .into(holder.imgProducto);
        }



        if (itemProducto.getPrecioDescuento() == 0.0){
            holder.precioView.setPrecio(itemProducto.getPrecio(),24,
                    context.getResources().getColor(R.color.contrasteCeleste));
//            holder.frgPrecio.setPrecioDecoration(miniProducto.getPrecio(),24,
//                    context.getResources().getColor(R.color.black));
        }

        holder.tvStock.setText(String.valueOf(itemProducto.getStock()));
        if (itemProducto.getStock() == 0){
            holder.tvStock.setTextColor(context.getResources().getColor(R.color.producto_agotado));
        } else {
            holder.tvStock.setTextColor(context.getResources().getColor(R.color.contrasteCeleste));
        }
        holder.tvStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onStockClick(itemProducto);
            }
        });
        holder.imgBtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onModificar(itemProducto);
            }
        });


    }



    // CLASES Y INTERFACES

    public class MiniProductoView extends RecyclerView.ViewHolder {

        private ImageView imgProducto;
        private PrecioView precioView;
        private TextView tvVariacion;
        private TextView tvStock;
        private TextView tvNombreProducto;
        private ImageButton imgBtnModificar;

        public MiniProductoView(@NonNull ItemMiniProductoBinding binding) {
            super(binding.getRoot());
            imgProducto = binding.imgProducto;
            precioView = binding.precioView;
            tvVariacion = binding.tvVariacion;
            tvNombreProducto = binding.tvNombreProducto;
            tvStock = binding.tvStock;
            imgBtnModificar = binding.imgBtnModificar;
        }
    }

    public interface OnProductoClick{
        public void onModificar(ItemProducto producto);
        public boolean onAgregarPedido(ItemProducto producto);
        public void onVerProducto(ItemProducto producto);
        public void onStockClick(ItemProducto producto);
    }


}
