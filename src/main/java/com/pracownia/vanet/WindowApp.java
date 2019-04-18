package com.pracownia.vanet;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WindowApp extends Application {

	private Group root = new Group();
	ShapesCreator shapesCreator = new ShapesCreator(root);
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

		shapesCreator.setRoutesLines(simulation);
		shapesCreator.setSourceEventCircles(simulation);

		setInterface(simulation);

		Scene scene = new Scene(root, 1100, 800);
		primaryStage.setTitle("Vanet");
		primaryStage.setScene(scene);
		primaryStage.show();
		simulation.tr.start();
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
		vehiclesAmountLabel.setLayoutY(170.0);
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
			shapesCreator.setVehicleCircles(simulation, Integer.parseInt(vehiclesAmountField.getText()));

		});

		root.getChildren().addAll(showRangeButton, spawnVehiclesButton, vehiclesAmountField, vehiclesAmountLabel);
	}


}
