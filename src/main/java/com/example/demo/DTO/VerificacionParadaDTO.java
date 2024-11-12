package com.example.demo.DTO;

import main.app.dto.GPSDTO;

public class VerificacionParadaDTO {
	 private GPSDTO gps;
	    private Long paradaId;


	    public VerificacionParadaDTO() {}

	    public VerificacionParadaDTO(GPSDTO gps, Long paradaId) {
	        this.gps = gps;
	        this.paradaId = paradaId;
	    }

	    public GPSDTO getGps() {
	        return gps;
	    }

	    public void setGps(GPSDTO gps) {
	        this.gps = gps;
	    }

	    public Long getParadaId() {
	        return paradaId;
	    }

	    public void setParadaId(Long paradaId) {
	        this.paradaId = paradaId;
	    }
}
