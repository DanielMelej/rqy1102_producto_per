package com.productos.vmproductos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productos.vmproductos.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    List<Producto> findByNombre(String nombre);
    Producto findByPrecio(Float precio);
    Producto findByStock(Integer stock);
    
}
