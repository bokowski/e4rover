package org.eclipsecon.ebots.client;

import javax.inject.Inject;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.UIEventHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipsecon.ebots.core.IPlayers;

public class PlayersView {
	@Inject
	Composite parent;
	private Text text;

	@PostConstruct
	public void init() {
		parent.setLayout(new FillLayout());
		text = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
	}

	@UIEventHandler(IPlayers.TOPIC)
	void queueUpdated(final IPlayers players) {
		if (parent != null && !parent.isDisposed()) {
			text.setText(players.toString());
		}
	};

}
