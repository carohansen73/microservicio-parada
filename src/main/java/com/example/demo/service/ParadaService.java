package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.DTO.MonopatinDTO;
import com.example.demo.DTO.PostParadaDTO;
import com.example.demo.Repository.MonopatinParadaRepository;
import com.example.demo.Repository.ParadaRepository;
import com.example.demo.controller.ParadaDTO;
import com.example.demo.modelo.MonopatinParada;
import com.example.demo.modelo.Parada;

import main.app.dto.GPSDTO;

@Service
public class ParadaService {

	@Autowired
	private final ParadaRepository repository;
	@Autowired
	private MonopatinParadaRepository monopatinParadaRepository;
	@Autowired
	private final RestTemplate restTemplate;
	
	public ParadaService(ParadaRepository repository, MonopatinParadaRepository monopatinParadaRepository, RestTemplate restTemplate) {
		this.repository = repository;
		this.monopatinParadaRepository = monopatinParadaRepository;
		this.restTemplate = restTemplate;
	}
	
	public Iterable<Parada> getAll(){
		return repository.findAll();
	}
	
	public ResponseEntity<String> save(PostParadaDTO dto) {
		Parada parada = new Parada(dto);
		repository.save(parada);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	public Parada addMonopatin(Integer paradaId, Integer monopatinId) {
		Optional<Parada> paradaOpcional = repository.findById(paradaId);
		if(paradaOpcional.isPresent()) {
			Parada parada = paradaOpcional.get();
			parada.getMonopatines().add(monopatinId);
			return repository.save(parada);
		}else {
			  throw new IllegalArgumentException("Parada no encontrada.");
		}
	}
	
	
	public Integer usarMonopatin(Integer paradaId) {
		Optional<Parada> paradaOpcional = repository.findById(paradaId);
		
		//Chequea que exista la parada
		if(paradaOpcional.isEmpty()) {
			throw new IllegalArgumentException("Parada no encontrada.");
		}
		Parada parada = paradaOpcional.get();
		
		//chequea que haya monopatines en la parada
		 if (!parada.tieneMonopatinesEstacionados()) {
			 throw new IllegalArgumentException("No hay monopatines en esta parada.");
		 }
		 
        // Obtiene y remueve el primer monopatin
		 MonopatinParada monopatinParada = parada.getMonopatines().remove(0);
		 Integer monopatinID = monopatinParada.getIdMonopatin();
		 
		 //Actualiza la tabla monopatinParada
		 monopatinParadaRepository.delete(monopatinParada);
		 
		 return monopatinID;
		
	}
	
	public ResponseEntity<String> delete(Integer paradaId) {
		Optional<Parada> paradaOpcional = repository.findById(paradaId);  
		
		//la parada no existe
		if(paradaOpcional.isEmpty()) {
			throw new IllegalArgumentException("Parada no encontrada.");
		}
		
		Parada parada = paradaOpcional.get();
		
		//si la parada tiene monopatines no la elimina
		if(parada.tieneMonopatinesEstacionados()) {
			 throw new IllegalArgumentException("No se puede eliminar la parada proque contiene monopatines.");
		} 
		
		//Si no tiene monopatines la elimina
		repository.deleteById(paradaId);
		return ResponseEntity.ok(null);
	}

	
	public List<Parada> findParadaMasCercana(long latitud, long longitud) {
		//TODO revisar si se puede ordenar en la query
        return (List<Parada>) repository.findAll().stream().sorted((p1, p2) -> Double.compare(
                calcularDistancia(latitud, longitud, p1.getLatitud(), p1.getLongitud()),
                calcularDistancia(latitud, longitud, p2.getLatitud(), p2.getLongitud())
            ))
            .collect(Collectors.toList());
    }

    private double calcularDistancia(long lat1, long lon1, double d, double e) {
        return Math.sqrt(Math.pow(d - lat1, 2) + Math.pow(e - lon1, 2));
    }
	
    public Boolean estaEnLaParada(long latitud, long longitud) {
        return repository.findByLatitudAndLongitud(latitud, longitud) != null;
    }

	public Optional<Parada> findById(Integer paradaId) {
		return repository.findById(paradaId);
	}

	public Parada findByGPS(GPSDTO gpsDTO) {
		return repository.findByLatitudAndLongitud(gpsDTO.getLatitud(),gpsDTO.getLongitud());
	}

	public ResponseEntity<?> estacionarMonopatin(Integer idParada, Integer idMonopatin) {
		//TODO hacer url
		String url = "monopatines/" + idMonopatin;
		ResponseEntity<MonopatinDTO> response = restTemplate.getForEntity(url, MonopatinDTO.class);
		Optional<Parada> OptionalParada = repository.findById(idParada);
		
		
		//la parada o el monopatin no existen
		if(response.getStatusCode() != HttpStatus.OK || OptionalParada.isEmpty()) {
			return (ResponseEntity<?>) ResponseEntity.notFound();
		}
		
		Parada parada = OptionalParada.get();
		MonopatinDTO monopatinDTO = response.getBody();
		
		//el monopatin no esta en la parada
		/*TODO ubicacion con dtoGps o lat y long?
		 * if(!monopatinDTO.getGps().isInLocation(parada.getGps())) {
			ResponseEntity<?> respuesta = new ResponseEntity(HttpStatus.BAD_REQUEST);
			return respuesta;
		}
		*/
		//Persiste en la tabla MonopatinParada
		MonopatinParada monopatinParada = new MonopatinParada(idMonopatin, idParada);
		this.monopatinParadaRepository.save(monopatinParada);
		
		this.repository.save(OptionalParada.get());	
		ResponseEntity<?> respuesta = new ResponseEntity(HttpStatus.CREATED);
		return respuesta;
	}
	
}
