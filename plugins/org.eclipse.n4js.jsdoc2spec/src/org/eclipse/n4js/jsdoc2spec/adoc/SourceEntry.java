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
package org.eclipse.n4js.jsdoc2spec.adoc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * {@link SourceEntry}s are data entities. They summarize e.g. data about their adoc location on the disk.
 */
public class SourceEntry {

	/** The property name. May be empty, but not null. */
	final public String property;
	/**
	 * The delimiter is either '#' or '@' (static). For getter and setters, '<' and '>' is added. May be empty, but not
	 * null.
	 */
	final public String delimiter;
	/** The element name. Usually starting with a type name. */
	final public String element;
	/** The module name also contains the package. */
	final public String module;
	/** The extension of the N4JS file. */
	final public String extension;
	/** The project folder. */
	final public String folder;
	/** True iff this SourceEntry is declared in a StaticPolyfillAware class */
	final public boolean isStaticPolyfillAware;
	/**
	 * In case of a polyfill aware class, features are copied to the filling classes. Then, the {@code trueFolder}
	 * refers to the polyfill aware class.
	 */
	final public String trueFolder;
	/** The project name */
	final public String project;
	/** The path name. Folders are separated by '/' */
	final public String path;
	/** The repository name */
	final public String repository;
	/** The start line in the N4JS file */
	final public int sourceLine;
	/** The path to the module file */
	final public String packageName;
	/** The module file itself */
	final public String moduleName;
	/** The number of folders of the {@link #packageName} */
	final public int modulePackageCount;
	/** The path to the adoc file. The '/' in the packageName are replaced by '.'. */
	final public String[] adocPathElems;
	/** The {@link #adocPathElems} as one String. */
	final public String adocPath;

	private String toString;

	SourceEntry(
			String repository,
			String path,
			String project,
			String folder,
			boolean isStaticPolyfillAware,
			String trueFolder,
			String module,
			String extension,
			String element,
			String delimiter,
			String property,
			String packageName,
			String moduleName,
			int sourceLine,
			int modulePackageCount,
			String[] fileNames,
			String fileName) {

		this.repository = repository;
		this.path = path;
		this.project = project;
		this.folder = folder;
		this.isStaticPolyfillAware = isStaticPolyfillAware;
		this.trueFolder = trueFolder;
		this.module = module;
		this.extension = extension;
		this.element = element;
		this.delimiter = delimiter;
		this.property = property;
		this.packageName = packageName;
		this.moduleName = moduleName;
		this.sourceLine = sourceLine;
		this.modulePackageCount = modulePackageCount;
		this.adocPathElems = fileNames;
		this.adocPath = fileName;
	}

	/**
	 * Constructor
	 */
	protected SourceEntry(SourceEntry se) {
		this.repository = se.repository;
		this.path = se.path;
		this.project = se.project;
		this.folder = se.folder;
		this.isStaticPolyfillAware = se.isStaticPolyfillAware;
		this.trueFolder = se.trueFolder;
		this.module = se.module;
		this.extension = se.extension;
		this.element = se.element;
		this.delimiter = se.delimiter;
		this.property = se.property;
		this.sourceLine = se.sourceLine;
		this.packageName = se.packageName;
		this.moduleName = se.moduleName;
		this.modulePackageCount = se.modulePackageCount;
		this.adocPathElems = se.adocPathElems;
		this.adocPath = se.adocPath;
	}

	/**
	 * Checks if both, {@link #delimiter} and {@link #property} are not null.
	 */
	public boolean hasProperty() {
		return !delimiter.isEmpty() && !property.isEmpty();
	}

	/**
	 * Creates a string that is conform to the PQN specification mentioned in IDE-2335.
	 */
	public String toPQN() {
		String s = repository;
		s += ":" + path;
		s += ":" + project;
		s += ":" + folder;
		s += "/" + module;
		s += ":" + element;
		s += delimiter;
		s += property;
		return s;
	}

	/**
	 * Used for anchors in adoc. Takes into account that anchors only allow the following characters (undocumented):
	 * a-z, 0-9, :, ., _, -,<br>
	 * FIXME: reconsider performance.
	 */
	public String getAdocCompatibleAnchorID() {
		String id = module.replace("/", ".");
		id += "." + element;
		switch (delimiter) {
		case "#<":
			id += ".getter.";
			break;
		case "#>":
			id += ".setter.";
			break;
		case "@<":
			id += ".static.getter.";
			break;
		case "@>":
			id += ".static.setter.";
			break;
		case "#":
			id += ".";
			break;
		case "@":
			id += ".static.";
			break;
		}
		id += getEscapedAdocAnchorString(property);

		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SourceEntry))
			return false;

		return this.toString().equals(o.toString());
	}

	@Override
	public int hashCode() {
		return Objects.hash(toString());
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = repository;
			toString += ":" + path;
			toString += ":" + project;
			toString += ":" + folder;
			if (!folder.equals(trueFolder))
				toString += "(->" + trueFolder + ")";
			toString += ":" + module;
			toString += ":" + element;
			toString += delimiter;
			toString += property;
			toString += ":" + sourceLine;
		}
		return toString;
	}

	/**
	 * First, this method escapes special characters like in html. Then, the '%' is replaced by ':' since colons are
	 * allowed in adoc anchors. The semicolons are omitted.
	 */
	public static String getEscapedAdocAnchorString(String str) {
		String htmlEscaped = "";
		try {
			htmlEscaped = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			htmlEscaped = "String.could.not.be.escaped";
		}
		String adocEscaped = htmlEscaped.replace("%", ":");
		return adocEscaped;
	}
}
