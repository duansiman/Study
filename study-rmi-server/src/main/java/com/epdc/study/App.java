package com.epdc.study;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            IRmiService rmiService = new RmiServiceImpl();
            LocateRegistry.createRegistry(2099);
            Registry registry = LocateRegistry.getRegistry(2099);
            registry.bind("rmiService", rmiService);
            System.out.println(Arrays.toString(registry.list()));
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
