package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeleteListener implements ActionListener {

	private Interface theInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		theInterface = Interface.getInstance();

		if(e.getSource().equals(theInterface.deleteTimelinesLeft)) {
			if(Main.getInstance().getLeftFile() != null) {
				deleteSelectedTimelines(theInterface.leftList.getSelectedValuesList(), true);
			}
		} else {
			if(Main.getInstance().getRightFile() != null) {
				deleteSelectedTimelines(theInterface.rightList.getSelectedValuesList(), false);
			}
		}
	}

	private void deleteSelectedTimelines(List<String> selectedTimelines, boolean isLeftSide) {
		for(String name : selectedTimelines) {
			if(isLeftSide) {
				theInterface.leftTimelineList.remove(name);
			} else {
				theInterface.rightTimelineList.remove(name);
			}
		}

		theInterface.updateNames();
	}
}
