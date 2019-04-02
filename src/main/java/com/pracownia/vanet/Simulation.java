package com.pracownia.vanet;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Simulation implements Runnable{

	Map map;
	List<Circle> circleList;
	List<Circle> rangeList;
	Thread tr;
	public Simulation() {
		map = new Map();
		circleList = new ArrayList<>();
		rangeList = new ArrayList<>();
		tr = new Thread(this);
	}


	public void run(){
		while (true) {
			updateVehiclesPosition();
			showVehiclesConnected();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	private void updateVehiclesPosition() {
		int it = 0;

			for (Vehicle vehicle : map.getVehicles()) {
				vehicle.update(map.routes);
				circleList.get(it).setCenterX(vehicle.getCurrentX());
				circleList.get(it).setCenterY(vehicle.getCurrentY());
				rangeList.get(it).setCenterX(vehicle.getCurrentX());
				rangeList.get(it).setCenterY(vehicle.getCurrentY());
				it++;
			}
	}

	private void showVehiclesConnected(){
		int it = 0;
		for (Vehicle vehicle : map.getVehicles()) {
			for (Vehicle vehicle2 : map.getVehicles()) {
				if(vehicle.id != vehicle2.id){
					double xcoord = Math.abs (vehicle.getCurrentX() - vehicle2.getCurrentX());
					double ycoord = Math.abs (vehicle.getCurrentY() - vehicle2.getCurrentY());
					double distance = Math.sqrt(ycoord*ycoord + xcoord * xcoord);
					if(distance<=vehicle.getRange()) {
						circleList.get(it).setFill(Color.GREEN);
						break;
					}
					else circleList.get(it).setFill(Color.BLACK);
				}
			}
			it++;
		}
	}

}
