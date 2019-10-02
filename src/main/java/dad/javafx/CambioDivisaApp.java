package dad.javafx;

import dad.cambiodivisa.Divisa;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CambioDivisaApp extends Application {

	private TextField cifraText, convertidoText;
	private ComboBox<Divisa> cifraMonedaCombo, convertidoMonedaCombo;
	private Button cambiaButton;

	@Override
	public void start(Stage primaryStage) throws Exception {

		cifraText = new TextField();
		cifraText.setMaxWidth(80);

		convertidoText = new TextField();
		convertidoText.setMaxWidth(80);
		convertidoText.setEditable(false);

		Divisa divisas[] = { new Divisa("Euro", 1.0), new Divisa("Libra", 0.8873), new Divisa("Dolar", 1.2007),
				new Divisa("Yen", 133.59) };
		cifraMonedaCombo = new ComboBox<Divisa>();
		cifraMonedaCombo.setMaxWidth(80);
		cifraMonedaCombo.getItems().addAll(divisas);
		cifraMonedaCombo.getSelectionModel().select(0);

		convertidoMonedaCombo = new ComboBox<Divisa>();
		convertidoMonedaCombo.setMaxWidth(80);
		convertidoMonedaCombo.getItems().addAll(divisas);
		convertidoMonedaCombo.getSelectionModel().select(1);

		cambiaButton = new Button("Cambiar");
		cambiaButton.setDefaultButton(true);
		cambiaButton.setOnAction(e -> onCambiaButtonAction(e));

		HBox cifraHbox = new HBox(5, cifraText, cifraMonedaCombo);
		cifraHbox.setAlignment(Pos.CENTER);
		HBox convertidoHbox = new HBox(5, convertidoText, convertidoMonedaCombo);
		convertidoHbox.setAlignment(Pos.CENTER);

		VBox root = new VBox(5, cifraHbox, convertidoHbox, cambiaButton);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 320, 200);
		primaryStage.setTitle("Cambio Divisa");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void onCambiaButtonAction(ActionEvent e) {
		Divisa origen = cifraMonedaCombo.getSelectionModel().getSelectedItem();
		Divisa destino = convertidoMonedaCombo.getSelectionModel().getSelectedItem();

		try {
			Double cantidad = Double.parseDouble(cifraText.getText());

			cantidad = destino.fromEuro(origen.toEuro(cantidad));
			convertidoText.setText(String.format("%.2f", Divisa.fromTo(origen, destino, cantidad)));

		} catch (NumberFormatException error) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("CambioDivisa");
			alert.setHeaderText("Campo con Carracter Invalido");
			alert.showAndWait();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
