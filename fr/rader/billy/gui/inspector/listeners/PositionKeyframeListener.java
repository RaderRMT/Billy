package fr.rader.billy.gui.inspector.listeners;

import fr.rader.billy.gui.inspector.TimelineInspector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PositionKeyframeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		int selectedType = ((JComboBox<String>) e.getSource()).getSelectedIndex();

		TimelineInspector timelineInspector = TimelineInspector.getInstance();

		if(timelineInspector.positionPath.getKeyframes().size() == 0) return;

		timelineInspector.timestampField.setText(String.valueOf(timelineInspector.positionPath.getKeyframes().get(selectedType).getTime()));

		double[] rotation = (double[]) timelineInspector.positionPath.getKeyframes().get(selectedType).getProperties().get("camera:rotation");

		timelineInspector.yawField.setText(String.valueOf(rotation[0]));
		timelineInspector.pitchField.setText(String.valueOf(rotation[1]));
		timelineInspector.rollField.setText(String.valueOf(rotation[2]));

		double[] position = (double[]) timelineInspector.positionPath.getKeyframes().get(selectedType).getProperties().get("camera:position");

		timelineInspector.xField.setText(String.valueOf(position[0]));
		timelineInspector.yField.setText(String.valueOf(position[1]));
		timelineInspector.zField.setText(String.valueOf(position[2]));

		timelineInspector.spectatorKeyframeCheck.setSelected(timelineInspector.positionPath.getKeyframes().get(selectedType).getProperties().containsKey("spectate"));
	}
}
