package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Lamp extends Entity {

	private Light light;
	
	public Lamp(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, index, position, rotX, rotY, rotZ, scale);
		light = new Light(new Vector3f(position.x, position.y + 13, position.z), new Vector3f(2, 2, 0), new Vector3f(0.5f, 0.01f, 0.002f));
	}
	
	

}
