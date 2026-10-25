package com.cgpdatalab.chambapro.util;

import java.text.NumberFormat;
import java.util.Locale;

/** Formatos de presentación (moneda colombiana). */
public final class Formato {

    private Formato() { }

    public static String precio(int valor) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));
        nf.setMaximumFractionDigits(0);
        return nf.format(valor);
    }
}
