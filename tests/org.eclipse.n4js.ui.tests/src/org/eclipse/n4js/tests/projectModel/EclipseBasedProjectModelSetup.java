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
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
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
			createProject(host.myProjectId, "{\n" +
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
			host.setLibProjectURI(createTempProject(host.libProjectId));
			createProject(host.libProjectId, "{\n" +
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
		} catch (CoreException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private void createProject(String projectName, String string) throws CoreException, UnsupportedEncodingException {
		IProject project = workspace.getProject(projectName);
		IFile projectDescriptionFile = project.getFile(PROJECT_DESCRIPTION_FILENAME);
		@SuppressWarnings("resource")
		StringInputStream content = new StringInputStream(string, Charsets.UTF_8.name());
		projectDescriptionFile.create(content, false, null);
		projectDescriptionFile.setCharset(Charsets.UTF_8.name(), null);

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
