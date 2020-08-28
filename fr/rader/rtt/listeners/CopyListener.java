package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;
import fr.rader.rtt.timeline.Timeline;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

public class CopyListener implements ActionListener {

	private Interface theInterface;
	private File selectedSide;

	@Override
	public void actionPerformed(ActionEvent e) {
		theInterface = Interface.getInstance();

		if(e.getSource().equals(theInterface.copyRight)) {
			if((selectedSide = Main.getInstance().getRightFile()) == null || Main.getInstance().getLeftFile() == null) return;

			copyToList(theInterface.leftList.getSelectedValuesList(), theInterface.leftTimelineList, theInterface.rightTimelineList);
		} else {
			if((selectedSide = Main.getInstance().getLeftFile()) == null || Main.getInstance().getRightFile() == null) return;

			copyToList(theInterface.rightList.getSelectedValuesList(), theInterface.rightTimelineList, theInterface.leftTimelineList);
		}
	}

	private void copyToList(List<String> selectedTimelines, Map<String, Timeline> fromList, Map<String, Timeline> toList) {
		for(String name : selectedTimelines) {
			if(toList == null) return;

			String oldName = name;
			while(toList.containsKey(name)) {
				name = JOptionPane.showInputDialog(null, "\"" + selectedSide.getName() + "\" already contains a timeline named \"" + name + "\"\nEnter a new name or press cancel to cancel");
			}

			if(name == null) continue;

			toList.put(name, fromList.get(oldName));
		}

		theInterface.updateNames();
	}
}
