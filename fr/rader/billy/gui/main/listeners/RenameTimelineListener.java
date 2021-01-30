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

public class RenameTimelineListener implements ActionListener {

	private final Logger logger = Main.getInstance().getLogger();

	private MainInterface mainInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		mainInterface = MainInterface.getInstance();

		if(e.getSource().equals(mainInterface.renameLeftTimeline)) {
			if(!mainInterface.openLeftReplayButton.getText().equals("Open Replay"))
				renameTimelines(mainInterface.leftNameList.getSelectedValuesList(),
						mainInterface.leftTimelineList);
		} else {
			if(!mainInterface.openRightReplayButton.getText().equals("Open Replay"))
				renameTimelines(mainInterface.rightNameList.getSelectedValuesList(),
						mainInterface.rightTimelineList);
		}
	}

	private void renameTimelines(List<String> selectedTimelines, Map<String, Timeline> timelineList) {
		logger.writeln("Renaming timelines " + Arrays.toString(selectedTimelines.toArray()));

		for(String selectedTimeline : selectedTimelines) {
			String newTimelineName = JOptionPane.showInputDialog(null, "Enter a new name for \"" + selectedTimeline + "\" or press cancel to cancel", selectedTimeline);

			while(timelineList.containsKey(newTimelineName)) {
				newTimelineName = JOptionPane.showInputDialog(null, "Enter a new name for \"" + selectedTimeline + "\" or press cancel to cancel", selectedTimeline);
			}

			if(newTimelineName == null) continue;

			timelineList.put(newTimelineName, timelineList.get(selectedTimeline));
			timelineList.remove(selectedTimeline);
		}

		mainInterface.updateNames();
	}
}
