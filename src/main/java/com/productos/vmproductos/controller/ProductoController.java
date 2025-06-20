package com.productos.vmproductos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productos.vmproductos.model.Producto;
import com.productos.vmproductos.repository.ProductoRepository;
import com.productos.vmproductos.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ProductoService productoService;


    @GetMapping
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.getAllProductos();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Integer id){
        try{
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto saveProducto = productoService.save(producto);
        return ResponseEntity.ok(saveProducto);
    }

        @PutMapping("/{id}")
        public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto productoActualizado) {
            try {
                Producto producto = productoService.update(id, productoActualizado);
                return ResponseEntity.ok(producto);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body(null);
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


    @PutMapping("/productos/{id}/stock/{nuevoStock}")
    public ResponseEntity<Void> actualizarStock(@PathVariable Integer id, @PathVariable Integer nuevoStock) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setStock(nuevoStock);
            productoRepository.save(producto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}

