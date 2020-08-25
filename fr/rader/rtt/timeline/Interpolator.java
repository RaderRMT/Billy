package fr.rader.rtt.timeline;

public class Interpolator {

	private String type;
	private double alpha;
	private String[] properties;

	public Interpolator(String type, double alpha, String[] properties) {
		this.type = type;
		this.alpha = alpha;
		this.properties = properties;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public String[] getProperties() {
		return properties;
	}

	public void setProperties(String[] properties) {
		this.properties = properties;
	}
}
