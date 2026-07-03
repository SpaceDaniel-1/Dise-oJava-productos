package com.proyecto.producto.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.producto.dto.AuditEventDTO;
import com.proyecto.producto.dto.ProductoDTO;
import com.proyecto.producto.model.Producto;
import com.proyecto.producto.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private AuditService auditService;

    public List<Producto> listarTodos() {
        return productoRepository.findByActivoTrue();
    }

    public Producto crear(ProductoDTO dto, String usuario) {

        Producto producto = new Producto();

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        producto.setActivo(true);

        Producto productoGuardado = productoRepository.save(producto);

        auditService.enviarEvento(new AuditEventDTO(
                "CREAR",
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                usuario,
                Instant.now().toString()
        ));

        return productoGuardado;
    }

    public Producto modificar(Long id, ProductoDTO dto, String usuario) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());

        if (dto.getActivo() != null) {
            producto.setActivo(dto.getActivo());
        }

        Producto productoGuardado = productoRepository.save(producto);

        auditService.enviarEvento(new AuditEventDTO(
                "MODIFICAR",
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                usuario,
                Instant.now().toString()
        ));

        return productoGuardado;
    }

    public void eliminar(Long id, String usuario) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setActivo(false);
        Producto productoGuardado = productoRepository.save(producto);

        auditService.enviarEvento(new AuditEventDTO(
                "ELIMINAR",
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                usuario,
                Instant.now().toString()
        ));
    }
}