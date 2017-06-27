/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.logging;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import org.eclipse.n4js.ui.N4JSUiModule;

/**
 * Register the EclipseAppender. This registration is done in the UI case programmatically on guice injector creation
 * {@link N4JSUiModule#N4JSUiModule(org.eclipse.ui.plugin.AbstractUIPlugin)}.
 *
 * Log-level configuration happens in bundle {@code org.eclipse.n4js.utils.logging}
 */
public class N4jsUiLoggingInitializer {

	/**
	 * Initializes the log4j logging with an additional appender which routes the logging to the Eclipse ErrorView.
	 */
	public static void init() {
		Logger rootLogger = Logger.getRootLogger();

		// # This appender will write to the Eclipse error log. It will ONLY log ERROR and FATAL messages.
		// log4j.appender.eclipse=org.eclipse.xtext.logging.EclipseLogAppender
		EclipseLogAppender eclipseAppender = new EclipseLogAppender();
		eclipseAppender.setName("eclipse");
		rootLogger.addAppender(eclipseAppender);

		// log4j.appender.eclipse.layout=org.apache.log4j.PatternLayout
		// log4j.appender.eclipse.layout.ConversionPattern=%c %x - %m%n
		eclipseAppender.setLayout(new PatternLayout("%c %x - %m%n"));

		// # EclipseLogAppender must not log to the 'eclipse' appender as that would cause a stack overflow!
		// log4j.additivity.org.eclipse.xtext.logging.EclipseLogAppender=false
		// log4j.logger.org.eclipse.xtext.logging.EclipseLogAppender=WARN, default
		Logger eclipseAppenderLogger = Logger.getLogger(EclipseLogAppender.class);
		eclipseAppenderLogger.setAdditivity(false);
		eclipseAppenderLogger.setLevel(Level.WARN);
		Appender defaultAppender = eclipseAppenderLogger.getAppender("default");
		if (eclipseAppenderLogger.getAllAppenders().hasMoreElements()) {
			eclipseAppenderLogger.removeAllAppenders();
		}
		if (defaultAppender != null) {
			eclipseAppenderLogger.addAppender(defaultAppender);
		}
	}
}
