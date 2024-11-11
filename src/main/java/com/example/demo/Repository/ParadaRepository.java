package com.example.demo.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Parada;

//No uso JPa porque es NoSQL
@Repository
public interface ParadaRepository extends MongoRepository<Parada,Long> {

}
