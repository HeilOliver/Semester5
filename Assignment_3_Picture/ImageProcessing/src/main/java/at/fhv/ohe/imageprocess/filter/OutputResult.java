package at.fhv.ohe.imageprocess.filter;

import at.fhv.ohe.imageprocess.centroidfilter.Coordinate;
import at.fhv.ohe.imageprocess.strategy.SolderingResult;
import at.fhv.pmp.filter.Sink;
import at.fhv.pmp.interfaces.Readable;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OutputResult extends Sink<ArrayList<Coordinate>> {
    private static final String CENTROID_COORDINATES_PATH = "ImageProcessing\\target\\classes\\at\\fhv\\ohe\\imageprocess\\coordinates\\expectedCentroids.txt";
    private int tolerance;
    private Set<SolderingResult> resultSet;
    private List<Coordinate> centroidCoordinates;


    public OutputResult(Readable<ArrayList<Coordinate>> input, int tolerance, Set<SolderingResult> pathResult) throws IOException {
        super(input);
        init();
        this.tolerance = tolerance;
        resultSet = pathResult;
    }

    public OutputResult(Set<SolderingResult> pathResult, int tolerance) throws IOException {
        super();
        init();
        this.tolerance = tolerance;
        resultSet = pathResult;
    }

    private void init() throws IOException {
        // Load the centroid coordinates
        centroidCoordinates = new ArrayList<>();
        centroidCoordinates = new CentroidCoordinateReader(CENTROID_COORDINATES_PATH)
                .getExpectedCoordinates();
    }

    @Override
    public void write(ArrayList<Coordinate> value) throws StreamCorruptedException {
        int i = 0;

        while (i < value.size()) {
            int toleranceX = value.get(i)._x - centroidCoordinates.get(i)._x;
            int toleranceY = value.get(i)._y - centroidCoordinates.get(i)._y;

            if (toleranceX < 0) {
                toleranceX = toleranceX * (-1);
            }

            if (toleranceY < 0) {
                toleranceY = toleranceY * (-1);
            }

            boolean inTolerance = (toleranceX <= tolerance) && (toleranceY <= tolerance);
            SolderingResult solderingResult = new SolderingResult(i, inTolerance, toleranceX, toleranceY);
            resultSet.add(solderingResult);

            i++;
        }
    }

    @Override
    public void run() {
        ArrayList<Coordinate> input = null;

        try {
            if (this.input == null)
                throw new StreamCorruptedException("input filter is null");

            input = this.input.read();
            if (input == null) return;
            write(input);
            epilogue();
        } catch (StreamCorruptedException e) {
            // Ignore
        }
    }

}
