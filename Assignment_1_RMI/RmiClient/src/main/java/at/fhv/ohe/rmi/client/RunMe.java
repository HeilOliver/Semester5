package at.fhv.ohe.rmi.client;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import at.fhv.ohe.rmi.server.contract.ICalc;

import java.io.Console;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class RunMe {
    private static final Logger logger = LogManager.getLogger("RunMe");

    public static void main(String[] args) throws IOException {
        logger.info("Start Client");

        Random random = new Random();
        Registry registry = LocateRegistry.getRegistry();
        ICalc server;
        try {
            server = (ICalc) registry
                    .lookup("CalculatorService");
        } catch (NotBoundException e) {
            logger.error("Got an Error on Client Startup", e);
            return;
        }
        boolean isEnding = false;
        while (isEnding) {
            int nextValue = random.nextInt(100);
            logger.info("Start Invocation with - " + nextValue);
            try {
                long result;
                result = server.calculateSquare(nextValue);
                logger.info("Finish Invocation with" + nextValue + " get " + result);
            } catch (Exception e) {
                logger.info("Finish Invocation with" + nextValue + " but got an Error", e);
            }
        }
    }

}
