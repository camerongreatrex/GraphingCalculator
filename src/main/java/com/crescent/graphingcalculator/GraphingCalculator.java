package com.crescent.graphingcalculator;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GraphingCalculator extends Application {
    // Create the main calculator pane
    public static Pane calcPane = new Pane();
    // Create the scene for the calculator
    public static Scene calcScene = new Scene(calcPane, 1000, 800, Color.WHITE);
    // Create the graphing pane
    public static Pane graphPane = new Pane();
    // Create scene for the graph
    public static Scene graphScene = new Scene(graphPane, 1000, 800, Color.WHITE);
    // Create buttons for the main calculator
    public static Button one = new Button("1"), two = new Button("2"), three = new Button("3"), four = new Button("4"), five = new Button("5"),
            six = new Button("6"), seven = new Button("7"), eight = new Button("8"), nine = new Button("9"), zero = new Button("0"),
            decimalPoint = new Button("."), negative = new Button("(-)"), plus = new Button("+"), minus = new Button("-"), exponent = new Button("^"),
            multiply = new Button("X"), divide = new Button("÷"), modulus = new Button("%"), openBracket = new Button("("),
            closeBracket = new Button(")"), sin = new Button("SIN"), cos = new Button("COS"), tan = new Button("TAN"), clear = new Button("CLEAR"),
            enter = new Button("ENTER"), graph = new Button("GRAPHING");
    // Create text box/display for calculations
    TextField textbox = new TextField();
    // Text fields, labels, line chart, and radio buttons,
    private TextField field1, field2, field3, field4;
    private Label formulaDisplay;
    private LineChart<Number, Number> chart;
    private RadioButton linearRadioButton, absoluteRadioButton, parabolaRadioButton, squarerootRadioButton, cubicRadioButton;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        // Show the stage, make it un-resizable, name the window, and set the first scene to the calculator
        stage.show();
        stage.setResizable(false);
        stage.setTitle("Graphing Calculator");
        stage.setScene(calcScene);

        // Set textbox of the calculator
        textbox.setEditable(false);
        textbox.setFocusTraversable(false);
        textbox.setFont(Font.font("Verdana", 20));
        textbox.setMaxWidth(210);
        textbox.setLayoutX(80);
        textbox.setLayoutY(100);
        calcPane.getChildren().add(textbox);

        // Set properties for the calculator buttons
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
        setButton(plus, 45, 30, three.getLayoutX() + 50, three.getLayoutY());
        setButton(minus, 45, 30, plus.getLayoutX(), plus.getLayoutY() - 50);
        setButton(multiply, 45, 30, plus.getLayoutX(), minus.getLayoutY() - 50);
        setButton(divide, 45, 30, multiply.getLayoutX(), multiply.getLayoutY() - 50);
        setButton(exponent, 45, 30, divide.getLayoutX(), divide.getLayoutY() - 50);
        setButton(modulus, 45, 30, nine.getLayoutX(), nine.getLayoutY() - 50);
        setButton(openBracket, 45, 30, seven.getLayoutX(), seven.getLayoutY() - 50);
        setButton(closeBracket, 45, 30, eight.getLayoutX(), eight.getLayoutY() - 50);
        setButton(sin, 45, 30, openBracket.getLayoutX(), openBracket.getLayoutY() - 50);
        setButton(cos, 45, 30, closeBracket.getLayoutX(), closeBracket.getLayoutY() - 50);
        setButton(tan, 45, 30, modulus.getLayoutX(), modulus.getLayoutY() - 50);
        setButton(clear, 45, 30, exponent.getLayoutX(), exponent.getLayoutY() - 50);
        clear.setStyle("-fx-font: 9 arial;");
        setButton(enter, 45, 30, negative.getLayoutX() + 50, negative.getLayoutY());
        enter.setStyle("-fx-font: 9 arial;");
        setButton(graph, 45, 30, sin.getLayoutX(), sin.getLayoutY() - 50);
        graph.setStyle("-fx-font: 9 arial;");

        // Set function of buttons
        {
            one.setOnMousePressed(startButtonEvent -> {
                textbox.setText(textbox.getText() + 1);
                textbox.positionCaret(textbox.getText().length());
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
            clear.setOnMousePressed(startButtonEvent -> textbox.setText(""));
        }
        // Graph button to toggle on/off graphing feature
        graph.setOnAction(startButtonEvent -> {
            textbox.setText("");
            stage.setScene(graphScene);
        });

        // Create X and Y axes for the chart
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("y");

        // Create the line chart to display the graph
        chart = new LineChart<>(xAxis, yAxis);
        chart.setLayoutX(20);
        chart.setLayoutY(150);
        chart.setPrefSize(600, 600);
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

        field4 = new TextField();
        field4.setLayoutX(210);
        field4.setLayoutY(20);
        field4.setPrefWidth(180);

        // Label to display the formula of the selected function
        formulaDisplay = new Label("FORMULA: y = mx + b");
        formulaDisplay.setLayoutX(20);

        // Radio buttons to select the function type
        linearRadioButton = new RadioButton("Linear Function");
        linearRadioButton.setSelected(true);
        linearRadioButton.setLayoutX(400);
        linearRadioButton.setLayoutY(20);

        absoluteRadioButton = new RadioButton("Absolute Function");
        absoluteRadioButton.setLayoutX(550);
        absoluteRadioButton.setLayoutY(60);

        parabolaRadioButton = new RadioButton("Parabola");
        parabolaRadioButton.setLayoutX(400);
        parabolaRadioButton.setLayoutY(60);

        squarerootRadioButton = new RadioButton("Square Root");
        squarerootRadioButton.setLayoutX(400);
        squarerootRadioButton.setLayoutY(100);

        cubicRadioButton = new RadioButton("Cubic");
        cubicRadioButton.setLayoutX(550);
        cubicRadioButton.setLayoutY(20);

        ToggleGroup functionToggleGroup = new ToggleGroup();
        linearRadioButton.setToggleGroup(functionToggleGroup);
        absoluteRadioButton.setToggleGroup(functionToggleGroup);
        parabolaRadioButton.setToggleGroup(functionToggleGroup);
        squarerootRadioButton.setToggleGroup(functionToggleGroup);
        cubicRadioButton.setToggleGroup(functionToggleGroup);

        // Buttons for plotting and resetting the graph
        Button plotGraphButton = new Button("Plot Graph");
        plotGraphButton.setLayoutX(700);
        plotGraphButton.setLayoutY(180);
        plotGraphButton.setOnAction(e -> plotGraph());

        Button resetButton = new Button("Reset Graph");
        resetButton.setLayoutX(700);
        resetButton.setLayoutY(220);
        resetButton.setOnAction(e -> resetGraph());

        Button backToCalc = new Button("Back");
        backToCalc.setLayoutX(700);
        backToCalc.setLayoutY(260);
        backToCalc.setOnAction(startButtonEvent -> {
            resetGraph();
            stage.setScene(calcScene);
        });

        // Adding all elements to the main pane
        graphPane.getChildren().addAll(chart, field1, field2, field3, field4, linearRadioButton, absoluteRadioButton,
                parabolaRadioButton, squarerootRadioButton, cubicRadioButton, formulaDisplay, plotGraphButton, resetButton, backToCalc);

        // Set the default to linear function and organize fields accordingly
        linearRadioButton.setSelected(true);
        field3.setVisible(false);
        resetGraph();

        // Event listeners to update the UI based on the selected function
        linearRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = mx + b");
            organizeFields("Enter slope (m)", "Enter y-intercept (b)", null, null);
            resetGraph();
        });
        absoluteRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = |a * x|");
            organizeFields("Enter coefficient (a)", null, null, null);
            resetGraph();
        });
        parabolaRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = ax^2 + bx + c");
            organizeFields("Enter coefficient (a)", "Enter constant (b)", "Enter constant (c)", null);
            resetGraph();
        });
        squarerootRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = a√b(x - h) + k");
            organizeFields("Enter coefficient (a)", "Enter constant (b)", null, null);
            resetGraph();
        });

        cubicRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = ax^3 + bx^2 + cx + d");
            organizeFields("Enter coefficient (a)", "Enter coefficient (b)", "Enter coefficient (c)", "Enter coefficient (d)");
            resetGraph();
        });

    }

    // Method for setting up number buttons
    public void setButton(Button button, int width, int height, double x, double y) {
        calcPane.getChildren().add(button);
        button.setPrefSize(width, height);
        button.setLayoutX(x);
        button.setLayoutY(y);
    }
    // Method for deciding what goes into the text fields along with which ones are displayed
    private void organizeFields(String prompt1, String prompt2, String prompt3, String prompt4) {
        field1.setPromptText(prompt1);
        field2.setPromptText(prompt2);
        field3.setPromptText(prompt3);
        field4.setPromptText(prompt4);
        if (prompt2 != null) {
            field2.setVisible(true);
            field2.setPromptText(prompt2);
        } else {
            field2.setVisible(false);
            field2.clear();
        }
        if (prompt3 != null) {
            field3.setVisible(true);
            field3.setPromptText(prompt3);
        } else {
            field3.setVisible(false);
            field3.clear();
        }
        if (prompt4 != null) {
            field4.setVisible(true);
        } else {
            field4.setVisible(false);
            field4.clear();
        }

    }

    // Method to plot the selected graph (linear, absolute, parabolic, square root, cubic)
    private void plotGraph() {
        if (linearRadioButton.isSelected()) {
            plotLine();
        } else if (absoluteRadioButton.isSelected()) {
            plotAbsoluteFunction();
        } else if (parabolaRadioButton.isSelected()) {
            plotParabola();
        } else if (squarerootRadioButton.isSelected()) {
            plotSquareRoot();
        } else if (cubicRadioButton.isSelected()) {
            plotCubicFunction();
        }
    }

    // Method to plot a linear function
    private void plotLine() {
        try {
            double slope = Double.parseDouble(field1.getText());
            double intercept = Double.parseDouble(field2.getText());

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -10; x <= 10; x += 0.5) {
                double y = slope * x + intercept;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            Label errorLabel = new Label("Please enter valid numbers.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135);
            graphPane.getChildren().removeIf(node -> node instanceof Label && !node.equals(formulaDisplay));
            graphPane.getChildren().add(errorLabel);
            // Hide the error label after 3 seconds
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(1.5));
            visiblePause.setOnFinished(event -> graphPane.getChildren().remove(errorLabel));
            visiblePause.play();
        }
    }

    // Method to plot an absolute function
    private void plotAbsoluteFunction() {
        try {
            double a = Double.parseDouble(field1.getText());

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -10; x <= 10; x += 0.5) {
                double y = Math.abs(a * x);
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            Label errorLabel = new Label("Please enter a valid number for coefficient (a).");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135);
            graphPane.getChildren().removeIf(node -> node instanceof Label && !node.equals(formulaDisplay));
            graphPane.getChildren().add(errorLabel);
            // Hide the error label after 3 seconds
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(1.5));
            visiblePause.setOnFinished(event -> graphPane.getChildren().remove(errorLabel));
            visiblePause.play();
        }
    }

    // Method to plot a parabolic function
    private void plotParabola() {
        try {
            double a = Double.parseDouble(field1.getText());
            double b = Double.parseDouble(field2.getText());
            double c = Double.parseDouble(field3.getText());

            // Calculate the x-coordinate of the vertex to fix the bug of printing more on one quadrant of another
            double vertexX = -b / (2 * a);

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = vertexX - 20; x <= vertexX + 20; x += 0.1) {
                double y = a * x * x + b * x + c;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            Label errorLabel = new Label("Please enter valid numbers");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135);
            graphPane.getChildren().removeIf(node -> node instanceof Label && !node.equals(formulaDisplay));
            graphPane.getChildren().add(errorLabel);
            // Hide the error label after 3 seconds
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(1.5));
            visiblePause.setOnFinished(event -> graphPane.getChildren().remove(errorLabel));
            visiblePause.play();
        }
    }

    // Method to plot a square root function
    private void plotSquareRoot() {
        try {
            double a = Double.parseDouble(field1.getText());
            double b = Double.parseDouble(field2.getText());

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = 0; x <= 10; x += 0.5) {
                double y = a * Math.sqrt(x) + b;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            Label errorLabel = new Label("Please enter valid numbers.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135);
            graphPane.getChildren().removeIf(node -> node instanceof Label && !node.equals(formulaDisplay));
            graphPane.getChildren().add(errorLabel);
            // Hide the error label after 3 seconds
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(1.5));
            visiblePause.setOnFinished(event -> graphPane.getChildren().remove(errorLabel));
            visiblePause.play();
        }
    }

    // Method to plot a cubic function
    private void plotCubicFunction() {
        try {
            double a = Double.parseDouble(field1.getText());
            double b = Double.parseDouble(field2.getText());
            double c = Double.parseDouble(field3.getText());
            double d = Double.parseDouble(field4.getText());

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -10; x <= 10; x += 0.5) {
                double y = a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            Label errorLabel = new Label("Please enter valid numbers.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(175);
            graphPane.getChildren().removeIf(node -> node instanceof Label && !node.equals(formulaDisplay));
            graphPane.getChildren().add(errorLabel);
            // Hide the error label after 3 seconds
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(1.5));
            visiblePause.setOnFinished(event -> graphPane.getChildren().remove(errorLabel));
            visiblePause.play();
        }
    }

    // Method to reset the graph and input fields
    private void resetGraph() {
        chart.getData().clear();
        field1.clear();
        field2.clear();
        field3.clear();
        field4.clear();

        if (linearRadioButton.isSelected()) {
            organizeFields("Enter slope (m)", "Enter y-intercept (b)", null, null);
        } else if (absoluteRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", null, null, null);
        } else if (parabolaRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", "Enter constant (b)", "Enter constant (c)", null);
        } else if (squarerootRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", "Enter constant (b)", null, null);
        } else if (cubicRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", "Enter coefficient (b)", "Enter coefficient (c)", "Enter coefficient (d)");
        }
    }
}