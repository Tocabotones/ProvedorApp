package com.example.provedorapp.ui.pedidoActual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.provedorapp.databinding.FragmentPedidoActualBinding;

public class PedidoActualFragment extends Fragment {

    private FragmentPedidoActualBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PedidoActualViewModel notificationsViewModel =
                new ViewModelProvider(this).get(PedidoActualViewModel.class);

        binding = FragmentPedidoActualBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}