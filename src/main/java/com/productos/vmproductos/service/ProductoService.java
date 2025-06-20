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
    public List<Producto> getAllProductos(){
        return productoRepository.findAll();
    }

    public Producto findById(Integer id){
        return productoRepository.findById(id).get();
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

                    public Producto update(Integer id, Producto productoActualizado) {
                        Producto productoExistente = productoRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID " + id));

                        // Reglas de negocio: proteger el ID
                        if (productoActualizado.getIdProducto() != null && !productoActualizado.getIdProducto().equals(id)) {
                            throw new RuntimeException("No se puede cambiar el ID del producto");
                        }

                        // Actualizar campos permitidos
                        productoExistente.setNombre(productoActualizado.getNombre());
                        productoExistente.setPrecio(productoActualizado.getPrecio());
                        productoExistente.setStock(productoActualizado.getStock());
                        productoExistente.setTamano(productoActualizado.getTamano());

                        return productoRepository.save(productoExistente); // ¡Usa el existente!
                    }

    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }

}
