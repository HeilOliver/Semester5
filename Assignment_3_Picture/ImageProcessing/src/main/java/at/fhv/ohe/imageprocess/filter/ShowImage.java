package at.fhv.ohe.imageprocess.filter;

import at.fhv.ohe.imageprocess.viewer.view.StepView;
import at.fhv.ohe.imageprocess.viewer.viewmodel.IStepCallBack;
import at.fhv.pmp.filter.DataTransformationFilter1;
import at.fhv.pmp.interfaces.Readable;
import at.fhv.pmp.interfaces.Writeable;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import javax.media.jai.PlanarImage;
import java.awt.image.BufferedImage;

public class ShowImage extends DataTransformationFilter1<PlanarImage> {
    private final IStepCallBack stepCallBack;

    public ShowImage(Readable<PlanarImage> input, IStepCallBack stepCallBack) {
        super(input);
        this.stepCallBack = stepCallBack;
    }

    public ShowImage(Writeable<PlanarImage> output, IStepCallBack stepCallBack) {
        super(output);
        this.stepCallBack = stepCallBack;
    }

    @Override
    protected void process(PlanarImage image) {
        String imageInfo = "Dimensions: " + image.getWidth() + "x" + image.getHeight() + " Bands:" + image.getNumBands();
        BufferedImage bufferedImage = image.getAsBufferedImage();

        WritableImage fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
        StepView stepView = new StepView(fxImage, imageInfo);
        stepCallBack.addStep(stepView);
    }
}
