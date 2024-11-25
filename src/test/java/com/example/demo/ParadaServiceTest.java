package com.example.demo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Repository.MonopatinParadaRepository;
import com.example.demo.Repository.ParadaRepository;
import com.example.demo.modelo.Parada;
import com.example.demo.service.ParadaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

class ParadaServiceTest {

    @Mock
    private ParadaRepository paradaRepository;

    @Mock
    private MonopatinParadaRepository monopatinParadaRepository;

    @InjectMocks
    private ParadaService paradaService;

    public ParadaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMonopatinConParadaExistente() {
        Parada parada = new Parada("Parada Test", 10.0, 20.0);
        parada.setMonopatines(new ArrayList<>());
        when(paradaRepository.findById(1)).thenReturn(Optional.of(parada));
        when(paradaRepository.save(any())).thenReturn(parada);

        Parada resultado = paradaService.addMonopatin(1, 101);

        assertNotNull(resultado);
        assertEquals(1, resultado.getMonopatines().size());
        verify(paradaRepository).save(parada);
    }

    @Test
    void testAddMonopatinConParadaInexistente() {
        when(paradaRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paradaService.addMonopatin(1, 101);
        });

        assertEquals("Parada no encontrada.", exception.getMessage());
    }
}
