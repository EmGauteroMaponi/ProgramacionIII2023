package org.ejemplo.servicios;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.exceptions.ClientException;
import org.ejemplo.exceptions.UserException;
import org.ejemplo.modelos.Cliente;
import org.ejemplo.repository.ClienteRepository;
import org.ejemplo.validations.ClienteValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClienteService {
    private ClienteRepository repository;
    public ClienteService(ClienteRepository repository){
        this.repository = repository;
    }

    public String guardarCliente(Cliente cliente) throws ClientException {
        ClienteValidations.validateUserForRegister(repository.findAll(), cliente);
        repository.save(cliente);
        return "usuario cargado correctamente";
    }
    public Optional<Cliente> findById(Integer id){
        return repository.findById(id);
    }

    public List<Cliente> retornarUsuarios(){
        return repository.findAll();
    }

    public void borrarUsuarios(Integer id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
