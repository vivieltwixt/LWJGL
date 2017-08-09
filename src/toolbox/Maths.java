package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

/***********************************************
 * Handles matrix math for matrix calculations
 *********************************************/
public class Maths {

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry,
			float rz, float scale)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation,matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f (scale,scale,scale),matrix,matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera cam)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(cam.getPitch()), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(cam.getYaw()), new Vector3f(0,1,0), matrix, matrix);
		Vector3f cameraPos = cam.getPosition();
		Vector3f negativeCamPos  = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		Matrix4f.translate(negativeCamPos, matrix, matrix);
		return matrix;
	}
}
