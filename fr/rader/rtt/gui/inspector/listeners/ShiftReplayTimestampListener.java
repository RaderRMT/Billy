package fr.rader.rtt.gui.inspector.listeners;

import fr.rader.rtt.gui.inspector.TimelineInspector;
import fr.rader.rtt.timeline.Keyframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShiftReplayTimestampListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		TimelineInspector timelineInspector = TimelineInspector.getInstance();

		int shift = (int) timelineInspector.shiftTimelineSpinner.getValue();

		for(Keyframe keyframe : timelineInspector.timePath.getKeyframes()) {
			keyframe.getProperties().replace("timestamp", (Integer) keyframe.getProperties().get("timestamp") + shift);
		}

		timelineInspector.keyframeTimestampField.setText(String.valueOf(Integer.parseInt(timelineInspector.keyframeTimestampField.getText()) + shift));
	}
}
