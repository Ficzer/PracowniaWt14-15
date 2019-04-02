package com.pracownia.vanet;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

public class WindowApp extends Application {

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});
		Simulation simulation = new Simulation();

		for(int i = 0; i<simulation.map.vehicles.size(); i++){
			Circle circle = circleCreator(simulation.getMap().getVehicles().get(i));
			Circle range = rangeCreator(simulation.getMap().getVehicles().get(i));
			simulation.circleList.add(circle);
			simulation.rangeList.add(range);
			root.getChildren().add(circle);
			root.getChildren().add(range);
		}

		Scene scene = new Scene(root, 450, 270);
		primaryStage.setTitle("Vanet");
		primaryStage.setScene(scene);
		primaryStage.show();
		simulation.tr.start();
	}

	Circle circleCreator(Vehicle vehicle){
		Circle circle = new Circle();
		circle.setCenterX(vehicle.getCurrentX());
		circle.setCenterY(vehicle.getCurrentY());
		circle.setFill(Color.BLACK);
		circle.setRadius(5.0);
		return circle;
	}

	Circle rangeCreator(Vehicle vehicle){
		Circle circle = new Circle();
		circle.setRadius(vehicle.getRange());
		circle.setCenterX(vehicle.getCurrentX());
		circle.setCenterY(vehicle.getCurrentY());
		circle.setFill(Color.TRANSPARENT);
		circle.setStroke(Color.BLACK);
		return circle;
	}
}
