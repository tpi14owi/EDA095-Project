package main.java.client;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Iterator;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import main.java.game.ActionWrapper;
import main.java.game.SnueMain;
import main.java.common.GameState;
import main.java.common.Player;

public class ClientMonitor {
	// private ArrayList<ActionWrapper> output;
	private ArrayList<ActionWrapper> actions;
	private SnueMain sm;
	private String name;
	ActionWrapper aw;
	private GameState state;
	private ArrayList<String> players;

	public ClientMonitor(String name) {
		// output = new ArrayList<ActionWrapper>();
		actions = new ArrayList<ActionWrapper>();
		this.name = name;
		players = new ArrayList<String>();
		// output.add(new ActionWrapper(name, 0, 200, 400));
		aw = new ActionWrapper(name, 0, 200, 400);
	}

	/**
	 * Lets the OutputThread fetch work from output correalted worklist to send
	 * towards the server
	 * @param readLine
	 */
	public synchronized ActionWrapper getOutput() {
		while (aw == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ActionWrapper temp = aw;
		aw = null;
		return temp;
	}

	/**
	 * Lets the Game fetch work from input correalted worklist to propagate to
	 * the game
	 * 
	 * @param readLine
	 */
	public synchronized char[] getInput() {
		return null;
	}

	public synchronized void move(int x, int y) {
		aw = new ActionWrapper(name, 1, x, y);
		notifyAll();
	}

	public synchronized void setAssetManager(SnueMain sm) {
		this.sm = sm;
	}

	public synchronized void addPlayer(String string, int x, int y) {
		actions.add(new ActionWrapper(string, 0, x, y));
	}

	public synchronized void updatePlayer(String string, int x, int y) {
		actions.add(new ActionWrapper(string, 1, x, y));
	}

	public synchronized void putWork(String string, int command, int x, int y) {
		actions.add(new ActionWrapper(string, command, x, y));
	}

	public synchronized ActionWrapper getWork() {
		if (actions.size() > 0) {
			return actions.remove(0);
		}
		return null;
	}

	public synchronized void setState(GameState state) {
		this.state = state;
		updatePlayers();
	}

	private void updatePlayers() {
		HashMap<Integer, Player> playersMap = state.getPlayers();

		Iterator itr = playersMap.entrySet().iterator();
		while(itr.hasNext()) {
			Player temp = (Player) itr.next();
			if (temp.getName().equals(name)) {
				continue;
			} else if (!players.contains(temp.getName())) { /* NEW PLAYER */
				players.add(temp.getName());
				putWork(temp.getName(), 0, temp.getX(), temp.getY());
			} else /* UPDATE OLD PLAYER */
			{
				putWork(temp.getName(), temp.getCommand(), temp.getX(), temp.getY());
			}
		}
	}
}
