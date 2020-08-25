package fr.rader.rtt;

import fr.rader.rtt.listeners.MenuItemListener;

import javax.swing.*;
import java.io.File;

public class Main {

	private static Main instance;

	private File leftFile;
	private File rightFile;

	public void start() {
		MenuItemListener menuItemListener = new MenuItemListener();

		JFrame frame = new JFrame("Replay Timeline Transfer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");

		JMenuItem saveBoth = new JMenuItem("Save Both");
		saveBoth.addActionListener(menuItemListener);

		JMenuItem saveLeft = new JMenuItem("Save Left");
		saveLeft.addActionListener(menuItemListener);

		JMenuItem saveRight = new JMenuItem("Save Right");
		saveRight.addActionListener(menuItemListener);

		fileMenu.add(saveBoth);
		fileMenu.add(saveLeft);
		fileMenu.add(saveRight);

		menuBar.add(fileMenu);

		frame.setJMenuBar(menuBar);

		frame.setContentPane(new Interface().panel1);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
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

	public File getLeftFile() {
		return leftFile;
	}

	public void setLeftFile(File leftFile) {
		this.leftFile = leftFile;
	}

	public File getRightFile() {
		return rightFile;
	}

	public void setRightFile(File rightFile) {
		this.rightFile = rightFile;
	}
}
