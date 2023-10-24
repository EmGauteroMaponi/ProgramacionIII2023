package org.ejemplo.servicios;

import org.ejemplo.exceptions.ClientException;
import org.ejemplo.exceptions.ProductoException;
import org.ejemplo.modelos.Cliente;
import org.ejemplo.repository.ClienteRepository;
import org.ejemplo.validations.ProductoValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.sql.SQLTimeoutException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteServiceTest {

    @Test
    public void testUsuarioCorrecto(){
        //Creamos un mock (objeto de simulación) que remplazará la conexión con la bases de datos
        ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);

        //Creamos el cliente que vamos a guardar
        Cliente cliente =new Cliente();
        cliente.setNombre("Emanuel");
        cliente.setCuit(123L);
        cliente.setDni(123L);

        //Cuando el servicio haga un findAll() sobre la base de datos, el objeto mock (de simulación) retornará una lista vacía
        Mockito.when(clienteRepository.findAll()).thenReturn(List.of());

        //Creamos el servicio cliente, indicando que el repositorio, será nuestro mock
        ClienteService clienteService = new ClienteService(clienteRepository);

        //Invocamos al servicio intentando guardar el cliente.
        //Al mismo tiempo verificamos que no exista ninguna exepción ya que el cliente cumple con las validaciones y no existe en la base de datos
        assertDoesNotThrow(() -> clienteService.guardarCliente(cliente));
    }
    @Test
    public void testUsuarioCargadoAteriormente(){
        //Creamos un mock (objeto de simulación) que remplazará la conexión con la bases de datos
        ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);

        //Creamos el cliente que vamos a guardar
        Cliente cliente =new Cliente();
        cliente.setNombre("Emanuel");
        cliente.setCuit(123L);
        cliente.setDni(123L);

        //Cuando el servicio haga un findAll() sobre la base de datos, el objeto mock (de simulación) retornará una lista con el mismo cliente que intentamos guardar
        Mockito.when(clienteRepository.findAll()).thenReturn(List.of(cliente));


        //Creamos el servicio cliente, indicando que el repositorio, será nuestro mock
        ClienteService clienteService = new ClienteService(clienteRepository);

        //Invocamos al servicio intentando guardar el cliente.
        //Al mismo tiempo verificamos que retorne un ClientException ya que el cliente ya existe en la base de datos
        ClientException exception = assertThrows(ClientException.class, () -> {
            clienteService.guardarCliente(cliente);
        });

        //validamos datos de la exepción
        assertEquals(HttpStatus.PRECONDITION_FAILED, exception.getStatusCode());
        assertEquals("No se puede ingresar el usuario Emanuel", exception.getMessage());
        assertEquals("Los campos [Nombre, Cuit, Dni] ya existen", exception.getCausa());
    }

    @Test
    public void testUsuarioCorrecteErrorALGuardarEnLaDB(){
        //Creamos un mock (objeto de simulación) que remplazará la conexión con la bases de datos
        ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);

        //Creamos el cliente que vamos a guardar
        Cliente cliente =new Cliente();
        cliente.setNombre("Emanuel");
        cliente.setCuit(123L);
        cliente.setDni(123L);

        //Cuando el servicio haga un findAll() sobre la base de datos, el objeto mock (de simulación) retornará una lista vacía
        Mockito.when(clienteRepository.findAll()).thenReturn(List.of());
        //Cuando el servicio intente guardar al cliente en la base de datos, el objeto mock (de simulación) retornará runtimeException simulando un error de conexión
        Mockito.when(clienteRepository.save(cliente)).thenThrow(new RuntimeException("Error en la conexion con la base de datos."));

        //Creamos el servicio cliente, indicando que el repositorio, será nuestro mock
        ClienteService clienteService = new ClienteService(clienteRepository);

        //Invocamos al servicio intentando guardar el cliente.
        //Al mismo tiempo verificamos que retorne una Exception, cascadeando el error de conexión que nos retorna el repositorio.
        Exception exception = assertThrows(Exception.class, () -> {
            clienteService.guardarCliente(cliente);
        });

        //validamos datos de la exepción
        assertEquals("Error en la conexion con la base de datos.", exception.getMessage());
    }
}
