package main.java.game;

import java.util.ArrayList;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

import main.java.client.ClientMonitor;

public class SnueMain extends SimpleApplication implements ActionListener {
	private long bulletCooldown;
	public Node player;
	private Node bulletNode;
	private ClientMonitor m;
	private PlayerControl pc;
	private ArrayList<Texture2D> textures;
	private Picture pic;
	private ArrayList<PlayerWrapper> players;
	private boolean left; 
	private String texname;
	private Node plats;

	public SnueMain(ClientMonitor m, String name) {
		textures = new ArrayList<Texture2D>();
		players = new ArrayList<PlayerWrapper>();
		texname = name;
		this.start();
		this.m = m;
		left = false;
	}

	/**
	 * Uppdaterar GUI för Client kontinuerligt. Hämtar arbete ifrån
	 * ClientMonitor för att få en konsekvent bild av andra spelare.
	 * 
	 * @param tpf
	 */
	@Override
	public void simpleUpdate(float tpf) {
		for(Spatial plat: plats.getChildren()){	
			//Creates bounding boxes for the platforms and the player (where to put this?)
			BoundingBox bb = new BoundingBox(plat.getLocalTranslation(), textures.get(7).getImage().getWidth()/2, 
					textures.get(7).getImage().getHeight()/2, 0);
			BoundingBox pb = new BoundingBox(player.getLocalTranslation(), textures.get(0).getImage().getWidth()/2-10, 
					textures.get(0).getImage().getHeight()/2, 0);
			//Creates a vector pointing towards where the player came
			Vector3f move = player.getLocalTranslation().subtract(plat.getLocalTranslation());
			
			//While colliding, move back
			while(bb.intersects(pb)){				
				player.getControl(PlayerControl.class).collided(move.normalize());
				pb.setCenter(player.getLocalTranslation());
			}
		}
		
		player.getControl(PlayerControl.class).jump();
		ActionWrapper pw = m.getWork();
		if (pw != null) {
			int command = pw.getCommand();
			String name = pw.getId();
			int x = pw.getX();
			int y = pw.getY();
			switch (command) {
			case 0:
				createPlayer(name, x);
				break;
			case 1:
				updatePlayer(name, x, y);
				break;
			}
		}

	}

	@Override
	public void simpleInitApp() {
		
		//Gets all the textures
		textures.add((Texture2D) assetManager.loadTexture(texname + "_right.png"));
		textures.add((Texture2D) assetManager.loadTexture(texname + "_right_step1.png"));
		textures.add((Texture2D) assetManager.loadTexture(texname + "_right_step2.png"));
		textures.add((Texture2D) assetManager.loadTexture(texname+ "_left.png"));
		textures.add((Texture2D) assetManager.loadTexture(texname + "_left_step1.png"));
		textures.add((Texture2D) assetManager.loadTexture(texname + "_left_step2.png"));
		textures.add((Texture2D) assetManager.loadTexture("bullet.png"));
		textures.add((Texture2D) assetManager.loadTexture("plat.png"));
		
		plats = new Node("platform");
		guiNode.attachChild(plats);
		
		for(int i = 0; i < 1; i++){
			Spatial plat = getSpatial("platforms");
			plat.move(new Vector3f(settings.getWidth() / 2-100-10*i, settings.getHeight() / 2, 0));
			plats.attachChild(plat);
		}
			
		// setup camera for 2D games
		cam.setParallelProjection(true);
		cam.setLocation(new Vector3f(0, 0, 0.5f));
		getFlyByCamera().setEnabled(false);

		// turn off stats view (you can leave it on, if you want)
		setDisplayStatView(false);
		setDisplayFps(false);

		// setup the player
		player = getSpatial("snue_right");
		player.setUserData("alive", true);
		player.move(settings.getWidth() / 2, settings.getHeight() / 2, 0);
		guiNode.attachChild(player);

		// setup input handling
		setUpKeys();

		pc = new PlayerControl(settings.getWidth(), settings.getHeight(), this, m);
		player.addControl(pc);

		// setup bullet
		bulletNode = new Node("bullet");
		guiNode.attachChild(bulletNode);
	}

	/**
	 * Uppdaterar en spelare men motion i, ser till att spelaren animeras.
	 * 
	 * @param i
	 */
	public void updateSpatial(int i) {
		player.detachChild(pic);
		pic = new Picture("sumtin");
		Texture2D currentTex = textures.get(i);
		pic.setTexture(assetManager, currentTex, true);
		// adjust picture
		float width = currentTex.getImage().getWidth();
		float height = currentTex.getImage().getHeight();
		pic.setWidth(width);
		pic.setHeight(height);
		pic.move(-width / 2f, -height / 2f, 0);
		// add a material to the picture
		Material picMat = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
		picMat.getAdditionalRenderState().setBlendMode(BlendMode.AlphaAdditive);
		player.setMaterial(picMat);
		player.attachChild(pic);
		

	}

