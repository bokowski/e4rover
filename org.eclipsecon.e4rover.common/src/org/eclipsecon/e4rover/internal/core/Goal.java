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

import java.util.Random;

import org.eclipsecon.e4rover.core.IGoal;

public class Goal extends ServerObject implements IGoal {

	public final TARGET target;
	public final INSTRUMENT instrument;

	static final Random rand = new Random();

	public Goal(TARGET target, INSTRUMENT instrument) {
		this.target = target;
		this.instrument = instrument;
	}

	public Goal() {
		target = TARGET.values()[rand.nextInt(3)];
		instrument = INSTRUMENT.values()[rand.nextInt(4)];
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Goal) && (((Goal)obj).target == target) && (((Goal)obj).instrument == instrument);
	}

	@Override
	public int hashCode() {
		return (target.toString() + instrument.toString()).hashCode();
	}
	
	public TARGET getTarget() {
		return target;
	}

	public INSTRUMENT getInstrument() {
		return instrument;
	}

	@Override
	public String toString() {
		return "Goal: (instrument= " + instrument + ") (target= " + target + ")";
	}
	
}
