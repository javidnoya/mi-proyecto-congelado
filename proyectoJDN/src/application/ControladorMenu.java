package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.Sesion;
import modelo.Usuario;

import java.io.IOException;
import java.sql.SQLException;

public class ControladorMenu {
	
	
	
	@FXML
	private void initialize() throws ClassNotFoundException {
	    Usuario usuarioActual = Sesion.getConectado(); 

	    try {
	        if (!AccesoBBDD.consultarDesbloqueado(usuarioActual.getId(), 1)) {
	            AccesoBBDD.insertarDesbloqueo(usuarioActual.getId(), 1);
	        }
	        
		    if(!AccesoBBDD.consultarDesbloqueado(usuarioActual.getId(), 3)) {
		    	if(AccesoBBDD.consultarElo(usuarioActual.getId()) >= 600) {
		    		AccesoBBDD.insertarDesbloqueo(usuarioActual.getId(), 3);
		    	}
		    }
		    
		    
		    
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	}
	
    @FXML
    private void verPerfil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Perfil.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    private void verInventario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Inventario.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    private void verCurso(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Curso.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void verCalculo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Calculo.fxml"));
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
    


}
