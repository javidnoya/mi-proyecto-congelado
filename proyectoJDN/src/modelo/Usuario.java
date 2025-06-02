package modelo;

public class Usuario {
	
	private int id;
	private String nombre;
	private String contrasenya;
	private String correo;
	private String fechaNacimiento;
	private String fechaRegistro;
	private int elo;
	
	
	
	public Usuario(int id, String nombre, String contrasenya, String correo, String fechaNacimiento,
			String fechaRegistro, int elo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.contrasenya = contrasenya;
		this.correo = correo;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaRegistro = fechaRegistro;
		this.elo = elo;
	}
	
	



	public Usuario(String nombre, String contrasenya, String correo, String fechaNacimiento) {
		super();
		this.nombre = nombre;
		this.contrasenya = contrasenya;
		this.correo = correo;
		this.fechaNacimiento = fechaNacimiento;
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



	public String getContrasenya() {
		return contrasenya;
	}



	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}



	public String getCorreo() {
		return correo;
	}



	public void setCorreo(String correo) {
		this.correo = correo;
	}



	public String getFechaNacimiento() {
		return fechaNacimiento;
	}



	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}



	public String getFechaRegistro() {
		return fechaRegistro;
	}



	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}



	public int getElo() {
		return elo;
	}



	public void setElo(int elo) {
		this.elo = elo;
	}
	
	
	
	
	

}
