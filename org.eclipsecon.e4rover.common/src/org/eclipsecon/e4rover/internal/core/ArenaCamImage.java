package org.eclipsecon.e4rover.internal.core;

import org.eclipsecon.e4rover.core.IArenaCamImage;

public class ArenaCamImage extends ServerObject implements IArenaCamImage {
	protected byte[] image;
	
	public ArenaCamImage(byte[] imageBytes) {
		this.image = imageBytes;
	}
	
	public byte[] getImage() {
		return image;
	}

}
