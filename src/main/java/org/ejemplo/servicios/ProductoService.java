package org.ejemplo.servicios;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.ProductoException;
import org.ejemplo.exceptions.UserException;
import org.ejemplo.modelos.Login;
import org.ejemplo.modelos.Producto;
import org.ejemplo.modelos.Usuario;
import org.ejemplo.repository.ProductoRepository;
import org.ejemplo.repository.UsuarioRepository;
import org.ejemplo.validations.ProductoValidations;
import org.ejemplo.validations.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductoService {
    @Autowired
    ProductoRepository productoRepository;

    public String guardarProducto(Producto producto) throws ProductoException {
        ProductoValidations.validateProductoForCreate(productoRepository.findAll(), producto);
        return saveProduct(producto);
    }

    public String actualizarProducto(Producto producto) throws ProductoException {
        ProductoValidations.validateProductoForUpdate(productoRepository.findAll(), producto);
        return saveProduct(producto);
    }

    public Producto updateStock(String codigo, Integer cantidad){
        Producto producto = productoRepository.findById(codigo).orElseThrow();
        producto.setStock(producto.getStock()-cantidad);
        saveProduct(producto);
        return producto;
    }

    public List<Producto> retornarProductos(){
        return productoRepository.findAll();
    }

    public void borrarProductos(String codigo) throws ProductoException {
        ProductoValidations.validateProductoForDelete(productoRepository.findAll(), codigo);
        productoRepository.deleteById(codigo);
    }

    public Producto findProducto(String codigo) {
        return productoRepository.findById(codigo).orElse(null);
    }


    private String saveProduct(Producto producto) {
        producto.setFechaDeActualizacion(new Date());
        productoRepository.save(producto);
        return "producto cargado correctamente";
    }
}
