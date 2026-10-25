package com.cgpdatalab.chambapro.data;

import com.cgpdatalab.chambapro.model.Servicio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Origen de datos de los servicios.
 *
 * Por ahora usa datos de ejemplo en memoria. Más adelante este es el único
 * punto que se cambia para consumir la API REST del backend MERN de ChambaPro.
 */
public final class ServicioRepository {

    public static final String TODAS = "Todas";

    private static final List<Servicio> SERVICIOS = Arrays.asList(
            new Servicio(1, "Reparación de fugas y tuberías", "Plomería",
                    "Detección y reparación de fugas, cambio de tuberías y sifones.",
                    "Carlos Rodríguez", "Acacías", 60000, 4.7),
            new Servicio(2, "Instalación de baños y griferías", "Plomería",
                    "Instalación completa de sanitarios, lavamanos, duchas y griferías.",
                    "Andrés Pineda", "Villavicencio", 150000, 4.5),
            new Servicio(3, "Instalaciones eléctricas residenciales", "Electricidad",
                    "Cableado, tableros y puntos eléctricos para casas y apartamentos.",
                    "Luz Marina Castro", "Acacías", 120000, 4.8),
            new Servicio(4, "Reparación de tomas y breakers", "Electricidad",
                    "Revisión y cambio de tomas, interruptores y protecciones.",
                    "Jhon Gómez", "Guamal", 50000, 4.3),
            new Servicio(5, "Muebles a medida", "Carpintería",
                    "Diseño y fabricación de closets, cocinas integrales y escritorios.",
                    "Hernán Duarte", "Acacías", 350000, 4.9),
            new Servicio(6, "Pintura de interiores y fachadas", "Pintura",
                    "Pintura con preparación de superficies, estuco y acabados.",
                    "Wilson Arévalo", "Villavicencio", 200000, 4.4),
            new Servicio(7, "Mantenimiento de jardines", "Jardinería",
                    "Poda, siembra, abonado y diseño de zonas verdes.",
                    "Marta Quintero", "Acacías", 80000, 4.6),
            new Servicio(8, "Aseo profundo de hogar", "Limpieza",
                    "Limpieza profunda de casas y apartamentos, incluye insumos.",
                    "Sandra Molina", "Villavicencio", 90000, 4.7),
            new Servicio(9, "Soporte técnico de computadores", "Tecnología",
                    "Mantenimiento, formateo, redes y reparación de equipos.",
                    "Yuliana Torres", "Acacías", 70000, 4.8)
    );

    private ServicioRepository() { }

    public static List<Servicio> obtenerTodos() {
        return new ArrayList<>(SERVICIOS);
    }

    /** Devuelve "Todas" seguido de las categorías existentes, sin repetir. */
    public static List<String> obtenerCategorias() {
        Set<String> categorias = new LinkedHashSet<>();
        categorias.add(TODAS);
        for (Servicio s : SERVICIOS) {
            categorias.add(s.getCategoria());
        }
        return new ArrayList<>(categorias);
    }

    /** Filtra por categoría y por texto (nombre, descripción o proveedor). */
    public static List<Servicio> filtrar(String categoria, String texto) {
        boolean todasCategorias = categoria == null || categoria.equals(TODAS);
        String busqueda = texto == null ? "" : texto.trim().toLowerCase(Locale.ROOT);

        List<Servicio> resultado = new ArrayList<>();
        for (Servicio s : SERVICIOS) {
            boolean coincideCategoria = todasCategorias || s.getCategoria().equals(categoria);
            boolean coincideTexto = busqueda.isEmpty()
                    || s.getNombre().toLowerCase(Locale.ROOT).contains(busqueda)
                    || s.getDescripcion().toLowerCase(Locale.ROOT).contains(busqueda)
                    || s.getProveedor().toLowerCase(Locale.ROOT).contains(busqueda);
            if (coincideCategoria && coincideTexto) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    public static Servicio buscarPorId(int id) {
        for (Servicio s : SERVICIOS) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }
}
