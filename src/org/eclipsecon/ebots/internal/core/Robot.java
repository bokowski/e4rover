package org.eclipsecon.ebots.internal.core;

import java.net.URI;

import org.eclipse.core.runtime.URIUtil;
import org.eclipsecon.ebots.core.ContestPlatform;
import org.eclipsecon.ebots.core.IRobot;

public class Robot extends ServerObject implements IRobot {

	public static final String ROBOT_FILE_NAME = "robot.xml";
	public static final URI ROBOT_FILE_URI = URIUtil.append(ContestPlatform.EROVER_SERVER_URI, ROBOT_FILE_NAME);
	
	int batteryLevel;
	int leftPower;
	int rightPower;
	int leftOdom;
	int rightOdom;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ebots.internal.core.IRobot#commandWheelPower(int, int)
	 */
	public boolean commandWheelPower(int left, int right) {
		// TODO Auto-generated method stub
		return false;
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

	@Override
	protected URI getURI() {
		return ROBOT_FILE_URI;
	}
	
}
