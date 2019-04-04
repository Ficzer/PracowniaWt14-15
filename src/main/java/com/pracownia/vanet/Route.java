package com.pracownia.vanet;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Trasa(ulica), zawiera 2 punkty oznaczające koniec i początek trasy
 */
@Data
public class Route {

	private Point startPoint;
	private Point endPoint;

	public Route(){
		startPoint = new Point();
		endPoint = new Point();
	}

	public Route(Point startPoint, Point endPoint) {

		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Route(double xStartPoint, double yStartPoint, double xEndPoint, double yEndPoint) {

		this.startPoint = new Point(xStartPoint, yStartPoint);
		this.endPoint = new Point(xEndPoint, yEndPoint);

	}

	public double getDistance()
	{
		return Math.sqrt(Math.pow(endPoint.getX()-startPoint.getX(), 2) + Math.pow(endPoint.getY()-startPoint.getY(), 2));
	}
}
