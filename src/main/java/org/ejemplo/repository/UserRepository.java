package org.ejemplo.repository;

import lombok.extern.slf4j.Slf4j;
import org.ejemplo.mappers.UsuarioMapper;
import org.ejemplo.modelos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Slf4j
@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate; //Objeto que nos permite ejecutar sentencias sobre a una base de datos

    @Autowired
    public UserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);// inicializamos el template con el objeto que contiene la configuración necesaria para conectarnos a nuestra base de datos
    }

    // metodo para guardar un usuario
    public Usuario guardarUsuario(Usuario usuario){
        String sql = "INSERT INTO p3.usuario (user, password, role) VALUES (?,?,?);";

        jdbcTemplate.update(sql, usuario.getUser(), usuario.getPassword(), usuario.getRole());
        return usuario;
    }

    // metodo para obtener todos los usuarios
    public List<Usuario> getAll(){
        String sql = "SELECT * FROM p3.usuario;";
        return jdbcTemplate.query(sql, new UsuarioMapper());
    }

    //metodo para eliminar un usuario por medio del parámetro user
    public void removeUser(String user) throws Exception {
        log.info("Eliminiando al usuario {}", user);
        String sql = "DELETE FROM p3.usuario where user = ?";

        int  rf = jdbcTemplate.update(sql, user);
        if (rf>0){
            log.info("Usuario borrado correctamente");
        }
        else {
            throw new Exception("Error al intentar borrar el usuario");
        }
    }
}
