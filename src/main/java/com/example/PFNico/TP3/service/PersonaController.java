package com.example.PFNico.TP3.service;

import com.example.PFNico.TP2.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class PersonaController {
    String url = "jdbc:sqlite:persona.db";
    static Logger logger = Logger.getLogger(PersonaService.class.getName());

    @Autowired
    public PersonaController(){
        crearTablaBD();
        //insertarPersona("Nico", "Perez Funes", 42164425, 123456789, "nico@hotmail.com","25/10/1999","M");
        //insertarPersona("Agustin", "Vicens", 42164425, 123456780, "agustin@hotmail.com","25/10/1999","M");
    }

    @GetMapping("/usuarios")
    List<Usuario> getUsuarios() {
        List<Usuario> res = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM persona")) {
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                res.add(new Usuario(
                        rs.getInt("id"),
                        rs.getInt("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("fecha_nacimiento"),
                        rs.getString("mail"),
                        rs.getInt("telefono"),
                        rs.getString("sexo")
                ));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return res;
    }

    @GetMapping("/usuarios/{id}")
    Usuario getUsuario(@PathVariable int id) {
        Usuario res = new Usuario();
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM persona WHERE id = ?")) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                res.setId(rs.getInt("id"));
                res.setNombre(rs.getString("nombre"));
                res.setDni(rs.getInt("dni"));
                res.setSexo(rs.getString("sexo"));
                res.setApellido(rs.getString("apellido"));
                res.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                res.setMail(rs.getString("mail"));
                res.setTelefono(rs.getInt("telefono"));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return res;
    }

    @PostMapping("/usuarios")
    public void insertarPersona(@RequestParam String nombre, String apellido, int dni, int telefono, String mail, String fecha_nacimiento, String sexo) {
        String insertUserSQL = "INSERT INTO persona (nombre, apellido, dni, telefono, mail, fecha_nacimiento, sexo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertUserSQL)) {

            // Insertar usuario y contraseña en la base de datos
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setInt(3, dni);
            pstmt.setInt(4, telefono);
            pstmt.setString(5, mail);
            pstmt.setString(6, fecha_nacimiento);
            pstmt.setString(7, sexo);
            pstmt.executeUpdate();
            logger.info("Persona insertada correctamente");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    @PutMapping("/usuarios/{id}")
    public void modificarPersona(@RequestBody Usuario usuario, @PathVariable int id) {
        String updateUserSQL = "UPDATE persona SET nombre=?, apellido=?, dni=?, telefono=?, mail=?, fecha_nacimiento=?, sexo=? WHERE id=?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(updateUserSQL)) {

            // Insertar los datos del usuario en la base de datos
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setInt(3, usuario.getDni());
            pstmt.setInt(4, usuario.getTelefono());
            pstmt.setString(5, usuario.getMail());
            pstmt.setString(6, usuario.getFecha_nacimiento());
            pstmt.setString(7, usuario.getSexo());
            pstmt.setInt(8, id);
            pstmt.executeUpdate();
            logger.info("Persona modificada correctamente");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }


    @DeleteMapping("/usuarios/{id}")
    public void eliminarPersona(@PathVariable int id) {
        String updateUserSQL = "DELETE FROM persona WHERE id=?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(updateUserSQL)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Persona eliminada correctamente");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }


    public void crearTablaBD(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS persona ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL,"
                + "apellido TEXT NOT NULL,"
                + "fecha_nacimiento TEXT NOT NULL,"
                + "mail TEXT NOT NULL,"
                + "dni INTEGER NOT NULL,"
                + "sexo TEXT NOT NULL,"
                + "telefono INTEGER NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSQL);
            logger.info("Tabla persona creada exitosamente");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

}

