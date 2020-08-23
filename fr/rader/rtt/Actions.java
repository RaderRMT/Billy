package fr.rader.rtt;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Actions implements ActionListener {

	private File tempFolder;

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		Main instance = Main.getInstance();

		switch(command) {
			case "Select Replay 1":
				File mcprToExtractTimeline = openFilePrompt();

				if(mcprToExtractTimeline == null) {
					System.out.println("file 1 is null");
					return;
				}

				instance.setReplayExtractTimelineButtonName(mcprToExtractTimeline.getName());
				tempFolder = new File(mcprToExtractTimeline.getParent() + "/extractor_temp/");
				instance.setMcprToExtractTimeline(mcprToExtractTimeline);

				break;

			case "Select Replay 2":
				File mcprToInsertTimeline = openFilePrompt();

				if(mcprToInsertTimeline == null) {
					System.out.println("file 2 is null");
					return;
				}

				instance.setReplayInsertTimelineButtonName(mcprToInsertTimeline.getName());
				instance.setMcprToInsertTimeline(mcprToInsertTimeline);

				break;

			case "Extract timeline without inserting":
				File file = instance.getMcprToExtractTimeline();

				if(file != null) {
					try {
						ZipFile test = new ZipFile(file);

						test.extractFile("timelines.json", tempFolder.getAbsolutePath());

						JOptionPane.showMessageDialog(null, "Timeline extracted to " + tempFolder.getAbsolutePath());
					} catch (ZipException zipException) {
						zipException.printStackTrace();

						JOptionPane.showMessageDialog(null, "An error occurred while extracting the timeline!");
					}
				}

				break;

			case "Extract and insert timeline":
				File extract = instance.getMcprToExtractTimeline();
				File insert = instance.getMcprToInsertTimeline();

				if(extract != null && insert != null) {
					try {
						ZipFile toExtract = new ZipFile(extract);
						ZipFile toInsert = new ZipFile(insert);

						toExtract.extractFile("timelines.json", tempFolder.getAbsolutePath());

						toInsert.addFile(tempFolder + "/timelines.json");

						JOptionPane.showMessageDialog(null, "Successfully extracted and inserted the timeline!");
					} catch (ZipException zipException) {
						zipException.printStackTrace();

						JOptionPane.showMessageDialog(null, "An error occurred while extracting/inserting the timeline!");
					}
				}
				break;
		}
	}

	private File openFilePrompt() {
		JFileChooser fileChooser = new JFileChooser(System.getenv("APPDATA") + "\\.minecraft\\replay_recordings\\");
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

		int option = fileChooser.showOpenDialog(null);

		if(option == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}

		return null;
	}
}
