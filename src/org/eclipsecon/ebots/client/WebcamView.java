package org.eclipsecon.ebots.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class WebcamView {

	@Inject
	public WebcamView(final Composite parent) throws IOException {
		final Image[] image = new Image[1];
		parent.setLayout(new FillLayout());
		parent.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (image[0] != null) {
					e.gc.drawImage(image[0], 0, 0);
				}
			}
		});
		final URL url = new URL(
				"http://www.collineduparlement-parliamenthill.gc.ca/text/newhillcam.jpg");
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					InputStream stream;
					try {
						stream = url.openStream();
						ImageLoader il = new ImageLoader();
						ImageData[] imageData = il.load(stream);
						image[0] = new Image(parent.getDisplay(), imageData[0]);
						parent.getDisplay().asyncExec(new Runnable() {
							@Override
							public void run() {
								parent.redraw();
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(5000);
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
