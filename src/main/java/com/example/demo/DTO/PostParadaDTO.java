package com.example.demo.DTO;

import java.io.Serializable;
import java.util.List;

public class PostParadaDTO implements Serializable{
	
	private Integer id;
	private String nombre;
	private Double latitud;
	private Double longitud;
	private List<Long> monopatines;
	
	public PostParadaDTO(Integer id, String nombre, Double latitud, Double longitud, List<Long> monopatines) {
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
	public Double getLatitud() {
		return latitud;
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	
	

}
