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
package org.eclipse.n4js.jsdoc2spec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.annotations.VisibleForTesting;

import org.eclipse.n4js.jsdoc.dom.Doclet;

/**
 * Info of a test method.
 *
 * A test method is always associated with a member. It basically defines a constraint for that member. In order to
 * enable several tests per member and to reduce manual typing, the following naming convention for the test method name
 * is assumed:
 *
 * <pre>
 * testName    ::=('test')? ('_'? method '__')? title ('___' case)?
 * description ::= <'_' separated>
 * case        ::= <'_' separated>
 * </pre>
 *
 * That is, a test method may start with "test" and it may be followed by the method name that is tested. Then, the
 * title of the tests follows, optionally with a case. All cases are later merged to one description.
 */
public class SpecTestInfo implements Comparable<SpecTestInfo> {

	/**
	 * Regular Expression Fragment: Matches a string with single underscores.
	 */
	private final static String NAME_WITH_UNDERSCORE = "(?:[^_]+(?:_?[^_])*)";

	/**
	 * Regular Expression matching a test name according to the test name convention.
	 */
	private final static Pattern TEST_NAME_PATTERN = Pattern.compile("(?:test)?(?:(?:_?(" +
			NAME_WITH_UNDERSCORE +
			")__)|(?:_))?(" +
			NAME_WITH_UNDERSCORE +
			")(?:___(" +
			NAME_WITH_UNDERSCORE +
			"))?");

	/**
	 * Matches names following the test name convention.
	 *
	 * @return method, title, and case.
	 */
	@VisibleForTesting
	static String[] parseName(QualifiedName methodName) {
		Matcher matcher = TEST_NAME_PATTERN.matcher(methodName.getLastSegment());
		if (!matcher.matches()) {
			throw new IllegalArgumentException(
					"Test name does not match naming convention: " + methodName.getLastSegment());
		}
		return new String[] {
				matcher.group(1),
				matcher.group(2),
				matcher.group(3)
		};
	}

	/**
	 * FQN of test method.
	 */
	private final QualifiedName methodName;

	/**
	 * Test description doclet
	 */
	final public Doclet doclet;

	/**
	 * Relative repository path
	 */
	final public RepoRelativePath rrp;

	/**
	 * The (simple) name of the member or type which is tested.
	 */
	final public String testeeName;

	/**
	 * Derived: testTitle
	 */
	final public String testTitle;
	/**
	 * Derived: testCase
	 */
	final public String testCase;

	/**
	 * Constructor
	 */
	public SpecTestInfo(String testeeName, QualifiedName methodName, Doclet doclet, RepoRelativePath rrp) {
		this.methodName = methodName;
		this.doclet = doclet;
		this.rrp = rrp;
		this.testeeName = testeeName;
		String[] parsedName = parseName(methodName);
		// String parsedTesteeName = parsedName[0];
		// parsedTesteeName and given testeeName may differ, this is not a problem since a single test may be used
		// for several methos (although this is not recommended).
		this.testCase = humanReadable(parsedName[2]);
		this.testTitle = humanReadable(parsedName[1]);
	}

	/**
	 * Converts a camel-cased or underscored string to a human readable string.
	 */
	public final static String humanReadable(String camelcaseOrUnderscoredString) {
		if (camelcaseOrUnderscoredString == null) {
			return null;
		}
		if (camelcaseOrUnderscoredString.indexOf('_') >= 0) {
			return camelcaseOrUnderscoredString.replaceAll("_", " ");
		} else {
			StringBuilder strb = new StringBuilder();
			char p = 0;
			for (int i = 0; i < camelcaseOrUnderscoredString.length(); i++) {
				char c = camelcaseOrUnderscoredString.charAt(i);
				if (Character.isUpperCase(c) && p != 0) {
					strb.append(' ');
					strb.append(Character.toLowerCase(c));
				} else {
					strb.append(c);
				}
				p = c;
			}
			return strb.toString();
		}
	}

	@Override
	public int compareTo(SpecTestInfo o) {
		int d = methodName != null ? methodName.compareTo(o.methodName) : (o.methodName != null ? -1 : 0);
		if (d == 0) {
			d = rrp != null ? rrp.compareTo(o.rrp) : (o.rrp != null ? -1 : 0);
		}

		return d;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SpecTestInfo) {
			return compareTo((SpecTestInfo) obj) == 0;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ((methodName != null ? methodName.hashCode() : 0) * 31 +
				((rrp != null ? rrp.hashCode() : 0)));

	}

	/**
	 * Returns the name of the test module specifier.
	 */
	public CharSequence testModuleSpec() {
		return methodName.getSegment(0);
	}

	/**
	 * Returns the container type name of the test method.
	 */
	public String testMethodTypeName() {
		return methodName.getSegment(1);
	}

	/**
	 * Returns the name of the test method.
	 */
	public String testMethodName() {
		return methodName.getSegment(2);
	}

}
