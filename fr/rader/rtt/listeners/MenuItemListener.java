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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class MenuItemListener implements ActionListener {

	private File lastFolderOpened;

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		TimelineSerialization serialization = new TimelineSerialization();
		Interface theInterface = Interface.getInstance();
		Main instance = Main.getInstance();

		try {
			switch(command) {
				case "Save Both":
					if(instance.getLeftFile() == null && instance.getRightFile() == null) return;

					saveTimeline(serialization.serialize(theInterface.leftTimelineList), OpenListener.LEFT_SIDE + "timelines.json", instance.getLeftFile());
					saveTimeline(serialization.serialize(theInterface.rightTimelineList), OpenListener.RIGHT_SIDE + "timelines.json", instance.getRightFile());
					break;
				case "Save Left":
					if(instance.getLeftFile() == null) return;

					saveTimeline(serialization.serialize(theInterface.leftTimelineList), OpenListener.LEFT_SIDE + "timelines.json", instance.getLeftFile());
					break;
				case "Save Right":
					if(instance.getRightFile() == null) return;

					saveTimeline(serialization.serialize(theInterface.rightTimelineList), OpenListener.RIGHT_SIDE + "timelines.json", instance.getRightFile());
					break;
			}
		} catch (IOException ioException) {
			JOptionPane.showMessageDialog(null, ioException.getLocalizedMessage());
		}
	}

	private void saveTimeline(String serializedTimeline, String timelinesFile, File instanceFile) {
		try {
			FileWriter writerLeft = new FileWriter(timelinesFile);

			writerLeft.write(serializedTimeline);
			writerLeft.flush();

			writerLeft.close();

			if(!instanceFile.getName().endsWith(".json")) {
				ZipFile mcprFile = new ZipFile(instanceFile);
				mcprFile.addFile(timelinesFile);

				JOptionPane.showMessageDialog(null, "Saved to " + instanceFile.getAbsolutePath().replace("/", "\\"));
				return;
			}

			if(!Main.getInstance().saveToDefaultFolder.getState()) {
				File file = saveTimelinePrompt();

				if(file == null) return;

				Files.copy(new File(timelinesFile).toPath(), file.toPath());

				JOptionPane.showMessageDialog(null, "Saved to " + file.getAbsolutePath());
				return;
			}

			JOptionPane.showMessageDialog(null, "Saved to " + timelinesFile.replace("/", "\\"));
		} catch (IOException zipException) {
			JOptionPane.showMessageDialog(null, zipException.getLocalizedMessage());
		}
	}

	private File saveTimelinePrompt() {
		JFileChooser fileChooser = new JFileChooser();

		if(lastFolderOpened != null) fileChooser = new JFileChooser(lastFolderOpened);

		fileChooser.setSelectedFile(new File("timelines.json"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().endsWith(".json");
			}

			@Override
			public String getDescription() {
				return "Replay Timeline (*.json)";
			}
		});

		while(true) {
			int option = fileChooser.showSaveDialog(null);

			if(option == JFileChooser.APPROVE_OPTION) {
				File toReturn = fileChooser.getSelectedFile();

				if(!toReturn.getName().endsWith(".json")) {
					toReturn = new File(toReturn.getAbsolutePath() + ".json");
				}

				String fileName = toReturn.getName();
				if(toReturn.getParentFile().listFiles(pathname -> pathname.getName().equals(fileName)).length != 0) {
					if(JOptionPane.showConfirmDialog(null, "A timeline with this name already exists, do you want to overwrite it?", "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
						continue;
					}
				}

				lastFolderOpened = toReturn.getParentFile();

				return toReturn;
			}

			return null;
		}
	}
}
