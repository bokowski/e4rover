/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipsecon.ebots.core;

import java.io.IOException;

import java.net.URI;

/**
 * The game service can be used to instantiate robot challenge games running
 * on some remote server.
 */
public interface IGameService {
	/**
	 * Instantiates a new game instance using the provided game server location. 
	 * Although the game instance returned is new, the game itself may be ongoing,
	 * so no assumptions can be made by the client about the current state of the game instance
	 * at time of creation.
	 * 
	 * @param server The server hosting the game.
	 * @return A new game instance representing a robot game running on the given server
	 * @exception IOException If communication problems occurred while connecting
	 * or transferring data from the server
	 */
	public IGame createGame(URI server) throws IOException;
}
