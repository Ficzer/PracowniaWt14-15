package com.pracownia.vanet;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class WindowApp extends Application {

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		List<Circle> circleList = new ArrayList<>();
		Simulation simulation = new Simulation();

		for (int j = 0; j < 1000; j++) {
			for (int i = 0; i < simulation.map.vehicles.size(); i++) {
				Circle circle = new Circle(
						simulation.getMap().getVehicles().get(i).getCoordinates().getX(),
						simulation.getMap().getVehicles().get(i).getCoordinates().getY(),
						5.0,
						simulation.getMap().getVehicles().get(i).color);
				simulation.run();
				circleList.add(circle);
				root.getChildren().add(circle);
			}
		}

		Scene scene = new Scene(root, 450, 270);
		primaryStage.setTitle("Vanet");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
