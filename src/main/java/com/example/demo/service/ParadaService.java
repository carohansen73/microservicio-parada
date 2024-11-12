package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.DTO.MonopatinDTO;
import com.example.demo.DTO.PostParadaDTO;
import com.example.demo.Repository.ParadaRepository;
import com.example.demo.modelo.Parada;

import main.app.dto.GPSDTO;

@Service
public class ParadaService {

	
	private final ParadaRepository repository;
	private final RestTemplate restTemplate;
	public ParadaService(ParadaRepository repository,RestTemplate restTemplate) {
		this.repository = repository;
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
	
	public Parada removeMonopatin(Integer paradaId, Integer monopatinId) {
		Optional<Parada> paradaOpcional = repository.findById(paradaId);
		if(paradaOpcional.isPresent()) {
			Parada parada = paradaOpcional.get();
			boolean eliminado = parada.getMonopatines().remove(monopatinId);
			
			if(!eliminado) {
				throw new IllegalArgumentException("Monopatín no encontrado en esta parada.");
			}
			return repository.save(parada);
		} else {
			throw new IllegalArgumentException("Parada no encontrada.");
		}
	}
	
	public Long useMonopatin(Integer paradaId) {
		Optional<Parada> paradaOpcional = repository.findById(paradaId);
		if(paradaOpcional.isPresent()) {
			Parada parada = paradaOpcional.get();
			
			//chequea que haya monopatines en la parada
			 if (!parada.getMonopatines().isEmpty()) {
		            // Remueve y guarda el primer monopatín
		            Long monopatinId = parada.getMonopatines().remove(0);
		            repository.save(parada);
		            return monopatinId;
			 } else {
		            throw new IllegalArgumentException("No hay monopatines en esta parada.");
		        }
		}else {
			 throw new IllegalArgumentException("Parada no encontrada.");
		}
	}
	
	public ResponseEntity<String> delete(Integer id) {
		repository.deleteById(id);
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
		if(!monopatinDTO.getGps().isInLocation(parada.getGps())) {
			ResponseEntity<?> respuesta = new ResponseEntity(HttpStatus.BAD_REQUEST);
			return respuesta;
		}
		
		parada.addMonopatin(idMonopatin);
		this.repository.save(OptionalParada.get());	
		ResponseEntity<?> respuesta = new ResponseEntity(HttpStatus.CREATED);
		
			
		
	}
	
}
