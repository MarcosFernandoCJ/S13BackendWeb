package com.tecup.s13_backend.controller;

import com.tecup.s13_backend.exceptiom.ResourceNotFoundException;
import com.tecup.s13_backend.model.Producto;
import com.tecup.s13_backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Listar todos los productos
    @GetMapping("/productos")
    public List<Producto> ListarProductos() {
        return productoRepository.findAll();
    }

    // Guardar un producto
    @PostMapping("/productos")
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // Obtener producto por ID
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> ListarProductoPorId(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return ResponseEntity.ok(producto);
    }

    // Actualizar un producto
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> ActualizarProducto(@PathVariable Long id, @RequestBody Producto productoRequest) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        producto.setNombre(productoRequest.getNombre());
        producto.setDescripcion(productoRequest.getDescripcion());
        producto.setPrecio(productoRequest.getPrecio());
        producto.setCantidad(productoRequest.getCantidad());

        Producto productoActualizado = productoRepository.save(producto);
        return ResponseEntity.ok(productoActualizado);
    }

    // Eliminar un producto
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>> EliminarProducto(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        productoRepository.delete(producto);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Eliminado", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
