package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	/*
	@PutMapping("/agregar-monopatin/{id}")
	public ResponseEntity<Parada> addMonopatin(@PathVariable Integer paradaId, @RequestBody Long monopatinId) {
		//{idParada}/agregarMonopatin/{idMonopatin}
		Parada paradaActualizada = paradaService.addMonopatin(paradaId, monopatinId);
		return ResponseEntity.ok(paradaActualizada);
	}*/
	
	/*
	 * TODO recibe lat y long o un dto?
	 
	@PostMapping("/verificar-ubicacion")
    public boolean verificarUbicacion(@RequestBody GPSDTO gpsDTO) {
        Parada parada = paradaService.findByGPS(gpsDTO);
        if (parada == null) {
        	//TODO esto hace una respuesta o hayq ue hacer return responseEntity
            throw new IllegalArgumentException("Parada no encontrada.");
        }else{
        	return true;
        }
    }
*/
	
	//Recibo monopatinId o saco uno cualquiera?
	/*
	@PutMapping("/usar-monopatin/{id}")
	public ResponseEntity<Parada> removeMonopatin(@PathVariable Integer paradaId, @RequestBody Long monopatinId) {
		Parada paradaActualizada = paradaService.removeMonopatin(paradaId, monopatinId);
		return ResponseEntity.ok(paradaActualizada);
	}*/
	
	@PutMapping("/usar-monopatin/{id}")
	public ResponseEntity<Integer> useMonopatin(@PathVariable Integer paradaId) {
		Integer monopatinId = paradaService.usarMonopatin(paradaId);
		return ResponseEntity.ok(monopatinId);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id){
		return paradaService.delete(id);
	}
	
	//parada mas cercana que tenga al menos  1 monopatin (lat y long)
	@GetMapping
	public List<Parada> findParadaMasCercana(long latitud, long longitud){
		return paradaService.findParadaMasCercana(latitud, longitud);
	}
	
	@PostMapping("/{idParada}/estacionarMonopatin/{idMonopatin}")
	public ResponseEntity<?> estacionarMonopatin(@PathVariable Integer idParada, @PathVariable long idMonopatin){
		return paradaService.estacionarMonoPatin(idParada,idMonopatin);
	}

}
