package io.github.steveplays28.rubbishimagedetection.log;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class RIDLog4JConfigurator {
	private static final String RID_CONVERSION_PATTERN = "[%d{HH:mm:ss}] [%t] (%c{1}) %m%n";

	public static void configure() {
		Logger rootLogger = Logger.getRootLogger();
		rootLogger.addAppender(new ConsoleAppender(new PatternLayout(RID_CONVERSION_PATTERN)));
	}
}
