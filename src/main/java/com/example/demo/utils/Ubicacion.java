package com.example.demo.utils;

public class Ubicacion {
	private double longitud;
	private double latitud;
	//distancia en metros entre dos ubicaciones con un grado de diferencia en la latitud;
	public static final double GRADO_LATITUD_A_METRO = 111320;
	public static final double CIRCUMFERENCIA_TIERRA_EN_METROS=40075000;
	public Ubicacion(double longitud, double latitud) {
		super();
		this.longitud = longitud;
		this.latitud = latitud;
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



	/**devuelve si otra ubicacion esta dentro de una cierta distancia.
	 * 
	 * La precision disminuye a medida que aumenta la distancia entre las dos ubicaciones
	 * @param otraUbicacion ubicacion a comparar
	 * @param distanciaEnMetros
	**/
	public boolean isWithinDistance(Ubicacion otraUbicacion, double distanciaEnMetros) {
		double deltaLongitud = Math.abs(this.longitud - otraUbicacion.getLongitud());
		double deltaLatitud = Math.abs(this.latitud - otraUbicacion.getLatitud());
		
		//convierte distancia de latitud de grados a metros 
		double distanciaLatitudMetros = deltaLatitud*GRADO_LATITUD_A_METRO;
		
		//traduce un grado de longitud a metros segun la latitud actual y convierte la distancia de longitud a metros
		double distanciaLongitudMetros = deltaLongitud * (CIRCUMFERENCIA_TIERRA_EN_METROS  * (Math.cos(this.latitud )/360));
		
		//pitagoras para calcular la distancia entre dos puntos
		//sqrt( (x1-x2)^2 + (y1-y2)^2 )
		return Math.sqrt(distanciaLongitudMetros*distanciaLongitudMetros + distanciaLatitudMetros*distanciaLatitudMetros)<distanciaEnMetros;
		
	}
	
	public boolean isInLocation(Ubicacion otraUbicacion) {
		return this.isWithinDistance(otraUbicacion, 10);
	}



	@Override
	public String toString() {
		return "Ubicacion [longitud=" + longitud + ", latitud=" + latitud + "]";
	}
	
	
}
