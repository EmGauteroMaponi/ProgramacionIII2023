package org.ejemplo.controladores;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.ProductoException;
import org.ejemplo.modelos.Producto;
import org.ejemplo.servicios.AuthenticationService;
import org.ejemplo.servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@Slf4j
public class ProductoController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ProductoService service;

    @PostMapping("/producto/create")
    public ResponseEntity<String> createProducto(@RequestHeader String token, @RequestBody Producto producto){
        try{
            authenticationService.validarToken(token);
            String respuesta = service.guardarProducto (producto);
            log.info("Producto creado de forma correcta {}", producto.getCodigo());
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }  catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (ProductoException e){
            log.warn("No se esta cumpliendo con las validaciones. Producto a crear: {}", producto);
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @GetMapping("/producto/getAll")
    public ResponseEntity<?> getAllProducts(@RequestHeader String token){
        try {
            authenticationService.validarToken(token);
            return ResponseEntity.ok(service.retornarProductos());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("/producto/update")
    public ResponseEntity<String> updateProducto(@RequestHeader String token, @RequestBody Producto producto){
        try{
            authenticationService.validarToken(token);
            String respuesta = service.actualizarProducto (producto);
            log.info("Producto creado de forma correcta {}", producto.getCodigo());
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (ProductoException e){
            log.warn("No se esta cumpliendo con las validaciones. Producto a crear: {}", producto);
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }
    @DeleteMapping("/producto/delete/{codigo}")
    public ResponseEntity<String> deleteProducto(@RequestHeader String token,@PathVariable(value = "codigo") String codigo){
        try{
            authenticationService.validarToken(token);
            service.borrarProductos (codigo);
            log.info("Producto borrado de forma correcta {}", codigo);
            return ResponseEntity.status(HttpStatus.OK).body("Producto Borraddo de Forma Correcta");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (ProductoException e){
            log.warn("No se esta cumpliendo con las validaciones. Producto a eliminar: {}", codigo);
            return ResponseEntity.status(e.getStatusCode()).body(String.format("%s \n %s", e.getMessage(), e.getCausa()));
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }

    @GetMapping("/producto/find/{codigo}")
    public ResponseEntity<?> findProducto(@RequestHeader String token,@PathVariable(value = "codigo") String codigo){
        try{
            authenticationService.validarToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(service.findProducto(codigo));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e){
            log.error("Error: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ups!!! Algo salio mal, nuestro desarrolladores estan trabajando para solucionarlo");
        }
    }
}
