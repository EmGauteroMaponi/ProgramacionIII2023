package org.ejemplo.servicios;

import org.ejemplo.modelos.Cliente;
import org.ejemplo.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ClienteServiceTest {

    @Test
    public void testUsuarioCorrecto(){
        ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);

        Cliente cliente =new Cliente();
        cliente.setNombre("Emanuel");
        cliente.setCuit(123L);
        cliente.setDni(123L);

        Mockito.when(clienteRepository.findAll()).thenReturn(List.of());

        ClienteService clienteService = new ClienteService(clienteRepository);

        assertDoesNotThrow(() -> clienteService.guardarCliente(cliente));
    }
    @Test
    public void testUsuarioCargadoAteriormente(){
        ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);

        Cliente cliente =new Cliente();
        cliente.setNombre("Emanuel");
        cliente.setCuit(123L);
        cliente.setDni(123L);

        Mockito.when(clienteRepository.findAll()).thenReturn(List.of(cliente));


        ClienteService clienteService = new ClienteService(clienteRepository);

        assertDoesNotThrow(() -> clienteService.guardarCliente(cliente));
    }
}
