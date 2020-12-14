package fr.rader.billy.gui.main.listeners;

import fr.rader.billy.Logger;
import fr.rader.billy.Main;
import fr.rader.billy.gui.main.MainInterface;
import fr.rader.billy.timeline.TimelineSerialization;
import net.lingala.zip4j.ZipFile;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class SaveReplayListener implements ActionListener {

	private Logger logger = Main.getInstance().getLogger();

	private File lastFolderOpened;

	@Override
	public void actionPerformed(ActionEvent e) {
		TimelineSerialization serialization = new TimelineSerialization();
		MainInterface mainInterface = MainInterface.getInstance();
		logger.writeln("Started saving timelines");

		if(e.getSource() instanceof JButton) {
			boolean right = e.getSource().equals(mainInterface.saveRightReplayButton);

				if(right && !mainInterface.saveRightReplayButton.getText().equals("Open Replay")) {
					saveTimeline(serialization.serialize(mainInterface.rightTimelineList),
							OpenReplayListener.RIGHT_SIDE + "timelines.json",
							OpenReplayListener.getRightFile(),
							null);
				} else if(!right && !mainInterface.saveLeftReplayButton.getText().equals("Open Replay")) {
					saveTimeline(serialization.serialize(mainInterface.leftTimelineList),
							OpenReplayListener.LEFT_SIDE + "timelines.json",
							OpenReplayListener.getLeftFile(),
							null);
				}
		} else {
			switch (((JMenuItem) e.getSource()).getText()) {
				case "Save Left As...":
					saveTimeline(serialization.serialize(mainInterface.leftTimelineList),
							OpenReplayListener.LEFT_SIDE + "timelines.json",
							OpenReplayListener.getLeftFile(),
							openFilePrompt());
					break;
				case "Save Right As...":
					saveTimeline(serialization.serialize(mainInterface.rightTimelineList),
							OpenReplayListener.RIGHT_SIDE + "timelines.json",
							OpenReplayListener.getRightFile(),
							openFilePrompt());
					break;
				case "Save Both":
					saveTimeline(serialization.serialize(mainInterface.rightTimelineList),
							OpenReplayListener.RIGHT_SIDE + "timelines.json",
							OpenReplayListener.getRightFile(),
							null);
					saveTimeline(serialization.serialize(mainInterface.leftTimelineList),
							OpenReplayListener.LEFT_SIDE + "timelines.json",
							OpenReplayListener.getLeftFile(),
							null);
					break;
				case "Save Right":
					saveTimeline(serialization.serialize(mainInterface.rightTimelineList),
							OpenReplayListener.RIGHT_SIDE + "timelines.json",
							OpenReplayListener.getRightFile(),
							null);
					break;
				case "Save Left":
					saveTimeline(serialization.serialize(mainInterface.leftTimelineList),
							OpenReplayListener.LEFT_SIDE + "timelines.json",
							OpenReplayListener.getLeftFile(),
							null);
					break;
			}
		}

		logger.writeln("Done!");
	}

	private void saveTimeline(String serializedTimeline, String timelinesFile, File mcprFile, File outputFile) {
		logger.writeln("Saving timelines to '" + mcprFile.getName() + "'");

		try {
			// Writing the serialized timeline in timelines file
			FileWriter writer = new FileWriter(timelinesFile);

			writer.write(serializedTimeline);
			writer.flush();

			writer.close();

			// Writing timeline to mcpr
			if(!mcprFile.getName().endsWith(".json")) {
				if(outputFile != null) {
					Files.copy(mcprFile.toPath(), outputFile.toPath());

					mcprFile = outputFile;
				}

				ZipFile outMcpr = new ZipFile(mcprFile);
				outMcpr.addFile(timelinesFile);

				JOptionPane.showMessageDialog(null, "Saved to " + mcprFile.getAbsolutePath());
				return;
			}

			// Writing timeline to json file
			if(!Main.getInstance().saveToDefaultFolder.getState()) {
				File file = saveTimelinePrompt();

				if(file == null) return;

				Files.copy(new File(timelinesFile).toPath(), file.toPath());

				JOptionPane.showMessageDialog(null, "Saved to " + file.getAbsolutePath());
				return;
			}

			JOptionPane.showMessageDialog(null, "Saved to " + timelinesFile);
		} catch (IOException ioException) {
			logger.exception(ioException);

			JOptionPane.showMessageDialog(null, ioException.getLocalizedMessage());
		}
	}

	private File openFilePrompt() {
		JFileChooser fileChooser = new JFileChooser();

		if(lastFolderOpened != null) fileChooser = new JFileChooser(lastFolderOpened);

		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().endsWith(".mcpr");
			}

			@Override
			public String getDescription() {
				return "Replay File (*.mcpr)";
			}
		});

		if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			lastFolderOpened = fileChooser.getSelectedFile().getParentFile();

			File toReturn = fileChooser.getSelectedFile();

			if(!toReturn.getName().endsWith(".mcpr")) {
				toReturn = new File(toReturn.getAbsolutePath() + ".mcpr");
			}

			return toReturn;
		}

		return null;
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
