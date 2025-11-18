package main.java;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 * 
 * Implementa totes les funcionalitats demanades a l'enunciat:
 *  - Reservar habitacions amb serveis addicionals
 *  - Alliberar habitacions
 *  - Consultar disponibilitat
 *  - Consultar dades d'una reserva
 *  - Consultar reserves per tipus d'habitació
 */
public class hotel {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    // IVA
    public static final float IVA = 0.21f;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();
            opcio = llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus() {
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    static void mostrarMenu() {
        System.out.println();
        System.out.println("===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Consultar dades d'una reserva");
        System.out.println("5. Consultar reserves per tipus");
        System.out.println("6. Ixir");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    static void gestionarOpcio(int opcio) {
        switch (opcio) {
            case 1:
                reservarHabitacio();
                break;
            case 2:
                alliberarHabitacio();
                break;
            case 3:
                consultarDisponibilitat();
                break;
            case 4:
                obtindreReserva();
                break;
            case 5:
                obtindreReservaPerTipus();
                break;
            case 6:
                // es gestiona al main
                break;
            default:
                System.out.println("Opció no vàlida.");
        }
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    static void reservarHabitacio() {
        System.out.println("\n===== RESERVAR HABITACIÓ =====");

        String tipusHabitacio = seleccionarTipusHabitacioDisponible();
        if (tipusHabitacio == null) {
            System.out.println("No hi ha disponibilitat per al tipus seleccionat.");
            return;
        }

        ArrayList<String> serveisSeleccionats = seleccionarServeis();
        float preuTotal = calcularPreuTotal(tipusHabitacio, serveisSeleccionats);

        int codiReserva = generarCodiReserva();

        // Guardar la informació de la reserva
        ArrayList<String> dadesReserva = new ArrayList<String>();
        dadesReserva.add(tipusHabitacio);                    // posició 0
        dadesReserva.add(String.valueOf(preuTotal));          // posició 1

        // Posicions 2-5: serveis (o buits)
        for (int i = 0; i < 4; i++) {
            if (i < serveisSeleccionats.size()) {
                dadesReserva.add(serveisSeleccionats.get(i));
            } else {
                dadesReserva.add("");
            }
        }

        reserves.put(codiReserva, dadesReserva);

        // Actualitzar disponibilitat
        int disponibles = disponibilitatHabitacions.get(tipusHabitacio);
        disponibilitatHabitacions.put(tipusHabitacio, disponibles - 1);

        System.out.println("\nReserva creada amb èxit!");
        System.out.println("Codi de reserva: " + codiReserva);
        System.out.printf("Preu total: %.2f€\n", preuTotal);
    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    static String seleccionarTipusHabitacio() {
        System.out.println("Tipus d'habitació:");
        System.out.println("1. " + TIPUS_ESTANDARD);
        System.out.println("2. " + TIPUS_SUITE);
        System.out.println("3. " + TIPUS_DELUXE);

        int opcio;
        do {
            opcio = llegirEnter("Seleccione el tipus: ");
            if (opcio < 1 || opcio > 3) {
                System.out.println("Opció no vàlida. Torna-ho a intentar.");
            }
        } while (opcio < 1 || opcio > 3);

        if (opcio == 1) {
            return TIPUS_ESTANDARD;
        } else if (opcio == 2) {
            return TIPUS_SUITE;
        } else {
            return TIPUS_DELUXE;
        }
    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    static String seleccionarTipusHabitacioDisponible() {
        System.out.println("\nTipus d'habitació disponibles:");

        mostrarInfoTipus(TIPUS_ESTANDARD);
        mostrarInfoTipus(TIPUS_SUITE);
        mostrarInfoTipus(TIPUS_DELUXE);

        String tipusSeleccionat = seleccionarTipusHabitacio();

        int disponibles = disponibilitatHabitacions.get(tipusSeleccionat);
        if (disponibles > 0) {
            return tipusSeleccionat;
        } else {
            System.out.println("No queden habitacions disponibles d'aquest tipus.");
            return null;
        }
    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String.
     */
    static ArrayList<String> seleccionarServeis() {
        ArrayList<String> serveisSeleccionats = new ArrayList<String>();

        System.out.println("\nServeis addicionals (0-4):");
        System.out.println("1. " + SERVEI_ESMORZAR + " (" + preusServeis.get(SERVEI_ESMORZAR) + "€)");
        System.out.println("2. " + SERVEI_GIMNAS + " (" + preusServeis.get(SERVEI_GIMNAS) + "€)");
        System.out.println("3. " + SERVEI_SPA + " (" + preusServeis.get(SERVEI_SPA) + "€)");
        System.out.println("4. " + SERVEI_PISCINA + " (" + preusServeis.get(SERVEI_PISCINA) + "€)");

        boolean continuar = true;
        while (continuar && serveisSeleccionats.size() < 4) {
            System.out.print("Vol afegir un servei? (s/n): ");
            //buidar buffer 
            sc.nextLine();
            String resposta = sc.nextLine();

            if (resposta.equals("s")) {
                int opcioServei = llegirEnter("Seleccione servei (1-4): ");
                String servei = null;
                switch (opcioServei) {
                    case 1:
                        servei = SERVEI_ESMORZAR;
                        break;
                    case 2:
                        servei = SERVEI_GIMNAS;
                        break;
                    case 3:
                        servei = SERVEI_SPA;
                        break;
                    case 4:
                        servei = SERVEI_PISCINA;
                        break;
                    default:
                        System.out.println("Opció de servei no vàlida.");
                }

                if (servei != null) {
                    if (!serveisSeleccionats.contains(servei)) {
                        serveisSeleccionats.add(servei);
                        System.out.println("Servei afegit: " + servei);
                    } else {
                        System.out.println("Aquest servei ja ha sigut seleccionat.");
                    }
                }
            } else {
                continuar = false;
            }
        }

        return serveisSeleccionats;
    }

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveisSeleccionats) {
        float subtotal = 0f;

        // Preu de l'habitació
        Float preuHabitacio = preusHabitacions.get(tipusHabitacio);
        if (preuHabitacio != null) {
            subtotal += preuHabitacio;
        }

        // Preu dels serveis
        for (int i = 0; i < serveisSeleccionats.size(); i++) {
            String servei = serveisSeleccionats.get(i);
            Float preuServei = preusServeis.get(servei);
            if (preuServei != null) {
                subtotal += preuServei;
            }
        }

        float total = subtotal * (1 + IVA);
        return total;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        int codi;
        do {
            codi = 100 + random.nextInt(900); // 100-999
        } while (reserves.containsKey(codi));
        return codi;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
        int codi = llegirEnter("Introdueix el codi de reserva: ");

        ArrayList<String> dades = reserves.get(codi);
        if (dades == null) {
            System.out.println("No existeix cap reserva amb aquest codi.");
            return;
        }

        String tipusHabitacio = dades.get(0);
        // Incrementar disponibilitat
        int disponibles = disponibilitatHabitacions.get(tipusHabitacio);
        disponibilitatHabitacions.put(tipusHabitacio, disponibles + 1);

        // Eliminar reserva
        reserves.remove(codi);
        System.out.println("Reserva alliberada correctament.");
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    static void consultarDisponibilitat() {
        System.out.println("\n===== DISPONIBILITAT D'HABITACIONS =====");
        System.out.println("Tipus\t\tLliures\tOcupades");
        System.out.println("---------------------------------");

        mostrarDisponibilitatTipus(TIPUS_ESTANDARD);
        mostrarDisponibilitatTipus(TIPUS_SUITE);
        mostrarDisponibilitatTipus(TIPUS_DELUXE);
    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    static void llistarReservesPerTipus(int[] codis, String tipus) {
        if (codis == null || codis.length == 0) {
            return; // cas base
        }

        int primerCodi = codis[0];
        ArrayList<String> dades = reserves.get(primerCodi);
        if (dades != null) {
            String tipusReserva = dades.get(0);
            if (tipusReserva.equals(tipus)) {
                mostrarDadesReserva(primerCodi);
                System.out.println();
            }
        }

        // Crear nou vector sense la posició 0
        int[] newCodis = new int[codis.length - 1];
        System.arraycopy(codis, 1, newCodis, 0, newCodis.length);

        // Crida recursiva
        llistarReservesPerTipus(newCodis, tipus);
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        int codi = llegirEnter("Introdueix el codi de reserva: ");

        if (!reserves.containsKey(codi)) {
            System.out.println("No s'ha trobat cap reserva amb aquest codi.");
            return;
        }

        mostrarDadesReserva(codi);
    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        String tipus = seleccionarTipusHabitacio();

        if (reserves.isEmpty()) {
            System.out.println("Encara no hi ha reserves.");
            return;
        }

        // Obtindre tots els codis de reserva en un array
        int[] codis = new int[reserves.size()];
        int i = 0;
        for (Integer codi : reserves.keySet()) {
            codis[i] = codi.intValue();
            i++;
        }

        System.out.println("\nReserves del tipus \"" + tipus + "\":\n");
        llistarReservesPerTipus(codis, tipus);
    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    static void mostrarDadesReserva(int codi) {
        ArrayList<String> dades = reserves.get(codi);
        if (dades == null) {
            System.out.println("No s'ha trobat cap reserva amb el codi " + codi + ".");
            return;
        }

        String tipusHabitacio = dades.get(0);
        float preuTotal = Float.parseFloat(dades.get(1));

        System.out.println("Codi: " + codi);
        System.out.println("Tipus d'habitació: " + tipusHabitacio);
        System.out.printf("Cost total: %.2f€\n", preuTotal);

        System.out.println("Serveis addicionals:");
        boolean teServeis = false;
        for (int i = 2; i < dades.size(); i++) {
            String servei = dades.get(i);
            if (servei != null && !servei.equals("")) {
                System.out.println(" - " + servei);
                teServeis = true;
            }
        }
        if (!teServeis) {
            System.out.println(" (Sense serveis addicionals)");
        }
    }

    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
                System.out.print(missatge);
                valor = sc.nextInt();
                correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
    }
}
