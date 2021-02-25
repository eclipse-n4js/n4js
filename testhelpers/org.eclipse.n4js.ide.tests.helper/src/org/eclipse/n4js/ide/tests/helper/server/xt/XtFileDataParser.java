/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

import static org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager.DEFAULT_PROJECT_NAME;
import static org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager.DEFAULT_SOURCE_FOLDER;
import static org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager.VENDOR;
import static org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager.VENDOR_NAME;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.eclipse.n4js.tests.codegen.Folder;
import org.eclipse.n4js.tests.codegen.Module;
import org.eclipse.n4js.tests.codegen.Project;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Sets;

/**
 * Class to parse xt files
 */
public class XtFileDataParser {

	/** Pattern group name for the qualified name of the xt test runner */
	static final String XT_RUNNER = "RUNNER";

	/** Pattern group name for the workspace configuration */
	static final String XT_WORKSPACE = "WORKSPACE";

	/**
	 * Pattern for Setup section of xt files
	 */
	static final Pattern XT_SETUP = Pattern.compile(
			"XPECT_SETUP\\s+(?<" + XT_RUNNER + ">[\\w\\d\\.]+)[\\s\\S]*?(Workspace\\s*\\{(?<" + XT_WORKSPACE
					+ ">[\\s\\S]*)\\}[\\s\\S]*)?END_SETUP");

	/** Parses the contents of the given file */
	static public XtFileData parse(File xtFile) throws IOException {
		String xtFileContent = Files.readString(xtFile.toPath());

		Matcher matcher = XT_SETUP.matcher(xtFileContent);
		Preconditions.checkState(matcher.find());
		String setupRunner = matcher.group(XT_RUNNER);
		Preconditions.checkNotNull(setupRunner);
		String setupWorkspace = matcher.group(XT_WORKSPACE);

		XtWorkspace workspace = getWorkspace(xtFile, setupWorkspace, xtFileContent);
		List<MethodData> startupMethodData = getDefaultStartupMethodData();
		List<MethodData> teardownMethodData = getDefaultTeardownMethodData();

		TreeSet<XtFileData.MethodData> testMethodData1 = new TreeSet<>();
		TreeSet<XtFileData.MethodData> testMethodData2 = new TreeSet<>();
		fillTestMethodData(xtFile.toString(), xtFileContent, testMethodData1, testMethodData2);

		return new XtFileData(xtFile, xtFileContent, setupRunner, workspace, startupMethodData, testMethodData1,
				testMethodData2, teardownMethodData);
	}

	static XtWorkspace getWorkspace(File xtFile, String setupWorkspace, String xtFileContent) {
		if (setupWorkspace == null) {
			File xtFileStripped = XtFileData.stripXtExtension(xtFile);
			return createDefaultWorkspace(xtFileStripped.getName(), xtFileContent);
		} else {
			return XtSetupWorkspaceParser.parse(xtFile, setupWorkspace, xtFileContent);
		}
	}

	static XtWorkspace createDefaultWorkspace(String fileName, String xtFileContent) {
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		String moduleName = fileName.substring(0, fileName.length() - 1 - extension.length());

		Module xtFileModule = new Module(moduleName, extension);
		xtFileModule.setContents(xtFileContent);
		Folder srcFolder = new Folder(DEFAULT_SOURCE_FOLDER);
		srcFolder.addModule(xtFileModule);
		Project project = new Project(DEFAULT_PROJECT_NAME, VENDOR, VENDOR_NAME);
		project.addSourceFolder(srcFolder);
		XtWorkspace workspace = new XtWorkspace();
		workspace.addProject(project);
		workspace.moduleNameOfXtFile = fileName;
		return workspace;
	}

	static List<XtFileData.MethodData> getDefaultStartupMethodData() {
		List<MethodData> startupMethodData = new ArrayList<>();
		startupMethodData.add(new MethodData("startAndWaitForLspServer"));
		return startupMethodData;
	}

	static List<XtFileData.MethodData> getDefaultTeardownMethodData() {
		return Collections.emptyList();
	}

	static void fillTestMethodData(String fileName, String xtFileContent,
			TreeSet<XtFileData.MethodData> testMethodData1, TreeSet<XtFileData.MethodData> testMethodData2) {

		for (XtMethodIterator iter = new XtMethodIterator(fileName, xtFileContent); iter.hasNext();) {
			MethodData methodData = iter.next();
			addTestMethodData(testMethodData1, testMethodData2, methodData);
		}
	}

