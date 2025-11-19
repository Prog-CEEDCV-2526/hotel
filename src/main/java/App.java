package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * PLANTILLA — Gestió de reserves d'un hotel
 * 
 * COMPLETA TOTS ELS MÈTODES SEGONS L’ENUNCIAT.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    public static final float IVA = 0.21f;

    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<>();
    public static HashMap<String, Float> preusServeis = new HashMap<>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<>();

    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    public static void main(String[] args) {

        inicialitzarPreus(); // ⚠️ OBLIGATORI

        int opcio;
        do {
            mostrarMenu();
            opcio = llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema...");
    }

    // --------- MÈTODES A IMPLEMENTAR ---------

    public static void inicialitzarPreus() {
        // TODO: Omplir HashMaps amb preus, capacitat i disponibilitat inicial
    }

    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Consultar dades d'una reserva");
        System.out.println("5. Consultar reserves per tipus");
        System.out.println("6. Ixir");
    }

    public static void gestionarOpcio(int opcio) {
        switch (opcio) {
            case 1: reservarHabitacio(); break;
            case 2: alliberarHabitacio(); break;
            case 3: consultarDisponibilitat(); break;
            case 4: obtindreReserva(); break;
            case 5: obtindreReservaPerTipus(); break;
            case 6: break;
            default: System.out.println("Opció no vàlida.");
        }
    }

    public static void reservarHabitacio() {
        // TODO: Demanar tipus, serveis, calcular preu, generar codi i guardar reserva
    }

    public static String seleccionarTipusHabitacio() {
        // TODO: Preguntar 1-3 i retornar Estàndard, Suite o Deluxe
        return null;
    }

    public static String seleccionarTipusHabitacioDisponible() {
        // TODO: Mostrar disponibilitat i assegurar que hi ha habitacions lliures
        return null;
    }

    public static ArrayList<String> seleccionarServeis() {
        // TODO: Permetre triar fins a 4 serveis sense repetir
        return new ArrayList<>();
    }

    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveis) {
        // TODO: Sumar habitació + serveis + IVA
        return 0f;
    }

    public static int generarCodiReserva() {
        // TODO: Generar codi 100-999 que NO estiga repetit
        return 0;
    }

    public static void alliberarHabitacio() {
        // TODO: Demanar codi, tornar habitació i eliminar reserva
    }

    public static void consultarDisponibilitat() {
        // TODO: Mostrar lliures i ocupades
    }

    public static void llistarReservesPerTipus(int[] codis, String tipus) {
        // TODO: Implementar recursivitat
    }

    public static void obtindreReserva() {
        // TODO: Mostrar dades d'una reserva concreta
    }

    public static void obtindreReservaPerTipus() {
        // TODO: Llistar reserves per tipus
    }

    public static void mostrarDadesReserva(int codi) {
        // TODO: Imprimir tota la informació d'una reserva
    }

    // --------- MÈTODES AUXILIARS ---------

    static int llegirEnter(String msg) {
        System.out.print(msg);
        return sc.nextInt();
    }
}

