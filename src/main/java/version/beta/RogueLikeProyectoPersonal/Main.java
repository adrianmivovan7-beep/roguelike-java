package version.beta.RogueLikeProyectoPersonal;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    private static final String ARCHIVO_GUARDADO = "partida.ser";

    public static void main(String[] args) {
        try (Scanner teclado = new Scanner(System.in)) {
            while (true) {
                boolean hayPartida = new File(ARCHIVO_GUARDADO).exists();

                if (!hayPartida) {
                    System.out.println("\n========== MENU PRINCIPAL ==========");
                    System.out.println("1. Crear personaje y comenzar partida");
                    System.out.println("2. Salir del programa");

                    int opcion = leerOpcion(teclado, 1, 2);

                    if (opcion == 2) {
                        System.out.println("Saliendo del programa...");
                        return;
                    }

                    Hero hero = new Hero(pedirNombre(teclado));
                    ArrayList<Character> personajes = crearPersonajes();
                    Collections.shuffle(personajes);

                    guardarPartida(hero, personajes, false);
                    jugarPartida(teclado, hero, personajes, false);

                } else {
                    System.out.println("\n========== MENU PRINCIPAL ==========");
                    System.out.println("1. Nueva run");
                    System.out.println("2. Continuar partida");
                    System.out.println("3. Crear nuevo personaje");
                    System.out.println("4. Ver estadisticas");
                    System.out.println("5. Salir del programa");

                    int opcion = leerOpcion(teclado, 1, 5);

                    switch (opcion) {
                        case 1: {
                            Object[] partida = cargarPartida();

                            if (partida == null) {
                                break;
                            }

                            Hero hero = (Hero) partida[0];

                            hero.setVidaActual(hero.getVidaMaxima());
                            hero.setEscudo(0);

                            ArrayList<Character> personajes = crearPersonajes();
                            Collections.shuffle(personajes);

                            System.out.println("\nNueva run iniciada.");
                            System.out.println("Se conservan los datos del heroe.");
                            System.out.println("Se ha generado una nueva lista de enemigos.");

                            guardarPartida(hero, personajes, false);
                            jugarPartida(teclado, hero, personajes, false);
                            break;
                        }

                        case 2: {
                            Object[] partida = cargarPartida();

                            if (partida == null) {
                                break;
                            }

                            Hero hero = (Hero) partida[0];

                            @SuppressWarnings("unchecked")
                            ArrayList<Character> personajes =
                                    (ArrayList<Character>) partida[1];

                            boolean debeDefenderse = (Boolean) partida[2];

                            if (personajes.isEmpty()) {
                                System.out.println("\nNo quedan enemigos.");
                                System.out.println("Generando una nueva lista de enemigos...");

                                personajes = crearPersonajes();
                                Collections.shuffle(personajes);
                                debeDefenderse = false;

                                guardarPartida(hero, personajes, debeDefenderse);
                            }

                            jugarPartida(teclado, hero, personajes, debeDefenderse);
                            break;
                        }

                        case 3: {
                            System.out.println("\nAVISO");
                            System.out.println("Se borraran los datos del heroe anterior.");
                            System.out.println("Se creara un nuevo personaje con estadisticas a cero.");
                            System.out.print("Deseas continuar? (s/n): ");

                            String confirmacion = teclado.nextLine().trim();

                            if (confirmacion.equalsIgnoreCase("s")) {
                                Hero hero = new Hero(pedirNombre(teclado));
                                ArrayList<Character> personajes = crearPersonajes();
                                Collections.shuffle(personajes);

                                guardarPartida(hero, personajes, false);
                                jugarPartida(teclado, hero, personajes, false);
                            } else {
                                System.out.println("Operacion cancelada.");
                            }

                            break;
                        }

                        case 4:
                            mostrarEstadisticasGuardadas();
                            break;

                        case 5:
                            System.out.println("Saliendo del programa...");
                            return;

                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("\nHa ocurrido un error inesperado.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void jugarPartida(
            Scanner teclado,
            Hero hero,
            ArrayList<Character> personajes,
            boolean debeDefenderse) {

        if (hero.getVidaActual() <= 0) {
            System.out.println("\nTu heroe no tiene vida para continuar.");
            System.out.println("Puedes iniciar una nueva run desde el menu.");
            guardarPartida(hero, personajes, debeDefenderse);
            return;
        }

        while (hayEnemigos(personajes) && hero.getVidaActual() > 0) {
            Enemy enemigo = obtenerSiguienteEnemigo(personajes);

            if (enemigo == null) {
                break;
            }

            System.out.println("\n================================");
            System.out.println("        COMIENZA EL COMBATE");
            System.out.println("Heroe: " + hero.getNombre());
            System.out.println("Vida: " + hero.getVidaActual()
                    + "/" + hero.getVidaMaxima());
            System.out.println("Escudo: " + hero.getEscudo());
            System.out.println("Vida del enemigo: " + enemigo.getVidaActual()
                    + "/" + enemigo.getVidaMaxima());
            System.out.println("================================");

            while (enemigo.estaVivo() && hero.getVidaActual() > 0) {
                System.out.println("\n1. Atacar");
                System.out.println("2. Defenderse");
                System.out.println("3. Guardar y salir");
                System.out.print("Elige una opcion: ");

                int opcion = leerOpcion(teclado, 1, 3);

                if (opcion == 3) {
                    guardarPartida(hero, personajes, debeDefenderse);
                    System.out.println("Partida guardada. Hasta pronto!");
                    return;
                }

                if (opcion == 1) {
                    hero.attack(enemigo);
                } else {
                    hero.defenderse();
                }

                if (!enemigo.estaVivo()) {
                    break;
                }

                if (debeDefenderse) {
                    enemigo.defenderse();
                    debeDefenderse = false;
                } else {
                    enemigo.resetearDefensa();
                    enemigo.atacar(hero);
                    debeDefenderse = true;
                }

                guardarPartida(hero, personajes, debeDefenderse);
            }

            if (hero.getVidaActual() <= 0) {
                System.out.println("\nHas sido derrotado!");
                hero.setDerrotas(hero.getDerrotas() + 1);

                guardarPartida(hero, personajes, debeDefenderse);
                return;
            }

            if (!enemigo.estaVivo()) {
                personajes.remove(enemigo);
                debeDefenderse = false;

                System.out.println("\nHas derrotado al enemigo!");

                hero.curarse();

                System.out.println("Te has curado despues del combate.");
                System.out.println("Vida actual: " + hero.getVidaActual()
                        + "/" + hero.getVidaMaxima());

                guardarPartida(hero, personajes, debeDefenderse);
            }
        }

        if (hero.getVidaActual() > 0 && !hayEnemigos(personajes)) {
            System.out.println("\n================================");
            System.out.println("ENHORABUENA, " + hero.getNombre() + "!");
            System.out.println("Has derrotado a todos los enemigos.");
            System.out.println("================================");

            hero.setVictorias(hero.getVictorias() + 1);
            guardarPartida(hero, personajes, false);
        }

        System.out.println("\nPartida guardada.");
        System.out.println("Volviendo al menu principal...");
    }

    public static String pedirNombre(Scanner teclado) {
        System.out.print("Introduce el nombre de tu heroe: ");

        String nombre = teclado.nextLine().trim();

        return nombre.isEmpty() ? "Heroe" : nombre;
    }

    public static ArrayList<Character> crearPersonajes() {
        ArrayList<Character> personajes = new ArrayList<>();

        personajes.add(new Enemy(2, 6, 0));
        personajes.add(new Enemy(3, 8, 1));
        personajes.add(new Enemy(2, 10, 1));
        personajes.add(new Enemy(4, 10, 2));
        personajes.add(new Enemy(3, 12, 1));

        return personajes;
    }

    public static Enemy obtenerSiguienteEnemigo(
            ArrayList<Character> personajes) {

        for (Character personaje : personajes) {
            if (personaje instanceof Enemy) {
                Enemy enemigo = (Enemy) personaje;

                if (enemigo.estaVivo()) {
                    return enemigo;
                }
            }
        }

        return null;
    }

    public static boolean hayEnemigos(ArrayList<Character> personajes) {
        for (Character personaje : personajes) {
            if (personaje instanceof Enemy
                    && ((Enemy) personaje).estaVivo()) {
                return true;
            }
        }

        return false;
    }

    public static int leerOpcion(
            Scanner teclado,
            int minimo,
            int maximo) {

        while (true) {
            String entrada = teclado.nextLine().trim();

            try {
                int opcion = Integer.parseInt(entrada);

                if (opcion >= minimo && opcion <= maximo) {
                    return opcion;
                }

                System.out.print("Opcion no valida. Elige entre "
                        + minimo + " y " + maximo + ": ");

            } catch (NumberFormatException e) {
                System.out.print("Introduce un numero valido: ");
            }
        }
    }

    public static void mostrarEstadisticasGuardadas() {
        Object[] partida = cargarPartida();

        if (partida == null) {
            System.out.println("No se han podido cargar las estadisticas.");
            return;
        }

        Hero hero = (Hero) partida[0];

        @SuppressWarnings("unchecked")
        ArrayList<Character> personajes =
                (ArrayList<Character>) partida[1];

        System.out.println("\n========== ESTADISTICAS ==========");
        hero.mostrarEstadisticas();
        System.out.println("Enemigos restantes: " + personajes.size());
        System.out.println("==================================");
    }

    public static void guardarPartida(
            Hero hero,
            ArrayList<Character> personajes,
            boolean debeDefenderse) {

        try (ObjectOutputStream salida = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_GUARDADO))) {

            salida.writeObject(hero);
            salida.writeObject(personajes);
            salida.writeObject(debeDefenderse);

        } catch (Exception e) {
            System.out.println("No se ha podido guardar la partida: "
                    + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Object[] cargarPartida() {
        try (ObjectInputStream entrada = new ObjectInputStream(
                new FileInputStream(ARCHIVO_GUARDADO))) {

            Hero hero = (Hero) entrada.readObject();

            ArrayList<Character> personajes =
                    (ArrayList<Character>) entrada.readObject();

            boolean debeDefenderse = false;

            try {
                debeDefenderse = (Boolean) entrada.readObject();
            } catch (EOFException e) {
            }

            return new Object[]{hero, personajes, debeDefenderse};

        } catch (Exception e) {
            System.out.println("No se ha podido cargar la partida.");
            System.out.println("Comprueba que el archivo de guardado sea valido.");
            return null;
        }
    }
}
