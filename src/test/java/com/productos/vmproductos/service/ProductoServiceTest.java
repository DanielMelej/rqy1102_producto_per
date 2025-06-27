package com.productos.vmproductos.service;

import com.productos.vmproductos.model.Producto;
import com.productos.vmproductos.repository.ProductoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void testGetAllProductos() {
        Producto producto1 = new Producto(1, "Producto1", 100.0f, 10, 100);
        Producto producto2 = new Producto(2, "Producto2", 200.0f, 20, 100);
        List<Producto> productos = Arrays.asList(producto1, producto2);

        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> result = productoService.getAllProductos();

        assertEquals(2, result.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Producto producto = new Producto(1, "Producto1", 100.0f, 10, 100);
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        Producto result = productoService.findById(1);

        assertNotNull(result);
        assertEquals("Producto1", result.getNombre());
    }

    @Test
    void testSaveProducto() {
        Producto producto = new Producto(null, "Nuevo", 50.0f, 5, 50);
        Producto saved = new Producto(1, "Nuevo", 50.0f, 5,50);
        when(productoRepository.save(producto)).thenReturn(saved);

        Producto result = productoService.save(producto);

        assertNotNull(result.getIdProducto());
        assertEquals("Nuevo", result.getNombre());
    }

    @Test
    void testFindByIdNotFound() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            productoService.findById(99);
        });
    }

}
