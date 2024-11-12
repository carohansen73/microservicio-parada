package com.example.demo.DTO;

import main.app.dto.GPSDTO;

public class VerificacionParadaDTO {
	 private GPSDTO gps;
	    private Integer paradaId;


	    public VerificacionParadaDTO() {}

	    public VerificacionParadaDTO(GPSDTO gps, Integer paradaId) {
	        this.gps = gps;
	        this.paradaId = paradaId;
	    }

	    public GPSDTO getGps() {
	        return gps;
	    }

	    public void setGps(GPSDTO gps) {
	        this.gps = gps;
	    }

	    public Integer getParadaId() {
	        return paradaId;
	    }

	    public void setParadaId(Integer paradaId) {
	        this.paradaId = paradaId;
	    }
}
