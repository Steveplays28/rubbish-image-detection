package io.github.steveplays28.cv;

import io.github.steveplays28.Main;
import nu.pattern.OpenCV;

public class OpenCVLoader {
	public static void loadOpenCV() {
		Main.LOGGER.info("Loading OpenCV.");
		OpenCV.loadLocally();
	}
}
