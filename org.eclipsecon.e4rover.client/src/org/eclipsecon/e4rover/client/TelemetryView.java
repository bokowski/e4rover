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
package org.eclipsecon.e4rover.client;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.UIEventHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipsecon.e4rover.core.IRobot;

public class TelemetryView {

	/* SWT parent composite for this view */
	@Inject Composite parent;
	private Text text;

	/*
	 * Methods annotated with @PostConstruct will be called after all values
	 * have been injected successfully. The equivalent for in 3.x would be
	 * createPartControl().
	 */
	@PostConstruct public void init() throws IOException {
		parent.setLayout(new FillLayout());
		text = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
	}

	/*
	 * The @UIEventHandlet annotation means that an OSGi event admin listener
	 * will be registered for us, and events of the given topic will cause this
	 * method to be called. @UIEventHandler methods will be called on the UI
	 * thread - if the thread is not important, use @EventHandler. At this time,
	 * we only support payload data that is passed in the OSGi Event object
	 * under the key IEventBroker#DATA. See ContestPlatform.java for the event
	 * producer side.
	 * 
	 * This method will update the text widget with up to date information about
	 * the robot state.
	 */
	@UIEventHandler(IRobot.TOPIC) void queueUpdated(final IRobot robot) {
		if (text != null && !text.isDisposed()) {
			text.setText(robot.toString());
		}
	};

}
