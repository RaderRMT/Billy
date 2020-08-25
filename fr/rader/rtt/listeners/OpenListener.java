package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;
import fr.rader.rtt.timeline.TimelineSerialization;
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
import java.util.Arrays;
import java.util.HashMap;

public class OpenListener implements ActionListener {

	public static final String REPLAY_RECORDINGS = System.getenv("APPDATA") + "/.minecraft/replay_recordings/";
	public static final String LEFT_SIDE = REPLAY_RECORDINGS + "extracted_timelines/left/";
	public static final String RIGHT_SIDE = REPLAY_RECORDINGS + "extracted_timelines/right/";

	private File lastOpenedFolder = new File(REPLAY_RECORDINGS);

	private File leftReplay;
	private File rightReplay;

	private boolean hasTimeline = true;

	private boolean hasNewReplay = false;

	@Override
	public void actionPerformed(ActionEvent e) {
		TimelineSerialization serialization = new TimelineSerialization();
		Interface theInterface = Interface.getInstance();

		boolean isOpenReplayLeft = e.getSource().equals(theInterface.openReplayLeft);

		openReplay(isOpenReplayLeft);

		if(hasNewReplay) {
			if(isOpenReplayLeft) {
				if(leftReplay != null) theInterface.openReplayLeft.setText(leftReplay.getName());

				try {
					if(hasTimeline) theInterface.leftTimelineList = serialization.deserialize(new File(LEFT_SIDE + "timelines.json"));
					else if(theInterface.leftTimelineList != null && !theInterface.leftTimelineList.isEmpty()) theInterface.leftTimelineList.clear();
					else if(theInterface.leftTimelineList == null) theInterface.leftTimelineList = new HashMap<>();
				} catch (IOException ioException) {
					JOptionPane.showMessageDialog(null, "Error while deserializing: " + ioException.getLocalizedMessage());
					return;
				}
			} else {
				if(rightReplay != null) theInterface.openReplayRight.setText(rightReplay.getName());

				try {
					if(hasTimeline) theInterface.rightTimelineList = serialization.deserialize(new File(RIGHT_SIDE + "timelines.json"));
					else if(theInterface.rightTimelineList != null && !theInterface.rightTimelineList.isEmpty()) theInterface.rightTimelineList.clear();
					else if(theInterface.rightTimelineList == null) theInterface.rightTimelineList = new HashMap<>();
				} catch (IOException ioException) {
					JOptionPane.showMessageDialog(null, "Error while deserializing: " + ioException.getLocalizedMessage());
					return;
				}
			}

			theInterface.updateNames();
		}
	}

	private void openReplay(boolean isOpenReplayLeft) {
		File file = openFilePrompt();

		if(file == null) {
			hasNewReplay = false;
			return;
		}

		hasNewReplay = true;
		hasTimeline = true;

		if(isOpenReplayLeft) {
			if(file.equals(rightReplay)) {
				hasNewReplay = false;
				JOptionPane.showMessageDialog(null, "Replays must be different!");
				return;
			}
		} else {
			if(file.equals(leftReplay)) {
				hasNewReplay = false;
				JOptionPane.showMessageDialog(null, "Replays must be different!");
				return;
			}
		}

		if(file.getName().equals("timelines.json")) {
			try {
				File test = new File(((isOpenReplayLeft) ? LEFT_SIDE : RIGHT_SIDE) + "timelines.json");
				test.getParentFile().mkdirs();

				Files.copy(file.toPath(), test.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ioException) {
				JOptionPane.showMessageDialog(null, "Error: " + Arrays.toString(ioException.getStackTrace()));
				hasNewReplay = false;
				return;
			}
		} else if(file.getName().endsWith(".mcpr")) {
			try {
				ZipFile mcprFile = new ZipFile(file);
				mcprFile.extractFile("timelines.json", (isOpenReplayLeft) ? LEFT_SIDE : RIGHT_SIDE);
			} catch (ZipException zipException) {
				hasTimeline = false;

				JOptionPane.showMessageDialog(null, "Warning:\n" + zipException.getLocalizedMessage() + "\nAssuming the Replay does not contain a timeline.");
			}
		}

		if(isOpenReplayLeft) {
			Main.getInstance().setLeftFile(file);
			leftReplay = file;
		} else {
			Main.getInstance().setRightFile(file);
			rightReplay = file;
		}
	}

	private File openFilePrompt() {
		JFileChooser fileChooser = new JFileChooser(lastOpenedFolder);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().endsWith(".mcpr") || file.getName().equals("timelines.json");
			}

			@Override
			public String getDescription() {
				return "Replay File (*.mcpr, timelines.json)";
			}
		});

		int option = fileChooser.showOpenDialog(null);

		if(option == JFileChooser.APPROVE_OPTION) {
			lastOpenedFolder = fileChooser.getSelectedFile().getParentFile();
			return fileChooser.getSelectedFile();
		}

		return null;
	}
}
