package com.example.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.DTO.CambiarParadaDTO;
import com.example.demo.DTO.MonopatinDTO;
import com.example.demo.DTO.ParadaDistanciaDTO;
import com.example.demo.DTO.PostParadaDTO;
import com.example.demo.Repository.MonopatinParadaRepository;
import com.example.demo.Repository.ParadaRepository;
import com.example.demo.Security.TokenHandler;
import com.example.demo.modelo.MonopatinParada;
import com.example.demo.modelo.Parada;
import com.example.demo.utils.GenericObjectPatcher;
import com.example.demo.utils.Ubicacion;

import jakarta.persistence.Tuple;


@Service
public class ParadaService {

	@Autowired
	private final ParadaRepository repository;
	@Autowired
	private MonopatinParadaRepository monopatinParadaRepository;
	@Autowired
	private final RestTemplate restTemplate;
	@Autowired
	private WebClient webClient;
	@Autowired
	private TokenHandler tokenHandler;
	
	@Value("${baseURLMonopatin}")
	private String baseURLMonopatin;
	
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
		
		try {
			repository.save(parada);;
    		return new ResponseEntity<String>("agregado", HttpStatus.CREATED);
    	}catch(IllegalArgumentException e) {
    		return  new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    	}
	}
	
	public Optional<Parada> findById(Integer paradaId) {
		return repository.findById(paradaId);
	}
	
	public Parada addMonopatin(Integer paradaId, Integer monopatinId) {
		Optional<Parada> paradaOpcional = repository.findById(paradaId);
		if(paradaOpcional.isPresent()) {
			Parada parada = paradaOpcional.get();
			parada.addMonopatin(monopatinId);
			return repository.save(parada);
		}else {
			  throw new IllegalArgumentException("Parada no encontrada.");
		}
	}
	
	
	public ResponseEntity<?> usarMonopatin(Integer paradaId) {
		Optional<Parada> paradaOpcional = repository.findById(paradaId);
		
		try {
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
			
			return new ResponseEntity<Integer>(monopatinID,HttpStatus.OK);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND); 
		}
		
	}
	
	public ResponseEntity<String> delete(Integer paradaId) {
		Optional<Parada> paradaOpcional = repository.findById(paradaId);  
		
		//la parada no existe
		if(paradaOpcional.isEmpty()) {
			return new ResponseEntity<String>("Parada no encontrada.",HttpStatus.NOT_FOUND);
		}
		
		Parada parada = paradaOpcional.get();
		
		//si la parada tiene monopatines no la elimina
		if(parada.tieneMonopatinesEstacionados()) {
			return new ResponseEntity<String>("No se puede eliminar la parada proque contiene monopatines.",HttpStatus.CONFLICT);
		} 
		
		//Si no tiene monopatines la elimina
		repository.deleteById(paradaId);
		return ResponseEntity.ok(null);
	}

	/*
	public List<Parada> findParadaMasCercana(long latitud, long longitud) {
		//TODO revisar si se puede ordenar en la query
        return (List<Parada>) repository.findAll().stream().sorted((p1, p2) -> Double.compare(
                calcularDistancia(latitud, longitud, p1.getLatitud(), p1.getLongitud()),
                calcularDistancia(latitud, longitud, p2.getLatitud(), p2.getLongitud())
            ))
            .collect(Collectors.toList());
    }*/

    private double calcularDistancia(long lat1, long lon1, double d, double e) {
        return Math.sqrt(Math.pow(d - lat1, 2) + Math.pow(e - lon1, 2));
    }
	/*
    public Boolean estaEnLaParada(long latitud, long longitud) {
        return repository.findByLatitudAndLongitud(latitud, longitud) != null;
    }
	*/
    /*
	public Parada findByGPS(GPSDTO gpsDTO) {
		return repository.findByLatitudAndLongitud(gpsDTO.getLatitud(),gpsDTO.getLongitud());
	}
*/
	public ResponseEntity<?> estacionarMonopatin(MonopatinDTO monopatin) {
		
		
		//paradas dentro de los 10 metros de rango
		List<Tuple> tuplasOrdenadas = this.repository.findWithinRange(monopatin.getLatitud(), monopatin.getLongitud(),10);
		if(tuplasOrdenadas.isEmpty()) {
			ResponseEntity<String> respuesta = new ResponseEntity<String>("El monopatin no se encuentra en una parada",HttpStatus.BAD_REQUEST);
			return respuesta;
		}
		
		
		
		Integer idParada = (Integer) tuplasOrdenadas.get(0).get("idParada");
		MonopatinParada monopatinParada = new MonopatinParada();
		monopatinParada.setIdMonopatin(monopatin.getIdMonopatin());
		monopatinParada.setParadaById(idParada);
		
		//Persiste en la tabla MonopatinParada
		this.monopatinParadaRepository.save(monopatinParada);
		//Devuelve en que parada se estaciono
		ResponseEntity<Integer> respuesta = new ResponseEntity<Integer>(idParada,HttpStatus.OK);
		return respuesta;
	}
	
	public ResponseEntity<List<ParadaDistanciaDTO>> findWithinRange (double latitud, double longitud, double distanciaMax){
		List<Tuple> results = this.repository.findWithinRange(latitud, longitud, distanciaMax);
		List<ParadaDistanciaDTO> resultsDTO = new ArrayList<ParadaDistanciaDTO>();
		for (Tuple tuple : results) {

			 Integer idParada = (Integer) tuple.get("idParada");
			 Integer cantidad = (Integer) tuple.get("cantidad",Long.class).intValue();
			ParadaDistanciaDTO dto = new ParadaDistanciaDTO(
					idParada,
		            tuple.get("nombre",String.class),
		            tuple.get("latitud",Double.class),
		            tuple.get("longitud",Double.class),
		            tuple.get("distancia",Double.class),
		            cantidad
		        );
		        
			resultsDTO.add(dto);
		}
		return new ResponseEntity<List<ParadaDistanciaDTO>>(resultsDTO,HttpStatus.OK);
	}

	public ResponseEntity<?> modificarParada(Integer id, PostParadaDTO paradaDTO) {
		if((paradaDTO.getId()!=null && paradaDTO.getId()!= id)) {
			return new ResponseEntity<String>("No se puede editar id",HttpStatus.BAD_REQUEST);
		}
		
		
		try {
			Parada parada = repository.findById(id).orElseThrow();
			GenericObjectPatcher.patch(new Parada(paradaDTO), parada);
			repository.save(parada);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<String>("id Invalido",HttpStatus.NOT_FOUND);
		}
	}

	/*
	public ResponseEntity<String> sacarMonopatin(Integer idMonopatin) {
		this.monopatinParadaRepository.deleteById(idMonopatin);
		return ResponseEntity.ok(null);
	}
	*/
}
