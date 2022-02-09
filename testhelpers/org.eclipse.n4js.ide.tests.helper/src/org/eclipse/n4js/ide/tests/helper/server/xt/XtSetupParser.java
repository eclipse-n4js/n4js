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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager;
import org.eclipse.n4js.tests.codegen.Folder;
import org.eclipse.n4js.tests.codegen.OtherFile;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.tests.codegen.Workspace;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.ProjectBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.ProjectBuilder.FolderBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.ProjectBuilder.FolderBuilder.OtherFileBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.YarnProjectBuilder;
import org.eclipse.n4js.tests.codegen.YarnWorkspaceProject;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

/**
 * Parses the configuration in the Xt setup section of an .xt file.
 */
public class XtSetupParser {
	/** Add d.ts extension since modules will be created for d.ts files */
	public static final Collection<String> ALL_N4_MODULE_EXTENSIONS = new HashSet<>() {
		{
			addAll(N4JSGlobals.ALL_N4_FILE_EXTENSIONS);
			add(N4JSGlobals.DTS_FILE_EXTENSION);
		}
	};

	/** Keyword for activating {@link Project#isGenerateDts() .d.ts generation}. */
	public static final String GENERATE_DTS = "GENERATE_DTS";
	/** Keyword for adding a dependency to a project. Expects to be followed by a name in quotes. */
	public static final String DEPENDS_ON = "DEPENDS_ON";

	static final String ERROR = "Xt setup parse error: ";

	static class TokenStream implements Iterator<String> {
		final String[] tokens;
		final String[] tokensComplete;
		int cursor;

		TokenStream(String setupWorkspace) {
			String commentsRemoved = setupWorkspace.replaceAll("\\/\\/[^\\n]*", "");
			this.tokens = commentsRemoved.trim().split("(?<=\\S)(?=\\{|\\})|(?<=\\{|\\})(?=\\S)|[=\\s]+");
			this.cursor = 0;
			this.tokensComplete = new String[this.tokens.length];
			for (int i = 0, pos = 0; i < this.tokens.length; i++) {
				int idx = setupWorkspace.indexOf(this.tokens[i], pos);
				int endpos = (i + 1 == this.tokens.length)
						? setupWorkspace.length()
						: setupWorkspace.indexOf(this.tokens[i + 1], idx + this.tokens[i].length());
				this.tokensComplete[i] = setupWorkspace.substring(pos, endpos);
				pos = endpos;
			}
		}

		@Override
		public boolean hasNext() {
			return cursor < tokens.length;
		}

		/** @return current and moves cursor to next */
		@Override
		public String next() {
			return tokens[cursor++];
		}

		public String current() {
			return tokens[cursor];
		}

		public String currentComplete() {
			return tokensComplete[cursor];
		}

		public String lookLast() {
			return tokens[cursor - 1];
		}

		public String lookNext() {
			return tokens[cursor + 1];
		}

		public void expect(String expectation) {
			Preconditions.checkState(hasNext(), ERROR + "Sudden end. Expected: " + expectation);
			Preconditions.checkState(next().equals(expectation),
					ERROR + "Found: " + current() + ", but expected: " + expectation);
		}

		public boolean hasNameInQuotes() {
			if (!hasNext()) {
				return false;
			}
			String name = current();
			return name.startsWith("\"") && name.endsWith("\"");
		}

		public String expectNameInQuotes() {
			String name = next();
			Preconditions.checkState(name.startsWith("\""));
			Preconditions.checkState(name.endsWith("\""));
			name = name.substring(1, name.length() - 1);
			return name;
		}
	}

	static class BuilderInfo {
		String moduleNameOfXtFile;
	}

	/**
	 * This class extends {@link Workspace} by some properties that are important for the xt use case.
	 */
	static public class XtWorkspace extends Workspace {

		String moduleNameOfXtFile;

	}

	/**
	 * Configuration values as parsed from the Xt setup.
	 */
	static public class XtSetupParseResult {
		String runner;
		XtWorkspace workspace;
		boolean generateDts = false;
		final Map<String, String> files = new HashMap<>();
		final Set<String> enabledIssues = new HashSet<>();
		final Set<String> disabledIssues = new HashSet<>();
	}

