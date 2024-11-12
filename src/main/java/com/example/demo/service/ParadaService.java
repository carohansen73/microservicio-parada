package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.DTO.PostParadaDTO;
import com.example.demo.Repository.ParadaRepository;
import com.example.demo.modelo.Parada;

@Service
public class ParadaService {

	
	private final ParadaRepository repository;
	
	public ParadaService(ParadaRepository repository) {
		this.repository = repository;
	}
	
	public Iterable<Parada> getAll(){
		return repository.findAll();
	}
	
	public ResponseEntity<String> save(PostParadaDTO dto) {
		Parada parada = new Parada(dto);
		repository.save(parada);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	public Parada addMonopatin(Long paradaId, Long monopatinId) {
		Optional<Parada> paradaOpcional = repository.findById(paradaId);
		if(paradaOpcional.isPresent()) {
			Parada parada = paradaOpcional.get();
			parada.getMonopatines().add(monopatinId);
			return repository.save(parada);
		}else {
			  throw new IllegalArgumentException("Parada no encontrada.");
		}
	}
	public List<Parada> findParadaMasCercana(long latitud, long longitud) {
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
	
}
