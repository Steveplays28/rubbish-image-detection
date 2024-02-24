package io.github.steveplays28.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class CLIOptionsParser {
	public static @NotNull CLIOptions parse() {
		Options options = new Options();

		Option inputDirectory = new Option("i", "input", true, "Input directory, where unprocessed images are located.");
		inputDirectory.setRequired(true);
		options.addOption(inputDirectory);

		Option outputDirectory = new Option("o", "output", true, "Output directory, where processed images will be stored.");
		outputDirectory.setRequired(true);
		options.addOption(outputDirectory);

		return new CLIOptions(Path.of(inputDirectory.getValue()), Path.of(outputDirectory.getValue()));
	}
}
