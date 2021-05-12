package fr.rader.billy.timeline;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.rader.billy.Logger;
import fr.rader.billy.Main;

import java.io.*;
import java.util.*;

public class TimelineSerialization {

	private final Logger logger = Main.getInstance().getLogger();

	public String serialize(Map<String, Timeline> timelines) {
		StringWriter stringWriter = new StringWriter();
		JsonWriter writer = new JsonWriter(stringWriter);

		logger.writeln("Started serializing timelines");

		// writing timeline
		try {
			writer.beginObject();
			for(Map.Entry<String, Timeline> entry : timelines.entrySet()) {
				logger.writeln("Serializing timeline '" + entry.getKey() + "'");

				Timeline timeline = entry.getValue();

				String timelineName = entry.getKey();
				if(timelineName.equals("\uD835\uDE25\uD835\uDE26\uD835\uDE27\uD835\uDE22\uD835\uDE36\uD835\uDE2D\uD835\uDE35")) {
					timelineName = "";
				}

				writer.name(timelineName).beginArray();

				// writing paths
				for(Path path : timeline.getPaths()) {
					writer.beginObject();
					writer.name("keyframes").beginArray();

					// writing keyframes
					for(Keyframe keyframe : path.getKeyframes()) {
						writer.beginObject();
						writer.name("time").value(keyframe.getTime());
						writer.name("properties").beginObject();

						for(Map.Entry<String, Object> property : keyframe.getProperties().entrySet()) {
							if(property.getKey().equals("timestamp")) {
								writer.name("timestamp").value((Integer) property.getValue());
							}

							if(property.getKey().equals("spectate")) {
								writer.name("spectate").value((Integer) property.getValue());
							}

							if(property.getKey().equals("interpolationFixed")) {
								writer.name("interpolationFixed").nullValue();
							}

							if(property.getKey().equals("camera:rotation") || property.getKey().equals("camera:position")) {
								writer.name(property.getKey()).beginArray();

								for(double value : (double[]) property.getValue()) {
									writer.value(value);
								}

								writer.endArray();
							}
						}

						writer.endObject();
						writer.endObject();
					}

					writer.endArray();

					// writing segments
					writer.name("segments").beginArray();
					for(int segment : path.getSegments()) {
						writer.value(segment);
					}

					writer.endArray();

					// writing interpolators
					writer.name("interpolators").beginArray();
					for(Interpolator interpolator : path.getInterpolators()) {
						writer.beginObject();
						writer.name("type");

						if(interpolator.getAlpha() == null) {
							writer.value(interpolator.getType());
						} else {
							writer.beginObject();
							writer.name("type").value(interpolator.getType());
							writer.name("alpha").value(Double.parseDouble(interpolator.getAlpha()));

							writer.endObject();
						}

						writer.name("properties").beginArray();
						for(String property : interpolator.getProperties()) {
							if(property != null) {
								writer.value(property);
							}
						}

						writer.endArray();
						writer.endObject();
					}

					writer.endArray();
					writer.endObject();
				}

				writer.endArray();
			}

			writer.endObject();
			writer.flush();

			logger.writeln("Done!");
		} catch (Exception exception) {
			logger.exception(exception);
		}

		return stringWriter.toString();
	}

