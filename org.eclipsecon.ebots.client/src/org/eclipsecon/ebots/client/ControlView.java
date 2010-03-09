package org.eclipsecon.ebots.client;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipsecon.ebots.core.ContestPlatform;

public class ControlView {

	@Inject
	Composite outerParent;
	
	Composite parent;
	@Inject
	ContestPlatform platform;
	@Inject
	IStylingEngine stylingEngine;
	@Inject
	@Named("preference-PLAYER_KEY")
	String playerKey;

	@PostConstruct
	public void init() {
		outerParent.setLayout(new GridLayout());
		parent = new Composite(outerParent, SWT.NONE);
		parent.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

		createSpacer(2);
		createButton("fastforward", 50, 50, 1000);
		createSpacer(2);

		createSpacer(2);
		createButton("forward", 20, 20, 500);
		createSpacer(2);

		createButton("hardleft", 20, -20, 500);
		createButton("left", 5, -5, 500);
		createSpacer(1);
		createButton("right", -5, 5, 500);
		createButton("hardright", -20, 20, 500);

		createSpacer(2);
		createButton("backward", -20, -20, 500);
		createSpacer(2);

		createSpacer(2);
		createButton("fastbackward", -50, -50, 1000);
		createSpacer(2);

		parent.setLayout(new GridLayout(5, true));
//		GridLayoutFactory.fillDefaults().numColumns(5).generateLayout(parent);
	}

	private void createSpacer(int count) {
		for (int i = 0; i < count; i++) {
			new Label(parent, SWT.NONE);
		}
	}

	private void createButton(String label, final int leftMotor,
			final int rightMotor, final int duration) {
		final Button button = new Button(parent, SWT.PUSH);
//		button.setText(label);
		stylingEngine.setClassname(button, label);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					platform.setRobotWheelVelocity(leftMotor, rightMotor,
							playerKey);
					Thread.sleep(duration);
					platform.setRobotWheelVelocity(0, 0, playerKey);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

}
