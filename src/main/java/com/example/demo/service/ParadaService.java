package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.DTO.MonopatinDTO;
import com.example.demo.DTO.ParadaDistanciaDTO;
import com.example.demo.DTO.PostParadaDTO;
import com.example.demo.Repository.MonopatinParadaRepository;
import com.example.demo.Repository.ParadaRepository;
import com.example.demo.modelo.MonopatinParada;
import com.example.demo.modelo.Parada;
import com.example.demo.utils.GenericObjectPatcher;
import com.example.demo.utils.Ubicacion;


@Service
public class ParadaService {

	@Autowired
	private final ParadaRepository repository;
	@Autowired
	private MonopatinParadaRepository monopatinParadaRepository;
	@Autowired
	private final RestTemplate restTemplate;
	
	@Value("${baseURLMonopatin}")
	private final String baseURLMonopatin;
	
	public ParadaService(ParadaRepository repository, MonopatinParadaRepository monopatinParadaRepository, RestTemplate restTemplate,String baseURLMonopatin) {
		this.repository = repository;
		this.monopatinParadaRepository = monopatinParadaRepository;
		this.restTemplate = restTemplate;
		this.baseURLMonopatin=baseURLMonopatin;
	}
	
	public Iterable<Parada> getAll(){
		return repository.findAll();
	}
	
	public ResponseEntity<String> save(PostParadaDTO dto) {
		Parada parada = new Parada(dto);
		repository.save(parada);
		return new ResponseEntity<String>(HttpStatus.CREATED);
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

    /*
	public Parada findByGPS(GPSDTO gpsDTO) {
		return repository.findByLatitudAndLongitud(gpsDTO.getLatitud(),gpsDTO.getLongitud());
	}
*/
	public ResponseEntity<?> estacionarMonopatin(Integer idParada, Integer idMonopatin) {
		//TODO setear el id de la parada en el monopatin
		//se puede setear haciendo un llamado desde aca o
		//que el servicio de monopatin haga esta request y si sale bien cambie el monopatin
		String url = baseURLMonopatin + "/" + idMonopatin;
		ResponseEntity<MonopatinDTO> response = restTemplate.getForEntity(url, MonopatinDTO.class);
		Optional<Parada> OptionalParada = repository.findById(idParada);
		
		
		//la parada o el monopatin no existen
		if(response.getStatusCode() != HttpStatus.OK || OptionalParada.isEmpty()) {
			return (ResponseEntity<?>) ResponseEntity.notFound();
		}
		
		Parada parada = OptionalParada.get();
		MonopatinDTO monopatinDTO = response.getBody();
		
		Ubicacion ubicacionMonopatin = new Ubicacion(monopatinDTO.getLatitud(),monopatinDTO.getLongitud());
		Ubicacion ubicacionParada = new Ubicacion(parada.getLatitud(),parada.getLongitud());
		//el monopatin no esta en la parada
		
		if(!ubicacionMonopatin.isInLocation(ubicacionParada)) {
			ResponseEntity<?> respuesta = new ResponseEntity(HttpStatus.BAD_REQUEST);
			return respuesta;
		}

		//Persiste en la tabla MonopatinParada
		MonopatinParada monopatinParada = new MonopatinParada(idMonopatin, idParada);
		this.monopatinParadaRepository.save(monopatinParada);
		
		ResponseEntity<?> respuesta = new ResponseEntity(HttpStatus.CREATED);
		return respuesta;
	}
	
	public ResponseEntity<List<ParadaDistanciaDTO>> findWithinRange (double latitud, double longitud, double distanciaMax){
		List<ParadaDistanciaDTO> results = this.repository.findWithinRange(latitud, longitud, distanciaMax);
		return new ResponseEntity<List<ParadaDistanciaDTO>>(results,HttpStatus.OK);
	}

	public ResponseEntity<?> modificarParada(Integer id, PostParadaDTO paradaDTO) {
		if((paradaDTO.getId()!=null)) {
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

	public ResponseEntity<String> sacarMonopatin(Integer idMonopatin) {
		this.monopatinParadaRepository.deleteById(idMonopatin);
		return ResponseEntity.ok(null);
	}
	
}
