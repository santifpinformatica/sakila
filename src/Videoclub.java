import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.ActorDAO;
import dao.FilmActorDAO;
import dto.ActorDTO;
import factory.ConnectionFactory;

public class Videoclub {

    private static final Scanner sc = new Scanner(System.in);
    private static final ActorDAO actorDAO = new ActorDAO();
    private static final FilmActorDAO filmActorDAO = new FilmActorDAO();

    public static void main(String[] args) throws Exception {


        int opcion = 0;

        while (opcion != 7) {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine(); 
            switch (opcion) {
                case 1 -> listarActores();
                case 2 -> buscarActorPorId();
                case 3 -> buscarActorPorNombreOApellido();
                case 4 -> crearActor();
                case 5 -> actualizarActor();
                case 6 -> borrarActor();
  
                case 7 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        }

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú de Actores ---");
        System.out.println("1. Encontrar todos los actores");
        System.out.println("2. Buscar actor por ID");
        System.out.println("3. Buscar actor por nombre y apellidos");
        System.out.println("4. Crear actor");
        System.out.println("5. Actualizar actor");
        System.out.println("6. Borrar actor");
        System.out.println("7. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void listarActores() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            List<ActorDTO> actores = actorDAO.findAll(conn);
            for(ActorDTO actor : actores){
                System.out.println(actor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void buscarActorPorId() {
        System.out.print("Introduce ID del actor: ");
        int id = sc.nextInt();
        sc.nextLine();
        try (Connection conn = ConnectionFactory.getConnection()) {
            ActorDTO actor = actorDAO.findById(conn, id);
            if (actor != null) {
                System.out.println(actor);
            } else {
                System.out.println("Actor no encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buscarActorPorNombreOApellido() {
        System.out.print("Introduce nombre o apellido del actor: ");
        String nombreOApellido = sc.nextLine();
        try (Connection conn = ConnectionFactory.getConnection()) {
            List<ActorDTO> actores = actorDAO.findByNameOrLastName(conn, nombreOApellido);
            if (!actores.isEmpty()) {
                for (ActorDTO actor : actores) {
                    System.out.println(actor);
                }
            } else {
                System.out.println("No se encontraron actores con ese nombre o apellido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void crearActor() {
        System.out.print("Introduce nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Introduce apellido: ");
        String apellido = sc.nextLine();
        ActorDTO nuevo = new ActorDTO();
        nuevo.setFirstName(nombre);
        nuevo.setLastName(apellido);
        try (Connection conn = ConnectionFactory.getConnection()) {
            actorDAO.create(conn, nuevo);
            System.out.println("Actor creado con ID: " + nuevo.getActorId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void actualizarActor() {
        System.out.print("Introduce ID del actor a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();
        try (Connection conn = ConnectionFactory.getConnection()) {
            ActorDTO actor = actorDAO.findById(conn, id);
            if (actor != null) {
                System.out.print("Nuevo nombre (" + actor.getFirstName() + "): ");
                String nombre = sc.nextLine();
                System.out.print("Nuevo apellido (" + actor.getLastName() + "): ");
                String apellido = sc.nextLine();
                actor.setFirstName(nombre.isEmpty() ? actor.getFirstName() : nombre);
                actor.setLastName(apellido.isEmpty() ? actor.getLastName() : apellido);
                actorDAO.update(conn, actor);
                System.out.println("Actor actualizado.");
            } else {
                System.out.println("Actor no encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void borrarActor() {
        System.out.print("Introduce ID del actor a borrar: ");
        int id = sc.nextInt();
        sc.nextLine();
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                filmActorDAO.deleteByActorId(conn, id);
                actorDAO.delete(conn, id);
                conn.commit();
                System.out.println("Actor borrado si existía.");
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
