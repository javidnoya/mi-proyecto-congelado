package modelo;

public class Sesion {
	
    private static Usuario usuarioActual;

    public static void setConectado(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getConectado() {
        return usuarioActual;
    }

}
