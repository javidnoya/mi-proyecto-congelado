package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.Invizimal;
import modelo.Sesion;
import modelo.Usuario;

public class ControladorColeccion {
	
	
	Usuario usuario = Sesion.getConectado();
	
	@FXML private Tooltip tt1;
	@FXML private Tooltip tt2;
	@FXML private Tooltip tt3;
	@FXML private ImageView img1;
	@FXML private ImageView img2;
	@FXML private ImageView img3;
	
	
	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {
	    

	    configurarInventario(1, tt1, img1);
	    configurarInventario(2, tt2, img2);
	    configurarInventario(3, tt3, img3);
	    
	    
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
    private void cerrarAplicacion() {
        System.exit(0);
    }
    
    
    private void configurarInventario(int idInvizimal, Tooltip tt, ImageView img) throws ClassNotFoundException, SQLException {
        boolean desbloqueado = AccesoBBDD.consultarDesbloqueado(usuario.getId(), idInvizimal);

        if (desbloqueado) {
            
            Invizimal inv = AccesoBBDD.consultarInvizimal(idInvizimal);
            String textoTooltip = String.format("Nombre: %s\nTipo: %s\nDescripcion: %s", inv.getNombre(), inv.getTipo(), inv.getDescripcion());
            tt.setText(textoTooltip);
            img.setOpacity(1.0);

        } else {
            String textoDesbloqueo = "";
            if (idInvizimal == 1) {
                textoDesbloqueo = "¡Desbloquea a Tigershark pup creando tu usuario!";
            } else if (idInvizimal == 2) {
                textoDesbloqueo = "¡Desbloquea a Sandflame pup jugando el Nivel 1!";
            } else if(idInvizimal == 3){
                textoDesbloqueo = "¡Desbloquea a Axolotl pup consiguiendo un elo de 600 puntos!";
            }else {
            	
            }
            tt.setText(textoDesbloqueo);
            img.setOpacity(0.5);

            
        }
    }

}
