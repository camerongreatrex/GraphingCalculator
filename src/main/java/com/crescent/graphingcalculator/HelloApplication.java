package com.crescent.graphingcalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    // stage renamed to window
    public static Stage window;
    // pane renamed to mainPane
    public static Pane mainPane = new Pane();
    //create the main menu scene
    public static Scene menuScene = new Scene(mainPane, 1000, 700);
    //create buttons for the calculator
    public static Button startCalcButton = new Button("▷"), closeCalcButton = new Button("X");

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
        startCalcButton.setOnAction(startButtonEvent -> {

        });
    }

    public static void main(String[] args) {
        launch();
    }
}