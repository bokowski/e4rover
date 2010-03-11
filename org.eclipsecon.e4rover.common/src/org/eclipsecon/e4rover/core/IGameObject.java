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

import java.util.Date;

/**
 * Base interface implemented by game objects. If the object has been retrieved
 * from the server, then the object will have a timestamp.
 */
public interface IGameObject {

	/**
	 * @return the date/time when this object was retrieved (number of
	 *         milliseconds since the Java epoch).
	 * @see System#currentTimeMillis()
	 * @see Date#Date(long)
	 */
	public long getTimestamp();
}
