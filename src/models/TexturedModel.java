package models;

import textures.ModelTexture;

/*********************************************
 * Represents a textured 3D stored in memory
 ********************************************/
public class TexturedModel
{
	private RawModel rawModel;
	private ModelTexture texture;

	public TexturedModel(RawModel model, ModelTexture texture)
	{
		this.rawModel = model;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}
}
