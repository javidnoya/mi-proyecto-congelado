package modelo;

public class Invizimal {
	
	private int id;
	private String nombre;
	private String tipo;
	private String descripcion;
	
	
	public Invizimal(int id, String nombre, String tipo, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.descripcion = descripcion;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
