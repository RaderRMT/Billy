package fr.rader.billy;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Logger {

	private final File LOG_OUTPUT = new File(System.getenv("APPDATA") + "/.minecraft/logs/billy.log");

	private List<String> unusedFields = new ArrayList<>();

	public void writeln(String message) {
		write(message + "\n");
	}

	public void write(String message) {
		if(logExists()) {
			try {
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(LOG_OUTPUT, true)));

				writer.print("[" + currentTime() + "]: " + message);

				writer.flush();
				writer.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	public boolean logExists() {
		return LOG_OUTPUT.exists();
	}

	public boolean createLog() {
		try {
			return LOG_OUTPUT.createNewFile();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		return false;
	}

	public void clearLog() {
		LOG_OUTPUT.delete();

		try {
			LOG_OUTPUT.createNewFile();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public String currentDate() {
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return date.format(formatter);
	}

	public String currentTime() {
		LocalTime date = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		return date.format(formatter);
	}

	public String readProperties(Map<String, Object> properties) {
		String out = "";

		for(Map.Entry<String, Object> property : properties.entrySet()) {
			out += property.getKey() + "=";
			if(property.getValue() instanceof double[]) {
				out += Arrays.toString((double[]) property.getValue()) + "; ";
			} else {
				out += property.getValue() + "; ";
			}
		}

		return out;
	}

	public void exception(Exception exception) {
		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		writeln("Caught exception:\n" + sw.toString().substring(0, sw.toString().length() - 2));
	}

	public void exception(Exception exception, String replay) {
		String message = exception.getLocalizedMessage();
		int index = Integer.parseInt(message.split("column ")[1].split(" ")[0]);
		writeln("Crash occured here (index:" + index + "): " + replay.substring(Math.max(index - 30, 0), index));

		printUnused(replay);
		exception(exception);

		writeln("Timelines dump: " + replay);

		JOptionPane.showMessageDialog(null, "ERROR: " + exception.getLocalizedMessage() +
				"\nPlease open an issue on https://github.com/Nemos59/Billy/issues, and provide the \".minecraft\\logs\\billy.log\" file.");
	}

	public void printUnused(String replay) {
		for(String field : unusedFields) {
			int min = replay.split(field)[0].length() - 50;
			if(min < 0) min = 0;

			writeln("Unused field: " + field + " => " + replay.split(field)[0].substring(min) + field + "\" <- HERE ");
		}
	}

	public void addUnusedField(String field) {
		unusedFields.add(field);
	}

	public void clearUnusedFields() {
		unusedFields.clear();
	}
}
