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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.n4js.cli.N4jscMain;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest.N4jscVariant;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Data class that holds all information after {@link N4jscMain} was executed
 */
public class CliCompileResult extends ProcessResult {
	final N4jscVariant n4jscVariant;
	long deletedFilesCount;
	Map<String, String> projects = new LinkedHashMap<>();
	Multimap<String, Diagnostic> issues = HashMultimap.create();
	Multimap<String, String> errors = HashMultimap.create();
	Multimap<String, String> warnings = HashMultimap.create();
	NavigableMap<Path, Set<File>> transpiledFiles = new TreeMap<>();

	/** Constructor: Result for in-process execution */
	public CliCompileResult() {
		this(N4jscVariant.inprocess);
	}

	/** Constructor */
	public CliCompileResult(N4jscVariant n4jscVariant) {
		this.n4jscVariant = n4jscVariant;
	}

	/** @return variant of execution (see {@link N4jscVariant}) */
	public N4jscVariant getN4jscVariant() {
		return n4jscVariant;
	}

	/** @return number of all errors */
	public int getErrs() {
		return getErrMsgs().size();
	}

	/** @return number of all warnings */
	public int getWrns() {
		return getWrnMsgs().size();
	}

	/** @return number of all files that have been deleted */
	public long getDeletedFilesCount() {
		return deletedFilesCount;
	}

	/** @return map of all projects and their locations */
	public Map<String, String> getProjects() {
		return projects;
	}

	/** @return list of all files that have issues */
	public Collection<String> getIssueFiles() {
		return new TreeSet<>(issues.keySet());
	}

	/** @return list of all issues found in the given sources */
	public Multimap<String, Diagnostic> getIssues() {
		return HashMultimap.create(issues);
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

	/** @return number of all transpiled files */
	public int getTranspiledFilesCount() {
		return getTranspiledFiles().size();
	}

	/** @return number of all transpiled files in the given directory */
	public int getTranspiledFilesCount(Path dir) {
		return getTranspiledFiles(dir).size();
	}

	/** @return list of all transpiled files */
	public Collection<File> getTranspiledFiles() {
		return getTranspiledFiles(null);
	}

	/** @return list of all transpiled files in the given directory */
	public Collection<File> getTranspiledFiles(Path dir) {
		return getFiles(dir, true);
	}

	/** @return list of all transpiled file names */
	public Collection<String> getTranspiledFileNames() {
		return getTranspiledFiles().stream()
				.map(f -> f.toString())
				.collect(Collectors.toList());
	}

	private Collection<File> getFiles(Path dir, boolean includeSubdirs) {
		if (transpiledFiles.isEmpty()) {
			return Collections.emptyList();
		}
		if (!includeSubdirs && dir != null) {
			Collection<File> filesInDir = transpiledFiles.get(dir);
			return filesInDir;
		}

		Path start = (dir == null) ? transpiledFiles.firstKey() : dir;
		Collection<File> filesInDir = new LinkedList<>();
		Map<Path, Set<File>> tailMap = transpiledFiles.tailMap(start);

		for (Iterator<Entry<Path, Set<File>>> it = tailMap.entrySet().iterator(); it.hasNext();) {
			Entry<Path, Set<File>> next = it.next();
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
	List<Pair<String, String>> getProperties() {
		List<Pair<String, String>> props = super.getProperties();

		List<String> fileNameList = getTranspiledFiles().stream().map(f -> f.toString()).collect(toList());
		props.add(Tuples.pair("transpiled (" + getTranspiledFilesCount() + ")", ""));
		if (getTranspiledFilesCount() > 0) {
			props.add(Tuples.pair(null, IDENT2 + String.join("\n" + IDENT2, fileNameList) + "\n"));
		}

		if (n4jscVariant == N4jscVariant.exprocess) {
			return props;
		}

		List<String> projectNameList = getProjects().entrySet().stream().map(e -> e.getKey() + " at " + e.getValue())
				.collect(toList());

		props.add(Tuples.pair("variant", n4jscVariant.toString()));
		props.add(Tuples.pair("projects (" + getProjects().size() + ")", ""));
		if (getProjects().size() > 0) {
			props.add(Tuples.pair(null, IDENT2 + String.join("\n" + IDENT2, projectNameList)));
		}

		props.add(Tuples.pair("warnings (" + getWrns() + ")", ""));
		if (getWrns() > 0) {
			props.add(Tuples.pair(null, IDENT1 + String.join("\n" + IDENT1, getWrnMsgs())));
		}
		props.add(Tuples.pair("errors (" + getErrs() + ")", ""));
		if (getErrs() > 0) {
			props.add(Tuples.pair(null, IDENT1 + String.join("\n" + IDENT1, getErrMsgs())));
		}

		return props;
	}

}
