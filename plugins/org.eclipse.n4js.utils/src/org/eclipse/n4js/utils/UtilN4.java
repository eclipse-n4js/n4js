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
package org.eclipse.n4js.utils;

import static com.google.common.base.Strings.nullToEmpty;
import static org.eclipse.xtext.util.Tuples.pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.nodemodel.impl.LeafNodeWithSyntaxError;
import org.eclipse.xtext.util.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * Basic utility methods that do not require any N4JS-specific code.
 */
public class UtilN4 {

	/** Files extension of JSON files (<b>not</b> including the separator dot). */
	public static final String JSON_FILE_EXTENSION = "json";
	/** Files extension of JS source files (<b>not</b> including the separator dot). */
	public static final String JS_FILE_EXTENSION = "js";
	/** File extension of JS source files that contain ES6 modules (<b>not</b> including the separator dot). */
	public static final String MJS_FILE_EXTENSION = "mjs";
	/** File extension of JS source files that contain CommonJS modules (<b>not</b> including the separator dot). */
	public static final String CJS_FILE_EXTENSION = "cjs";
	/** Files extension of N4JS source files (<b>not</b> including the separator dot). */
	public static final String N4JS_FILE_EXTENSION = "n4js";
	/** Files extension of JSX source files (<b>not</b> including the separator dot). */
	public static final String JSX_FILE_EXTENSION = "jsx";
	/** Files extension of N4JSX source files (<b>not</b> including the separator dot). */
	public static final String N4JSX_FILE_EXTENSION = "n4jsx";
	/** Files extension of N4JS definition files (<b>not</b> including the separator dot). */
	public static final String N4JSD_FILE_EXTENSION = "n4jsd";
	/** Files extension of TS files (<b>not</b> including the separator dot). */
	public static final String TS_FILE_EXTENSION = "ts";
	/** Files extension of TS definition files (<b>not</b> including the separator dot). */
	public static final String DTS_FILE_EXTENSION = "d.ts";
	/** Files extension of XT source files (<b>not</b> including the separator dot). */
	public static final String XT_FILE_EXTENSION = "xt";

	/** Name of the N4JS Git repository, i.e. "n4js". */
	public static final String N4JS_GIT_REPOSITORY_NAME = "n4js";

	/** Name of NPM's package json file. */
	public static final String PACKAGE_JSON = "package.json";
	/** Name of package.json property "dependencies". */
	public static final String PACKAGE_JSON__DEPENDENCIES = "dependencies";
	/** Name of package.json property "devDependencies". */
	public static final String PACKAGE_JSON__DEV_DEPENDENCIES = "devDependencies";
	/** Name of the N4JS section in package.json files. */
	public static final String PACKAGE_JSON__N4JS = "n4js";
	/** Name of package.json property "sources". */
	public static final String PACKAGE_JSON__SOURCES = "sources";
	/** Default string used for indentation of a single level. */
	public static final String DEFAULT_INDENTATION_STR = "    ";

	/** Elements exported using the 'export default ...' syntax are visible under this name from outside. */
	public static final String EXPORT_DEFAULT_NAME = "default";

	/**
	 * The maximum memory usage measured with method {@link #measureUsedMemory(boolean)} since the JVM was started.
	 * Client code may reset this to 0 in order to start over.
	 */
	public static final AtomicLong MAX_USED_MEMORY_IN_BYTES = new AtomicLong(0L);

	private static Logger logger = Logger.getLogger(UtilN4.class);

	private static final Iterable<Pair<String, String>> CHARS_TO_ESCAPED_CHARS = ImmutableList
			.<Pair<String, String>> builder()
			.add(pair("&", "&amp;"))
			.add(pair("<", "&lt;"))
			.add(pair(">", "&gt;"))
			.build();

	private static final Function<String, String> ESCAPE_FUNC = new Function<>() {

		@Override
		public String apply(final String t) {
			final AtomicReference<String> s = new AtomicReference<>(nullToEmpty(t));
			CHARS_TO_ESCAPED_CHARS.forEach(input -> s.set(s.get().replace(input.getFirst(), input.getSecond())));
			return s.get();
		}
	};

