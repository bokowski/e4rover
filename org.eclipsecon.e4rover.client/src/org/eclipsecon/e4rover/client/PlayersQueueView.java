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
import javax.inject.Named;
import javax.inject.Provider;

import org.apache.commons.httpclient.HttpStatus;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.services.StatusReporter;
import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.UIEventHandler;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipsecon.e4rover.core.ContestPlatform;
import org.eclipsecon.e4rover.core.IPlayerQueue;
import org.eclipsecon.e4rover.core.RobotServerException;

public class PlayersQueueView extends Object {
	/* SWT parent composite for this view */
	@Inject Composite parent;

	// This is being used to set the PLAYER_KEY preference.
	@Inject IEclipsePreferences preferences;

	/*
	 * ContestPlatform is a domain-specific interface that is registered as an
	 * OSGi declarative service (see the ContestPlatform.java and
	 * contestplatform.xml files). OSGi services (declarative or not) are
	 * available to e4 parts through dependency injection.
	 */
	@Inject ContestPlatform platform;

	/*
	 * Injecting a javax.inject.Provider instead of the status reporter itself
	 * means that the instance can be accessed lazily through Provider.get().
	 * This was not necessary for this view to work, we just wanted to
	 * demonstrate an exemplary use of Provider - the status reporter instance
	 * will only be needed if we need handle an exception.
	 */
	@Inject Provider<StatusReporter> statusReporter;

	private Text text;
	private Text keyText;

	/*
	 * Methods annotated with @PostConstruct will be called after all values
	 * have been injected successfully. The equivalent for in 3.x would be
	 * createPartControl().
	 */
	@PostConstruct public void init() {
		text = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
		GridDataFactory.defaultsFor(text).span(2, 1).applyTo(text);
		new Label(parent, SWT.NONE).setText("Player Key:");
		keyText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		keyText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				preferences.put("PLAYER_KEY", keyText.getText());
			}
		});
		final Button enqueue = new Button(parent, SWT.PUSH);
		enqueue.setText("Request Control");
		enqueue.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					platform.enterPlayerQueue(keyText.getText());
				} catch (RobotServerException rse) {
					if (rse.getResponseCode() == HttpStatus.SC_CONFLICT) {
						statusReporter.get().show(StatusReporter.ERROR, "Player queue full!  Please try again later.",
								null);
					} else {
						statusReporter.get().show(StatusReporter.ERROR, "An error occurred when requesting control",
								rse);
					}
				} catch (IOException e1) {
					statusReporter.get().show(StatusReporter.ERROR, "An error occurred when requesting control", e1);
				}
			}
		});

		final Button dequeue = new Button(parent, SWT.PUSH);
		dequeue.setText("Leave Queue");
		dequeue.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					platform.leavePlayerQueue(keyText.getText());
				} catch (IOException e1) {
					statusReporter.get().show(StatusReporter.ERROR, "An error occurred when leaving queue", e1);
				}
			}
		});

		GridLayoutFactory.fillDefaults().numColumns(2).generateLayout(parent);
	}

	/*
	 * The value of the "PLAYER_KEY" preference will be injected here. Any time
	 * the preference value changes, this method will be called again. (Behind
	 * the scenes, a preference listener will be managed by the framework.) You
	 * can use field injection instead of method injection if no code needs to
	 * run in response to preference changes.
	 * 
	 * [Note that the weird 'preference' prefix is not going to be the final
	 * API, we are planning to replace the generic @Named annotation below with
	 * a more specific annotation @Preference("PLAYER_KEY") in the final API.]
	 */
	@Inject void setPlayerKey(@Named("preference-PLAYER_KEY") final String playerKey) {
		parent.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (!keyText.getText().equals(playerKey)) {
					keyText.setText(playerKey);
				}
			}
		});
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
	 * the player queue.
	 */
	@UIEventHandler(IPlayerQueue.TOPIC) void queueUpdated(final IPlayerQueue queue) {
		if (parent != null && !parent.isDisposed()) {
			text.setText(queue.toString());
		}
	};

}
