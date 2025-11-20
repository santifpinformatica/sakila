import java.util.List;
import java.util.Scanner;

import dao.ActorDAO;
import dto.ActorDTO;

public class Videoclub {

    private static final Scanner sc = new Scanner(System.in);
    private static final ActorDAO actorDAO = new ActorDAO();

    public static void main(String[] args) throws Exception {


        int opcion = 0;

        while (opcion != 6) {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1 -> listarActores();
                case 2 -> buscarActorPorId();
                case 3 -> crearActor();
                case 4 -> actualizarActor();
                case 5 -> borrarActor();
                case 6 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        }

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú de Actores ---");
        System.out.println("1. Encontrar todos los actores");
        System.out.println("2. Buscar actor por ID");
        System.out.println("3. Crear actor");
        System.out.println("4. Actualizar actor");
        System.out.println("5. Borrar actor");
        System.out.println("6. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void listarActores() {
        List<ActorDTO> actores = actorDAO.findAll();
        for(ActorDTO actor : actores){
            System.out.println(actor);
        }
    }

    private static void buscarActorPorId() {
        System.out.print("Introduce ID del actor: ");
        int id = sc.nextInt();
        sc.nextLine();
        ActorDTO actor = actorDAO.findById(id);
        if (actor != null) {
            System.out.println(actor);
        } else {
            System.out.println("Actor no encontrado.");
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
        actorDAO.create(nuevo);
        System.out.println("Actor creado con ID: " + nuevo.getActorId());
    }

    private static void actualizarActor() {
        System.out.print("Introduce ID del actor a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();
        ActorDTO actor = actorDAO.findById(id);
        if (actor != null) {
            System.out.print("Nuevo nombre (" + actor.getFirstName() + "): ");
            String nombre = sc.nextLine();
            System.out.print("Nuevo apellido (" + actor.getLastName() + "): ");
            String apellido = sc.nextLine();
            actor.setFirstName(nombre.isEmpty() ? actor.getFirstName() : nombre);
            actor.setLastName(apellido.isEmpty() ? actor.getLastName() : apellido);
            actorDAO.update(actor);
            System.out.println("Actor actualizado.");
        } else {
            System.out.println("Actor no encontrado.");
        }
    }

    private static void borrarActor() {
        System.out.print("Introduce ID del actor a borrar: ");
        int id = sc.nextInt();
        sc.nextLine();
        actorDAO.delete(id);
        System.out.println("Actor borrado si existía.");
    }
}
