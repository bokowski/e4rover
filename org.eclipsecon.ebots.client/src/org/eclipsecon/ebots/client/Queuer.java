package org.eclipsecon.ebots.client;

import java.io.IOException;

import org.eclipsecon.ebots.core.ContestPlatform;

public class Queuer {

	public static void main(String[] args) throws IOException {
		ContestPlatform.getDefault().enterPlayerQueue();
	}
}
