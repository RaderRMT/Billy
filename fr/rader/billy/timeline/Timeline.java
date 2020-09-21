package fr.rader.billy.timeline;

import java.util.List;

public class Timeline {

	private List<Path> paths;

	public Timeline(List<Path> paths) {
		this.paths = paths;
	}

	public List<Path> getPaths() {
		return paths;
	}

	public void setPaths(List<Path> paths) {
		this.paths = paths;
	}
}
