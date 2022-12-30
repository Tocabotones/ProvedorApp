package com.example.provedorapp.ui.detallesProducto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.provedorapp.R;
import com.example.provedorapp.databinding.FragmentClientesBinding;
import com.example.provedorapp.databinding.FragmentDetallesProductosBinding;
import com.example.provedorapp.databinding.FragmentProductosBinding;
import com.example.provedorapp.ui.productos.ProductosViewModel;

public class DetallesProductosFragment extends Fragment {

    FragmentDetallesProductosBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDetallesProductosBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        return root;
    }
}