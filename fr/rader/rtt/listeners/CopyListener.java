package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;
import fr.rader.rtt.timeline.Timeline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CopyListener implements ActionListener {

	private Interface theInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		theInterface = Interface.getInstance();

		if(Main.getInstance().getLeftFile() == null || Main.getInstance().getRightFile() == null) {
			return;
		}

		if(e.getSource().equals(theInterface.copyRight)) {
			copyToList(theInterface.leftList.getSelectedIndices(), true);
		} else {
			copyToList(theInterface.rightList.getSelectedIndices(), false);
		}
	}

	private void copyToList(int[] selectedTimelines, boolean copyToRight) {
		for(int timelineIndex : selectedTimelines) {
			if(copyToRight) {
				theInterface.rightTimelineList.put((String) theInterface.leftTimelineList.keySet().toArray()[timelineIndex], (Timeline) theInterface.leftTimelineList.values().toArray()[timelineIndex]);
			} else {
				theInterface.leftTimelineList.put((String) theInterface.rightTimelineList.keySet().toArray()[timelineIndex], (Timeline) theInterface.rightTimelineList.values().toArray()[timelineIndex]);
			}
		}

		theInterface.updateNames();
	}
}
