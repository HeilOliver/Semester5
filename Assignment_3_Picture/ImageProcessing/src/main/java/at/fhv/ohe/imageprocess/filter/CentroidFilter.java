package at.fhv.ohe.imageprocess.filter;

import at.fhv.pmp.filter.DataTransformationFilter2;
import at.fhv.pmp.interfaces.Readable;
import at.fhv.pmp.interfaces.Writeable;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

public class CentroidFilter extends DataTransformationFilter2<PlanarImage, PlanarImage> {


    public CentroidFilter(Writeable<PlanarImage> output) throws InvalidParameterException {
        super(output);
    }

    public CentroidFilter(Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
    }

    @Override
    protected PlanarImage process(PlanarImage image) {

        float[] kernelMatrix;
        KernelJAI kernel = null;
        kernelMatrix = new float[]
                {0, 0, 0, 1, 1, 1, 1, 0, 0, 0,
                        0, 0, 1, 1, 1, 1, 1, 1, 0, 0,
                        0, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        0, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                        0, 0, 1, 1, 1, 1, 1, 1, 0, 0,
                        0, 0, 0, 1, 1, 1, 1, 0, 0, 0};

        kernel = new KernelJAI(10, 10, kernelMatrix);

        ParameterBlock parameterBlock = new ParameterBlock();
        parameterBlock.addSource(image);
        parameterBlock.add(kernel);

        image = JAI.create("erode", parameterBlock);

        parameterBlock = new ParameterBlock();
        parameterBlock.addSource(image);
        parameterBlock.add(kernel);

        image = JAI.create("dilate", parameterBlock);

        return image;
    }
}
