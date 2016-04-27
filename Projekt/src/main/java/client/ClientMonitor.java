package main.java.client;

import java.util.LinkedList;
import java.util.Queue;

public class ClientMonitor {
	private Queue<String> worklist;


	public ClientMonitor() {
		worklist = new LinkedList<String>();
	}

	/**
	 * Lets the OutputThread fetch work from output correalted
	 * worklist to send towards the server
	 * @param readLine
	 */
	public synchronized char[] getOutput() {
		while (worklist.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return worklist.poll().toCharArray();
	}

	/**
	 * Lets the UpdaterThread put work into the output-worklist
	 * for the OutputThread to send to server.
	 * @param readLine
	 */
	public synchronized void putOutput(String readLine) {
		// TODO Auto-generated method stub		
	}

	/**
	 * Lets the Game fetch work from input correalted worklist
	 * to propagate to the game
	 * @param readLine
	 */
	public synchronized char[] getInput() {
		return null;
	}

	/**
	 * Lets the InputThread put work into the worklist for
	 * the updater to propagate to the game/GUI
	 * @param readLine
	 */
	public synchronized void putInput(String readLine) {
		// TODO Auto-generated method stub		
	}

	public synchronized void moveLeft() {
		worklist.add("left");
		notifyAll();
	}

	public synchronized void moveRight() {
		worklist.add("right");
		notifyAll();
	}
}
