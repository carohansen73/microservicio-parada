package com.example.demo.DTO;

import java.util.List;

public class ParadaDTO {

	private Integer id;
	private String nombre;
	private double latitud;
	private double longitud;
	private List<Long> monopatines;
	
	public ParadaDTO(Integer id, String nombre, double latitud, double longitud, List<Long> monopatines) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
		this.monopatines = monopatines;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
