package org.ejemplo.mappers;

import org.ejemplo.modelos.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//Objeto utilizado para Mappear una fila de nuestra base de datos a un objeto creado en java (en este caso el objeto es "Usuario")
public class UsuarioMapper implements RowMapper<Usuario> {
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {//Sobrescribimos el método MapRow para crear nuestro objeto a partir del ResultSet
        Usuario usuario = new Usuario();
        usuario.setUser(rs.getString("user"));//por medio del método rs.getString pedimos que transforme el contenido de la columna "user" a un string, y lo asignamos al objeto usuario
        usuario.setPassword(rs.getString("password"));
        usuario.setRole(rs.getString("role"));
        return usuario;
    }
}
