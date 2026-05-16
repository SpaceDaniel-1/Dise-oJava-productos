package com.proyecto.producto.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.producto.dto.ProductoDTO;
import com.proyecto.producto.model.Producto;
import com.proyecto.producto.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // LISTAR TODOS LOS PRODUCTOS
    public List<Producto> listarTodos() {
        return productoRepository.findByActivoTrue();
    }

    // CREAR PRODUCTO
    public Producto crear(ProductoDTO dto) {

        Producto producto = new Producto();

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        producto.setActivo(true);

        return productoRepository.save(producto);
    }

    // MODIFICAR PRODUCTO
    public Producto modificar(Long id, ProductoDTO dto) {

        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        producto.setActivo(dto.getActivo());

        return productoRepository.save(producto);
    }

    // ELIMINAR PRODUCTO (BORRADO LOGICO)
    public void eliminar(Long id) {

        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        producto.setActivo(false);
        productoRepository.save(producto);
    }
}