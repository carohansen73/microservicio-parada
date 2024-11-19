package com.example.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class MonopatinParada {
	
	@Id
	private Integer idMonopatin;
	
	
	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
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

	
	
}
