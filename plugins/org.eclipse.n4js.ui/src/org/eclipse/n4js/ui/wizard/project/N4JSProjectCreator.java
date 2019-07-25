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
package org.eclipse.n4js.ui.wizard.project;

import static org.eclipse.core.resources.IResource.DEPTH_INFINITE;
import static org.eclipse.xtext.ui.XtextProjectHelper.BUILDER_ID;
import static org.eclipse.xtext.ui.XtextProjectHelper.NATURE_ID;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.ui.util.ProjectFactory;
import org.eclipse.xtext.ui.wizard.AbstractProjectCreator;
import org.eclipse.xtext.ui.wizard.IProjectInfo;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Creates a Eclipse project with Xtext nature, a src and a src-gen folder and generating an example N4JS file inside
 * the src folder and a package.json file in the project root. The name of the project as well as the contents of the
 * package.json are derived from the project name given by the user in the new project wizard.
 */
public class N4JSProjectCreator extends AbstractProjectCreator {

	private static Logger LOGGER = Logger.getLogger(N4JSProjectCreator.class);

	/** The default source folder name */
	private static final String SRC_ROOT = N4JSLanguageConstants.DEFAULT_PROJECT_SRC;
	/** The default test source folder name */
	private static final String TEST_SRC_ROOT = N4JSLanguageConstants.DEFAULT_PROJECT_TEST;

	/** The default generated source folder name */
	private static final String SRC_GEN = N4JSLanguageConstants.DEFAULT_PROJECT_OUTPUT;

	/** The default builders */
	private static final String[] BUILDERS = { BUILDER_ID };
	/** The default natures */
	private static final String[] NATURES = { NATURE_ID };
	/** The default project folders list */
	private static final List<String> SRC_FOLDER_LIST = ImmutableList.of(SRC_ROOT, SRC_GEN);

	/** The mangelhaft default dependencies */
	private static final List<N4JSProjectName> MANGELHAFT_DEPENDENCIES = Arrays.asList(N4JSGlobals.MANGELHAFT,
			N4JSGlobals.MANGELHAFT_ASSERT);

	// Set folder names to default values
	private String modelFolderName = SRC_ROOT;
	private final List<String> allFolders = new ArrayList<>(SRC_FOLDER_LIST);

	private IProject createdProject = null; // set in #createProject()

	@Inject
	private Provider<ProjectFactory> projectFactoryProvider;

	/**
	 * Returns the project created by this creator or <code>null</code> if not created yet (i.e. before
	 * {@link #createProject(IProgressMonitor)} is invoked).
	 */
	public IProject getCreatedProject() {
		return createdProject;
	}

	@Override
	protected ProjectFactory createProjectFactory() {
		return projectFactoryProvider.get();
	}

	@Override
	protected IProject createProject(IProgressMonitor monitor) {
		IProject project = super.createProject(monitor);
		createdProject = project;

		// Throw an exception if project is <code>null</code>.
		// A null-project indicates that something went wrong while creating the plain
		// Eclipse project. Due to the given Xtext infrastructure there is no
		// way to actually find out what went wrong without re-implementing {@link ProjectFactory}.
		if (null == project) {
			throw new NullPointerException("The project could not be created.");
		}

		return project;
	}

	@Override
	protected String getModelFolderName() {
		return modelFolderName;
	}

	@Override
	protected List<String> getAllFolders() {
		return allFolders;
	}

	@Override
	protected String[] getProjectNatures() {
		return NATURES;
	}

	@Override
	protected String[] getBuilders() {
		return BUILDERS;
	}

	/*
	 * Override setProjectInfo to change folder name presets according to the project info.
	 */
	@Override
	public void setProjectInfo(IProjectInfo projectInfo) {
		super.setProjectInfo(projectInfo);

		if (projectInfo instanceof N4JSProjectInfo &&
				ProjectType.TEST.equals(((N4JSProjectInfo) projectInfo).getProjectType())) {
			configureTestProject((N4JSProjectInfo) projectInfo);
		}
	}

	/**
	 * Configures the project creator to create a test project
	 */
	private void configureTestProject(N4JSProjectInfo projectInfo) {
		modelFolderName = TEST_SRC_ROOT;
		allFolders.add(TEST_SRC_ROOT);

		if (!projectInfo.getAdditionalSourceFolder()) {
			allFolders.remove(SRC_ROOT);
		}
	}

	@Override
	protected ProjectFactory configureProjectFactory(final ProjectFactory factory) {
		final ProjectFactory projectFactory = super.configureProjectFactory(factory);
		final N4JSProjectInfo projectInfo = (N4JSProjectInfo) getProjectInfo();
		if (null != projectInfo.getProjectLocation()) {
			projectFactory.setLocation(projectInfo.getProjectLocation());
		}
		return projectFactory;
	}

