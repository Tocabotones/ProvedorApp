package com.example.provedorapp.ui.gestionClientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.provedorapp.R;
import com.example.provedorapp.server.SQLQueries;
import com.example.provedorapp.server.SQLite;
import com.example.provedorapp.clases.Cliente;
import com.example.provedorapp.clases.DatosFactura;
import com.example.provedorapp.databinding.ActivityGestionClientesBinding;

public class GestionClientes extends AppCompatActivity {

    private ActivityGestionClientesBinding binding;

    private TextView tvTituloOperacionCliente;

    private AutoCompleteTextView autoCtvNombreTienda;
    private AutoCompleteTextView autoCtvNombreContacto;
    private AutoCompleteTextView autoCtvNombreFactura;
    private AutoCompleteTextView autoCtvDni;
    private AutoCompleteTextView autoCtvDir;
    private CheckBox cbxRetencion;
    private Button btnAgregar;
    private Button btnCancelar;

    private Cliente cliente;


    private final String ERR_NOMBRE_TIENDA = "Tienes que introducir el nombre de la Tienda";
    private final String ERR_NOMBRE_CONTACTO = "Tienes que introducir el nombre " +
            "de la persona de Contacto";
    private final String ERR_NOMBRE_FACTURA = "Tienes que introducir el nombre que aparece en la " +
            "factura";
    private final String ERR_NOMBRE_DNI = "Tienes que introducir el dni";
    private final String ERR_NOMBRE_DIR = "Tienes que introducir la direccion de la Tienda";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGestionClientesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setResult(RESULT_CANCELED);
        setContentView(view);

        init();

    }

    private void init() {
        tvTituloOperacionCliente = binding.tvTituloOperacionCliente;
        autoCtvNombreTienda = binding.autoCtvNombreTienda;
        autoCtvNombreContacto = binding.autoCtvNomContacto;
        autoCtvNombreFactura = binding.autoCtvNomFactura;
        autoCtvDni = binding.autoCtvDni;
        autoCtvDir = binding.autoCtvDir;
        cbxRetencion = binding.cbxRetencion;
        btnAgregar = binding.btnAgregarCliente;
        btnCancelar = binding.btnCancelarCliente;

        if (!getIntent().hasExtra("idCliente")) {
            initAgregarCliente();
            cliente = null;
        } else {
            initModificarCliente(getIntent().getIntExtra("idCliente", 0));
        }

    }

    private void initAgregarCliente() {
        tvTituloOperacionCliente.setText(getResources().getString(R.string.agregar_cliente));

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (operacionCliente()) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });
    }

    private void initModificarCliente(int idCliente) {
        String titulo = getResources().getString(R.string.modificar_cliente);
        tvTituloOperacionCliente.setText(titulo);

        cliente = SQLQueries.getCliente(idCliente, this);
        initInfoCliente();
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (operacionCliente()) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });
    }

    public boolean operacionCliente() {
        Cliente c = new Cliente();
        DatosFactura datosFactura = new DatosFactura();

        boolean isValido = true;

        String nombreTienda = autoCtvNombreTienda.getText().toString();
        String nombreContacto = autoCtvNombreContacto.getText().toString();
        String nombreFactura = autoCtvNombreFactura.getText().toString();
        String dni = autoCtvDni.getText().toString();
        String dir = autoCtvDir.getText().toString();
        int retencion = cbxRetencion.isChecked() ? 1 : 0;

        if (!nombreTienda.isEmpty()) {
            c.setNombreTienda(nombreTienda);

        } else {
            isValido = false;
            autoCtvNombreTienda.setError(ERR_NOMBRE_TIENDA);
        }

        if (!nombreContacto.isEmpty()) {
            c.setPersonaContacto(nombreContacto);
        } else {
            isValido = false;
            autoCtvNombreContacto.setError(ERR_NOMBRE_CONTACTO);
        }

        if (!nombreFactura.isEmpty()) {
            datosFactura.setNombreFactura(nombreFactura);
        } else {
            isValido = false;
            autoCtvNombreFactura.setError(ERR_NOMBRE_FACTURA);
        }

        if (!dni.isEmpty()) {
            datosFactura.setDni(dni);
        } else {
            isValido = false;
            autoCtvDni.setError(ERR_NOMBRE_DNI);
        }

        if (!dir.isEmpty()) {
            datosFactura.setDireccion(dir);
        } else {
            isValido = false;
            autoCtvDir.setError(ERR_NOMBRE_DIR);
        }

        datosFactura.setRetencion(retencion);

        if (isValido) {
            c.setDatosFactura(datosFactura);
            if (cliente == null) {
                SQLQueries.insertarCliente(c, this);
            } else {
                c.setIdTienda(cliente.getIdTienda());
                SQLQueries.actualizarCliente(c, this);
            }
        }

        return isValido;
    }


    public void cancelar() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void initInfoCliente() {
        autoCtvNombreTienda.setText(cliente.getNombreTienda());
        autoCtvNombreContacto.setText(cliente.getPersonaContacto());

        DatosFactura datosFactura = cliente.getDatosFactura();

        autoCtvNombreFactura.setText(datosFactura.getNombreFactura());
        autoCtvDni.setText(datosFactura.getDni());
        autoCtvDir.setText(datosFactura.getDireccion());

        if (datosFactura.getRetencion() == 1) cbxRetencion.setChecked(true);
    }


}