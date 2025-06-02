package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import modelo.Instancia;
import modelo.Sesion;
import modelo.Usuario;

public class ControladorCalculo {
	
	private Usuario usuarioActual = Sesion.getConectado();
	private Instancia instanciaActual;
	private StringBuilder operacionJugador = new StringBuilder();
	private boolean ultimoFueNumero = false;
	private Timeline temporizador;
	private int tiempoRestante = 30; // o 90 si quieres 1:30 min

	


	@FXML private Button btn1;
	@FXML private Button btn2;
	@FXML private Button btn3;
	@FXML private Button btn4;
	@FXML private Button btn5;
	@FXML private Button btn6;
	@FXML private Button btnBorrar;
	@FXML private Button btnSuma;
	@FXML private Button btnResta;
	@FXML private Button btnMulti;
	@FXML private Pane panelJuego;
	@FXML private Label lblTiempo;
	@FXML private Label lblNombre;
	@FXML private Label lblRec1;
	@FXML private Label lblRec2;

	
	
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
    private void mostrarAyuda(ActionEvent event) throws IOException {
    	Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    	alerta.setTitle("Ayuda");
        alerta.setHeaderText("Reglas del Juego");
        alerta.setContentText("Al pulsar PLAY inicia el juego de cálculo:" + "\n"
        + "-Pulsa los números y operaciones para obtener el número objetivo." + "\n"
        + "-Funciona como una calculadora, al número obtenido tras hacer una operación se le hace la siguiente, por lo que:" + "\n"
        + "TU RESPUESTA SE CALCULA DE IZQUIERDA A DERECHA, IGNORANDO EL ORDER DE PRIORIDAD DE LAS OPERACIONES." + "\n"
        + "- Al acabar verás tu resultado y se comparará al obtenido por el rival..." + "\n"
        + "- Si te has acercado más al objetivo +25 puntos." + "\n"
        + "- Si el usuario está más cerca, pierdes 25 puntos." + "\n"
        + "- Si habéis obtenido el mismo resultado, no obtienes puntos salvo que hayas utilizado menos operaciones (+10)." + "\n"
        + "¡DISPONES DE 30 SEGUNDOS, BUENA SUERTE!");
        alerta.showAndWait();
               
    }
    
    
    
