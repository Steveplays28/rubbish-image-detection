package io.github.steveplays28.rubbishimagedetection.cli;

import io.github.steveplays28.rubbishimagedetection.Main;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class CLIOptionsParser {
	private static final CommandLineParser COMMAND_LINE_PARSER = new DefaultParser();
	private static final HelpFormatter HELP_FORMATTER = new HelpFormatter();

	private static CommandLine commandLine;

	public static @NotNull CLIOptions parse(String[] args) {
		Options options = new Options();

		Option inputDirectory = new Option("i", "input", true, "Input directory, where unprocessed images are located.");
		options.addOption(inputDirectory);

		Option outputDirectory = new Option("o", "output", true, "Output directory, where processed images will be stored.");
		options.addOption(outputDirectory);

		Option help = new Option("h", "help", false, "Prints the program usage instructions.");
		options.addOption(help);

		try {
			commandLine = COMMAND_LINE_PARSER.parse(options, args);
		} catch (ParseException ex) {
			Main.LOGGER.error(ex.getMessage());
			HELP_FORMATTER.printHelp(Main.PROJECT_ID, options);

			System.exit(1);
		}

		if (commandLine.hasOption(help.getOpt())) {
			HELP_FORMATTER.printHelp(Main.PROJECT_ID, options);
			System.exit(0);
		} else if (commandLine.getOptionValue(inputDirectory.getOpt()) == null || commandLine.getOptionValue(
				outputDirectory.getOpt()) == null) {
			Main.LOGGER.error("Input directory or output directory was not specified. Check if the arguments have the right name.");
			HELP_FORMATTER.printHelp(Main.PROJECT_ID, options);
			System.exit(1);
		}

		return new CLIOptions(
				Path.of(commandLine.getOptionValue(inputDirectory.getOpt())),
				Path.of(commandLine.getOptionValue(outputDirectory.getOpt()))
		);
	}
}
