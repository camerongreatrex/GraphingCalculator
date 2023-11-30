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

        mainPane = new Pane();
        primaryStage.setResizable(false);

        // create axis for the chart
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("y");

        // create the line chart
        chart = new LineChart<>(xAxis, yAxis);
        chart.setLayoutX(20);
        chart.setLayoutY(150);
        chart.setPrefSize(650, 650);
        chart.setLegendVisible(false);

        // initialize text fields, radio buttons, and buttons
        setUpFields();
        setUpRadioButtons();
        setUpButtons();

        // add nodes to the main pane
        mainPane.getChildren().addAll(chart, slopeField, interceptField, constantField, linearRadioButton,
                parabolaRadioButton);

        // create the scene and set it to the stage
        Scene scene = new Scene(mainPane, 800, 800);
        primaryStage.setScene(scene);

        // set initial text prompt and visibility for constantField
        slopeField.setPromptText("Enter slope (m)");
        interceptField.setPromptText("Enter y-intercept (b)");
        constantField.setVisible(false);
        constantField.setPromptText("Enter constant (a)");

        // define actions for radio button selection changes
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

    // initialize text fields for slope, intercept, and constant
    private void setUpFields() {
        slopeField = createTextField(20, 20);
        interceptField = createTextField(20, 60);
        constantField = createTextField(20, 100);
        constantField.setVisible(false);
    }

    // create a text field with given layout coordinates
    private TextField createTextField(double layoutX, double layoutY) {
        TextField textField = new TextField();
        textField.setLayoutX(layoutX);
        textField.setLayoutY(layoutY);
        textField.setPrefWidth(180);
        mainPane.getChildren().add(textField);
        return textField;
    }

    // initialize radio buttons for linear and parabolic functions
    private void setUpRadioButtons() {
        linearRadioButton = createRadioButton("Linear Function", 250, 20, true);
        parabolaRadioButton = createRadioButton("Parabola", 250, 60, false);

        ToggleGroup functionToggleGroup = new ToggleGroup();
        linearRadioButton.setToggleGroup(functionToggleGroup);
        parabolaRadioButton.setToggleGroup(functionToggleGroup);
    }

    // create a radio/option button with given text, layout coordinates, and selection status
    private RadioButton createRadioButton(String text, double layoutX, double layoutY, boolean selected) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setLayoutX(layoutX);
        radioButton.setLayoutY(layoutY);
        radioButton.setSelected(selected);
        mainPane.getChildren().add(radioButton);
        return radioButton;
    }

    // initialize buttons for plot and reset functionalities
    private void setUpButtons() {
        Button plotGraphButton = createButton("Plot Graph", 450, 20);
        plotGraphButton.setOnAction(e -> plotGraph());

        Button resetButton = createButton("Reset Graph", 450, 60);
        resetButton.setOnAction(e -> resetGraph());

        mainPane.getChildren().addAll(plotGraphButton, resetButton);
    }

    // create a button with given text, layout coordinates, and add it to the main pane
    private Button createButton(String text, double layoutX, double layoutY) {
        Button button = new Button(text);
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        mainPane.getChildren().add(button);
        return button;
    }

    // plot the selected graph type (linear or parabolic)
    private void plotGraph() {
        try {
            if (linearRadioButton.isSelected()) {
                plotLine();
            } else if (parabolaRadioButton.isSelected()) {
                plotParabola();
            }
        } catch (NumberFormatException ex) {
            Label errorLabel = new Label("Please enter valid numbers for coefficients.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135);
            mainPane.getChildren().removeIf(node -> node instanceof Label);
            mainPane.getChildren().add(errorLabel);
        }
    }

    // plot a linear function based on user-provided values
    private void plotLine() {
        try {
            double slope = Double.parseDouble(slopeField.getText());
            double intercept = Double.parseDouble(interceptField.getText());

            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            for (double x = -10; x <= 10; x += 0.1) {
                double y = slope * x + intercept;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            Label errorLabel = new Label("Please enter valid numbers for slope and intercept.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135);
            mainPane.getChildren().removeIf(node -> node instanceof Label);
            mainPane.getChildren().add(errorLabel);
        }
    }

    // plot a parabolic function based on user-provided values
    private void plotParabola() {
        try {
            double a = Double.parseDouble(slopeField.getText());
            double b = Double.parseDouble(interceptField.getText());
            double c = Double.parseDouble(constantField.getText());

            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            for (double x = -10; x <= 10; x += 0.1) {
                double y = a * x * x + b * x + c;
                series.getData().add(new XYChart.Data<>(x, y));
            }

            chart.getData().clear();
            chart.getData().add(series);
        } catch (NumberFormatException ex) {
            Label errorLabel = new Label("Please enter valid numbers for coefficients.");
            errorLabel.setLayoutX(20);
            errorLabel.setLayoutY(135);
            mainPane.getChildren().removeIf(node -> node instanceof Label);
            mainPane.getChildren().add(errorLabel);
        }
    }

    // reset the graph and input fields based on the selected function type
    private void resetGraph() {
        chart.getData().clear();
        slopeField.clear();
        interceptField.clear();
        constantField.clear();

        if (linearRadioButton.isSelected()) {
            slopeField.setPromptText("Enter slope (m)");
            interceptField.setPromptText("Enter y-intercept (b)");
        } else if (parabolaRadioButton.isSelected()) {
            constantField.setPromptText("Enter constant (a)");
        }
    }
}
