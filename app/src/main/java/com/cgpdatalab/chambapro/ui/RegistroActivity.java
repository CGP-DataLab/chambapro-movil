package com.cgpdatalab.chambapro.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cgpdatalab.chambapro.R;
import com.cgpdatalab.chambapro.data.SessionManager;
import com.cgpdatalab.chambapro.util.Validador;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/** Módulo 1: autenticación (registro de cliente o proveedor). */
public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        SessionManager sesion = new SessionManager(this);

        TextInputLayout tilNombre = findViewById(R.id.tilNombre);
        TextInputLayout tilEmail = findViewById(R.id.tilEmail);
        TextInputLayout tilContrasena = findViewById(R.id.tilContrasena);
        TextInputEditText etNombre = findViewById(R.id.etNombre);
        TextInputEditText etEmail = findViewById(R.id.etEmail);
        TextInputEditText etContrasena = findViewById(R.id.etContrasena);
        RadioButton rbProveedor = findViewById(R.id.rbProveedor);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(v -> {
            String nombre = String.valueOf(etNombre.getText()).trim();
            String email = String.valueOf(etEmail.getText()).trim();
            String contrasena = String.valueOf(etContrasena.getText());

            tilNombre.setError(null);
            tilEmail.setError(null);
            tilContrasena.setError(null);

            if (!Validador.nombreValido(nombre)) {
                tilNombre.setError(getString(R.string.error_nombre));
                return;
            }
            if (!Validador.emailValido(email)) {
                tilEmail.setError(getString(R.string.error_email));
                return;
            }
            if (!Validador.contrasenaValida(contrasena)) {
                tilContrasena.setError(getString(R.string.error_contrasena));
                return;
            }

            String rol = rbProveedor.isChecked()
                    ? SessionManager.ROL_PROVEEDOR : SessionManager.ROL_CLIENTE;

            if (sesion.registrar(nombre, email, contrasena, rol)) {
                Toast.makeText(this, R.string.registro_exitoso, Toast.LENGTH_LONG).show();
                finish();
            } else {
                tilEmail.setError(getString(R.string.error_email_registrado));
            }
        });
    }
}
