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
	Map<String, String> projects = new LinkedHashMap<>();
	Multimap<String, String> errors = HashMultimap.create();
	Multimap<String, String> warnings = HashMultimap.create();
	TreeMap<Path, HashSet<File>> transpiledFiles = new TreeMap<>();

	CliCompileResult() {
		this(N4jscVariant.inprocess);
	}

	CliCompileResult(N4jscVariant n4jscVariant) {
		super(String.format("[%s]", n4jscVariant.toString()));
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
	List<Pair<String, String>> getProperties() {
		List<Pair<String, String>> props = super.getProperties();

		List<String> fileNameList = getTranspiledFiles().stream().map(f -> f.toString()).collect(toList());
		props.add(Tuples.pair("transpiled (" + getTranspiledFilesCount() + ")", ""));
		if (getTranspiledFilesCount() > 0) {
			props.add(Tuples.pair(null, "      + " + String.join("\n      + ", fileNameList) + "\n"));
		}

		if (n4jscVariant == N4jscVariant.exprocess) {
			return props;
		}

		List<String> projectNameList = getProjects().entrySet().stream().map(e -> e.getKey() + " at " + e.getValue())
				.collect(toList());

		props.add(Tuples.pair("variant", n4jscVariant.toString()));
		props.add(Tuples.pair("projects (" + getProjects().size() + ")", ""));
		if (getProjects().size() > 0) {
			props.add(Tuples.pair(null, "      + " + String.join("\n      + ", projectNameList)));
		}

		props.add(Tuples.pair("warnings (" + getWrns() + ")", ""));
		if (getWrns() > 0) {
			props.add(Tuples.pair(null, "      + " + String.join("\n      + ", getWrnMsgs()) + "\n"));
		}
		props.add(Tuples.pair("errors (" + getErrs() + ")", ""));
		if (getErrs() > 0) {
			props.add(Tuples.pair(null, "      + " + String.join("\n      + ", getErrMsgs()) + "\n"));
		}

		return props;
	}

}
