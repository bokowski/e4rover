package org.eclipsecon.ebots.core;


public interface IArenaCamImage extends IGameObject {

	/**
	 * Returns JPEG image file contents captured from the game's arena camera.
	 * 
	 * To convert this into a SWT image that is suitable for display in an e4 app, do the following:
	 * 
	 * IArenaCamImage arenaCamImage = ContestPlatform.getArenaCameraImage();
	 * ImageLoader il = new ImageLoader();
	 * ImageData[] imageData = il.load(new ByteArrayInputStream(arenaCamImage.getImage()));
	 * Image image = new Image(Display.getDefault(), imageData[0]);
	 * 
	 * @return The bytes of a JPEG image
	 */
	public byte[] getImage();
}
