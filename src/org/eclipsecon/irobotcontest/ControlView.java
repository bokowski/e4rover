package org.eclipsecon.irobotcontest;

import javax.inject.Inject;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class ControlView {

	@Inject
	public void ControlView(Composite parent) {
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
		Listener listener = new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if (event.widget == left) {
					System.out.println("turn left!");
				} else if (event.widget == right) {
					System.out.println("turn right!");
				} else if (event.widget == forward) {
					System.out.println("go forward!");
				} else if (event.widget == backward) {
					System.out.println("go backward!");
				}
			}
		};
		left.addListener(SWT.Selection, listener);
		forward.addListener(SWT.Selection, listener);
		backward.addListener(SWT.Selection, listener);
		right.addListener(SWT.Selection, listener);
		GridLayoutFactory.fillDefaults().numColumns(3).generateLayout(parent);
	}
}
