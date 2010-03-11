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
package org.eclipsecon.e4rover.core;

/**
 * The contest arena has a camera mounted overhead. This interface represents
 * the image from that camera. You can retrieve the most current version of this
 * object (even if you're not the current player) by calling
 * {@link ContestPlatform#getArenaCameraImage()}. However, the best way to get
 * the latest images is to register as an {@link IUpdateListener} on
 * {@link ContestPlatform}.
 * 
 * @see ContestPlatform
 */
public interface IArenaCamImage extends IGameObject {

	public static final String TOPIC = "org/eclipsecon/e4rover/arenaimage";

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