	private static final Function<String, String> UNESCAPE_FUNC = new Function<>() {

		@Override
		public String apply(final String t) {
			final AtomicReference<String> s = new AtomicReference<>(nullToEmpty(t));
			CHARS_TO_ESCAPED_CHARS.forEach(input -> s.set(s.get().replace(input.getSecond(), input.getFirst())));
			return s.get();
		}
	};

	/**
	 * Simple utility method for measuring memory usage. Keeps track of the maximum usage measured in
	 * {@link #MAX_USED_MEMORY_IN_BYTES}.
	 *
	 * @return the currently used memory in bytes.
	 */
	public static final long measureUsedMemory(boolean logToStdOut) {
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		long usedBytes = runtime.totalMemory() - runtime.freeMemory();
		long maxBytes = MAX_USED_MEMORY_IN_BYTES.updateAndGet(oldValue -> Math.max(oldValue, usedBytes));
		if (logToStdOut) {
			long usedMeBiBytes = usedBytes / (1024L * 1024L);
			long maxMeBiBytes = maxBytes / (1024L * 1024L);
			System.out.println("used memory: " + usedMeBiBytes + "MiB (max: " + maxMeBiBytes + "MiB)");
		}
		return usedBytes;
	}

	/**
	 * Finds and returns the first cycle in the directed graph defined by the given edge relation
	 * <code>getNextNodes</code>. Consequential errors are omitted, that is, if X -> A, and if A -> B and B -> A, then
	 * no error is reported for X as it is not part of the cycle itself.
	 *
	 * @param startNode
	 *            the node where the search should be started.
	 * @param getNextNodes
	 *            the edge relation of the graph. May never return <code>null</code> but may return an empty list.
	 * @return a non-empty list of nodes forming a cyclic path in the graph starting at <code>startNode</code> or
	 *         <code>null</code> if no such cycle exists. If a non-null list is returned, its length is 2 or greater.
	 */
	public static final <T> List<T> findCycleInDirectedGraph(T startNode, Function<T, Iterable<T>> getNextNodes) {
		final RecursionGuard<T> guard = new RecursionGuard<>();
		guard.tryNext(startNode); // guard will be empty, so this will always return true
		return findCycleInDirectedGraph(startNode, getNextNodes, guard);
	}

	private static final <T> List<T> findCycleInDirectedGraph(T node, Function<T, Iterable<T>> getNextNodes,
			RecursionGuard<T> guard) {
		for (T nextNode : getNextNodes.apply(node)) {
			if (guard.tryNext(nextNode)) {
				try {
					final List<T> cycle = findCycleInDirectedGraph(nextNode, getNextNodes, guard);
					if (cycle != null)
						return cycle;
				} finally {
					guard.done(nextNode);
				}
			} else {
				// found cycle
				final Stack<T> path = guard.getElements();
				if (nextNode != path.firstElement()) {
					// ignore "nested" cycles further down the graph that do not involve
					// the node where we started the search
				} else {
					// report cycle
					path.push(nextNode); // close the cycle
					return new ArrayList<>(path);
				}
			}
		}
		return null;
	}

	/**
	 * Escapes all HTML special characters in <code>str</code>, e.g. character '&lt;' is changed to '&amp;lt;'.
	 */
	public static final String sanitizeForHTML(String str) {
		return ESCAPE_FUNC.apply(UNESCAPE_FUNC.apply(str));
	}

	/**
	 * If the character at the given index is a quote (as defined by {@link #isQuote(char)}), then this method will
	 * return the index after the corresponding closing quote, after the next NL character, or after the last character
	 * in <code>str</code> (whichever is encountered first). Otherwise, the given index is returned unchanged.
	 * <p>
	 * Does not handle:
	 * <ul>
	 * <li>the case that <code>idx</code> already points to a character inside a string literal.
	 * <li>triple-quoted string literals, e.g. <code>'''hello world!'''</code>.
	 * </ul>
	 */
	public static int skipQuotedText(String str, int idx) {
		int l = str.length();
		if (idx >= l) {
			return idx;
		}
		char quote = str.charAt(idx);
		if (!isQuote(quote)) {
			return idx;
		}
		char ch;
		do {
			idx++;
			if (idx >= l) {
				break;
			}
			ch = str.charAt(idx);
			if (ch == '\\') {
				// consume the backslash and the following control character
				// (even if the control character is CR or NL)
				idx++;
				continue;
			}
		} while (ch != quote && ch != '\n'); // note: we assume unquoted NL ends string literals
		return idx < l ? idx + 1 : l;
	}

