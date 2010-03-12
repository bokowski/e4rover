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

import org.eclipse.e4.core.services.annotations.Optional;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.e4.workbench.ui.IExceptionHandler;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipsecon.e4rover.core.ContestPlatform;

/**
 * A command handler that initiates a request for this player
 * to take control of the robot.
 */
public class RequestControlHandler {
	@Inject
	@Optional	// since it may not be set 
	@Named("preference-PLAYER_KEY")
	String playerKey;
	
	@Inject
	ContestPlatform platform;
	
	@Inject
	IExceptionHandler handler;
	
	@Inject
	IStylingEngine stylingEngine;

	@Optional	// since may be set up before there's a shell
	@Inject
	Shell shell;

	public boolean canExecute() {
 		if(playerKey == null || playerKey.trim().isEmpty()) {
 			// FIXME: This should use IStatusHandler
			MessageDialog dialog = new MessageDialog(shell, 
					"Unable to register", null,
					"Please provide your player key",
					MessageDialog.ERROR,
					new String[] { IDialogConstants.OK_LABEL },
					SWT.SHEET);
			dialog.create();
            if (dialog.getShell().getBackgroundMode() == SWT.INHERIT_NONE) {
            	dialog.getShell().setBackgroundMode(SWT.INHERIT_DEFAULT);
            }
			stylingEngine.style(dialog);
			dialog.open();
			return false;
		}
		// we could check to see if we're already in the queue
		return true;
	}
	
	public void execute() {
		try {
			platform.enterPlayerQueue(playerKey);
		} catch (IOException e) {
			handler.handleException(e);
		}
	}
}
