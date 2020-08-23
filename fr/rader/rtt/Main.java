package fr.rader.rtt;

import javax.swing.*;
import java.io.File;

public class Main {

	private static Main instance;

	public static Main getInstance() {
		return instance;
	}

	private final Actions actions = new Actions();

	private File mcprToExtractTimeline;
	private File mcprToInsertTimeline;

	private JButton replayExtractTimelineButton;
	private JButton replayInsertTimelineButton;

	private void start() {
		JFrame frame = new JFrame("Replay Timeline Extractor");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new SpringLayout());

		JLabel replayExtractTimeline = new JLabel("Replay to extract the timeline:");
		panel.add(replayExtractTimeline);
		replayExtractTimelineButton = new JButton("Select Replay 1");
		replayExtractTimelineButton.addActionListener(actions);
		panel.add(replayExtractTimelineButton);

		JLabel replayInsertTimeline = new JLabel("Replay to insert the timeline:");
		panel.add(replayInsertTimeline);
		replayInsertTimelineButton = new JButton("Select Replay 2");
		replayInsertTimelineButton.addActionListener(actions);
		panel.add(replayInsertTimelineButton);

		JButton extractTimelineOnly = new JButton("Extract timeline without inserting");
		extractTimelineOnly.addActionListener(actions);
		panel.add(extractTimelineOnly);
		JButton extractAndInsert = new JButton("Extract and insert timeline");
		extractAndInsert.addActionListener(actions);
		panel.add(extractAndInsert);

		SpringUtilities.makeCompactGrid(panel, 3, 2, 6, 6, 6, 6);

		frame.setContentPane(panel);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		instance = this;

		// Set OS look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		start();
	}

	public File getMcprToExtractTimeline() {
		return mcprToExtractTimeline;
	}

	public void setMcprToExtractTimeline(File mcprToExtractTimeline) {
		this.mcprToExtractTimeline = mcprToExtractTimeline;
	}

	public File getMcprToInsertTimeline() {
		return mcprToInsertTimeline;
	}

	public void setMcprToInsertTimeline(File mcprToInsertTimeline) {
		this.mcprToInsertTimeline = mcprToInsertTimeline;
	}

	public void setReplayInsertTimelineButtonName(String name) {
		replayInsertTimelineButton.setText(name);
	}

	public void setReplayExtractTimelineButtonName(String name) {
		replayExtractTimelineButton.setText(name);
	}
}
