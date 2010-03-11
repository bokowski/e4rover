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

import org.eclipsecon.e4rover.core.IRobot;

public class Robot extends ServerObject implements IRobot {

	int batteryLevel;
	int leftWheelVelocity;
	int rightWheelVelocity;
	int leftWheelOdometry;
	int rightWheelOdometry;

	/**
	 * Clients should not call this constructor. Robot objects are fetched
	 * automatically from the server and can be accessed via
	 * ContestPlatform.getRobot() or by listening on that class.
	 */
	public Robot(int batteryLevel, int leftWheelVelocity, int rightWheelVelocity, int leftOdom,
			int rightOdom) {
		this.batteryLevel = batteryLevel;
		this.leftWheelVelocity = leftWheelVelocity;
		this.rightWheelVelocity = rightWheelVelocity;
		this.leftWheelOdometry = leftOdom;
		this.rightWheelOdometry = rightOdom;
	}
	
	public int getBatteryLevel() {
		return batteryLevel;
	}

	public int getLeftWheelVelocity() {
		return leftWheelVelocity;
	}

	public int getRightWheelVelocity() {
		return rightWheelVelocity;
	}

	public int getLeftWheelOdometry() {
		return leftWheelOdometry;
	}

	public int getRightWheelOdometry() {
		return rightWheelOdometry;
	}

	public void setBatteryLevel(int batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public void setLeftWheelVelocity(int leftWheelVelocity) {
		this.leftWheelVelocity = leftWheelVelocity;
	}

	public void setRightWheelVelocity(int rightWheelVelocity) {
		this.rightWheelVelocity = rightWheelVelocity;
	}

	public void setLeftWheelOdometry(int leftWheelOdometry) {
		this.leftWheelOdometry = leftWheelOdometry;
	}

	public void setRightWheelOdometry(int rightWheelOdometry) {
		this.rightWheelOdometry = rightWheelOdometry;
	}
}
