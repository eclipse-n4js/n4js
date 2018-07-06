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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.utils.URIUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 */
public class FileBasedProjectModelSetup extends AbstractProjectModelSetup {

	private File workspaceRoot;
	private final FileBasedWorkspace workspace;

	/***/
	protected FileBasedProjectModelSetup(AbstractProjectModelTest host, FileBasedWorkspace workspace) {
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
			final URI myProjectURI = URIUtils.normalize(createTempProject(host.myProjectId));
			host.setMyProjectURI(myProjectURI);
			createProject(myProjectURI, "{\n" +
					"  \"name\": \"" + host.myProjectId + "\",\n" +
					"  \"version\": \"0.0.1-SNAPSHOT\",\n" +
					"  \"dependencies\": {\n" +
					"    \"" + host.libProjectId + "\": \"0.0.1-SNAPSHOT\"\n" +
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
					"    },\n" +
					"    \"moduleLoader\": \"n4js\"\n" +
					"  }\n" +
					"}");
			final URI libProjectURI = URIUtils.normalize(createTempProject(host.libProjectId));
			host.setLibProjectURI(libProjectURI);
			createProject(libProjectURI, "{\n" +
					"  \"name\": \"" + host.libProjectId + "\",\n" +
					"  \"version\": \"0.0.1-SNAPSHOT\",\n" +
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
	private void createProject(URI projectDir, String packageJSONContent) throws IOException {
		File directory = new File(java.net.URI.create(projectDir.toString()));
		Files.write(packageJSONContent, new File(directory, PROJECT_DESCRIPTION_FILENAME), Charsets.UTF_8);
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
	protected URI createTempProject(String projectId) {
		File myProjectDir = new File(workspaceRoot, projectId);
		if (!myProjectDir.mkdir()) {
			throw new RuntimeException();
		}
		return URI.createURI(myProjectDir.toURI().toString()).trimSegments(1);
	}

}
