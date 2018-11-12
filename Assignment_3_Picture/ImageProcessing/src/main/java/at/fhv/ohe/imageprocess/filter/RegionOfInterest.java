package at.fhv.ohe.imageprocess.filter;

import at.fhv.pmp.filter.DataTransformationFilter2;
import at.fhv.pmp.interfaces.Readable;
import at.fhv.pmp.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.security.InvalidParameterException;

public class RegionOfInterest extends DataTransformationFilter2<PlanarImage, PlanarImage> {

    public RegionOfInterest(Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
    }

    public RegionOfInterest(Writeable<PlanarImage> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected PlanarImage process(PlanarImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Rectangle rectangle = new Rectangle(10, 50, width - 12, height - 200);
        image = PlanarImage.wrapRenderedImage(image.getAsBufferedImage(rectangle, image.getColorModel()));
        image.setProperty("offsetX", 10);
        image.setProperty("offsetY", 50);
        return image;
    }

}