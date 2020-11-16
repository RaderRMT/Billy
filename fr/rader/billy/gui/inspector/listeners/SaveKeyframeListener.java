package fr.rader.billy.gui.inspector.listeners;

import fr.rader.billy.Logger;
import fr.rader.billy.Main;
import fr.rader.billy.gui.inspector.TimelineInspector;
import fr.rader.billy.timeline.Keyframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SaveKeyframeListener implements ActionListener {

	private Logger logger = Main.getInstance().getLogger();

	@Override
	public void actionPerformed(ActionEvent e) {
		TimelineInspector timelineInspector = TimelineInspector.getInstance();

		if(e.getSource().equals(timelineInspector.savePositionKeyframeButton)) {
			int selectedKeyframe = timelineInspector.selectedKeyframeCombo.getSelectedIndex();

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

			logger.writeln("Started saving position keyframe #" + selectedKeyframe);
			logger.writeln("Writing timestamp: " + timestamp);
			logger.writeln("Writing spectator: " + isSpectator);

			Map<String, Object> properties = new HashMap<>();
			properties.put("camera:rotation", rotation);
			properties.put("camera:position", position);
			if(isSpectator) properties.put("spectate", 1);

			logger.writeln("Writing properties: " + logger.readProperties(properties));

			Keyframe newKeyframe = new Keyframe(timestamp, properties);
			timelineInspector.positionPath.getKeyframes().set(selectedKeyframe, newKeyframe);
		} else {
			int selectedKeyframe = timelineInspector.selectedTimeKeyframeCombo.getSelectedIndex();

			int timestamp = Integer.parseInt(timelineInspector.keyframeTimestampField.getText());

			int replayTimestamp = Integer.parseInt(timelineInspector.replayTimestampField.getText());

			logger.writeln("Started saving time keyframe #" + selectedKeyframe);
			logger.writeln("Writing keyframe timestamp: " + timestamp);
			logger.writeln("Writing replay timestamp: " + replayTimestamp);

			Map<String, Object> properties = new HashMap<>();
			properties.put("timestamp", replayTimestamp);

			logger.writeln("Writing properties: " + logger.readProperties(properties));

			Keyframe newKeyframe = new Keyframe(timestamp, properties);
			timelineInspector.timePath.getKeyframes().set(selectedKeyframe, newKeyframe);
		}
	}
}
