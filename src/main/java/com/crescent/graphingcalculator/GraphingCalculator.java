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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;



public class GraphingCalculator extends Application {
    // Create the main calculator pane and graph pane
    private Pane calcPane, graphPane;
    // Create the scene for the calculator and graph
    private Scene calcScene, graphScene;
    // Create text fields, labels, line chart, and radio buttons,
    private TextField field1, field2, field3, field4, valueField;
    // Create the formula text display
    private Label formulaDisplay;
    // Create the line chart
    private LineChart<Number, Number> chart;
    // Create the selection (radio) buttons
    private RadioButton linearRadioButton, absoluteRadioButton, parabolaRadioButton, reciprocalRadioButton,
            squarerootRadioButton, cubicRadioButton, sinRadioButton, cosRadioButton, tanRadioButton;

    // create graph check variable
    public boolean graphCheck = false;
    // create graph "trace" button
    Text trace = new Text();

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) {
        // Assign a pane to calcPane and assign calcPane to calcScene
        calcPane = new Pane();
        calcScene = new Scene(calcPane, 1000, 800, Color.WHITE);
        // Assign a pane to graphPane and assign graphPane to graphScene
        graphPane = new Pane();
        graphScene = new Scene(graphPane, 1000, 800, Color.WHITE);
        // Declare and initialize textbox
        TextField textbox = new TextField();
        // Declare and initialize button instances
        Button one = new Button("1"), two = new Button("2"), three = new Button("3"), four = new Button("4"), five = new Button("5"), six = new Button("6"), seven = new Button("7"), eight = new Button("8"), nine = new Button("9"), zero = new Button("0"), decimalPoint = new Button("."), negative = new Button("(-)"), plus = new Button("+"), minus = new Button("-"), exponent = new Button("^"), multiply = new Button("*"), divide = new Button("/"), modulus = new Button("%"), openBracket = new Button("("), closeBracket = new Button(")"), sin = new Button("SIN"), cos = new Button("COS"), tan = new Button("TAN"), clear = new Button("CLEAR"), enter = new Button("ENTER"), graph = new Button("GRAPHING");
        // Show the stage, make it un-resizable, name the window, and set the first scene to the calculator
        stage.show();
        stage.setResizable(false);
        stage.setTitle("Graphing Calculator");
        stage.setScene(calcScene);
        // Assign textbox of the calculator
        textbox.setEditable(false);
        textbox.setFocusTraversable(false);
        textbox.setFont(Font.font("Verdana", 20));
        textbox.setMaxWidth(210);
        textbox.setLayoutX(80);
        textbox.setLayoutY(100);
        calcPane.getChildren().add(textbox);
        // Assign properties for the calculator buttons
        setButton(one, 45, 90, 500);
        setButton(two, 45, one.getLayoutX() + 50, one.getLayoutY());
        setButton(three, 45, one.getLayoutX() + 100, one.getLayoutY());
        setButton(four, 45, one.getLayoutX(), one.getLayoutY() - 50);
        setButton(five, 45, two.getLayoutX(), two.getLayoutY() - 50);
        setButton(six, 45, three.getLayoutX(), three.getLayoutY() - 50);
        setButton(seven, 45, one.getLayoutX(), four.getLayoutY() - 50);
        setButton(eight, 45, two.getLayoutX(), five.getLayoutY() - 50);
        setButton(nine, 45, three.getLayoutX(), six.getLayoutY() - 50);
        setButton(zero, 45, one.getLayoutX(), one.getLayoutY() + 50);
        setButton(decimalPoint, 45, two.getLayoutX(), zero.getLayoutY());
        setButton(negative, 45, three.getLayoutX(), zero.getLayoutY());
        setButton(plus, 45, three.getLayoutX() + 50, three.getLayoutY());
        setButton(minus, 45, plus.getLayoutX(), plus.getLayoutY() - 50);
        setButton(multiply, 45, plus.getLayoutX(), minus.getLayoutY() - 50);
        setButton(divide, 45, multiply.getLayoutX(), multiply.getLayoutY() - 50);
        setButton(exponent, 45, divide.getLayoutX(), divide.getLayoutY() - 50);
        setButton(modulus, 45, nine.getLayoutX(), nine.getLayoutY() - 50);
        setButton(openBracket, 45, seven.getLayoutX(), seven.getLayoutY() - 50);
        setButton(closeBracket, 45, eight.getLayoutX(), eight.getLayoutY() - 50);
        setButton(sin, 45, openBracket.getLayoutX(), openBracket.getLayoutY() - 50);
        setButton(cos, 45, closeBracket.getLayoutX(), closeBracket.getLayoutY() - 50);
        setButton(tan, 45, modulus.getLayoutX(), modulus.getLayoutY() - 50);
        setButton(clear, 45, exponent.getLayoutX(), exponent.getLayoutY() - 50);
        clear.setStyle("-fx-font: 9 arial;");
        setButton(enter, 45, negative.getLayoutX() + 50, negative.getLayoutY());
        enter.setStyle("-fx-font: 9 arial;");
        setButton(graph, 97, sin.getLayoutX() + 48, sin.getLayoutY() - 50);
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
                textbox.setText(textbox.getText() + "*");
                textbox.positionCaret(textbox.getText().length());
            });
            divide.setOnMousePressed(startButtonEvent -> {
                textbox.setText(textbox.getText() + "/");
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
        // Declare and initialize X and Y axes for the chart
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("y");
        // Declare and initialize the line chart to display the graph
        chart = new LineChart<>(xAxis, yAxis);
        chart.setLayoutX(20);
        chart.setLayoutY(150);
        chart.setPrefSize(600, 600);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        // Declare and initialize text fields for user input
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
        valueField = new TextField();
        valueField.setLayoutX(800);
        valueField.setLayoutY(340);
        valueField.setPrefWidth(100);
        valueField.setPromptText("X = ");
        // Declare and initialize label to display the formula of the selected function
        formulaDisplay = new Label("FORMULA: y = mx + b");
        formulaDisplay.setLayoutX(20);
        // Declare and initialize radio buttons to select the function type
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
        reciprocalRadioButton = new RadioButton("Reciprocal");
        reciprocalRadioButton.setLayoutX(550);
        reciprocalRadioButton.setLayoutY(100);
        squarerootRadioButton = new RadioButton("Square Root");
        squarerootRadioButton.setLayoutX(400);
        squarerootRadioButton.setLayoutY(100);
        cubicRadioButton = new RadioButton("Cubic");
        cubicRadioButton.setLayoutX(550);
        cubicRadioButton.setLayoutY(20);
        sinRadioButton = new RadioButton("Sin");
        sinRadioButton.setLayoutX(700);
        sinRadioButton.setLayoutY(20);
        cosRadioButton = new RadioButton("Cos");
        cosRadioButton.setLayoutX(700);
        cosRadioButton.setLayoutY(60);
        tanRadioButton = new RadioButton("Tan");
        tanRadioButton.setLayoutX(700);
        tanRadioButton.setLayoutY(100);
        // Declare and initialize toggle groups for all radio buttons
        ToggleGroup functionToggleGroup = new ToggleGroup();
        linearRadioButton.setToggleGroup(functionToggleGroup);
        absoluteRadioButton.setToggleGroup(functionToggleGroup);
        parabolaRadioButton.setToggleGroup(functionToggleGroup);
        reciprocalRadioButton.setToggleGroup(functionToggleGroup);
        squarerootRadioButton.setToggleGroup(functionToggleGroup);
        cubicRadioButton.setToggleGroup(functionToggleGroup);
        sinRadioButton.setToggleGroup(functionToggleGroup);
        cosRadioButton.setToggleGroup(functionToggleGroup);
        tanRadioButton.setToggleGroup(functionToggleGroup);
        // Declare and initialize buttons for plotting, resetting, and exiting the graph
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

        //graph "trace" buttons + display text
        trace.setLayoutX(800);
        trace.setLayoutY(300);
        //create "zero" button + function(is y = 0, x = ?)
        Button zeroGraph = new Button("ZERO");
        zeroGraph.setLayoutX(700);
        zeroGraph.setLayoutY(300);
        zeroGraph.setOnAction(startButtonEvent -> {
            resetTrace();
            //round the numbers
            //*use round() to round to nearest 100th
            if (linearRadioButton.isSelected() && graphCheck) {
                trace.setText("ZERO = " + round(-Double.parseDouble(field2.getText()) / Double.parseDouble(field1.getText())));
            } else if (parabolaRadioButton.isSelected() && graphCheck) {
                solveParabola(Double.parseDouble(field1.getText()), Double.parseDouble(field2.getText()), Double.parseDouble(field3.getText()));
            } else if (squarerootRadioButton.isSelected() && graphCheck) {
                trace.setText("ZERO = " + round((-Math.sqrt(Double.parseDouble(field4.getText()) / Double.parseDouble(field1.getText()))
                + Double.parseDouble(field3.getText())) / Double.parseDouble(field2.getText())));
            } else if (cubicRadioButton.isSelected() && graphCheck) {
                solveCubic();
            } else if (absoluteRadioButton.isSelected() && graphCheck) {
                trace.setText("ZERO = 0.0");
            } else if (reciprocalRadioButton.isSelected() && graphCheck) {
                // trace.setText("ZERO = ∞");
                trace.setText("THERE IS NO ZERO");
            }
        });
        
        //create "value" button + function(if x = __, y = ?)
        Button ValueGraph = new Button("X=");
        ValueGraph.setLayoutX(700);
        ValueGraph.setLayoutY(340);
        ValueGraph.setOnAction(startButtonEvent -> {
            if (linearRadioButton.isSelected() && graphCheck) {
                //mx + b
                trace.setText("Y = " + Double.parseDouble(field1.getText()) * Double.parseDouble(valueField.getText()) + Double.parseDouble(field2.getText()));
            } else if (parabolaRadioButton.isSelected() && graphCheck) {
                //ax^2 + bx + c
                trace.setText("Y = " + Double.parseDouble(field1.getText()) * Double.parseDouble(valueField.getText()) * Double.parseDouble(valueField.getText())
                + Double.parseDouble(field2.getText()) * Double.parseDouble(valueField.getText()) + Double.parseDouble(field3.getText()));
            } else if (squarerootRadioButton.isSelected() && graphCheck) {
                //a√(b(x - h)) + k
                trace.setText("Y = " + Double.parseDouble(field1.getText())
                * Math.sqrt(Double.parseDouble(field2.getText())
                    * (Double.parseDouble(valueField.getText()) - Double.parseDouble(field3.getText())))
                + Double.parseDouble(field4.getText()));
            } else if (cubicRadioButton.isSelected() && graphCheck) {
                //ax^3 + bx^2 + cx + d
                trace.setText("Y = " + Double.parseDouble(field1.getText()) * Math.pow(Double.parseDouble(valueField.getText()), 3)
                 + Double.parseDouble(field2.getText()) * Double.parseDouble(valueField.getText()) * Double.parseDouble(valueField.getText())
                 + Double.parseDouble(field3.getText()) * Double.parseDouble(valueField.getText()) + Double.parseDouble(field4.getText()));
            } else if (absoluteRadioButton.isSelected() && graphCheck) {
                //|a * x|
                trace.setText("Y = " + Math.abs(Double.parseDouble(field1.getText()) * Double.parseDouble(valueField.getText())));
            } else if (reciprocalRadioButton.isSelected() && graphCheck) {
                //y = a / x
                trace.setText("Y = " + Double.parseDouble(field1.getText()) / Double.parseDouble(valueField.getText()));
            } else if (sinRadioButton.isSelected() && graphCheck) {
                trace.setText("Y = " + Math.sin(Double.parseDouble(valueField.getText())));
            } else if (cosRadioButton.isSelected() && graphCheck) {
                trace.setText("Y = " + Math.cos(Double.parseDouble(valueField.getText())));
            } else if (tanRadioButton.isSelected() && graphCheck) {
                trace.setText("Y = " + Math.tan(Double.parseDouble(valueField.getText())));
            }
        });



        // Add all elements to the main pane
        graphPane.getChildren().addAll(chart, field1, field2, field3, field4, linearRadioButton, absoluteRadioButton,
                parabolaRadioButton, reciprocalRadioButton, squarerootRadioButton, cubicRadioButton, sinRadioButton,
                cosRadioButton, tanRadioButton, formulaDisplay, plotGraphButton, resetButton, backToCalc, zeroGraph, trace, valueField, ValueGraph);
        // Set the default to linear function and reset the graph to ensure everything is correct
        linearRadioButton.setSelected(true);
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
        reciprocalRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = a / x");
            organizeFields("Enter coefficient (a)", null, null, null);
            resetGraph();
        });
        squarerootRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = a√(b(x - h)) + k");
            organizeFields("Enter coefficient (a)", "Enter constant (b)", "Enter horizontal translation (h)", "Enter vertical translation (k)");
            resetGraph();
        });

        cubicRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = ax^3 + bx^2 + cx + d");
            organizeFields("Enter coefficient (a)", "Enter coefficient (b)", "Enter coefficient (c)", "Enter coefficient (d)");
            resetGraph();
        });
        sinRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = sin(x)");
            organizeFields("Enter coefficient (a)", null, null, null);
            resetGraph();
        });
        cosRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = cos(x)");
            organizeFields("Enter coefficient (a)", null, null, null);
            resetGraph();
        });
        tanRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = tan(x)");
            organizeFields("Enter coefficient (a)", null, null, null);
            resetGraph();
        });

    }

    // Method for setting up number buttons
    private void setButton(Button button, int width, double x, double y) {
        calcPane.getChildren().add(button);
        button.setPrefSize(width, 30);
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
        resetTrace();
    }

    // Method to plot the selected graph (linear, absolute, parabolic, square root, cubic)
    private void plotGraph() {
        resetTrace();
        if (linearRadioButton.isSelected()) {
            valueField.setVisible(true);
            plotLine();
        } else if (absoluteRadioButton.isSelected()) {
            plotAbsoluteFunction();
        } else if (parabolaRadioButton.isSelected()) {
            plotParabola();
        } else if (squarerootRadioButton.isSelected()) {
            plotSquareRoot();
        } else if (cubicRadioButton.isSelected()) {
            plotCubicFunction();
        } else if (reciprocalRadioButton.isSelected()) {
            plotReciprocalFunction();
        } else if (sinRadioButton.isSelected()) {
            plotSinFunction();
        } else if (cosRadioButton.isSelected()) {
            plotCosFunction();
        } else if (tanRadioButton.isSelected()) {
            plotTanFunction();
        }
        graphCheck = true;
    }

    // Method to plot a linear function
    private void plotLine() {
        try {
            double slope = Double.parseDouble(field1.getText());
            double intercept = Double.parseDouble(field2.getText());
            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -20; x <= 20; x += 0.5) {
                double y = slope * x + intercept;
                series.getData().add(new XYChart.Data<>(x, y));
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
        }
    }

    // Method to plot an absolute function
    private void plotAbsoluteFunction() {
        try {
            double a = Double.parseDouble(field1.getText());
            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -20; x <= 20; x += 0.5) {
                double y = Math.abs(a * x);
                series.getData().add(new XYChart.Data<>(x, y));
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
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

            for (double x = vertexX - 20; x <= vertexX + 20; x += 0.5) {
                double y = a * x * x + b * x + c;
                series.getData().add(new XYChart.Data<>(x, y));
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
        }
    }

    private void solveParabola(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c, x1, x2;
        if (discriminant >= 0) {
            x1 = (-b - Math.sqrt(discriminant)) / (2 * a);
            x2 = (-b + Math.sqrt(discriminant)) / (2 * a);
            trace.setText("ZERO = " + round(x1) + " and " + round(x2));
        } else {
            trace.setText("THERE IS NO ZERO");
        }
    }

    // Method to plot a square root function
    private void plotSquareRoot() {
        try {
            double a = Double.parseDouble(field1.getText());
            double b = Double.parseDouble(field2.getText());
            double h = Double.parseDouble(field3.getText());
            double k = Double.parseDouble(field4.getText());
            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = 0; x <= 20; x += 0.5) {
                double y = a * Math.sqrt(b * (x + h)) + k;
                series.getData().add(new XYChart.Data<>(x, y));
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
        }
    }

    // Method to plot reciprocal function
    private void plotReciprocalFunction() {
        try {
            double a = Double.parseDouble(field1.getText());
            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -20; x <= 20; x += 0.5) {
                if (x != 0) {
                    double y = a / x;
                    series.getData().add(new XYChart.Data<>(x, y));
                }
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
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

            for (double x = -20; x <= 20; x += 0.5) {
                double y = a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
                series.getData().add(new XYChart.Data<>(x, y));
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
        }
    }

    private void solveCubic() {
        double discriminant = Double.parseDouble(field4.getText()) * Double.parseDouble(field4.getText())
                            - 4 * Math.pow(Double.parseDouble(field3.getText()), 3);
        double root1 = Math.cbrt((-Double.parseDouble(field4.getText()) + discriminant) / 2);
        Double root2 = Math.cbrt((-Double.parseDouble(field4.getText()) - discriminant) / 2);
        Double root3 = root1 + root2;
        trace.setText("ZERO = " + round(root1) + ", " + round(root2) + ", " + round(root3));
    }

    // Method to plot sin
    private void plotSinFunction() {
        try {
            double a = Double.parseDouble(field1.getText());
            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -20; x <= 20; x += 0.5) {
                double y = a * Math.sin(x);
                series.getData().add(new XYChart.Data<>(x, y));
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
        }
    }

    // Method to plot cos
    private void plotCosFunction() {
        try {
            double a = Double.parseDouble(field1.getText());
            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -20; x <= 20; x += 0.5) {
                double y = a * Math.cos(x);
                series.getData().add(new XYChart.Data<>(x, y));
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
        }
    }

    // Method to plot tan (bugged)
    private void plotTanFunction() {
        try {
            double a = Double.parseDouble(field1.getText());
            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -20; x <= 20; x += 0.5) {
                double y = a * Math.tan(x);
                series.getData().add(new XYChart.Data<>(x, y));
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
        }
    }

    // Method to handle input user input
    private void handleInvalidInputs() {
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

    // Method to reset the graph and input fields
    private void resetGraph() {
        chart.getData().clear();
        field1.clear();
        field2.clear();
        field3.clear();
        field4.clear();
        graphCheck = false;
        resetTrace();

        if (linearRadioButton.isSelected()) {
            organizeFields("Enter slope (m)", "Enter y-intercept (b)", null, null);
        } else if (absoluteRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", null, null, null);
        } else if (parabolaRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", "Enter constant (b)", "Enter constant (c)", null);
        } else if (squarerootRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", "Enter constant (b)", "Enter constant (h)", "Enter constant (k)");
        } else if (cubicRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", "Enter coefficient (b)", "Enter coefficient (c)", "Enter coefficient (d)");
        } else if (sinRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", null, null, null);
        } else if (cosRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", null, null, null);
        } else if (tanRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", null, null, null);
        }
    }

    private void resetTrace() {
        valueField.clear();
        trace.setText("");
    }

    private double round(double a) {
        return (double)Math.round(a * 100) / 100;
    }
}