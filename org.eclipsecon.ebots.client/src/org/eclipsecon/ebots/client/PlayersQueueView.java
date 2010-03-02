package org.eclipsecon.ebots.client;

import javax.inject.Inject;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.PreDestroy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipsecon.ebots.core.ContestPlatform;
import org.eclipsecon.ebots.core.IPlayerQueue;
import org.eclipsecon.ebots.core.IUpdateListener;
import org.eclipsecon.ebots.core.UpdateAdapter;

public class PlayersQueueView extends Object {
	@Inject Composite parent;
	@Inject ContestPlatform platform;

	protected IUpdateListener listener;
	
	@PostConstruct
	public void init() {
		parent.setLayout(new FillLayout());
		final Text text = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);

		platform.addUpdateListener(listener = new UpdateAdapter() {
			@Override
			public void playerQueueUpdated(final IPlayerQueue queue) {
				if(parent.isDisposed()) { return; }
				parent.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						if(!parent.isDisposed()) {
							text.setText(queue.toString());
						}
					}
				});
			}});		
	}
	
	@PreDestroy
	public void dispose() {
		if(listener != null) {
			platform.removeUpdateListener(listener);
		}
		listener = null;
	}

}
