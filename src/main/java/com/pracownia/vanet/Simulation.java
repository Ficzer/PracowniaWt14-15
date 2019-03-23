package com.pracownia.vanet;

import lombok.Data;

@Data
public class Simulation {

	Map map;

	public Simulation(){
		map = new Map();
	}
	public void run(){

		//while(true) {
			for (Vehicle vehicle : map.getVehicles()) {
				vehicle.update();
			}
		//}
	}
}
