package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;
import fr.rader.rtt.timeline.TimelineSerialization;
import net.lingala.zip4j.ZipFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MenuItemListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		TimelineSerialization serialization = new TimelineSerialization();
		Interface theInterface = Interface.getInstance();
		Main instance = Main.getInstance();

		try {
			switch(command) {
				case "Save Both":
					saveTimeline(serialization.serialize(theInterface.leftTimelineList), OpenListener.LEFT_SIDE + "timelines.json", instance.getLeftFile());
					saveTimeline(serialization.serialize(theInterface.rightTimelineList), OpenListener.RIGHT_SIDE + "timelines.json", instance.getRightFile());

					JOptionPane.showMessageDialog(null, "Saved!");
					break;
				case "Save Left":
					saveTimeline(serialization.serialize(theInterface.leftTimelineList), OpenListener.LEFT_SIDE + "timelines.json", instance.getLeftFile());

					JOptionPane.showMessageDialog(null, "Saved!");
					break;
				case "Save Right":
					saveTimeline(serialization.serialize(theInterface.rightTimelineList), OpenListener.RIGHT_SIDE + "timelines.json", instance.getRightFile());

					JOptionPane.showMessageDialog(null, "Saved!");
					break;
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	private void saveTimeline(String serializedTimeline, String timelinesFile, File instanceFile) {
		try {
			FileWriter writerLeft = new FileWriter(timelinesFile);

			writerLeft.write(serializedTimeline);
			writerLeft.flush();

			writerLeft.close();

			if(!instanceFile.getName().equals("timelines.json")) {
				ZipFile mcprFile = new ZipFile(instanceFile);
				mcprFile.addFile(timelinesFile);
			}
		} catch (IOException zipException) {
			JOptionPane.showMessageDialog(null, zipException.getLocalizedMessage());
		}
	}
}
