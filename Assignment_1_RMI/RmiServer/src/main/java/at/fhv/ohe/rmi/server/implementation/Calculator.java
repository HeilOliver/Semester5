package at.fhv.ohe.rmi.server.implementation;

import at.fhv.ohe.rmi.server.contract.ICalc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Assignment_1_RMI
 * at.fhv.ohe.rmi.server.implementation
 * null.java
 * 22/10/2018 OliverHeil
 * <p>
 * Enter Description here
 */
public class Calculator implements ICalc {

    private static final Logger logger = LogManager.getLogger("Calculator");

    public long calculateSquare(int var) {
        logger.debug("Calculate square out of - " + var);
        return var * (long) var;
    }

}
