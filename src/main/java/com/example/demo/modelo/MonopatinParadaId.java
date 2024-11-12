package com.example.demo.modelo;

import java.io.Serializable;
import java.util.Objects;


public class MonopatinParadaId implements Serializable{
	private Integer idMonopatin;
	private Integer idParada;
	
	public MonopatinParadaId(Integer idMonopatin, Integer idParada) {
		super();
		this.idMonopatin = idMonopatin;
		this.idParada = idParada;
	}
	public Integer getIdMonopatin() {
		return idMonopatin;
	}
	public void setIdMonopatin(Integer idMonopatin) {
		this.idMonopatin = idMonopatin;
	}
	public Integer getIdParada() {
		return idParada;
	}
	public void setIdParada(Integer idParada) {
		this.idParada = idParada;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonopatinParadaId that = (MonopatinParadaId) o;
        return Objects.equals(idMonopatin, that.idMonopatin) && 
               Objects.equals(idParada, that.idParada);
    }
	
	@Override
    public int hashCode() {
        return Objects.hash(idMonopatin, idParada);
    }
	
	
}
