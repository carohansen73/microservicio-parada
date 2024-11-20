package com.example.demo.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MonopatinParada {
	
	@Id
	private Integer idMonopatin;
	
	
	@ManyToOne
	@JoinColumn(name = "idParada", nullable = false)
	@JsonBackReference
	private Parada parada;
	
	public MonopatinParada() {
	}
	    
	public MonopatinParada(Integer id, Parada parada) {
		super();
		this.idMonopatin = id;
		this.parada = parada;
	}

	public Integer getIdMonopatin() {
		return idMonopatin;
	}

	public void setIdMonopatin(Integer id) {
		this.idMonopatin = id;
	}

	public Parada getParada() {
		return parada;
	}

	public void setParada(Parada parada) {
		this.parada = parada;
	}
	
	 public void setParadaById(Integer idParada) {
	        Parada parada = new Parada();
	        parada.setIdParada(idParada);
	        this.parada = parada;
	    }

	
	
}
