package fr.rader.billy.gui.inspector.listeners;

import fr.rader.billy.Logger;
import fr.rader.billy.Main;
import fr.rader.billy.gui.inspector.TimelineInspector;
import fr.rader.billy.timeline.Keyframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShiftListener implements ActionListener {

    private final Logger logger = Main.getInstance().getLogger();

    private TimelineInspector timelineInspector;

    @Override
    public void actionPerformed(ActionEvent e) {
        timelineInspector = TimelineInspector.getInstance();

        int shift = (int) timelineInspector.shiftSpinner.getValue();
        if(e.getSource().equals(timelineInspector.shiftAllKeyframesButton)) {
            shiftPositionKeyframes(shift);
            shiftTimeKeyframes(shift);
        } else if(e.getSource().equals(timelineInspector.shiftPositionKeyframesButton)) {
            shiftPositionKeyframes(shift);
        } else if(e.getSource().equals(timelineInspector.shiftTimeKeyframesButton)) {
            shiftTimeKeyframes(shift);
        } else if(e.getSource().equals(timelineInspector.shiftReplayTimestampsButton)) {
            logger.writeln("Shifting replay timestamps: " + shift);

            int oldTimestamp;
            for(Keyframe keyframe : timelineInspector.timePath.getKeyframes()) {
                oldTimestamp = (int) keyframe.getProperties().get("timestamp");
                keyframe.getProperties().replace("timestamp", oldTimestamp + shift);
            }

            oldTimestamp = Integer.parseInt(timelineInspector.replayTimestampField.getText());
            timelineInspector.replayTimestampField.setText(String.valueOf(oldTimestamp + shift));
        }
    }

    private void shiftPositionKeyframes(int shift) {
        logger.writeln("Shifting position keyframes: " + shift);

        for(Keyframe keyframe : timelineInspector.positionPath.getKeyframes()) {
            keyframe.setTime(keyframe.getTime() + shift);
        }

        int oldTimestamp = Integer.parseInt(timelineInspector.positionKeyframeTimestampField.getText());
        timelineInspector.positionKeyframeTimestampField.setText(String.valueOf(oldTimestamp + shift));
    }

    private void shiftTimeKeyframes(int shift) {
        logger.writeln("Shifting time keyframes: " + shift);

        for(Keyframe keyframe : timelineInspector.timePath.getKeyframes()) {
            keyframe.setTime(keyframe.getTime() + shift);
        }

        int oldTimestamp = Integer.parseInt(timelineInspector.timeKeyframeTimestampField.getText());
        timelineInspector.timeKeyframeTimestampField.setText(String.valueOf(oldTimestamp + shift));
    }
}
