package io.github.steveplays28.rubbishimagedetection.cv;

import io.github.steveplays28.rubbishimagedetection.Main;
import nu.pattern.OpenCV;

public class OpenCVLoader {
	public static void loadOpenCV() {
		Main.LOGGER.info("Loading OpenCV.");
		OpenCV.loadLocally();
	}
}
