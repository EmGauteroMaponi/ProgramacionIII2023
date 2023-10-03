package org.ejemplo.validation;

import org.ejemplo.exceptions.ProductoException;
import org.ejemplo.modelos.Producto;
import org.ejemplo.validations.ProductoValidations;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoValidationTest {
    @Test
    public void validateCreateProduct(){
        Producto producto  = new Producto();
        producto.setCodigo("123");
        producto.setNombre("Prueba");
        producto.setPrecio(100.0);

        ProductoException exception = assertThrows(ProductoException.class, () -> {
            ProductoValidations.validateProductoForCreate(List.of(producto), producto);
        });

        assertEquals(HttpStatus.PRECONDITION_FAILED, exception.getStatusCode());
        assertEquals("No se puede ingresar el producto 123", exception.getMessage());
        assertEquals("El producto ya se encuentra registrado", exception.getCausa());
    }

    @Test
    public void validateValidExistProduct(){
        Producto producto  = new Producto();
        producto.setCodigo("123");
        producto.setNombre("Prueba");
        producto.setPrecio(100.0);

        assertDoesNotThrow(() -> ProductoValidations.validateProductoForCreate(List.of(), producto));

    }

}


