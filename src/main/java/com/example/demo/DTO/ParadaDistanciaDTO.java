package com.example.demo.DTO;

import java.io.Serializable;

public class ParadaDistanciaDTO implements Serializable{
	
	
	private Integer id;
	private String nombre;
	private double latitud;
	private double longitud;
	private double distancia;
	private Integer cantidad;
	
	public ParadaDistanciaDTO() {
			}
	
	public ParadaDistanciaDTO(Integer id, String nombre, double latitud, double longitud, double distancia,
			Integer cantidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
		this.distancia = distancia;
		this.cantidad = cantidad;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
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

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	
}


