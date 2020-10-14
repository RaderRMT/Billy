package fr.rader.billy.gui.main.listeners;

import fr.rader.billy.gui.inspector.TimelineInspector;
import fr.rader.billy.gui.main.MainInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class EditViewTimelineListener implements ActionListener {

	private MainInterface mainInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		mainInterface = MainInterface.getInstance();

		if(e.getSource().equals(mainInterface.editLeftTimelineButton)) {
			if(OpenReplayListener.getLeftFile().equals(new File(""))) return;
			if(mainInterface.leftNameList.getSelectedValuesList().size() == 0) return;
		} else {
			if(OpenReplayListener.getRightFile().equals(new File("")) && e.getSource().equals(mainInterface.editRightTimelineButton)) return;
			if(mainInterface.rightNameList.getSelectedValuesList().size() == 0) return;
		}

		JFrame frame = new JFrame("Timeline Inspector");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 600);

		if(e.getSource().equals(mainInterface.editLeftTimelineButton)) {
			frame.setContentPane(new TimelineInspector(mainInterface.leftTimelineList.get(mainInterface.leftNameList.getSelectedValue()), mainInterface.leftNameList.getSelectedValue()).panel);
		} else {
			frame.setContentPane(new TimelineInspector(mainInterface.rightTimelineList.get(mainInterface.rightNameList.getSelectedValue()), mainInterface.rightNameList.getSelectedValue()).panel);
		}

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
