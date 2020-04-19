package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
  private Scene welcomeScene;
  private Scene farmReportScene;
  private Label farm;
  private TextField farmTF;
  private Label header;
  private TableColumn<String, DataEntry> column1;
  private Label year;
  private TextField yearTF;
  private VBox entriesVB;
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

    // Sets up welcome header
    Label welcomeHeader = new Label("Welcome to The Farm Report");
    welcomeHeader.setAlignment(Pos.CENTER);
    welcomeHeader.setFont(Font.font("Questrial", 25));

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
    welcome.setMargin(welcomeHeader, new Insets(50, 0, 0, 0));
    welcome.setAlignment(welcomeHeader, Pos.CENTER);
    welcome.setCenter(optionsVBox);

    // Create scene
    welcomeScene = new Scene(welcome, WINDOW_WIDTH, WINDOW_HEIGHT);



    // creates farm report header

    VBox headerBox = new VBox();

    header = new Label("Farm Report");
    Label milkTotalLabel = new Label("Total Milk Weight:");
    header.setFont(Font.font("Times New Roman", 18));
    milkTotalLabel.setFont(Font.font("Times New Roman", 16));
    headerBox.setAlignment(Pos.CENTER);
    headerBox.getChildren().add(header);
    headerBox.getChildren().add(milkTotalLabel);

    // created right panel side asking for user input
    entriesVB = new VBox();
    farm = new Label("Farm ID:");
    farm.setFont(Font.font("Times New Roman", 18));
    farmTF = new TextField();
    farmTF.setPromptText("Enter Farm ID");
    farmTF.setAlignment(Pos.CENTER);
    year = new Label("Year:");
    year.setFont(Font.font("Times New Roman", 18));
    yearTF = new TextField();
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
    column1 = new TableColumn<>("Month");
    // column1.setCellValueFactory(new PropertyValueFactory("month"));
    TableColumn<String, DataEntry> column2 = new TableColumn<>("Percentage of Total");
    // column2.setCellValueFactory(new PropertyValueFactory("percent"));
    tableView.getColumns().add(column1);
    tableView.getColumns().add(column2);
    tableView.setPlaceholder(new Label("No data to display."));
    // tableView.getItems().add(new DataEntry("January", 0.25));
    // tableView.getItems().add(new DataEntry("Febuary", 0.75));
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    VBox vbox = new VBox(tableView);

    // Bottom Panel
    Button button = new Button("Back");
    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent arg0) {
        // TODO Auto-generated method stub
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
      }
    });

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
    farmReportScene = new Scene(reportReport, WINDOW_WIDTH, WINDOW_HEIGHT);

    // Add the stuff and set the primary stage
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(welcomeScene);
    primaryStage.show();

    farmReportButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        // TODO Auto-generated method stub
        setReportScene(primaryStage, 'F');
      }

    });
    annualReportButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        // TODO Auto-generated method stub
        setReportScene(primaryStage, 'A');
      }

    });
    monthlyReportButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        // TODO Auto-generated method stub
        setReportScene(primaryStage, 'M');
      }

    });
    dateRangeReportButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        // TODO Auto-generated method stub
        setReportScene(primaryStage, 'D');
      }

    });
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }

  private void setReportScene(Stage primaryStage, char report) {
    if (report == 'F') {
      primaryStage.setTitle(APP_TITLE);
      primaryStage.setScene(farmReportScene);
      primaryStage.show();
    } else if (report == 'M') {
      farm.setText("Month: ");
      farmTF.setPromptText("Enter A Month");
      header.setText("Monthly Report");
      column1.setText("Farm ID");
      primaryStage.setTitle(APP_TITLE);
      primaryStage.setScene(farmReportScene);
      primaryStage.show();
    } else if (report == 'A') {
      farm.setVisible(false);
      farmTF.setVisible(false);
      header.setText("Annual Report");
      column1.setText("Farm ID");
      primaryStage.setTitle(APP_TITLE);
      primaryStage.setScene(farmReportScene);
      primaryStage.show();
    } else if (report == 'D') {
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
      primaryStage.setTitle(APP_TITLE);
      primaryStage.setScene(farmReportScene);
      primaryStage.show();
    }


  }
}
