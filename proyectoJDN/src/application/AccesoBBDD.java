package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import modelo.Invizimal;
import modelo.Progreso;
import modelo.Usuario;



public class AccesoBBDD {
	
	
	public static void insertar(Usuario usuario) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "INSERT INTO usuario (nombre, contrasenya, correo, fecha_nacimiento) "
					+ "VALUES (?, ?, ?, ?)";
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.setString(1, usuario.getNombre());
			sentenciaPreparada.setString(2, usuario.getContrasenya());
			sentenciaPreparada.setString(3, usuario.getCorreo());
			sentenciaPreparada.setString(4, usuario.getFechaNacimiento());
			sentenciaPreparada.executeUpdate();
		}finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	
	public static boolean consultarNombreUnico(String nombre) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "SELECT COUNT(*) FROM usuario WHERE nombre = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, nombre);
			ResultSet resultados = sentencia.executeQuery();
			
			boolean esUnico = false;
			
			if(resultados.next()) {
				int cantidad = resultados.getInt(1);
				if(cantidad == 0) {
					esUnico = true;
				}
			}
			
			return esUnico;

		}finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	public static Usuario validarCredenciales(String nombre, String contrasenya) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "SELECT * FROM usuario where nombre = ? AND contrasenya = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, nombre);
			sentencia.setString(2, contrasenya);
			ResultSet resultados = sentencia.executeQuery();
			if(resultados.next()) {
				int id = resultados.getInt("id");
				String correo = resultados.getString("correo");
				String fechaNacimiento = resultados.getString("fecha_nacimiento");
				String fechaRegistro = resultados.getString("fecha_registro");
				int elo = resultados.getInt("elo");
				Usuario usuario = new Usuario(id, nombre, contrasenya, correo, fechaNacimiento, fechaRegistro, elo);
				return usuario;
			}
			return null;

		}finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	
	public static int actualizarContrasenya(String nueva, int id) throws SQLException, ClassNotFoundException {
		
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "UPDATE usuario SET contrasenya = ? WHERE id = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, nueva);
			sentencia.setInt(2, id);
			int actualizado = sentencia.executeUpdate();
			return actualizado;

		}finally {
			ConfigBD.cerrarConexion(conexion);
		}
		
		
	}
	
	
	public static int borrarUsuario(int id) throws ClassNotFoundException, SQLException {
		
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "DELETE FROM usuarios WHERE id = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			int actualizado = sentencia.executeUpdate();
			return actualizado;

		}finally {
			ConfigBD.cerrarConexion(conexion);
		}
		
	}
	
	
	public static void nivelCompletado(int idUsuario, int idNivel, String completado) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "INSERT INTO progreso (id_usuario, id_nivel, completado) VALUES (?, ?, ?)" ;
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.setInt(1, idUsuario);
			sentenciaPreparada.setInt(2, idNivel);
			sentenciaPreparada.setString(3, completado);
			sentenciaPreparada.executeUpdate();
		}finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	public static int actualizarElo(int cambioElo, int id) throws SQLException, ClassNotFoundException {
		
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "UPDATE usuario SET elo = elo + ? WHERE id = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, cambioElo);
			sentencia.setInt(2, id);
			int actualizado = sentencia.executeUpdate();
			return actualizado;

		}finally {
			ConfigBD.cerrarConexion(conexion);
		}
		
		
	}
	
	public static void insertarDesbloqueo(int idUsuario, int idInvizimal) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "INSERT INTO desbloqueo (id_usuario, id_invizimal) "
					+ "VALUES (?, ?)";
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.setInt(1, idUsuario);
			sentenciaPreparada.setInt(2, idInvizimal);
			sentenciaPreparada.executeUpdate();
		}finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	
	public static void insertarPartida(int idUsuario, int puntuacion, int num_operaciones, int diferencia_objetivo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaSQL = "INSERT INTO partida (id_usuario, puntuacion, num_operaciones, diferencia_objetivo) "
					+ "VALUES (?, ?, ?, ?)";
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.setInt(1, idUsuario);
			sentenciaPreparada.setInt(2, puntuacion);
			sentenciaPreparada.setInt(3, num_operaciones);
			sentenciaPreparada.setInt(4, diferencia_objetivo);
			sentenciaPreparada.executeUpdate();
		}finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	
	public static String consultarProgreso(int id) throws ClassNotFoundException, SQLException{
		Connection conexion = null;
		String estado = "FALSE";
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "SELECT completado FROM progreso WHERE id_usuario = ? ";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			ResultSet resultados = sentencia.executeQuery();
			while(resultados.next()) {
				estado = resultados.getString("completado");
			}
			return estado;
			}finally {
			ConfigBD.cerrarConexion(conexion);
		}
		
		
	}
	
	
	public static int consultarElo(int id) throws ClassNotFoundException, SQLException{
		Connection conexion = null;
		int elo=0;
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "SELECT elo FROM usuario WHERE id = ? ";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			ResultSet resultados = sentencia.executeQuery();
			while(resultados.next()) {
				elo = resultados.getInt("elo");
			}
			return elo;
			}finally {
			ConfigBD.cerrarConexion(conexion);
		}
		
		
	}
	
	
	public static double mediaDistanciaObjetivo(int id) throws ClassNotFoundException, SQLException{
		Connection conexion = null;
		double distanciaMedia = 0;
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "SELECT AVG(diferencia_objetivo) AS media_diferencia\r\n"
					+ "FROM Partida\r\n"
					+ "WHERE id_usuario = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			ResultSet resultados = sentencia.executeQuery();
			while(resultados.next()) {
				distanciaMedia = resultados.getDouble("media_diferencia");
			}
			return distanciaMedia;
			}finally {
			ConfigBD.cerrarConexion(conexion);
		}
		
		
	}
	
	public static int totalPartidas(int id) throws ClassNotFoundException, SQLException{
		Connection conexion = null;
		int total = 0;
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "SELECT COUNT(*) AS total_partidas\r\n"
					+ "FROM Partida\r\n"
					+ "WHERE id_usuario = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			ResultSet resultados = sentencia.executeQuery();
			while(resultados.next()) {
				total = resultados.getInt("total_partidas");
			}
			return total;
			}finally {
			ConfigBD.cerrarConexion(conexion);
		}
		
		
	}
	
	
	
	
	
	
	public static int consultarRanking(int id) throws ClassNotFoundException, SQLException{
		Connection conexion = null;
		int posicion = 0;
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "SELECT posicion FROM (\r\n"
					+ "    SELECT \r\n"
					+ "        id,\r\n"
					+ "        RANK() OVER (ORDER BY elo DESC) AS posicion\r\n"
					+ "    FROM Usuario\r\n"
					+ ") AS ranking\r\n"
					+ "WHERE id = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			ResultSet resultados = sentencia.executeQuery();
			while(resultados.next()) {
				posicion = resultados.getInt("posicion");
			}
			return posicion;
			}finally {
			ConfigBD.cerrarConexion(conexion);
		}
		
		
	}
	
	
	public static Invizimal consultarInvizimal(int id) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "SELECT * FROM invizimal where id = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			ResultSet resultados = sentencia.executeQuery();
			if(resultados.next()) {
				String nombre = resultados.getString("nombre");
				String tipo = resultados.getString("tipo");
				String descripcion = resultados.getString("descripcion");
				Invizimal invizimal = new Invizimal(id, nombre, tipo, descripcion);
				return invizimal;
			}
			return null;

		}finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	
	public static boolean consultarDesbloqueado(int idUsuario, int idInvizimal) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		
		try {
			conexion = ConfigBD.abrirConexion();
			String sql = "SELECT COUNT(*) FROM Desbloqueo WHERE id_usuario = ? AND id_invizimal = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, idUsuario);
			sentencia.setInt(2, idInvizimal);
			ResultSet resultados = sentencia.executeQuery();
			
			boolean desbloqueado = false;
			
			if(resultados.next()) {
				int cantidad = resultados.getInt(1);
				if(cantidad == 1) {
					desbloqueado = true;
				}
			}
			
			return desbloqueado;

		}finally {
			ConfigBD.cerrarConexion(conexion);
		}
	}
	
	
	
	
	
	
	
	

}
