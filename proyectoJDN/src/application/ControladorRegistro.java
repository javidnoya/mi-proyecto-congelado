package application;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import modelo.Usuario;

public class ControladorRegistro {
	
    @FXML private TextField tfNombre;
    @FXML private PasswordField pfContrasenya;
    @FXML private PasswordField pfConfirmarContrasenya;
    @FXML private TextField tfCorreo;
    @FXML private DatePicker dpNacimiento;
    @FXML private Button botonConfirmar;
    
    
    @FXML
    private void initialize() {
        botonConfirmar.setOnAction(e -> {
            try {
                validarYRegistrarUsuario();
            } catch (Exception ex) {
                ex.printStackTrace();
                mostrarAlerta("Error interno", "Ocurrió un error inesperado.");
            }
        });
    }
    
    
    
    
    private void validarYRegistrarUsuario() throws SQLException, IOException, ClassNotFoundException {
        String nombre = tfNombre.getText().trim();
        String contrasenya = pfContrasenya.getText().trim();
        String confirmar = pfConfirmarContrasenya.getText().trim();
        String correo = tfCorreo.getText().trim();
        LocalDate fechaNacimiento = dpNacimiento.getValue();

        
        if (nombre.length() < 8 || nombre.length() > 16) {
            mostrarAlerta("Nombre inválido", "Debe tener entre 8 y 16 caracteres.");
            return;
        }

        if (!AccesoBBDD.consultarNombreUnico(nombre)) {
            mostrarAlerta("Nombre en uso", "Ya existe un usuario con ese nombre.");
            return;
        }

        if (contrasenya.length() < 8 || contrasenya.length() > 12 || !contrasenya.matches(".*\\d.*")) {
            mostrarAlerta("Contraseña inválida", "Debe tener entre 8 y 12 caracteres e incluir al menos un número.");
            return;
        }

        if (!contrasenya.equals(confirmar)) {
            mostrarAlerta("Confirmación incorrecta", "Las contraseñas no coinciden.");
            return;
        }

        if (!correo.endsWith("@gmail.com")) {
            mostrarAlerta("Correo inválido", "El correo debe terminar en @gmail.com.");
            return;
        }

        if (fechaNacimiento == null) {
            mostrarAlerta("Fecha faltante", "Debes seleccionar una fecha de nacimiento.");
            return;
        }

        // Convertir fecha a string
        String fechaNacimientoStr = fechaNacimiento.toString();

        // Crear e insertar el usuario
        Usuario usuario = new Usuario(nombre, contrasenya, correo, fechaNacimientoStr);
        AccesoBBDD.insertar(usuario);
        
        mostrarAlerta("Usuario creado en el sistema" ,"Proceda a ingresar sus credenciales..");
        // Cambiar a la pantalla de menú
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Inicio.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) botonConfirmar.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    
    @FXML
    private void cerrarAplicacion() {
        System.exit(0);
    }
    
    
    
    
    
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
