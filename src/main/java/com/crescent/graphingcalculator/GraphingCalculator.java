package com.crescent.graphingcalculator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        startCalcButton.setLayoutX(350);
        startCalcButton.setLayoutY(250);
        mainPane.getChildren().add(startCalcButton);
        startCalcButton.setMinWidth(200);
        startCalcButton.setMinHeight(100);
        startCalcButton.setStyle(
                "-fx-font-size: 50px;-fx-background-color: Black;-fx-text-fill: white; -fx-background-radius: 15px;");
        //set properties for the calculator close button
        closeCalcButton.setLayoutX(350);
        closeCalcButton.setLayoutY(375);
        mainPane.getChildren().add(closeCalcButton);
        closeCalcButton.setMinWidth(200);
        closeCalcButton.setMinHeight(100);
        closeCalcButton.setStyle(
                "-fx-font-size: 50px;-fx-background-color: Black;-fx-text-fill: white; -fx-background-radius: 15px;");
        
        //set properties for numerical buttons
        setButton(one, 50, 50, 60, 210);
        setButton(two, 50, 50, 110, 210);
        setButton(three, 50, 50, 160, 210);
        setButton(four, 50, 50, 60, 260);
        setButton(five, 50, 50, 110, 260);
        setButton(six, 50, 50, 160, 260);
        setButton(seven, 50, 50, 60, 310);
        setButton(eight, 50, 50, 110, 310);
        setButton(nine, 50, 50, 160, 310);
        setButton(zero, 50, 50, 110, 360);

        
        startCalcButton.setOnAction(startButtonEvent -> {
            window.setScene(graphScene);
        });

        window.setOnCloseRequest(closeEvent -> {
            Platform.exit();
        });
        closeCalcButton.setOnAction(closeButtonEvent -> {
            window.close();
        });
    }

    public void setButton(Button button, int i, int j, int x, int y) {
        graphGroup.getChildren().add(button);
        button.setPrefSize(i, j);
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    public static void main(String[] args) {
        launch();
    }
}