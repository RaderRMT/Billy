package fr.rader.rtt.listeners;

import fr.rader.rtt.Interface;
import fr.rader.rtt.Main;
import fr.rader.rtt.timeline.TimelineSerialization;
import net.lingala.zip4j.ZipFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class MenuItemListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		TimelineSerialization serialization = new TimelineSerialization();
		Main instance = Main.getInstance();

		if(instance.getLeftFile() == null || instance.getRightFile() == null) {
			return;
		}

		Interface theInterface = Interface.getInstance();

		switch(command) {
			case "Save Both":
				if(instance.getLeftFile() == null || instance.getRightFile() == null) return;

				try {
					FileWriter writerLeft = new FileWriter(OpenListener.REPLAY_RECORDINGS + "extractor_temp/left/timelines.json");

					writerLeft.write(serialization.serialize(theInterface.leftTimelineList));
					writerLeft.flush();

					writerLeft.close();

					if(!instance.getLeftFile().getName().equals("timelines.json")) {
						ZipFile file = new ZipFile(instance.getLeftFile());
						file.addFile(OpenListener.REPLAY_RECORDINGS + "extractor_temp/left/timelines.json");
					} else {
						JOptionPane.showMessageDialog(null, "Saved to \"" + OpenListener.REPLAY_RECORDINGS + "extractor_temp/left/timelines.json\"");
					}

					FileWriter writerRight = new FileWriter(OpenListener.REPLAY_RECORDINGS + "extractor_temp/right/timelines.json");

					writerRight.write(serialization.serialize(theInterface.rightTimelineList));
					writerRight.flush();

					writerRight.close();

					if(!instance.getRightFile().getName().equals("timelines.json")) {
						ZipFile file = new ZipFile(instance.getRightFile());
						file.addFile(OpenListener.REPLAY_RECORDINGS + "extractor_temp/right/timelines.json");
					} else {
						JOptionPane.showMessageDialog(null, "Saved to \"" + OpenListener.REPLAY_RECORDINGS + "extractor_temp/right/timelines.json\"");
					}
				} catch (IOException ioException) {
					JOptionPane.showMessageDialog(null, ioException.getLocalizedMessage());
				}

				break;
			case "Save Left":
				if(instance.getLeftFile() == null) return;

				try {
					FileWriter writer = new FileWriter(OpenListener.REPLAY_RECORDINGS + "extractor_temp/left/timelines.json");

					writer.write(serialization.serialize(theInterface.leftTimelineList));
					writer.flush();

					writer.close();

					if(!instance.getLeftFile().getName().equals("timelines.json")) {
						ZipFile file = new ZipFile(instance.getLeftFile());
						file.addFile(OpenListener.REPLAY_RECORDINGS + "extractor_temp/left/timelines.json");
					} else {
						JOptionPane.showMessageDialog(null, "Saved to \"" + OpenListener.REPLAY_RECORDINGS + "extractor_temp/left/timelines.json\"");
					}
				} catch (IOException ioException) {
					ioException.printStackTrace();
					JOptionPane.showMessageDialog(null, ioException.getLocalizedMessage());
				}
				break;
			case "Save Right":
				if(instance.getRightFile() == null) return;

				try {
					FileWriter writer = new FileWriter(OpenListener.REPLAY_RECORDINGS + "extractor_temp/right/timelines.json");

					writer.write(serialization.serialize(theInterface.rightTimelineList));
					writer.flush();

					writer.close();

					System.out.println(instance.getRightFile().getAbsolutePath());

					if(!instance.getRightFile().getName().equals("timelines.json")) {
						ZipFile file = new ZipFile(instance.getRightFile());
						file.addFile(OpenListener.REPLAY_RECORDINGS + "extractor_temp/right/timelines.json");
					} else {
						JOptionPane.showMessageDialog(null, "Saved to \"" + OpenListener.REPLAY_RECORDINGS + "extractor_temp/right/timelines.json\"");
					}
				} catch (IOException ioException) {
					JOptionPane.showMessageDialog(null, ioException.getLocalizedMessage());
				}

				break;
		}
	}
}
