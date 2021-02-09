package fr.rader.billy.gui.main.listeners;

import com.google.gson.Gson;
import fr.rader.billy.Logger;
import fr.rader.billy.Main;
import fr.rader.billy.gui.main.MainInterface;
import fr.rader.billy.timeline.TimelineSerialization;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OpenReplayListener implements ActionListener {

	private static final Logger logger = Main.getInstance().getLogger();

	public static String REPLAY_RECORDINGS;
	public static String LEFT_SIDE;
	public static String RIGHT_SIDE;

	private static File[] files;

	private static boolean hasTimeline = true;
	private static boolean hasNewReplay = false;

	private static TimelineSerialization serialization;
	private static MainInterface mainInterface;

	public static void setupPaths() {
		String os = System.getProperty("os.name").toLowerCase();

		if(os.contains("windows")) {
			REPLAY_RECORDINGS = System.getenv("APPDATA") + "/.minecraft/replay_recordings/";
		} else if(os.contains("nix") || os.contains("nux") || os.contains("aix")) {
			REPLAY_RECORDINGS = System.getProperty("user.home") + "/.minecraft/replay_recordings/";
		} else if(os.contains("mac")) {
			REPLAY_RECORDINGS = System.getProperty("user.home") + "/Library/Application Support/minecraft/replay_recordings/";
		}

		LEFT_SIDE = REPLAY_RECORDINGS + "extracted_timelines/left/";
		RIGHT_SIDE = REPLAY_RECORDINGS + "extracted_timelines/right/";

		files = new File[] {
				new File(REPLAY_RECORDINGS),
				new File(""),
				new File("")
		};
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		serialization = new TimelineSerialization();
		mainInterface = MainInterface.getInstance();

		boolean isOpenLeft = e.getSource().equals(mainInterface.openLeftReplayButton);
		String side = isOpenLeft ? LEFT_SIDE : RIGHT_SIDE;

		openReplay(openFilePrompt(), side);

		File selectedFile = (isOpenLeft) ? files[1] : files[2];
		if(hasNewReplay) {
			if(selectedFile.getName().endsWith(".json")) startLoadingReplay(side, isOpenLeft, "unknown");
			else startLoadingReplay(side, isOpenLeft, getVersion(selectedFile));
		}
	}

	private static void startLoadingReplay(String side, boolean isOpenLeft, String version) {
		if(isOpenLeft && !files[1].equals(new File(""))) { // left side
			mainInterface.openLeftReplayButton.setText(files[1].getName() + " (" + version + ")");
		} else if(!isOpenLeft && !files[2].equals(new File(""))) { // right side
			mainInterface.openRightReplayButton.setText(files[2].getName() + " (" + version + ")");
		}

		if(isOpenLeft) {
			if(hasTimeline) mainInterface.leftTimelineList = serialization.deserialize(new File(side + "timelines.json"));
			else if(mainInterface.leftTimelineList != null && !mainInterface.leftTimelineList.isEmpty()) mainInterface.leftTimelineList.clear();
			else if(mainInterface.leftTimelineList == null) mainInterface.leftTimelineList = new HashMap<>();
		} else {
			if(hasTimeline) mainInterface.rightTimelineList = serialization.deserialize(new File(side + "timelines.json"));
			else if(mainInterface.rightTimelineList != null && !mainInterface.rightTimelineList.isEmpty()) mainInterface.rightTimelineList.clear();
			else if(mainInterface.rightTimelineList == null) mainInterface.rightTimelineList = new HashMap<>();
		}

		mainInterface.updateNames();
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

		logger.writeln("Opening Replay '" + file.getName() + "' on side " + side);

		if(!hasNewReplay) {
			logger.writeln("Same replay!");
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

		logger.writeln("Done!");
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

	public static String getVersion(File file) {
		try {
			ZipFile mcprFile = new ZipFile(file);
			mcprFile.extractFile("metaData.json", REPLAY_RECORDINGS + "/extracted_timelines/");

			Gson gson = new Gson();

			Map<?, ?> map = gson.fromJson(new FileReader(REPLAY_RECORDINGS + "/extracted_timelines/metaData.json"), Map.class);

			return map.get("mcversion").toString();
		} catch (FileNotFoundException | ZipException e) {
			e.printStackTrace();
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
			logger.writeln("Refreshing left replay...");
			hasNewReplay = true;
			openReplay(files[1], LEFT_SIDE);
			if(files[1].getName().endsWith(".json")) startLoadingReplay(LEFT_SIDE, true, "unknown");
			else startLoadingReplay(LEFT_SIDE, true, getVersion(files[1]));
		}
	}

	public static void refreshRight() {
		if(!files[2].equals(new File(""))) {
			logger.writeln("Refreshing right replay...");
			hasNewReplay = true;
			openReplay(files[2], RIGHT_SIDE);
			if(files[2].getName().endsWith(".json")) startLoadingReplay(RIGHT_SIDE, false, "unknown");
			else startLoadingReplay(RIGHT_SIDE, false, getVersion(files[2]));
		}
	}
}
