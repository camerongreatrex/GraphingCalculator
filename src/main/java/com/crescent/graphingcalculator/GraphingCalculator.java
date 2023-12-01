package com.crescent.graphingcalculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GraphingCalculator extends Application {

    private TextField slopeField;
    private TextField interceptField;
    private TextField constantField;
    private Pane mainPane;
    private LineChart<Number, Number> chart;
    private RadioButton linearRadioButton;
    private RadioButton parabolaRadioButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Graphing Calculator");


        //TODO
            //fix scaling on the axis
            //make points hoverable
            //fix some labeling on the axis dissapearing along with random axis lines drawn


        mainPane = new Pane();
        primaryStage.setResizable(false);

        // Create the axis
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("y");

        // Create the line chart
        chart = new LineChart<>(xAxis, yAxis);
        chart.setLayoutX(20);
        chart.setLayoutY(150); // Adjusted layout for graph to avoid overlap
        chart.setPrefSize(650, 650);
        chart.setLegendVisible(false);

        // Text fields for slope, intercept, and constant
        slopeField = new TextField();
        slopeField.setLayoutX(20);
        slopeField.setLayoutY(20);
        slopeField.setPrefWidth(180); // Adjusted width for text fields

        interceptField = new TextField();
        interceptField.setLayoutX(20);
        interceptField.setLayoutY(60);
        interceptField.setPrefWidth(180); // Adjusted width for text fields

        constantField = new TextField();
        constantField.setLayoutX(20);
        constantField.setLayoutY(100);
        constantField.setPrefWidth(180); // Adjusted width for text fields

        // Radio buttons for selecting function type
        linearRadioButton = new RadioButton("Linear Function");
        linearRadioButton.setSelected(true);
        linearRadioButton.setLayoutX(250);
        linearRadioButton.setLayoutY(20);

        parabolaRadioButton = new RadioButton("Parabola");
        parabolaRadioButton.setLayoutX(250);
        parabolaRadioButton.setLayoutY(60);

        ToggleGroup functionToggleGroup = new ToggleGroup();
        linearRadioButton.setToggleGroup(functionToggleGroup);
        parabolaRadioButton.setToggleGroup(functionToggleGroup);

        // Button to plot the graph
        Button plotGraphButton = new Button("Plot Graph");
        plotGraphButton.setLayoutX(450);
        plotGraphButton.setLayoutY(20);
        plotGraphButton.setOnAction(e -> plotGraph());

        // Button to reset the graph
        Button resetButton = new Button("Reset Graph");
        resetButton.setLayoutX(450);
        resetButton.setLayoutY(60);
        resetButton.setOnAction(e -> resetGraph());

        mainPane.getChildren().addAll(chart, slopeField, interceptField, constantField, linearRadioButton,
                parabolaRadioButton, plotGraphButton, resetButton);

        Scene scene = new Scene(mainPane, 800, 800); // Adjusted scene height
        primaryStage.setScene(scene);

        // Set initial prompt texts based on the selected function type
        slopeField.setPromptText("Enter slope (m)");
        interceptField.setPromptText("Enter y-intercept (b)");
        constantField.setVisible(false);
        constantField.setPromptText("Enter constant (a)");

        constantField.setVisible(false);
        linearRadioButton.setOnAction(e -> {
            constantField.setVisible(false);
            resetGraph();
        });
        parabolaRadioButton.setOnAction(e -> {
            constantField.setVisible(true);
            resetGraph();
        });

        primaryStage.show();
    }

    // Method to plot the selected graph (linear or parabolic)
    private void plotGraph() {
        try {
            if (linearRadioButton.isSelected()) {
                plotLine();
            } else if (parabolaRadioButton.isSelected()) {
                plotParabola();
            }
        } catch (NumberFormatException ex) {
            // Handle invalid input
            Label errorLabel = new Label("Please enter valid numbers for coefficients.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135); // Adjusted layout for error label
            mainPane.getChildren().removeIf(node -> node instanceof Label);
            mainPane.getChildren().add(errorLabel);
        }
    }

    // Method to plot a linear function
    private void plotLine() {
        try {
            double slope = Double.parseDouble(slopeField.getText());
            double intercept = Double.parseDouble(interceptField.getText());

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            // Plotting the graph for x values from -10 to 10
            for (double x = -10; x <= 10; x += 0.1) {
                double y = slope * x + intercept;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            // Handle invalid input
            Label errorLabel = new Label("Please enter valid numbers for slope and intercept.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135); // Adjusted layout for error label
            mainPane.getChildren().removeIf(node -> node instanceof Label);
            mainPane.getChildren().add(errorLabel);
        }
    }

    // Method to plot a parabolic function
    private void plotParabola() {
        try {
            double a = Double.parseDouble(slopeField.getText()); // Coefficient of x^2
            double b = Double.parseDouble(interceptField.getText()); // Coefficient of x
            double c = Double.parseDouble(constantField.getText()); // Constant


            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            // Plotting the graph for x values from -10 to 10
            for (double x = -10; x <= 10; x += 0.1) {
                double y = a * x * x + b * x + c;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            // Handle invalid input
            Label errorLabel = new Label("Please enter valid numbers for coefficients.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135); // Adjusted layout for error label
            mainPane.getChildren().removeIf(node -> node instanceof Label);
            mainPane.getChildren().add(errorLabel);
        }
    }

    // Method to reset the graph and input fields
    private void resetGraph() {
        chart.getData().clear();
        slopeField.clear();
        interceptField.clear();
        constantField.clear();

        // Set initial prompt texts based on the selected function type
        if (linearRadioButton.isSelected()) {
            slopeField.setPromptText("Enter slope (m)");
            interceptField.setPromptText("Enter y-intercept (b)");
        } else if (parabolaRadioButton.isSelected()) {
            constantField.setPromptText("Enter constant (a)");
        }
    }
}