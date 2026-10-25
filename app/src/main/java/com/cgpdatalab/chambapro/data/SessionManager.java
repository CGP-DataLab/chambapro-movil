package com.cgpdatalab.chambapro.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.cgpdatalab.chambapro.model.Servicio;
import com.cgpdatalab.chambapro.model.Solicitud;
import com.cgpdatalab.chambapro.util.Hasher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Registro de usuarios, sesión y solicitudes guardados localmente con SharedPreferences.
 * Las contraseñas se guardan como hash SHA-256, nunca en texto plano.
 */
public class SessionManager {

    public static final String ROL_CLIENTE = "Cliente";
    public static final String ROL_PROVEEDOR = "Proveedor";

    private static final String PREFS = "chambapro_prefs";
    private static final String KEY_SESION = "sesion_email";
    private static final String PREFIJO_USUARIO = "usuario_";
    private static final String PREFIJO_SOLICITUDES = "solicitudes_";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    private static String normalizar(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    /** @return false si el correo ya estaba registrado. */
    public boolean registrar(String nombre, String email, String contrasena, String rol) {
        String clave = PREFIJO_USUARIO + normalizar(email);
        if (prefs.contains(clave)) {
            return false;
        }
        try {
            JSONObject usuario = new JSONObject();
            usuario.put("nombre", nombre.trim());
            usuario.put("hash", Hasher.sha256(contrasena));
            usuario.put("rol", rol);
            prefs.edit().putString(clave, usuario.toString()).apply();
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /** @return true si las credenciales son correctas (y deja la sesión abierta). */
    public boolean iniciarSesion(String email, String contrasena) {
        String guardado = prefs.getString(PREFIJO_USUARIO + normalizar(email), null);
        if (guardado == null) {
            return false;
        }
        try {
            String hash = new JSONObject(guardado).getString("hash");
            if (hash.equals(Hasher.sha256(contrasena))) {
                prefs.edit().putString(KEY_SESION, normalizar(email)).apply();
                return true;
            }
        } catch (JSONException e) {
            return false;
        }
        return false;
    }

    public boolean haySesion() {
        return prefs.getString(KEY_SESION, null) != null;
    }

    public void cerrarSesion() {
        prefs.edit().remove(KEY_SESION).apply();
    }

    public String getEmail() {
        return prefs.getString(KEY_SESION, "");
    }

    public String getNombre() {
        return campoUsuario("nombre", "");
    }

    public String getRol() {
        return campoUsuario("rol", ROL_CLIENTE);
    }

    private String campoUsuario(String campo, String porDefecto) {
        String guardado = prefs.getString(PREFIJO_USUARIO + getEmail(), null);
        if (guardado == null) {
            return porDefecto;
        }
        try {
            return new JSONObject(guardado).optString(campo, porDefecto);
        } catch (JSONException e) {
            return porDefecto;
        }
    }

    public void agregarSolicitud(Servicio servicio) {
        String clave = PREFIJO_SOLICITUDES + getEmail();
        try {
            JSONArray lista = new JSONArray(prefs.getString(clave, "[]"));
            JSONObject item = new JSONObject();
            item.put("servicio", servicio.getNombre());
            item.put("proveedor", servicio.getProveedor());
            item.put("precio", servicio.getPrecio());
            item.put("fecha", new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    .format(new Date()));
            lista.put(item);
            prefs.edit().putString(clave, lista.toString()).apply();
        } catch (JSONException ignored) {
            // Si el JSON guardado estuviera dañado, la solicitud simplemente no se agrega.
        }
    }

    public List<Solicitud> obtenerSolicitudes() {
        List<Solicitud> resultado = new ArrayList<>();
        try {
            JSONArray lista = new JSONArray(
                    prefs.getString(PREFIJO_SOLICITUDES + getEmail(), "[]"));
            for (int i = lista.length() - 1; i >= 0; i--) {
                JSONObject o = lista.getJSONObject(i);
                resultado.add(new Solicitud(o.getString("servicio"), o.getString("proveedor"),
                        o.getInt("precio"), o.getString("fecha")));
            }
        } catch (JSONException ignored) {
            // Devuelve lo que alcanzó a leer.
        }
        return resultado;
    }
}
