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

import org.eclipsecon.e4rover.core.IPlayer;

public class Player extends ServerObject implements IPlayer {

	private final String name;
	private int timesPlayed;
	private int highScore;

	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public int getPlayCount() {
		return timesPlayed;
	}

	public void incrementPlayCount() {
		timesPlayed++;
	}
	
	public void setTimesPlayed(int timesPlayed) {
		this.timesPlayed = timesPlayed;
	}

	public int compareTo(IPlayer o) {
		int diff =  o.getHighScore() - highScore;
		if (diff == 0)
			return o.getName().compareTo(getName());
		return diff; 
	}

	public void updateHighScore(int latestScore) {
		if (highScore < latestScore)
			highScore = latestScore;
	}
}


