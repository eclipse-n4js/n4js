/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;

/**
 * A TypeScript triple-slash directive.
 */
public class TripleSlashDirective {

	private static final Pattern PATTERN = Pattern.compile("\\<\\s*"
			+ "(?<name>(\\w|-)+)"
			+ "("
			+ "\\s+(?<attrName>(\\w|-)+)"
			+ "\\s*=\\s*"
			+ "(("
			+ "\\\"(?<attrValue1>.*)\\\""
			+ ")|("
			+ "\\\'(?<attrValue2>.*)\\\'"
			+ "))"
			+ ")?"
			+ "\\s*\\/\\>");

	/** Name of the directive. */
	public final String name;

	/** Attribute of the directive as a key/value pair. */
	public final Optional<Pair<String, String>> attr;

	/** Creates a new {@link TripleSlashDirective} without an attribute. */
	public TripleSlashDirective(String name) {
		this.name = name;
		this.attr = Optional.absent();
	}

	/** Creates a new {@link TripleSlashDirective} with the given attribute. */
	public TripleSlashDirective(String name, Pair<String, String> attr) {
		this.name = name;
		this.attr = Optional.of(attr);
	}

	@Override
	public String toString() {
		if (attr.isPresent()) {
			return "<" + name + " " + attr.get().getKey() + "=\"" + attr.get().getValue() + "\" />";
		}
		return "<" + name + "/>";
	}

	/**
	 * Creates a new {@link TripleSlashDirective} by parsing the given string. Returns <code>null</code> in case of
	 * syntax error.
	 */
	public static TripleSlashDirective parse(String str) {
		Matcher m = PATTERN.matcher(str);
		if (m.matches()) {
			String name = m.group("name");
			String attrName = m.group("attrName");
			String attrValue1 = m.group("attrValue1");
			String attrValue2 = m.group("attrValue2");
			String attrValue = attrValue1 != null ? attrValue1 : attrValue2;
			if (attrName == null && attrValue == null) {
				return new TripleSlashDirective(name);
			} else if (attrName != null && attrValue != null) {
				return new TripleSlashDirective(name, Pair.of(attrName, attrValue));
			}
		}
		return null;
	}
}
