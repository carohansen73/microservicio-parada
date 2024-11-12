package com.example.demo.DTO;

import main.app.model.GPS;
import main.app.model.Parada;

public class MonopatinDTO {

	private Integer id;
    private boolean isDisponible;
    private boolean isEncendido;
    private GPS gps;
    private Parada parada;
    
	public MonopatinDTO(Integer id, boolean isDisponible, boolean isEncendido, GPS gps, Parada parada) {
		super();
		this.id = id;
		this.isDisponible = isDisponible;
		this.isEncendido = isEncendido;
		this.gps = gps;
		this.parada = parada;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isDisponible() {
		return isDisponible;
	}

	public void setDisponible(boolean isDisponible) {
		this.isDisponible = isDisponible;
	}

	public boolean isEncendido() {
		return isEncendido;
	}

	public void setEncendido(boolean isEncendido) {
		this.isEncendido = isEncendido;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public Parada getParada() {
		return parada;
	}

	public void setParada(Parada parada) {
		this.parada = parada;
	}
    
    
}
