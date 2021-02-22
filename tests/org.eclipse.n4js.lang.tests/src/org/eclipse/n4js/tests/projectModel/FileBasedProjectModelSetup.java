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
package org.eclipse.n4js.tests.projectModel;

import static org.eclipse.n4js.N4JSGlobals.N4JS_RUNTIME;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 */
public class FileBasedProjectModelSetup extends AbstractProjectModelSetup<FileURI> {

	/** Version used for dummy "n4js-runtime" packages created with method {@link #createDummyN4JSRuntime(Path)}. */
	public static final String N4JS_RUNTIME_DUMMY_VERSION = "0.0.1-dummy";

	private File workspaceRoot;
	private final FileBasedWorkspace workspace;

	/***/
	public FileBasedProjectModelSetup(AbstractProjectModelTest<FileURI> host, FileBasedWorkspace workspace) {
		super(host);
		this.workspace = workspace;
	}

	@Override
	protected void deleteTempProjects() {
		try {
			deleteRecursively(workspaceRoot);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	/*
	 * deprecated in guava but save to use here
	 */
	private void deleteDirectoryContents(File directory) throws IOException {
		File[] files = directory.listFiles();
		if (files == null) {
			throw new IOException("Error listing files for " + directory);
		}
		for (File file : files) {
			deleteRecursively(file);
		}
	}

	/*
	 * deprecated in guava but save to use here
	 */
	private void deleteRecursively(File file) throws IOException {
		if (file.isDirectory()) {
			deleteDirectoryContents(file);
		}
		if (!file.delete()) {
			throw new IOException("Failed to delete " + file);
		}
	}

	@Override
	protected void createTempProjects() {
		try {
			workspaceRoot = Files.createTempDir();
			final FileURI myProjectURI = createTempProject(host.myProjectName);
			host.setMyProjectURI(myProjectURI);
			createProject(myProjectURI, "{\n" +
					"  \"name\": \"" + host.myProjectName + "\",\n" +
					"  \"version\": \"0.0.1-SNAPSHOT\",\n" +
					"  \"dependencies\": {\n" +
					"    \"" + N4JS_RUNTIME + "\": \"" + N4JS_RUNTIME_DUMMY_VERSION + "\",\n" +
					"    \"" + host.libProjectName + "\": \"0.0.1-SNAPSHOT\"\n" +
					"  },\n" +
					"  \"n4js\": {\n" +
					"    \"projectType\": \"library\",\n" +
					"    \"vendorId\": \"org.eclipse.n4js\",\n" +
					"    \"vendorName\": \"Eclipse N4JS Project\",\n" +
					"    \"output\": \"src-gen\",\n" +
					"    \"sources\": {\n" +
					"      \"source\": [\n" +
					"        \"src\"\n" +
					"      ]\n" +
					"    }\n" +
					"  }\n" +
					"}");
			final FileURI libProjectURI = createTempProject(host.libProjectName);
			host.setLibProjectURI(libProjectURI);
			createProject(libProjectURI, "{\n" +
					"  \"name\": \"" + host.libProjectName + "\",\n" +
					"  \"version\": \"0.0.1-SNAPSHOT\",\n" +
					"  \"dependencies\": {\n" +
					"    \"" + N4JS_RUNTIME + "\": \"" + N4JS_RUNTIME_DUMMY_VERSION + "\"\n" +
					"  },\n" +
					"  \"n4js\": {\n" +
					"    \"projectType\": \"library\",\n" +
					"    \"vendorId\": \"org.eclipse.n4js\",\n" +
					"    \"vendorName\": \"Eclipse N4JS Project\",\n" +
					"    \"output\": \"src-gen\",\n" +
					"    \"sources\": {\n" +
					"      \"source\": [\n" +
					"        \"src\"\n" +
					"      ]\n" +
					"    }\n" +
					"  }\n" +
					"}");
			workspace.registerProject(myProjectURI);
			workspace.registerProject(libProjectURI);
			workspace.registerProject(
					new FileURI(createDummyN4JSRuntime(workspaceRoot.toPath()).toFile()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a new temporary project with dummy modules in the 'src' folder and the given {@code package.json}
	 * content.
	 *
	 * @throws IOException
	 *             if the files cannot be created successfully.
	 */
	private void createProject(FileURI projectDir, String packageJSONContent) throws IOException {
		File directory = projectDir.toJavaIoFile();
		Files.asCharSink(new File(directory, PROJECT_DESCRIPTION_FILENAME), Charsets.UTF_8).write(packageJSONContent);
		File src = new File(directory, "src");
		assertTrue(src.mkdir());
		File sub = new File(src, "sub");
		assertTrue(sub.mkdir());
		File leaf = new File(sub, "leaf");
		assertTrue(leaf.mkdir());
		assertTrue(new File(src, "A.js").createNewFile());
		assertTrue(new File(src, "B.js").createNewFile());
		assertTrue(new File(sub, "B.js").createNewFile());
		assertTrue(new File(sub, "C.js").createNewFile());
		assertTrue(new File(leaf, "D.js").createNewFile());
	}

	/***/
	protected FileURI createTempProject(N4JSProjectName projectName) {
		File myProjectDir = new File(workspaceRoot, projectName.getRawName());
		if (!myProjectDir.mkdir()) {
			throw new RuntimeException();
		}
		return new FileURI(myProjectDir);
	}

	/**
	 * Creates a dummy version of "n4js-runtime" that is sufficient to avoid compilation errors in package.json files
	 * (i.e. missing dependency to "n4js-runtime") but won't suffice for executing N4JS code. Intended for simple tests
	 * that only require compilation.
	 * <p>
	 * The newly created npm package will have a special dummy version which is available as constant
	 * {@link #N4JS_RUNTIME_DUMMY_VERSION}, allowing clients to check for this particular version, where needed.
	 *
	 * @param location
	 *            path to a folder that will become the parent folder of the newly created npm package "n4js-runtime".
	 * @return path to the root folder of the newly created npm package.
	 */
	public static Path createDummyN4JSRuntime(Path location) {
		Path projectPath = location.resolve(N4JSGlobals.N4JS_RUNTIME.getRawName());
		Path packageJsonFile = projectPath.resolve(N4JSGlobals.PACKAGE_JSON);
		try {
			java.nio.file.Files.createDirectories(projectPath);
			java.nio.file.Files.write(packageJsonFile, Lists.newArrayList(
					"{",
					"    \"name\": \"" + N4JSGlobals.N4JS_RUNTIME + "\",",
					"    \"version\": \"" + N4JS_RUNTIME_DUMMY_VERSION + "\"",
					"}"));
		} catch (IOException e) {
			throw new RuntimeException("failed to create dummy n4js-runtime for testing purposes", e);
		}
		return projectPath;
	}
}
