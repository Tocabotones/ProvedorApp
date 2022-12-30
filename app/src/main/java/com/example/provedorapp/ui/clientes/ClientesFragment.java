package com.example.provedorapp.ui.clientes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.provedorapp.R;
import com.example.provedorapp.SQLite;
import com.example.provedorapp.clases.Cliente;
import com.example.provedorapp.databinding.FragmentClientesBinding;
import com.example.provedorapp.databinding.FragmentProductosBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ClientesFragment extends Fragment {


    private FragmentClientesBinding binding;
    private ArrayList<Cliente> clientes;
    private ArrayAdapter<Cliente> clientesAdapter;
    private Spinner spnClientes;

    private TextView tvNombreTienda;
    private TextView tvPersonaContacto;
    private TextView tvNombreFactura;
    private TextView tvDni;
    private TextView tvDireccion;
    private TextView tvRetencion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentClientesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spnClientes = binding.spnClientes;
        tvNombreTienda = binding.tvNombreTienda;
        tvPersonaContacto = binding.tvPersonaContacto;
        tvNombreFactura = binding.tvNombreFactura;
        tvDni = binding.tvDni;
        tvDireccion = binding.tvDireccion;
        tvRetencion = binding.tvRetencion;

        clientes = getClientes();
        clientesAdapter = new ArrayAdapter<>(getActivity()
                , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, clientes);

        spnClientes.setAdapter(clientesAdapter);

        if(!clientes.isEmpty()){
            Cliente c = (Cliente) spnClientes.getSelectedItem();
            actualizarInfoCliente(c);
        }
        return root;
    }

    public void actualizarInfoCliente(Cliente c){
        tvNombreTienda.setText(c.getNombreTienda());
        tvPersonaContacto.setText(c.getPersonaContacto());
        tvNombreFactura.setText(c.getNombreFactura());
        tvDni.setText(c.getDni());
        tvDireccion.setText(c.getDireccion());
        tvRetencion.setText(c.getRetencion() == 1 ? "Si" : "No");

    }

    public ArrayList<Cliente> getClientes(){

        ArrayList<Cliente> clientes = new ArrayList<>();

        SQLite helper = new SQLite(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT idCliente, nombreContacto," +
                        " nombreTienda, nombreFactura, dniPropietario, direccion," +
                " retencion FROM clientes",null);

        while(cursor.moveToNext()){
            Cliente cliente = new Cliente();

            cliente.setIdTienda(cursor.getInt(0));
            cliente.setPersonaContacto(cursor.getString(1));
            cliente.setNombreTienda(cursor.getString(2));
            cliente.setNombreFactura(cursor.getString(3));
            cliente.setDni(cursor.getString(4));
            cliente.setDireccion(cursor.getString(5));
            cliente.setRetencion(cursor.getInt(6));

            clientes.add(cliente);
        }

        return clientes;
    }

}