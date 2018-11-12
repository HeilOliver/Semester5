package at.fhv.ohe.imageprocess.strategy;

/**
 * Assignment_3_Picture
 * at.fhv.ohe.imageprocess.strategy
 * null.java
 * 12/11/2018 OliverHeil
 * <p>
 * Enter Description here
 */
public class SolderingResult {
    private int number;
    private boolean isOk;
    private double toleranceX;
    private double toleranceY;

    public SolderingResult(int number, boolean isOk, double toleranceX, double toleranceY) {
        this.number = number;
        this.isOk = isOk;
        this.toleranceX = toleranceX;
        this.toleranceY = toleranceY;
    }

    public int getNumber() {
        return number;
    }

    public boolean isOk() {
        return isOk;
    }

    public double getToleranceX() {
        return toleranceX;
    }

    public double getToleranceY() {
        return toleranceY;
    }
}

