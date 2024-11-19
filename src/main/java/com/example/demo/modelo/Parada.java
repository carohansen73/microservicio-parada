package com.example.demo.modelo;

import java.io.Serializable;
import java.util.List;

import com.example.demo.DTO.PostParadaDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Parada implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idParada;
	
	private String nombre;
	
	@OneToMany(mappedBy = "parada", fetch = FetchType.LAZY)
	private List<MonopatinParada> monopatines;
	
	@Column
	private Double latitud;
	
	@Column
	private Double longitud;
	
	public Parada() {
		
	}
	public Parada(String nombre, double latitud, double longitud) {
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	
	public Parada(PostParadaDTO dto) {
		this.idParada = dto.getId(); 
		this.nombre = dto.getNombre();
		this.latitud = dto.getLatitud();
		this.longitud = dto.getLongitud();
	}

	public Integer getidParada() {
		return idParada;
	}
	public void setIdParada(Integer idParada) {
		this.idParada = idParada;
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
	
	public List<MonopatinParada> getMonopatines(){
		return monopatines;
	}
	
	public void setMonopatines(List<MonopatinParada> monopatines) {
		this.monopatines = monopatines;
	}
	
	public boolean tieneMonopatinesEstacionados() {
	    return monopatines != null && !monopatines.isEmpty();
	}

	@Override
	public String toString() {
		return "Parada [id=" + idParada + ", nombre=" + nombre + ", monopatines=" + monopatines + ", latitud=" + latitud
				+ ", longitud=" + longitud + "]";
	}


	public void addMonopatin(Integer monopatinId) {
		MonopatinParada monopatin = new MonopatinParada(monopatinId,this);
		this.monopatines.add(monopatin);
		
	}
	
	
}
