package fr.rader.billy.gui.inspector;

import fr.rader.billy.gui.inspector.listeners.*;
import fr.rader.billy.timeline.Path;
import fr.rader.billy.timeline.Timeline;

import javax.swing.*;

public class TimelineInspector {

	private static TimelineInspector instance;

	public JPanel translateTimelinePanel;
	public JPanel rightPanel;
	public JPanel leftPanel;
	public JPanel panel;

	private JLabel timelineNameLabel;
	private JLabel positionKeyframesLabel;
	private JLabel timeKeyframesLabel;
	private JLabel selectedPositionKeyframeLabel;
	private JLabel timestampLabel;
	private JLabel cameraRotationLabel;
	private JLabel yawLabel;
	private JLabel pitchLabel;
	private JLabel rollLabel;
	private JLabel cameraPositionLabel;
	private JLabel xLabel;
	private JLabel yLabel;
	private JLabel zLabel;
	private JLabel spectatorKeyframeLabel;
	private JLabel selectedTimeKeyframeLabel;
	private JLabel replayTimestampLabel;
	private JLabel keyframeTimestampLabel;
	private JLabel shiftTimelineLabel;
	private JLabel oldCoordsLabel;
	private JLabel oldXCoordsLabel;
	private JLabel oldYCoordsLabel;
	private JLabel oldZCoordsLabel;
	private JLabel newCoordsLabel;
	private JLabel newXCoordsLabel;
	private JLabel newYCoordsLabel;
	private JLabel newZCoordsLabel;

	public JTextField timelineNameField;
	public JTextField positionKeyframesField;
	public JTextField timeKeyframesField;
	public JComboBox<String> selectedKeyframeCombo;
	public JComboBox<String> selectedTimeKeyframeCombo;
	public JTextField timestampField;
	public JTextField yawField;
	public JTextField pitchField;
	public JTextField rollField;
	public JTextField xField;
	public JTextField yField;
	public JTextField zField;
	public JCheckBox spectatorKeyframeCheck;
	public JTextField replayTimestampField;
	public JTextField keyframeTimestampField;
	public JButton shiftTimelineButton;
	public JButton savePositionKeyframeButton;
	public JButton saveTimeKeyframeButton;
	public JSpinner shiftTimelineSpinner;
	public JButton moveTimelineButton;
	public JTextField oldXField;
	public JTextField oldYField;
	public JTextField oldZField;
	public JTextField newXField;
	public JTextField newYField;
	public JTextField newZField;
	private JButton shiftReplayTimestampsButton;

	public Path timePath;
	public Path positionPath;

	public TimelineInspector(Timeline timeline, String name) {
		instance = this;

		timelineNameField.setText(name);

		timePath = timeline.getPaths().get(0);
		positionPath = timeline.getPaths().get(1);

		positionKeyframesField.setText(String.valueOf(positionPath.getKeyframes().size()));
		timeKeyframesField.setText(String.valueOf(timePath.getKeyframes().size()));

		selectedKeyframeCombo.addActionListener(new PositionKeyframeListener());
		selectedTimeKeyframeCombo.addActionListener(new TimeKeyframeListener());

		SaveKeyframeListener saveKeyframeListener = new SaveKeyframeListener();
		savePositionKeyframeButton.addActionListener(saveKeyframeListener);
		saveTimeKeyframeButton.addActionListener(saveKeyframeListener);

		shiftTimelineButton.addActionListener(new ShiftTimelineListener());

		moveTimelineButton.addActionListener(new TranslateTimelineListener());

		shiftReplayTimestampsButton.addActionListener(new ShiftReplayTimestampListener());

		for(int x = 0; x < positionPath.getKeyframes().size(); x++) {
			selectedKeyframeCombo.addItem(String.valueOf(x));
		}

		for(int x = 0; x < timePath.getKeyframes().size(); x++) {
			selectedTimeKeyframeCombo.addItem(String.valueOf(x));
		}
	}

	public static TimelineInspector getInstance() {
		return instance;
	}
}