	/**
	 * @param xtFile
	 *            without {@code xt} extension
	 */
	static public XtSetupParseResult parse(File xtFile, String setupStr, String xtFileContent) {
		TokenStream tokens = new TokenStream(setupStr);

		XtSetupParseResult result = new XtSetupParseResult();

		tokens.expect(XtFileDataParser.XT_SETUP_START);

		result.runner = tokens.next();

		while (tokens.hasNext()) {
			switch (tokens.next()) {
			case GENERATE_DTS:
				result.generateDts = true;
				break;
			case "IssueConfiguration":
				tokens.expect("{");
				parseIssueConfiguration(tokens, xtFile, result);
				break;
			case "Workspace":
				Preconditions.checkState(result.workspace == null,
						ERROR + "Multiple Workspace nodes in file " + xtFile.getPath());
				tokens.expect("{");
				result.workspace = parseWorkspace(tokens, xtFile, xtFileContent);
				break;
			case "File":
				parseFile(tokens, result);
				break;
			case XtFileDataParser.XT_SETUP_END:
				applyInlinedFileContents(result.workspace, result.files);
				applyMissingPackageJson(result.workspace);
				applyTopLevelGenerateDtsToAllProjects(result.workspace, result.generateDts);
				return result;
			default:
				Preconditions.checkState(false,
						ERROR + "Unexpected token in setup: " + tokens.lookLast() + " in file " + xtFile.getPath());
			}
		}

		throw new IllegalStateException(
				ERROR + "Unexpected end of Xt setup preamble in file " + xtFile.getPath());
	}

	private static void parseIssueConfiguration(TokenStream tokens, File xtFile, XtSetupParseResult result) {

		LOOP: while (tokens.hasNext()) {
			switch (tokens.next()) {
			case "IssueCode":
				parseIssueCode(tokens, xtFile, result);
				break;
			case "}":
				break LOOP;
			default:
				Preconditions.checkState(false,
						ERROR + "Unexpected token in IssueConfiguration: " + tokens.lookLast() + " in file "
								+ xtFile.getPath());
			}
		}
	}

	private static void parseIssueCode(TokenStream tokens, File xtFile, XtSetupParseResult result) {
		String issueCode = tokens.expectNameInQuotes();

		if (tokens.hasNext() && Objects.equal(tokens.current(), "{")) { // optional block
			tokens.next(); // consume opening curly brace
			tokens.expect("enabled");
			// tokens.expect("="); // note: tokenizer is removing '='
			switch (tokens.next().toLowerCase()) {
			case "true":
				result.disabledIssues.remove(issueCode);
				result.enabledIssues.add(issueCode);
				break;
			case "false":
				result.enabledIssues.remove(issueCode);
				result.disabledIssues.add(issueCode);
				break;
			default:
				Preconditions.checkState(false,
						ERROR + "Unexpected value for property 'enabled' of IssueCode in file " + xtFile.getPath());
			}
			tokens.expect("}");
		}
	}

	private static XtWorkspace parseWorkspace(TokenStream tokens, File xtFile, String xtFileContent) {
		WorkspaceBuilder builder = new WorkspaceBuilder(new BuilderInfo());
		YarnProjectBuilder yarnProjectBuilder = builder.addYarnProject(TestWorkspaceManager.YARN_TEST_PROJECT);

		LOOP: while (tokens.hasNext()) {
			switch (tokens.next()) {
			case "Project":
			case "JavaProject":
				parseProject(tokens, xtFile, xtFileContent, yarnProjectBuilder);
				break;
			case "}":
				break LOOP;
			default:
				Preconditions.checkState(false,
						ERROR + "Unexpected token in Workspace: " + tokens.lookLast() + " in file " + xtFile.getPath());
			}
		}

		XtWorkspace xtWorkspace = builder.build(new XtWorkspace());
		if (xtWorkspace.getProjects().size() == 1 && xtWorkspace.getProjects().get(0) instanceof YarnWorkspaceProject &&
				((YarnWorkspaceProject) xtWorkspace.getProjects().get(0)).getMemberProjects().size() == 1) {

			YarnWorkspaceProject yarnWorkspaceProject = (YarnWorkspaceProject) xtWorkspace.getProjects().get(0);
			if (yarnWorkspaceProject.getMemberProjects().size() == 1) {
				Project project = yarnWorkspaceProject.getMemberProjects().iterator().next();
				xtWorkspace.clearProjects();
				xtWorkspace.addProject(project);
			}
		}
		xtWorkspace.moduleNameOfXtFile = ((BuilderInfo) builder.builderInfo).moduleNameOfXtFile;
		return xtWorkspace;
	}

