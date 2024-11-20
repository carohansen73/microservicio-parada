package com.example.demo.DTO;

import java.io.Serializable;
public class MonopatinDTO implements Serializable{

	private Integer id;
    private boolean isDisponible;
    private boolean isEncendido;
    private double longitud;
    private double latitud;
    private Integer idParada;
    
    public MonopatinDTO() {
    	
    }

	public MonopatinDTO(Integer id, boolean isDisponible, boolean isEncendido, double longitud, double latitud,
			Integer idParada) {
		super();
		this.id = id;
		this.isDisponible = isDisponible;
		this.isEncendido = isEncendido;
		this.longitud = longitud;
		this.latitud = latitud;
		this.idParada = idParada;
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

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public Integer getIdParada() {
		return idParada;
	}

	public void setIdParada(Integer idParada) {
		this.idParada = idParada;
	}
    
	
    
}
