package com.cgpdatalab.chambapro.model;

/** Servicio que ofrece un proveedor dentro del marketplace ChambaPro. */
public class Servicio {

    private final int id;
    private final String nombre;
    private final String categoria;
    private final String descripcion;
    private final String proveedor;
    private final String ciudad;
    private final int precio;
    private final double calificacion;

    public Servicio(int id, String nombre, String categoria, String descripcion,
                    String proveedor, String ciudad, int precio, double calificacion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.proveedor = proveedor;
        this.ciudad = ciudad;
        this.precio = precio;
        this.calificacion = calificacion;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public String getProveedor() { return proveedor; }
    public String getCiudad() { return ciudad; }
    public int getPrecio() { return precio; }
    public double getCalificacion() { return calificacion; }
}
