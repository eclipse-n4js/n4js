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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 */
public class CliResult {
	String stdOut;
	String errOut;
	int exitCode;
	Exception cause;
	int errs;
	int wrns;
	List<String> errMsgs;
	List<String> wrnMsgs;
	long duration;
	TreeMap<Path, HashSet<File>> transpiledFiles;

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
		return errs;
	}

	/** @return number of all warnings */
	public int getWrns() {
		return wrns;
	}

	/** @return list of all error messages found in the given sources */
	public List<String> getErrMsgs() {
		return errMsgs;
	}

	/** @return list of all warning messages found in the given sources */
	public List<String> getWrnMsgs() {
		return wrnMsgs;
	}

	/** @return duration of invoking the cli command */
	public long getDuration() {
		return duration;
	}

	/** @return number of all files that where transpiled to js files */
	public int getTranspiledFilesCount() {
		return transpiledFiles.size();
	}

	/** @return number of all files that where transpiled to js files in the given directory */
	public int getTranspiledFilesCount(Path dir) {
		return getTranspiledFiles(dir).size();
	}

	/** @return list of all files that where transpiled to js files in the given directory */
	public Collection<File> getTranspiledFiles(Path dir) {
		return getTranspiledFiles(dir, false);
	}

	private Collection<File> getTranspiledFiles(Path dir, boolean exclusive) {
		Collection<File> filesInDir = transpiledFiles.get(dir);
		if (exclusive) {
			return filesInDir;
		}

		filesInDir = new LinkedList<>();
		Map<Path, HashSet<File>> tailMap = transpiledFiles.tailMap(dir);
		for (Iterator<Entry<Path, HashSet<File>>> it = tailMap.entrySet().iterator(); it.hasNext();) {
			Entry<Path, HashSet<File>> next = it.next();
			Path curDir = next.getKey();
			if (curDir.startsWith(dir)) {
				filesInDir.addAll(next.getValue());
			} else {
				break;
			}
		}

		return filesInDir;
	}

}