	private static void addTestMethodData(Collection<XtFileData.MethodData> testMethodData1,
			Collection<XtFileData.MethodData> testMethodData2, MethodData testMethodData) {

		switch (testMethodData.name) {
		case "nowarnings":
		case "noerrors":
		case "warnings":
		case "errors":
			testMethodData1.add(testMethodData);
			break;
		default:
			testMethodData2.add(testMethodData);
			break;
		}
	}

	static class XtMethodIterator extends AbstractIterator<MethodData> {
		static final String NL = "\n";
		static final String XT_KEYWORD = " XPECT ";
		static final String XT_FIXME = " FIXME ";
		static final String XT_IGNORE = "!";
		static final String XT_EXPECT_SL = "-->";
		static final String XT_EXPECT_ML = "---";
		static final String XT_EXPECT_ML_LIT = "===";

		final Map<String, Integer> methodNameCounters = new HashMap<>();
		final String fileName;
		final CommentIterator commentIter;

		Token commentToken = null;
		int cursorInComment = 0;

		XtMethodIterator(String fileName, String fullString) {
			this.fileName = fileName;
			this.commentIter = new CommentIterator(fullString);
		}

		@Override
		protected MethodData computeNext() {
			if (commentToken == null) {
				commentToken = commentIter.next();
				cursorInComment = 0;
			}
			String comment = commentToken.text;

			Token locKeyword = skipUntil(NL, comment, cursorInComment, XT_KEYWORD);
			while (locKeyword.isEOF && commentIter.hasNext()) {
				commentToken = commentIter.next();
				comment = commentToken.text;
				cursorInComment = 0;
				locKeyword = skipUntil(NL, comment, cursorInComment, XT_KEYWORD);
			}

			if (locKeyword.isEOF) {
				endOfData();
				return null;
			}

			Token locStart = locKeyword.pred == null ? new Token("", cursorInComment, null) : locKeyword.pred;
			Token locModifier = indexOf(comment, locKeyword.end,
					XT_FIXME, XT_IGNORE, XT_EXPECT_SL, XT_EXPECT_ML, XT_EXPECT_ML_LIT, NL);
			Token locExpectation = locModifier;

			switch (locModifier.text) {
			case XT_FIXME:
			case XT_IGNORE:
				locExpectation = indexOf(comment, locModifier.end,
						XT_EXPECT_SL, XT_EXPECT_ML, XT_EXPECT_ML_LIT, NL);
				break;
			default:
				locModifier = null;
			}

			Token locEnd = null;
			switch (locExpectation.text) {
			case "EOF":
			case NL:
				locEnd = new Token(NL, locExpectation.end, null);
				break;
			case XT_EXPECT_SL:
				locEnd = indexOf(comment, locExpectation.end, NL);
				break;
			case XT_EXPECT_ML:
				locEnd = indexOf(comment, locExpectation.end, XT_EXPECT_ML);
				break;
			case XT_EXPECT_ML_LIT:
				locEnd = indexOf(comment, locExpectation.end, XT_EXPECT_ML_LIT);
				break;
			default:
				endOfData();
				return null;
			}

			cursorInComment = locEnd.end;

			int offset = commentToken.start + locEnd.end;
			String mdComment = comment.substring(locStart.end, locKeyword.start).trim();
			String mdMethodAndArgs = comment.substring(locKeyword.end, locExpectation.start).trim();
			String mdExpectation = comment.substring(locExpectation.end, locEnd.start).trim();
			boolean isFixme = locModifier != null && XT_FIXME.equals(locModifier.text);
			boolean isIgnore = locModifier != null && XT_IGNORE.equals(locModifier.text);
			int idxEndOfName = mdMethodAndArgs.indexOf(" ");
			String name = idxEndOfName < 0 ? mdMethodAndArgs : mdMethodAndArgs.substring(0, idxEndOfName).trim();
			String args = idxEndOfName < 0 ? "" : mdMethodAndArgs.substring(idxEndOfName).trim();
			int counter = methodNameCounters.getOrDefault(name, 0);
			methodNameCounters.put(name, counter + 1);

			return new MethodData(fileName, mdComment, name, args, counter, mdExpectation, offset, isFixme, isIgnore);
		}
	}

	static class CommentIterator extends AbstractIterator<Token> {
		static final String ERROR = "File parse error: ";
		static final String COMMENT_SL_OPEN = "//";
		static final String NL = "\n";
		static final String COMMENT_ML_OPEN = "/*";
		static final String COMMENT_ML_CLOSE = "*/";
		static final String[] WHITESPACE = { " ", "\t", "\r", "\n" };

