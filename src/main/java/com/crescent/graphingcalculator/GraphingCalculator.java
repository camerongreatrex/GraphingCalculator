package com.crescent.graphingcalculator;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.util.FastMath;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.math.BigInteger;
import java.util.*;

public class GraphingCalculator extends Application {
    // Create graph "trace" button
    // Can be final because trace points to the same text object, but the state
    // (text held) can be changed
    private final Text trace = new Text();
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
    // Create graph check variable
    private boolean graphCheck = false;
    // Create music variables
    private Button playpauseButton, restartButton, nextButton, previousButton, loopButton, shuffleButton;
    private Label songLabel, currentSongTime, songTotal, volumeIcon;
    private Slider volumeSlider;
    private ProgressBar songProgressBar;
    private Media media;
    private MediaPlayer mediaPlayer;
    private int songNumber;
    private ArrayList<File> songs;
    private Timer timer;
    private boolean running, loop, playpause, shuffle;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        // Assign a pane to calcPane and assign calcPane to calcScene
        calcPane = new Pane();
        calcScene = new Scene(calcPane, 600, 800, Color.WHITE);
        // Assign a pane to graphPane and assign graphPane to graphScene
        graphPane = new Pane();
        graphScene = new Scene(graphPane, 1000, 800, Color.WHITE);
        // Declare and initialize button instances
        Button one = new Button("1"), two = new Button("2"), three = new Button("3"), four = new Button("4"), five = new Button("5"),
                six = new Button("6"), seven = new Button("7"), eight = new Button("8"), nine = new Button("9"), zero = new Button("0"),
                decimalPoint = new Button("."), negative = new Button("(-)"), plus = new Button("+"), minus = new Button("-"),
                multiply = new Button("*"), divide = new Button("/"), modulus = new Button("%"), openBracket = new Button("("),
                closeBracket = new Button(")"), sin = new Button("SIN"), cos = new Button("COS"), tan = new Button("TAN"),
                clear = new Button("CLEAR"), enter = new Button("ENTER"), graph = new Button("GRAPHING"), normal = new Button("normalDist"),
                prob = new Button("prob");
        // Show the stage, make it un-resizable, name the window, and set the first scene to the calculator
        stage.show();
        stage.setResizable(false);
        stage.setTitle("Graphing Calculator");
        stage.setScene(calcScene);

        // Declare, initialize and format textbox
        TextField textbox = new TextField();
        textbox.setEditable(false);
        textbox.setFocusTraversable(false);
        textbox.setFont(Font.font("Verdana", 20));
        textbox.setMaxWidth(210);
        textbox.setLayoutX(187);
        textbox.setLayoutY(190);
        calcPane.getChildren().add(textbox);

        // Initialize the song buttons, label, slider and progress bar variables
        playpauseButton = new Button("⏯");
        playpauseButton.setStyle(
                "-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12px;");
        songLabel = new Label();
        currentSongTime = new Label();
        songTotal = new Label();
        restartButton = new Button("Restart");
        restartButton.setStyle(
                "-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12px;");
        restartButton.setOnAction(event -> restartMedia());
        nextButton = new Button("▷|");
        nextButton.setStyle(
                "-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12px;");
        nextButton.setOnAction(event -> nextSong());
        previousButton = new Button("|◁");
        previousButton.setStyle(
                "-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12px;");
        previousButton.setOnAction(event -> previousSong());
        loopButton = new Button("⟳");
        loopButton.setStyle(
                "-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12px;");
        loopButton.setOnAction(event -> loopSong());
        shuffleButton = new Button("🔀");
        shuffleButton.setStyle(
                "-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12px;");
        shuffleButton.setOnAction(event -> shuffleSong());
        volumeIcon = new Label("🔊");
        volumeIcon.setStyle(
                "-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12px;");
        volumeSlider = new Slider();
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(50);
        songProgressBar = new ProgressBar(0);
        songProgressBar.setStyle("-fx-accent: #1DB954;");

