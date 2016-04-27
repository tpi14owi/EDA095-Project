package main.java.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerInputThread extends Thread {
	private Socket s;
	private ServerMonitor sm;
	private InputStream is;
	private BufferedReader ir;

	public ServerInputThread(Socket s, ServerMonitor sm) {
		this.s = s;
		this.sm = sm;
	}

	public void run() {

		try {
			is = s.getInputStream();
			ir = new BufferedReader(new InputStreamReader(is));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (s.isConnected()) {
			try {
//				sm.propagateAction(ir.readLine());
				ir.readLine();
			} catch (IOException e) {
				System.out.println("FWWWL");
				e.printStackTrace();
			}
		}
		sm.removeConnection(s);
	}
}
