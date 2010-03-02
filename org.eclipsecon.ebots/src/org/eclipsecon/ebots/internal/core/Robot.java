package org.eclipsecon.ebots.internal.core;

import org.eclipsecon.ebots.core.IRobot;

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
