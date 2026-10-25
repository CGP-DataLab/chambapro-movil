package com.cgpdatalab.chambapro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.cgpdatalab.chambapro.util.Hasher;

import org.junit.Test;

public class HasherTest {

    @Test
    public void sha256_devuelveElValorConocido() {
        assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad",
                Hasher.sha256("abc"));
    }

    @Test
    public void sha256_mismoTextoMismoHash() {
        assertEquals(Hasher.sha256("clave123"), Hasher.sha256("clave123"));
    }

    @Test
    public void sha256_textosDistintosHashDistinto() {
        assertNotEquals(Hasher.sha256("clave123"), Hasher.sha256("clave124"));
    }

    @Test
    public void sha256_tieneSesentaYCuatroCaracteres() {
        assertEquals(64, Hasher.sha256("cualquier texto").length());
    }
}