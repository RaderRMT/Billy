package fr.rader.billy.gui.main.listeners;

import fr.rader.billy.Logger;
import fr.rader.billy.Main;
import fr.rader.billy.gui.main.MainInterface;
import fr.rader.billy.timeline.Timeline;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MoveTimelineListener implements ActionListener {

	private final Logger logger = Main.getInstance().getLogger();

	private MainInterface mainInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		mainInterface = MainInterface.getInstance();

		if(mainInterface.rightTimelineList == null || mainInterface.leftTimelineList == null) return;

		if(e.getSource().equals(mainInterface.moveToRightButton)) {
			moveToList(mainInterface.leftNameList.getSelectedValuesList(),
					mainInterface.leftTimelineList,
					mainInterface.rightTimelineList,
					mainInterface.openRightReplayButton.getText());
		} else {
			moveToList(mainInterface.rightNameList.getSelectedValuesList(),
					mainInterface.rightTimelineList,
					mainInterface.leftTimelineList,
					mainInterface.openLeftReplayButton.getText());
		}
	}

	private void moveToList(List<String> selectedTimelines, Map<String, Timeline> fromList, Map<String, Timeline> toList, String replayName) {
		if(replayName.equals("Open Replay")) return;

		logger.writeln("Moving timelines " + Arrays.toString(selectedTimelines.toArray()) + " to '" + replayName + "'");

		for(String name : selectedTimelines) {
			String oldName = name;
			while(toList.containsKey(name)) {
				name = JOptionPane.showInputDialog(null, "\"" + replayName + "\" already contains a timeline named \"" + name + "\"\nEnter a new name or press cancel to cancel", oldName);
			}

			if(name == null) continue;

			toList.put(name, fromList.get(oldName));
			fromList.remove(oldName);
		}

		mainInterface.updateNames();
	}
}
