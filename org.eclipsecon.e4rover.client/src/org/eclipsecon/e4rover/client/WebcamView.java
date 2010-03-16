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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.e4.core.services.annotations.EventHandler;
import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipsecon.e4rover.core.IArenaCamImage;

public class WebcamView {

	/* SWT parent composite for this view */
	@Inject Composite parent;

	protected Image image;

	/*
	 * Methods annotated with @PostConstruct will be called after all values
	 * have been injected successfully. The equivalent for in 3.x would be
	 * createPartControl().
	 */
	@PostConstruct public void init() throws IOException {

		parent.setLayout(new FillLayout());
		parent.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (image != null) {
					e.gc.drawImage(image, 0, 0);
				}
			}
		});
	}

	/*
	 * The @EventHandlet annotation means that an OSGi event admin listener will
	 * be registered for us, and events of the given topic will cause this
	 * method to be called. @EventHandler methods will be called on any thread -
	 * use @UIEventHandler if the call should be on the UI thread. At this time,
	 * we only support payload data that is passed in the OSGi Event object
	 * under the key IEventBroker#DATA. See ContestPlatform.java for the event
	 * producer side.
	 * 
	 * This method will update the current image and cause a redraw. Using a
	 * non-UI event handler so that the expensive image manipulation happens off
	 * the UI thread.
	 */
	@EventHandler(IArenaCamImage.TOPIC) void arenaCamViewUpdated(IArenaCamImage img) {
		if (parent.isDisposed()) {
			return;
		}
		ImageLoader il = new ImageLoader();
		ImageData[] imageData = il.load(new ByteArrayInputStream(img.getImage()));
		if (image != null && !image.isDisposed()) {
			Image toBeDisposed = image;
			image = null;
			toBeDisposed.dispose();
		}
		image = new Image(parent.getDisplay(), imageData[0]);
		redraw();
	};

	void redraw() {
		if (parent.isDisposed()) {
			return;
		}
		parent.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (parent.isDisposed()) {
					return;
				}
				parent.redraw();
			}
		});
	}

}
