package main.java.game;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

import main.java.client.ClientMonitor;

public class PlayerControl extends AbstractControl {

	private int screenWidth, screenHeight;

	// is the player currently moving?
	public boolean up, left, right;
	// speed of the player
	private float speed = 400f;
	private long timer;
	private int lastMovement;
	private SnueMain sm;
	private boolean lastMoveWasRight;
    private ClientMonitor m;


	public PlayerControl(int width, int height, SnueMain sm, ClientMonitor m) {
		this.screenWidth = width;
		this.screenHeight = height;
		timer = 0;
		this.sm = sm;
        this.m = m;
		lastMovement = 0;
		lastMoveWasRight = true;
	}

	private void changePicture(int i) {
		sm.updateSpatial(i);
	}

	@Override
	protected void controlUpdate(float tpf) {
		if (left) {
			if (spatial.getLocalTranslation().x > (Float) spatial.getUserData("radius")) {
				spatial.move(tpf * -speed, 0, 0);
				m.moveLeft();
			}
			lastMoveWasRight = false;
			if (System.currentTimeMillis() - timer > 100) {
				changePicture(lastMovement + 3);
				timer = System.currentTimeMillis();
				lastMovement++;
				if (lastMovement > 2)
					lastMovement = 0;				
			}
			// spatial.rotate(0,0, -lastRotation + FastMath.PI);
			// lastRotation=FastMath.PI;
		} else if (right) {
			if (spatial.getLocalTranslation().x < screenWidth - (Float) spatial.getUserData("radius")) {
				spatial.move(tpf * speed, 0, 0);
                m.moveRight();
			}
			lastMoveWasRight = true;
			if (System.currentTimeMillis() - timer > 100) {
				changePicture(lastMovement);
				timer = System.currentTimeMillis();
				lastMovement++;
				if (lastMovement > 2)
					lastMovement = 0;				
			}
		} else if (lastMoveWasRight) {
			changePicture(0);
		} else if (!lastMoveWasRight) {
			changePicture(3);
		}
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
	}

	// reset the moving values (i.e. for spawning)
	public void reset() {
		up = false;
		left = false;
		right = false;
	}	
}
