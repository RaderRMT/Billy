package fr.rader.billy.gui.inspector.listeners;

import fr.rader.billy.gui.inspector.TimelineInspector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteKeyframesListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        TimelineInspector inspector = TimelineInspector.getInstance();

        switch(((JButton) e.getSource()).getText()) {
            case "Remove All Position Keyframes":
                inspector.positionPath.getKeyframes().clear();
                break;
            case "Remove All Time Keyframes":
                inspector.timePath.getKeyframes().clear();
                break;
        }
    }
}
