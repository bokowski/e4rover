package org.eclipsecon.ebots.client;

import javax.inject.Inject;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.UIEventHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipsecon.ebots.core.IPlayerQueue;

public class PlayersQueueView extends Object {
	@Inject
	Composite parent;

	private Text text;

	@PostConstruct
	public void init() {
		parent.setLayout(new FillLayout());
		text = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
	}

	@UIEventHandler(IPlayerQueue.TOPIC)
	void queueUpdated(final IPlayerQueue queue) {
		if (parent != null && !parent.isDisposed()) {
			text.setText(queue.toString());
		}
	};

}
