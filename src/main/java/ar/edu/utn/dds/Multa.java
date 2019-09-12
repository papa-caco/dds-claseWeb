package ar.edu.utn.dds;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Multa {

	private String fecha;
	private String tipo;

	
	public Multa() {
		super();
	
	}
	
	public Multa(String fecha, String tipo) {
		super();
		this.fecha = fecha;
		this.tipo = tipo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	
	
	
	
	
}
