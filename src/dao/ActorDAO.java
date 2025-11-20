package dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dto.ActorDTO;
import factory.ConnectionFactory;

public class ActorDAO {

    public void create(ActorDTO actor) {
        String sql = "INSERT INTO actor(first_name, last_name) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, actor.getFirstName());
            ps.setString(2, actor.getLastName());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    actor.setActorId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ActorDTO findById(int id) {
        String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE actor_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ActorDTO actor = new ActorDTO();
                    actor.setActorId(rs.getInt("actor_id"));
                    actor.setFirstName(rs.getString("first_name"));
                    actor.setLastName(rs.getString("last_name"));
                    return actor;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ActorDTO> findAll() {
        List<ActorDTO> actores = new ArrayList<>();
        String sql = "SELECT actor_id, first_name, last_name FROM actor";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ActorDTO actor = new ActorDTO();
                actor.setActorId(rs.getInt("actor_id"));
                actor.setFirstName(rs.getString("first_name"));
                actor.setLastName(rs.getString("last_name"));
                actores.add(actor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actores;
    }

    public void update(ActorDTO actor) {
        String sql = "UPDATE actor SET first_name = ?, last_name = ? WHERE actor_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, actor.getFirstName());
            ps.setString(2, actor.getLastName());
            ps.setInt(3, actor.getActorId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM actor WHERE actor_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ActorDTO> findByNameOrLastName(String nombre) {
    List<ActorDTO> actores = new ArrayList<>();
    String sql = "SELECT actor_id, first_name, last_name FROM actor " +
                 "WHERE first_name LIKE ? OR last_name LIKE ?";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        String filtro = "%" + nombre + "%";
        ps.setString(1, filtro);
        ps.setString(2, filtro);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ActorDTO actor = new ActorDTO();
                actor.setActorId(rs.getInt("actor_id"));
                actor.setFirstName(rs.getString("first_name"));
                actor.setLastName(rs.getString("last_name"));
                actores.add(actor);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return actores;
}


}