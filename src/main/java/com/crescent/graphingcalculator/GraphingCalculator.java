package com.crescent.graphingcalculator;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GraphingCalculator extends Application {

    // Text fields, labels, chart, radio buttons, and the main pane for UI elements
    private TextField field1, field2, field3;
    private Label formulaDisplay;
    private Pane mainPane;
    private LineChart<Number, Number> chart;
    private RadioButton linearRadioButton, parabolaRadioButton, squarerootRadioButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Graphing Calculator");

        mainPane = new Pane();
        primaryStage.setResizable(false);

        // Create X and Y axes for the chart
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("y");

        // Create the line chart to display the graph
        chart = new LineChart<>(xAxis, yAxis);
        chart.setLayoutX(20);
        chart.setLayoutY(150);
        chart.setPrefSize(650, 650);
        chart.setLegendVisible(false);
        chart.setAnimated(false);

        // Text fields for user input
        field1 = new TextField();
        field1.setLayoutX(20);
        field1.setLayoutY(20);
        field1.setPrefWidth(180);

        field2 = new TextField();
        field2.setLayoutX(20);
        field2.setLayoutY(60);
        field2.setPrefWidth(180);

        field3 = new TextField();
        field3.setLayoutX(20);
        field3.setLayoutY(100);
        field3.setPrefWidth(180);

        // Label to display the formula of the selected function
        formulaDisplay = new Label("FORMULA: y = mx + b");
        formulaDisplay.setLayoutX(20);

        // Radio buttons to select the function type
        linearRadioButton = new RadioButton("Linear Function");
        linearRadioButton.setSelected(true);
        linearRadioButton.setLayoutX(250);
        linearRadioButton.setLayoutY(20);

        parabolaRadioButton = new RadioButton("Parabola");
        parabolaRadioButton.setLayoutX(250);
        parabolaRadioButton.setLayoutY(60);

        squarerootRadioButton = new RadioButton("Square Root");
        squarerootRadioButton.setLayoutX(250);
        squarerootRadioButton.setLayoutY(100);

        ToggleGroup functionToggleGroup = new ToggleGroup();
        linearRadioButton.setToggleGroup(functionToggleGroup);
        parabolaRadioButton.setToggleGroup(functionToggleGroup);
        squarerootRadioButton.setToggleGroup(functionToggleGroup);

        // Buttons for plotting and resetting the graph
        Button plotGraphButton = new Button("Plot Graph");
        plotGraphButton.setLayoutX(450);
        plotGraphButton.setLayoutY(20);
        plotGraphButton.setOnAction(e -> plotGraph());

        Button resetButton = new Button("Reset Graph");
        resetButton.setLayoutX(450);
        resetButton.setLayoutY(60);
        resetButton.setOnAction(e -> resetGraph());

        // Adding all elements to the main pane
        mainPane.getChildren().addAll(chart, field1, field2, field3, linearRadioButton,
                parabolaRadioButton, squarerootRadioButton, formulaDisplay, plotGraphButton, resetButton);

        // Creating the scene and setting it on the stage
        Scene scene = new Scene(mainPane, 800, 800);
        primaryStage.setScene(scene);

        // Set the default to linear function and organize fields accordingly
        linearRadioButton.setSelected(true);
        field3.setVisible(false);
        resetGraph();

        // Event listeners to update the UI based on the selected function
        linearRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = mx + b");
            organizeFields("Enter slope (m)", "Enter y-intercept (b)", null);
            resetGraph();
        });
        parabolaRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = ax^2 + bx + c");
            organizeFields("Enter coefficient (a)", "Enter constant (b)", "Enter constant (c)");
            resetGraph();
        });
        squarerootRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = a√b(x - h) + k");
            organizeFields("Enter coefficient (a)", "Enter constant (b)", null);
            resetGraph();
        });

        // Display the stage
        primaryStage.show();
    }

    // Method to organize fields based on the selected function
    private void organizeFields(String prompt1, String prompt2, String prompt3) {
        field1.setPromptText(prompt1);
        field2.setPromptText(prompt2);
        if (prompt3 != null) {
            field3.setVisible(true);
            field3.setPromptText(prompt3);
        } else {
            field3.setVisible(false);
            field3.clear();
        }
    }

    // Method to plot the selected graph (linear, parabolic, square root)
    private void plotGraph() {
        if (linearRadioButton.isSelected()) {
            plotLine();
        } else if (parabolaRadioButton.isSelected()) {
            plotParabola();
        } else if (squarerootRadioButton.isSelected()) {
            plotSquareRoot();
        }
    }

    // Method to plot a linear function
    private void plotLine() {
        try {
            double slope = Double.parseDouble(field1.getText());
            double intercept = Double.parseDouble(field2.getText());

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -10; x <= 10; x += 0.1) {
                double y = slope * x + intercept;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            Label errorLabel = new Label("Please enter valid numbers.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135);
            mainPane.getChildren().removeIf(node -> node instanceof Label && !node.equals(formulaDisplay));
            mainPane.getChildren().add(errorLabel);
            // Hide the error label after 3 seconds
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(1.5));
            visiblePause.setOnFinished(event -> mainPane.getChildren().remove(errorLabel));
            visiblePause.play();
        }
    }

    // Method to plot a parabolic function
    private void plotParabola() {
        try {
            double a = Double.parseDouble(field1.getText());
            double b = Double.parseDouble(field2.getText());
            double c = Double.parseDouble(field3.getText());

            // Calculate the x-coordinate of the vertex
            double vertexX = -b / (2 * a);

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = vertexX - 10; x <= vertexX + 10; x += 1) {
                double y = a * x * x + b * x + c;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            Label errorLabel = new Label("Please enter valid numbers");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135);
            mainPane.getChildren().removeIf(node -> node instanceof Label && !node.equals(formulaDisplay));
            mainPane.getChildren().add(errorLabel);
            // Hide the error label after 3 seconds
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(1.5));
            visiblePause.setOnFinished(event -> mainPane.getChildren().remove(errorLabel));
            visiblePause.play();
        }
    }

    // Method to plot a square root function
    private void plotSquareRoot() {
        try {
            double a = Double.parseDouble(field1.getText());
            double b = Double.parseDouble(field2.getText());

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = 0; x <= 10; x += 0.1) {
                double y = a * Math.sqrt(x) + b;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            Label errorLabel = new Label("Please enter valid numbers.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135);
            mainPane.getChildren().removeIf(node -> node instanceof Label && !node.equals(formulaDisplay));
            mainPane.getChildren().add(errorLabel);
            // Hide the error label after 3 seconds
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(1.5));
            visiblePause.setOnFinished(event -> mainPane.getChildren().remove(errorLabel));
            visiblePause.play();
        }
    }

    // Method to reset the graph and input fields
    private void resetGraph() {
        chart.getData().clear();
        field1.clear();
        field2.clear();
        field3.clear();

        if (linearRadioButton.isSelected()) {
            organizeFields("Enter slope (m)", "Enter y-intercept (b)", null);
        } else if (parabolaRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", "Enter constant (b)", "Enter constant (c)");
        } else if (squarerootRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", "Enter constant (b)", null);
        }
    }
}
