package main.java.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	static final int PORT = 1337;


	public static void main(String[] args) {

		ServerSocket ss = null;
		ServerMonitor sm = new ServerMonitor();
		try {
			ss = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Socket s = null;
			while(true) {
				s = ss.accept();
				Thread it = new ServerInputThread(s, sm);
				Thread ot = new ServerOutputThread(s, sm);
				it.start();
				ot.start();
				sm.addConnection(s);
			}
//			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
