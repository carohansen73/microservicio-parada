package com.example.demo.DTO;

import java.io.Serializable;
import java.util.List;

public class PostParadaDTO implements Serializable{
	
	private Long id;
	private String nombre;
	private double latitud;
	private double longitud;
	private List<Long> monopatines;
	
	public PostParadaDTO(Long id, String nombre, double latitud, double longitud, List<Long> monopatines) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
		this.monopatines = monopatines;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
	

}
