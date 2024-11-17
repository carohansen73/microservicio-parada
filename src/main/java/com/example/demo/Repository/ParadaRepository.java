package com.example.demo.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.DTO.ParadaDistanciaDTO;
import com.example.demo.modelo.Parada;


@Repository
public interface ParadaRepository extends JpaRepository<Parada,Integer> {
	
	@Query()
    List<Parada> findParadaMasCercana(@Param("latitud") double latitud, @Param("longitud") double longitud);
	
	@Query()
    Parada findByLatitudAndLongitud(double latitud, double longitud);
	
	//native para usar la funcion de coseno
	//TODO checkear si se convierte el resultado de la query nativa al dto
	
	/**
	 * Obtiene los datos de las paradas dentro de un distancia y la cantidad de monopatines que tienen.
	 * 
	 * @param latitud latitud desde donde se calcula la distancia a las paradas
	 * @param longitud longitud desde donde se calcula la distancia a las paradas
	 * @param distanciaMax distancia maxima a la que pueden estar las paradas
	 */
	
	//convierte diferencia de grados en latitud a distancia en metros
	//convierte diferencia de grados en longitud a distancia en metros, considerando que la convercion de grados a metros tiende a 0
	//a medida que se acerca a los polos
	//se calcula la distancia con pitagoras, como si fuera un plano en vez de la superficie de un esferoide, lo que hace que no sea preciso para distancias grandes
	//query anidada para no tener que realizar la calculacion de distancia tanto en SELECT como en WHERE
	 @Query(value = "SELECT p.id, p.nombre, p.latitud, p.longitud, p.distancia, COUNT(mp.idMonopatin) as cantidad "
	 		+ "FROM (SELECT p.id, p.nombre,p.latitud,p.longitud, SQRT(POWER(ABS((p.latitud - :latitud) * 111320), 2) + " +
	 		 														"POWER(ABS((p.longitud - :longitud) * COS(RADIANS(:latitud))), 2)) " +
	 		 														"AS distancia " +
	 		 		"FROM parada) as p " +
	 		 "JOIN monopatinParada as mp " +
	 		 "ON p.id = mp.idParada " +
	 		 "WHERE p.distancia < :distanciaMax " +
	 		 "GROUP BY p.id, p.nombre, p.latitud, p.longitud, p.distancia  "+
	 		 "ORDER BY p.distancia ", nativeQuery = true)
	 List<ParadaDistanciaDTO> findWithinRange(double latitud, double longitud, double distanciaMax);
}
