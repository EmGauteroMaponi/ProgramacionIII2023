package org.ejemplo.servicios;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static final String REGISTRO_CREAR_PRODUCTO = "Se registro el producto %s";
    private static final String REGISTRO_ACTUALIZAR_PRODUCTO = "Se actualiza el producto %s";
    private static final String REGISTRO_ACTUALIZAR_STOCK_PRODUCTO = "Nuevo consumo en el producto %s con la cantidad %d";
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    RegistroService registroService;

    public String guardarProducto(String user, Producto producto) throws ProductoException {
        ProductoValidations.validateProductoForCreate(productoRepository.findAll(), producto);
        return saveProduct(producto, user, String.format(REGISTRO_CREAR_PRODUCTO, producto.getCodigo()));
    }

    public String actualizarProducto(String user, Producto producto) throws ProductoException {
        ProductoValidations.validateProductoForUpdate(productoRepository.findAll(), producto);
        return saveProduct(producto, user, String.format(REGISTRO_ACTUALIZAR_PRODUCTO,producto.getCodigo()));
    }

    public Producto updateStock(String user, String codigo, Integer cantidad){
        Producto producto = productoRepository.findById(codigo).orElseThrow();
        producto.setStock(producto.getStock()-cantidad);
        saveProduct(producto, user, String.format(REGISTRO_ACTUALIZAR_STOCK_PRODUCTO, codigo, cantidad));
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


    private String saveProduct(Producto producto,String user, String accion) {
        producto.setFechaDeActualizacion(new Date());
        productoRepository.save(producto);
        registroService.registrar(accion, user, (new Date()).toString(), new ObjectMapper().convertValue(producto, new TypeReference<>() {}));
        return "producto cargado correctamente";
    }
}
