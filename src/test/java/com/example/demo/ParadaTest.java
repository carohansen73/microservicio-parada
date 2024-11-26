package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.modelo.MonopatinParada;
import com.example.demo.modelo.Parada;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class ParadaTest {

    @Test
    void testTieneMonopatinesEstacionadosConListaNoVacia() {
        Parada parada = new Parada();
        List<MonopatinParada> monopatines = new ArrayList<>();
        monopatines.add(new MonopatinParada());
        parada.setMonopatines(monopatines);

        assertTrue(parada.tieneMonopatinesEstacionados());
    }

    @Test
    void testTieneMonopatinesEstacionadosConListaVacia() {
        Parada parada = new Parada();
        parada.setMonopatines(new ArrayList<>());

        assertFalse(parada.tieneMonopatinesEstacionados());
    }

    @Test
    void testAddMonopatin() {
        Parada parada = new Parada("Parada Test", 10.0, 20.0);
        parada.setMonopatines(new ArrayList<>());

        parada.addMonopatin(1);

        assertEquals(1, parada.getMonopatines().size());
        assertEquals(1, parada.getMonopatines().get(0).getIdMonopatin());
    }
}
