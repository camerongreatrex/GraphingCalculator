package com.crescent.graphingcalculator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
    //create buttons for numbers 0-9 and decimal point
    public static Button one = new Button("1"), two = new Button("2"), three = new Button("3"), four = new Button("4"), five = new Button("5"),
            six = new Button("6"), seven = new Button("7"), eight = new Button("8"), nine = new Button("9"), zero = new Button("0"),
            decimalPoint = new Button("."), negative = new Button("(-)");
    //create basic math buttons
    public static Button plus = new Button("+"), minus = new Button("-"), exponent = new Button("^"),
            multiply = new Button("X"), divide = new Button("÷"), modulus = new Button("%"), openBracket = new Button("("),
            closeBracket = new Button(")");
    //create trig buttons
    public static Button sin = new Button("SIN"), cos = new Button("COS"), tan = new Button("TAN");
    //create other calculator buttons
    public static Button clear = new Button("CLEAR"), enter = new Button("ENTER"), graph = new Button("Y=");

    //create text box/screen
    TextField textbox = new TextField();

    private double lastMouseX, lastMouseY;

    //control variable for graphing(y=) textboxs
    public static boolean graphcheck = false;

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

        //set properties for numerical buttons, decimal point and negative sign
        setButton(one, 45, 30, 90, 500);
        setButton(two, 45, 30, one.getLayoutX() + 50, one.getLayoutY());
        setButton(three, 45, 30, one.getLayoutX() + 100, one.getLayoutY());
        setButton(four, 45, 30, one.getLayoutX(), one.getLayoutY() - 50);
        setButton(five, 45, 30, two.getLayoutX(), two.getLayoutY() - 50);
        setButton(six, 45, 30, three.getLayoutX(), three.getLayoutY() - 50);
        setButton(seven, 45, 30, one.getLayoutX(), four.getLayoutY() - 50);
        setButton(eight, 45, 30, two.getLayoutX(), five.getLayoutY() - 50);
        setButton(nine, 45, 30, three.getLayoutX(), six.getLayoutY() - 50);
        setButton(zero, 45, 30, one.getLayoutX(), one.getLayoutY() + 50);
        setButton(decimalPoint, 45, 30, two.getLayoutX(), zero.getLayoutY());
        setButton(negative, 45, 30, three.getLayoutX(), zero.getLayoutY());

        //set properties for basic math buttons
        setButton(plus, 45, 30, three.getLayoutX() + 50, three.getLayoutY());
        setButton(minus, 45, 30, plus.getLayoutX(), plus.getLayoutY() - 50);
        setButton(multiply, 45, 30, plus.getLayoutX(), minus.getLayoutY() - 50);
        setButton(divide, 45, 30, multiply.getLayoutX(), multiply.getLayoutY() - 50);
        setButton(exponent, 45, 30, divide.getLayoutX(), divide.getLayoutY() - 50);
        setButton(modulus, 45, 30, nine.getLayoutX(), nine.getLayoutY() - 50);
        setButton(openBracket, 45, 30, seven.getLayoutX(), seven.getLayoutY() - 50);
        setButton(closeBracket, 45, 30, eight.getLayoutX(), eight.getLayoutY() - 50);

        //set properties for trig buttons
        setButton(sin, 45, 30, openBracket.getLayoutX(), openBracket.getLayoutY() - 50);
        setButton(cos, 45, 30, closeBracket.getLayoutX(), closeBracket.getLayoutY() - 50);
        setButton(tan, 45, 30, modulus.getLayoutX(), modulus.getLayoutY() - 50);

        //set properties for other calculator buttons
        setButton(clear, 45, 30, exponent.getLayoutX(), exponent.getLayoutY() - 50);
        clear.setStyle("-fx-font: 9 arial;");
        setButton(enter, 45, 30, negative.getLayoutX() + 50, negative.getLayoutY());
        enter.setStyle("-fx-font: 9 arial;");
        setButton(graph, 45, 30, sin.getLayoutX(), sin.getLayoutY() - 50);
        graph.setStyle("-fx-font: 9 arial;");

        //set textboxes of calculator
        //main textbox
        graphGroup.getChildren().add(textbox);
        textbox.setEditable(false);
        textbox.setFocusTraversable(false);
        textbox.setFont(Font.font("Verdana", 20));
        textbox.setMaxWidth(210);
        textbox.setLayoutX(80);
        textbox.setLayoutY(100);



        //set function of buttons
    {
        //make all buttons work for different textboxes
        one.setOnMousePressed(startButtonEvent -> {
            if (!graphcheck) {
            textbox.setText(textbox.getText() + 1);
            textbox.positionCaret(textbox.getText().length());
            }
        });
        two.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + 2);
            textbox.positionCaret(textbox.getText().length());
        });
        three.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + 3);
            textbox.positionCaret(textbox.getText().length());
        });
        four.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + 4);
            textbox.positionCaret(textbox.getText().length());
        });
        five.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + 5);
            textbox.positionCaret(textbox.getText().length());
        });
        six.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + 6);
            textbox.positionCaret(textbox.getText().length());
        });
        seven.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + 7);
            textbox.positionCaret(textbox.getText().length());
        });
        eight.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + 8);
            textbox.positionCaret(textbox.getText().length());
        });
        nine.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + 9);
            textbox.positionCaret(textbox.getText().length());
        });
        zero.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + 0);
            textbox.positionCaret(textbox.getText().length());
        });
    }
    {
        plus.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + "+");
            textbox.positionCaret(textbox.getText().length());
        });
        minus.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + "-");
            textbox.positionCaret(textbox.getText().length());
        });
        multiply.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + "x");
            textbox.positionCaret(textbox.getText().length());
        });
        divide.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + "÷");
            textbox.positionCaret(textbox.getText().length());
        });
        modulus.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + "%");
            textbox.positionCaret(textbox.getText().length());
        });
        openBracket.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + "(");
            textbox.positionCaret(textbox.getText().length());
        });
        closeBracket.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + ")");
            textbox.positionCaret(textbox.getText().length());
        });
        sin.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + "SIN(");
            textbox.positionCaret(textbox.getText().length());
        });
        cos.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + "COS(");
            textbox.positionCaret(textbox.getText().length());
        });
        tan.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + "TAN(");
            textbox.positionCaret(textbox.getText().length());
        });
        exponent.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + "^");
            textbox.positionCaret(textbox.getText().length());
        });
        decimalPoint.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + ".");
            textbox.positionCaret(textbox.getText().length());
        });
        negative.setOnMousePressed(startButtonEvent -> {
            textbox.setText(textbox.getText() + "(-)");
            textbox.positionCaret(textbox.getText().length());
        });
        enter.setOnMousePressed(startButtonEvent -> {



            //WIP WIP WIP WIP


        });
        clear.setOnMousePressed(startButtonEvent -> {
            textbox.setText("");
        });
    }
    {
        
        graph.setOnAction(startButtonEvent -> {
            if (graphcheck) {
                textbox.setVisible(true);
            } else {
                textbox.setVisible(false);
            }
            graphcheck = !graphcheck;
        });
    }  
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

    // void checkTextbox() {
    //     if (graphchecks)
    // }

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