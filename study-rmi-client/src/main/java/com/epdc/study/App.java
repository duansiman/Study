package com.epdc.study;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
//            Registry registry = LocateRegistry.getRegistry(2099);
//            IRmiService rmiService = (IRmiService) registry.lookup("rmiService");
//            RmiResp rmiResp = rmiService.hello(new RmiParam("hello rmi server"));
//            System.out.println(rmiResp.getValue());

            Context context = new InitialContext();
            IRmiService rmiService = (IRmiService) context.lookup("rmi://localhost:2099/rmiService");
            RmiResp rmiResp = rmiService.hello(new RmiParam("hello rmi server"));
            System.out.println(rmiResp.getValue());
            TimeUnit.DAYS.sleep(1L);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