	/**
	 * Skapar en spatial som kopplas till GUIet.
	 * 
	 * @param name
	 * @return
	 */
	private Node getSpatial(String name) {
		Node node = new Node(name);
		// load picture
		pic = new Picture(name);
		// pc.setPic(pic);
		Texture2D tex = null;
		if (name.equals("bullet")) {
			//!!! HARDCODED (fix with map instead of list?)
			tex = textures.get(6);
		} else
		if (name.equals("platforms")) {
			tex = textures.get(7);
		} else {
			tex = textures.get(0);
		}
		pic.setTexture(assetManager, tex, true);
		// adjust picture
		float width = tex.getImage().getWidth();
		float height = tex.getImage().getHeight();
		pic.setWidth(width);
		pic.setHeight(height);
		pic.move(-width / 2f, -height / 2f, 0);
		// add a material to the picture
		Material picMat = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
		picMat.getAdditionalRenderState().setBlendMode(BlendMode.AlphaAdditive);
		node.setMaterial(picMat);
		// set the radius of the spatial
		// (using width only as a simple approximation)
		node.setUserData("radius", width / 2);
		// attach the picture to the node and return it
		node.attachChild(pic);
		return node;
	}

	/**
	 * Actionlistener för clienten.
	 * 
	 * @param name
	 * @param isPressed
	 * @param tpf
	 */
	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		if ((Boolean) player.getUserData("alive")) {
			if (name.equals("left")) {
				left = true;
				player.getControl(PlayerControl.class).left = isPressed;
			} else if (name.equals("right")) {
				left = false;
				player.getControl(PlayerControl.class).right = isPressed;
			} else if (name.equals("lcontrol")) {
				player.getControl(PlayerControl.class).lcontrol = isPressed;
			} else if (name.equals("space")) {
				player.getControl(PlayerControl.class).space = isPressed;
				// shoot Bullet
				if (System.currentTimeMillis() - bulletCooldown > 300f) {
					bulletCooldown = System.currentTimeMillis();
					Vector3f aim = getAimDirection();
					float direction = 0;
					if (left) {
						direction = -2;
					} else {
						direction = (float) 0.3;
					}
					Vector3f offset = new Vector3f(direction, (float) -0.8, 0);
					// init bullet 1
					Spatial bullet = getSpatial("bullet");
					Vector3f finalOffset = aim.add(offset).mult(20);
					Vector3f trans = player.getLocalTranslation().add(finalOffset);
					bullet.setLocalTranslation(trans);
					bullet.addControl(new BulletControl(aim, settings.getWidth(), settings.getHeight(), left));
					bulletNode.attachChild(bullet);
				}
			}
		}
	}
	
	private Vector3f getAimDirection() {
	    Vector3f playerPos = player.getLocalTranslation();
	    Vector3f dif = new Vector3f(playerPos.x,playerPos.y,0);
	    return dif.normalizeLocal();
	}	

	private void setUpKeys() {
		inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));
		inputManager.addMapping("space", new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addMapping("lcontrol", new KeyTrigger(KeyInput.KEY_LCONTROL));

		inputManager.addListener(this, "left");
		inputManager.addListener(this, "right");
		inputManager.addListener(this, "space");
		inputManager.addListener(this, "lcontrol");
	}
	/**
	 * Skapar en motståndare som anslutit sig till spelet
	 * 
	 * @param id
	 */
	private void createPlayer(String id, int x) {
		Node opponent = getSpatial(id);
		opponent.setUserData("alive", true);
		opponent.move(100, settings.getHeight() / 2, 0);
		// guiNode.attachChild(opponent);
		players.add(new PlayerWrapper(id, opponent, pic));
	}

	/**
	 * Hjälpmetod så GUIet kan uppdatera en motståndare som rört sig.
	 * 
	 * @param id
	 * @param x
	 * @param y
	 */
	private void updatePlayer(String id, int x, int y) {
		PlayerWrapper tmp = null;
		System.out.println("Trying to move: " + id + "(x,y): " + x + ", " + y);
		for (PlayerWrapper p : players) {
			if (p.getId().equals(id)) {
				tmp = p;
				break;
			}
		}
		if (tmp != null) {
			tmp.getNode().move(x - (float) tmp.getNode().getLocalTranslation().x,
					y - (float) tmp.getNode().getLocalTranslation().y, 0);
			updateOpponent(tmp, 0);
		}

	}

	/**
	 * Uppdaterar en spelare men motion i, ser till att spelaren animeras.
	 * 
	 * @param i
	 */
	private void updateOpponent(PlayerWrapper opp, int i) {
		opp.getNode().detachChild(opp.getPic());
		Picture pic = new Picture();
		Texture2D tex = textures.get(0);
		pic.setTexture(assetManager, tex, true);
		// adjust picture
		float width = tex.getImage().getWidth();
		float height = tex.getImage().getHeight();
		pic.setWidth(width);
		pic.setHeight(height);
		pic.move(-width / 2f, -height / 2f, 0);
		// add a material to the picture
		Material picMat = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
		picMat.getAdditionalRenderState().setBlendMode(BlendMode.AlphaAdditive);
		opp.getNode().setMaterial(picMat);
		opp.getNode().attachChild(pic);
		guiNode.attachChild(opp.getNode());
		opp.setPic(pic);
	}
}
