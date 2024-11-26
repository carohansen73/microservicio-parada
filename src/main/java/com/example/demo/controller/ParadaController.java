package com.example.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.*;

import com.example.demo.DTO.MonopatinDTO;
import com.example.demo.DTO.ParadaDTO;
import com.example.demo.DTO.ParadaDistanciaDTO;
import com.example.demo.DTO.PostParadaDTO;
import com.example.demo.DTO.UbicacionDTO;
import com.example.demo.modelo.Parada;
import com.example.demo.service.ParadaService;
import com.example.demo.utils.Ubicacion;


@RestController
@RequestMapping("paradas")
public class ParadaController {

	@Autowired
	private final ParadaService paradaService;


	public ParadaController(ParadaService paradaService) {
		super();
		this.paradaService = paradaService;
	}

	@Operation(summary = "Obtener todas las paradas", 
			description = "Obtiene una lista de todas las paradas registradas.")
	@ApiResponse(responseCode = "200", description = "Lista de paradas")
	@GetMapping("/")
	public ResponseEntity<Iterable<Parada>> getAll() {
		Iterable<Parada> paradas = paradaService.getAll();
		return new ResponseEntity<>(paradas, HttpStatus.OK);
	}
	
	@Operation(summary = "Obtener una parada", 
			description = "Obtiene la parada con el id especificado.")
	@GetMapping("/{id}")
	public ResponseEntity<Parada> findById(@PathVariable Integer id) {
		try {
			return new ResponseEntity<Parada>(paradaService.findById(id).orElseThrow(),HttpStatus.OK);
		}catch(NoSuchElementException e) {
			
			return new ResponseEntity<Parada>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Crear una nueva parada", 
			description = "Crea una nueva parada con los datos proporcionados.")
	@ApiResponse(responseCode = "201", description = "Parada creada")
	@PostMapping("/")
	public ResponseEntity<String> save(@RequestBody PostParadaDTO dto) {
		return paradaService.save(dto);
	}

	@Operation(summary = "Obtener un monopatin de una parada", 
			description = "Obtiene el id de un monopatin disponible en la parada especificada y lo saca de la parada.")
	@ApiResponse(responseCode = "200", description = "ID del monopatín obtenido")
	@PutMapping("/{idParada}/usar-monopatin")
	public ResponseEntity<?> useMonopatin(@Parameter(description = "ID de la parada") 
	@PathVariable Integer idParada) {
		return paradaService.usarMonopatin(idParada);
	}

	@Operation(summary = "Eliminar una parada", 
			description = "Elimina la parada con el ID especificado.")
	@ApiResponse(responseCode = "200", description = "Parada eliminada")
	@ApiResponse(responseCode = "400", description = "La parada contiene monopatines y no puede ser eliminada")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@Parameter(description = "ID de la parada a eliminar") 
	@PathVariable Integer id) {
		return paradaService.delete(id);
	}

	@Operation(summary = "Modificar los detalles de una parada", 
			description = "Permite modificar los detalles de una parada existente.")
	@ApiResponse(responseCode = "200", description = "Parada modificada")
	@PatchMapping("/{id}")
	public ResponseEntity<?> modificarParada(@Parameter(description = "Datos de la parada a modificar") 
	@RequestBody PostParadaDTO parada, 
	@Parameter(description = "ID de la parada") 
	@PathVariable Integer id) {
		return paradaService.modificarParada(id, parada);
	}
	
	@Operation(summary = "Estacionar un monopatín", 
			description = "Estaciona un monopatín la parada mas cercana.")
	@ApiResponse(responseCode = "200", description = "Monopatín estacionado")
	@PostMapping("/estacionarMonopatin")
	public ResponseEntity<?> estacionarMonopatin(
	@Parameter(description = "Monopatin a Estacionar") 
	@RequestBody MonopatinDTO monopatin) {
		return paradaService.estacionarMonopatin(monopatin);
	}

	@Operation(summary = "Obtener paradas cercanas con monopatines disponibles", 
			description = "Obtiene una lista de paradas que tienen monopatines a menos de 500 metros de distancia")
	@ApiResponse(responseCode = "200", description = "Lista de paradas cercanas con monopatines disponibles")
	@GetMapping("/cercanas")
	public ResponseEntity<List<ParadaDistanciaDTO>> getCantEnParadasCercanas(@RequestBody UbicacionDTO ubicacion) {
		return this.paradaService.findWithinRange(ubicacion.getLatitud(), ubicacion.getLongitud(), 500);
	}

	
	/*
	@Operation(summary = "Retirar un monopatín de una parada", 
			description = "Permite retirar un monopatín de la parada especificada.")
	@ApiResponse(responseCode = "200", description = "Monopatín retirado")
	@DeleteMapping("/monopatin/{id}")
	public ResponseEntity<String> sacarMonopatin(@Parameter(description = "ID del monopatín a retirar") 
	@PathVariable Integer id) {
		return paradaService.sacarMonopatin(id);
	}

	*/
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

	/*
	//parada mas cercana que tenga al menos  1 monopatin (lat y long)
	@GetMapping
	public List<Parada> findParadaMasCercana(long latitud, long longitud){
		return paradaService.findParadaMasCercana(latitud, longitud);
	}*/

	//estacionar o agregar un monopatin nuevo

}
