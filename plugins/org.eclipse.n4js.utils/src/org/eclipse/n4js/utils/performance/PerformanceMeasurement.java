/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.performance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.base.Stopwatch;

/**
 * A performance measurement is a collection of elapsed time periods, organized as a list of checkpoints.
 *
 * It can be used to carry out measurements about the duration of several steps an overall process performs.
 */
public class PerformanceMeasurement {

	private static final String MEASUREMENTS_FOLDER = "performance-measurements";

	/**
	 * Creates a new performance measurement for the given {@code clazz}.
	 */
	public static PerformanceMeasurement createMeasurement(Class<?> clazz) {
		return new PerformanceMeasurement(clazz.getSimpleName());
	}

	/**
	 * Creates a new performance measurement for the given {@code name}.
	 */
	public static PerformanceMeasurement createMeasurement(String name) {
		return new PerformanceMeasurement(name);
	}

	private final String name;
	private Stopwatch currentStopwatch;
	private String currentDescription;
	private final Map<String, Long> checkpoints = new LinkedHashMap<>();

	/**
	 * Instantiates a new {@link PerformanceMeasurement} with the given name.
	 */
	private PerformanceMeasurement(String name) {
		this.name = name;
	}

	/**
	 * Creates a new checkpoint within this measurement.
	 *
	 * The passed time between the newly created checkpoint and the last one is stored as a measurement under
	 * {@code description}.
	 *
	 * If this is the first invocation of {@link #checkpoint(String)}, the checkpoint marks the beginning of the
	 * measurement.
	 */
	public void checkpoint(String description) throws IOException {
		if (this.currentStopwatch != null) {
			this.currentStopwatch.stop();
			final long elapsedTime = this.currentStopwatch.elapsed(TimeUnit.MILLISECONDS);
			this.checkpoints.put(currentDescription, elapsedTime);
		}
		this.currentDescription = description;
		// start a new stopwatch
		this.currentStopwatch = Stopwatch.createStarted();

		this.write();
	}

	/**
	 * Writes out the data collected in this measurement to disk.
	 */
	public void write() throws IOException {
		final File measurementsFolder = new File(MEASUREMENTS_FOLDER);
		if (!measurementsFolder.exists()) {
			measurementsFolder.mkdir();
		}

		final File measurementsFile = new File(measurementsFolder, this.name + ".csv");
		System.out.println("Storing performance measurements of " + this.name + " in "
				+ measurementsFile.getAbsolutePath().toString());
		measurementsFile.createNewFile();

		try (FileWriter fileWriter = new FileWriter(measurementsFile)) {
			// write column label row
			final String columnRow = this.checkpoints.entrySet().stream().map(e -> e.getKey())
					.collect(Collectors.joining(","));
			final String valuesRow = this.checkpoints.entrySet().stream().map(e -> e.getValue().toString())
					.collect(Collectors.joining(","));

			fileWriter.write(columnRow + "\n");
			fileWriter.write(valuesRow + "\n");
		}
	}
}
