package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;
import fr.rader.rtt.timeline.Timeline;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class RenameListener implements ActionListener {

	private Interface theInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		theInterface = Interface.getInstance();

		if(e.getSource().equals(theInterface.renameLeftTimeline)) {
			if(Main.getInstance().getLeftFile() == null) return;

			renameTimelines(theInterface.leftList.getSelectedValuesList(), theInterface.leftTimelineList);
		} else {
			if(Main.getInstance().getRightFile() == null) return;

			renameTimelines(theInterface.rightList.getSelectedValuesList(), theInterface.rightTimelineList);
		}
	}

	private void renameTimelines(List<String> selectedTimelines, Map<String, Timeline> timelineList) {
		for(String selectedTimeline : selectedTimelines) {
			String newTimelineName = JOptionPane.showInputDialog(null, "Enter a new name for \"" + selectedTimeline + "\" or press cancel to cancel");

			while(timelineList.containsKey(newTimelineName)) {
				newTimelineName = JOptionPane.showInputDialog(null, "Enter a new name for \"" + selectedTimeline + "\" or press cancel to cancel");
			}

			if(newTimelineName == null) return;

			timelineList.put(newTimelineName, timelineList.get(selectedTimeline));
			timelineList.remove(selectedTimeline);
		}

		theInterface.updateNames();
	}
}