    @FXML
    private void jugar(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {

        instanciaActual = elegirInstancia();
        asignarValores();
        panelJuego.setDisable(false);
        operacionJugador.setLength(0);
        lblRec2.setText("");
        ultimoFueNumero = false;
        String nombre = usuarioActual.getNombre();
        lblNombre.setText(nombre);

        // Desactiva todos los botones por si acaso
        for (Button btn : List.of(btn1, btn2, btn3, btn4, btn5, btn6)) {
            btn.setDisable(false);
        }
        btnSuma.setDisable(false);
        btnResta.setDisable(false);
        btnMulti.setDisable(false);
        btnBorrar.setDisable(true);

        iniciarTemporizador();  // <--- AÑADIDO
    	
    }
    
    
    @FXML
    private void clickNumero(ActionEvent event) {
        if (ultimoFueNumero) {
            return; // Evita dos números seguidos
        }
        Button btn = (Button) event.getSource();
        String numero = btn.getText();

        operacionJugador.append(numero);
        lblRec2.setText(operacionJugador.toString());

        btn.setDisable(true); // Desactivar el botón después de usarlo
        ultimoFueNumero = true;
        btnBorrar.setDisable(operacionJugador.length() == 0);
        
        if (operacionJugador.toString().trim().split(" ").length >= 11) {
            btnSuma.setDisable(true);
            btnResta.setDisable(true);
            btnMulti.setDisable(true);
        }
    }
    
    
    @FXML
    private void clickOperacion(ActionEvent event) {
        if (!ultimoFueNumero) {
            return; // Impide dos operadores seguidos o empezar con uno
        }

        Button btn = (Button) event.getSource();
        String id = btn.getId();
        String operacion = "";

        switch (id) {
            case "btnSuma":
                operacion = "+";
                break;
            case "btnResta":
                operacion = "-";
                break;
            case "btnMulti":
                operacion = "×";
                break;
        }

        operacionJugador.append(" " + operacion + " ");
        lblRec2.setText(operacionJugador.toString());
        ultimoFueNumero = false;
        btnBorrar.setDisable(operacionJugador.length() == 0);
    }
  
    
    @FXML
    private void cerrarAplicacion() {
        System.exit(0);
    }
    
    private Instancia elegirInstancia() throws FileNotFoundException {
    	
    	Gson gson = new Gson();
        FileReader reader = new FileReader("data/instancias.json");
        Instancia instancia = null;
        
        Type instanciaListType = new TypeToken<List<Instancia>>() {}.getType();
        List<Instancia> todasInstancias = gson.fromJson(reader, instanciaListType);
        
        Random random = new Random();
        int idElegido = random.nextInt(17) + 1;
        
        for (Instancia ins : todasInstancias) {
            if (ins.getId() == idElegido) {
            	
            	instancia = new Instancia(ins.getId(),ins.getNumeros(),ins.getObjetivo(), ins.getSolucionIA(),ins.getTotalIA());
            	 break;
            }
        }
        
		return instancia;
    	
    }
    
    
    private void asignarValores() {
        int[] numeros = instanciaActual.getNumeros();

        btn1.setText(String.valueOf(numeros[0]));
        btn2.setText(String.valueOf(numeros[1]));
        btn3.setText(String.valueOf(numeros[2]));
        btn4.setText(String.valueOf(numeros[3]));
        btn5.setText(String.valueOf(numeros[4]));
        btn6.setText(String.valueOf(numeros[5]));

        lblRec1.setText(String.valueOf(instanciaActual.getObjetivo()));
    }
    
    @FXML
    private void clickBorrar(ActionEvent event) {
        if (operacionJugador.length() == 0) return;

        String[] partes = operacionJugador.toString().trim().split(" ");
        if (partes.length == 0) return;

        String ultimo = partes[partes.length - 1];

        // Eliminar último elemento del StringBuilder
        int index = operacionJugador.lastIndexOf(ultimo);
        if (index != -1) {
            operacionJugador.delete(index, operacionJugador.length());
        }

        // Si era un número, reactiva el botón
        if (ultimo.matches("\\d+")) {
            reactivarBotonNumero(Integer.parseInt(ultimo));
            ultimoFueNumero = false;
        } else {
            ultimoFueNumero = true;
        }

        lblRec2.setText(operacionJugador.toString());
        btnBorrar.setDisable(operacionJugador.length() == 0);
    }
    
    
    private void reactivarBotonNumero(int valor) {
        for (Button btn : List.of(btn1, btn2, btn3, btn4, btn5, btn6)) {
            if (btn.getText().equals(String.valueOf(valor)) && btn.isDisable()) {
                btn.setDisable(false);
                break;
            }
        }
    }
    
  
    
    
    private void terminarJuego(int resultadoJugador) throws ClassNotFoundException, SQLException {
        


        int objetivo = instanciaActual.getObjetivo();
        int valorIA = instanciaActual.getTotalIA();

        int difJugador = Math.abs((int) resultadoJugador - objetivo);
        int difIA = Math.abs(valorIA - objetivo);

        String resultado;
        int cambioElo = 0;
        if (difJugador < difIA) {
            resultado = "¡Ganaste! +25 ELO";
            cambioElo = 25;
        } else if (difJugador > difIA) {
            resultado = "Perdiste. -25 ELO";
            cambioElo = -25;
        } else {
            resultado = "Empate. Sin cambios.";
        }

        AccesoBBDD.actualizarElo(cambioElo, usuarioActual.getId());
        AccesoBBDD.insertarPartida(usuarioActual.getId(), cambioElo, 0, difJugador);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin del juego");
        alert.setHeaderText("Resultado");
        alert.setContentText(
            "Tu resultado: " + resultadoJugador + "\n" +
            "Solución IA: " + instanciaActual.getSolucionIA() + " = " + valorIA + "\n" +
            resultado
        );
        alert.showAndWait();

        panelJuego.setDisable(true); // opcional
    }
    

    
    private int evaluar(String expresion) {
        String[] tokens = expresion.trim().split(" ");
        
        // Si la expresión termina en un operador, eliminarlo
        while (tokens.length > 0 && (tokens[tokens.length - 1].equals("+") || 
                                      tokens[tokens.length - 1].equals("-") || 
                                      tokens[tokens.length - 1].equals("×"))) {
            // Eliminar el último operador
            expresion = expresion.substring(0, expresion.lastIndexOf(" " + tokens[tokens.length - 1]));
            tokens = expresion.trim().split(" ");
        }

        if (tokens.length == 0) return 0;

        try {
            int resultado = Integer.parseInt(tokens[0]);

            for (int i = 1; i < tokens.length - 1; i += 2) {
                String operador = tokens[i];
                int siguienteNumero = Integer.parseInt(tokens[i + 1]);

                switch (operador) {
                    case "+": resultado += siguienteNumero; break;
                    case "-": resultado -= siguienteNumero; break;
                    case "×": resultado *= siguienteNumero; break;
                    default: return 0;
                }
            }

            return resultado;
        } catch (NumberFormatException e) {
            System.err.println("Expresión inválida: " + expresion);
            return 0;
        }
    }
    private void iniciarTemporizador() {
        tiempoRestante = 30; 
        lblTiempo.setText(String.valueOf(tiempoRestante));

        if (temporizador != null) {
            temporizador.stop();
        }

        temporizador = new Timeline(
            new javafx.animation.KeyFrame(
                javafx.util.Duration.seconds(1),
                e -> {
                    tiempoRestante--;
                    lblTiempo.setText(String.valueOf(tiempoRestante));
                    
                    if (tiempoRestante <= 0) {
                        temporizador.stop();
                        Platform.runLater(() -> {
                            try {
                                int resultado = evaluar(operacionJugador.toString());
                                terminarJuego(resultado);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                }
            )
        );
        temporizador.setCycleCount(Timeline.INDEFINITE);
        temporizador.play();
    }
  

}
