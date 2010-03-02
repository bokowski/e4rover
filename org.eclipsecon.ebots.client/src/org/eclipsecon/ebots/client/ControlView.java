package org.eclipsecon.ebots.client;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipsecon.ebots.core.ContestPlatform;
import org.eclipsecon.ebots.core.IPlayerQueue;
import org.eclipsecon.ebots.core.NotYourTurnException;

public class ControlView {

	@Inject Composite parent;
	ContestPlatform platform = ContestPlatform.getDefault();
	IPlayerQueue playerQ;
	
	@PostConstruct
	public void init() {
		new Label(parent, SWT.NONE);
		final Button forward = new Button(parent, SWT.PUSH);
		forward.setText("Forward");
		new Label(parent, SWT.NONE);

		final Button left = new Button(parent, SWT.PUSH);
		left.setText("Left");
		new Label(parent, SWT.NONE);
		final Button right = new Button(parent, SWT.PUSH);
		right.setText("Right");

		new Label(parent, SWT.NONE);
		final Button backward = new Button(parent, SWT.PUSH);
		backward.setText("Backward");
		new Label(parent, SWT.NONE);

		new Label(parent, SWT.NONE);
		final Button enqueue = new Button(parent, SWT.PUSH);
		enqueue.setText("Request Control");
		new Label(parent, SWT.NONE);

		
		Listener listener = new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if (event.widget == left) {
					System.out.println("turn left!");
					try {
						platform.setRobotWheelVelocity(-20, 20);
						Thread.sleep(250);
						platform.setRobotWheelVelocity(0, 0);

					} catch (IOException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					} catch (NotYourTurnException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					} catch (InterruptedException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					}
				} else if (event.widget == right) {
					System.out.println("turn right!");
					try {
						platform.setRobotWheelVelocity(20, -20);
						Thread.sleep(250);
						platform.setRobotWheelVelocity(0, 0);

					} catch (IOException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					} catch (NotYourTurnException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					} catch (InterruptedException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					}
				} else if (event.widget == forward) {
					System.out.println("go forward!");
					try {
						platform.setRobotWheelVelocity(50, 50);
						Thread.sleep(1000);
						platform.setRobotWheelVelocity(0, 0);
					} catch (IOException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					} catch (NotYourTurnException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					} catch (InterruptedException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					}
				} else if (event.widget == backward) {
					System.out.println("go backward!");
					try {
						platform.setRobotWheelVelocity(-50, -50);
						Thread.sleep(1000);
						platform.setRobotWheelVelocity(0, 0);
					} catch (IOException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					} catch (NotYourTurnException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					} catch (InterruptedException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					}
				} else if(event.widget == enqueue) {
					try {
						platform.enterPlayerQueue();
					} catch(IOException e) {
						System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
					}
				}
			}
		};
		left.addListener(SWT.Selection, listener);
		forward.addListener(SWT.Selection, listener);
		backward.addListener(SWT.Selection, listener);
		right.addListener(SWT.Selection, listener);
		enqueue.addListener(SWT.Selection, listener);
		GridLayoutFactory.fillDefaults().numColumns(3).generateLayout(parent);
	}
	
}
