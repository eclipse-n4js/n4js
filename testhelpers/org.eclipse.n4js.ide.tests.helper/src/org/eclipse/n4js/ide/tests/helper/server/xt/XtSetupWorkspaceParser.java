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
import java.util.Iterator;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager;
import org.eclipse.n4js.tests.codegen.Workspace;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.ProjectBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.ProjectBuilder.FolderBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.ProjectBuilder.FolderBuilder.OtherFileBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.YarnProjectBuilder;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Parses the workspace configuration in the setup section of an .xt file
 */
public class XtSetupWorkspaceParser {
	static final String ERROR = "Workspace parse error: ";

	static class TokenStream implements Iterator<String> {
		final String[] tokens;
		int cursor;

		TokenStream(String setupWorkspace) {
			String commentsRemoved = setupWorkspace.replaceAll("\\/\\/[^\\n]*", "");
			this.tokens = commentsRemoved.trim().split("(?<=\\S)(?=\\{|\\})|(?<=\\{|\\})(?=\\S)|[=\\s]+");
			this.cursor = 0;
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
	 * @param xtFile
	 *            without {@code xt} extension
	 */
	static public XtWorkspace parse(File xtFile, String setupWorkspace, String xtFileContent) {
		TokenStream tokens = new TokenStream(setupWorkspace);
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
		} else {
			Preconditions.checkState(fromFile != null,
					ERROR + "Missing file name to load content from " + name + " in file " + xtFile.getPath());

			try {
				content = Files.readString(fromFile);
			} catch (IOException e) {
				Preconditions.checkState(false,
						ERROR + "Could not read: " + fromFile.toString() + " in file " + xtFile.getPath());
			}
		}

		Preconditions.checkState(content != null,
				ERROR + "Missing content of file " + name + " in file " + xtFile.getPath());

		int idx = name.lastIndexOf(".");
		String nameWithoutExtension = name.substring(0, idx);
		String extension = name.substring(idx + 1);
		boolean isModule = N4JSGlobals.ALL_N4_FILE_EXTENSIONS.contains(extension);
		OtherFileBuilder fileBuilder = null;
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

}
