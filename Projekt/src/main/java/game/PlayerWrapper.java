package main.java.game;

import com.jme3.scene.Node;

public class PlayerWrapper {
	private Node node;
	private String id;
	
	public PlayerWrapper(String id, Node node) {		
		this.id = id;
		this.node = node;
	}	
	
	public String getId() {
		return id;
	}

	public Node getNode() {
		return node;
	}

}
