package fr.rader.rtt.gui.inspector.listeners;

import fr.rader.rtt.gui.inspector.TimelineInspector;
import fr.rader.rtt.gui.main.MainInterface;
import fr.rader.rtt.timeline.Keyframe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SaveKeyframeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		TimelineInspector timelineInspector = TimelineInspector.getInstance();

		int selectedKeyframe = timelineInspector.selectedKeyframeCombo.getSelectedIndex();

		if(e.getSource().equals(timelineInspector.savePositionKeyframeButton)) {
			int timestamp = Integer.parseInt(timelineInspector.timestampField.getText());

			double[] rotation = {
					Double.parseDouble(timelineInspector.yawField.getText()),
					Double.parseDouble(timelineInspector.pitchField.getText()),
					Double.parseDouble(timelineInspector.rollField.getText())
			};

			double[] position = {
					Double.parseDouble(timelineInspector.xField.getText()),
					Double.parseDouble(timelineInspector.yField.getText()),
					Double.parseDouble(timelineInspector.zField.getText())
			};

			boolean isSpectator = timelineInspector.spectatorKeyframeCheck.isSelected();

			Map<String, Object> properties = new HashMap<>();
			properties.put("camera:rotation", rotation);
			properties.put("camera:position", position);
			if(isSpectator) properties.put("spectate", 1);

			Keyframe newKeyframe = new Keyframe(timestamp, properties);
			timelineInspector.positionPath.getKeyframes().set(selectedKeyframe, newKeyframe);
		} else {
			int timestamp = Integer.parseInt(timelineInspector.keyframeTimestampField.getText());

			int replayTimestamp = Integer.parseInt(timelineInspector.replayTimestampField.getText());

			Map<String, Object> properties = new HashMap<>();
			properties.put("timestamp", replayTimestamp);

			Keyframe newKeyframe = new Keyframe(timestamp, properties);
			timelineInspector.timePath.getKeyframes().set(selectedKeyframe, newKeyframe);
		}
	}
}
