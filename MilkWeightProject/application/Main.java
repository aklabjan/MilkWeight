package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 500;
	private static final String APP_TITLE = "Milk Weights";
	private Scene welcomeScene;
	private Scene reportScene;
	private Label oneLabel;
	private TextField oneTF;
	private Label header;
	private TableColumn<DataEntry, String> column1;
	private DatePicker beginningDate;
	private DatePicker endDate;
	private Label twoLabel;
	private TextField twoTF;
	private VBox entriesVB;
	private Button display;
	private Button writeToFile;
	private TextField filePath;
	private Label minMaxAverage;
	private Label milkTotalLabel;
	private CheeseFactory cheeseFactory;
	private DataManager dm;
	private char reportType;
	private TableView<DataEntry> tableView;
	private TableColumn<DataEntry, String> column2;
	private ArrayList<DataEntry> currentData = new ArrayList<DataEntry>();

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Sets up welcome header
		Label welcomeHeader = new Label("Welcome to The Farm Report");
		welcomeHeader.setAlignment(Pos.CENTER);
		welcomeHeader.setFont(Font.font("Questrial", 25));

		// creates left side to import data
		VBox importFileVBox = new VBox();
		importFileVBox.setAlignment(Pos.CENTER);
		Label importFileLabel = new Label("Filepath to folder containing data:");
		TextField importFilePathTF = new TextField();
		importFilePathTF.setPromptText("Entire filepath");
		importFilePathTF.setAlignment(Pos.CENTER);
		Label yearLabel = new Label("Year folder contains data for:");
		TextField yearTF = new TextField();
		importFilePathTF.setPromptText("Entire year");
		importFilePathTF.setAlignment(Pos.CENTER);
		Button importFileButton = new Button("Import Data");
		importFileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					cheeseFactory = new CheeseFactory("Name");
					cheeseFactory.insertData(importFilePathTF.getText(), yearTF.getText());
					dm = new DataManager(cheeseFactory);
					welcomeHeader.setText("Welcome to The Farm Report");
				} catch (FileNotFoundException e1) {
					cheeseFactory = null;
					welcomeHeader.setText("Invalid file name or year. Please try again.");
				}
			}
		});
		importFileButton.setAlignment(Pos.CENTER);
		importFileVBox.getChildren().addAll(importFileLabel, importFilePathTF, yearLabel, yearTF, importFileButton);

		// Set up options of reports
		VBox optionsVBox = new VBox();

		Button farmReportButton = new Button();
		farmReportButton.setText("Farm Report");
		farmReportButton.setMinSize(150, 50);

		Button annualReportButton = new Button();
		annualReportButton.setText("Annual Report");
		annualReportButton.setMinSize(150, 50);

		Button monthlyReportButton = new Button();
		monthlyReportButton.setText("Monthly Report");
		monthlyReportButton.setMinSize(150, 50);

		Button dateRangeReportButton = new Button();
		dateRangeReportButton.setText("Date Range Report");
		dateRangeReportButton.setMinSize(150, 50);

		optionsVBox.setAlignment(Pos.CENTER);
		optionsVBox.getChildren().addAll(farmReportButton, annualReportButton, monthlyReportButton,
				dateRangeReportButton);

		// Main layout is Border Pane
		BorderPane welcome = new BorderPane();

		// Add to the panel
		welcome.setTop(welcomeHeader);
		welcome.setLeft(importFileVBox);
		welcome.setMargin(welcomeHeader, new Insets(50, 0, 0, 0));
		welcome.setAlignment(welcomeHeader, Pos.CENTER);
		welcome.setCenter(optionsVBox);

		// Create scene
		welcomeScene = new Scene(welcome, WINDOW_WIDTH, WINDOW_HEIGHT);

		// creates farm report header

		VBox headerBox = new VBox();

		header = new Label("Farm Report");
		milkTotalLabel = new Label("Total Milk Weight:");
		minMaxAverage = new Label("Average Weight: Maximum Weight: Average Weight:");
		header.setFont(Font.font("Times New Roman", 18));
		milkTotalLabel.setFont(Font.font("Times New Roman", 16));
		headerBox.setAlignment(Pos.CENTER);
		headerBox.getChildren().addAll(header, milkTotalLabel, minMaxAverage);

		// created right panel side asking for user input
		entriesVB = new VBox();
		oneLabel = new Label();
		oneLabel.setFont(Font.font("Times New Roman", 18));
		oneTF = new TextField();
		oneTF.setAlignment(Pos.CENTER);
		twoLabel = new Label();
		twoLabel.setFont(Font.font("Times New Roman", 18));
		twoTF = new TextField();
		twoTF.setAlignment(Pos.CENTER);
		display = new Button("Display");
		filePath = new TextField();
		filePath.setPromptText("Entire File Path Name");
		filePath.setAlignment(Pos.CENTER);
		writeToFile = new Button("Write To File");
		beginningDate = new DatePicker();
		endDate = new DatePicker();
		entriesVB.setAlignment(Pos.CENTER);
		// creates farm report data table
		tableView = new TableView<DataEntry>();
		column1 = new TableColumn<>();
		column2 = new TableColumn<>("Percent of Total");
		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox vbox = new VBox(tableView);

		// Bottom Panel
		Button button = new Button("Back");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setTitle(APP_TITLE);
				primaryStage.setScene(welcomeScene);
				primaryStage.show();
			}
		});

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane reportReport = new BorderPane();

		// Add to the panel
		reportReport.setTop(headerBox);
		reportReport.setCenter(vbox);
		reportReport.setBottom(button);
		reportReport.setRight(entriesVB);
		// report.setCenter();
		reportScene = new Scene(reportReport, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(welcomeScene);
		primaryStage.show();

		farmReportButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (cheeseFactory == null) {
					welcomeHeader.setText("Please upload data before selecting a report.");
				} else {
					welcomeHeader.setText("Welcome to The Farm Report");
					reportType = 'F';
					setReportScene(primaryStage);
				}

			}
		});
		annualReportButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (cheeseFactory == null) {
					welcomeHeader.setText("Please upload data before selecting a report.");
				} else {
					welcomeHeader.setText("Welcome to The Farm Report");
					reportType = 'A';
					setReportScene(primaryStage);
				}
			}
		});
		monthlyReportButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (cheeseFactory == null) {
					welcomeHeader.setText("Please upload data before selecting a report.");
				} else {
					welcomeHeader.setText("Welcome to The Farm Report");
					reportType = 'M';
					setReportScene(primaryStage);
				}
			}
		});
		dateRangeReportButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (cheeseFactory == null) {
					welcomeHeader.setText("Please upload data before selecting a report.");
				} else {
					welcomeHeader.setText("Welcome to The Farm Report");
					reportType = 'D';
					setReportScene(primaryStage);
				}
			}
		});

		display.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentData.clear();
				if (reportType == 'F') { // inserts data for farm report
					ObservableList<DataEntry> data = FXCollections.observableArrayList();
					try {
						for (application.DataManager.DataEntry d : dm.getDataForFarmReport(oneTF.getText())) {
							DataEntry temp = new DataEntry(d.column1Data, d.column2Data);
							data.add(temp);
							currentData.add(temp);
							header.setText("Farm Report");
						}
						column1.setCellValueFactory(new PropertyValueFactory<DataEntry, String>("column1Data"));
						column2.setCellValueFactory(new PropertyValueFactory<DataEntry, String>("column2Data"));
						tableView.setItems(data);
						milkTotalLabel.setText("Total Milk Weight: " + dm.total);
						minMaxAverage.setText("Average Weight: " + dm.getMonthlyAverageForFarm()
								+ "    Maximum Weight: " + dm.getMonthlyMaxForFarm() + "    Minimum Weight: "
								+ dm.getMonthlyMinForFarm());
						milkTotalLabel.setVisible(true);
						minMaxAverage.setVisible(true);
					} catch (Exception e) {
						milkTotalLabel.setVisible(false);
						minMaxAverage.setVisible(false);
						header.setText("Error: Please enter a valid farmID in Cheese Factory.");
					}
				} else if (reportType == 'M') { // inserts data for farm report
					ObservableList<DataEntry> data = FXCollections.observableArrayList();
					try {
						for (application.DataManager.DataEntry d : dm.getDataforMonthlyReport(oneTF.getText())) {
							DataEntry temp = new DataEntry(d.column1Data, d.column2Data);
							data.add(temp);
							currentData.add(temp);
							header.setText("Farm Report");
						}
						column1.setCellValueFactory(new PropertyValueFactory<DataEntry, String>("column1Data"));
						column2.setCellValueFactory(new PropertyValueFactory<DataEntry, String>("column2Data"));
						tableView.setItems(data);
						milkTotalLabel.setText("Total Milk Weight: " + dm.total);
						minMaxAverage.setText("Average Weight: " + dm.getMonthlyAverage() + "    Maximum Weight: "
								+ dm.getMonthlyMax() + "    Minimum Weight: " + dm.getMonthlyMin());
						milkTotalLabel.setVisible(true);
						minMaxAverage.setVisible(true);
					} catch (Exception e) {
						milkTotalLabel.setVisible(false);
						minMaxAverage.setVisible(false);
						header.setText("Error: Please enter a value month(1-12).");
					}

				}
			}
		});
		writeToFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File file = new File(filePath.getText());
				try {
					if (!file.exists()) {
						file.createNewFile();
					}

					PrintWriter pw = new PrintWriter(file);
					for (int i = 0; i < currentData.size(); i++) {
						pw.println(currentData.get(i));
					}
					pw.close();
				} catch (IOException e) {

				}

			}

		});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * sets up the report scene based on what report is wanted
	 */
	private void setReportScene(Stage primaryStage) {
		oneTF.clear();
		twoTF.clear();
		tableView.getItems().clear();
		tableView.setPlaceholder(new Label("No data to display."));
		minMaxAverage.setVisible(false);
		milkTotalLabel.setVisible(false);
		entriesVB.getChildren().clear();
		if (reportType == 'F') { // farm report
			entriesVB.getChildren().addAll(oneLabel, oneTF, display, filePath, writeToFile);
			column1.setText("Month");
			oneLabel.setText("Farm ID:");
			oneTF.setPromptText("Enter Farm ID");
		} else if (reportType == 'M') { // monthly report
			entriesVB.getChildren().addAll(oneLabel, oneTF, display, filePath, writeToFile);
			oneLabel.setText("Month: ");
			oneTF.setPromptText("Ex: For January enter 1");
			header.setText("Monthly Report");
			column1.setText("Farm ID");
		} else if (reportType == 'A') { // annual report
			entriesVB.getChildren().addAll(filePath, writeToFile);
			header.setText("Annual Report");
			column1.setText("Farm ID");
			currentData.clear();
			ObservableList<DataEntry> data = FXCollections.observableArrayList();
			for (application.DataManager.DataEntry d : dm.getDataforAnnualReport()) {
				DataEntry temp = new DataEntry(d.column1Data, d.column2Data);
				data.add(temp);
				currentData.add(temp);
			}
			column1.setCellValueFactory(new PropertyValueFactory<DataEntry, String>("column1Data"));
			column2.setCellValueFactory(new PropertyValueFactory<DataEntry, String>("column2Data"));
			tableView.setItems(data);
			milkTotalLabel.setText("Total Milk Weight: " + dm.cheeseFactory.totalWeight);
			milkTotalLabel.setVisible(true);
		} else if (reportType == 'D') { // date report
			entriesVB.getChildren().addAll(oneLabel, beginningDate, twoLabel, endDate, display, filePath, writeToFile);
			oneLabel.setText("Beginning Date:");
			twoLabel.setText("End Date:");
			header.setText("Date Range Report");
			column1.setText("Farm ID");
		}
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(reportScene);
		primaryStage.show();
	}

	public static class DataEntry {
		private final String column1Data;
		private final double column2Data;

		public DataEntry(String d1, double d2) {
			column1Data = d1;
			column2Data = d2;
		}

		public String getColumn1Data() {
			return column1Data;
		}

		public double getColumn2Data() {
			return column2Data;
		}

		public String toString() {
			return column1Data + "  " + column2Data;
		}
	}
}
