package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Sesion;
import modelo.Usuario;

public class ControladorCurso {
	
	Usuario usuarioActual = Sesion.getConectado();
	
    @FXML private RadioButton rbtn1_25, rbtn1_144, rbtn1_48;
    @FXML private RadioButton rbtn2_0, rbtn2_9, rbtn2_1;
    @FXML private RadioButton rbtn3_64, rbtn3_8, rbtn3_24;
    @FXML private RadioButton rbtn4_1, rbtn4_8, rbtn4_0;
    @FXML private Label lblLecciones;
    @FXML private Label lblProgreso;
    @FXML private Pane panelLeccion;
    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private Button btnNvl1;
    @FXML private Button btnNvl2;

    @FXML private ToggleGroup grupo1, grupo2, grupo3, grupo4;

    private RadioButton correcto1, correcto2, correcto3, correcto4;

    @FXML
    public void initialize() throws ClassNotFoundException, SQLException {
        correcto1 = rbtn1_144;
        correcto2 = rbtn2_1;
        correcto3 = rbtn3_8;
        correcto4 = rbtn4_8;

        String progreso = AccesoBBDD.consultarProgreso(usuarioActual.getId());

        // Solo se cambia la vista si el progreso es "TRUE"
        if ("TRUE".equalsIgnoreCase(progreso)) {
            lblLecciones.setVisible(false);
            panelLeccion.setVisible(true);
            lblProgreso.setText("1/4");
            img1.setVisible(false);
            img2.setVisible(true);
            btnNvl1.setDisable(true);
            btnNvl2.setDisable(false);
        }

        // Si progreso es null o distinto de TRUE, simplemente no se hace nada especial.
    }
    
    
    @FXML
    private void corregirNivel1() throws ClassNotFoundException, SQLException{
        if (grupo1.getSelectedToggle() == null || grupo2.getSelectedToggle() == null
                || grupo3.getSelectedToggle() == null || grupo4.getSelectedToggle() == null) {
            mostrarAlerta("Error", "Debes responder todas las preguntas.");
            return;
        }

        int aciertos = 0;
        aciertos += comprobarRespuesta(grupo1, correcto1);
        aciertos += comprobarRespuesta(grupo2, correcto2);
        aciertos += comprobarRespuesta(grupo3, correcto3);
        aciertos += comprobarRespuesta(grupo4, correcto4);

        if (aciertos == 4) {
            mostrarAlerta("¡Perfecto!", "¡Buen trabajo! Has acertado todas.");
        } else {
            mostrarAlerta("¡Sigue así!", "Has acertado " + aciertos + " de 4.");
        }

        Usuario usuario = Sesion.getConectado();
        AccesoBBDD.nivelCompletado(usuario.getId(), 1, "TRUE");
        AccesoBBDD.insertarDesbloqueo(usuarioActual.getId(), 2);
 
    }
    
    
    
    private int comprobarRespuesta(ToggleGroup grupo, RadioButton respuestaCorrecta) {
        RadioButton seleccionado = (RadioButton) grupo.getSelectedToggle();
        if (seleccionado.equals(respuestaCorrecta)) {
            seleccionado.setStyle("-fx-text-fill: green;");
            return 1;
        } else {
            seleccionado.setStyle("-fx-text-fill: red;");
            respuestaCorrecta.setStyle("-fx-text-fill: green;");
            return 0;
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    private void verMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuPrincipal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void verNvl1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Nivel1.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
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
    private void cerrarAplicacion() {
        System.exit(0);
    }
    
    
  
}
