package fr.rader.billy.gui.main.listeners;

import fr.rader.billy.gui.main.MainInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReloadReplayListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource().equals(MainInterface.getInstance().reloadLeftReplayButton)) {
			OpenReplayListener.refreshLeft();
		} else {
			OpenReplayListener.refreshRight();
		}
	}
}
