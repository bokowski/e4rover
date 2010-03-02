package org.eclipsecon.ebots.client;

import javax.inject.Inject;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.PreDestroy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipsecon.ebots.core.ContestPlatform;
import org.eclipsecon.ebots.core.IPlayers;
import org.eclipsecon.ebots.core.IUpdateListener;
import org.eclipsecon.ebots.core.UpdateAdapter;

public class PlayersView {
	@Inject Composite parent;
	protected IUpdateListener listener;
	
	@PostConstruct
	public void init() {
		parent.setLayout(new FillLayout());
		final Text text = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);

		ContestPlatform cp = ContestPlatform.getDefault();
		cp.addUpdateListener(listener = new UpdateAdapter() {
			
			@Override
			public void playersUpdated(final IPlayers players) {
				if(parent.isDisposed()) { return; }
				parent.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						if(!parent.isDisposed()) {
							text.setText(players.toString());
						}
					}
				});
			}});		
	}
	
	@PreDestroy
	public void dispose() {
		if(listener != null) {
			ContestPlatform cp = ContestPlatform.getDefault();
			cp.removeUpdateListener(listener);
		}
		listener = null;
	}

}
