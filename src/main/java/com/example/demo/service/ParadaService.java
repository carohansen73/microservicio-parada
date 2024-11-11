package com.example.demo.service;

import java.util.Optional;

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
	
	
}
