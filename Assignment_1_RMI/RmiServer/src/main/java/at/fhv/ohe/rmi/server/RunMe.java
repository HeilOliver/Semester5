package at.fhv.ohe.rmi.server;

import at.fhv.ohe.rmi.server.contract.ICalc;
import at.fhv.ohe.rmi.server.implementation.Calculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RunMe {
    private static final Logger logger = LogManager.getLogger("RunMe");

    public static void main(String[] args) {
        System.setProperty("java.security.policy","file:./server.policy");
        logger.info("Start Server");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            ICalc calculator = new Calculator();

            // Take Port value of zero indicates that we don’t care
            // which port exportObject uses, which is typical
            // and so chosen dynamically.
            ICalc stub = (ICalc) UnicastRemoteObject
                    .exportObject(calculator, 0);

            // Register stub here
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("CalculatorService", stub);
        } catch (RemoteException e) {
            logger.error(e);
        }
    }
}
