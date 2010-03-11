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
 * Convenience class for {@link IUpdateListener}.
 */
public class UpdateAdapter implements IUpdateListener {

	public void arenaCamViewUpdated(IArenaCamImage pq) {}

	public void gameUpdated(IGame game) {}

	public void playerQueueUpdated(IPlayerQueue queue) {}

	public void playersUpdated(IPlayers players) {}

	public void robotUpdated(IRobot robot) {}

}
