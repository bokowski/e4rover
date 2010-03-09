package org.eclipsecon.ebots.client;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipsecon.ebots.core.ContestPlatform;

public class ControlView {

	@Inject
	Composite parent;
	@Inject
	ContestPlatform platform;
	@Inject
	@Named("preference-PLAYER_KEY")
	String playerKey;

	@PostConstruct
	public void init() {
		createSpacer(2);
		createButton("Fast Forward", 50, 50, 1000);
		createSpacer(2);

		createSpacer(2);
		createButton("Forward", 20, 20, 500);
		createSpacer(2);

		createButton("Hard Left", 20, -20, 500);
		createButton("Left", 5, -5, 500);
		createSpacer(1);
		createButton("Right", -5, 5, 500);
		createButton("Hard Right", -20, 20, 500);

		createSpacer(2);
		createButton("Backward", -20, -20, 500);
		createSpacer(2);

		createSpacer(2);
		createButton("Fast Backward", -50, -50, 1000);
		createSpacer(2);

		createSpacer(2);
		final Button enqueue = new Button(parent, SWT.PUSH);
		enqueue.setText("Request Control");
		enqueue.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					platform.enterPlayerQueue(playerKey);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		createSpacer(2);

		GridLayoutFactory.fillDefaults().numColumns(5).generateLayout(parent);
	}

	private void createSpacer(int count) {
		for (int i = 0; i < count; i++) {
			new Label(parent, SWT.NONE);
		}
	}

	private void createButton(String label, final int leftMotor,
			final int rightMotor, final int duration) {
		final Button forward = new Button(parent, SWT.PUSH);
		forward.setText(label);
		forward.addSelectionListener(new SelectionAdapter() {
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
