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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.projectModel.IN4JSProject;

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
			URI myProjectURI = createTempProject(host.myProjectId);
			host.setMyProjectURI(myProjectURI);
			createManifest(myProjectURI, "ProjectId: " + host.myProjectId + "\n" +
					"ProjectType: library\n" +
					"ProjectVersion: 0.0.1-SNAPSHOT\n" +
					"VendorId: org.eclipse.n4js\n" +
					"VendorName: \"Eclipse N4JS Project\"\n" +
					"Libraries { \"" + LIB_FOLDER_NAME + "\"\n }\n" +
					"Output: \"src-gen\"\n" +
					"Sources {\n" +
					"	source {" +
					"		\"src\"\n" +
					"	}\n" +
					"}" +
					"ProjectDependencies { " + host.libProjectId + ", " + host.archiveProjectId + " }\n");
			createArchive(myProjectURI);
			URI libProjectURI = createTempProject(host.libProjectId);
			host.setLibProjectURI(libProjectURI);
			createManifest(libProjectURI, "ProjectId: " + host.libProjectId + "\n" +
					"ProjectType: library\n" +
					"ProjectVersion: 0.0.1-SNAPSHOT\n" +
					"VendorId: org.eclipse.n4js\n" +
					"VendorName: \"Eclipse N4JS Project\"\n" +
					"Libraries { \"" + LIB_FOLDER_NAME + "\"\n }\n" +
					"Output: \"src-gen\"\n" +
					"Sources {\n" +
					"	source {" +
					"		\"src\"\n" +
					"	}\n" +
					"}");
			workspace.registerProject(myProjectURI);
			workspace.registerProject(libProjectURI);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void createArchive(URI baseDir) throws IOException {
		File directory = new File(java.net.URI.create(baseDir.toString()));
		File lib = new File(directory, "lib");
		assertTrue(lib.mkdir());
		File nfar = new File(lib, host.archiveProjectId + ".nfar");
		final ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(nfar));
		zipOutputStream.putNextEntry(new ZipEntry("src/A.js"));
		zipOutputStream.putNextEntry(new ZipEntry("src/B.js"));
		zipOutputStream.putNextEntry(new ZipEntry("src/sub/B.js"));
		zipOutputStream.putNextEntry(new ZipEntry("src/sub/C.js"));
		zipOutputStream.putNextEntry(new ZipEntry("src/sub/leaf/D.js"));

		zipOutputStream.putNextEntry(new ZipEntry(IN4JSProject.N4MF_MANIFEST));
		// this will close the stream
		zipOutputStream.write(("ProjectId: " + host.archiveProjectId + "\n" +
				"ProjectType: library\n" +
				"ProjectVersion: 0.0.1-SNAPSHOT\n" +
				"VendorId: org.eclipse.n4js\n" +
				"VendorName: \"Eclipse N4JS Project\"\n" +
				"Output: \"src-gen\"\n" +
				"Sources {\n" +
				"	source {" +
				"		\"src\"\n" +
				"	}\n" +
				"}").getBytes(Charsets.UTF_8));
		zipOutputStream.close();
		host.setArchiveFileURI(URI.createURI(nfar.toURI().toString()));
	}

	private void createManifest(URI projectDir, String string) throws IOException {
		File directory = new File(java.net.URI.create(projectDir.toString()));
		Files.write(string, new File(directory, IN4JSProject.N4MF_MANIFEST), Charsets.UTF_8);
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
