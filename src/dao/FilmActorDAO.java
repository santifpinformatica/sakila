package dao;

import dto.FilmActorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FilmActorDAO {

    public void add(Connection conn, FilmActorDTO filmActor) {
        String sql = "INSERT INTO film_actor(film_id, actor_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, filmActor.getFilmId());
            ps.setInt(2, filmActor.getActorId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByFilmId(Connection conn, int filmId) {
        String sql = "DELETE FROM film_actor WHERE film_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, filmId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByActorId(Connection conn, int actorId) {
        String sql = "DELETE FROM film_actor WHERE actor_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, actorId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
