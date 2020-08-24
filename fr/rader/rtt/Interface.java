package fr.rader.rtt;

import fr.rader.rtt.listeners.CopyListener;
import fr.rader.rtt.listeners.MoveListener;
import fr.rader.rtt.listeners.OpenListener;
import fr.rader.rtt.timeline.Timeline;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Interface {

	private static Interface instance;

	public static Interface getInstance() {
		return instance;
	}

	public JPanel panel1;
	private JPanel panel;

	public JButton openReplayLeft;
	public JButton openReplayRight;

	public JList<String> leftList;
	public JList<String> rightList;

	public JButton moveLeft;
	public JButton moveRight;

	public JButton copyRight;
	public JButton copyButton;

	private OpenListener openListener = new OpenListener();
	private MoveListener moveListener = new MoveListener();
	private CopyListener copyListener = new CopyListener();

	public Map<String, Timeline> leftTimelineList;
	public Map<String, Timeline> rightTimelineList;

	public Interface() {
		instance = this;

		openReplayLeft.addActionListener(openListener);
		openReplayRight.addActionListener(openListener);

		moveLeft.addActionListener(moveListener);
		moveRight.addActionListener(moveListener);

		copyButton.addActionListener(copyListener);
		copyRight.addActionListener(copyListener);
	}

	public void updateNames() {
		if(leftTimelineList != null) {
			List<String> names = new ArrayList<>();
			names.addAll(leftTimelineList.keySet());
			leftList.setListData(names.toArray(new String[0]));
		}

		if(rightTimelineList != null) {
			List<String> names = new ArrayList<>();
			names.addAll(rightTimelineList.keySet());
			rightList.setListData(names.toArray(new String[0]));
		}
	}
}
