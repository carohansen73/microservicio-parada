package com.example.demo.modelo;

import java.util.List;

import com.example.demo.DTO.PostParadaDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import main.app.model.GPS;

@Entity
public class Parada {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private Long id;
	
	@Column
	private String nombre;
	
	@Column
	private List<Long> monopatines;
	
	@Column
	private double latitud;
	
	@Column
	private double longitud;
	@Column
    private GPS gps;
	
	public Parada(String nombre, double latitud, double longitud) {
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	
	public Parada(PostParadaDTO dto) {
		this.id = dto.getId(); 
		this.nombre = dto.getNombre();
		this.latitud = dto.getLatitud();
		this.longitud = dto.getLongitud();
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
	public GPS getGps() {
	    return gps;
	}
	public void setGps(GPS gps) {
	    this.gps = gps;
	}
	public List<Long> getMonopatines() {
		return monopatines;
	}

	public void setMonopatines(List<Long> monopatines) {
		this.monopatines = monopatines;
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

	@Override
	public String toString() {
		return "Parada [id=" + id + ", nombre=" + nombre + ", monopatines=" + monopatines + ", latitud=" + latitud
				+ ", longitud=" + longitud + "]";
	}
	
	
}
