package io.github.steveplays28;

import io.github.steveplays28.util.Color;
import nu.pattern.OpenCV;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
	public static final Logger LOGGER = LoggerFactory.getLogger("Rubbish Image Detection");
	public static final String ASSETS_PATH = "assets";
	public static final String UNPROCESSED_IMAGES_PATH = String.format("%s/unprocessed", ASSETS_PATH);
	public static final String PROCESSED_IMAGES_PATH = String.format("%s/processed", ASSETS_PATH);
	public static final int COLOR_BOUNDS_DEVIATION = 15;
	public static final Color BLUE_BOTTLE_COLOR = new Color(255, 70, 1);

	public static void main(String[] args) {
		LOGGER.info("Loading OpenCV.");
		OpenCV.loadLocally();

		try (var filePaths = Files.walk(Path.of(UNPROCESSED_IMAGES_PATH))) {
			filePaths.filter(Files::isRegularFile).forEach(Main::processImage);
		} catch (IOException ex) {
			LOGGER.error("Error while traversing unprocessed images folder: ", ex);
		}
	}

	@NotNull
	public static Mat loadImage(String imagePath) {
		return Imgcodecs.imread(imagePath);
	}

	public static boolean saveImage(String imagePath, Mat image) {
		return Imgcodecs.imwrite(imagePath, image);
	}

	private static void processImage(@NotNull Path filePath) {
		Mat loadedImage = loadImage(filePath.toString());
		Mat resultImage = new Mat();
		Mat colorConvertedImage = new Mat();
		Imgproc.cvtColor(loadedImage, colorConvertedImage, Imgproc.COLOR_BGR2RGB, 0);
		var resultOk = saveImage(
				String.format(
						"%s/%s_color_converted.%s", PROCESSED_IMAGES_PATH, FilenameUtils.getBaseName(filePath.toString()),
						FilenameUtils.getExtension(filePath.toString())
				), colorConvertedImage);
		if (resultOk) {
			LOGGER.info("Converted image colors successfully.");
		} else {
			LOGGER.info("Error occurred when saving the color converted image.");
		}

		Core.inRange(
				colorConvertedImage, new Scalar(getColorLowerBound(BLUE_BOTTLE_COLOR).toDoubleArrayWithAlpha(1d)),
				new Scalar(getColorUpperBound(BLUE_BOTTLE_COLOR).toDoubleArrayWithAlpha(1d)), resultImage
		);

		var resultOk2 = saveImage(String.format(
				"%s/%s_processed.%s", PROCESSED_IMAGES_PATH, FilenameUtils.getBaseName(filePath.toString()),
				FilenameUtils.getExtension(filePath.toString())
		), resultImage);
		if (resultOk2) {
			LOGGER.info("Processed image successfully.");
		} else {
			LOGGER.info("Error occurred when saving the processed image.");
		}
	}

	public static Color getColorUpperBound(@NotNull Color color) {
		return color.add(new Color(COLOR_BOUNDS_DEVIATION, COLOR_BOUNDS_DEVIATION, COLOR_BOUNDS_DEVIATION));
	}

	public static Color getColorLowerBound(@NotNull Color color) {
		return color.subtract(new Color(COLOR_BOUNDS_DEVIATION, COLOR_BOUNDS_DEVIATION, COLOR_BOUNDS_DEVIATION));
	}
}
