package com.productos.vmproductos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productos.vmproductos.model.Producto;
import com.productos.vmproductos.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto findById(Integer id) {
        return productoRepository.findById(id).get();
    }

    public Producto save(Producto producto) {
        if (producto == null) {
            throw new RuntimeException("❌ El producto no puede ser nulo.");
        }

        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new RuntimeException("❌ El nombre del producto no puede estar vacío.");
        }

        if (producto.getNombre().length() > 250) {
            throw new RuntimeException("❌ El nombre del producto no puede tener más de 250 caracteres.");
        }

        if (producto.getPrecio() == null || producto.getPrecio() < 0) {
            throw new RuntimeException("❌ El precio debe ser un número válido y mayor o igual a 0.");
        }

        if (producto.getStock() == null) {
            throw new RuntimeException("❌ El stock no puede ser nulo.");
        }

        if (producto.getStock() < 0) {
            throw new RuntimeException("❌ El stock no puede ser negativo.");
        }

        if (producto.getStock() == 0) {
            throw new RuntimeException("⚠️ El producto no está disponible (stock = 0).");
        }

        if (producto.getTamano() == null || producto.getTamano().trim().isEmpty()) {
            throw new RuntimeException("❌ El tamaño del producto no puede estar vacío ni ser solo espacios.");
        }

        // Validar que siga el formato '100ml', '250ml', etc.
        if (!producto.getTamano().matches("^\\d{1,4}ml$")) {
            throw new RuntimeException(
                    "❌ El tamaño debe estar en formato válido (ejemplo: '100ml', '250ml'). Solo se aceptan números seguidos de 'ml'.");
        }

        return productoRepository.save(producto);
    }

    public Producto update(Integer id, Producto productoActualizado) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Producto no encontrado con ID " + id));

        // Validar que no se cambie el ID
        if (productoActualizado.getIdProducto() != null && !productoActualizado.getIdProducto().equals(id)) {
            throw new RuntimeException("❌ No se puede cambiar el ID del producto");
        }

        // Validar y actualizar nombre si viene
        if (productoActualizado.getNombre() != null) {
            if (productoActualizado.getNombre().trim().isEmpty()) {
                throw new RuntimeException("❌ El nombre no puede estar vacío");
            }
            if (productoActualizado.getNombre().length() > 250) {
                throw new RuntimeException("❌ El nombre no puede tener más de 250 caracteres");
            }
            productoExistente.setNombre(productoActualizado.getNombre());
        }

        // Validar y actualizar precio si viene
        if (productoActualizado.getPrecio() != null) {
            if (productoActualizado.getPrecio() < 0) {
                throw new RuntimeException("❌ El precio debe ser mayor o igual a 0");
            }
            productoExistente.setPrecio(productoActualizado.getPrecio());
        }

        // Validar y actualizar stock si viene
        if (productoActualizado.getStock() != null) {
            if (productoActualizado.getStock() < 0) {
                throw new RuntimeException("❌ El stock no puede ser negativo");
            }
            if (productoActualizado.getStock() == 0) {
                System.out.println("⚠️ El producto no está disponible (stock = 0)");
            }
            productoExistente.setStock(productoActualizado.getStock());
        }

        // Validar y actualizar tamaño si viene
        if (productoActualizado.getTamano() != null) {
            if (productoActualizado.getTamano().trim().isEmpty()) {
                throw new RuntimeException("❌ El tamaño no puede estar vacío");
            }
            if (!productoActualizado.getTamano().matches("^\\d{1,4}ml$")) {
                throw new RuntimeException("❌ El tamaño debe estar en formato válido (ejemplo: '100ml')");
            }
            productoExistente.setTamano(productoActualizado.getTamano());
        }

        // Guardar producto actualizado
        return productoRepository.save(productoExistente);
    }

    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }

}
