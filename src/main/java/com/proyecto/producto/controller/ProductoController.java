package com.proyecto.producto.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.producto.dto.ProductoDTO;
import com.proyecto.producto.model.Producto;
import com.proyecto.producto.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody ProductoDTO dto, Principal principal) {
        String usuario = obtenerUsuario(principal);
        Producto producto = productoService.crear(dto, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> modificar(@PathVariable Long id, @RequestBody ProductoDTO dto, Principal principal) {
        String usuario = obtenerUsuario(principal);
        Producto producto = productoService.modificar(id, dto, usuario);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, Principal principal) {
        String usuario = obtenerUsuario(principal);
        productoService.eliminar(id, usuario);
        return ResponseEntity.noContent().build();
    }

    private String obtenerUsuario(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return "usuario_desconocido";
    }
}
