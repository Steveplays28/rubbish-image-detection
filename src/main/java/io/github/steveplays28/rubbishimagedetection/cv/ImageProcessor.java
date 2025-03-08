// This file is part of Rubbish Image Detection, licensed under the GNU GPLv3 license.
// Copyright (C) 2024 Karim Keroum, Jaymi Krol and Darion Spaargaren
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <https://www.gnu.org/licenses/>.

package io.github.steveplays28.rubbishimagedetection.cv;

import io.github.steveplays28.rubbishimagedetection.cli.CLIOptions;
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
import java.util.ArrayList;
import java.util.List;

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
		List<Mat> colorFilteredImages = new ArrayList<>();
		for (var color : COLORS) {
			var outputImage = new Mat();
			var colorLowerBound = color.subtract(COLOR_BOUNDS_DEVIATION);
			var colorUpperBound = color.add(COLOR_BOUNDS_DEVIATION);

			Core.inRange(image, new Scalar(colorLowerBound.toDoubleArrayWithAlpha(1d)),
					new Scalar(colorUpperBound.toDoubleArrayWithAlpha(1d)), outputImage
			);
			colorFilteredImages.add(outputImage);
		}

		Mat previousColorFilteredImage = null;
		Mat outputImage = new Mat();
		for (var colorFilteredImage : colorFilteredImages) {
			if (previousColorFilteredImage == null) {
				previousColorFilteredImage = colorFilteredImage;
				continue;
			}

			Core.bitwise_or(previousColorFilteredImage, colorFilteredImage, outputImage);
			previousColorFilteredImage = outputImage;
		}

		// Apply color filtered image as a mask to the original image
		Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_GRAY2BGR);
		Core.bitwise_and(image, outputImage, outputImage);
		Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_BGR2RGB);
		return outputImage;
	}

	private static @NotNull Mat cannyDetectEdges(@NotNull Mat image) {
		Mat cannyEdgeDetectedImage = new Mat();
		Imgproc.cvtColor(image, cannyEdgeDetectedImage, Imgproc.COLOR_RGB2GRAY);
		Imgproc.blur(cannyEdgeDetectedImage, cannyEdgeDetectedImage, new Size(3, 3));
		Imgproc.Canny(cannyEdgeDetectedImage, cannyEdgeDetectedImage, 100, 210, 3, true);

		return cannyEdgeDetectedImage;
	}
}
