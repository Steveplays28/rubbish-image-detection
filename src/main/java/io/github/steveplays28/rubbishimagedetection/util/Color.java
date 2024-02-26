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

package io.github.steveplays28.rubbishimagedetection.util;

import org.jetbrains.annotations.NotNull;

import static io.github.steveplays28.rubbishimagedetection.util.MathUtil.clamp;

/**
 * Color class that takes in red, green and blue values between 0-255. Values outside of this range are truncated.
 */
public class Color {
	public int red;
	public int green;
	public int blue;

	public Color(int red, int green, int blue) {
		this.red = clamp(red, 0, 255);
		this.green = clamp(green, 0, 255);
		this.blue = clamp(blue, 0, 255);
	}

	public Color(int hex) {
		red = (hex >> 16) & 0xFF;
		green = (hex >> 8) & 0xFF;
		blue = hex & 0xFF;
	}

	public Color(@NotNull Color color) {
		red = color.red;
		green = color.green;
		blue = color.blue;
	}

	@Override
	public String toString() {
		return String.format("Color(%s, %s, %s)", red, green, blue);
	}

	public int toInt() {
		int hex = red;
		hex = (hex << 8) + green;
		hex = (hex << 8) + blue;

		return hex;
	}

	public double[] toDoubleArrayWithAlpha(double alpha) {
		return new double[]{this.red, this.green, this.blue, alpha};
	}

	public Color multiply(@NotNull Color color) {
		var newColor = new Color(this);

		newColor.red = clamp(newColor.red * color.red, 0, 255);
		newColor.green = clamp(newColor.green * color.green, 0, 255);
		newColor.blue = clamp(newColor.blue * color.blue, 0, 255);

		return newColor;
	}

	public Color add(@NotNull Color color) {
		var newColor = new Color(this);

		newColor.red = clamp(newColor.red + color.red, 0, 255);
		newColor.green = clamp(newColor.green + color.green, 0, 255);
		newColor.blue = clamp(newColor.blue + color.blue, 0, 255);

		return newColor;
	}

	public @NotNull Color add(int value) {
		var newColor = new Color(this);

		newColor.red = clamp(newColor.red + value, 0, 255);
		newColor.green = clamp(newColor.green + value, 0, 255);
		newColor.blue = clamp(newColor.blue + value, 0, 255);

		return newColor;
	}

	public Color subtract(@NotNull Color color) {
		var newColor = new Color(this);

		newColor.red = clamp(newColor.red - color.red, 0, 255);
		newColor.green = clamp(newColor.green - color.green, 0, 255);
		newColor.blue = clamp(newColor.blue - color.blue, 0, 255);

		return newColor;
	}

	public @NotNull Color subtract(int value) {
		var newColor = new Color(this);

		newColor.red = clamp(newColor.red - value, 0, 255);
		newColor.green = clamp(newColor.green - value, 0, 255);
		newColor.blue = clamp(newColor.blue - value, 0, 255);

		return newColor;
	}

	public Color invert() {
		var newColor = new Color(this);

		newColor.red = clamp(255 - this.red, 0, 255);
		newColor.green = clamp(255 - this.green, 0, 255);
		newColor.blue = clamp(255 - this.blue, 0, 255);

		return newColor;
	}
}
