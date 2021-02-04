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
import org.eclipse.n4js.tests.codegen.Workspace;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder;
import org.eclipse.n4js.tests.codegen.WorkspaceBuilder.FolderBuilder;
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
			System.out.println(current());
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

		public String expectNameInQuotes() {
			String name = next();
			Preconditions.checkState(name.startsWith("\""));
			Preconditions.checkState(name.endsWith("\""));
			name = name.substring(1, name.length() - 1);
			return name;
		}
	}

	/**
	 */
	static public Workspace parse(File xtFile, String setupWorkspace, String xtFileContent) {
		TokenStream tokens = new TokenStream(setupWorkspace);
		WorkspaceBuilder builder = new WorkspaceBuilder();

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

		return builder.build();
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

		String name = isThis ? stripXtExtension(xtFile) : tokens.expectNameInQuotes();
		String content = isThis ? xtFileContent : null;

		if (Objects.equal(tokens.current(), "{")) { // optional block
			tokens.expect("{");
			LOOP: while (tokens.hasNext()) {
				switch (tokens.next()) {
				case "from":
					String copyContentFrom = tokens.expectNameInQuotes();
					Path xtFileFolder = xtFile.toPath().getParent();
					Path contentFile = xtFileFolder.resolve(copyContentFrom);
					try {
						content = Files.readString(contentFile);
					} catch (IOException e) {
						Preconditions.checkState(false, ERROR + "Could not read: " + contentFile.toString());
					}
					break;
				case "}":
					break LOOP;
				default:
					Preconditions.checkState(false, ERROR + "Unexpected token in File: " + tokens.current());
				}
			}
		}

		Preconditions.checkNotNull(content, ERROR + "Missing 'from' property of file " + name);
		String extension = name.substring(name.lastIndexOf(".") + 1);
		boolean isModule = N4JSGlobals.ALL_N4_FILE_EXTENSIONS.contains(extension);
		if (isModule) {
			folderBuilder.addModule(name, content);
			folderBuilder.isSourceFolder = true;
		} else {
			folderBuilder.addFile(name, content);
		}
	}

	private static String stripXtExtension(File xtFile) {
		String nameWithXt = xtFile.getName();
		Preconditions.checkArgument(nameWithXt.endsWith("." + N4JSGlobals.XT_FILE_EXTENSION));
		String name = nameWithXt.substring(0, nameWithXt.length() - 1 - N4JSGlobals.XT_FILE_EXTENSION.length());
		return name;
	}

}
