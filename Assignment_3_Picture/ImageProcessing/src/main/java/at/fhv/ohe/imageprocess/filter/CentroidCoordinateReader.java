package at.fhv.ohe.imageprocess.filter;

import at.fhv.ohe.imageprocess.centroidfilter.Coordinate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class CentroidCoordinateReader {

    private String path;

    CentroidCoordinateReader(String path) {
        this.path = path;
    }

    ArrayList<Coordinate> getExpectedCoordinates() throws IOException {
        try (BufferedReader buf = new BufferedReader(new FileReader(path))) {

            String lineJustFetched = buf.readLine();
            String[] coords = lineJustFetched.split("\\s+");
            ArrayList<Coordinate> coordinates = new ArrayList<>();

            for (String s : coords) {

                String[] coordXY = s.split(",");
                int x = Integer.parseInt(coordXY[0]);
                int y = Integer.parseInt(coordXY[1]);
                coordinates.add(new Coordinate(x, y));
            }
            return coordinates;
        }
    }

}
