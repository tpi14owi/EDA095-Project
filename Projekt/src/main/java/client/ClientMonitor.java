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
	private ArrayList<ActionWrapper> output;
	private SnueMain sm;
	private ArrayList<ActionWrapper> actions;
	private String name;

	public ClientMonitor(String name) {
		output = new ArrayList<ActionWrapper>();
		actions = new ArrayList<ActionWrapper>();
		this.name = name;
		output.add(new ActionWrapper(name, 0, 200, 400));	
	}

	/**
	 * Lets the OutputThread fetch work from output correalted
	 * worklist to send towards the server
	 * @param readLine
	 */
	public synchronized ActionWrapper getOutput() {
		while (output.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return output.remove(0);
	}
	

	/**
	 * Lets the Game fetch work from input correalted worklist
	 * to propagate to the game
	 * @param readLine
	 */
	public synchronized char[] getInput() {
		return null;
	}

	public synchronized void move(int x, int y) {
		output.add(new ActionWrapper(name, 1, x, y));
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

	public void putWork(String string, int command, int x, int y) {
		actions.add(new ActionWrapper(string, command, x, y));
	}

	public ActionWrapper getWork() {
		if (actions.size() > 0) {
			return actions.remove(0);
		}
		return null;
	}
}
