package com.example.demo.Repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Parada;

//No uso JPa porque es NoSQL
@Repository
public interface ParadaRepository extends MongoRepository<Parada,Long> {
	
	@Query(value = "{}", sort = "{ $add: [ { $pow: [ { $subtract: [ :latitud , latitud ] }, 2 ] }, { $pow: [ { $subtract: [ :longitud , longitud ] }, 2 ] } ] }")
    List<Parada> findParadaMasCercana(@Param("latitud") long latitud, @Param("longitud") long longitud);
	
	@Query("{ 'latitud': ?0, 'longitud': ?1 }")
    Parada findByLatitudAndLongitud(long latitud, long longitud);
}
