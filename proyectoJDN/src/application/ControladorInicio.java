package application;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Sesion;
import modelo.Usuario;


import java.io.IOException;
import java.sql.SQLException;

public class ControladorInicio {
	
	@FXML private TextField tfNombre;
	@FXML private PasswordField pfContrasenya;

	    @FXML
	    private void verRegistro(ActionEvent event) throws IOException {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Registro.fxml"));
	        Parent root = loader.load();
	        
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    }
	    
	    
	    @FXML
	    private void verMenu(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
	        String nombre = tfNombre.getText().trim();
	        String contrasenya = pfContrasenya.getText().trim();
	    	
	        if (nombre.isEmpty() || contrasenya.isEmpty()) {
	            mostrarAlerta("Campos vacíos", "Por favor, introduce usuario y contraseña.");
	            return;
	        }
	        
	        Usuario usuario = AccesoBBDD.validarCredenciales(nombre, contrasenya);

	        if (usuario == null) {
	            mostrarAlerta("Credenciales incorrectas", "Usuario o contraseña incorrectos.");
	            return;
	        }
	        
	        Sesion.setConectado(usuario);
	    	
	        // Si todo va bien, cargar el menú principal
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuPrincipal.fxml"));
	        Parent root = loader.load();

	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    }
	    
	    @FXML
	    private void cerrarAplicacion() {
	        System.exit(0);
	    }
	    
	    
	    
	    
	    
	    private void mostrarAlerta(String titulo, String mensaje) {
	        Alert alerta = new Alert(Alert.AlertType.ERROR);
	        alerta.setTitle("Error");
	        alerta.setHeaderText(titulo);
	        alerta.setContentText(mensaje);
	        alerta.showAndWait();
	    }
	    
	    
	


}
