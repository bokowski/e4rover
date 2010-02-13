package org.eclipsecon.ebots.internal.core;

import org.eclipsecon.ebots.core.IDrive;

public class Drive extends ServerObject implements IDrive {
	private int velocity;
	private int radius;
	private int distance;
	
	public int getVelocity() {
		return velocity;
	}
	
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
}
