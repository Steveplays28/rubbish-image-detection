package io.github.steveplays28;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Main {
	public static final Logger LOGGER = LoggerFactory.getLogger("Rubbish Image Detection");

	public static void main(String[] args) {
		LOGGER.info("Loading OpenCV.");
		OpenCV.loadShared();

		Mat loadedImage = loadImage(sourceImagePath);

		var mask = new Mat();
	}
}
