package com.crescent.graphingcalculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GraphingCalculator extends Application {

    private TextField slopeField;
    private TextField interceptField;
    private Pane mainPane;
    private LineChart<Number, Number> lineChart;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Graphing Calculator");

        mainPane = new Pane();

        // Create the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("y");

        // Create the line chart
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setLayoutX(20);
        lineChart.setLayoutY(90);
        lineChart.setPrefSize(560, 300);
        lineChart.setLegendVisible(false);

        // Text fields for slope and intercept
        slopeField = new TextField();
        slopeField.setPromptText("Enter slope (m)");
        slopeField.setLayoutX(20);
        slopeField.setLayoutY(20);

        interceptField = new TextField();
        interceptField.setPromptText("Enter intercept (c)");
        interceptField.setLayoutX(20);
        interceptField.setLayoutY(50);

        // Button to plot the graph
        Button plotButton = new Button("Plot Graph");
        plotButton.setLayoutX(180);
        plotButton.setLayoutY(20);
        plotButton.setOnAction(e -> plotGraph());

        // Button to reset the graph
        Button resetButton = new Button("Reset Graph");
        resetButton.setLayoutX(280);
        resetButton.setLayoutY(20);
        resetButton.setOnAction(e -> resetGraph());

        mainPane.getChildren().addAll(slopeField, interceptField, lineChart, plotButton, resetButton);

        // Bindings for scalable content
        lineChart.prefWidthProperty().bind(mainPane.widthProperty().subtract(40)); // Adjust chart width
        lineChart.prefHeightProperty().bind(mainPane.heightProperty().subtract(130)); // Adjust chart height

        Scene scene = new Scene(mainPane, 600, 450);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void plotGraph() {
        try {
            double slope = Double.parseDouble(slopeField.getText());
            double intercept = Double.parseDouble(interceptField.getText());

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            // Plotting the graph for x values from -10 to 10
            for (double x = -10; x <= 10; x += 0.1) {
                double y = slope * x + intercept;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            lineChart.getData().clear();
            lineChart.getData().add(series);
        } catch (NumberFormatException ex) {
            // Handle invalid input
            Label errorLabel = new Label("Please enter valid numbers for slope and intercept.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(80);
            mainPane.getChildren().removeIf(node -> node instanceof Label);
            mainPane.getChildren().add(errorLabel);
        }
    }

    private void resetGraph() {
        lineChart.getData().clear();
        slopeField.clear();
        interceptField.clear();
    }
}
