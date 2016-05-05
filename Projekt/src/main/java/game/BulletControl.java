package main.java.game;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class BulletControl extends AbstractControl {
    private int screenWidth, screenHeight;  
    public Vector3f direction;  
    private boolean left;
 
    public BulletControl(Vector3f direction, int screenWidth, int screenHeight, boolean left) {
        this.direction = direction;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.left = left;
    }
 
    @Override
    protected void controlUpdate(float tpf) {
//        movement
    	if (!left) {
    		spatial.move((float) 0.12,0,0); 
    	} else {
    		spatial.move((float) -0.12,0,0); 
    	}
 
//        check boundaries
        Vector3f loc = spatial.getLocalTranslation();
        if (loc.x > screenWidth || 
            loc.y > screenHeight ||
            loc.x < 0 ||
            loc.y < 0) {
            spatial.removeFromParent();
        }
    }
 
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
}