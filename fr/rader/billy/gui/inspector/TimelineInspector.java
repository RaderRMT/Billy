package fr.rader.billy.gui.inspector;

import fr.rader.billy.gui.inspector.listeners.*;
import fr.rader.billy.timeline.Path;
import fr.rader.billy.timeline.Timeline;

import javax.swing.*;

public class TimelineInspector {

    // Main panel and timeline infos
    public JPanel panel;
    private JLabel timelineNameLabel;
    private JLabel positionKeyframeLabel;
    private JLabel timeKeyframesLabel;
    public JTextField timelineNameField;
    public JTextField positionKeyframesAmountField;
    public JTextField timeKeyframesAmountField;

    // Timestamp Data Panel
    public JPanel timestampDataPanel;
    public JLabel selectedPositionKeyframeLabel;
    public JLabel selectedTimeKeyframeLabel;
    public JComboBox<String> selectedPositionKeyframeCombo;
    public JComboBox<String> selectedTimeKeyframeCombo;

    private JLabel positionKeyframeTimestampLabel;
    public JTextField positionKeyframeTimestampField;

    private JLabel cameraRotationLabel;
    private JLabel yawLabel;
    private JLabel pitchLabel;
    private JLabel rollLabel;
    public JTextField yawField;
    public JTextField pitchField;
    public JTextField rollField;

    private JLabel cameraPositionLabel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel zLabel;
    public JTextField xField;
    public JTextField yField;
    public JTextField zField;

    private JLabel isSpectatorKeyframeLabel;
    public JCheckBox isSpectatorKeyframeCheck;

    private JLabel replayTimestampLabel;
    private JLabel timeKeyframeLabel;
    public JTextField replayTimestampField;
    public JTextField timeKeyframeTimestampField;

    // Move path
    public JPanel movePathPanel;
    public JButton savePositionKeyframeButton;
    public JButton movePathButton;
    public JButton saveTimeKeyframeButton;

    private JLabel oldCoordsLabel;
    private JLabel oldXLabel;
    private JLabel oldYLabel;
    private JLabel oldZLabel;
    public JTextField oldXField;
    public JTextField oldYField;
    public JTextField oldZField;

    private JLabel newCoordsLabel;
    private JLabel newXLabel;
    private JLabel newYLabel;
    private JLabel newZLabel;
    public JTextField newXField;
    public JTextField newYField;
    public JTextField newZField;

    // Shift Keyframes
    public JPanel shiftPanel;
    private JLabel shiftLabel;
    public JButton shiftPositionKeyframesButton;
    public JButton shiftTimeKeyframesButton;
    public JButton shiftReplayTimestampsButton;
    public JButton shiftAllKeyframesButton;
    public JSpinner shiftSpinner;
    private JButton removePositionKeyframeButton;
    private JButton removeTimeKeyframeButton;

    // Not GUI related:
    private static TimelineInspector instance;

    public Path timePath;
    public Path positionPath;

    public TimelineInspector(Timeline timeline, String name) {
        instance = this;

        timePath = timeline.getPaths().get(0);
        positionPath = timeline.getPaths().get(1);

        timelineNameField.setText(name);

        positionKeyframesAmountField.setText(String.valueOf(positionPath.getKeyframes().size()));
        timeKeyframesAmountField.setText(String.valueOf(timePath.getKeyframes().size()));

        // Adding listeners
        selectedPositionKeyframeCombo.addActionListener(new PositionKeyframeListener());
        selectedTimeKeyframeCombo.addActionListener(new TimeKeyframeListener());

        SaveKeyframeListener saveKeyframeListener = new SaveKeyframeListener();
        savePositionKeyframeButton.addActionListener(saveKeyframeListener);
        saveTimeKeyframeButton.addActionListener(saveKeyframeListener);

        ShiftListener shiftListener = new ShiftListener();
        shiftAllKeyframesButton.addActionListener(shiftListener);
        shiftPositionKeyframesButton.addActionListener(shiftListener);
        shiftReplayTimestampsButton.addActionListener(shiftListener);
        shiftTimeKeyframesButton.addActionListener(shiftListener);

        movePathButton.addActionListener(new MovePathListener());

        DeleteKeyframesListener deleteKeyframesListener = new DeleteKeyframesListener();
        removePositionKeyframeButton.addActionListener(deleteKeyframesListener);
        removeTimeKeyframeButton.addActionListener(deleteKeyframesListener);

        for(int x = 0; x < positionPath.getKeyframes().size(); x++) {
            selectedPositionKeyframeCombo.addItem(String.valueOf(x));
        }

        for(int x = 0; x < timePath.getKeyframes().size(); x++) {
            selectedTimeKeyframeCombo.addItem(String.valueOf(x));
        }
    }

    public static TimelineInspector getInstance() {
        return instance;
    }
}
