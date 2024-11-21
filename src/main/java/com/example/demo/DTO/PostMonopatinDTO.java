package com.example.demo.DTO;

import java.io.Serializable;
public class PostMonopatinDTO implements Serializable{

    private boolean isDisponible;
    private boolean isEncendido;
    private boolean isMantenimiento;
    private double longitud;
    private double latitud;
    
    public PostMonopatinDTO() {
    	
    }

	

	public PostMonopatinDTO(boolean isDisponible, boolean isEncendido, boolean isMantenimiento, double longitud,
			double latitud) {
		super();
		this.isDisponible = isDisponible;
		this.isEncendido = isEncendido;
		this.isMantenimiento = isMantenimiento;
		this.longitud = longitud;
		this.latitud = latitud;
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



	public boolean isMantenimiento() {
		return isMantenimiento;
	}



	public void setMantenimiento(boolean isMantenimiento) {
		this.isMantenimiento = isMantenimiento;
	}
  
	
    
}
