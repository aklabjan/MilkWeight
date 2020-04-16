package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also

	private static final int WINDOW_WIDTH = 400;
	private static final int WINDOW_HEIGHT = 450;
	private static final String APP_TITLE = "Milk Weights";
	private boolean isFarmReport;
	private boolean isAnnualReport;
	private boolean isMonthlyReport;
	private boolean isDateRangeReport;

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

		// creates farm report header

		VBox headerBox = new VBox();

		Label header = new Label("Farm Report");
		Label milkTotalLabel = new Label("Total Milk Weight:");
		header.setFont(Font.font("Times New Roman", 18));
		milkTotalLabel.setFont(Font.font("Times New Roman", 16));
		headerBox.setAlignment(Pos.CENTER);
		headerBox.getChildren().add(header);
		headerBox.getChildren().add(milkTotalLabel);

		// created right panel side asking for user input
		VBox entriesVB = new VBox();
		Label farm = new Label("Farm ID:");
		farm.setFont(Font.font("Times New Roman", 18));
		TextField farmTF = new TextField();
		farmTF.setPromptText("Enter Farm ID");
		farmTF.setAlignment(Pos.CENTER);
		Label year = new Label("Year:");
		year.setFont(Font.font("Times New Roman", 18));
		TextField yearTF = new TextField();
		yearTF.setPromptText("Enter A Year");
		yearTF.setAlignment(Pos.CENTER);
		Button display = new Button("Display");
		TextField filePath = new TextField();
		filePath.setPromptText("Entire File Path Name");
		filePath.setAlignment(Pos.CENTER);
		Button writeToFile = new Button("Write To File");
		entriesVB.setAlignment(Pos.CENTER);
		entriesVB.getChildren().addAll(farm, farmTF, year, yearTF, display, filePath, writeToFile);
		// creates farm report data table
		TableView tableView = new TableView();
		TableColumn<String, DataEntry> column1 = new TableColumn<>("Month");
		//column1.setCellValueFactory(new PropertyValueFactory("month"));
		TableColumn<String, DataEntry> column2 = new TableColumn<>("Percentage of Total");
		//column2.setCellValueFactory(new PropertyValueFactory("percent"));
		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		tableView.setPlaceholder(new Label("No data to display."));
		//tableView.getItems().add(new DataEntry("January", 0.25));
		//tableView.getItems().add(new DataEntry("Febuary", 0.75));
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox vbox = new VBox(tableView);

		// Bottom Panel
		Button button = new Button("Back");
		
		// modifications for each type of report
		if (isAnnualReport) {
			farm.setVisible(false);
			farmTF.setVisible(false);
			header.setText("Annual Report");
			column1.setText("Farm ID");
		} else if (isMonthlyReport) {
			farm.setText("Month: ");
			farmTF.setPromptText("Enter A Month");
			header.setText("Monthly Report");
			column1.setText("Farm ID");
		} else if (isDateRangeReport) {
			header.setText("Date Range Report");
			column1.setText("Farm ID");
			DatePicker beginningDate = new DatePicker();
			DatePicker endDate = new DatePicker();
			farm.setText("Beginning Date:");
			farmTF.setVisible(false);
			year.setText("End Date:");
			yearTF.setVisible(false);
			entriesVB.getChildren().add(1, beginningDate);
			entriesVB.getChildren().add(4, endDate);
		}

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane reportReport = new BorderPane();

		// Add to the panel
		reportReport.setTop(headerBox);
		reportReport.setCenter(vbox);
		reportReport.setBottom(button);
		reportReport.setRight(entriesVB);
		// report.setCenter();
		Scene farmReportScene = new Scene(reportReport, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(farmReportScene);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
