package server;

import java.net.Socket;

public class ServerMonitor {

	public synchronized void addConnection(Socket s) {
		// TODO Auto-generated method stub
		
	}

	public synchronized boolean connectionAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized void removeConnection(Socket s) {
		// TODO Auto-generated method stub
		
	}

	public synchronized void propagateAction(String readLine) {
		if (readLine != null) {
			System.out.println(readLine);
			
		}
	}

}
