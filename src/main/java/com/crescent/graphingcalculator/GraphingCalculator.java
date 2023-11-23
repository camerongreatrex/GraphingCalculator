package com.crescent.graphingcalculator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphingCalculator extends Application {
    // stage renamed to window
    public static Stage window;
    // pane renamed to mainPane
    public static Pane mainPane = new Pane();
    //create the main menu scene
    public static Scene menuScene = new Scene(mainPane, 1000, 700);
    //create buttons for the calculator
    public static Button startCalcButton = new Button("▷"), closeCalcButton = new Button("X");
    //create the groups for the calculator
    public static Group graphGroup = new Group();
    //create the scenes for the calculator
    public static Scene graphScene = new Scene(graphGroup, 1000, 700, Color.WHITE);

    @Override
    public void start(Stage stage) throws IOException {
        //set the stage to the main stage
        window = stage;
        // show the stage, make it un-resizable, name the window, and set the first scene to the menu scene
        window.show();
        window.setResizable(false);
        window.setTitle("Graphing Calculator");
        window.setScene(menuScene);
        //set properties for the calculator start button
        startCalcButton.setLayoutX(400);
        startCalcButton.setLayoutY(250);
        mainPane.getChildren().add(startCalcButton);
        startCalcButton.setMinWidth(200);
        startCalcButton.setMinHeight(100);
        startCalcButton.setStyle(
                "-fx-font-size: 50px;-fx-background-color: Black;-fx-text-fill: white; -fx-background-radius: 15px;");
        //set properties for the calculator close button
        closeCalcButton.setLayoutX(400);
        closeCalcButton.setLayoutY(375);
        mainPane.getChildren().add(closeCalcButton);
        closeCalcButton.setMinWidth(200);
        closeCalcButton.setMinHeight(100);
        closeCalcButton.setStyle(
                "-fx-font-size: 50px;-fx-background-color: Black;-fx-text-fill: white; -fx-background-radius: 15px;");
        startCalcButton.setOnAction(startButtonEvent -> {
            window.setScene(graphScene);
            //create number axes
            final NumberAxis xAxis = new NumberAxis(-500, 500, 100);
            final NumberAxis yAxis = new NumberAxis(-500, 500, 100);

            // Create scatter chart
            final ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
            scatterChart.setPrefSize(650, 650);
            scatterChart.setLayoutX(350);
            scatterChart.setLayoutY(0);
            xAxis.setTickLabelGap(10);
            yAxis.setTickLabelGap(10);
            xAxis.setTickLabelRotation(90);
            yAxis.setTickLabelRotation(0);
            xAxis.setTickMarkVisible(false);
            yAxis.setTickMarkVisible(false);
            scatterChart.setLegendVisible(false);


            // Define series for quadrants
            XYChart.Series<Number, Number> quadrant1 = new XYChart.Series<>();
            XYChart.Series<Number, Number> quadrant2 = new XYChart.Series<>();
            XYChart.Series<Number, Number> quadrant3 = new XYChart.Series<>();
            XYChart.Series<Number, Number> quadrant4 = new XYChart.Series<>();
            //quadrant1.getData().add(new XYChart.Data<>(50, 50));
            scatterChart.getData().addAll(quadrant1, quadrant2, quadrant3, quadrant4);

            //add the chart to the graph group
            graphGroup.getChildren().add(scatterChart);
        });

        window.setOnCloseRequest(closeEvent -> {
            Platform.exit();
        });
        closeCalcButton.setOnAction(closeButtonEvent -> {
            window.close();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}