package application;

import java.io.FileNotFoundException;

import javafx.application.Application;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	private TableColumn<String, DataEntry> column1;
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
		Button importFileButton = new Button("Import Data");
		importFileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					cheeseFactory = new CheeseFactory("Name");
					cheeseFactory.insertData(importFilePathTF.getText());
					welcomeHeader.setText("Welcome to The Farm Report");
				} catch (FileNotFoundException e) {
					cheeseFactory = null;
					welcomeHeader.setText("Invalid file name. Please try again.");
				}
			}
		});
		importFileButton.setAlignment(Pos.CENTER);
		importFileVBox.getChildren().addAll(importFileLabel, importFilePathTF, importFileButton);

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
		TableView tableView = new TableView();
		column1 = new TableColumn<>();
		// column1.setCellValueFactory(new PropertyValueFactory("month"));
		TableColumn<String, DataEntry> column2 = new TableColumn<>("Percent of Total");
		// column2.setCellValueFactory(new PropertyValueFactory("percent"));
		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		tableView.setPlaceholder(new Label("No data to display."));

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
					setReportScene(primaryStage, 'F');
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
					setReportScene(primaryStage, 'A');
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
					setReportScene(primaryStage, 'M');
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
					setReportScene(primaryStage, 'D');
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
	private void setReportScene(Stage primaryStage, char report) {
		if (report == 'F') { //farm report
			entriesVB.getChildren().clear();
			entriesVB.getChildren().addAll(oneLabel, oneTF, display, filePath, writeToFile);
			column1.setText("Month");
			oneLabel.setText("Farm ID:");
			oneTF.setPromptText("Enter Farm ID");
			minMaxAverage.setVisible(false);
			milkTotalLabel.setVisible(false);
			primaryStage.setTitle(APP_TITLE);
			primaryStage.setScene(reportScene);
			primaryStage.show();
		} else if (report == 'M') { //monthly report
			entriesVB.getChildren().clear();
			entriesVB.getChildren().addAll(oneLabel, oneTF, display, filePath, writeToFile);
			oneLabel.setText("Month: ");
			oneTF.setPromptText("Ex: For January enter 1");
			header.setText("Monthly Report");
			column1.setText("Farm ID");
			minMaxAverage.setVisible(false);
			milkTotalLabel.setVisible(false);
			primaryStage.setTitle(APP_TITLE);
			primaryStage.setScene(reportScene);
			primaryStage.show();
		} else if (report == 'A') { //annual report
			entriesVB.getChildren().clear();
			entriesVB.getChildren().addAll(display, filePath, writeToFile);
			header.setText("Annual Report");
			column1.setText("Farm ID");
			minMaxAverage.setVisible(false);
			milkTotalLabel.setVisible(false);
			primaryStage.setTitle(APP_TITLE);
			primaryStage.setScene(reportScene);
			primaryStage.show();
		} else if (report == 'D') { //date report
			entriesVB.getChildren().clear();
			entriesVB.getChildren().addAll(oneLabel, beginningDate, twoLabel, endDate, display, filePath, writeToFile);
			oneLabel.setText("Beginning Date:");
			twoLabel.setText("End Date:");
			header.setText("Date Range Report");
			column1.setText("Farm ID");
			minMaxAverage.setVisible(false);
			milkTotalLabel.setVisible(false);
			primaryStage.setTitle(APP_TITLE);
			primaryStage.setScene(reportScene);
			primaryStage.show();
		}

	}
}
