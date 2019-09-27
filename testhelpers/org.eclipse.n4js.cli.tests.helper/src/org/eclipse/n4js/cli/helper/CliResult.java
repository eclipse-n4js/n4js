/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.helper;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 *
 */
public class CliResult {
	String stdOut;
	String errOut;
	int exitCode;
	Exception cause;
	Multimap<String, String> errors = HashMultimap.create();
	Multimap<String, String> warnings = HashMultimap.create();
	long duration;
	TreeMap<Path, HashSet<File>> transpiledFiles = new TreeMap<>();

	/** @return all outputs on the standard output stream */
	public String getStdOut() {
		return stdOut;
	}

	/** @return all outputs on the error stream */
	public String getErrOut() {
		return errOut;
	}

	/** @return exit code of the cli command */
	public int getExitCode() {
		return exitCode;
	}

	/** @return exception in case the cli command was ended non-successful */
	public Exception getCause() {
		return cause;
	}

	/** @return number of all errors */
	public int getErrs() {
		return getErrMsgs().size();
	}

	/** @return number of all warnings */
	public int getWrns() {
		return getWrnMsgs().size();
	}

	/** @return list of all error messages found in the given sources */
	public Collection<String> getErrMsgs() {
		return errors.values();
	}

	/** @return list of all warning messages found in the given sources */
	public Collection<String> getWrnMsgs() {
		return warnings.values();
	}

	/** @return duration of invoking the cli command */
	public long getDuration() {
		return duration;
	}

	/** @return number of all files that where transpiled to js files */
	public int getTranspiledFilesCount() {
		return getTranspiledFiles().size();
	}

	/** @return number of all files that where transpiled to js files in the given directory */
	public int getTranspiledFilesCount(Path dir) {
		return getTranspiledFiles(dir).size();
	}

	/** @return list of all files that where transpiled to js files */
	public Collection<File> getTranspiledFiles() {
		return getTranspiledFiles(null, false);
	}

	/** @return list of all files that where transpiled to js files in the given directory */
	public Collection<File> getTranspiledFiles(Path dir) {
		return getTranspiledFiles(dir, false);
	}

	private Collection<File> getTranspiledFiles(Path dir, boolean exclusive) {
		if (transpiledFiles.isEmpty()) {
			return Collections.emptyList();
		}
		if (exclusive && dir != null) {
			Collection<File> filesInDir = transpiledFiles.get(dir);
			return filesInDir;
		}

		Path start = (dir == null) ? transpiledFiles.firstKey() : dir;
		Collection<File> filesInDir = new LinkedList<>();
		Map<Path, HashSet<File>> tailMap = transpiledFiles.tailMap(start);
		for (Iterator<Entry<Path, HashSet<File>>> it = tailMap.entrySet().iterator(); it.hasNext();) {
			Entry<Path, HashSet<File>> next = it.next();
			Path curDir = next.getKey();
			if (dir == null || curDir.startsWith(dir)) {
				filesInDir.addAll(next.getValue());
			} else {
				break;
			}
		}

		return filesInDir;
	}

	@Override
	public String toString() {
		List<String> fileNameList = getTranspiledFiles().stream().map(f -> f.toString()).collect(Collectors.toList());

		String s = "CLI Result:\n"
				+ "    duration   " + duration + "ms\n"
				+ "    exit code  " + exitCode + "\n"
				+ "    transpiled (" + getTranspiledFilesCount() + "):\n"
				+ (getTranspiledFilesCount() > 0 ? "       " : "")
				+ String.join("\n       ", fileNameList) + "\n"
				+ "    warnings (" + getWrns() + "):\n" + (getWrns() > 0 ? "    " : "")
				+ String.join("\n    ", getWrnMsgs()) + "\n"
				+ "    errors (" + getErrs() + "):\n" + (getErrs() > 0 ? "    " : "")
				+ String.join("\n    ", getErrMsgs()) + "\n"
				+ ((cause != null) ? "    exception " + cause.getMessage() : "")
				+ "    std out:\n"
				+ (stdOut.isBlank() ? "" : ">>>>\n" + stdOut + "\n<<<<\n")
				+ "    err out:\n"
				+ (errOut.isBlank() ? "" : ">>>>\n" + errOut + "\n<<<<\n")
				+ "CLI Result end\n";
		return s;
	}
}
