package io.github.steveplays28.rubbishimagedetection.cv;

import io.github.steveplays28.rubbishimagedetection.cli.CLIOptions;
import io.github.steveplays28.rubbishimagedetection.util.Color;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.github.steveplays28.rubbishimagedetection.Main.*;

public class ImageProcessor {
	public static void processImagesInFolder(@NotNull CLIOptions cliOptions) {
		try (var filePaths = Files.walk(cliOptions.inputDirectory())) {
			filePaths.filter(Files::isRegularFile).forEach(
					path -> processImage(cliOptions.outputDirectory(), parseImageFile(path.toFile())));

			LOGGER.info("Successfully processed all images in {}. The resulting images have been saved in {}.", cliOptions.inputDirectory(),
					cliOptions.outputDirectory()
			);
		} catch (IOException ex) {
			LOGGER.error("Error while traversing unprocessed images folder: ", ex);
		}
	}

	public static @NotNull Image parseImageFile(@NotNull File imageFile) {
		var imageFileName = imageFile.getName();
		return new Image(imageFile, Imgcodecs.imread(imageFile.toString()), FilenameUtils.getBaseName(imageFileName),
				FilenameUtils.getExtension(imageFileName)
		);
	}

	/**
	 * Processes the input image and saves the result to the <code>outputDirectory</code>.
	 *
	 * @param outputDirectory The directory where the resulting image will be saved.
	 * @param image           The image that should be processed.
	 */
	public static void processImage(@NotNull Path outputDirectory, @NotNull Image image) {
		var colorConvertedImage = convertImageColorSpace(image.matrix());
		saveProcessedImage(outputDirectory, image, "color_filtering", filterImageColors(colorConvertedImage));
		saveProcessedImage(outputDirectory, image, "canny_edge_detection", cannyDetectEdges(colorConvertedImage));
	}

	private static void saveProcessedImage(@NotNull Path outputDirectory, @NotNull Image image, @NotNull String fileNameSuffix, @NotNull Mat matrix) {
		Imgcodecs.imwrite(String.format("%s/%s_processed_%s.png", outputDirectory, image.name(), fileNameSuffix), matrix);
	}

	private static @NotNull Mat convertImageColorSpace(@NotNull Mat image) {
		Mat colorSpaceConvertedImage = new Mat();
		Imgproc.cvtColor(image, colorSpaceConvertedImage, Imgproc.COLOR_BGR2RGB, 0);

		return colorSpaceConvertedImage;
	}

	private static @NotNull Mat filterImageColors(@NotNull Mat image) {
		Mat colorFilteredImage = new Mat();
		Core.inRange(image, new Scalar(BLUE_BOTTLE_COLOR.subtract(
						new Color(COLOR_BOUNDS_DEVIATION, COLOR_BOUNDS_DEVIATION, COLOR_BOUNDS_DEVIATION)).toDoubleArrayWithAlpha(1d)), new Scalar(
						BLUE_BOTTLE_COLOR.add(
								new Color(COLOR_BOUNDS_DEVIATION, COLOR_BOUNDS_DEVIATION, COLOR_BOUNDS_DEVIATION)).toDoubleArrayWithAlpha(1d)),
				colorFilteredImage
		);

		return colorFilteredImage;
	}

	private static @NotNull Mat cannyDetectEdges(@NotNull Mat image) {
		Mat cannyEdgeDetectedImage = new Mat();
		Imgproc.cvtColor(image, cannyEdgeDetectedImage, Imgproc.COLOR_RGB2GRAY);
		Imgproc.blur(cannyEdgeDetectedImage, cannyEdgeDetectedImage, new Size(3, 3));
		Imgproc.Canny(cannyEdgeDetectedImage, cannyEdgeDetectedImage, 100, 210, 3, true);

		return cannyEdgeDetectedImage;
	}
}
