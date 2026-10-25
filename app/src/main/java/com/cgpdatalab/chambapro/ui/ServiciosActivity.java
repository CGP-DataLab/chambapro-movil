package com.cgpdatalab.chambapro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgpdatalab.chambapro.R;
import com.cgpdatalab.chambapro.data.ServicioRepository;
import com.cgpdatalab.chambapro.data.SessionManager;
import com.cgpdatalab.chambapro.model.Servicio;

import java.util.List;

/** Módulo 2: catálogo de servicios con filtro por categoría y búsqueda. */
public class ServiciosActivity extends AppCompatActivity {

    private ServicioAdapter adapter;
    private Spinner spCategoria;
    private EditText etBuscar;
    private TextView tvVacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        SessionManager sesion = new SessionManager(this);

        TextView tvSaludo = findViewById(R.id.tvSaludo);
        Button btnSolicitudes = findViewById(R.id.btnMisSolicitudes);
        Button btnSalir = findViewById(R.id.btnCerrarSesion);
        spCategoria = findViewById(R.id.spCategoria);
        etBuscar = findViewById(R.id.etBuscar);
        tvVacio = findViewById(R.id.tvVacio);
        RecyclerView rvServicios = findViewById(R.id.rvServicios);

        tvSaludo.setText(getString(R.string.saludo_usuario, sesion.getNombre(), sesion.getRol()));

        ArrayAdapter<String> categorias = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ServicioRepository.obtenerCategorias());
        categorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(categorias);

        adapter = new ServicioAdapter(ServicioRepository.obtenerTodos(), servicio -> {
            Intent intent = new Intent(this, DetalleServicioActivity.class);
            intent.putExtra(DetalleServicioActivity.EXTRA_SERVICIO_ID, servicio.getId());
            startActivity(intent);
        });
        rvServicios.setLayoutManager(new LinearLayoutManager(this));
        rvServicios.setAdapter(adapter);

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actualizarLista();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actualizarLista();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        btnSolicitudes.setOnClickListener(v ->
                startActivity(new Intent(this, SolicitudesActivity.class)));

        btnSalir.setOnClickListener(v -> {
            sesion.cerrarSesion();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void actualizarLista() {
        String categoria = String.valueOf(spCategoria.getSelectedItem());
        String texto = String.valueOf(etBuscar.getText());
        List<Servicio> resultado = ServicioRepository.filtrar(categoria, texto);
        adapter.actualizar(resultado);
        tvVacio.setVisibility(resultado.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
