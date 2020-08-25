package fr.rader.rtt;

import fr.rader.rtt.listeners.*;
import fr.rader.rtt.timeline.Timeline;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Interface {

	private OpenListener openListener = new OpenListener();
	private MoveListener moveListener = new MoveListener();
	private CopyListener copyListener = new CopyListener();
	private DeleteListener deleteListener = new DeleteListener();
	private RenameListener renameListener = new RenameListener();

	private static Interface instance;

	public JPanel panel1;
	private JPanel panel;

	public JList<String> leftList;
	public JList<String> rightList;

	public JButton openReplayLeft;
	public JButton openReplayRight;

	public JButton moveLeft;
	public JButton moveRight;

	public JButton copyRight;
	public JButton copyLeft;

	public JButton renameRightTimeline;
	public JButton renameLeftTimeline;

	public JButton deleteTimelinesLeft;
	public JButton deleteTimelinesRight;

	public Map<String, Timeline> leftTimelineList;
	public Map<String, Timeline> rightTimelineList;

	public Interface() {
		instance = this;

		openReplayLeft.addActionListener(openListener);
		openReplayRight.addActionListener(openListener);

		moveLeft.addActionListener(moveListener);
		moveRight.addActionListener(moveListener);

		copyLeft.addActionListener(copyListener);
		copyRight.addActionListener(copyListener);

		deleteTimelinesLeft.addActionListener(deleteListener);
		deleteTimelinesRight.addActionListener(deleteListener);

		renameLeftTimeline.addActionListener(renameListener);
		renameRightTimeline.addActionListener(renameListener);
	}

	public void updateNames() {
		if(leftTimelineList != null) {
			List<String> names = new ArrayList<>(leftTimelineList.keySet());
			leftList.setListData(names.toArray(new String[0]));
		}

		if(rightTimelineList != null) {
			List<String> names = new ArrayList<>(rightTimelineList.keySet());
			rightList.setListData(names.toArray(new String[0]));
		}
	}

	public static Interface getInstance() {
		return instance;
	}
}
