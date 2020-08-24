package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MoveListener implements ActionListener {

	private Interface theInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		theInterface = Interface.getInstance();

		if(Main.getInstance().getLeftFile() == null || Main.getInstance().getRightFile() == null) {
			return;
		}

		if(e.getSource().equals(theInterface.moveRight)) {
			moveToList(theInterface.leftList.getSelectedValuesList(), true);
		} else {
			moveToList(theInterface.rightList.getSelectedValuesList(), false);
		}
	}

	private void moveToList(List<String> selectedTimelines, boolean moveToRight) {
		for(String name : selectedTimelines) {
			if(moveToRight) {
				theInterface.rightTimelineList.put(name, theInterface.leftTimelineList.get(name));
				theInterface.leftTimelineList.remove(name);
			} else {
				theInterface.leftTimelineList.put(name, theInterface.rightTimelineList.get(name));
				theInterface.rightTimelineList.remove(name);
			}
		}

		theInterface.updateNames();
	}
}
