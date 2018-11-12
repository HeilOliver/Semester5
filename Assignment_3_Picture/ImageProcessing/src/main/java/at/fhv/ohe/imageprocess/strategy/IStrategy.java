package at.fhv.ohe.imageprocess.strategy;

import at.fhv.ohe.imageprocess.viewer.viewmodel.IStepCallBack;

import java.io.IOException;
import java.util.Set;

/**
 * Assignment_3_Picture
 * at.fhv.ohe.imageprocess.strategy
 * null.java
 * 12/11/2018 OliverHeil
 * <p>
 * Enter Description here
 */
public interface IStrategy {

    Set<SolderingResult> run(String input, int threshold, IStepCallBack stepCallBack) throws IOException;

    String getStrategyName();
}
