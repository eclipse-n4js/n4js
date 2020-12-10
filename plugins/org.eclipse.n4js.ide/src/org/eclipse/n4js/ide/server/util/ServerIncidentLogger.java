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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
 * TODO GH-2002: this is TEMPORARY functionality for debugging that will be removed in the future.
 */
@Singleton
public class ServerIncidentLogger {

	/** Path of the folder where the log files are placed, relative to the user's home directory. */
	public static final Path SERVER_INCIDENTS_FOLDER = Path.of(".n4js", "server-incidents");
	/** Base name of the log file. The name will be amended by '_' followed by a time stamp. */
	public static final String DEFAULT_BASE_FILE_NAME = "server-incident";
	/** File extension of the log file name. */
	public static final String FILE_EXTENSION = ".log";

	/**
	 * When receiving more than {@link #SUSPENSION_COUNT} incidents within {@link #SUSPENSION_INTERVAL} seconds,
	 * reporting will be suspended until not receiving any further incidents for {@link #SUSPENSION_DURATION} seconds.
	 */
	public static final int SUSPENSION_COUNT = 30;
	/** @see #SUSPENSION_COUNT */
	public static final int SUSPENSION_INTERVAL = 30;
	/** @see #SUSPENSION_COUNT */
	public static final long SUSPENSION_DURATION = 120;

	private static final DateFormat TIME_STAMP_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");

	private static final String SEPARATOR = "========================================";

	private static final String NL = System.lineSeparator();

	@Inject
	private DebugService debugService;

	private final SuspensionTracker suspensionTracker = new SuspensionTracker();
	private final FileCreator fileCreator = new FileCreator();

	/**
	 * Write a report file.
	 *
	 * @param msg
	 *            message to report
	 * @param includeDebugInfo
	 *            iff true the debug info will be written to the output as well
	 */
	public void report(String msg, boolean includeDebugInfo) {
		doReport(DEFAULT_BASE_FILE_NAME, msg, includeDebugInfo, null);
	}

	/**
	 * Same as {@link #report(String, boolean)}, with additional parameter:
	 *
	 * @param baseFileName
	 *            file name prefix of generated log file
	 */
	public void reportWithFileBaseName(String baseFileName, String msg, boolean includeDebugInfo) {
		doReport(baseFileName, msg, includeDebugInfo, null);
	}

	/**
	 * Same as {@link #report(String, boolean)}, with {@code includeDebugInfo} set to {@code true}. Also including the
	 * stack trace of the given throwable.
	 */
	public void reportError(String msg, Throwable th) {
		doReport(DEFAULT_BASE_FILE_NAME, msg, true, th);
	}

	private void doReport(String baseFileName, String msg, boolean includeDebugInfo, Throwable th) {
		long timeStamp = System.currentTimeMillis();
		if (suspensionTracker.isSuspended(timeStamp, fileCreator)) {
			return; // exit before compiling debug info, etc.
		}
		StringBuilder sb = new StringBuilder();
		sb.append(msg);
		if (th != null) {
			String stackTraceStr = Throwables.getStackTraceAsString(th);
			sb.append(": ");
			sb.append(stackTraceStr);
		}
		if (includeDebugInfo) {
			String debugInfo = debugService.getDebugInfo();
			sb.append(NL);
			sb.append(SEPARATOR);
			sb.append(NL);
			sb.append(debugInfo);
		}
		String fullMsg = sb.toString();
		fileCreator.createFile(baseFileName, timeStamp, fullMsg);
	}

	private static String getTimeStampString(long timeStamp) {
		return TIME_STAMP_FORMAT.format(new Date(timeStamp));
	}

	private static String sanitizeTimeStampForFileName(String timeStampStr) {
		return timeStampStr.replace(":", "").replaceAll("\\W", "_");
	}

	private static class SuspensionTracker {

		private long suspendedUntil = 0;
		private final List<Long> timeStampHistory = new ArrayList<>();

		/** See {@link ServerIncidentLogger#SUSPENSION_COUNT SUSPENSION_COUNT} for details. */
		public synchronized boolean isSuspended(long timeStamp, FileCreator fileCreator) {
			boolean isSuspended = timeStamp < suspendedUntil;
			if (!isSuspended) {
				timeStampHistory.add(timeStamp);
				long historyStartTime = timeStamp - TimeUnit.SECONDS.toMillis(SUSPENSION_INTERVAL);
				timeStampHistory.removeIf(t -> t < historyStartTime);
				isSuspended = timeStampHistory.size() > SUSPENSION_COUNT;
				if (isSuspended) {
					timeStampHistory.clear();
					if (fileCreator != null) {
						String msg = "Reporting is being suspended due to receiving more than "
								+ SUSPENSION_COUNT + " incidents within " + SUSPENSION_INTERVAL + " seconds.\n"
								+ "This will be in effect until not receiving any further incidents for "
								+ SUSPENSION_DURATION + " seconds.";
						fileCreator.createFile(timeStamp, msg, "__SUSPENDED");
					}
				}
			}
			if (isSuspended) {
				suspendedUntil = timeStamp + TimeUnit.SECONDS.toMillis(SUSPENSION_DURATION);
				return true;
			}
			return false;
		}
	}

	private static class FileCreator {

		private final ExecutorService executorService = Executors.newSingleThreadExecutor();

		private final UUID serverInstanceId = UUID.randomUUID();
		private final String serverInstanceTimeStamp = getTimeStampString(System.currentTimeMillis());

		private Path outputFolder = null;

		public void createFile(long timeStamp, String content, String fileNameSuffix) {
			createFile(DEFAULT_BASE_FILE_NAME, timeStamp, content, fileNameSuffix);
		}

		public void createFile(String baseFileName, long timeStamp, String content) {
			createFile(baseFileName, timeStamp, content, null);
		}

		public void createFile(String baseFileName, long timeStamp, String content, String fileNameSuffix) {
			executorService.submit(() -> doCreateFile(baseFileName, timeStamp, content, fileNameSuffix));
		}

		private void doCreateFile(String baseFileName, long timeStamp, String content, String fileNameSuffix) {
			try {
				Path folder = getOrCreateOutputFolder();
				Path file = folder.resolve(baseFileName + "_" + FILE_EXTENSION);
				String timeStampStr = getTimeStampString(timeStamp);
				file = FileUtils.appendToFileName(file, sanitizeTimeStampForFileName(timeStampStr));
				if (fileNameSuffix != null) {
					file = FileUtils.appendToFileName(file, fileNameSuffix);
				}
				String actualContent = "Server Instance ID: " + serverInstanceId + NL
						+ "Server Instance Time Stamp: " + serverInstanceTimeStamp + NL
						+ "Incident Time Stamp: " + timeStampStr + NL
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
