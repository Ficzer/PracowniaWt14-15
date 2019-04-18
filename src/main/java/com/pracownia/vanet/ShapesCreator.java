package com.pracownia.vanet;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ShapesCreator
{
    private Group root;

    public ShapesCreator(Group root)
    {
        this.root = root;
    }

    private Circle circleCreator(Vehicle vehicle){
        Circle circle = new Circle();
        circle.setCenterX(vehicle.getCurrentLocation().getX());
        circle.setCenterY(vehicle.getCurrentLocation().getY());
        circle.setFill(Color.BLACK);
        circle.setRadius(8.0);
        return circle;
    }

    private Circle circleCreator(EventSource eventSource){
        Circle circle = new Circle();
        circle.setCenterX(eventSource.getLocalization().getX());
        circle.setCenterY(eventSource.getLocalization().getY());
        circle.setFill(Color.RED);
        circle.setRadius(8.0);
        return circle;
    }

    private Circle rangeCreator(Vehicle vehicle){
        Circle circle = new Circle();
        circle.setRadius(vehicle.getRange());
        circle.setCenterX(vehicle.getCurrentLocation().getX());
        circle.setCenterY(vehicle.getCurrentLocation().getY());
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.TRANSPARENT);
        return circle;
    }

    private Circle rangeCreator(EventSource eventSource){
        Circle circle = new Circle();
        circle.setRadius(eventSource.getRange());
        circle.setCenterX(eventSource.getLocalization().getX());
        circle.setCenterY(eventSource.getLocalization().getY());
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

    public void setRoutesLines(Simulation simulation)
    {
        for(int i=0; i<simulation.getMap().getRoutes().size(); i++)
        {
            Line line = lineCrator(simulation.getMap().getRoutes().get(i));
            root.getChildren().add(line);
        }
    }

    public void setVehicleCircles(Simulation simulation, int amount)
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

    public void setSourceEventCircles(Simulation simulation)
    {
        for(int i=0; i<simulation.getMap().getEventSources().size(); i++)
        {
            Circle circle = circleCreator(simulation.getMap().getEventSources().get(i));
            Circle rangeCircle = rangeCreator(simulation.getMap().getEventSources().get(i));
            root.getChildren().add(circle);
            root.getChildren().add(rangeCircle);
        }
    }
}
