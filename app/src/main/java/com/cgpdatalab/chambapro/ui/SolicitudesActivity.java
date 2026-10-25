package com.cgpdatalab.chambapro.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgpdatalab.chambapro.R;
import com.cgpdatalab.chambapro.data.SessionManager;
import com.cgpdatalab.chambapro.model.Solicitud;

import java.util.List;

/** Módulo 4: historial de solicitudes del usuario. */
public class SolicitudesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        List<Solicitud> solicitudes = new SessionManager(this).obtenerSolicitudes();

        RecyclerView rvSolicitudes = findViewById(R.id.rvSolicitudes);
        TextView tvVacio = findViewById(R.id.tvVacio);
        Button btnVolver = findViewById(R.id.btnVolver);

        rvSolicitudes.setLayoutManager(new LinearLayoutManager(this));
        rvSolicitudes.setAdapter(new SolicitudAdapter(solicitudes));
        tvVacio.setVisibility(solicitudes.isEmpty() ? View.VISIBLE : View.GONE);

        btnVolver.setOnClickListener(v -> finish());
    }
}
