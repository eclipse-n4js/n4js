package org.eclipse.n4js.tests.typedefinitions;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.n4js.N4JSUiInjectorProvider;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Simple plugin test that imports client, definition and implementation projects into the workspace and checks that the
 * type definition shadowing works as intended.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSUiInjectorProvider.class)
public class TypeDefinitionsShadowingPluginTest extends AbstractBuilderParticipantTest {
	private static final N4JSProjectName YARN_WORKSPACE_PROJECT = new N4JSProjectName("YarnWorkspaceProject");
	private static final String PROBANDS = "probands";
	private static final String PROBANDS_SUBFOLDER = "type-definitions";

	private static final String POSITIVE_FIXTURE_FOLDER = "simple-positive";
	private static final String NEGATIVE_FIXTURE_FOLDER = "simple-negative";

	/**
	 * Positive test case.
	 *
	 * Imports client, definition and implementation project and expects none of the projects to raise any compilation
	 * issues.
	 *
	 * The client project imports both module 'A' and 'B'. For 'A', the definition project provides type information,
	 * for 'B' it uses a dynamic namespace import, since the definition project does not provide any type information on
	 * it.
	 */
	@Test
	public void testValidTypeDefinitionsShadowing() throws CoreException {
		final File testdataLocation = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER, POSITIVE_FIXTURE_FOLDER));

		ProjectTestsUtils.importYarnWorkspace(libraryManager, testdataLocation, YARN_WORKSPACE_PROJECT);

		final IProject clientProject = ResourcesPlugin.getWorkspace().getRoot().getProject("Client");
		final IProject definitionProject = ResourcesPlugin.getWorkspace().getRoot().getProject("Def");
		final IProject implProject = ResourcesPlugin.getWorkspace().getRoot().getProject("Impl");

		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertMarkers("Client project should not have any markers (no compilation issues)", clientProject, 0);
		assertMarkers("Definition project should not have any markers (no compilation issues)", definitionProject, 0);
		assertMarkers("Implementation project should not have any markers (no compilation issues)", implProject, 0);
	}

	/**
	 * Negative test case.
	 *
	 * Imports client, definition and implementation project that declare an invalid type definitions configuration.
	 *
	 * More specifically, the "definesPackage" property of the definition project does not point to the intended
	 * implementation project. As a consequence, the client project cannot make use of any type information on the
	 * implementation project.
	 */
	@Test
	public void testInvalidTypeDefinitionsShadowing() throws CoreException {
		final File testdataLocation = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER, NEGATIVE_FIXTURE_FOLDER));

		ProjectTestsUtils.importYarnWorkspace(libraryManager, testdataLocation, YARN_WORKSPACE_PROJECT);

		final IProject clientProject = ResourcesPlugin.getWorkspace().getRoot().getProject("Broken_Client");
		final IProject definitionProject = ResourcesPlugin.getWorkspace().getRoot().getProject("Broken_Def");
		final IProject implProject = ResourcesPlugin.getWorkspace().getRoot().getProject("Impl");

		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		IResource clientModule = clientProject.findMember("src/Client.n4js");

		// line 2: Couldn't resolve reference to TExportableElement 'A'.
		// line 6: Couldn't resolve reference to IdentifiableElement 'A'.
		// line 2: The import of <A>(proxy) is unused.
		assertMarkers("Client module should have compilation issues, as type definitions cannot be resolved",
				clientModule, 3);

		assertMarkers("Definition project should not have any markers (no compilation issues)", definitionProject, 0);
		assertMarkers("Implementation project should not have any markers (no compilation issues)", implProject, 0);
	}
}
