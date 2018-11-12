package at.fhv.ohe.imageprocess.filter;

import at.fhv.pmp.filter.DataTransformationFilter2;
import at.fhv.pmp.interfaces.Readable;
import at.fhv.pmp.interfaces.Writeable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

public class MedianFilter extends DataTransformationFilter2<PlanarImage, PlanarImage> {

    public MedianFilter(Readable<PlanarImage> input, Writeable<PlanarImage> output) throws InvalidParameterException {
        super(input, output);
    }

    public MedianFilter(Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
    }

    public MedianFilter(Writeable<PlanarImage> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected PlanarImage process(PlanarImage image) {

        ParameterBlock parameterBlock = new ParameterBlock();
        parameterBlock.addSource(image);
        parameterBlock.add(MedianFilterDescriptor.MEDIAN_MASK_SQUARE);
        parameterBlock.add(6);

        image = JAI.create("MedianFilter", parameterBlock);

        return image;
    }
}
