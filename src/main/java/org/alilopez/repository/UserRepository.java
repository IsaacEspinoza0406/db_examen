package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setIdUser(rs.getInt("idUser")); // <- nombre correcto de la columna
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                users.add(u);
            }
        }
        return users;
    }

    public User findByIdUser(int idUser) throws SQLException {
        User user = null;
        String query = "SELECT * FROM users WHERE idUser = ?"; // <- importante

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUser);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setIdUser(rs.getInt("idUser")); // <- nombre correcto
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                }
            }
        }
        return user;
    }


    public void save(User user) throws SQLException {
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.executeUpdate();
        }
    }


    // Actualizar usuario
    public void update(User user) throws SQLException {
        String query = "UPDATE users SET name = ?, email = ? WHERE idUser = ?"; // <- OJO
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getIdUser()); // <- getIdUser()
            stmt.executeUpdate();
        }
    }


    // Eliminar usuario
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM users WHERE idUser = ?"; // <- OJO
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }



}
