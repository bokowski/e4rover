package org.eclipsecon.ebots.client;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.UIEventHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipsecon.ebots.core.IRobot;

public class TelemetryView {

	@Inject Composite parent;
	private Text text;

	@PostConstruct
	public void init() throws IOException {
		parent.setLayout(new FillLayout());
		text = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
	}
	
	@UIEventHandler(IRobot.TOPIC)
	void queueUpdated(final IRobot robot) {
		if (parent != null && !parent.isDisposed()) {
			text.setText(robot.toString());
		}
	};

}
