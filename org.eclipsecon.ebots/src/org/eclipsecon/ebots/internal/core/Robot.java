package org.eclipsecon.ebots.internal.core;

import org.eclipsecon.ebots.core.IRobot;

public class Robot extends ServerObject implements IRobot {

	int batteryLevel;
	int leftPower;
	int rightPower;
	int leftOdom;
	int rightOdom;

	/**
	 * Clients should not call this constructor. Robot objects are returned
	 * automatically from the server and can be accessed via
	 * ContestPlatform.getRobot().
	 */
	public Robot(int batteryLevel, int leftPower, int rightPower, int leftOdom,
			int rightOdom) {
		this.batteryLevel = batteryLevel;
		this.leftPower = leftPower;
		this.rightPower = rightPower;
		this.leftOdom = leftOdom;
		this.rightOdom = rightOdom;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ebots.internal.core.IRobot#getBatteryLevel()
	 */
	public int getBatteryLevel() {
		return batteryLevel;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ebots.internal.core.IRobot#getLeftWheelPower()
	 */
	public int getLeftPower() {
		return leftPower;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ebots.internal.core.IRobot#getRightWheelPower()
	 */
	public int getRightPower() {
		return rightPower;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ebots.internal.core.IRobot#getLeftTachoCount()
	 */
	public int getLeftOdom() {
		return leftOdom;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ebots.internal.core.IRobot#getRightTachoCount()
	 */
	public int getRightOdom() {
		return rightOdom;
	}
}
