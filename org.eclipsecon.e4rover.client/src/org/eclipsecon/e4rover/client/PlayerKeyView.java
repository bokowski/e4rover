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

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PlayerKeyView {
	@Inject Composite parent;
	@Inject IEclipsePreferences preferences;
	private Text text;
	
	@Inject void setPlayerKey(@Named("preference-PLAYER_KEY") final String playerKey) {
		parent.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (!text.getText().equals(playerKey)) {
					text.setText(playerKey);
				}
			}
		});
	}
	
	@PostConstruct
	public void init() {
		new Label(parent, SWT.NONE).setText("Player Key:");
		text = new Text(parent, SWT.SINGLE| SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				preferences.put("PLAYER_KEY", text.getText());
			}
		});
		GridLayoutFactory.fillDefaults().numColumns(2).generateLayout(parent);
	}
}
