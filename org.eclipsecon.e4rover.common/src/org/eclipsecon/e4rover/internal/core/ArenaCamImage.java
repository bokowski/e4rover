/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/
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
