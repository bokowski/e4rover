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

import java.util.Collections;
import java.util.List;

import org.eclipsecon.e4rover.core.IPlayerQueue;

public class PlayerQueue extends ServerObject implements IPlayerQueue {
	final List<String> playerQueue;
	
	public PlayerQueue(List<String> playerQueue) {
		this.playerQueue = playerQueue;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.eclipsecon.e4rover.internal.core.IPlayers#getPlayerQueue()
	 */
	public List<String> getPlayerQueue() {
		return Collections.unmodifiableList(playerQueue);
	}

}
