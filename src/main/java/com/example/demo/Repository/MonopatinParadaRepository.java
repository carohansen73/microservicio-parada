package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.MonopatinParada;
import com.example.demo.modelo.MonopatinParadaId;


@Repository
public interface MonopatinParadaRepository extends JpaRepository<MonopatinParada, MonopatinParadaId> {

}
