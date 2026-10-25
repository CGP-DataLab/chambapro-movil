package com.cgpdatalab.chambapro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cgpdatalab.chambapro.data.ServicioRepository;
import com.cgpdatalab.chambapro.model.Servicio;

import org.junit.Test;

import java.util.List;

public class ServicioRepositoryTest {

    @Test
    public void obtenerTodos_devuelveLosNueveServicios() {
        assertEquals(9, ServicioRepository.obtenerTodos().size());
    }

    @Test
    public void obtenerCategorias_empiezaPorTodasYNoRepite() {
        List<String> categorias = ServicioRepository.obtenerCategorias();
        assertEquals(ServicioRepository.TODAS, categorias.get(0));
        assertEquals(7, categorias.size() - 1);
        assertEquals(categorias.size(), categorias.stream().distinct().count());
    }

    @Test
    public void filtrar_porCategoriaSoloDevuelveEsaCategoria() {
        List<Servicio> plomeria = ServicioRepository.filtrar("Plomería", "");
        assertEquals(2, plomeria.size());
        for (Servicio s : plomeria) {
            assertEquals("Plomería", s.getCategoria());
        }
    }

    @Test
    public void filtrar_todasSinTextoDevuelveTodo() {
        assertEquals(9, ServicioRepository.filtrar(ServicioRepository.TODAS, null).size());
    }

    @Test
    public void filtrar_porTextoIgnoraMayusculas() {
        List<Servicio> resultado = ServicioRepository.filtrar(ServicioRepository.TODAS, "ELÉCTRIC");
        assertFalse(resultado.isEmpty());
    }

    @Test
    public void filtrar_combinaCategoriaYTexto() {
        List<Servicio> resultado = ServicioRepository.filtrar("Electricidad", "breakers");
        assertEquals(1, resultado.size());
        assertEquals(4, resultado.get(0).getId());
    }

    @Test
    public void filtrar_sinCoincidenciasDevuelveListaVacia() {
        assertTrue(ServicioRepository.filtrar(ServicioRepository.TODAS, "xyz-no-existe").isEmpty());
    }

    @Test
    public void buscarPorId_existenteYNoExistente() {
        assertNotNull(ServicioRepository.buscarPorId(5));
        assertEquals("Muebles a medida", ServicioRepository.buscarPorId(5).getNombre());
        assertNull(ServicioRepository.buscarPorId(999));
    }
}