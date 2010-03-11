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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipsecon.e4rover.core.IGameObject;
import org.eclipsecon.e4rover.core.XmlSerializer;

/**
 * Base level server object.
 */
public class ServerObject implements IGameObject {
	protected transient long timestamp;
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp; 
	}
	
	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss.SSS");	// expensive but worth it
		return "<!-- timestamp: " + formatter.format(new Date(timestamp)) + " -->\n" +
			new XmlSerializer().toXML(this);
	}
}
