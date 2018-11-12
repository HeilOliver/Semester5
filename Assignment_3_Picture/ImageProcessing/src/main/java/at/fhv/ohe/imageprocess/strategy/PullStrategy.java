package at.fhv.ohe.imageprocess.strategy;

import at.fhv.ohe.imageprocess.centroidfilter.CalcCentroidsFilter;
import at.fhv.ohe.imageprocess.filter.*;
import at.fhv.ohe.imageprocess.viewer.viewmodel.IStepCallBack;
import at.fhv.pmp.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Assignment_3_Picture
 * at.fhv.ohe.imageprocess.strategy
 * null.java
 * 12/11/2018 OliverHeil
 * <p>
 * Enter Description here
 */
public class PullStrategy implements IStrategy {
    @Override
    public Set<SolderingResult> run(String input, int threshold, IStepCallBack stepCallBack) throws IOException {
        Set<SolderingResult> results = new HashSet<>();

        OutputResult outputResult = new OutputResult(results, threshold);


        CalcCentroidsFilter filterCalcCentroids = new CalcCentroidsFilter(outputResult);

        //SaveImage saveImage = new SaveImage(filterCalcCentroids);
        ShowImage showImage4 = new ShowImage(filterCalcCentroids, stepCallBack);

        CentroidFilter centroidFilter = new CentroidFilter((Writeable<PlanarImage>) showImage4);

        MedianFilter medianFilter = new MedianFilter((Writeable<PlanarImage>) centroidFilter);

        ShowImage showImage = new ShowImage((Writeable<PlanarImage>) medianFilter, stepCallBack);

        ThresholdOperator thresholdOperator = new ThresholdOperator((Writeable<PlanarImage>) showImage);

        ShowImage showImage2 = new ShowImage((Writeable<PlanarImage>) thresholdOperator, stepCallBack);

        RegionOfInterest regionOfInterest = new RegionOfInterest((Writeable<PlanarImage>) showImage2);

        ShowImage showImage3 = new ShowImage((Writeable<PlanarImage>) regionOfInterest, stepCallBack);

        InputPicture inputPicture = new InputPicture(showImage3, input);

        if (inputPicture != null) {

            inputPicture.run();
        }
        return results;
    }

    @Override
    public String getStrategyName() {
        return "Pull Strategy";
    }
}
