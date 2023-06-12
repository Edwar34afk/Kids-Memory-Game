package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	private static final int NUM_CARTAS = 16;
    private static final int NUM_PARES = 8;
    private static final int COLUMNAS = 4;
    private static final int FILAS = 4;
    private static final int ANCHO_CARTA = 80;
    private static final int ALTO_CARTA = 120;

    private Carta[] cartas;
    private Carta cartaSeleccionada;
    private int paresEncontrados;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kids Memory Game");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        cartas = new Carta[NUM_CARTAS];
        generarCartas();

        int index = 0;
        for (int row = 0; row < FILAS; row++) {
            for (int col = 0; col < COLUMNAS; col++) {
                gridPane.add(cartas[index], col, row);
                index++;
            }
        }

        Scene scene = new Scene(gridPane, COLUMNAS * ANCHO_CARTA, FILAS * ALTO_CARTA);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void generarCartas() {
        int[] valores = new int[NUM_PARES];
        for (int i = 0; i < NUM_PARES; i++) {
            valores[i] = i;
        }

        for (int i = 0; i < NUM_CARTAS; i++) {
            int indiceValor = i % NUM_PARES;
            int valor = valores[indiceValor];
            cartas[i] = new Carta(valor);
        }
    }

    private class Carta extends StackPane {
        private int valor;
        private Rectangle fondo;
        private boolean volteada;

        public Carta(int valor) {
            this.valor = valor;
            this.fondo = new Rectangle(ANCHO_CARTA, ALTO_CARTA);
            this.fondo.setFill(Color.BLUE);
            this.fondo.setStroke(Color.BLACK);

            Label etiqueta = new Label();
            etiqueta.setText(Integer.toString(valor));
            etiqueta.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            getChildren().addAll(fondo, etiqueta);
            setOnMouseClicked(e -> voltear());
            volteada = false;
        }

        public void voltear() {
            if (volteada) {
                return;
            }

            if (cartaSeleccionada == null) {
                cartaSeleccionada = this;
                volteada = true;
                fondo.setFill(Color.WHITE);
            } else {
                if (cartaSeleccionada.valor == this.valor && cartaSeleccionada != this) {
                    cartaSeleccionada.volteada = true;
                    cartaSeleccionada.setDisable(true);
                    this.volteada = true;
                    this.setDisable(true);
                    paresEncontrados++;

                    if (paresEncontrados == NUM_PARES) {
                        // Todos los pares han sido encontrados, aquí puedes mostrar un mensaje de victoria.
                        System.out.println("¡Has ganado!");
                    }
                } else {
                    cartaSeleccionada.volteada = false;
                    cartaSeleccionada.fondo.setFill(Color.BLUE);
                    cartaSeleccionada = null;
                    volteada = false;
                    fondo.setFill(Color.BLUE);
                }
            }
        }
    }
}