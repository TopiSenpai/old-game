package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {

	private static final float MOVE_SPEED = 60;
	private static final float RUN_SPEED = 40;
	private static final float SIDE_SPEED = 40;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 20;
	
	
	private float currentXSpeed = 0;
	private float currentZSpeed = 0;
	private float currentTurnSpeed = 0;
	private float currentUpwardSpeed = 0;
	
	private boolean isInAir = false;
	private boolean isSprint = false;
	
	private long cd = System.currentTimeMillis();
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTime(), 0);
		float tx = currentXSpeed * DisplayManager.getFrameTime();
		float tz = currentZSpeed * DisplayManager.getFrameTime();
		float distance = (float) Math.sqrt(tx * tx + tz * tz);
		float fx = (float) (tx * Math.sin(Math.toRadians(super.getRotY())));
		float fz = (float) (tx * Math.cos(Math.toRadians(super.getRotY())));
		
		float sx = (float) (tz * Math.sin(Math.toRadians(super.getRotY()) + Math.PI / 2));
		float sz = (float) (tz * Math.cos(Math.toRadians(super.getRotY()) + Math.PI / 2));
		
		super.increasePosition(fx + sx, 0, fz + sz);
		currentUpwardSpeed += GRAVITY * DisplayManager.getFrameTime();
		super.increasePosition(0, currentUpwardSpeed * DisplayManager.getFrameTime(), 0);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y < terrainHeight) {
			currentUpwardSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
		}
		Vector3f pos = super.getPosition();
		System.out.println("Pos: " + Math.round(pos.x) + " " + Math.round(pos.y) + " " + Math.round(pos.z));
	}
	
	private void jump() {
		if(!isInAir) {
			currentUpwardSpeed = JUMP_POWER;
			isInAir = true;
		}
	}
	
	public void turn(float speed) {
		this.currentTurnSpeed = -speed;
	}
	
	public void checkInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			if(this.isSprint) {
				this.currentXSpeed = RUN_SPEED;
			}
			else {
				this.currentXSpeed = MOVE_SPEED;
			} 
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
        	this.currentXSpeed = -MOVE_SPEED;
        }
        else {
        	this.currentXSpeed = 0;
        	this.isSprint = false;
        }
		
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
        	this.currentZSpeed = -SIDE_SPEED;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
        	this.currentZSpeed = SIDE_SPEED;
        }
        else {
        	this.currentZSpeed = 0;
        }
        
        
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            jump();
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            this.isSprint = true;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
        	if(cd + 1000 < System.currentTimeMillis()) {
        		cd = System.currentTimeMillis();
        		if(Mouse.isGrabbed()) {
            		Mouse.setGrabbed(false);
            	}
            	else {
            		Mouse.setGrabbed(true);
            	}
        	}
        }
	}
	
}
