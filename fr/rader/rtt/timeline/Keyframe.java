package fr.rader.rtt.timeline;

import java.util.Map;

public class Keyframe {

	private long time;
	private Map<String, Object> properties;

	public Keyframe(long time, Map<String, Object> properties) {
		this.time = time;
		this.properties = properties;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
}
