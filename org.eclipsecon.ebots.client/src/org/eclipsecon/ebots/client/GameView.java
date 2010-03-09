package org.eclipsecon.ebots.client;

import javax.inject.Inject;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.UIEventHandler;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IGoal.INSTRUMENT;
import org.eclipsecon.ebots.core.IGoal.TARGET;

public class GameView {
	@Inject
	Composite parent;
	private Text timestampText;
	private Text playerText;
	private Label secondsLabel;
	private Text secondsText;
	private Text scoreText;
	private Text targetRockText;
	private Text targetInstrumentText;
	private Text rewardText;
	private Label targetRockLabel;
	private Label targetInstrumentLabel;
	private Label rewardLabel;
	private Control[] controls;

	@Inject IStylingEngine stylingEngine;
	
	@PostConstruct
	public void init() {
		new Label(parent, SWT.NONE).setText("Timestamp:");
		timestampText = new Text(parent, SWT.READ_ONLY);
		stylingEngine.setClassname(timestampText, "foo");
		new Label(parent, SWT.NONE).setText("Player:");
		playerText = new Text(parent, SWT.READ_ONLY);
		new Label(parent, SWT.NONE).setText("Score:");
		scoreText = new Text(parent, SWT.READ_ONLY);
		secondsLabel = new Label(parent, SWT.NONE);
		secondsLabel.setText("Remaining seconds:");
		secondsText = new Text(parent, SWT.READ_ONLY);
		targetRockLabel = new Label(parent, SWT.NONE);
		targetRockLabel.setText("Target rock:");
		targetRockText = new Text(parent, SWT.READ_ONLY);
		targetInstrumentLabel = new Label(parent, SWT.NONE);
		targetInstrumentLabel.setText("Target instrument:");
		targetInstrumentText = new Text(parent, SWT.READ_ONLY);
		rewardLabel = new Label(parent, SWT.NONE);
		rewardLabel.setText("Reward:");
		rewardText = new Text(parent, SWT.READ_ONLY);
		// for layout purposes, remember all controls
		controls = new Control[] { timestampText, playerText, scoreText,
				secondsLabel, secondsText, targetRockLabel, targetRockText,
				targetInstrumentLabel, targetInstrumentText, rewardLabel,
				rewardText };
		GridLayoutFactory.fillDefaults().numColumns(2).generateLayout(parent);
	}

	@UIEventHandler(IGame.TOPIC)
	void gameUpdated(final IGame game) {
		if (parent != null && !parent.isDisposed()) {
			timestampText.setText("" + game.getTimestamp());
			String playerName = game.getPlayerName();
			playerText.setText(playerName == null ? "(nobody)" : playerName);
			scoreText.setText("" + game.getScore());
			if (game.getCountdownSeconds() > 0 || game.getNextGoal() == null) {
				secondsLabel.setText("Countdown:");
				secondsText.setText("" + game.getCountdownSeconds());
				setDetailWidgetVisibility(false);
			} else {
				secondsLabel.setText("Time remaining:");
				secondsText.setText("" + game.getRemainingSeconds());
				TARGET targetRock = game.getNextGoal().getTarget();
				targetRockText.setText(targetRock.toString());
				Display display = parent.getDisplay();
				switch (targetRock) {
				case ADIRONDACK:
					targetRockText.setBackground(display
							.getSystemColor(SWT.COLOR_BLUE));
					break;
				case HUMPHREY:
					targetRockText.setBackground(display
							.getSystemColor(SWT.COLOR_YELLOW));
					break;
				case MAZATZAL:
					targetRockText.setBackground(display
							.getSystemColor(SWT.COLOR_GREEN));
					break;
				case MIMI:
					targetRockText.setBackground(display
							.getSystemColor(SWT.COLOR_RED));
					break;
				}
				INSTRUMENT targetInstrument = game.getNextGoal()
						.getInstrument();
				targetInstrumentText.setText(targetInstrument.toString());
				switch (targetInstrument) {
				case DRILL:
					targetInstrumentText.setBackground(display
							.getSystemColor(SWT.COLOR_RED));
					break;
				case SPECTROMETER:
					targetInstrumentText.setBackground(display
							.getSystemColor(SWT.COLOR_GREEN));
					break;
				}
				rewardText.setText("" + game.getNextReward());
				setDetailWidgetVisibility(true);
			}
			parent.layout(controls, SWT.DEFER);
		}
	}

	private void setDetailWidgetVisibility(boolean visible) {
		targetRockLabel.setVisible(visible);
		targetRockText.setVisible(visible);
		targetInstrumentLabel.setVisible(visible);
		targetInstrumentText.setVisible(visible);
		rewardLabel.setVisible(visible);
		rewardText.setVisible(visible);
	};

}