		final String fullString;

		int cursor;

		CommentIterator(String fullString) {
			this.fullString = fullString;
			this.cursor = 0;
		}

		@Override
		protected Token computeNext() {
			Token commentOpensAT = indexOf(fullString, cursor, COMMENT_SL_OPEN, COMMENT_ML_OPEN);
			if (commentOpensAT.isEOF) {
				endOfData();
				return null;
			}

			String comment = null;
			Token commentClosesAT;
			switch (commentOpensAT.text) {
			case COMMENT_SL_OPEN:
				Token tmpCommentOpensAT = commentOpensAT;
				commentClosesAT = indexOf(fullString, tmpCommentOpensAT.end, NL);
				comment = fullString.substring(commentOpensAT.end, commentClosesAT.start);

				tmpCommentOpensAT = indexOfOtherThan(fullString, commentClosesAT.end, WHITESPACE);
				while (!tmpCommentOpensAT.isEOF && tmpCommentOpensAT.text.startsWith(COMMENT_SL_OPEN)) {
					commentClosesAT = indexOf(fullString, tmpCommentOpensAT.start, NL);
					comment += NL + fullString.substring(tmpCommentOpensAT.start + COMMENT_SL_OPEN.length(),
							commentClosesAT.start);
					tmpCommentOpensAT = indexOfOtherThan(fullString, commentClosesAT.end, WHITESPACE);
				}

				break;
			case COMMENT_ML_OPEN:
				commentClosesAT = indexOf(fullString, commentOpensAT.end, COMMENT_ML_CLOSE);
				comment = fullString.substring(commentOpensAT.end, commentClosesAT.start);
				break;
			default:
				endOfData();
				return null;
			}

			cursor = commentClosesAT.end;
			return new Token(comment, commentOpensAT.end);
		}
	}

	static class Token {
		final String text;
		final int start;
		final int end;
		final Token pred;
		final boolean isEOF;

		Token(String name, int start) {
			this(name, start, null);
		}

		Token(String name, int start, Token pred) {
			this.text = name;
			this.start = start;
			this.end = start + name.length();
			this.pred = pred;
			this.isEOF = false;
		}

		Token(int end, Token pred) {
			this.text = "EOF";
			this.start = end;
			this.end = end;
			this.pred = pred;
			this.isEOF = true;
		}

		Token(int end) {
			this(end, null);
		}
	}

	static Token skipUntil(String skip, String content, int cursorStart, String... find) {
		return skipUntil(new String[] { skip }, content, cursorStart, find);
	}

	static Token skipUntil(String[] skip, String content, int cursorStart, String... find) {
		String[] search = concat(skip, find);
		Set<String> findSet = Sets.newHashSet(find);

		Token lastLoc = null;
		int currIdx = cursorStart;
		Token loc = null;
		while (!(loc = indexOf(content, currIdx, search)).isEOF) {
			if (findSet.contains(loc.text)) {
				return new Token(loc.text, loc.start, lastLoc);
			}
			lastLoc = loc;
			currIdx = loc.end;
		}
		return loc;
	}

	static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	static Token indexOf(String content, int cursorStart, String... strings) {
		int cursor = cursorStart;
		int minLength = Integer.MAX_VALUE;
		for (int i = 0; i < strings.length; i++) {
			minLength = Math.min(minLength, strings[i].length());
		}
		while (content.length() > cursor + minLength - 1) {
			for (int i = 0; i < strings.length; i++) {
				if (content.regionMatches(cursor, strings[i], 0, strings[i].length())) {
					return new Token(strings[i], cursor);
				}
			}
			cursor++;
		}
		return new Token(content.length());
	}

	static Token indexOfOtherThan(String content, int cursorStart, String... strings) {
		int cursor = cursorStart;
		int minLength = Integer.MAX_VALUE;
		for (int i = 0; i < strings.length; i++) {
			minLength = Math.min(minLength, strings[i].length());
		}
		while (content.length() > cursor + minLength) {
			boolean isOtherThan = true;
			for (int i = 0; i < strings.length; i++) {
				isOtherThan &= !content.regionMatches(cursor, strings[i], 0, strings[i].length());
			}
			if (isOtherThan) {
				return new Token(content.substring(cursor), cursor);
			}
			cursor++;
		}
		return new Token(content.length());
	}

}
