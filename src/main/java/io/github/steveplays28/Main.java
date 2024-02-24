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

package io.github.steveplays28;

import io.github.steveplays28.cli.CLIOptionsParser;
import io.github.steveplays28.cv.ImageProcessor;
import io.github.steveplays28.cv.OpenCVLoader;
import io.github.steveplays28.util.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	public static final Logger LOGGER = LoggerFactory.getLogger("Rubbish Image Detection");
	public static final int COLOR_BOUNDS_DEVIATION = 15;
	public static final Color BLUE_BOTTLE_COLOR = new Color(255, 70, 1);

	public static void main(String[] args) {
		OpenCVLoader.loadOpenCV();
		var cliOptions = CLIOptionsParser.parse();
		ImageProcessor.processImagesInFolder(cliOptions);
	}
}
