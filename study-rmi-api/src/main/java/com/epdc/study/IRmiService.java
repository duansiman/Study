package com.epdc.study;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRmiService extends Remote {

	RmiResp hello(RmiParam param) throws RemoteException;

}
