package com.example.demo.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Parada;


@Repository
public interface ParadaRepository extends JpaRepository<Parada,Integer> {
	
	@Query()
    List<Parada> findParadaMasCercana(@Param("latitud") double latitud, @Param("longitud") double longitud);
	
	@Query()
    Parada findByLatitudAndLongitud(double latitud, double longitud);
}
