package com.example.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@IdClass(MonopatinParadaId.class)
public class MonopatinParada {
	
	@Id
	private Integer idMonopatin;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	private Integer idParada;

	
	
	public MonopatinParada(Integer id, Integer idParada) {
		super();
		this.idMonopatin = id;
		this.idParada = idParada;
	}

	public Integer getIdMonopatin() {
		return idMonopatin;
	}

	public void setIdMonopatin(Integer id) {
		this.idMonopatin = id;
	}

	public Integer getIdParada() {
		return idParada;
	}

	public void setIdParada(Integer idParada) {
		this.idParada = idParada;
	}
	
	
}
