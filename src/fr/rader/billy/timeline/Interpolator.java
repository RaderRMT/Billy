package fr.rader.billy.timeline;

public class Interpolator {

	private String type;
	private String alpha;
	private String[] properties;

	public Interpolator(String type, String alpha, String[] properties) {
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

	public String getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = String.valueOf(alpha);
	}

	public String[] getProperties() {
		return properties;
	}

	public void setProperties(String[] properties) {
		this.properties = properties;
	}
}
