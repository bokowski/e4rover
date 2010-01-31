package org.eclipsecon.ebots.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class GameView {

	@Inject
	public GameView(final Composite parent) throws IOException {
		parent.setLayout(new FillLayout());
		final Text text = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
		final URL url = new URL(
				"http://ebots.s3.amazonaws.com/game.xml");
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						InputStream stream = url.openStream();
						byte[] buffer = new byte[4096];
						int len;
						final StringBuffer content = new StringBuffer();
						while ((len = stream.read(buffer, 0, buffer.length)) > 0) {
							content.append(new String(buffer, 0, len));
						}
						parent.getDisplay().asyncExec(new Runnable() {
							@Override
							public void run() {
								text.setText(content.toString());
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(100000);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		};
		t.setDaemon(true);
		t.start();
	}
}
