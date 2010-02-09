package org.eclipsecon.ebots.internal.core;

import org.eclipsecon.ebots.core.IRobot;

import com.thoughtworks.xstream.XStream;

public class Robot implements IRobot {

	private static XStream xstream;
	static {
		xstream = new XStream();
		xstream.alias("robot", Robot.class);	
	}	

	public static final Robot instance = new Robot();
	
	int batteryLevel;
	int leftWheelPower;
	int rightWheelPower;
	int leftTachoCount;
	int rightTachoCount;
	
	/**
	 * Only the server gets to use this constructor. The client always uses the
	 * fromXML method.
	 */
	Robot() {/*empty*/}
	
	static IRobot fromXML(String xml) {
		return (IRobot) xstream.fromXML(xml);
	}
	
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
	public int getLeftWheelPower() {
		return leftWheelPower;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ebots.internal.core.IRobot#getRightWheelPower()
	 */
	public int getRightWheelPower() {
		return rightWheelPower;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ebots.internal.core.IRobot#getLeftTachoCount()
	 */
	public int getLeftTachoCount() {
		return leftTachoCount;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ebots.internal.core.IRobot#getRightTachoCount()
	 */
	public int getRightTachoCount() {
		return rightTachoCount;
	}
	
	@Override
	public String toString() {
		return xstream.toXML(this);
	}
	
	public String toXML() {
		return this.toString();
	}

}
