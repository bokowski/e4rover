package org.eclipsecon.ebots.internal.core;

import org.eclipsecon.ebots.core.IArenaCamImage;

public class ArenaCamImage extends ServerObject implements IArenaCamImage {
	protected byte[] image;
	
	public ArenaCamImage(byte[] imageBytes) {
		this.image = imageBytes;
	}
	
	public byte[] getImage() {
		return image;
	}

}
