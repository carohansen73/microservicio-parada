package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.PostParadaDTO;
import com.example.demo.DTO.VerificacionParadaDTO;
import com.example.demo.modelo.Parada;
import com.example.demo.service.ParadaService;

import main.app.dto.GPSDTO;


@RestController
@RequestMapping("paradas")
public class ParadaController {

	@Autowired
	private final ParadaService paradaService;
	
	
	public ParadaController(ParadaService paradaService) {
		super();
		this.paradaService = paradaService;
	}

	@GetMapping("/")
	public ResponseEntity<Iterable<Parada>> getAll() {
		Iterable<Parada> paradas = paradaService.getAll();
		return new ResponseEntity<Iterable<Parada>>(paradas,HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<String> save(@PathVariable PostParadaDTO dto) {
		return paradaService.save(dto);
	}
	
	@PutMapping("/agregar-monopatin/{id}")
	public ResponseEntity<Parada> addMonopatin(@PathVariable Long paradaId, @RequestBody Long monopatinId) {
		Parada paradaActualizada = paradaService.addMonopatin(paradaId, monopatinId);
		return ResponseEntity.ok(paradaActualizada);
	}
	@PostMapping("/verificar-ubicacion")
    public boolean verificarUbicacion(@RequestBody VerificacionParadaDTO verificacionParadaDTO) {
        Parada parada = paradaService.findById(verificacionParadaDTO.getParadaId());
        if (parada == null) {
            throw new IllegalArgumentException("Parada no encontrada.");
        }

        GPSDTO gpsMonopatin = verificacionParadaDTO.getGps();
        GPSDTO gpsParada = parada.getGps(); 

        // Compara la latitud y longitud de la parada con la del monopatín
        return gpsMonopatin.getLatitud() == gpsParada.getLatitud()
            && gpsMonopatin.getLongitud() == gpsParada.getLongitud();
    }
}
