package main.java.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import main.java.game.ActionWrapper;
import main.java.game.SnueMain;

public class ClientMonitor {
	private Queue<String> worklist;
	private SnueMain sm;
	private ArrayList<ActionWrapper> actions;
	
	public ClientMonitor() {
		worklist = new LinkedList<String>();	
		actions = new ArrayList<ActionWrapper>();
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

	public void setAssetManager(SnueMain sm) {
		this.sm = sm;
	}

	public void addPlayer(String string, int x, int y) {
		actions.add(new ActionWrapper(string, 0, x, y));
	}

	public void updatePlayer(String string, int x, int y) {
		actions.add(new ActionWrapper(string, 1, x, y));		
	}

	public ActionWrapper getWork() {
		if (actions.size() > 0) {
			return actions.remove(0);
		} 
		return null;		
	}	
}
