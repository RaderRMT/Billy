package fr.rader.billy.gui.main.listeners;

import fr.rader.billy.gui.main.MainInterface;
import fr.rader.billy.timeline.TimelineSerialization;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public class OpenReplayListener implements ActionListener {

	public static final String REPLAY_RECORDINGS = System.getenv("APPDATA") + "/.minecraft/replay_recordings/";
	public static final String LEFT_SIDE = REPLAY_RECORDINGS + "extracted_timelines/left/";
	public static final String RIGHT_SIDE = REPLAY_RECORDINGS + "extracted_timelines/right/";

	private static File[] files = new File[] {
			new File(REPLAY_RECORDINGS),
			new File(""),
			new File("")
	};

	private static boolean hasTimeline = true;
	private static boolean hasNewReplay = false;

	private static TimelineSerialization serialization;
	private static MainInterface mainInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		serialization = new TimelineSerialization();
		mainInterface = MainInterface.getInstance();

		boolean isOpenLeft = e.getSource().equals(mainInterface.openLeftReplayButton);
		String side = isOpenLeft ? LEFT_SIDE : RIGHT_SIDE;

		openReplay(openFilePrompt(), side);

		startLoadingReplay(side, isOpenLeft);
	}

	private static void startLoadingReplay(String side, boolean isOpenLeft) {
		if(hasNewReplay) {
			if(isOpenLeft && !files[1].equals(new File(""))) { // left side
				mainInterface.openLeftReplayButton.setText(files[1].getName());
			} else if(!isOpenLeft && !files[2].equals(new File(""))) { // right side
				mainInterface.openRightReplayButton.setText(files[2].getName());
			}

			if(isOpenLeft) {
				try {
					if(hasTimeline) mainInterface.leftTimelineList = serialization.deserialize(new File(side + "timelines.json"));
					else if(mainInterface.leftTimelineList != null && !mainInterface.leftTimelineList.isEmpty()) mainInterface.leftTimelineList.clear();
					else if(mainInterface.leftTimelineList == null) mainInterface.leftTimelineList = new HashMap<>();
				} catch (IOException ioException) {
					JOptionPane.showMessageDialog(null, "Error while deserializing: " + ioException.getLocalizedMessage());
					return;
				}
			} else {
				try {
					if(hasTimeline) mainInterface.rightTimelineList = serialization.deserialize(new File(side + "timelines.json"));
					else if(mainInterface.rightTimelineList != null && !mainInterface.rightTimelineList.isEmpty()) mainInterface.rightTimelineList.clear();
					else if(mainInterface.rightTimelineList == null) mainInterface.rightTimelineList = new HashMap<>();
				} catch (IOException ioException) {
					JOptionPane.showMessageDialog(null, "Error while deserializing: " + ioException.getLocalizedMessage());
					return;
				}
			}

			mainInterface.updateNames();
		}
	}

	private static void openReplay(File file, String side) {
		if(file == null) {
			hasNewReplay = false;
			return;
		}

		hasNewReplay = true;
		hasTimeline = true;

		if(side.contains(LEFT_SIDE)) {
			if(!files[2].equals(file)) files[1] = file;
			else hasNewReplay = false;
		} else {
			if(!files[1].equals(file)) files[2] = file;
			else hasNewReplay = false;
		}

		if(!hasNewReplay) {
			JOptionPane.showMessageDialog(null, "Replays must be different!");
			return;
		}

		if(file.getName().endsWith(".json")) {
			try {
				File timelineFolder = new File(side + "timelines.json");

				Files.copy(file.toPath(), timelineFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ioException) {
				JOptionPane.showMessageDialog(null, "Error: " + ioException.getLocalizedMessage());
				hasNewReplay = false;
			}
		} else if(file.getName().endsWith(".mcpr")) {
			try {
				ZipFile mcprFile = new ZipFile(file);
				mcprFile.extractFile("timelines.json", side);
			} catch (ZipException zipException) {
				hasTimeline = false;

				JOptionPane.showMessageDialog(null, "Warning:\n" + zipException.getLocalizedMessage() + "\nAssuming the Replay does not contain a timeline.");
			}
		}
	}

	private File openFilePrompt() {
		JFileChooser fileChooser = new JFileChooser(files[0]);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file != files[1] || file != files[2] || file.isDirectory() || file.getName().endsWith(".mcpr") || file.getName().endsWith(".json");
			}

			@Override
			public String getDescription() {
				return "Replay File (*.mcpr, *.json)";
			}
		});

		int option = fileChooser.showOpenDialog(null);

		if(option == JFileChooser.APPROVE_OPTION) {
			files[0] = fileChooser.getSelectedFile().getParentFile();
			return fileChooser.getSelectedFile();
		}

		return null;
	}

	public static File getRightFile() {
		return files[2];
	}

	public static File getLeftFile() {
		return files[1];
	}

	public static void refreshLeft() {
		if(!files[1].equals(new File(""))) {
			hasNewReplay = true;
			openReplay(files[1], LEFT_SIDE);
			startLoadingReplay(LEFT_SIDE, true);
		}
	}

	public static void refreshRight() {
		if(!files[2].equals(new File(""))) {
			hasNewReplay = true;
			openReplay(files[2], RIGHT_SIDE);
			startLoadingReplay(RIGHT_SIDE, false);
		}
	}
}
