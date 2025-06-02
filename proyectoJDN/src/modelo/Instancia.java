package modelo;

public class Instancia {
	
	private int id;
    private int[] numeros;
    private int objetivo;
    private String solucionIA;
    private int totalIA;
    
    
    
	public Instancia(int id, int[] numeros, int objetivo, String solucionIA, int totalIA) {
		super();
		this.id = id;
		this.numeros = numeros;
		this.objetivo = objetivo;
		this.solucionIA = solucionIA;
		this.totalIA = totalIA;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int[] getNumeros() {
		return numeros;
	}
	public void setNumeros(int[] numeros) {
		this.numeros = numeros;
	}
	public int getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(int objetivo) {
		this.objetivo = objetivo;
	}
	public String getSolucionIA() {
		return solucionIA;
	}
	public void setSolucionIA(String solucionIA) {
		this.solucionIA = solucionIA;
	}
	public int getTotalIA() {
		return totalIA;
	}
	public void setTotalIA(int totalIA) {
		this.totalIA = totalIA;
	}
    
    
    
    
    
    

}
