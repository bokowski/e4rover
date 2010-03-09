package org.eclipsecon.ebots.client;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
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
import org.eclipsecon.ebots.core.ContestPlatform;
import org.eclipsecon.ebots.core.IPlayerQueue;

public class PlayersQueueView extends Object {
	@Inject
	Composite parent;
	@Inject
	IEclipsePreferences preferences;
	@Inject
	ContestPlatform platform;

	private Text text;
	private Text keyText;

	@PostConstruct
	public void init() {
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
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		GridLayoutFactory.fillDefaults().numColumns(2).generateLayout(parent);
	}

	@Inject
	void setPlayerKey(@Named("preference-PLAYER_KEY") final String playerKey) {
		parent.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (!keyText.getText().equals(playerKey)) {
					keyText.setText(playerKey);
				}
			}
		});
	}

	@UIEventHandler(IPlayerQueue.TOPIC)
	void queueUpdated(final IPlayerQueue queue) {
		if (parent != null && !parent.isDisposed()) {
			text.setText(queue.toString());
		}
	};

}
