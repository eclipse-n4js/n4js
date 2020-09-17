/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.n4js.ide.xtext.server.DebugService;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.io.FileUtils;

import com.google.common.base.Throwables;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Writes diagnosis information as returned by {@link DebugService#getDebugInfo()} to disk when one of the
 * report-methods is invoked.
 * <p>
 * This is TEMPORARY functionality for debugging that will be removed in the future.
 */
@Singleton
public class ServerIncidentLogger {

	/** Path of the folder where the log files are placed, relative to the user's home directory. */
	public static final Path SERVER_INCIDENTS_FOLDER = Path.of(".n4js", "server-incidents");
	/** Base name of the log file, will be amended by a time stamp. */
	public static final String BASE_FILE_NAME = "server-incident_.log";

	private static final DateFormat TIME_STAMP_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");

	private static final String SEPARATOR = "========================================";

	private static final String NL = System.lineSeparator();

	@Inject
	private DebugService debugService;

	private final FileCreator fileCreator = new FileCreator();

	/** Same as {@link #reportError(String)}, but also including the stack trace of the given throwable. */
	public void reportError(String msg, Throwable th) {
		String stackTraceStr = Throwables.getStackTraceAsString(th);
		reportError(msg + ": " + stackTraceStr);
	}

	/** Write the given message to a report file, including additional diagnosis information. */
	public void reportError(String msg) {
		String debugInfo = debugService.getDebugInfo();
		String fullMsg = msg + NL
				+ SEPARATOR + NL
				+ debugInfo;
		report(fullMsg);
	}

	/** Write the given message to a report file without adding any additional information. */
	public void report(String msg) {
		fileCreator.createFile(msg);
	}

	private static String getTimeStamp() {
		return TIME_STAMP_FORMAT.format(new Date());
	}

	private static String sanitizeTimeStampForFileName(String timeStamp) {
		return timeStamp.replace(":", "").replaceAll("\\W", "_");
	}

	private static class FileCreator {

		private final ExecutorService executorService = Executors.newSingleThreadExecutor();

		private final UUID serverInstanceId = UUID.randomUUID();
		private final String serverInstanceTimeStamp = getTimeStamp();

		private Path outputFolder = null;

		public void createFile(String content) {
			executorService.submit(() -> doCreateFile(content));
		}

		private void doCreateFile(String content) {
			try {
				Path folder = getOrCreateOutputFolder();
				Path file = folder.resolve(BASE_FILE_NAME);
				String timeStamp = getTimeStamp();
				file = FileUtils.appendToFileName(file, sanitizeTimeStampForFileName(timeStamp));
				String actualContent = "Server Instance ID: " + serverInstanceId + NL
						+ "Server Instance Time Stamp: " + serverInstanceTimeStamp + NL
						+ "Incident Time Stamp: " + timeStamp + NL
						+ SEPARATOR + NL
						+ content;
				if (!actualContent.endsWith(NL)) {
					actualContent += NL; // always end the file with a NL
				}
				Files.writeString(file, actualContent, StandardOpenOption.CREATE_NEW);
			} catch (Throwable th) {
				// ignore
			}
		}

		private Path getOrCreateOutputFolder() throws IOException {
			if (outputFolder == null) {
				Path userHomePath = FileUtils.getUserHomeFolder();
				String languageVersion = getLanguageVersion();
				String serverInstanceFolderName = sanitizeTimeStampForFileName(serverInstanceTimeStamp)
						+ "__" + languageVersion
						+ "__" + serverInstanceId.toString().substring(0, 6);
				outputFolder = userHomePath.resolve(SERVER_INCIDENTS_FOLDER).resolve(serverInstanceFolderName);
			}
			Files.createDirectories(outputFolder);
			return outputFolder;
		}

		private String getLanguageVersion() {
			try {
				return N4JSLanguageUtils.getLanguageVersion();
			} catch (Throwable th) {
				return N4JSLanguageUtils.DEFAULT_LANGUAGE_VERSION;
			}
		}
	}
}