	/**
	 * Tells whether the given character is a single or double quote or a back tick.
	 */
	public static boolean isQuote(char ch) {
		return ch == '"' || ch == '\'' || ch == '`';
	}

	/**
	 * Like {@link String#toUpperCase()}, but converts the first character only.
	 */
	public static final String toUpperCaseFirst(String str) {
		if (str != null && str.length() > 0) {
			final char first = str.charAt(0);
			if (Character.isLowerCase(first)) {
				return Character.toUpperCase(first) + str.substring(1);
			}
		}
		return str;
	}

	/**
	 * Like {@link String#toLowerCase()}, but converts the first character only.
	 */
	public static final String toLowerCaseFirst(String str) {
		if (str != null && str.length() > 0) {
			final char first = str.charAt(0);
			if (Character.isUpperCase(first)) {
				return Character.toLowerCase(first) + str.substring(1);
			}
		}
		return str;
	}

	/**
	 * If 'str' starts with one or more of the given prefixes, the prefixes will be removed.
	 */
	public static final String trimPrefix(String str, String... prefix) {
		if (str == null)
			return null;
		String result = str;
		for (int idx = 0; idx < prefix.length; idx++) {
			if (prefix[idx] == null || prefix[idx].length() == 0)
				continue;
			if (result.startsWith(prefix[idx])) {
				result = result.substring(prefix[idx].length());
				idx = 0;
			}
		}
		return result;
	}

	/**
	 * If 'str' ends with one or more of the given suffixes, the suffixes will be removed.
	 */
	public static final String trimSuffix(String str, String... suffix) {
		if (str == null)
			return null;
		final int len = str.length();
		String result = str;
		for (int idx = 0; idx < suffix.length; idx++) {
			if (suffix[idx] == null || suffix[idx].length() == 0)
				continue;
			if (result.endsWith(suffix[idx])) {
				result = result.substring(0, len - suffix[idx].length());
				idx = 0;
			}
		}
		return result;
	}

	/**
	 * Tells whether the given candidate string matches the given query according to the filtering logic applied by
	 * VSCode during client-side filtering of completion proposals, document symbols, etc.
	 * <p>
	 * The algorithm applied by VS Code seems to be:
	 *
	 * <pre>
	 * check that characters in query string are contained in symbol name ...
	 * - in the order they appear in the query string,
	 * - the query string must be exhausted, i.e. *all* characters of the query string must be contained in the symbol name,
	 * - if a character is contained in the query string N times, it must be contained in the symbol name N times,
	 * - case is ignored.
	 * </pre>
	 */
	public static boolean isMatchAccordingToVSCode(String candidate, String query) {
		int ic = 0;
		int iq = 0;
		while (ic < candidate.length() && iq < query.length()) {
			char cc = candidate.charAt(ic);
			char cq = query.charAt(iq);
			if (Character.toLowerCase(cc) == Character.toLowerCase(cq)) {
				// character matches, so consume this character in the query string:
				iq++;
			}
			ic++;
		}
		// we have a match iff the query string was exhausted:
		return iq == query.length();
	}

	/**
	 * Fills a string buffer with spaces until it has at least the given length. Not very efficient, basically used for
	 * debug output.
	 */
	public static void fill(StringBuilder strb, int offset) {
		while (strb.length() < offset)
			strb.append(' ');
	}

