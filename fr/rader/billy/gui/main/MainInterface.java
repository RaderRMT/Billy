package fr.rader.billy.gui.main;

import fr.rader.billy.gui.main.listeners.*;
import fr.rader.billy.timeline.Timeline;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainInterface {

	private static MainInterface instance;

	public JPanel mainPanel;

	public JList<String> leftNameList;
	public JList<String> rightNameList;

	public JButton deleteLeftTimeline;
	public JButton moveToLeftButton;
	public JButton openLeftReplayButton;
	public JButton copyToLeftButton;
	public JButton renameLeftTimeline;
	public JButton editLeftTimelineButton;
	public JButton saveLeftReplayButton;
	public JButton reloadLeftReplayButton;

	public JButton openRightReplayButton;
	public JButton deleteRightTimeline;
	public JButton renameRightTimeline;
	public JButton moveToRightButton;
	public JButton copyToRightButton;
	public JButton editRightTimelineButton;
	public JButton saveRightReplayButton;
	public JButton reloadRightReplayButton;

	public Map<String, Timeline> leftTimelineList;
	public Map<String, Timeline> rightTimelineList;

	public MainInterface() {
		instance = this;

		OpenReplayListener openReplayListener = new OpenReplayListener();
		openLeftReplayButton.addActionListener(openReplayListener);
		openRightReplayButton.addActionListener(openReplayListener);

		CopyTimelineListener copyTimelineListener = new CopyTimelineListener();
		copyToLeftButton.addActionListener(copyTimelineListener);
		copyToRightButton.addActionListener(copyTimelineListener);

		DeleteTimelineListener deleteTimelineListener = new DeleteTimelineListener();
		deleteLeftTimeline.addActionListener(deleteTimelineListener);
		deleteRightTimeline.addActionListener(deleteTimelineListener);

		MoveTimelineListener moveTimelineListener = new MoveTimelineListener();
		moveToLeftButton.addActionListener(moveTimelineListener);
		moveToRightButton.addActionListener(moveTimelineListener);

		RenameTimelineListener renameTimelineListener = new RenameTimelineListener();
		renameLeftTimeline.addActionListener(renameTimelineListener);
		renameRightTimeline.addActionListener(renameTimelineListener);

		EditViewTimelineListener editViewTimelineListener = new EditViewTimelineListener();
		editLeftTimelineButton.addActionListener(editViewTimelineListener);

		SaveReplayListener saveReplayListener = new SaveReplayListener();
		saveLeftReplayButton.addActionListener(saveReplayListener);
		saveRightReplayButton.addActionListener(saveReplayListener);

		ReloadReplayListener reloadReplayListener = new ReloadReplayListener();
		reloadLeftReplayButton.addActionListener(reloadReplayListener);
		reloadRightReplayButton.addActionListener(reloadReplayListener);
	}

	public void updateNames() {
		if(leftTimelineList != null) {
			List<String> names = new ArrayList<>(leftTimelineList.keySet());
			leftNameList.setListData(names.toArray(new String[0]));
		}

		if(rightTimelineList != null) {
			List<String> names = new ArrayList<>(rightTimelineList.keySet());
			rightNameList.setListData(names.toArray(new String[0]));
		}
	}

	public static MainInterface getInstance() {
		return instance;
	}
}
