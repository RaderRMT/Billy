package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;
import fr.rader.rtt.timeline.Timeline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class DeleteListener implements ActionListener {

	private Interface theInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		theInterface = Interface.getInstance();

		if(e.getSource().equals(theInterface.deleteTimelinesLeft)) {
			if(Main.getInstance().getLeftFile() != null) {
				deleteSelectedTimelines(theInterface.leftList.getSelectedValuesList(), theInterface.leftTimelineList);
			}
		} else {
			if(Main.getInstance().getRightFile() != null) {
				deleteSelectedTimelines(theInterface.rightList.getSelectedValuesList(), theInterface.rightTimelineList);
			}
		}
	}

	private void deleteSelectedTimelines(List<String> selectedTimelines, Map<String, Timeline> timelinesList) {
		for(String name : selectedTimelines) {
			timelinesList.remove(name);
		}

		theInterface.updateNames();
	}
}
