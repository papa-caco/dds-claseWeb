package ar.edu.utn.dds;



public class Auto {


	
	private String patente;
	private String modelo;
	private String marca;
	private int anio;
	
	
	public String getPatente() {
		return patente;
	}
	public void setPatente(String patente) {
		this.patente = patente;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	
	
	

	public Auto(){
		
	}
	
	public Auto(String patente, String modelo, String marca, int anio){
		this();
		this.patente = patente;
		this.modelo = modelo;
		this.marca = marca;
		this.anio = anio;
	}
	
	
}
