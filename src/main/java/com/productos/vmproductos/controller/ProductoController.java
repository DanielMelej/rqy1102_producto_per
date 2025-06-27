package com.productos.vmproductos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.productos.vmproductos.model.Producto;
import com.productos.vmproductos.service.ProductoService;

@RestController
@RequestMapping("${app.api.base-url}/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    // @GetMapping
    // public ResponseEntity<List<Producto>> listar() {
    //     List<Producto> productos = productoService.getAllProductos();
    //     if (productos.isEmpty()) {
    //         return ResponseEntity.noContent().build();
    //     }
    //     return ResponseEntity.ok(productos);
    // }

    @GetMapping
    public ResponseEntity<List<Producto>> listar(@RequestParam(required = false) String nombre) {
        List<Producto> productos;
        
        if (nombre != null && !nombre.isBlank()) {
            productos = productoService.buscarPorNombre(nombre);
        } else {
            productos = productoService.getAllProductos();
        }

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Integer id) {
        try {
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Producto producto) {
        try {
            Producto saveProducto = productoService.save(producto);
            return ResponseEntity.ok(saveProducto);
        } catch (RuntimeException e) {
            // Devuelve el mensaje de error personalizado con código 400 (Bad Request)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Si ocurre otro error inesperado
            return ResponseEntity.status(500).body("Error interno al crear el producto");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Producto productoActualizado) {
        try {
            Producto producto = productoService.update(id, productoActualizado);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            // Errores de validación o lógica de negocio
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Otros errores inesperados
            return ResponseEntity.status(500).body("❌ Error interno al actualizar el producto.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