	private static void parseProject(TokenStream tokens, File xtFile, String xtFileContent,
			YarnProjectBuilder yarnProjectBuilder) {

		String projectName = tokens.expectNameInQuotes();
		ProjectBuilder prjBuilder = yarnProjectBuilder.addProject(projectName);
		parseContainerRest(tokens, xtFile, xtFileContent, prjBuilder, ".", "Project");
	}

	private static void parseContainerRest(TokenStream tokens, File xtFile, String xtFileContent,
			ProjectBuilder prjBuilder, String path, String metaName) {

		if (Objects.equal(tokens.current(), "{")) { // optional block
			tokens.expect("{");
			LOOP: while (tokens.hasNext()) {
				String currToken = tokens.next();
				switch (currToken) {
				case "SrcFolder": {
					parseFolder(tokens, xtFile, xtFileContent, prjBuilder, path, "src");
					break;
				}
				case "Folder": {
					String name = tokens.expectNameInQuotes();
					parseFolder(tokens, xtFile, xtFileContent, prjBuilder, path, name);
					break;
				}
				case "File": {
					FolderBuilder folderBuilder = prjBuilder.getOrAddFolder(path);
					parseFile(tokens, xtFile, xtFileContent, false, folderBuilder);
					break;
				}
				case "ThisFile": {
					FolderBuilder folderBuilder = prjBuilder.getOrAddFolder(path);
					parseFile(tokens, xtFile, xtFileContent, true, folderBuilder);
					break;
				}
				case GENERATE_DTS: {
					prjBuilder.setGenerateDts(true);
					break;
				}
				case DEPENDS_ON: {
					String arg = tokens.expectNameInQuotes();
					// for consistency with TestWorkspaceManager#CFG_DEPENDENCIES we support a comma-separated list:
					String[] names = arg.split(",");
					for (String name : names) {
						Preconditions.checkState(!name.isEmpty(), ERROR + "Empty project name: " + DEPENDS_ON + " "
								+ tokens.lookLast() + " in file " + xtFile.getPath());
						prjBuilder.addProjectDependency(name);
					}
					break;
				}
				case "}":
					break LOOP;
				default:
					Preconditions.checkState(false,
							ERROR + "Unexpected token in " + metaName + ": " + tokens.lookLast()
									+ " in file " + xtFile.getPath());
				}
			}
		}
	}

	private static void parseFolder(TokenStream tokens, File xtFile, String xtFileContent,
			ProjectBuilder prjBuilder, String path, String name) {

		String newPath = path + File.separator + name;
		prjBuilder.getOrAddFolder(newPath);
		parseContainerRest(tokens, xtFile, xtFileContent, prjBuilder, newPath, "Folder");
	}

