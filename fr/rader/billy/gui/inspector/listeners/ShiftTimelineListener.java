package fr.rader.billy.gui.inspector.listeners;

import fr.rader.billy.gui.inspector.TimelineInspector;
import fr.rader.billy.timeline.Keyframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShiftTimelineListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		TimelineInspector timelineInspector = TimelineInspector.getInstance();

		int shift = (int) timelineInspector.shiftTimelineSpinner.getValue();

		for(Keyframe keyframe : timelineInspector.timePath.getKeyframes()) {
			keyframe.setTime(keyframe.getTime() + shift);
		}

		for(Keyframe keyframe : timelineInspector.positionPath.getKeyframes()) {
			keyframe.setTime(keyframe.getTime() + shift);
		}

		timelineInspector.timestampField.setText(String.valueOf(Integer.parseInt(timelineInspector.timestampField.getText()) + shift));
		timelineInspector.replayTimestampField.setText(String.valueOf(Integer.parseInt(timelineInspector.replayTimestampField.getText()) + shift));
	}
}
