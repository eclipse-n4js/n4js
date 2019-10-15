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

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.n4js.cli.N4jscMain;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Data class that holds all information after {@link N4jscMain} was executed
 */
public class CliResult {
	String stdOut;
	String errOut;
	int exitCode;
	Map<String, String> projects = new LinkedHashMap<>();
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

	/** @return map of all projects and their locations */
	public Map<String, String> getProjects() {
		return projects;
	}

	/** @return list of all files that have errors */
	public Collection<String> getErrFiles() {
		return new TreeSet<>(errors.keySet());
	}

	/** @return list of all error messages found in the given sources */
	public Collection<String> getErrMsgs() {
		return new TreeSet<>(errors.values());
	}

	/** @return list of all files that have warnings */
	public Collection<String> getWrnFiles() {
		return new TreeSet<>(warnings.keySet());
	}

	/** @return list of all warning messages found in the given sources */
	public Collection<String> getWrnMsgs() {
		return new TreeSet<>(warnings.values());
	}

	/** @return duration of invoking the cli command */
	public long getDuration() {
		return duration;
	}

	/** @return number of all files that where transpiled to js files */
	public int getTranspiledFilesCount() {
		return getTranspiledFiles().size();
	}

	/** @return list of all file names that where transpiled to js files */
	public Collection<String> getTranspiledFileNames() {
		return getTranspiledFiles().stream()
				.map(f -> f.toString())
				.collect(Collectors.toList());
	}

	/** @return list of all files that where transpiled to js files */
	public Collection<File> getTranspiledFiles() {
		return getJSFiles(null, true, false);
	}

	/** @return number of all js files */
	public int getJSFilesCount() {
		return getJSFiles().size();
	}

	/** @return number of all js files in the given directory */
	public int getJSFilesCount(Path dir) {
		return getJSFiles(dir).size();
	}

	/** @return list of all js files */
	public Collection<File> getJSFiles() {
		return getJSFiles(null, false, false);
	}

	/** @return list of all js files in the given directory */
	public Collection<File> getJSFiles(Path dir) {
		return getJSFiles(dir, false, false);
	}

	private Collection<File> getJSFiles(Path dir, boolean transpiledOnly, boolean exclusive) {
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
			if (!transpiledOnly || pathContainsSrcGen(curDir)) {
				if (dir == null || curDir.startsWith(dir)) {
					filesInDir.addAll(next.getValue());
				} else {
					break;
				}
			}
		}

		return filesInDir;
	}

	private boolean pathContainsSrcGen(Path path) {
		if (path == null || path.getNameCount() == 0) {
			return false;
		}
		Path pathName = path.getName(path.getNameCount() - 1);
		if ("src-gen".equals(pathName.toString())) {
			return true;
		}
		return pathContainsSrcGen(path.getParent());
	}

	@Override
	public String toString() {
		List<String> fileNameList = getTranspiledFiles().stream().map(f -> f.toString()).collect(toList());
		List<String> projectNameList = getProjects().entrySet().stream().map(e -> e.getKey() + " at " + e.getValue())
				.collect(toList());

		String s = "CLI Result:\n";
		s += "    duration:  " + duration + "ms\n";
		s += "    exit code: " + exitCode + "\n";
		s += "    projects (" + getProjects().size() + "):\n";
		s += "       " + String.join("\n       ", projectNameList) + "\n";
		s += "    transpiled (" + getTranspiledFilesCount() + "):\n";
		s += (getTranspiledFilesCount() > 0 ? "       " : "");
		s += String.join("\n       ", fileNameList) + "\n";
		s += "    warnings (" + getWrns() + "):\n" + (getWrns() > 0 ? "    " : "");
		s += String.join("\n    ", getWrnMsgs()) + "\n";
		s += "    errors (" + getErrs() + "):\n" + (getErrs() > 0 ? "    " : "");
		s += String.join("\n    ", getErrMsgs()) + "\n";
		s += ((cause == null) ? "" : "    exception " + cause.getMessage());
		s += "    std out:\n";
		s += (stdOut == null || stdOut.isBlank() ? "" : ">>>>\n" + stdOut + "\n<<<<\n");
		s += "    err out:\n";
		s += (errOut == null || errOut.isBlank() ? "" : ">>>>\n" + errOut + "\n<<<<\n");
		s += "CLI Result End.\n";
		return s;
	}
}
