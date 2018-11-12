package at.fhv.ohe.imageprocess.filter;

import at.fhv.pmp.filter.DataTransformationFilter2;
import at.fhv.pmp.interfaces.Readable;
import at.fhv.pmp.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import javax.media.jai.operator.ThresholdDescriptor;
import java.security.InvalidParameterException;

public class ThresholdOperator extends DataTransformationFilter2<PlanarImage, PlanarImage> {

    public ThresholdOperator(Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
    }

    public ThresholdOperator(Writeable<PlanarImage> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected PlanarImage process(PlanarImage image) {

        double[] constant = {255.0};
        double[] max = {34.0};
        double[] min = {0.0};

        image = ThresholdDescriptor.create(image, min, max, constant, null);
        return image;
    }
}
