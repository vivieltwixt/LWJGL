package renderEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import models.RawModel;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/***********************************************
 * Loads 3D model into memory by storing
 * positional data about the model in VAO
 ***********************************************/
public class Loader {

	private List<Integer> vaos =  new ArrayList<Integer>();
	private List<Integer> vbos =  new ArrayList<Integer>();
	private List<Integer> textures =  new ArrayList<Integer>();

	// Take in positions of the model's vertices data, loads this data into a VAO, and returns info about the VAO in the package of a Rawmodel
	public RawModel loadToVAO(float[] positions,float[] textureCoords,float[] normals, int[] indices)
	{
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0,3, positions);
		storeDataInAttributeList(1,2, textureCoords);
		storeDataInAttributeList(2,3, normals);
		unbindVAO();
		return new RawModel(vaoID,indices.length);
	}

	public int loadTexture(String fileName)
	{
		Texture texture = null;
		try
		{
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}
	
	// Deletes all vaos, vbos, and textures 
	public void cleanUp()
	{
		for(int vao:vaos)
		{
			GL30.glDeleteVertexArrays(vao);
		}

		for(int vbo:vbos)
		{
			GL15.glDeleteBuffers(vbo);
		}
		
		for(int texture:textures)
		{
			GL11.glDeleteTextures(texture);
		}
	}

	// Creates a new empty VAO and returns its ID
	private int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	// Stores data into one of VAO's attribute list slots
	// Slot is indicated by attributeNumber
	private void storeDataInAttributeList(int attributeNumber,int coordSize, float[] data)
	{
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber,coordSize, GL11.GL_FLOAT, false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private void unbindVAO()
	{
		GL30.glBindVertexArray(0);
	}

	// Loads an indices buffer and binds it to a vbo
	private void bindIndicesBuffer(int[] indices)
	{
		int vboID = GL15.glGenBuffers();	// Create a vbo object
		vbos.add(vboID);	//add vbo to list so that it may be deleted later
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID); //bind vbo to the current bound vao 
		IntBuffer buffer = storeDataInIntBuffer(indices);  //transform int data int intbuffer so that vbo may store it
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);  // store buffer data into vbo
	}

	private IntBuffer storeDataInIntBuffer(int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	// Converts float data into float buffer, the form that VBO stores
	private FloatBuffer storeDataInFloatBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