	/**
	 * Concatenate the given collections and return a newly created {@link List list}.
	 * <p>
	 * If an iterable is sufficient as a result, use {@link Iterables#concat(Iterable, Iterable)} or similar methods
	 * instead.
	 */
	@SafeVarargs
	public static <T> List<T> concat(Collection<? extends T>... collections) {
		if (collections.length == 0) {
			return new ArrayList<>();
		}
		int size = 0;
		for (Collection<? extends T> coll : collections) {
			size += coll.size();
		}
		ArrayList<T> result = new ArrayList<>(size);
		for (Collection<? extends T> coll : collections) {
			result.addAll(coll);
		}
		return result;
	}

	/** Same as {@link #indent(String, String)}, using the {@link #DEFAULT_INDENTATION_STR}. */
	public static String indent(String str) {
		return indent(str, DEFAULT_INDENTATION_STR);
	}

	/** Same as {@link #indentExceptFirstLine(String, String)}, but using the {@link #DEFAULT_INDENTATION_STR}. */
	public static String indentExceptFirstLine(String str) {
		return indentExceptFirstLine(str, DEFAULT_INDENTATION_STR);
	}

	/** Adds the given indentation string at the beginning of each line of {@code str}. */
	public static String indent(String str, String indentStr) {
		return indentStr + indentExceptFirstLine(str, indentStr);
	}

	/** Adds the given indentation string at the beginning of each line of {@code str}, except the first line. */
	public static String indentExceptFirstLine(String str, String indentStr) {
		return str.replace("\n", "\n" + indentStr);
	}

	private static final Map<Throwable, Boolean> reportedExceptions = Collections.synchronizedMap(
			new WeakHashMap<>());

	/**
	 * Same as {@link #reportError(Logger, String, Throwable)}, but will log the incident only if the given exception
	 * was not logger before using this method.
	 */
	@SuppressWarnings("hiding")
	public static <T extends Throwable> T reportErrorIfNotYetReported(Logger logger, String msg, T exception) {
		if (reportedExceptions.put(exception, Boolean.TRUE) == null) {
			reportError(logger, msg, exception);
		}
		return exception;
	}

	/**
	 * Same as {@link #reportError(String, Throwable)}, using the given {@link Throwable}'s message as first argument.
	 */
	public static <T extends Throwable> T reportError(T exception) {
		return reportError(exception.getMessage(), exception);
	}

	/**
	 * Report the given exception and then throw it. This method is intended for main failures that should be reported
	 * in one way or another. Currently, we just make sure the exception is logged to the console (using
	 * {@link Throwable#printStackTrace() #printStackTrace()}; later, this method could report the error to a remote
	 * server.
	 * <p>
	 * Currently, the main benefit of this method is that we make sure the exception is at least reported on the
	 * console, even if some code higher up in the call hierarchy catches and ignores the exception (e.g. Xsemantics).
	 */
	public static <T extends Throwable> T reportError(String msg, T exception) {
		return reportError(logger, msg, exception);
	}

	/**
	 * Same as {@link #reportError(String, Throwable)}, but a custom logger can be provided for logging the error.
	 */
	@SuppressWarnings("hiding")
	public static <T extends Throwable> T reportError(Logger logger, String msg, T exception) {
		logger.error(msg, exception); // make sure we see this (some clients eat up all exceptions!)
		return exception;
	}

	/**
	 * {@code true} if the leaf node argument is an instance of {@link LeafNodeWithSyntaxError} and the issue code of
	 * the syntax error message matches with any of the ignored syntax error issue codes argument. Otherwise, returns
	 * with {@code false}.
	 */
	public static boolean isIgnoredSyntaxErrorNode(final INode leaf, final String... ignoredSyntaxErrorIssues) {
		if (leaf instanceof LeafNodeWithSyntaxError) {
			final SyntaxErrorMessage errorMessage = leaf.getSyntaxErrorMessage();
			if (null != errorMessage) {
				return contains(errorMessage.getIssueCode(), ignoredSyntaxErrorIssues);
			}
		}
		return false;
	}

