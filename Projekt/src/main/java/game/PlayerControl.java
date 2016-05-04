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
	public boolean space, left, right;
	// speed of the player
	private float speed = 400f;
	private long timer;
	private int lastMovement;
	private SnueMain sm;
	private boolean lastMoveWasRight, isJumping, goingDown;
    private ClientMonitor m;
    private int ground;
    private float speedFactor;


	public PlayerControl(int width, int height, SnueMain sm, ClientMonitor m) {
		this.screenWidth = width;
		this.screenHeight = height;
		timer = 0;
		this.sm = sm;
        this.m = m;
		lastMovement = 0;
		lastMoveWasRight = true;
		isJumping = false;
		ground = (int) (screenHeight / 2f);
		speedFactor = (float) 1.4E-4;
		goingDown = false;
	}

	private void changePicture(int i) {
		sm.updateSpatial(i);
	}

	@Override
	protected void controlUpdate(float tpf) {	
		speedFactor = tpf;
		if (left) {
			if (spatial.getLocalTranslation().x > (Float) spatial.getUserData("radius")) {
				spatial.move(tpf * -speed, 0, 0);				
				m.move((int) spatial.getLocalTranslation().x, (int) spatial.getLocalTranslation().y);				
			}
			lastMoveWasRight = false;
			if (System.currentTimeMillis() - timer > 100) {
				changePicture(lastMovement + 3);
				timer = System.currentTimeMillis();
				lastMovement++;
				if (lastMovement > 2)
					lastMovement = 0;
			}
		} else if (right) {
			if (spatial.getLocalTranslation().x < screenWidth - (Float) spatial.getUserData("radius")) {
				spatial.move(tpf * speed, 0, 0);
				m.move((int) spatial.getLocalTranslation().x, (int) spatial.getLocalTranslation().y);
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
		if (space) {			
			if (!isJumping) {
				isJumping = true;
			}
		}
	}
	
	public void jump() {
		if(!goingDown && isJumping && spatial.getLocalTranslation().y <= ground + 100) {
			spatial.move(0, speedFactor * speed, 0);
			m.move((int) spatial.getLocalTranslation().x, (int) spatial.getLocalTranslation().y);
		} else if (!goingDown && spatial.getLocalTranslation().y >= ground + 100) {
			goingDown = true;
		} else if(goingDown && spatial.getLocalTranslation().y > ground) {			
			spatial.move(0, speedFactor * -speed, 0);
			m.move((int) spatial.getLocalTranslation().x, (int) spatial.getLocalTranslation().y);
		} 
		if (spatial.getLocalTranslation().y <= ground) {
			isJumping = false;
			goingDown = false;
		}
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
	}

	// reset the moving values (i.e. for spawning)
	public void reset() {
		space = false;
		left = false;
		right = false;
	}
}