	private static void parseFile(TokenStream tokens, File xtFile, String xtFileContent, boolean isThis,
			FolderBuilder folderBuilder) {

		File strippedXtFile = XtFileData.stripXtExtension(xtFile);
		Path xtFileFolder = xtFile.toPath().getParent();
		String nameInQuotes = tokens.hasNameInQuotes() ? tokens.expectNameInQuotes() : null; // optional
		Path fromFile = nameInQuotes == null ? null : xtFileFolder.resolve(nameInQuotes);

		if (Objects.equal(tokens.current(), "{")) { // optional block
			tokens.expect("{");
			LOOP: while (tokens.hasNext()) {
				switch (tokens.next()) {
				case "from":
					Preconditions.checkState(!isThis,
							ERROR + "'This' file's content cannot be loaded using 'from'" + " in file "
									+ xtFile.getPath());

					String copyContentFrom = tokens.expectNameInQuotes();
					fromFile = xtFileFolder.resolve(copyContentFrom).normalize();
					break;
				case "}":
					break LOOP;
				default:
					Preconditions.checkState(false,
							ERROR + "Unexpected token: " + tokens.current() + " in file " + xtFile.getPath());
				}
			}
		}

		boolean implicitIsThis = fromFile != null && fromFile.equals(xtFile.toPath());
		isThis |= implicitIsThis;
		String name = nameInQuotes != null ? nameInQuotes : //
				(isThis ? strippedXtFile.getName() : //
						(fromFile != null ? fromFile.toFile().getName() : null));

		Preconditions.checkState(name != null,
				ERROR + "Missing new file name in file " + xtFile.getPath());

		String content = null;
		if (isThis) {
			content = xtFileContent;
		} else if (fromFile != null && fromFile.toFile().isFile()) {
			try {
				content = Files.readString(fromFile);
			} catch (IOException e) {
				Preconditions.checkState(false,
						ERROR + "Could not read: " + fromFile.toString() + " in file " + xtFile.getPath());
			}
		} else {
			// content must be given in a 'File' section and is set later
		}

		String lastSegment = name.substring(name.lastIndexOf('/') + 1);
		int idx = lastSegment.lastIndexOf('.');
		String nameWithoutExtension = idx >= 0 ? name.substring(0, idx) : name;
		String extension = idx >= 0 ? name.substring(idx + 1) : null;
		boolean isModule = extension != null && ALL_N4_MODULE_EXTENSIONS.contains(extension);
		OtherFileBuilder fileBuilder;
		if (isModule) {
			fileBuilder = folderBuilder.addModule(nameWithoutExtension, extension, content);
			folderBuilder.setSourceFolder();
		} else {
			fileBuilder = folderBuilder.addFile(name, content);
		}
		if (isThis) {
			BuilderInfo bi = folderBuilder.getBuilderInfo();
			bi.moduleNameOfXtFile = fileBuilder.getPath();
		}
	}

	private static void parseFile(TokenStream tokens, XtSetupParseResult result) {
		String fileName = tokens.expectNameInQuotes();
		StringBuilder fileContent = new StringBuilder();
		tokens.expect("{");
		int openBlocks = 0;
		while (openBlocks >= 0 && tokens.hasNext()) {
			String token = tokens.current();
			if ("{".equals(token)) {
				openBlocks++;
			} else if ("}".equals(token)) {
				openBlocks--;
			}
			if (openBlocks >= 0) {
				String tokenComplete = tokens.currentComplete();
				fileContent.append(tokenComplete);
			}

			tokens.next();
		}
		result.files.put(fileName, fileContent.toString());
	}

	private static void applyInlinedFileContents(XtWorkspace workspace, Map<String, String> files) {
		if (workspace == null) {
			return; // nothing to do in this case
		}
		for (Project prj : workspace.getAllProjects()) {
			for (Folder srcFolder : prj.getSourceFolders()) {
				Iterable<OtherFile> allFiles = Iterables.concat(srcFolder.getModules(), srcFolder.getOtherFiles());
				for (OtherFile file : allFiles) {
					String mName = file.getNameWithExtension();
					if (file.getContents() == null) {
						if (files.containsKey(mName)) {
							String contents = files.get(mName);
							file.setContents(contents);
						} else {
							throw new IllegalStateException(ERROR + "File not found: " + mName);
						}
					}
				}
			}
		}
	}

	private static void applyMissingPackageJson(XtWorkspace workspace) {
		if (workspace == null) {
			return; // nothing to do in this case
		}
		for (Project prj : workspace.getAllProjects()) {
			if (prj.getProjectDescriptionContent() == null) {
				// infer default settings

			}
		}
	}

	private static void applyTopLevelGenerateDtsToAllProjects(Workspace workspace, boolean topLevelGenerateDts) {
		if (workspace == null || !topLevelGenerateDts) {
			return; // nothing to apply in this case
		}
		for (Project project : workspace.getAllProjects()) {
			if (!(project instanceof YarnWorkspaceProject)) {
				project.setGenerateDts(true);
			}
		}
	}
}
