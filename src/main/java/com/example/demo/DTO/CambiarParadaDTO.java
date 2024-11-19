package com.example.demo.DTO;

import java.io.Serializable;

public class CambiarParadaDTO implements Serializable{

	private Integer idParada;

	
	public CambiarParadaDTO() {
		super();
	}
	
	public CambiarParadaDTO( Integer parada) {
		super();
		this.idParada = parada;
	}

	public Integer getIdParada() {
		return idParada;
	}

	public void setIdParada(Integer parada) {
		this.idParada = parada;
	}
	
	
	
}
