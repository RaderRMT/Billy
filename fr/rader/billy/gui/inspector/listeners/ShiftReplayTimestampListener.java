package fr.rader.billy.gui.inspector.listeners;

import fr.rader.billy.Logger;
import fr.rader.billy.Main;
import fr.rader.billy.gui.inspector.TimelineInspector;
import fr.rader.billy.timeline.Keyframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShiftReplayTimestampListener implements ActionListener {

	private Logger logger = Main.getInstance().getLogger();

	@Override
	public void actionPerformed(ActionEvent e) {
		TimelineInspector timelineInspector = TimelineInspector.getInstance();

		int shift = (int) timelineInspector.shiftTimelineSpinner.getValue();

		logger.writeln("Shifting replay timestamps: " + shift);

		for(Keyframe keyframe : timelineInspector.timePath.getKeyframes()) {
			keyframe.getProperties().replace("timestamp", (Integer) keyframe.getProperties().get("timestamp") + shift);
		}

		timelineInspector.keyframeTimestampField.setText(String.valueOf(Integer.parseInt(timelineInspector.keyframeTimestampField.getText()) + shift));
	}
}
