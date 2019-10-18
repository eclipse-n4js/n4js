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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

/**
 * Data class that holds all information after {@code node.js} was executed
 */
public class ProcessResult {
	static final int NO_EXIT_CODE = Integer.MIN_VALUE;
	static final long NO_DURATION = Long.MIN_VALUE;

	static final String IDENT1 = "   ";
	static final String IDENT2 = "      ";

	long duration = NO_DURATION;
	String workingDir = "";
	String command = "";
	String stdOut = "";
	String errOut = "";
	Exception exception;
	int exitCode = NO_EXIT_CODE;

	/** Constructor */
	public ProcessResult() {
	}

	/** Constructor */
	public ProcessResult(ProcessResult processResult) {
		workingDir = processResult.getWorkingDir();
		command = processResult.getCommand();
		duration = processResult.getDuration();
		exception = processResult.getException();
		exitCode = processResult.getExitCode();
		stdOut = processResult.getStdOut();
		errOut = processResult.getErrOut();
	}

	/** @return working directory where the process was executed */
	public String getWorkingDir() {
		return workingDir;
	}

	/** @return command that started the process */
	public String getCommand() {
		return command;
	}

	/** @return duration of the process */
	public long getDuration() {
		return duration;
	}

	/** @return output of the process */
	public String getStdOut() {
		return stdOut;
	}

	/** @return error output of the process */
	public String getErrOut() {
		return errOut;
	}

	/** @return exception thrown while executing the process */
	public Exception getException() {
		return exception;
	}

	/** @return exit code of process */
	public int getExitCode() {
		return exitCode;
	}

	/** @return a list of pairs where the first entry is a name and the second is a property. */
	List<Pair<String, String>> getProperties() {
		List<Pair<String, String>> props = new ArrayList<>();
		props.add(Tuples.pair("workingDir", workingDir));
		props.add(Tuples.pair("command", command));
		props.add(Tuples.pair("exit code", (exitCode == NO_EXIT_CODE ? "-" : String.valueOf(exitCode))));
		props.add(Tuples.pair("duration", (duration == NO_DURATION ? "-" : duration + "ms")));
		props.add(Tuples.pair("exception", exception == null ? "" : exception.getMessage()));

		props.add(Tuples.pair("std out", ""));
		if (stdOut != null && !stdOut.isBlank()) {
			props.add(Tuples.pair(null, ">>>>\n" + stdOut + "\n<<<<"));
		}
		props.add(Tuples.pair("err out", ""));
		if (errOut != null && !errOut.isBlank()) {
			props.add(Tuples.pair(null, ">>>>\n" + errOut + "\n<<<<"));
		}

		return props;
	}

	@Override
	public String toString() {
		String s = "Result:\n";
		for (Pair<String, String> prop : getProperties()) {
			String first = prop.getFirst();
			String second = prop.getSecond();
			if (first == null) {
				s += second + "\n";
			} else {
				s += String.format("%s%-12s:   %s\n", IDENT1, first, second);
			}
		}
		s += "Result End.\n";
		return s;
	}

}
