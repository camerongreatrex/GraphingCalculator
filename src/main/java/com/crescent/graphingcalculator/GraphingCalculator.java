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
    TextField graph1 = new TextField(), graph2 = new TextField();

    private double lastMouseX, lastMouseY;

    //control variable for graphing(y=) textboxs
    public static boolean graphchecks = false;

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

        //graphing textbox 1
        graphGroup.getChildren().add(graph1);
        graph1.setVisible(false);
        graph1.setEditable(true);
        graph1.setFocusTraversable(false);
        graph1.setFont(Font.font("Verdana", 20));
        graph1.setMaxWidth(210);
        graph1.setLayoutX(80);
        graph1.setLayoutY(90);

        //graphing textbox 2
        graphGroup.getChildren().add(graph2);
        graph2.setVisible(false);
        graph2.setEditable(true);
        graph2.setFocusTraversable(false);
        graph2.setFont(Font.font("Verdana", 20));
        graph2.setMaxWidth(210);
        graph2.setLayoutX(80);
        graph2.setLayoutY(130);

        //set function of buttons
    {
        //make all buttons work for different textboxes
        one.setOnMousePressed(startButtonEvent -> {
            if (!graphchecks) {
            textbox.setText(textbox.getText() + 1);
            textbox.positionCaret(textbox.getText().length());
            } else if(graphchecks) {
                graph1.setText(graph1.getText() + 1);
                graph1.positionCaret(graph1.getText().length());
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
            if (graphchecks) {
                graph1.setVisible(false);
                graph2.setVisible(false);
                textbox.setVisible(true);
            } else {
                graph1.setVisible(true);
                graph2.setVisible(true);
                textbox.setVisible(false);
            }
            graphchecks = !graphchecks;
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
