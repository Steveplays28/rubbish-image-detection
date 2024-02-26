//    This file is part of Rubbish Image Detection, licensed under the GNU GPLv3 license.
//    Copyright (C) 2024  Karim Keroum, Jaymi Krol, and Darion Spaargaren
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <https://www.gnu.org/licenses/>.

package io.github.steveplays28.rubbishimagedetection;

import io.github.steveplays28.rubbishimagedetection.cli.CLIOptionsParser;
import io.github.steveplays28.rubbishimagedetection.cv.ImageProcessor;
import io.github.steveplays28.rubbishimagedetection.cv.OpenCVLoader;
import io.github.steveplays28.rubbishimagedetection.log.RIDLog4JConfigurator;
import io.github.steveplays28.rubbishimagedetection.util.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static final String PROJECT_ID = "rubbish-image-detection";
	public static final Logger LOGGER = LoggerFactory.getLogger(PROJECT_ID);
	public static final int COLOR_BOUNDS_DEVIATION = 15;
	public static final List<Color> COLORS = new ArrayList<>() {
		{
			// Red balls
			add(new Color(255, 70, 1));
			// Bottles
			add(new Color(72, 125, 149));
			add(new Color(165, 176, 185));
			add(new Color(18, 108, 67));
		}
	};

	public static void main(String[] args) {
		RIDLog4JConfigurator.configure();
		OpenCVLoader.loadOpenCV();
		var cliOptions = CLIOptionsParser.parse(args);
		ImageProcessor.processImagesInFolder(cliOptions);
	}
}
