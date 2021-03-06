package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/************************************
 * Represents viewpoint of camera
 ************************************/
public class Camera {
	private Vector3f position = new Vector3f(0,0,50);
	private float pitch;
	private float yaw;
	private float roll;


	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
}
