package at.fhv.ohe.rmi.server.contract;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Assignment_1_RMI
 * at.fhv.ohe.rmi.server.contract
 * null.java
 * 22/10/2018 OliverHeil
 * <p>
 * Enter Description here
 */
public interface ICalc extends Remote {

    /**
     * Takes an Integer as argument and returns the square from it
     *
     * @param var - The Value that shout be squared
     * @return - The squared input
     */
    long calculateSquare(int var) throws RemoteException;

}
