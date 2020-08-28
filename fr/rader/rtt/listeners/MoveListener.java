package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;
import fr.rader.rtt.timeline.Timeline;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class MoveListener implements ActionListener {

	private Interface theInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		theInterface = Interface.getInstance();

		if(Main.getInstance().getLeftFile() == null || Main.getInstance().getRightFile() == null) {
			return;
		}

		if(e.getSource().equals(theInterface.moveRight)) {
			if(Main.getInstance().getLeftFile() == null) return;

			moveToList(theInterface.leftList.getSelectedValuesList(), theInterface.leftTimelineList, theInterface.rightTimelineList);
		} else {
			if(Main.getInstance().getRightFile() == null) return;

			moveToList(theInterface.rightList.getSelectedValuesList(), theInterface.rightTimelineList, theInterface.leftTimelineList);
		}
	}

	private void moveToList(List<String> selectedTimelines, Map<String, Timeline> fromList, Map<String, Timeline> toList) {
		for(String name : selectedTimelines) {
			if(toList == null) return;

			String oldName = name;
			while(toList.containsKey(name)) {
				name = JOptionPane.showInputDialog(null, "\"" + Main.getInstance().getRightFile().getName() + "\" already contains a timeline named \"" + name + "\"\nEnter a new name or press cancel to cancel");
			}

			if(name == null) continue;

			toList.put(name, fromList.get(oldName));
			fromList.remove(oldName);
		}

		theInterface.updateNames();
	}
}
