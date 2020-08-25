package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;
import fr.rader.rtt.timeline.Timeline;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CopyListener implements ActionListener {

	private Interface theInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		theInterface = Interface.getInstance();

		if(Main.getInstance().getLeftFile() == null || Main.getInstance().getRightFile() == null) {
			return;
		}

		if(e.getSource().equals(theInterface.copyRight)) {
			copyToList(theInterface.leftList.getSelectedValuesList(), true);
		} else {
			copyToList(theInterface.rightList.getSelectedValuesList(), false);
		}
	}

	private void copyToList(List<String> selectedTimelines, boolean copyToRight) {
		for(String name : selectedTimelines) {
			if(copyToRight) {
				if(theInterface.rightTimelineList != null && !theInterface.rightTimelineList.containsKey(name)) {
					theInterface.rightTimelineList.put(name, theInterface.leftTimelineList.get(name));
				} else if(theInterface.rightTimelineList != null) {
					JOptionPane.showMessageDialog(null, "\"" + Main.getInstance().getRightFile().getName() + "\" already contains a timeline named \"" + name + "\"");
				}
			} else {
				if(theInterface.leftTimelineList != null && !theInterface.leftTimelineList.containsKey(name)) {
					theInterface.leftTimelineList.put(name, theInterface.rightTimelineList.get(name));
				} else if(theInterface.leftTimelineList != null) {
					JOptionPane.showMessageDialog(null, "\"" + Main.getInstance().getLeftFile().getName() + "\" already contains a timeline named \"" + name + "\"");
				}
			}
		}

		theInterface.updateNames();
	}
}
