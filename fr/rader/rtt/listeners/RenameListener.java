package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RenameListener implements ActionListener {

	private Interface theInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		theInterface = Interface.getInstance();

		if(e.getSource().equals(theInterface.renameLeftTimeline)) {
			if(Main.getInstance().getLeftFile() == null) return;

			renameTimelines(theInterface.leftList.getSelectedValuesList(), true);
		} else {
			if(Main.getInstance().getRightFile() == null) return;

			renameTimelines(theInterface.rightList.getSelectedValuesList(), false);
		}
	}

	private void renameTimelines(List<String> selectedTimelines, boolean isRenamingLeft) {
		for(String selectedTimeline : selectedTimelines) {
			String newTimelineName = JOptionPane.showInputDialog(null, "Enter a new name for \"" + selectedTimeline + "\" or press cancel to cancel");

			if(newTimelineName != null) {
				if(isRenamingLeft) {
					while(theInterface.leftTimelineList.containsKey(newTimelineName)) {
						newTimelineName = JOptionPane.showInputDialog(null, "Enter a new name for \"" + selectedTimeline + "\" or press cancel to cancel");
						if(newTimelineName == null) return;
					}

					theInterface.leftTimelineList.put(newTimelineName, theInterface.leftTimelineList.get(selectedTimeline));
					theInterface.leftTimelineList.remove(selectedTimeline);
				} else {
					while(theInterface.rightTimelineList.containsKey(newTimelineName)) {
						newTimelineName = JOptionPane.showInputDialog(null, "Enter a new name for \"" + selectedTimeline + "\" or press cancel to cancel");
						if(newTimelineName == null) return;
					}

					theInterface.rightTimelineList.put(newTimelineName, theInterface.rightTimelineList.get(selectedTimeline));
					theInterface.rightTimelineList.remove(selectedTimeline);
				}
			}
		}

		theInterface.updateNames();
	}
}