	@Override
	protected void enhanceProject(final IProject project, final IProgressMonitor monitor) throws CoreException {

		final N4JSProjectInfo pi = (N4JSProjectInfo) getProjectInfo();

		if (pi.getOutputFolder() == null) {
			// Set the default source output folder
			pi.setOutputFolder(SRC_GEN);
		}

		IWorkbench wb = PlatformUI.getWorkbench();
		wb.getWorkingSetManager().addToWorkingSets(project, pi.getSelectedWorkingSets());

		// IDEBUG-844 project name based string token used for generated files
		String projectName = pi.getProjectName();

		// create folders

		// folders in allFolders are already created by the super class
		List<String> otherFolders = Arrays.asList();
		for (Iterator<String> iterator = otherFolders.iterator(); iterator.hasNext();) {
			String folderName = iterator.next();
			IFolder folder = project.getFolder(folderName);
			folder.create(false, true, monitor);
		}

		// create files
		final String derivedProjectNameIdentifier = deriveN4JSIdentifierFromProjectName(projectName);
		Charset charset = getWorkspaceCharsetOrUtf8();

		// Path-Content map of the files to create
		Map<String, CharSequence> pathContentMap = new HashMap<>();

		// For test projects create a test project greeter if wanted
		if (ProjectType.TEST.equals(pi.getProjectType()) && pi.getCreateGreeterFile()) {
			pathContentMap.put(modelFolderName + "/" + "Test_" + derivedProjectNameIdentifier + ".n4js",
					N4JSNewProjectFileTemplates.getSourceFileWithTestGreeter(derivedProjectNameIdentifier));
		}

		// For other projects create the default greeter file
		if (!ProjectType.TEST.equals(pi.getProjectType()) && pi.getCreateGreeterFile()) {
			pathContentMap.put(modelFolderName + "/" + "GreeterModule_" + derivedProjectNameIdentifier + ".n4js",
					N4JSNewProjectFileTemplates.getSourceFileWithGreeterClass(projectName,
							derivedProjectNameIdentifier));
		}

		// create initial files
		for (Map.Entry<String, CharSequence> entry : pathContentMap.entrySet()) {
			createIfNotExists(project, entry.getKey(), entry.getValue(), charset, monitor);
		}

		// prepare the package.json contents
		List<String> sources = pi.getSourceFolders();
		List<String> tests = pi.getTestSourceFolders();

		// If it's a test project use model folder name as test source folder
		// and optionally add the source folder as source,
		if (ProjectType.TEST.equals(pi.getProjectType())) {
			if (pi.getAdditionalSourceFolder()) {
				sources.add(SRC_ROOT);
			}
			tests.add(modelFolderName);
		} else { // Otherwise add the model folder name as source folder
			sources.add(modelFolderName);
		}

		// Gather default project dependencies
		if (N4JSGlobals.PROJECT_TYPES_REQUIRING_N4JS_RUNTIME.contains(pi.getProjectType())) {
			List<String> projectDependencies = pi.getProjectDependencies();
			projectDependencies.add(N4JSGlobals.N4JS_RUNTIME.getRawName());
		}
		if (ProjectType.TEST.equals(pi.getProjectType())) {
			List<String> projectDependencies = pi.getProjectDependencies();
			for (N4JSProjectName mangelhaftDep : MANGELHAFT_DEPENDENCIES) {
				projectDependencies.add(mangelhaftDep.getRawName());
			}
			List<String> projectDevDependencies = pi.getProjectDevDependencies();
			projectDevDependencies.add(N4JSGlobals.MANGELHAFT_CLI.getRawName());
		}

		// Generate package.json content
		CharSequence projectDescriptionContent = N4JSNewProjectFileTemplates.getProjectDescriptionContents(pi);

		// create package.json file
		createIfNotExists(project, IN4JSProject.PACKAGE_JSON, projectDescriptionContent, charset, monitor);

		project.refreshLocal(DEPTH_INFINITE, monitor);
	}

	/**
	 * Derives a new N4JS (sub-)identifier from the given Eclipse N4JS project name.
	 *
	 * Makes sure the returned identifier does not include any characters that are not allowed in N4JS identifiers.
	 *
	 * Note that this method does not provide a bijective mapping between project names and N4JS identifier (e.g. scope
	 * name are not considered).
	 */
	private String deriveN4JSIdentifierFromProjectName(String projectName) {
		final String n4jsProjectName = ProjectDescriptionUtils.convertEclipseProjectNameToN4JSProjectName(projectName);
		final String plainProjectName = ProjectDescriptionUtils.getPlainProjectName(n4jsProjectName);
		return plainProjectName.replaceAll("\\.", "_").replaceAll("-", "_").trim();
	}

	/**
	 * Creates a new file with the given contents at the relative path, if no file at that path already exists.
	 *
	 * @param project
	 *            The project to create the file in
	 * @param relativePath
	 *            The file path inside the project
	 * @param contents
	 *            The file contents
	 * @param charset
	 *            The charset to use for the file writing
	 * @param monitor
	 *            The progress monitor to report to
	 * @throws CoreException
	 *             If the file creation fails. (See
	 *             {@link IFile#create(java.io.InputStream, boolean, IProgressMonitor)})
	 */
	private void createIfNotExists(IProject project, String relativePath, CharSequence contents, Charset charset,
			IProgressMonitor monitor) throws CoreException {
		IFile file = project.getFile(relativePath);
		if (!file.exists()) {
			file.create(FileContentUtil.from(contents, charset), false, monitor);
		}

	}

	/**
	 * Tries to get {@link org.eclipse.core.resources.IWorkspaceRoot#getDefaultCharset() workspace default charset}. In
	 * case of errors, will return {@link StandardCharsets#UTF_8}
	 *
	 * @return workspace charset or default one
	 */
	private Charset getWorkspaceCharsetOrUtf8() {
		try {
			return Charset.forName(super.getEncoding());
		} catch (CoreException | UnsupportedCharsetException e) {
			LOGGER.error("Cannot obtain workspace charset", e);
			LOGGER.info("Exceptions when obtaining workspace default charset, fall back to the "
					+ StandardCharsets.UTF_8.name(), e);
			return StandardCharsets.UTF_8;
		}
	}
}
