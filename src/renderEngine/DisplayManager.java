package renderEngine;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;


/**********************************
 * Creates general window display 
 **********************************/
public class DisplayManager {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static int FPS_CAP = 120;

	public static void createDisplay()
	{
		ContextAttribs attribs = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCore(true);

		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			attribs.withForwardCompatible(true).withProfileCompatibility(true).withProfileCore(true);
			Display.setTitle("                                                                                                           Pizza Party President!");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}	

		GL11.glViewport(0,0,WIDTH,HEIGHT);
	}

	public static void updateDisplay()
	{
		Display.sync(FPS_CAP);
		Display.update();
	}

	public static void closeDisplay()
	{
		Display.destroy();
	}
}
