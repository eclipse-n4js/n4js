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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.util.StringInputStream;

import com.google.common.base.Charsets;

/**
 */
public class EclipseBasedProjectModelSetup extends AbstractProjectModelSetup {

	private final IWorkspaceRoot workspace;

	/***/
	protected EclipseBasedProjectModelSetup(AbstractProjectModelTest host, IWorkspaceRoot workspace) {
		super(host);
		this.workspace = workspace;
	}

	@Override
	protected void deleteTempProjects() {
		try {
			org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.cleanWorkspace();
			org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.waitForBuild();
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void createTempProjects() {
		try {
			host.setMyProjectURI(createTempProject(host.myProjectId));
			createManifest(host.myProjectId, "ProjectId: " + host.myProjectId + "\n" +
					"ProjectType: library\n" +
					"ProjectVersion: 0.0.1-SNAPSHOT\n" +
					"VendorId: org.eclipse.n4js\n" +
					"VendorName: \"Eclipse N4JS Project\"\n" +
					"Libraries { \"" + LIB_FOLDER_NAME + "\"\n }\n" +
					"Output: \"src-gen\"" +
					"Sources {\n" +
					"	source {\n" +
					"		\"src\"\n" +
					"	}\n" +
					"}\n" +
					"ProjectDependencies { " + host.libProjectId + ", " + host.archiveProjectId + " } \n");
			createArchive(host.myProjectId);
			host.setLibProjectURI(createTempProject(host.libProjectId));
			createManifest(host.libProjectId, "ProjectId: " + host.libProjectId + "\n" +
					"ProjectType: library\n" +
					"ProjectVersion: 0.0.1-SNAPSHOT\n" +
					"VendorId: org.eclipse.n4js\n" +
					"VendorName: \"Eclipse N4JS Project\"\n" +
					"Output: \"src-gen\"" +
					"Sources {\n" +
					"	source { " +
					"		\"src\"\n" +
					"	}\n" +
					"}\n");
		} catch (CoreException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void createArchive(String projectName) throws CoreException, IOException {
		IProject project = workspace.getProject(projectName);
		IFolder libFolder = project.getFolder(LIB_FOLDER_NAME);
		libFolder.create(false, true, null);

		IFile archiveFile = libFolder.getFile(host.archiveProjectId + ".nfar");
		ByteArrayOutputStream byteArrayOutputSteam = new ByteArrayOutputStream();
		final ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputSteam);
		zipOutputStream.putNextEntry(new ZipEntry("src/A.js"));
		zipOutputStream.putNextEntry(new ZipEntry("src/B.js"));
		zipOutputStream.putNextEntry(new ZipEntry("src/sub/B.js"));
		zipOutputStream.putNextEntry(new ZipEntry("src/sub/C.js"));
		zipOutputStream.putNextEntry(new ZipEntry("src/sub/leaf/D.js"));

		zipOutputStream.putNextEntry(new ZipEntry(IN4JSProject.N4MF_MANIFEST));

		try {
			zipOutputStream.write(("ProjectId: " + host.archiveProjectId + "\n" +
					"ProjectType: library\n" +
					"ProjectVersion: 0.0.1-SNAPSHOT\n" +
					"VendorId: org.eclipse.n4js\n" +
					"VendorName: \"Eclipse N4JS Project\"\n" +
					"Libraries { \"" + LIB_FOLDER_NAME + "\"\n }\n" +
					"Output: \"src-gen\"" +
					"Sources {\n" +
					"	source { " +
					"		\"src\"\n" +
					"	}\n" +
					"}\n").getBytes(Charsets.UTF_8));

		} finally {
			zipOutputStream.close();
		}
		archiveFile.create(new ByteArrayInputStream(byteArrayOutputSteam.toByteArray()), false, null);

		host.setArchiveFileURI(URI.createPlatformResourceURI(archiveFile.getFullPath().toString(), true));
	}

	private void createManifest(String projectName, String string) throws CoreException, UnsupportedEncodingException {
		IProject project = workspace.getProject(projectName);
		IFile manifestFile = project.getFile(IN4JSProject.N4MF_MANIFEST);
		@SuppressWarnings("resource")
		StringInputStream content = new StringInputStream(string, Charsets.UTF_8.name());
		manifestFile.create(content, false, null);
		manifestFile.setCharset(Charsets.UTF_8.name(), null);

		IFolder src = project.getFolder("src");
		src.create(false, true, null);
		IFolder sub = src.getFolder("sub");
		sub.create(false, true, null);
		IFolder leaf = sub.getFolder("leaf");
		leaf.create(false, true, null);
		src.getFile("A.js").create(new ByteArrayInputStream(new byte[0]), false, null);
		src.getFile("B.js").create(new ByteArrayInputStream(new byte[0]), false, null);
		sub.getFile("B.js").create(new ByteArrayInputStream(new byte[0]), false, null);
		sub.getFile("C.js").create(new ByteArrayInputStream(new byte[0]), false, null);
		leaf.getFile("D.js").create(new ByteArrayInputStream(new byte[0]), false, null);
	}

	/***/
	protected URI createTempProject(String projectName) throws CoreException {
		IProjectDescription description = workspace.getWorkspace().newProjectDescription(projectName);
		// deliberately avoid the build command
		description.setNatureIds(new String[] { XtextProjectHelper.NATURE_ID });
		IProject newProject = workspace.getProject(projectName);
		newProject.create(null);
		newProject.open(null);
		newProject.setDescription(description, null);
		return URI.createPlatformResourceURI(projectName, true);
	}

}
