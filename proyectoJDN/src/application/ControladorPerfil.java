package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import modelo.Sesion;
import modelo.Usuario;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ControladorPerfil {
	
	@FXML private Label lblCorreo;
	@FXML private Label lblFechaNacimiento;
	@FXML private Label lblFechaRegistro;
	@FXML private Label lblElo;
	@FXML private Label lblDistancia;
	@FXML private Label lblPartidas;
	@FXML private Label lblRanking;
	@FXML private Label lblUsuario;
	
	@FXML private PasswordField pfNuevaContrasenya;
	@FXML private Button btnGuardar;
	
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
	    Usuario usuario = Sesion.getConectado();
	    if (usuario != null) {
	    	lblUsuario.setText(usuario.getNombre());
	        lblCorreo.setText(usuario.getCorreo());
	        lblFechaNacimiento.setText(usuario.getFechaNacimiento().toString());
	        lblFechaRegistro.setText(usuario.getFechaRegistro().toString());
	        lblElo.setText(String.valueOf(AccesoBBDD.consultarElo(usuario.getId())));
	        lblDistancia.setText(String.valueOf(AccesoBBDD.mediaDistanciaObjetivo(usuario.getId())));
	        lblPartidas.setText(String.valueOf(AccesoBBDD.totalPartidas(usuario.getId())));
	        lblRanking.setText(String.valueOf(AccesoBBDD.consultarRanking(usuario.getId())));
	    }
	}
	
    @FXML
    private void verMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuPrincipal.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    private void mostrarCampos(ActionEvent event) throws IOException {
        pfNuevaContrasenya.setVisible(true);
        btnGuardar.setVisible(true);
    }
    
    
    @FXML
    private void guardarNuevaContrasenya(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
    	
    	String nuevaContrasena = pfNuevaContrasenya.getText().trim();
    	
        if (nuevaContrasena.isEmpty()) {
            mostrarAlerta("Campo vacío", "Introduce una nueva contraseña.");
            return;
        }
        
        Usuario usuario = Sesion.getConectado();
        int actualizado = AccesoBBDD.actualizarContrasenya(nuevaContrasena, usuario.getId());
        
        if(actualizado > 0) {
        	
        	usuario.setContrasenya(nuevaContrasena);
        	mostrarAlerta("Éxito", "Contraseña actualizada correctamente.");
            pfNuevaContrasenya.clear();
            pfNuevaContrasenya.setVisible(false);
            btnGuardar.setVisible(false);

        }else {
        	mostrarAlerta("Error", "No se pudo actualizar la contraseña.");
        }
    	
    }
    
    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Inicio.fxml"));
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
    
    
    @FXML
    private void borrarUsuario(ActionEvent event) throws SQLException, ClassNotFoundException {
    	Usuario usuario = Sesion.getConectado();
    	
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación");
        confirmacion.setHeaderText("¿Estás seguro?");
        confirmacion.setContentText("Esta acción eliminará tu cuenta permanentemente.");

        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");

        confirmacion.getButtonTypes().setAll(botonSi, botonNo);

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == botonSi) {
        	
        	if(AccesoBBDD.borrarUsuario(usuario.getId()) > 0) {
                System.exit(0);
        	}

        }
    }
    
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
