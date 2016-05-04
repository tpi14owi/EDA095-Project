package main.java.common;

import java.io.Serializable;
import java.util.HashMap;


public class GameState implements Serializable {
	private HashMap<Integer, Player> players;

	public GameState() {

	}

	public HashMap<Integer, Player> getPlayers() {
		return players;
	}

	public void setState(int i, int x, int y) {
		players.get(i).setX(x);
		players.get(i).setY(y);
	}
}



