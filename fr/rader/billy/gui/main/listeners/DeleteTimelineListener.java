package fr.rader.billy.gui.main.listeners;

import fr.rader.billy.Logger;
import fr.rader.billy.Main;
import fr.rader.billy.gui.main.MainInterface;
import fr.rader.billy.timeline.Timeline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DeleteTimelineListener implements ActionListener {

	private Logger logger = Main.getInstance().getLogger();

	private MainInterface mainInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		mainInterface = MainInterface.getInstance();

		if(e.getSource().equals(mainInterface.deleteLeftTimeline)) {
			if(!mainInterface.openLeftReplayButton.getText().equals("Open Replay"))
				deleteSelectedTimelines(mainInterface.leftNameList.getSelectedValuesList(),
						mainInterface.leftTimelineList);
		} else {
			if(!mainInterface.openRightReplayButton.getText().equals("Open Replay"))
				deleteSelectedTimelines(mainInterface.rightNameList.getSelectedValuesList(),
						mainInterface.rightTimelineList);
		}
	}

	private void deleteSelectedTimelines(List<String> selectedTimelines, Map<String, Timeline> timelinesList) {
		logger.writeln("Deleting timelines " + Arrays.toString(selectedTimelines.toArray()));

		for(String name : selectedTimelines) {
			timelinesList.remove(name);
		}

		mainInterface.updateNames();
	}
}
