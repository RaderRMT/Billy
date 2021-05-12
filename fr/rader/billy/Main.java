package fr.rader.billy;

import fr.rader.billy.gui.main.MainInterface;
import fr.rader.billy.gui.main.listeners.OpenReplayListener;
import fr.rader.billy.gui.main.listeners.SaveReplayListener;

import javax.swing.*;
import java.io.File;

public class Main {

	private static Main instance;

	private final Logger logger = new Logger();

	public JCheckBoxMenuItem saveToDefaultFolder;

	private void start() {
		if(!logger.logExists()) {
			logger.createLog();
		} else {
			logger.clearLog();
		}

		logger.writeln("Starting Billy");
		logger.writeln("Current date is: " + logger.currentDate());

		logger.writeln("Checking folders...");
		OpenReplayListener.setupPaths();

		// Check if every folders exists
		File toCheck = new File(OpenReplayListener.REPLAY_RECORDINGS);
		if(!toCheck.exists()) JOptionPane.showMessageDialog(null, "The replay_recordings folder does not exist.");

		toCheck = new File(OpenReplayListener.LEFT_SIDE);
		if(!toCheck.exists()) toCheck.mkdirs();

		toCheck = new File(OpenReplayListener.RIGHT_SIDE);
		if(!toCheck.exists()) toCheck.mkdirs();

		logger.writeln("Creating interface");
		createInterface();
	}

	public void createInterface() {
		JFrame frame = new JFrame("Billy - a ReplayMod companion");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");

		// File
		SaveReplayListener saveReplayListener = new SaveReplayListener();
		JMenuItem saveLeftAs = new JMenuItem("Save Left As...");
		JMenuItem saveRightAs = new JMenuItem("Save Right As...");
		saveLeftAs.addActionListener(saveReplayListener);
		saveRightAs.addActionListener(saveReplayListener);
		JMenuItem saveBoth = new JMenuItem("Save Both");
		saveBoth.addActionListener(saveReplayListener);
		JMenuItem saveLeft = new JMenuItem("Save Left");
		saveLeft.addActionListener(saveReplayListener);
		JMenuItem saveRight = new JMenuItem("Save Right");
		saveRight.addActionListener(saveReplayListener);
		saveToDefaultFolder = new JCheckBoxMenuItem("Save timelines to default folder");
		saveToDefaultFolder.setState(true);

		// Help
		JMenuItem aboutItem = new JMenuItem("About");
		// todo: add event to show a "about" popup to describe this program

		fileMenu.add(saveLeftAs);
		fileMenu.add(saveRightAs);
		fileMenu.add(new JSeparator());
		fileMenu.add(saveBoth);
		fileMenu.add(saveLeft);
		fileMenu.add(saveRight);
		fileMenu.add(new JSeparator());
		fileMenu.add(saveToDefaultFolder);

		helpMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		frame.setJMenuBar(menuBar);

		frame.setLocationRelativeTo(null);
		frame.setContentPane(new MainInterface().mainPanel);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}

	private Main() {
		instance = this;

		// Set OS look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		start();
	}

	public static Main getInstance() {
		return instance;
	}

	public Logger getLogger() {
		return this.logger;
	}
}
