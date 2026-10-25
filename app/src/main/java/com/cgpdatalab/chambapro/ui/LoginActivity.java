package com.cgpdatalab.chambapro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cgpdatalab.chambapro.R;
import com.cgpdatalab.chambapro.data.SessionManager;
import com.cgpdatalab.chambapro.util.Validador;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/** Módulo 1: autenticación (inicio de sesión). */
public class LoginActivity extends AppCompatActivity {

    private SessionManager sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sesion = new SessionManager(this);

        if (sesion.haySesion()) {
            irAServicios();
            return;
        }

        setContentView(R.layout.activity_login);

        TextInputLayout tilEmail = findViewById(R.id.tilEmail);
        TextInputLayout tilContrasena = findViewById(R.id.tilContrasena);
        TextInputEditText etEmail = findViewById(R.id.etEmail);
        TextInputEditText etContrasena = findViewById(R.id.etContrasena);
        Button btnIngresar = findViewById(R.id.btnIngresar);
        TextView tvRegistro = findViewById(R.id.tvRegistro);

        btnIngresar.setOnClickListener(v -> {
            String email = String.valueOf(etEmail.getText()).trim();
            String contrasena = String.valueOf(etContrasena.getText());

            tilEmail.setError(null);
            tilContrasena.setError(null);

            if (!Validador.emailValido(email)) {
                tilEmail.setError(getString(R.string.error_email));
                return;
            }
            if (!Validador.contrasenaValida(contrasena)) {
                tilContrasena.setError(getString(R.string.error_contrasena));
                return;
            }
            if (sesion.iniciarSesion(email, contrasena)) {
                irAServicios();
            } else {
                Toast.makeText(this, R.string.error_credenciales, Toast.LENGTH_LONG).show();
            }
        });

        tvRegistro.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));
    }

    private void irAServicios() {
        startActivity(new Intent(this, ServiciosActivity.class));
        finish();
    }
}
