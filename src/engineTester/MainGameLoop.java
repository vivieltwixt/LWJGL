package engineTester;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;


/********************************************************************************
 *  Main class, creates and renders
 *  display 
 *  
 *  Entire java program created with some modifications from Thin Matrix 3D 
 *  Game Tutorial
 *  
 *  https://www.youtube.com/playlist?list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP
 *  
 *  Modifications include addition of pizza object, texturing and adding balloon
 *  object, use of two light sources, and added music!
 ******************************************************************************/
public class MainGameLoop
{

	public static void main(String[] args)
	{
		DisplayManager.createDisplay();

		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		ModelData BalloonData = OBJFileLoader.loadOBJ("Balloon");
		ModelData pizzaData = OBJFileLoader.loadOBJ("Pizza");

		RawModel BalloonModel = loader.loadToVAO(BalloonData.getVertices(),BalloonData.getTextureCoords(),
				BalloonData.getNormals(),BalloonData.getIndices());
		RawModel pizzaModel = loader.loadToVAO(pizzaData.getVertices(),pizzaData.getTextureCoords(),
				pizzaData.getNormals(), pizzaData.getIndices());

		ModelTexture balloonTexture = new ModelTexture(loader.loadTexture("drawing"));
		ModelTexture pizzaTexture = new ModelTexture(loader.loadTexture("Pizza_Texture"));

		TexturedModel staticModelBalloon = new TexturedModel(BalloonModel,balloonTexture);
		TexturedModel staticModelPizza = new TexturedModel(pizzaModel,pizzaTexture);

		Entity leftBalloon = new Entity(staticModelBalloon, new Vector3f(-25f,0.5f,-20),0,0,0,1);
		Entity rightBalloon = new Entity(staticModelBalloon, new Vector3f(25f,0.5f,-20),0,0,0,1);
		Entity pizza = new Entity(staticModelPizza, new Vector3f(0,0.5f,-20),0,0,0,1);

		Light backLight = new Light(new Vector3f(0,0,-25),new Vector3f(1,1,1));
		Light frontLight = new Light(new Vector3f(0,0,0),new Vector3f(1,1,1));

		Camera cam = new Camera();

		//http://stackoverflow.com/questions/21143903/adding-music-to-java
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("res/mvrasseli_play_the_game_0.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		} 
		catch(UnsupportedAudioFileException | LineUnavailableException | IOException uae) {
			System.out.println(uae);
		} 


		while(!Display.isCloseRequested())
		{
			leftBalloon.increaseRotation(0,1,0);
			rightBalloon.increaseRotation(0,1,0);
			pizza.increaseRotation(1,1,0);

			renderer.prepare();

			shader.start();
			shader.loadLight(backLight);
			shader.loadLight(frontLight);
			shader.loadViewMatrix(cam);

			renderer.render(leftBalloon,shader);
			renderer.render(rightBalloon,shader);
			renderer.render(pizza, shader);

			shader.stop();

			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
