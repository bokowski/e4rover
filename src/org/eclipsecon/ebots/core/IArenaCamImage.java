package org.eclipsecon.ebots.core;

public interface IArenaCamImage extends IGameObject {

	/**
	 * Returns an image captured from the game's arena camera.
	 * @return The bytes of a JPEG image
	 */
	public byte[] getImage();
}
