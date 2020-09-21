package fr.rader.billy.gui.main.listeners;

import fr.rader.billy.gui.inspector.TimelineInspector;
import fr.rader.billy.gui.main.MainInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditViewTimelineListener implements ActionListener {

	private MainInterface mainInterface;

	@Override
	public void actionPerformed(ActionEvent e) {
		mainInterface = MainInterface.getInstance();

		JFrame frame = new JFrame("Timeline Inspector");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 600);

		frame.setContentPane(new TimelineInspector(mainInterface.leftTimelineList.get(mainInterface.leftNameList.getSelectedValue()), mainInterface.leftNameList.getSelectedValue()).panel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
