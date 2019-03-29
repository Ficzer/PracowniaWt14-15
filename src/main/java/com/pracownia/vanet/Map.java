package com.pracownia.vanet;

import com.pracownia.vanet.net.Rsu;
import javafx.scene.paint.Color;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Przechoowuje samochody i ich powiązania z trasami.
 */

@Data
public class Map {

	private double width = 450.0;
	private double height = 270.0;

	List<Route> routes;
	List<Vehicle> vehicles;
	List<Rsu> rsus = new ArrayList<>();

	public Map(){
		ReadRoutes readRoutes = new ReadRoutes("./routes/");
		routes = readRoutes.readAllRoutes();
		vehicles = new ArrayList<>();

		for(int i = 0; i < routes.size(); ++i){
			vehicles.add(new Vehicle(routes.get(i), i, Color.rgb((61*i)%200, (97*i)%200, (157*i)%200)));
		}

		rsus.add(new Rsu(new Point(96, 96)));
		rsus.add(new Rsu(new Point(64, 128)));
		rsus.add(new Rsu(new Point(256, 128)));
		rsus.add(new Rsu(new Point(384, 256)));
		rsus.add(new Rsu(new Point(300, 32)));
	}


}
