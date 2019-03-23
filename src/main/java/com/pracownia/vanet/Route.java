package com.pracownia.vanet;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Trasa. Zawiera 2 listy ze współrzędnymi.
 */
@Data
public class Route {

	List<Double> xCoordinates;
	List<Double> yCoordinates;

	public Route(){
		xCoordinates = new ArrayList<>();
		yCoordinates = new ArrayList<>();

	}


	/**
	 * trasy tylko dla testu. Potem można wczytać z pliku np.
	 */
	public void route1(){
		for(double i = 500.0; i<800; i+=0.1){
			xCoordinates.add(i);
			yCoordinates.add(100.0);
		}
	}

	public void route2(){
		for(double i = 300.0; i<1000; i+=0.1){
			xCoordinates.add(i);
			yCoordinates.add(50.0);
		}
	}

	public void route3(){
		for(double i = 200; i<500; i+=0.01){
			xCoordinates.add(100.0);
			yCoordinates.add(i);
		}
	}

}
