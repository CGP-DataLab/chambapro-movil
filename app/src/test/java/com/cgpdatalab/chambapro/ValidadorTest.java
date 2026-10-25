package com.cgpdatalab.chambapro;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.cgpdatalab.chambapro.util.Validador;

import org.junit.Test;

public class ValidadorTest {

    @Test
    public void emailValido_aceptaCorreosCorrectos() {
        assertTrue(Validador.emailValido("giovanny@cgpdatalab.com"));
        assertTrue(Validador.emailValido("  usuario.prueba@correo.co  "));
    }

    @Test
    public void emailValido_rechazaCorreosIncorrectos() {
        assertFalse(Validador.emailValido(null));
        assertFalse(Validador.emailValido(""));
        assertFalse(Validador.emailValido("sin-arroba.com"));
        assertFalse(Validador.emailValido("usuario@"));
        assertFalse(Validador.emailValido("usuario@dominio"));
    }

    @Test
    public void contrasenaValida_exigeMinimoSeisCaracteres() {
        assertTrue(Validador.contrasenaValida("123456"));
        assertFalse(Validador.contrasenaValida("12345"));
        assertFalse(Validador.contrasenaValida(null));
    }

    @Test
    public void nombreValido_exigeMinimoTresCaracteres() {
        assertTrue(Validador.nombreValido("Ana"));
        assertFalse(Validador.nombreValido("  Al "));
        assertFalse(Validador.nombreValido(null));
    }
}
