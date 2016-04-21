package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ClientOutputThread extends Thread {
	private ClientMonitor monitor;
	private BufferedWriter bw;
	
	public ClientOutputThread(ClientMonitor monitor, OutputStream os) {
		this.monitor = monitor;
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}

	public void run() {
		System.out.println("Hej, jag är en OutputThread! Hej då!");
		while(true) {
			try {
				sleep((long) (Math.random() * 10000));
				System.out.println(monitor.getOutput());
				bw.write(monitor.getOutput());
				bw.flush();
			} catch (Exception e) {
				System.out.println("Couldn't write data to ServerInput");
				e.printStackTrace();
			}			
		}
	}
	
}
