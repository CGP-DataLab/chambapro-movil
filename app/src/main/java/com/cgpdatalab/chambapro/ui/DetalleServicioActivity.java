package com.cgpdatalab.chambapro.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cgpdatalab.chambapro.R;
import com.cgpdatalab.chambapro.data.ServicioRepository;
import com.cgpdatalab.chambapro.data.SessionManager;
import com.cgpdatalab.chambapro.model.Servicio;
import com.cgpdatalab.chambapro.util.Formato;

/** Módulo 3: detalle de un servicio y solicitud por parte del cliente. */
public class DetalleServicioActivity extends AppCompatActivity {

    public static final String EXTRA_SERVICIO_ID = "servicio_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_servicio);

        int id = getIntent().getIntExtra(EXTRA_SERVICIO_ID, -1);
        Servicio servicio = ServicioRepository.buscarPorId(id);
        if (servicio == null) {
            finish();
            return;
        }

        SessionManager sesion = new SessionManager(this);

        ((TextView) findViewById(R.id.tvNombre)).setText(servicio.getNombre());
        ((TextView) findViewById(R.id.tvCategoria)).setText(servicio.getCategoria());
        ((TextView) findViewById(R.id.tvDescripcion)).setText(servicio.getDescripcion());
        ((TextView) findViewById(R.id.tvProveedor)).setText(
                getString(R.string.detalle_proveedor, servicio.getProveedor(), servicio.getCiudad()));
        ((TextView) findViewById(R.id.tvPrecio)).setText(
                getString(R.string.detalle_precio, Formato.precio(servicio.getPrecio())));
        ((TextView) findViewById(R.id.tvCalificacion)).setText(
                getString(R.string.detalle_calificacion, servicio.getCalificacion()));

        Button btnSolicitar = findViewById(R.id.btnSolicitar);
        Button btnVolver = findViewById(R.id.btnVolver);

        if (SessionManager.ROL_PROVEEDOR.equals(sesion.getRol())) {
            btnSolicitar.setEnabled(false);
            btnSolicitar.setText(R.string.solo_clientes);
        }

        btnSolicitar.setOnClickListener(v -> {
            sesion.agregarSolicitud(servicio);
            Toast.makeText(this, R.string.solicitud_enviada, Toast.LENGTH_LONG).show();
            finish();
        });

        btnVolver.setOnClickListener(v -> finish());
    }
}
