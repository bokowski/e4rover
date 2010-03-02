package org.eclipsecon.ebots.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.PreDestroy;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipsecon.ebots.core.ContestPlatform;
import org.eclipsecon.ebots.core.IArenaCamImage;
import org.eclipsecon.ebots.core.IUpdateListener;
import org.eclipsecon.ebots.core.UpdateAdapter;

public class WebcamView {
	@Inject Composite parent;
	@Inject ContestPlatform platform;

	protected Image image;
	protected IUpdateListener listener;
	
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
		
		platform.addUpdateListener(listener = new UpdateAdapter() {
			@Override
			public void arenaCamViewUpdated(IArenaCamImage img) {
				if(parent.isDisposed()) { return; }
				ImageLoader il = new ImageLoader();
				ImageData[] imageData = il.load(new ByteArrayInputStream(img.getImage()));
				image = new Image(parent.getDisplay(), imageData[0]);
				redraw();
			}});
	}

	public void redraw() {
		if(parent.isDisposed()) { return; }
		parent.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if(parent.isDisposed()) { return; }
				parent.redraw();
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
