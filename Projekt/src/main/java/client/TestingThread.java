package main.java.client;

import com.jme3.scene.Node;

public class TestingThread extends Thread {
	private ClientMonitor cm;
	private int x;
	private int y;

	public TestingThread(ClientMonitor cm) {
		this.cm = cm;
		x = 0;
		y = 400;
	}

	public void run() {
		try {
		sleep(2000);
		cm.addPlayer("Opponent", x, y);
		while(!isInterrupted()) {
				System.out.println("Hej nu sover jag");
				sleep(2000);
				System.out.println("Hej nu vaknade jag");
				cm.updatePlayer("Opponent", x, 0);
				x += 50;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
