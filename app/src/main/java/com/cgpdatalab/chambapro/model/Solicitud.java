package com.cgpdatalab.chambapro.model;

/** Solicitud de un servicio hecha por un cliente. */
public class Solicitud {

    private final String servicio;
    private final String proveedor;
    private final int precio;
    private final String fecha;

    public Solicitud(String servicio, String proveedor, int precio, String fecha) {
        this.servicio = servicio;
        this.proveedor = proveedor;
        this.precio = precio;
        this.fecha = fecha;
    }

    public String getServicio() { return servicio; }
    public String getProveedor() { return proveedor; }
    public int getPrecio() { return precio; }
    public String getFecha() { return fecha; }
}
