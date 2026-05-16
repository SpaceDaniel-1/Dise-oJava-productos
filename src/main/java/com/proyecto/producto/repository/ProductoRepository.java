package com.proyecto.producto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.producto.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Método para listar solo los productos activos
    List<Producto> findByActivoTrue();
    
}
