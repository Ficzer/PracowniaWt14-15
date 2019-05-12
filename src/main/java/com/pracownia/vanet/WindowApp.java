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

    public TextField trustLevelField;
    public TextField speedField;
    public TextField vehIdField;
    public TextField connPointsField;
    private Group root = new Group();
    private ShapesCreator shapesCreator;
    private boolean isRangeRendered = false;
    private Simulation simulation;

    public static void main(String[] args) {
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

        this.simulation = new Simulation();
        this.shapesCreator = new ShapesCreator(root, this.simulation, this);

        shapesCreator.setRoutesLines(simulation);
        shapesCreator.setSourceEventCircles(simulation);
        shapesCreator.setStationaryPointCircles(simulation);

        setInterface(simulation);

        Scene scene = new Scene(root, 1100, 800);
        primaryStage.setTitle("Vanet");
        primaryStage.setScene(scene);
        primaryStage.show();
        simulation.tr.start();
    }


    private void setInterface(Simulation simulation) {
        Button showRangeButton = new Button("Show Range");
        Button changeRangeButton = new Button("ChangeRange");
        Button spawnVehiclesButton = new Button("Spawn Vehicles");
        TextField vehiclesAmountField = new TextField();
        TextField rangeAmountField = new TextField();
        Label rangeAmountLabel = new Label("Range");
        Label vehiclesAmountLabel = new Label("Vehicle Amount");

        // Start stop simulation.
        Button startSimulation = new Button("Start simulation");
        startSimulation.setLayoutX(950.0);
        startSimulation.setLayoutY(280.);
        startSimulation.setOnAction(e -> {
            simulation.setSimulationRunning(true);
        });

        Button stopSimulation = new Button("Stop simulation");
        stopSimulation.setLayoutX(950.0);
        stopSimulation.setLayoutY(310.);
        stopSimulation.setOnAction(e -> {
            simulation.setSimulationRunning(false);
        });


        // Vehicle informations.
        this.trustLevelField = new TextField();
        trustLevelField.setLayoutX(950.0);
        trustLevelField.setLayoutY(400.0);

        Label trustLevelLabel = new Label("Trust level");
        trustLevelLabel.setLayoutX(950.0);
        trustLevelLabel.setLayoutY(430.0);

        this.speedField = new TextField();
        speedField.setLayoutX(950.0);
        speedField.setLayoutY(460.0);

        Label speedLabel = new Label("Speed");
        speedLabel.setLayoutX(950.0);
        speedLabel.setLayoutY(490.0);

        this.vehIdField = new TextField();
        vehIdField.setLayoutX(950.0);
        vehIdField.setLayoutY(520.0);

        Label vehIdLabel = new Label("Veh id");
        vehIdLabel.setLayoutX(950.0);
        vehIdLabel.setLayoutY(550.0);


        this.connPointsField = new TextField();
        connPointsField.setLayoutX(950.0);
        connPointsField.setLayoutY(580.0);

        Label connPointsLabel = new Label("Conn points");
        connPointsLabel.setLayoutX(950.0);
        connPointsLabel.setLayoutY(610.0);

        // Other stuff.

        showRangeButton.setLayoutX(950.0);
        showRangeButton.setLayoutY(80.0);

        changeRangeButton.setLayoutX(950.0);
        changeRangeButton.setLayoutY(110.0);
        rangeAmountLabel.setLayoutX(950.0);
        rangeAmountLabel.setLayoutY(140.0);
        rangeAmountField.setLayoutX(950.0);
        rangeAmountField.setLayoutY(160.0);
        rangeAmountField.setText("40.0");

        spawnVehiclesButton.setLayoutX(950.0);
        spawnVehiclesButton.setLayoutY(190.0);
        vehiclesAmountLabel.setLayoutX(950.0);
        vehiclesAmountLabel.setLayoutY(220.0);
        vehiclesAmountField.setLayoutX(950.0);
        vehiclesAmountField.setLayoutY(240.0);
        vehiclesAmountField.setText("10");

        changeRangeButton.setOnAction(e -> simulation.changeVehiclesRanges(Double.parseDouble(rangeAmountField.getText())));


        showRangeButton.setOnAction(e -> {
            isRangeRendered = !isRangeRendered;
            if (isRangeRendered) {
                simulation.switchOnRangeCircles();
            } else {
                simulation.switchOffRangeCircles();
            }
        });

        spawnVehiclesButton.setOnAction(e -> {
            simulation.getMap().addVehicles(Integer.parseInt(vehiclesAmountField.getText()));
            shapesCreator.setVehicleCircles(simulation, Integer.parseInt(vehiclesAmountField.getText()));
            shapesCreator.setLabels(simulation, Integer.parseInt(vehiclesAmountField.getText()));
        });

        root.getChildren()
                .addAll(showRangeButton,
                        spawnVehiclesButton,
                        vehiclesAmountField,
                        stopSimulation,
                        trustLevelField,
                        trustLevelLabel,
                        speedField,
                        speedLabel,
                        vehIdField,
                        vehIdLabel,
                        connPointsField,
                        connPointsLabel,
                        startSimulation,
                        vehiclesAmountLabel,
                        rangeAmountLabel,
                        rangeAmountField,
                        changeRangeButton);
    }
}
