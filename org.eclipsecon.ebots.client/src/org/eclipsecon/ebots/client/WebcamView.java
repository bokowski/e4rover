package org.eclipsecon.ebots.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.e4.core.services.annotations.EventHandler;
import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipsecon.ebots.core.IArenaCamImage;

public class WebcamView {
	@Inject
	Composite parent;

	protected Image image;

	@PostConstruct
	public void init() throws IOException {

		parent.setLayout(new FillLayout());
		parent.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (image != null) {
					e.gc.drawImage(image, 0, 0);
				}
			}
		});
	}

	// use a non-UI event handler so that the expensive image manipulation happens off the UI thread.
	@EventHandler(IArenaCamImage.TOPIC)
	void arenaCamViewUpdated(IArenaCamImage img) {
		if (parent.isDisposed()) {
			return;
		}
		ImageLoader il = new ImageLoader();
		ImageData[] imageData = il
				.load(new ByteArrayInputStream(img.getImage()));
		if (image != null && !image.isDisposed()) {
			Image toBeDisposed = image;
			image = null;
			toBeDisposed.dispose();
		}
		image = new Image(parent.getDisplay(), imageData[0]);
		redraw();
	};

	void redraw() {
		if (parent.isDisposed()) {
			return;
		}
		parent.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (parent.isDisposed()) {
					return;
				}
				parent.redraw();
			}
		});
	}

}
