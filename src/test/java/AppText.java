import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.hotel;

import java.util.ArrayList;

public class AppText {

    @BeforeEach
    void setup() {
        hotel.preusHabitacions.clear();
        hotel.preusServeis.clear();
        hotel.capacitatInicial.clear();
        hotel.disponibilitatHabitacions.clear();
        hotel.reserves.clear();

        hotel.inicialitzarPreus();
    }

    @Test
    void testInicialitzarPreus() {
        assertEquals(50f, hotel.preusHabitacions.get(hotel.TIPUS_ESTANDARD));
        assertEquals(100f, hotel.preusHabitacions.get(hotel.TIPUS_SUITE));
        assertEquals(150f, hotel.preusHabitacions.get(hotel.TIPUS_DELUXE));

        assertEquals(30, hotel.disponibilitatHabitacions.get(hotel.TIPUS_ESTANDARD));
        assertEquals(20, hotel.disponibilitatHabitacions.get(hotel.TIPUS_SUITE));
        assertEquals(10, hotel.disponibilitatHabitacions.get(hotel.TIPUS_DELUXE));
    }

    private void assertEquals(float f, Float float1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    private void assertEquals(float f, Integer float1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    @Test
    void testCalcularPreuTotalSenseServeis() {
        ArrayList<String> serveis = new ArrayList<>();
        float total = hotel.calcularPreuTotal(hotel.TIPUS_ESTANDARD, serveis);
        assertEquals(50f * 1.21f, total, 0.001);
    }

    private void assertEquals(float f, float total, double d) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    @Test
    void testCalcularPreuTotalAmbServeis() {
        ArrayList<String> serveis = new ArrayList<>();
        serveis.add(hotel.SERVEI_ESMORZAR);  // 10€
        serveis.add(hotel.SERVEI_SPA);       // 20€

        float total = hotel.calcularPreuTotal(hotel.TIPUS_SUITE, serveis);

        float expected = (100 + 10 + 20) * 1.21f;
        assertEquals(expected, total, 0.001);
    }

    @Test
    void testGenerarCodiUnic() {
        int c1 = hotel.generarCodiReserva();
        int c2 = hotel.generarCodiReserva();
        assertNotEquals(c1, c2);
    }

    private void assertNotEquals(int c1, int c2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertNotEquals'");
    }
}