	public Map<String, Timeline> deserialize(File timelineFile) {
		String replay = timelineToString(timelineFile);

		if(replay == null) return null;

		JsonReader reader = new JsonReader(new StringReader(replay));
		Map<String, Timeline> timelines = new LinkedHashMap<>();

		logger.writeln("Started deserializing timelines");
		logger.clearUnusedFields();

		try {
			reader.beginObject();
			while(reader.hasNext()) {
				String timelineName = reader.nextName();

				if(timelineName.equals("")) {
					timelineName = "\uD835\uDE25\uD835\uDE26\uD835\uDE27\uD835\uDE22\uD835\uDE36\uD835\uDE2D\uD835\uDE35";
				}

				List<Path> paths = new ArrayList<>();

				logger.writeln("Deserializing timeline '" + timelineName + "'");

				// Read timelines
				reader.beginArray();
				while(reader.hasNext()) {
					List<Keyframe> keyframes = new ArrayList<>();
					List<Integer> segments = new ArrayList<>();
					List<Interpolator> interpolators = new ArrayList<>();

					// Read Paths
					reader.beginObject();
					while(reader.hasNext()) {
						switch(reader.nextName()) {
							case "keyframes":
								long time = 0;
								Map<String, Object> keyframeProperties;

								// Read Keyframe
								reader.beginArray();
								while(reader.hasNext()) {
									keyframeProperties = new HashMap<>();

									reader.beginObject();
									while(reader.hasNext()) {
										String nextName = reader.nextName();

										switch(nextName) {
											case "time":
												time = reader.nextLong();
												break;

											case "properties":
												reader.beginObject();
												while(reader.hasNext()) {
													String nextNameProperty = reader.nextName();

													switch(nextNameProperty) {
														case "timestamp":
															keyframeProperties.put("timestamp", reader.nextInt());
															break;

														case "spectate":
															keyframeProperties.put("spectate", reader.nextInt());
															break;

														case "interpolationFixed":
															keyframeProperties.put("interpolationFixed", null);
															reader.nextNull();
															break;

														case "camera:rotation":
														case "camera:position":
															double[] cameraProperties = new double[3];
															byte i = 0;

															reader.beginArray();
															while(reader.hasNext()) {
																cameraProperties[i] = reader.nextDouble();
																i++;
															}

															keyframeProperties.put(nextNameProperty, cameraProperties);

															reader.endArray();
															break;

														default:
															logger.writeln("Found unused field in code (keyframe property): " + nextNameProperty);
															logger.addUnusedField(nextNameProperty);
															break;
													}
												}

												reader.endObject();

												break;

											default:
												logger.writeln("Found unused field in code (keyframe): " + nextName);
												logger.addUnusedField(nextName);
												break;
										}
									}

									keyframes.add(new Keyframe(time, keyframeProperties));

									reader.endObject();
								}

								reader.endArray();
								break;

							case "segments":
								reader.beginArray();
								while(reader.hasNext()) {
									segments.add(reader.nextInt());
								}

								reader.endArray();
								break;

							case "interpolators":
								reader.beginArray();
								while(reader.hasNext()) {
									String type = null;
									String alpha = null;
									String[] cameraProperties = new String[2];

									// Read interpolator
									reader.beginObject();
									while(reader.hasNext()) {
										String nextName = reader.nextName();

										switch(nextName) {
											case "type":
												try {
													reader.beginObject();
													while(reader.hasNext()) {
														String nextNameType = reader.nextName();

														switch(nextNameType) {
															case "type":
																type = reader.nextString();
																break;
															case "alpha":
																alpha = String.valueOf(reader.nextDouble());
																break;

															default:
																logger.writeln("Found unused field in code (interpolator type): " + nextNameType);
																logger.addUnusedField(nextNameType);
																break;
														}
													}

													reader.endObject();
												} catch(IllegalStateException e) {
													type = reader.nextString();
												}
												break;
											case "properties":
												byte i = 0;

												reader.beginArray();
												while(reader.hasNext()) {
													cameraProperties[i] = reader.nextString();
													i++;
												}

												reader.endArray();
												break;

											default:
												logger.writeln("Found unused field in code (interpolator): " + nextName);
												logger.addUnusedField(nextName);
												break;
										}
									}

									interpolators.add(new Interpolator(type, alpha, cameraProperties));

									reader.endObject();
								}

								reader.endArray();
								break;
						}
					}

					reader.endObject();

					paths.add(new Path(keyframes, segments, interpolators));
				}

				reader.endArray();

				timelines.put(timelineName, new Timeline(paths));
			}

			reader.endObject();

			logger.writeln("Done!");
			logger.printUnused(replay);
		} catch (Exception exception) {
			logger.exception(exception, replay);
		}

		return timelines;
	}

	private String timelineToString(File timelineFile) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(timelineFile));

			String line = reader.readLine();

			reader.close();

			return line;
		} catch (IOException e) {
			logger.exception(e);
		}

		return null;
	}
}
