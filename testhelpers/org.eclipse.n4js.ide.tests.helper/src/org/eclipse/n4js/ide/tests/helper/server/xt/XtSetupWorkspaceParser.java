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
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.FolderBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.FolderBuilder.ModuleBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.ProjectBuilder;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 *
 */
public class XtSetupWorkspaceParser {
	static final String ERROR = "Workspace parse error: ";

	static class TokenStream implements Iterator<String> {
		final String[] tokens;
		int cursor;

		TokenStream(String setupWorkspace) {
			this.tokens = setupWorkspace.trim().split("=|\\s+");
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
	 * @param xtFile
	 *            without {@code xt} extension
	 */
	static public XtWorkspace parse(File xtFile, String setupWorkspace, String xtFileContent) {
		TokenStream tokens = new TokenStream(setupWorkspace);
		WorkspaceBuilder builder = new WorkspaceBuilder(new BuilderInfo());

		while (tokens.hasNext()) {
			switch (tokens.next()) {
			case "Project":
			case "JavaProject":
				parseProject(tokens, xtFile, xtFileContent, builder);
				break;
			default:
				Preconditions.checkState(false, ERROR + "Unexpected token in Workspace: " + tokens.current());
			}
		}

		XtWorkspace xtWorkspace = builder.build(new XtWorkspace());
		xtWorkspace.moduleNameOfXtFile = ((BuilderInfo) builder.builderInfo).moduleNameOfXtFile;
		return xtWorkspace;
	}

	/**
	 */
	private static void parseProject(TokenStream tokens, File xtFile, String xtFileContent,
			WorkspaceBuilder wsBuilder) {

		String projectName = tokens.expectNameInQuotes();
		ProjectBuilder prjBuilder = wsBuilder.addProject(projectName);
		parseContainerRest(tokens, xtFile, xtFileContent, prjBuilder, ".", "Project");
	}

	private static void parseContainerRest(TokenStream tokens, File xtFile, String xtFileContent,
			ProjectBuilder prjBuilder, String path, String metaName) {

		if (Objects.equal(tokens.current(), "{")) { // optional block
			tokens.expect("{");
			LOOP: while (tokens.hasNext()) {
				switch (tokens.next()) {
				case "Folder": {
					parseFolder(tokens, xtFile, xtFileContent, prjBuilder, path);
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
							ERROR + "Unexpected token in " + metaName + ": " + tokens.current());
				}
			}
		}
	}

	private static void parseFolder(TokenStream tokens, File xtFile, String xtFileContent,
			ProjectBuilder prjBuilder, String path) {

		String newPath = path + File.separator + tokens.expectNameInQuotes();
		parseContainerRest(tokens, xtFile, xtFileContent, prjBuilder, newPath, "Folder");
	}

	private static void parseFile(TokenStream tokens, File xtFile, String xtFileContent, boolean isThis,
			FolderBuilder folderBuilder) {

		String nameInQuotes = tokens.hasNameInQuotes() ? tokens.expectNameInQuotes() : null; // optional
		String content = isThis ? xtFileContent : null;
		Path fromFile = null;

		if (Objects.equal(tokens.current(), "{")) { // optional block
			tokens.expect("{");
			LOOP: while (tokens.hasNext()) {
				switch (tokens.next()) {
				case "from":
					Preconditions.checkState(!isThis, ERROR + "'This' file's content cannot be loaded using 'from'");

					String copyContentFrom = tokens.expectNameInQuotes();
					Path xtFileFolder = xtFile.toPath().getParent();
					fromFile = xtFileFolder.resolve(copyContentFrom);
					try {
						content = Files.readString(fromFile);
					} catch (IOException e) {
						Preconditions.checkState(false, ERROR + "Could not read: " + fromFile.toString());
					}
					break;
				case "}":
					break LOOP;
				default:
					Preconditions.checkState(false, ERROR + "Unexpected token in File: " + tokens.current());
				}
			}
		}

		String name = nameInQuotes != null ? nameInQuotes : //
				(isThis ? xtFile.getName() : //
						(fromFile != null ? fromFile.toFile().getName() : null));

		Preconditions.checkState(content != null && name != null, ERROR + "Missing 'from' property of file " + name);

		int idx = name.lastIndexOf(".");
		String nameWithoutExtension = name.substring(0, idx);
		String extension = name.substring(idx + 1);
		boolean isModule = N4JSGlobals.ALL_N4_FILE_EXTENSIONS.contains(extension);
		if (isModule) {
			ModuleBuilder moduleBuilder = folderBuilder.addModule(nameWithoutExtension, extension, content);
			folderBuilder.isSourceFolder = true;
			if (isThis) {
				BuilderInfo bi = moduleBuilder.getBuilderInfo();
				bi.moduleNameOfXtFile = moduleBuilder.getFolderBuilder().name + "/" + moduleBuilder.name;
			}
		} else {
			folderBuilder.addFile(name, content);
		}
	}

}
