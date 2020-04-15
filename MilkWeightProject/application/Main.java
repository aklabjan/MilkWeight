package application;

import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "Milk Weights";

	private class DataEntry {
		private String month;
		private double percent;

		public DataEntry(String m, double p) {
			month = m;
			percent = p;
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Create a vertical box with Hello labels for each args
		HBox yearFarm = new HBox();
		VBox headerBox = new VBox();
		yearFarm.getChildren().add(new Label("Farm: 0000000   "));
		yearFarm.getChildren().add(new Label("Year: 0000"));
		headerBox.getChildren().add(new Label("Farm Report"));
		headerBox.getChildren().add(yearFarm);
		headerBox.getChildren().add(new Label("Total Milk Weight: 10"));

		TableView tableView = new TableView();

		TableColumn<String, DataEntry> column1 = new TableColumn<>("Month");
		column1.setCellValueFactory(new PropertyValueFactory("month"));

		TableColumn<String, DataEntry> column2 = new TableColumn<>("Percentage of Total");
		column2.setCellValueFactory(new PropertyValueFactory("percent"));

		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		
		//tableView.setPlaceholder(new Label("No rows to display"));
		tableView.getItems().add(new DataEntry("January", 0.25));
		tableView.getItems().add(new DataEntry("Febuary", 0.75));
		//tableView.setPrefWidth(300);
        //tableView.setPrefHeight(400);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox vbox = new VBox(tableView);

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane report = new BorderPane();

		// Add the vertical box to the center of the root pane
		report.setTop(headerBox);
		report.setCenter(vbox);
		// report.setCenter();
		Scene reportScene = new Scene(report, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(reportScene);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
