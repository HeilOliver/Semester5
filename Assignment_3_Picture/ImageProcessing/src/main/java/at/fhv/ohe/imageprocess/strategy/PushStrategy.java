package at.fhv.ohe.imageprocess.strategy;

import at.fhv.ohe.imageprocess.centroidfilter.CalcCentroidsFilter;
import at.fhv.ohe.imageprocess.filter.*;
import at.fhv.ohe.imageprocess.viewer.viewmodel.IStepCallBack;
import at.fhv.pmp.interfaces.Readable;

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
public class PushStrategy implements IStrategy {

    @Override
    public Set<SolderingResult> run(String input, int threshold, IStepCallBack stepCallBack) throws IOException {

        InputPicture inputPicture = new InputPicture(input);

        ShowImage showImage = new ShowImage(inputPicture, stepCallBack);

        RegionOfInterest regionOfInterest = new RegionOfInterest((Readable<PlanarImage>) showImage);
        ShowImage showImage2 = new ShowImage((Readable<PlanarImage>) regionOfInterest, stepCallBack);

        ThresholdOperator thresholdOperator = new ThresholdOperator((Readable<PlanarImage>) showImage2);
        ShowImage showImage3 = new ShowImage((Readable<PlanarImage>) thresholdOperator, stepCallBack);

        MedianFilter medianFilter = new MedianFilter((Readable<PlanarImage>) showImage3);
        ShowImage showImageMedianFilter = new ShowImage((Readable<PlanarImage>) medianFilter, stepCallBack);

        CentroidFilter centroidFilter = new CentroidFilter((Readable<PlanarImage>) showImageMedianFilter);
        ShowImage showImageBallsFilter = new ShowImage((Readable<PlanarImage>) centroidFilter, stepCallBack);

        //SaveImage saveImage = new SaveImage((Readable<PlanarImage>) showImageBallsFilter);

        CalcCentroidsFilter filterCalcCentroids = new CalcCentroidsFilter(showImageBallsFilter);

        Set<SolderingResult> result = new HashSet<>();
        OutputResult out = new OutputResult(filterCalcCentroids, threshold, result);

        out.run();

        return result;
    }

    @Override
    public String getStrategyName() {
        return "Push Strategy";
    }
}
