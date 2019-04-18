package com.pracownia.vanet;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

public class WindowApp extends Application {

	private Group root = new Group();
	private boolean isRangeRendered = false;

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});

		Simulation simulation = new Simulation();

		setRoutesLines(simulation);
		//setVehicleCircles(simulation);
		setInterface(simulation);

		Scene scene = new Scene(root, 1100, 800);
		primaryStage.setTitle("Vanet");
		primaryStage.setScene(scene);
		primaryStage.show();
		simulation.tr.start();
	}

	Circle circleCreator(Vehicle vehicle){
		Circle circle = new Circle();
		circle.setCenterX(vehicle.getCurrentLocation().getX());
		circle.setCenterY(vehicle.getCurrentLocation().getY());
		circle.setFill(Color.BLACK);
		circle.setRadius(5.0);
		return circle;
	}

	Circle rangeCreator(Vehicle vehicle){
		Circle circle = new Circle();
		circle.setRadius(vehicle.getRange());
		circle.setCenterX(vehicle.getCurrentLocation().getX());
		circle.setCenterY(vehicle.getCurrentLocation().getY());
		circle.setFill(Color.TRANSPARENT);
		circle.setStroke(Color.TRANSPARENT);
		return circle;
	}

	private Line lineCrator(Route route){
		Line line = new Line();
		line.setStartX(route.getStartPoint().getX());
		line.setStartY(route.getStartPoint().getY());
		line.setEndX(route.getEndPoint().getX());
		line.setEndY(route.getEndPoint().getY());

		return line;
	}

	private void setRoutesLines(Simulation simulation)
	{
		for(int i=0; i<simulation.getMap().getRoutes().size(); i++)
		{
			Line line = lineCrator(simulation.getMap().getRoutes().get(i));
			root.getChildren().add(line);
		}
	}

	private void setVehicleCircles(Simulation simulation, int amount)
	{
		for(int i=simulation.getMap().getVehicles().size() - amount; i<simulation.getMap().getVehicles().size(); i++)
		{
			Circle circle = circleCreator(simulation.getMap().getVehicles().get(i));
			Circle rangeCircle = rangeCreator(simulation.getMap().getVehicles().get(i));
			simulation.getCircleList().add(circle);
			simulation.getRangeList().add(rangeCircle);
			root.getChildren().add(circle);
			root.getChildren().add(rangeCircle);
		}
	}

	private void setInterface(Simulation simulation)
	{
		Button showRangeButton = new Button("Show Range");
		Button spawnVehiclesButton = new Button("Spawn Vehicles");
		TextField vehiclesAmountField = new TextField();
		Label vehiclesAmountLabel = new Label("Vehicle Amount");

		showRangeButton.setLayoutX(950.0);
		showRangeButton.setLayoutY(100.0);
		spawnVehiclesButton.setLayoutX(950.0);
		spawnVehiclesButton.setLayoutY(130.0);
		vehiclesAmountLabel.setLayoutX(950.0);
		vehiclesAmountLabel.setLayoutY(160.0);
		vehiclesAmountField.setLayoutX(950.0);
		vehiclesAmountField.setLayoutY(190.0);
;
		showRangeButton.setOnAction(e -> {
			isRangeRendered = !isRangeRendered;
			if(isRangeRendered)
			{
				simulation.switchOnRangeCircles();
			}
			else
			{
				simulation.switchOffRangeCircles();
			}
		});

		spawnVehiclesButton.setOnAction(e -> {
			simulation.getMap().addVehicles(Integer.parseInt(vehiclesAmountField.getText()));
			setVehicleCircles(simulation, Integer.parseInt(vehiclesAmountField.getText()));

		});

		root.getChildren().addAll(showRangeButton, spawnVehiclesButton, vehiclesAmountField, vehiclesAmountLabel);
	}


}
