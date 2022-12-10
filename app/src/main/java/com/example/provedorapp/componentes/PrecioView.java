package com.example.provedorapp.componentes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.provedorapp.R;

public class PrecioView extends ConstraintLayout {

    private TextView tvPrecioEntero;
    private TextView tvPrecioDecimal;
    private TextView tvPrecioAnterior;

    public PrecioView(@NonNull Context context) {
        super(context);
//        init(context);
    }

    public PrecioView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        init(context);
    }

    public PrecioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init(context);
    }

    public PrecioView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context);
    }

    private void init(Context context){

        inflate(context,R.layout.view_precio,this);

        tvPrecioEntero = findViewById(R.id.tvPrecioEntero);
        tvPrecioDecimal = findViewById(R.id.tvPrecioDecimal);
        tvPrecioAnterior = findViewById(R.id.tvPrecioAnterior);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        init(getContext());
    }

    public void setPrecio(double precioOferta, float precioSize,
                          int colorOferta){

        String precioStr = String.valueOf(precioOferta);
        String [] precio = precioStr.split("\\.");

        if (precio.length == 2){

            tvPrecioEntero.setText(precio[0]+ ",");
            tvPrecioEntero.setTextSize(precioSize);
            tvPrecioEntero.setTextColor(colorOferta);

            tvPrecioDecimal.setText(precio[1]+ "€");
            tvPrecioDecimal.setTextSize(precioSize - 6);
            tvPrecioDecimal.setTextColor(colorOferta);

            tvPrecioAnterior.setVisibility(View.GONE);

        }



    }

}