        // Add volume control functionality
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                // Set volume based on the slider value (divide by 600, so it isn't crazy loud)
                mediaPlayer.setVolume(newValue.doubleValue() / 600);
            }
        });

        // Create an arraylist of songs in the music lib directory and add all the songs
        // to the arraylist
        songs = new ArrayList<>();
        File directory = new File("src/main/java/lib/music");
        File[] files = directory.listFiles();
        if (files != null) {
            songs.addAll(Arrays.asList(files));
        }

        // Start displaying music functions on main calculator page
        musicToMain();
        startMedia();

        // Assign properties for the calculator buttons
        setButton(one, 45, 195, 500);
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
        setButton(modulus, 45, nine.getLayoutX(), nine.getLayoutY() - 50);
        setButton(openBracket, 45, seven.getLayoutX(), seven.getLayoutY() - 50);
        setButton(closeBracket, 45, eight.getLayoutX(), eight.getLayoutY() - 50);
        setButton(sin, 45, openBracket.getLayoutX(), openBracket.getLayoutY() - 50);
        setButton(cos, 45, closeBracket.getLayoutX(), closeBracket.getLayoutY() - 50);
        setButton(tan, 45, modulus.getLayoutX(), modulus.getLayoutY() - 50);
        setButton(clear, 45, divide.getLayoutX(), divide.getLayoutY() - 50);
        clear.setStyle("-fx-font: 9 arial;");
        setButton(enter, 45, negative.getLayoutX() + 50, negative.getLayoutY());
        enter.setStyle("-fx-font: 9 arial;");
        setButton(graph, 97, sin.getLayoutX() + 48, sin.getLayoutY() - 50);
        graph.setStyle("-fx-font: 9 arial;");
        setButton(normal, 45, seven.getLayoutX() - 50, seven.getLayoutY());
        setButton(prob, 45, four.getLayoutX() - 50, four.getLayoutY());

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
            decimalPoint.setOnMousePressed(startButtonEvent -> {
                textbox.setText(textbox.getText() + ".");
                textbox.positionCaret(textbox.getText().length());
            });
            negative.setOnMousePressed(startButtonEvent -> {
                textbox.setText(textbox.getText() + "(-)");
                textbox.positionCaret(textbox.getText().length());
            });
            enter.setOnAction(startButtonEvent -> performOperation(textbox));
            clear.setOnMousePressed(startButtonEvent -> textbox.setText(""));
            normal.setOnMousePressed(startButtonEvent -> {
                // Create the UI elements for the normal distribution input
                Label meanLabel = new Label("Mean:");
                Label stdDevLabel = new Label("Standard Deviation:");
                Label numberLabel = new Label("Number:");
                TextField meanField = new TextField();
                TextField stdDevField = new TextField();
                TextField numberField = new TextField();
                RadioButton leftRadioButton = new RadioButton("Left");
                RadioButton rightRadioButton = new RadioButton("Right");
                ToggleGroup toggleGroup = new ToggleGroup();
                leftRadioButton.setToggleGroup(toggleGroup);
                rightRadioButton.setToggleGroup(toggleGroup);
                Button calculateButton = new Button("Calculate");
                Label resultLabel = new Label();

                // Set the layout for the normal distribution input
                GridPane inputLayout = new GridPane();
                inputLayout.setVgap(10);
                inputLayout.setHgap(10);
                inputLayout.addRow(0, meanLabel, meanField);
                inputLayout.addRow(1, stdDevLabel, stdDevField);
                inputLayout.addRow(2, numberLabel, numberField);
                inputLayout.addRow(3, new Label("Calculate to the:"), leftRadioButton, rightRadioButton);
                inputLayout.addRow(4, calculateButton);
                inputLayout.addRow(5, resultLabel);

                // Create a new stage for the normal distribution input
                Stage inputStage = new Stage();
                inputStage.setTitle("Normal Distribution Input");
                Scene inputScene = new Scene(inputLayout, 500, 225);
                inputStage.setScene(inputScene);

                // Set the action for the calculate button
                calculateButton.setOnAction(event -> {
                    try {
                        // Retrieve values from the input fields
                        double mean = Double.parseDouble(meanField.getText());
                        double stdDev = Double.parseDouble(stdDevField.getText());
                        double number = Double.parseDouble(numberField.getText());

                        // Create a normal distribution object
                        NormalDistribution normalDistribution = new NormalDistribution(mean, stdDev);

                        // Calculate the cumulative probability
                        double cumulativeProbability;
                        if (leftRadioButton.isSelected()) {
                            cumulativeProbability = normalDistribution.cumulativeProbability(number);
                            resultLabel.setText("Area to the left of " + number + ": " + cumulativeProbability);
                        } else if (rightRadioButton.isSelected()) {
                            cumulativeProbability = 1 - normalDistribution.cumulativeProbability(number);
                            resultLabel.setText("Area to the right of " + number + ": " + cumulativeProbability);
                        } else {
                            resultLabel.setText("Please select whether to calculate to the left or right.");
                        }

                    } catch (NumberFormatException e) {
                        resultLabel.setText("Invalid input. Please enter numeric values.");
                    }
                });

                // Show the input stage
                inputStage.show();
            });

        }
        prob.setOnMousePressed(startButtonEvent -> {
            // Create the UI elements for the combination/permutation input
            Label nLabel = new Label("n:");
            Label rLabel = new Label("r:");
            TextField nField = new TextField();
            TextField rField = new TextField();
            Button calculateButton = new Button("Calculate");
            Label resultLabel = new Label();

            // ToggleGroup for the operation choice
            ToggleGroup operationToggleGroup = new ToggleGroup();

            RadioButton combinationRadioButton = new RadioButton("Combination");
            combinationRadioButton.setToggleGroup(operationToggleGroup);
            combinationRadioButton.setSelected(true); // Default selection

            RadioButton permutationRadioButton = new RadioButton("Permutation");
            permutationRadioButton.setToggleGroup(operationToggleGroup);

            // Set the layout for the combination/permutation input
            GridPane inputLayout = new GridPane();
            inputLayout.setVgap(10);
            inputLayout.setHgap(10);
            inputLayout.addRow(0, nLabel, nField);
            inputLayout.addRow(1, rLabel, rField);
            inputLayout.addRow(2, combinationRadioButton, permutationRadioButton);
            inputLayout.addRow(3, calculateButton);
            inputLayout.addRow(4, resultLabel);

            // Create a new stage for the combination/permutation input
            Stage inputStage = new Stage();
            inputStage.setTitle("Combination/Permutation Input");
            Scene inputScene = new Scene(inputLayout, 500, 225);
            inputStage.setScene(inputScene);

            // Set the action for the calculate button
            calculateButton.setOnAction(event -> {
                try {
                    // Retrieve values from the input fields
                    int n = Integer.parseInt(nField.getText());
                    int r = Integer.parseInt(rField.getText());

                    // Perform either combination or permutation calculation
                    BigInteger result;
                    if (combinationRadioButton.isSelected()) {
                        result = calculateCombination(n, r);
                    } else {
                        result = calculatePermutation(n, r);
                    }

                    resultLabel.setText("Result: " + result);
                } catch (NumberFormatException e) {
                    resultLabel.setText("Invalid input. Please enter valid integers.");
                }
            });

            // Show the input stage
            inputStage.show();
        });
        // Graph button to toggle on/off graphing feature
        graph.setOnAction(startButtonEvent -> {
            // Reset calculator textfield
            textbox.setText("");
            // Move music to graphing page
            musicToGraph();
            // Set the scene to the graphing scene
            stage.setScene(graphScene);
        });

        // Declare and initialize X and Y axes for the chart
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        // Declare, initialize and format the line chart to display the graph
        chart = new LineChart<>(xAxis, yAxis);
        chart.setLayoutX(20);
        chart.setLayoutY(150);
        chart.setPrefSize(600, 600);
        chart.setLegendVisible(false);
        chart.setAnimated(false);

        // Declare, initialize and format text fields for user input
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
        valueField.setLayoutY(300);
        valueField.setPrefWidth(100);
        valueField.setPromptText("X = ");

        // Declare and initialize label to display the formula of the selected function
        // (starting with linear)
        formulaDisplay = new Label("FORMULA: y = mx + b");
        formulaDisplay.setLayoutX(20);

        // Declare, initialize and format radio buttons to select the function type
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
        // Plot the graph and allow for point hovering over points
        plotGraphButton.setOnAction(e -> {
            plotGraph();
            addPointHovering();
        });

        // Declare, initialize and format sidebar buttons
        Button resetButton = new Button("Reset Graph");
        resetButton.setLayoutX(700);
        resetButton.setLayoutY(220);
        // Reset the graph on press
        resetButton.setOnAction(e -> resetGraph());
        Button backToCalc = new Button("Back");
        backToCalc.setLayoutX(850);
        backToCalc.setLayoutY(50);
        // Return to the calculator scene and reset the graph on press
        backToCalc.setOnAction(startButtonEvent -> {
            // Reset the graph
            resetGraph();
            // Move music controls back to main calculator page
            musicToMain();
            // Set the scene to the main calculator scene
            stage.setScene(calcScene);
        });

        // Graph trace buttons + display text
        trace.setLayoutX(800);
        trace.setLayoutY(278);
        // Create "zero" button
        Button zeroGraph = getZeroGraph();
        // Create "value" button
        Button ValueGraph = getValueGraph();
        // Add all elements to the main pane
        graphPane.getChildren().addAll(chart, field1, field2, field3, field4, linearRadioButton,
                absoluteRadioButton, parabolaRadioButton, reciprocalRadioButton, squarerootRadioButton,
                cubicRadioButton, sinRadioButton, cosRadioButton, tanRadioButton, formulaDisplay,
                plotGraphButton, resetButton, backToCalc, zeroGraph, trace, valueField, ValueGraph);
        // Set the default to linear function and reset the graph to ensure everything
        // is correct
        linearRadioButton.setSelected(true);
        resetGraph();
        // Event listeners to update the UI based on the selected function
        // Reset the graph when a new type is selected, change the formula needed to be
        // entered, only make the correct
        // amount of fields
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
            organizeFields("Enter coefficient (a)", "Enter constant (b)", "Enter horizontal translation (h)",
                    "Enter vertical translation (k)");
            resetGraph();
        });

        cubicRadioButton.setOnAction(e -> {
            formulaDisplay.setText("FORMULA: y = ax^3 + bx^2 + cx + d");
            organizeFields("Enter coefficient (a)", "Enter coefficient (b)", "Enter coefficient (c)",
                    "Enter coefficient (d)");
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

    //method to do simple arithmetic expressions
    private void performOperation(TextField textbox) {
        try {
            String input = textbox.getText();
            double result = evaluateExpression(input);
            textbox.setText(String.valueOf(result));
        } catch (Exception ex) {
            textbox.setText("Error");
        }
    }

    // Method to finalize the expression
    private double evaluateExpression(String expression) {
        // Use a stack to keep track of intermediate results
        Stack<Double> stack = new Stack<>();
        // Use a recursive helper function to evaluate the expression
        return evaluateExpressionHelper(expression, 0, stack);
    }

    // Method to calculate the complex math behind the stack of operations
    private double evaluateExpressionHelper(String expression, int index, Stack<Double> stack) {
        double currentOperand = 0;
        double decimalMultiplier = 0.1;
        char currentOperator = '+';
        boolean isDecimal = false;
        boolean isNegative = false;

        while (index < expression.length()) {
            char c = expression.charAt(index);

            if (Character.isDigit(c)) {
                if (isDecimal) {
                    currentOperand += (c - '0') * decimalMultiplier;
                    decimalMultiplier *= 0.1; // Move the decimal point to the left
                } else {
                    currentOperand = currentOperand * 10 + (c - '0');
                }
            } else if (c == '.') {
                isDecimal = true;
            } else if (isNegativeSign(expression, index)) {
                isNegative = true;
                index += 2; // Skip the "(-)" characters
            } else if (isOperator(c)) {
                applyOperator(stack, (isNegative ? -currentOperand : currentOperand), currentOperator);
                currentOperand = 0;
                currentOperator = c;
                isDecimal = false;
                decimalMultiplier = 0.1; // Reset decimal handling
                isNegative = false;
            } else if (c == '(') {
                currentOperand = evaluateExpressionHelper(expression, index + 1, stack);
                index++; // Skip the corresponding ')'
            } else if (c == ')') {
                break; // End of the current recursive call
            }

            index++;
        }

        applyOperator(stack, (isNegative ? -currentOperand : currentOperand), currentOperator);

        return stack.stream().mapToDouble(Double::doubleValue).sum();
    }

    // Method to check if there is a user-inputted negative sign before a number
    private boolean isNegativeSign(String expression, int index) {
        // Check if the next three characters are "(-)"
        return (index + 2 < expression.length() && expression.startsWith("(-)", index));
    }


    //TODO: ADD SIN,COS,TAN FUNCTIONALITY ON THE CALCULATOR


    // Method to apply the operator to the operand
    private void applyOperator(Stack<Double> stack, double operand, char operator) {
        switch (operator) {
            case '+':
                stack.push(operand);
                break;
            case '-':
                stack.push(-operand);
                break;
            case '*':
                stack.push(stack.pop() * operand);
                break;
            case '/':
                stack.push(stack.pop() / operand);
                break;
            case '%':
                double previousOperand = stack.pop();
                stack.push(previousOperand % operand);
                break;

            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    // Method to check for each operator
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    // Method to calculate combinations using BigInteger to avoid StackOverFlow errors
    private BigInteger calculateCombination(int n, int r) {
        BigInteger numerator = factorial(n);
        BigInteger denominator = factorial(r).multiply(factorial(n - r));

        if (denominator.equals(BigInteger.ZERO)) {
            // Division by zero, return zero
            return BigInteger.ZERO;
        } else {
            return numerator.divide(denominator);
        }
    }

    // Method to calculate permutations using BigInteger to avoid StackOverFlow errors
    private BigInteger calculatePermutation(int n, int r) {
        BigInteger numerator = factorial(n);
        BigInteger denominator = factorial(n - r);

        if (denominator.equals(BigInteger.ZERO)) {
            // Division by zero, return zero
            return BigInteger.ZERO;
        } else {
            return numerator.divide(denominator);
        }
    }

    // Method to calculate factorials using BigInteger to avoid StackOverFlow errors
    private BigInteger factorial(int n) {
        if (n == 0 || n == 1) {
            return BigInteger.ONE;
        } else {
            BigInteger result = BigInteger.ONE;
            for (int i = 2; i <= n; i++) {
                result = result.multiply(BigInteger.valueOf(i));
            }
            return result;
        }
    }


    // Method for setting up number buttons in relation to all buttons, so they are
    // the same size
    private void setButton(Button button, int width, double x, double y) {
        calcPane.getChildren().add(button);
        button.setPrefSize(width, 30);
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    // Method for deciding what goes into the text fields along with which ones are
    // displayed
    // Each function has its own number of prompts that get inputting into the
    // method
    private void organizeFields(String prompt1, String prompt2, String prompt3, String prompt4) {
        field1.setPromptText(prompt1);
        field2.setPromptText(prompt2);
        field3.setPromptText(prompt3);
        field4.setPromptText(prompt4);

        // Display just enough prompts based on the function being used
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
        // Set graphCheck to true once the graph is plotted
        graphCheck = true;
    }

    // Method to plot a linear function
    private void plotLine() {
        // Try-catch for inputs that are not numbers
        try {
            // Set the variables to the first and second fields depending on the amount
            // needed for the equation
            double slope = Double.parseDouble(field1.getText());
            double intercept = Double.parseDouble(field2.getText());
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            // Plot all y values from -20 to +20, and similar for other graphs as well
            for (double x = -20; x <= 20; x += 0.5) {
                double y = slope * x + intercept;
                series.getData().add(new XYChart.Data<>(x, y));
            }
            // Add the new data and remove the old data from previous charts
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            // Call the invalid input function and ask for proper inputs
            handleInvalidInputs();
        }
    }

    // Method to plot an absolute function
    private void plotAbsoluteFunction() {
        try {
            double a = Double.parseDouble(field1.getText());
            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -20; x <= 20; x += 0.5) {
                double y = FastMath.abs(a * x);
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
            // Calculate the x-coordinate of the vertex to fix the bug of plotting more
            // points on one quadrant than another
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

    // Method to solve/find the zero of a parabolic function
    private void solveParabola(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c, x1, x2;
        x1 = (-b - FastMath.sqrt(discriminant)) / (2 * a);
        x2 = (-b + FastMath.sqrt(discriminant)) / (2 * a);
        if (discriminant > 0) {
            trace.setText("ZERO = " + round(x1) + " and " + round(x2));
        } else if (discriminant == 0) {
            trace.setText("ZERO = " + round(x1));
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
                double y = a * FastMath.sqrt(b * (x + h)) + k;
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
            // Uses two series' to prevent a line from being drawn across the asymptotes and
            // falsely connecting the function
            XYChart.Series<Number, Number> positiveSeries = new XYChart.Series<>();
            XYChart.Series<Number, Number> negativeSeries = new XYChart.Series<>();
            // Set the style for both series and points (same color)
            String seriesStyle = "-fx-stroke: orange;";
            String pointStyle = "-fx-background-color: orange, white; -fx-background-insets: 0, 2; -fx-background-radius: 5px;";

            for (double x = 0.5; x <= 10; x += 0.5) {
                double y = a / x;
                positiveSeries.getData().add(new XYChart.Data<>(x, y));
            }
            for (double x = -10; x < 0; x += 0.5) {
                double y = a / x;
                negativeSeries.getData().add(new XYChart.Data<>(x, y));
            }
            chart.getData().clear();
            // Add the data separately to avoid "varargs" warning
            chart.getData().add(positiveSeries);
            chart.getData().add(negativeSeries);
            // Apply the style to both series
            positiveSeries.getNode().setStyle(seriesStyle);
            positiveSeries.getData().forEach(data -> data.getNode().setStyle(pointStyle));
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
                double y = a * FastMath.pow(x, 3) + b * FastMath.pow(x, 2) + c * x + d;
                series.getData().add(new XYChart.Data<>(x, y));
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
        }
    }

    // Method to solve/find the zero of a cubic function
    private void solveCubic() {
        double discriminant = Double.parseDouble(field4.getText()) * Double.parseDouble(field4.getText()) - 4 *
                FastMath.pow(Double.parseDouble(field3.getText()), 3);
        double root1 = FastMath.cbrt((-Double.parseDouble(field4.getText()) + discriminant) / 2);
        double root2 = FastMath.cbrt((-Double.parseDouble(field4.getText()) - discriminant) / 2);
        double root3 = root1 + root2;
        trace.setText("ZERO = " + round(root1) + ", " + round(root2) + ", " + round(root3));
    }

    // Method to plot sin
    private void plotSinFunction() {
        try {
            double a = Double.parseDouble(field1.getText());
            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            for (double x = -20; x <= 20; x += 0.5) {
                double y = a * FastMath.sin(x);
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
                double y = a * FastMath.cos(x);
                series.getData().add(new XYChart.Data<>(x, y));
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
        }
    }

    // Method to plot tan
    private void plotTanFunction() {
        try {
            double a = Double.parseDouble(field1.getText());
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            // Plotting the tangent function over a limited range to duplication issues
            for (double x = -FastMath.PI / 2 + 0.1; x <= FastMath.PI / 2 - 0.1; x += 0.05) {
                double y = a * FastMath.tan(x);
                // Handling the vertical asymptotes by checking for large y values
                if (Double.isFinite(y) && FastMath.abs(y) < 50) {
                    series.getData().add(new XYChart.Data<>(x, y));
                }
            }
            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            handleInvalidInputs();
        }
    }

    // Method to return the y value at an x on any graph - intellij formatted
    @NotNull
    private Button getValueGraph() {
        // Declare and initialize the x value button on the graph
        Button ValueGraph = new Button("X=");
        ValueGraph.setLayoutX(700);
        ValueGraph.setLayoutY(300);
        ValueGraph.setOnAction(startButtonEvent -> {
            // Try catch to ensure no error if the valueField is null
            try {
                // For each function, set x = text field to the y value when x = a certain value
                // (similar to the math in
                // the function point plotting as long as a graph is plotted and the value field
                // has a number in it
                if (linearRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                    // mx + b
                    trace.setText("Y = " + round(Double.parseDouble(field1.getText()) *
                            Double.parseDouble(valueField.getText()) + Double.parseDouble(field2.getText())));
                } else if (parabolaRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                    // ax^2 + bx + c
                    trace.setText("Y = " + round(Double.parseDouble(field1.getText()) *
                            Double.parseDouble(valueField.getText()) * Double.parseDouble(valueField.getText()) +
                            Double.parseDouble(field2.getText()) * Double.parseDouble(valueField.getText()) +
                            Double.parseDouble(field3.getText())));
                } else if (squarerootRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                    // a√(b(x - h)) + k
                    trace.setText("Y = " + round(Double.parseDouble(field1.getText()) *
                            FastMath.sqrt(
                                    Double.parseDouble(field2.getText()) * (Double.parseDouble(valueField.getText())
                                            - Double.parseDouble(field3.getText())))
                            + Double.parseDouble(field4.getText())));
                } else if (cubicRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                    // ax^3 + bx^2 + cx + d
                    trace.setText("Y = " + round(Double.parseDouble(field1.getText()) *
                            FastMath.pow(Double.parseDouble(valueField.getText()), 3)
                            + Double.parseDouble(field2.getText()) *
                            Double.parseDouble(valueField.getText()) * Double.parseDouble(valueField.getText())
                            +
                            Double.parseDouble(field3.getText()) * Double.parseDouble(valueField.getText()) +
                            Double.parseDouble(field4.getText())));
                } else if (absoluteRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                    // |a * x|
                    trace.setText("Y = " + round(FastMath.abs(Double.parseDouble(field1.getText()) *
                            Double.parseDouble(valueField.getText()))));
                } else if (reciprocalRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                    // y = a / x
                    trace.setText("Y = "
                            + round(Double.parseDouble(field1.getText()) / Double.parseDouble(valueField.getText())));
                } else if (sinRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                    trace.setText("Y = " + round(FastMath.sin(Double.parseDouble(valueField.getText()))));
                } else if (cosRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                    trace.setText("Y = " + round(FastMath.cos(Double.parseDouble(valueField.getText()))));
                } else if (tanRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                    trace.setText("Y = " + round(FastMath.tan(Double.parseDouble(valueField.getText()))));
                }
            } catch (NumberFormatException ignored) {

            }
        });
        return ValueGraph;
    }

    // Method to return the zeros of the graph - intellij formatted
    @NotNull
    private Button getZeroGraph() {
        // Declare and initialize the zeros button on the graph
        Button zeroGraph = new Button("ZERO");
        zeroGraph.setLayoutX(700);
        zeroGraph.setLayoutY(260);
        zeroGraph.setOnAction(startButtonEvent -> {
            // Reset the trace textbox
            resetTrace();
            // Set the text accordingly (if the right button is selected, the graph exists,
            // and valueField has a value
            // to the x intercepts of each graph (minus redundant functions that don't need
            // x ints calculated)
            // Use round() to round to nearest 100th
            if (linearRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                trace.setText("ZERO = "
                        + round(-Double.parseDouble(field2.getText()) / Double.parseDouble(field1.getText())));
            } else if (parabolaRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                solveParabola(Double.parseDouble(field1.getText()), Double.parseDouble(field2.getText()),
                        Double.parseDouble(field3.getText()));
            } else if (squarerootRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                trace.setText("ZERO = " + round((-FastMath.sqrt(Double.parseDouble(field4.getText()) /
                        Double.parseDouble(field1.getText())) + Double.parseDouble(field3.getText())) /
                        Double.parseDouble(field2.getText())));
            } else if (cubicRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                solveCubic();
            } else if (absoluteRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                trace.setText("ZERO = 0.0");
            } else if (reciprocalRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                trace.setText("THERE IS NO ZERO");
            } else if (sinRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                trace.setText("Not Calculating For Sin");
            } else if (cosRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                trace.setText("Not Calculating For Cos");
            } else if (tanRadioButton.isSelected() && graphCheck && valueField.getText() != null) {
                trace.setText("Not Calculating For Tan");
            } else {
                trace.setText("Graph A Function Please");
            }
        });
        return zeroGraph;
    }

    // Method to round the zeros to two decimal places
    private double round(double a) {
        return (double) FastMath.round(a * 100) / 100;
    }

    // Method to reset the text field for zeros and y values
    private void resetTrace() {
        valueField.clear();
        trace.setText("");
    }

    // Method to display the point being hovered over
    private void addPointHovering() {
        // Iterate through the chart, along with all the data
        for (XYChart.Series<Number, Number> series : chart.getData()) {
            for (XYChart.Data<Number, Number> data : series.getData()) {
                // Round the x and y values
                double roundedX = round(data.getXValue().doubleValue());
                double roundedY = round(data.getYValue().doubleValue());
                // Create a tooltip and node to hold then display all the data on screen in a
                // hovered textbox
                Node node = data.getNode();
                Tooltip tooltip = new Tooltip("(" + roundedX + ", " + roundedY + ")");
                Tooltip.install(node, tooltip);
                // Show the tooltip as the mouse is hovered over the node that holds the tooltip
                // a point in the scene
                node.setOnMouseEntered(event -> {
                    Point2D pointInScene = node.localToScreen(node.getBoundsInLocal().getMaxX(),
                            node.getBoundsInLocal().getMaxY());
                    tooltip.show(node, pointInScene.getX(), pointInScene.getY());
                });
                // Hide the tooltip after the mouse is no longer hovered over a point
                node.setOnMouseExited(event -> tooltip.hide());
            }
        }
    }

    // Method to handle invalid user input
    private void handleInvalidInputs() {
        // Assign values to the error label
        Label errorLabel = new Label("Please enter valid numbers");
        errorLabel.setLayoutX(20);
        errorLabel.setLayoutY(135);
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
        // Reorganizes the fields that are displayed for each function
        if (linearRadioButton.isSelected()) {
            organizeFields("Enter slope (m)", "Enter y-intercept (b)", null, null);
        } else if (absoluteRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", null, null, null);
        } else if (parabolaRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", "Enter constant (b)", "Enter constant (c)", null);
        } else if (squarerootRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", "Enter constant (b)", "Enter constant (h)", "Enter constant (k)");
        } else if (cubicRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", "Enter coefficient (b)", "Enter coefficient (c)",
                    "Enter coefficient (d)");
        } else if (sinRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", null, null, null);
        } else if (cosRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", null, null, null);
        } else if (tanRadioButton.isSelected()) {
            organizeFields("Enter coefficient (a)", null, null, null);
        }
    }

    // Music Methods
    private void startMedia() {
        // Handles when the play or pause button is pressed then starts the music
        playpauseButton.setOnAction(event -> {
            try {
                // Change the icon of the playpause button on button press
                playpauseIcon();
                // If there is media, add the first song to the player and set the label and
                // volume to the default values
                // then start the timer to handle the progress bar
                if (mediaPlayer == null) {
                    media = new Media(songs.get(songNumber).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    songLabel.setText(songs.get(songNumber).getName());
                    mediaPlayer.setVolume(volumeSlider.getValue() / 600);
                    beginTimer();
                }
                mediaPlayer.setVolume(volumeSlider.getValue() / 600);

                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                    // Stop timer to keep track of process
                    cancelTimer();
                } else {
                    mediaPlayer.play();
                    // Start timer to keep track of process
                    beginTimer();
                }
            } catch (Exception ignored) {

            }
        });
    }

    // Handles when the next song button is pressed and plays the next song
    private void nextSong() {
        try {
            playpause = false;
            playpauseIcon();
            // If it is not the last song, add one to the current song number and stop the
            // old song
            // If it is the last song, go back to the first one in the array
            if (songNumber < songs.size() - 1) {
                songNumber++;
                mediaPlayer.stop();
                if (running) {
                    cancelTimer();
                }

            } else {
                songNumber = 0;
                mediaPlayer.stop();
            }
            // Add the next song to the media player, song label, set the volume to the
            // default value, and play the song
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            mediaPlayer.setVolume(volumeSlider.getValue() / 600);

            mediaPlayer.play();
            beginTimer();
        } catch (Exception ignored) {

        }
    }

    // Previous song button to play the previous song
    private void previousSong() {
        try {
            playpause = false;
            playpauseIcon();
            // If it is not the first song, play the previous and stop the old song
            // If it is the first song, go back to the last song in the array
            if (songNumber > 0) {
                songNumber--;

            } else {
                songNumber = songs.size() - 1;

            }
            mediaPlayer.stop();
            if (running) {
                cancelTimer();
            }
            // Add the previous song to the media player, song label, set the volume to the
            // default value, and play the song
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            mediaPlayer.setVolume(volumeSlider.getValue() / 600);

            mediaPlayer.play();
            beginTimer();
        } catch (Exception ignored) {

        }
    }

    // Restart the song
    private void restartMedia() {
        try {
            // Reset the player to 0 seconds
            mediaPlayer.seek(Duration.seconds(0));
        } catch (Exception ignored) {

        }
    }

    // Handles the timer for the progress bar and starts when a song starts playing
    // - iterates every second
    private void beginTimer() {
        // Declare and initialize the timer and timer task thread
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                // Set timer running to true
                running = true;
                // Get the current and end time of the song
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(current / end);

                // Calculate current time and total length in proper format (minutes:seconds)
                int currentMinutes = (int) current / 60;
                int currentSeconds = (int) current % 60;
                int totalMinutes = (int) end / 60;
                int totalSeconds = (int) end % 60;

                // Update the songLabel to display current time and total length
                String currentTime = String.format("%02d:%02d", currentMinutes, currentSeconds);
                String totalTime = String.format("%02d:%02d", totalMinutes, totalSeconds);
                // UI-related operations must happen in the primary javafx thread, not the timer
                // task therefore
                // it must "run later" which is directly after a timer iteration
                // Essentially queuing the task until after the thread opens up
                Platform.runLater(() -> {
                    currentSongTime.setText(currentTime);
                    songTotal.setText(totalTime);
                });

                // Handle what happens when the song finishes
                // Loops the song when song is looped
                if (loop && current / end == 1) {
                    restartMedia();
                } else if (!loop && current / end == 1) {
                    // Shuffle songs if shuffle button is clicked
                    if (shuffle) {
                        // generate random song number
                        int random = ((int) (Math.random() * songs.size()));
                        shuffleCheck(random);
                        // Play shuffled song
                        cancelTimer();
                        mediaPlayer.stop();
                        media = new Media(songs.get(random).toURI().toString());
                        mediaPlayer = new MediaPlayer(media);
                        Platform.runLater(() -> songLabel.setText(songs.get(random).getName()));
                        mediaPlayer.setVolume(volumeSlider.getValue() / 600);
                        mediaPlayer.play();
                        beginTimer();
                        // Play next song if songs are not shuffled
                    }
                }
            }
        };
        // Iterate every second
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    // Handles when the timer is stopped
    private void cancelTimer() {
        // set running to false and cancel the timer
        running = false;
        timer.cancel();
    }

    // Loop song
    private void loopSong() {
        // Darken the button when clicked to indicate the song if looped
        // Reset button colour when toggled off
        if (!loop) {
            loopButton.setStyle(
                    "-fx-background-color: #147a38; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12px;");
            // If songs are shuffled, turn off shuffle
            // Songs can be either looped or shuffled, not both
            if (shuffle) {
                shuffleSong();
            }
        } else {
            loopButton.setStyle(
                    "-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12px;");
        }
        loop = !loop;
    }

    // Shuffle song button to play songs in random order
    private void shuffleSong() {
        // Change the colour of the button when shuffle is toggled on/off
        // On = dark, off = normal
        if (shuffle) {
            shuffleButton.setStyle(
                    "-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12px;");
        } else {
            shuffleButton.setStyle(
                    "-fx-background-color: #147a38; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12px;");
            // Turn off loop if it is on
            // You can only loop or shuffle songs, not both
            if (loop) {
                loopSong();
            }
        }
        shuffle = !shuffle;
    }

    // Method to change the icon of the playpause button
    private void playpauseIcon() {
        // Change button from play to pause(+vice versa) when clicked
        if (playpause) {
            playpauseButton.setText("▶");
            playpauseButton.setStyle(
                    "-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 12.5px;");
        } else {
            playpauseButton.setText("⏸");
            playpauseButton.setStyle(
                    "-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 50px; -fx-padding: 8px 8.5px;");
        }
        playpause = !playpause;
    }

    // Makes sure that the shuffle function does not repeat songs
    private void shuffleCheck(int random) {
        if (random == songNumber) {
            random = (int) (Math.random() * songs.size());
            shuffleCheck(random);
        }
    }

    // set layout of music functions on calculator and add them to the pane
    private void musicToMain() {
        calcPane.getChildren().addAll(playpauseButton, restartButton, nextButton, previousButton, loopButton,
                shuffleButton, songLabel, currentSongTime, songTotal, songProgressBar, volumeSlider, volumeIcon);
        restartButton.setLayoutX(60);
        restartButton.setLayoutY(20);
        previousButton.setLayoutX(188);
        previousButton.setLayoutY(20);
        playpauseButton.setLayoutX(224);
        playpauseButton.setLayoutY(20);
        nextButton.setLayoutX(257);
        nextButton.setLayoutY(20);
        loopButton.setLayoutX(142);
        loopButton.setLayoutY(20);
        shuffleButton.setLayoutX(302);
        shuffleButton.setLayoutY(20);
        songLabel.setLayoutX(218);
        songLabel.setLayoutY(100);
        currentSongTime.setLayoutX(56);
        currentSongTime.setLayoutY(80);
        songTotal.setLayoutX(489);
        songTotal.setLayoutY(80);
        songProgressBar.setLayoutX(86);
        songProgressBar.setLayoutY(80);
        songProgressBar.setPrefWidth(400);
        volumeIcon.setLayoutX(351);
        volumeIcon.setLayoutY(20);
        volumeSlider.setLayoutX(391);
        volumeSlider.setLayoutY(37);
    }

    // set layout of music functions on graph and add them to the pane
    private void musicToGraph() {
        graphPane.getChildren().addAll(playpauseButton, restartButton, nextButton, previousButton, loopButton,
                shuffleButton, songLabel, currentSongTime, songTotal, songProgressBar, volumeSlider, volumeIcon);
        restartButton.setLayoutX(690);
        restartButton.setLayoutY(500);
        previousButton.setLayoutX(804);
        previousButton.setLayoutY(500);
        playpauseButton.setLayoutX(834);
        playpauseButton.setLayoutY(500);
        nextButton.setLayoutX(864);
        nextButton.setLayoutY(500);
        loopButton.setLayoutX(764);
        loopButton.setLayoutY(500);
        shuffleButton.setLayoutX(900);
        shuffleButton.setLayoutY(500);
        songLabel.setLayoutX(730);
        songLabel.setLayoutY(560);
        currentSongTime.setLayoutX(650);
        currentSongTime.setLayoutY(580);
        songTotal.setLayoutX(940);
        songTotal.setLayoutY(580);
        songProgressBar.setLayoutX(680);
        songProgressBar.setLayoutY(580);
        songProgressBar.setPrefWidth(250);
        volumeIcon.setLayoutX(710);
        volumeIcon.setLayoutY(610);
        volumeSlider.setLayoutX(750);
        volumeSlider.setLayoutY(625);
    }
}