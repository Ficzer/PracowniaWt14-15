package com.pracownia.vanet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReadRoutes {

	/*private String fileName;

	public ReadRoutes(String fileName) {
		this.fileName = fileName;

	}

	public List<Route> readAllRoutes() {
		List<Route> routes = new ArrayList<>();
		try (Stream<Path> paths = Files.walk(Paths.get(fileName))) {
			paths
					.filter(o -> o.toString().endsWith(".txt"))
					.forEach(t -> routes.add(getRoute(fileName + "/" + t.getFileName().toString())));


			return routes;
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
		}
		return null;
	}

	private Route getRoute(String fileName) {
		Route route = new Route();
		List<Integer> xCoordinates = new ArrayList<>();
		List<Integer> yCoordinates = new ArrayList<>();
		File file = new File(fileName);

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(" ");
				xCoordinates.add(Integer.parseInt(split[0]));
				yCoordinates.add(Integer.parseInt(split[1]));
			}
			
			route.xCoordinates = xCoordinates;
			route.yCoordinates = yCoordinates;
			
			return route;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}*/
}
