import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

public class SnueMain extends SimpleApplication implements ActionListener {
	private long bulletCooldown;
	public Node player;
	private Node bulletNode;
	private Picture pic;
	private int lastMovement;
	private static long timer;
	private PlayerMonitor pm;

	public static void main(String[] args) {
		SnueMain app = new SnueMain();
		PlayerMonitor pm = new PlayerMonitor();
		app.setMonitor(pm);
		app.start();
		// (new PlayerUpdaterThread(pm)).start();
		timer = 0;
	}

	public void setMonitor(PlayerMonitor pm) {
		this.pm = pm;
	}

	@Override
	public void simpleInitApp() {
		lastMovement = 0;

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
		inputManager.addMapping("mousePick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(this, "mousePick");

		inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));

		inputManager.addListener(this, "left");
		inputManager.addListener(this, "right");

		player.addControl(new PlayerControl(settings.getWidth(), settings.getHeight()));
	}

	@Override
	public void simpleUpdate(float tpf) {

	}

	@Override
	public void simpleRender(RenderManager rm) {

	}

	public void updateSpatial(String name, Node player) {
		player.detachChild(pic);

		pic = new Picture(name);
		Texture2D tex = (Texture2D) assetManager.loadTexture(name + ".png");
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
		player.setMaterial(picMat);

		player.attachChild(pic);
	}

	private Node getSpatial(String name) {
		Node node = new Node(name);
		// load picture
		pic = new Picture(name);
		Texture2D tex = (Texture2D) assetManager.loadTexture(name + ".png");
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

	@Override
	/**
	 * PROBLEMET: Räknar bara ett knapptryck, samt när detta släpps. Behöver
	 * kontinuerlig kontroll på nedtryckt knapp
	 */
	public void onAction(String name, boolean isPressed, float tpf) {
		if ((Boolean) player.getUserData("alive")) {
			if (name.equals("left")) {
				player.getControl(PlayerControl.class).left = isPressed;				
				updateSpatial("snue_left", player);
			} else if (name.equals("right")) {
				player.getControl(PlayerControl.class).right = isPressed;
				updateSpatial("snue_right", player);
			}
		}
		pm.setRightPressed(player.getControl(PlayerControl.class).right);
		pm.setLeftPressed(player.getControl(PlayerControl.class).left);
	}
}