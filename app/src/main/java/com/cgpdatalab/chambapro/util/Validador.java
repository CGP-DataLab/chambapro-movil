package com.cgpdatalab.chambapro.util;

import java.util.regex.Pattern;

/** Reglas de validación de los formularios (sin dependencias de Android, fácil de probar). */
public final class Validador {

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static final int LONGITUD_MINIMA_CONTRASENA = 6;
    public static final int LONGITUD_MINIMA_NOMBRE = 3;

    private Validador() { }

    public static boolean emailValido(String email) {
        return email != null && EMAIL.matcher(email.trim()).matches();
    }

    public static boolean contrasenaValida(String contrasena) {
        return contrasena != null && contrasena.length() >= LONGITUD_MINIMA_CONTRASENA;
    }

    public static boolean nombreValido(String nombre) {
        return nombre != null && nombre.trim().length() >= LONGITUD_MINIMA_NOMBRE;
    }
}
