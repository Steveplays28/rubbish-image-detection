package io.github.steveplays28;

import nu.pattern.OpenCV;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Main {
	public static final Logger LOGGER = LoggerFactory.getLogger("Rubbish Image Detection");

	public static void main(String[] args) {
		LOGGER.info("Loading OpenCV.");
		OpenCV.loadLocally();
		Mat loadedImage = loadImage("assets/bottles_at_dump.jpg");
		Mat resultImage = new Mat();
		Mat colorConvertedImage = new Mat();
		Imgproc.cvtColor(loadedImage, colorConvertedImage, Imgproc.COLOR_BGR2RGB, 0);
		var resultOk = saveImage("assets/bottles_at_dump_opencv_color_converted.jpg", colorConvertedImage);
		if (resultOk) {
			LOGGER.info("Converted image colors successfully.");
		} else {
			LOGGER.info("Error occurred when saving the color converted image.");
		}

		var upperColorBound = new double[]{38d, 181d, 179d, 1d};
		var lowerColorBound = new double[]{38d, 181d, 179d, 1d};
		Core.inRange(colorConvertedImage, new Scalar(lowerColorBound), new Scalar(upperColorBound), resultImage);

		var resultOk2 = saveImage("assets/bottles_at_dump_opencv_processed.jpg", resultImage);
		if (resultOk2) {
			LOGGER.info("Processed image successfully.");
		} else {
			LOGGER.info("Error occurred when saving the processed image.");
		}
	}

	@NotNull
	public static Mat loadImage(String imagePath) {
		return Imgcodecs.imread(imagePath);
	}

	public static boolean saveImage(String imagePath, Mat image) {
		return Imgcodecs.imwrite(imagePath, image);
	}
}
