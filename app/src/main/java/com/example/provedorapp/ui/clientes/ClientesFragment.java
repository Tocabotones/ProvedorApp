package com.example.provedorapp.ui.clientes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.provedorapp.R;
import com.example.provedorapp.server.SQLQueries;
import com.example.provedorapp.server.SQLite;
import com.example.provedorapp.clases.Cliente;
import com.example.provedorapp.clases.DatosFactura;
import com.example.provedorapp.databinding.FragmentClientesBinding;
import com.example.provedorapp.ui.gestionClientes.GestionClientes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class ClientesFragment extends Fragment {


    private FragmentClientesBinding binding;
    private ArrayList<Cliente> clientes;
    private ArrayAdapter<Cliente> clientesAdapter;
    private Spinner spnClientes;
    private FloatingActionButton fabClientes;
    private ImageButton imgBtnModCliente;
    private ImageButton imgBtnDelCliente;

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
        fabClientes = binding.fabClientes;
        imgBtnModCliente = binding.imgBtnMod;
        imgBtnDelCliente = binding.imgBtnBorrarCliente;

        clientes = getClientes();
        clientesAdapter = new ArrayAdapter<>(getActivity()
                , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, clientes);

        spnClientes.setAdapter(clientesAdapter);

        spnClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Cliente c = (Cliente) spnClientes.getSelectedItem();
                actualizarInfoCliente(c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(!clientes.isEmpty()){
            Cliente c = (Cliente) spnClientes.getSelectedItem();
            actualizarInfoCliente(c);
        }

        fabClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GestionClientes.class);
                getIntentActivityResultLauncher.launch(intent);
            }
        });

        imgBtnModCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarCliente();
            }
        });

        imgBtnDelCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarCliente();
            }
        });

        return root;


    }


    public void actualizarInfoCliente(Cliente c){
        tvNombreTienda.setText(c.getNombreTienda());
        tvPersonaContacto.setText(c.getPersonaContacto());

        DatosFactura datosFactura = c.getDatosFactura();

        tvNombreFactura.setText(datosFactura.getNombreFactura());
        tvDni.setText(datosFactura.getDni());
        tvDireccion.setText(datosFactura.getDireccion());
        tvRetencion.setText(datosFactura.getRetencion() == 1 ? "Si" : "No");

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
            DatosFactura datosFactura = new DatosFactura();

            cliente.setIdTienda(cursor.getInt(0));
            cliente.setPersonaContacto(cursor.getString(1));
            cliente.setNombreTienda(cursor.getString(2));
            datosFactura.setNombreFactura(cursor.getString(3));
            datosFactura.setDni(cursor.getString(4));
            datosFactura.setDireccion(cursor.getString(5));
            datosFactura.setRetencion(cursor.getInt(6));
            cliente.setDatosFactura(datosFactura);
            clientes.add(cliente);
        }

        return clientes;
    }

    public void modificarCliente(){
        if (!clientes.isEmpty()){
            Cliente c = (Cliente) spnClientes.getSelectedItem();

            int idCliente = c.getIdTienda();

            Intent intent = new Intent(getActivity(),GestionClientes.class);
            intent.putExtra("idCliente",idCliente);
            getIntentActivityResultLauncher.launch(intent);
        }

    }

    public void borrarCliente(){
        if (!clientes.isEmpty()){
            Cliente c = (Cliente) spnClientes.getSelectedItem();

            AlertDialog dialogo = new AlertDialog
                    .Builder(getActivity())
                    .setPositiveButton(getActivity().getResources().getString(R.string.confirmar_afirmativo)
                            , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SQLQueries.borrarCliente(c.getIdTienda(),getActivity());
                            actualizarUI();
                            Snackbar snackbar = Snackbar.make(binding.fragmentClientesCtl,
                                            getActivity().getResources().getString(R.string.cliente_borrado),
                            BaseTransientBottomBar.LENGTH_SHORT)
                            .setAction("Deshacer", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    SQLQueries.reInsertarCliente(c,getActivity());
                                    actualizarUI();
                                    Snackbar.make(binding.fragmentClientesCtl,
                                            "El producto se ha vuelto a agregar",
                                            BaseTransientBottomBar.LENGTH_SHORT).show();
                                }
                            });

                            snackbar.show();
                        }
                    })
                    .setNegativeButton(getActivity().getResources().getString(R.string.confirmar_negativo), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setTitle(getActivity().getResources().getString(R.string.confirmar_borrado))
                    .setMessage("¿Deseas eliminar el Cliente " + c.getNombreTienda() + " ?")
                    .create();

                    dialogo.show();

        }
    }

    ActivityResultLauncher<Intent> getIntentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if(result.getResultCode() == -1){
                actualizarUI();
            }


        }
    });

    private void actualizarUI() {
        clientes = getClientes();
        clientesAdapter = new ArrayAdapter<>(getActivity()
                , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, clientes);
        spnClientes.setAdapter(clientesAdapter);
        Cliente c = (Cliente) spnClientes.getSelectedItem();
        actualizarInfoCliente(c);
    }
}