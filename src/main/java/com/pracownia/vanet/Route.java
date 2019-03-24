package com.pracownia.vanet;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Trasa. Zawiera 2 listy ze współrzędnymi.
 */
@Data
public class Route {

	List<Integer> xCoordinates;
	List<Integer> yCoordinates;

	public Route(){
		xCoordinates = new ArrayList<>();
		yCoordinates = new ArrayList<>();
	}
}
