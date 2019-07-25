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
import static org.eclipse.n4js.tests.util.ProjectTestsUtils.N4JS_RUNTIME_DUMMY_VERSION;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.util.StringInputStream;

import com.google.common.base.Charsets;

/**
 */
public class EclipseBasedProjectModelSetup extends AbstractProjectModelSetup<PlatformResourceURI> {

	private final IWorkspaceRoot workspace;

	/***/
	protected EclipseBasedProjectModelSetup(AbstractProjectModelTest<PlatformResourceURI> host,
			IWorkspaceRoot workspace) {
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
			host.setMyProjectURI(createTempProject(host.myProjectName));
			createProject(host.myProjectName, "{\n" +
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
			host.setLibProjectURI(createTempProject(host.libProjectName));
			createProject(host.libProjectName, "{\n" +
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
		} catch (CoreException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private void createProject(N4JSProjectName projectName, String string)
			throws CoreException, UnsupportedEncodingException {
		IProject project = workspace.getProject(projectName.toEclipseProjectName().getRawName());
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

		ProjectTestsUtils.createDummyN4JSRuntime(project);
	}

	/***/
	protected PlatformResourceURI createTempProject(N4JSProjectName projectName) throws CoreException {
		IProjectDescription description = workspace.getWorkspace()
				.newProjectDescription(projectName.toEclipseProjectName().getRawName());
		// deliberately avoid the build command
		description.setNatureIds(new String[] { XtextProjectHelper.NATURE_ID });
		IProject newProject = workspace.getProject(projectName.toEclipseProjectName().getRawName());
		newProject.create(null);
		newProject.open(null);
		newProject.setDescription(description, null);
		return new PlatformResourceURI(newProject);
	}

}
