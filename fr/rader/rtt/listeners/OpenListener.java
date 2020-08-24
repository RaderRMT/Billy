package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;
import fr.rader.rtt.timeline.TimelineSerialization;
import net.lingala.zip4j.ZipFile;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public class OpenListener implements ActionListener {

	public static String REPLAY_RECORDINGS = System.getenv("APPDATA") + "\\.minecraft\\replay_recordings\\";

	private Interface theInterface;

	private File leftFile;
	private File rightFile;

	@Override
	public void actionPerformed(ActionEvent e) {
		TimelineSerialization serialization = new TimelineSerialization();
		theInterface = Interface.getInstance();

		if(e.getSource().equals(theInterface.openReplayLeft)) {
			leftFile = openFilePrompt();

			if(leftFile == null) return;

			if(leftFile.equals(rightFile)) {
				JOptionPane.showMessageDialog(null, "Replays must be different!");
				return;
			}

			theInterface.openReplayLeft.setText(leftFile.getName());

			if(!leftFile.getName().equals("timelines.json")) {
				try {
					ZipFile zipFile = new ZipFile(leftFile);
					zipFile.extractFile("timelines.json", REPLAY_RECORDINGS + "extractor_temp/left/");
				} catch (IOException ioException) {
					JOptionPane.showMessageDialog(null, "\"" + leftFile.getName() + "\" does not contain a timeline!");
					theInterface.openReplayLeft.setText("Open Replay 1");
					return;
				}
			} else {
				try {
					Files.copy(leftFile.toPath(), new File(REPLAY_RECORDINGS + "extractor_temp/left/timelines.json").toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException ioException) {
					System.out.println("could not copy file");
					ioException.printStackTrace();
					return;
				}
			}

			Main.getInstance().setLeftFile(leftFile);

			try {
				theInterface.leftTimelineList = serialization.deserialize(new File(REPLAY_RECORDINGS + "extractor_temp/left/timelines.json"));

				theInterface.updateNames();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}

		if(e.getSource().equals(theInterface.openReplayRight)) {
			rightFile = openFilePrompt();

			if(rightFile == null) return;

			if(rightFile.equals(leftFile)) {
				JOptionPane.showMessageDialog(null, "Replays must be different!");
				return;
			}

			Main.getInstance().setRightFile(rightFile);

			theInterface.openReplayRight.setText(rightFile.getName());

			if(!rightFile.getName().equals("timelines.json")) {
				try {
					ZipFile zipFile = new ZipFile(rightFile);
					zipFile.extractFile("timelines.json", REPLAY_RECORDINGS + "extractor_temp/right/");
				} catch (IOException ignored) {
					theInterface.rightTimelineList = new HashMap<>();
					theInterface.rightList.setListData(new String[] {});
					return;
				}
			} else {
				try {
					Files.copy(rightFile.toPath(), new File(REPLAY_RECORDINGS + "extractor_temp/right/timelines.json").toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException ioException) {
					System.out.println("could not copy file");
					ioException.printStackTrace();
					return;
				}
			}

			try {
				theInterface.rightTimelineList = serialization.deserialize(new File(REPLAY_RECORDINGS + "extractor_temp/right/timelines.json"));

				theInterface.updateNames();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	private File openFilePrompt() {
		JFileChooser fileChooser = new JFileChooser(System.getenv("APPDATA") + "\\.minecraft\\replay_recordings\\");
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
			return fileChooser.getSelectedFile();
		}

		return null;
	}
}
