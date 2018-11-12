package at.fhv.ohe.imageprocess.viewer.view;

import javafx.scene.image.Image;

/**
 * Assignment_3_Picture
 * at.fhv.ohe.imageprocess.viewer.view
 * null.java
 * 12/11/2018 OliverHeil
 * <p>
 * Enter Description here
 */
public class StepView {

    private Image image;
    private String stepMessage;

    public StepView(Image image, String stepMessage) {
        this.image = image;
        this.stepMessage = stepMessage;
    }

    public Image getImage() {
        return image;
    }

    public String getStepMessage() {
        return stepMessage;
    }
}
