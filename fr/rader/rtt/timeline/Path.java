package fr.rader.rtt.timeline;

import java.util.List;

public class Path {

	private List<Keyframe> keyframes;
	private List<Integer> segments;
	private List<Interpolator> interpolators;

	public Path(List<Keyframe> keyframes, List<Integer> segments, List<Interpolator> interpolators) {
		this.keyframes = keyframes;
		this.segments = segments;
		this.interpolators = interpolators;
	}

	public List<Keyframe> getKeyframes() {
		return keyframes;
	}

	public void setKeyframes(List<Keyframe> keyframes) {
		this.keyframes = keyframes;
	}

	public List<Integer> getSegments() {
		return segments;
	}

	public void setSegments(List<Integer> segments) {
		this.segments = segments;
	}

	public List<Interpolator> getInterpolators() {
		return interpolators;
	}

	public void setInterpolators(List<Interpolator> interpolators) {
		this.interpolators = interpolators;
	}
}
