package com.example.demo.DTO;

import java.io.Serializable;
public class MonopatinDTO implements Serializable{

	private Integer idMonopatin;
    private Boolean disponible;
    private Boolean encendido;
    private Double longitud;
    private Double latitud;
    private Integer idParada;
    private Boolean mantenimiento;
    
    public MonopatinDTO() {
    	
    }

	public MonopatinDTO(Integer idMonopatin, Boolean disponible, Boolean encendido, Double longitud, Double latitud,
			Integer idParada, Boolean mantenimiento) {
		super();
		this.idMonopatin = idMonopatin;
		this.disponible = disponible;
		this.encendido = encendido;
		this.longitud = longitud;
		this.latitud = latitud;
		this.idParada = idParada;
		this.mantenimiento = mantenimiento;
	}

	public Integer getIdMonopatin() {
		return idMonopatin;
	}

	public void setIdMonopatin(Integer idMonopatin) {
		this.idMonopatin = idMonopatin;
	}

	public Boolean getDisponible() {
		return disponible;
	}

	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	}

	public Boolean getEncendido() {
		return encendido;
	}

	public void setEncendido(Boolean encendido) {
		this.encendido = encendido;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Integer getIdParada() {
		return idParada;
	}

	public void setIdParada(Integer idParada) {
		this.idParada = idParada;
	}

	public Boolean getMantenimiento() {
		return mantenimiento;
	}

	public void setMantenimiento(Boolean mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	
    
	
    
}
