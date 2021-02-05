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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.eclipse.n4js.tests.codegen.Folder;
import org.eclipse.n4js.tests.codegen.Module;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.tests.codegen.Workspace;

import com.google.common.base.Preconditions;

/**
 *
 */
public class XtFileDataParser {
	/** Identifier of test cases {@code X_PECT} (without '_') */
	// Note that there must not be any string like X_PECT (without '_') due to the X_PECT Eclipse plugin
	static final String XT_X_PECT = "X_PECT".replace("_", "");

	/** Pattern group name to reference the comment stated before {@value #XT_X_PECT} */
	static final String XT_COMMENT = "COMMENT";

	/**
	 * Pattern group name to reference the method name and its arguments stated after {@value #XT_X_PECT} but before
	 * {@code --->}/{@code ---}
	 */
	static final String XT_METHOD = "METHOD";

	/**
	 */
	static final String XT_RUNNER = "RUNNER";

	/**
	 */
	static final String XT_WORKSPACE = "WORKSPACE";

	/**
	 * Pattern group name to reference the expectation stated after {@code --->}/{@code ---}. <br/>
	 * <b>Note:</b> The expectation might still contain line breaks and comment artifacts like {@code //} (see
	 * {@link #XT_COMMENT_ARTIFACT_1}) or {@code *} (see {@link #XT_COMMENT_ARTIFACT_2}).
	 */
	static final String XT_EXPECTATION = "EXPECTATION";

	/** Artifacts of multi-line comments using {@code //} at the beginning of every line */
	static final String XT_COMMENT_ARTIFACT_1 = "[\\n][\\t ]*\\/\\/+";

	/** Artifacts of multi-line comments using {@code /*  *\/} to start/end the comment */
	static final String XT_COMMENT_ARTIFACT_2 = "[\\n][\\t ]*\\*";

	/**
	 * Pattern for single-line comments using {@code //} at the beginning. The XT method uses {@code -->} to start the
	 * expectation.
	 */
	static final Pattern XT_SINGLE_LINE = Pattern.compile(
			"[^\\n]*\\/\\/+(?<" + XT_COMMENT + ">[^\\n]*)\\s" + XT_X_PECT + "\\s(?<" + XT_METHOD + ">[^\\n]*)-->(?<"
					+ XT_EXPECTATION + ">[^\\n]*)");

	/**
	 * Pattern for multi-line comments using {@code //} at the beginning of every line. The XT method uses {@code ---}
	 * to start and end the expectation.
	 */
	static final Pattern XT_MULTI_LINE1 = Pattern.compile(
			"[^\\n]*\\/\\/+(?<" + XT_COMMENT + ">[^\\n]*)\\s" + XT_X_PECT + "\\s(?<" + XT_METHOD + ">[^\\n]*)---(?<"
					+ XT_EXPECTATION + ">[\\s\\S]*)---");
	/**
	 * Pattern for multi-line comments using {@code /*  *\/} to start/end the comment. The XT method uses {@code ---} to
	 * start and end the expectation.
	 */
	static final Pattern XT_MULTI_LINE2 = Pattern.compile(
			"[^\\n]*\\/\\*+(?<" + XT_COMMENT + ">[^\\n]*)\\s" + XT_X_PECT + "\\s(?<" + XT_METHOD + ">[^\\n]*)---(?<"
					+ XT_EXPECTATION + ">[\\s\\S]*?)---[\\s\\S]*?\\*\\/");

	/**
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

		Workspace workspace = getProject(xtFile, setupWorkspace, xtFileContent);
		List<MethodData> startupMethodData = getDefaultStartupMethodData();
		List<MethodData> teardownMethodData = getDefaultTeardownMethodData();
		List<MethodData> testMethodData = getTestMethodData(xtFileContent);
		return new XtFileData(xtFile, xtFileContent, setupRunner, workspace, startupMethodData, testMethodData,
				teardownMethodData);
	}

	static Workspace getProject(File xtFile, String setupWorkspace, String xtFileContent) {
		if (setupWorkspace == null) {
			return createDefaultProject(xtFile, xtFileContent);
		} else {
			return XtSetupWorkspaceParser.parse(xtFile, setupWorkspace, xtFileContent);
		}
	}

	static Workspace createDefaultProject(File xtFile, String xtFileContent) {
		String xtFileName = xtFile.getName();
		Preconditions.checkArgument(xtFileName.endsWith("." + N4JSGlobals.XT_FILE_EXTENSION));

		String fileName = xtFileName.substring(0, xtFileName.length() - 1 - N4JSGlobals.XT_FILE_EXTENSION.length());
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		String moduleName = fileName.substring(0, fileName.length() - 1 - extension.length());

		Module xtFileModule = new Module(moduleName, extension);
		xtFileModule.setContents(xtFileContent);
		Folder srcFolder = new Folder(DEFAULT_SOURCE_FOLDER);
		srcFolder.addModule(xtFileModule);
		Project project = new Project(DEFAULT_PROJECT_NAME, VENDOR, VENDOR_NAME);
		project.addSourceFolder(srcFolder);
		Workspace workspace = new Workspace();
		workspace.addProject(project);
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

	static List<XtFileData.MethodData> getTestMethodData(String xtFileContent) {
		List<XtFileData.MethodData> methodData = new ArrayList<>();
		Map<String, Integer> methodNameCounters = new HashMap<>();

		for (Matcher matcher = XT_SINGLE_LINE.matcher(xtFileContent); matcher.find();) {
			methodData.add(createMethodData(matcher, null, methodNameCounters));
		}

		for (Matcher matcher = XT_MULTI_LINE1.matcher(xtFileContent); matcher.find();) {
			methodData.add(createMethodData(matcher, XT_COMMENT_ARTIFACT_1, methodNameCounters));
		}

		for (Matcher matcher = XT_MULTI_LINE2.matcher(xtFileContent); matcher.find();) {
			methodData.add(createMethodData(matcher, XT_COMMENT_ARTIFACT_2, methodNameCounters));
		}

		return methodData;
	}

	private static MethodData createMethodData(Matcher matcher, String findAndRemove,
			Map<String, Integer> methodNameCounters) {

		int offset = matcher.end();
		String comment = matcher.group(XT_COMMENT);
		String methodAndArgs = matcher.group(XT_METHOD);
		String expectation = matcher.group(XT_EXPECTATION);
		if (findAndRemove != null) {
			expectation = expectation.replaceAll(findAndRemove, expectation);
		}
		String[] nameAndArgsArray = methodAndArgs.split("\\s+");
		Preconditions.checkArgument(nameAndArgsArray.length > 0);
		String name = nameAndArgsArray[0];
		String[] args = Arrays.copyOfRange(nameAndArgsArray, 1, nameAndArgsArray.length);
		int counter = methodNameCounters.getOrDefault(name, 0);
		methodNameCounters.put(name, counter + 1);
		return new MethodData(comment, name, args, counter, expectation, offset);
	}
}
