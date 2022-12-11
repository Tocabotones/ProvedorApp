package com.example.provedorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.provedorapp.R;
import com.example.provedorapp.clases.MiniProducto;
import com.example.provedorapp.componentes.PrecioView;
import com.example.provedorapp.databinding.ItemMiniProductoBinding;


public class MiniProductosAdapter extends ListAdapter<MiniProducto
        , MiniProductosAdapter.MiniProductoView> {

    private OnProductoClick listener;
    private Context context;

    public MiniProductosAdapter(Context context,OnProductoClick listener) {
        super(MiniProducto.itemCallback);
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
        MiniProducto miniProducto = getItem(position);


        holder.tvNombreProducto.setText(miniProducto.getNombre());
        holder.tvVariacion.setText(miniProducto.getVariacion());
        Glide.with(context)
                .load(miniProducto.getImg())
                .into(holder.imgProducto);


        if (miniProducto.getPrecioDescuento() == 0.0){
            holder.precioView.setPrecio(miniProducto.getPrecio(),24,
                    context.getResources().getColor(R.color.contrasteCeleste));
//            holder.frgPrecio.setPrecioDecoration(miniProducto.getPrecio(),24,
//                    context.getResources().getColor(R.color.black));
        }



    }



    // CLASES Y INTERFACES

    public class MiniProductoView extends RecyclerView.ViewHolder {

        private ImageView imgProducto;
        private PrecioView precioView;
        private TextView tvVariacion;
        private TextView tvNombreProducto;

        public MiniProductoView(@NonNull ItemMiniProductoBinding binding) {
            super(binding.getRoot());
            imgProducto = binding.imgProducto;
            precioView = binding.precioView;
            tvVariacion = binding.tvVariacion;
            tvNombreProducto = binding.tvNombreProducto;
        }
    }

    public interface OnProductoClick{
        public boolean onAgregarPedido(MiniProducto producto);
        public void onVerProducto(MiniProducto producto);
    }


}
