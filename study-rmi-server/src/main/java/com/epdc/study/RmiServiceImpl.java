package com.epdc.study;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class RmiServiceImpl extends UnicastRemoteObject implements IRmiService {

	protected RmiServiceImpl() throws RemoteException {
	}

	protected RmiServiceImpl(int port) throws RemoteException {
		super(port);
	}

	protected RmiServiceImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
		super(port, csf, ssf);
	}

	@Override
	public RmiResp hello(RmiParam rmiParam) throws RemoteException {
		System.out.println("rmi client into ..." + rmiParam.getValue());
		return new RmiResp("hello rmi client");
	}
}