	/**
	 * Naive linear check whether the {@code elementToCheck} argument is contained among the {@code element} variadic
	 * arguments or not. Returns with {@code true} if contained, otherwise returns with {@code false}.
	 *
	 * @param elementToCheck
	 *            the element to check whether mentioned among the {@code elements} or not.
	 * @param elements
	 *            the optional elements argument.
	 * @return {@code true} if the first argument is among the second argument, otherwise {@code false}.
	 */
	private static boolean contains(String elementToCheck, String... elements) {
		if (Arrays2.isEmpty(elements)) {
			return false;
		}
		for (final String element : elements) {
			if (null == element && null == elementToCheck) {
				return true;
			}
			if (null != element && element.equals(elementToCheck)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Same as {@link #findN4jsRepoRootPathFailSafe()}, but throws an {@link IllegalStateException} exception if the
	 * path cannot be obtained or doesn't point to an existing directory.
	 * <p>
	 * Not intended to be used in production (only in MWE2 work flows, tests, etc.).
	 */
	public static Path findN4jsRepoRootPath() {
		final Path rootPath = findN4jsRepoRootPathFailSafe();
		if (rootPath == null || !rootPath.toFile().exists() || !rootPath.toFile().isDirectory()) {
			throw new IllegalStateException(
					"N4JS repository root path not found (does the current working directory lie inside "
							+ "an N4JS repository clone?) starting from working directory " +
							new File("").getAbsolutePath() + ".");
		}
		return rootPath;
	}

	/**
	 * Assuming the current working directory lies somewhere inside ...
	 * <ol>
	 * <li>an N4JS repository clone OR
	 * <li>a sibling repository clone
	 * </ol>
	 * this method will return the root folder (i.e. the folder named "n4js"). Otherwise, <code>null</code> is returned.
	 * <p>
	 * Not intended to be used in production (only in MWE2 work flows, tests, etc.).
	 */
	public static Path findN4jsRepoRootPathFailSafe() {
		try {
			// derive path of parent folder containing an N4JS Git repository, starting at current working directory
			File curr = new File("").getCanonicalFile();
			while (curr != null && !isN4jsRepoRoot(new File(curr, N4JS_GIT_REPOSITORY_NAME))) {
				curr = curr.isDirectory() ? curr.getParentFile() : null;
			}
			return curr != null ? curr.toPath().resolve(N4JS_GIT_REPOSITORY_NAME) : null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Tells if the given folder is the root folder of an N4JS repository clone. A ".git" sub folder is not required.
	 */
	public static boolean isN4jsRepoRoot(File folder) {
		return folder.isDirectory()
				&& N4JS_GIT_REPOSITORY_NAME.equals(folder.getName())
				&& new File(folder, "plugins/" + UtilN4.class.getPackage().getName()).isDirectory();
	}

	/**
	 * Utility for iterating over the digits of a natural number in the numeral system defined by the given base.
	 */
	public static class DigitIterator implements Iterator<Integer> {

		private final int base;
		private int value;
		private int valueConsumed;

		/**
		 * Creates an instance for iterating over the digits of 'initialValue' in the numeral system with base 'base'.
		 */
		public DigitIterator(int initialValue, int base) {
			this.base = base;
			setValue(initialValue);
		}

		/** Increments current value and resets the digit iterator. */
		public void inc() {
			if (value == Integer.MAX_VALUE) {
				throw new IllegalStateException("integer overflow");
			}
			setValue(value + 1);
		}

		/** Decrements current value and resets the digit iterator. */
		public void dec() {
			setValue(value - 1);
		}

		/** Sets the value and resets the digit iterator. */
		public void setValue(int value) {
			if (value < 0) {
				throw new IllegalArgumentException("value must be non-negative, but was: " + value);
			}
			this.value = value;
			this.valueConsumed = value;
		}

		/** Returns the current value. To obtain individual digits use {@link #nextDigit()} or {@link #next()}. */
		public int getValue() {
			return value;
		}

		/** Returns the next digit. */
		public int nextDigit() {
			final int digit = valueConsumed % base;
			valueConsumed = valueConsumed / base;
			return digit;
		}

		@Override
		public Integer next() {
			return nextDigit();
		}

		/** Tells if there are more non-zero digits not yet returned by the "next" methods. */
		@Override
		public boolean hasNext() {
			return valueConsumed > 0;
		}
	}
}
