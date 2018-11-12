package at.fhv.ohe.imageprocess.filter;

import at.fhv.pmp.filter.DataTransformationFilter1;
import at.fhv.pmp.interfaces.Readable;
import at.fhv.pmp.interfaces.Writeable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.security.InvalidParameterException;

public class SaveImage extends DataTransformationFilter1<PlanarImage> {


    public SaveImage(Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
    }

    public SaveImage(Writeable<PlanarImage> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected void process(PlanarImage image) {
        JAI.create(
                "filestore", image,
                "src\\images\\loetstellen_centroids.jpg",
                "jpeg");
    }
}
