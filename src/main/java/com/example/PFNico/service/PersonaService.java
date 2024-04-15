package com.example.PFNico.service;

import org.springframework.stereotype.Service;
import org.webservicesoap.persona.PersonaRequest;
import org.webservicesoap.persona.PersonaResponse;

import java.sql.*;
import java.util.logging.Logger;

@Service
public class PersonaService {
    String url = "jdbc:sqlite:persona.db";
    static Logger logger = Logger.getLogger(PersonaService.class.getName());
    public PersonaService(){
        crearTablaBD();
        insertarPersona("Nico", "Perez Funes", 42164425, 123456789, "nico@hotmail.com","25/10/1999","M");
        insertarPersona("Agustin", "Vicens", 42164425, 123456780, "agustin@hotmail.com","25/10/1999","M");
    }

    public PersonaResponse consultar(PersonaRequest request){
        PersonaResponse PersonaResponse = new PersonaResponse();
        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM persona WHERE dni = ?")) {
            pstmt.setString(1, String.valueOf(request.getDni()));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                PersonaResponse.setDni(rs.getInt("dni"));
                PersonaResponse.setNombre(rs.getString("nombre"));
                PersonaResponse.setApellido(rs.getString("apellido"));
                PersonaResponse.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                PersonaResponse.setMail(rs.getString("mail"));
                PersonaResponse.setTelefono(rs.getInt("telefono"));
                PersonaResponse.setSexo(rs.getString("sexo"));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            //PersonaResponse.setStatus(false);
        }
        return PersonaResponse;
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

    public void insertarPersona(String nombre, String apellido, int dni, int telefono, String mail, String fecha_nacimiento, String sexo) {
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
}
