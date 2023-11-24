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
    //create the groups for the buttons screen
    public static Group buttonsGroup = new Group();
    //create buttons for numbers 0-9
    public static Button one = new Button("1"), two = new Button("2"), three = new Button("3"), four = new Button("4"), five = new Button("5"),
    six = new Button("6"), seven = new Button("7"), eight = new Button("8"), nine = new Button("9"), zero = new Button("0");
    //create basic math buttons
    public static Button plus = new Button("+"), minus = new Button("-"), exponent = new Button("^"),
    multiply = new Button("*"), divide = new Button("/"), modulus = new Button("%"), openBracket = new Button("("),
    closeBracket = new Button(")");

    private double lastMouseX, lastMouseY;
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
        
        //set properties for numerical buttons
        setButton(one, 50, 50, 100, 350);
        setButton(two, 50, 50, one.getLayoutX() + 50, one.getLayoutY());
        setButton(three, 50, 50, one.getLayoutX() + 100, one.getLayoutY());
        setButton(four, 50, 50, one.getLayoutX(), one.getLayoutY() + 50);
        setButton(five, 50, 50, two.getLayoutX(), two.getLayoutY() + 50);
        setButton(six, 50, 50, three.getLayoutX(), three.getLayoutY() + 50);
        setButton(seven, 50, 50, one.getLayoutX(), one.getLayoutY() + 100);
        setButton(eight, 50, 50, two.getLayoutX(), two.getLayoutY() + 100);
        setButton(nine, 50, 50, three.getLayoutX(), three.getLayoutY() + 100);
        setButton(zero, 50, 50, two.getLayoutX(), two.getLayoutY() + 150);

        //set properties for basic math buttons
        setButton(plus, 50, 50, one.getLayoutX() - 50, one.getLayoutY());
        setButton(minus, 50, 50, four.getLayoutX() - 50, four.getLayoutY());
        setButton(exponent, 50, 50, seven.getLayoutX() - 50, seven.getLayoutY());
        setButton(multiply, 50, 50, three.getLayoutX() - 50, three.getLayoutY());
        setButton(divide, 50, 50, six.getLayoutX() - 50, six.getLayoutY());
        setButton(modulus, 50, 50, nine.getLayoutX() - 50, nine.getLayoutY());
        
        startCalcButton.setOnAction(startButtonEvent -> {
            window.setScene(graphScene);
            //create number axes
            final NumberAxis xAxis = new NumberAxis(-500, 500, 100);
            final NumberAxis yAxis = new NumberAxis(-500, 500, 100);
            //create scatter chart
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


            //define series for quadrants
            XYChart.Series<Number, Number> quadrant1 = new XYChart.Series<>();
            XYChart.Series<Number, Number> quadrant2 = new XYChart.Series<>();
            XYChart.Series<Number, Number> quadrant3 = new XYChart.Series<>();
            XYChart.Series<Number, Number> quadrant4 = new XYChart.Series<>();
            //quadrant1.getData().add(new XYChart.Data<>(50, 50));
            scatterChart.getData().addAll(quadrant1, quadrant2, quadrant3, quadrant4);

            //add the chart to the graph group
            graphGroup.getChildren().add(scatterChart);
            //enable zooming for the scatter chart
            enableChartZooming(scatterChart, xAxis, yAxis);
            enableChartPanning(scatterChart);
        });

        window.setOnCloseRequest(closeEvent -> {
            Platform.exit();
        });
        closeCalcButton.setOnAction(closeButtonEvent -> {
            window.close();
        });
    }

    //method for setting up number buttons
    public void setButton(Button button, int width, int height, double x, double y) {
        graphGroup.getChildren().add(button);
        button.setPrefSize(width, height);
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    //method to enable zooming for the scatter chart
    private void enableChartZooming(ScatterChart<Number, Number> chart, NumberAxis xAxis, NumberAxis yAxis) {
        chart.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.1;
            double deltaY = event.getDeltaY();

            if (deltaY < 0) {
                zoomFactor = 1.0 / zoomFactor; // Zoom out
            }

            double mouseX = event.getX();
            double mouseY = event.getY();

            double xAxisWidth = xAxis.getUpperBound() - xAxis.getLowerBound();
            double yAxisHeight = yAxis.getUpperBound() - yAxis.getLowerBound();

            double xOffset = (mouseX - xAxis.getLayoutX()) / xAxis.getWidth() * xAxisWidth;
            double yOffset = (mouseY - yAxis.getLayoutY()) / yAxis.getHeight() * yAxisHeight;

            double newLowerX = xAxis.getLowerBound() + xOffset - (xOffset / zoomFactor);
            double newUpperX = newLowerX + xAxisWidth / zoomFactor;

            double newLowerY = yAxis.getLowerBound() + yOffset - (yOffset / zoomFactor);
            double newUpperY = newLowerY + yAxisHeight / zoomFactor;

            //adjust the axis bounds to multiples of 10
            double xMin = roundToNearest(newLowerX, 10);
            double xMax = roundToNearest(newUpperX, 10);
            double yMin = roundToNearest(newLowerY, 10);
            double yMax = roundToNearest(newUpperY, 10);

            xAxis.setLowerBound(xMin);
            xAxis.setUpperBound(xMax);
            yAxis.setLowerBound(yMin);
            yAxis.setUpperBound(yMax);
        });
    }
    private double roundToNearest(double value, double nearest) {
        return Math.round(value / nearest) * nearest;
    }

    private void enableChartPanning(ScatterChart<Number, Number> chart) {
        chart.setOnMousePressed(event -> {
            lastMouseX = event.getX();
            lastMouseY = event.getY();
        });

        chart.setOnMouseDragged(event -> {
            double deltaX = event.getX() - lastMouseX;
            double deltaY = event.getY() - lastMouseY;

            NumberAxis xAxis = (NumberAxis) chart.getXAxis();
            NumberAxis yAxis = (NumberAxis) chart.getYAxis();

            xAxis.setLowerBound(xAxis.getLowerBound() - deltaX);
            xAxis.setUpperBound(xAxis.getUpperBound() - deltaX);
            yAxis.setLowerBound(yAxis.getLowerBound() - deltaY);
            yAxis.setUpperBound(yAxis.getUpperBound() - deltaY);

            lastMouseX = event.getX();
            lastMouseY = event.getY();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}