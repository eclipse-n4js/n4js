package org.eclipse.n4js.smith.ui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.n4js.N4JSUiInjectorProvider;
import org.eclipse.n4js.projectModel.names.EclipseProjectName;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.transpiler.sourcemap.SourceMapFileLocator;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests SourceMapFileLocator within a workspace. This test needs to be run as plugin tests. This is why it is located
 * in the smith.ui.tests bundle rather than in the transpiler.tests bundle.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSUiInjectorProvider.class)
public class SourceMapFileLocatorPluginTest {

	@Inject
	SourceMapFileLocator sourceMapFileLocator;

	/**
	 * Imports and compiles test project
	 *
	 * @throws CoreException
	 *             in case project cannot be loaded
	 */
	@BeforeClass
	public static void setupEclipseWorkspace() throws CoreException {
		try {
			IResourcesSetupUtil.cleanWorkspace();
			importTestProject(new N4JSProjectName(("SVDemo")));
		} catch (Exception ex) {
			System.out.println("Cannot set up Eclipse workspace for smith.ui tests: " + ex);
			throw ex;
		}
	}

	/**
	 * Imports a project from the probands/sourcemaps folder
	 *
	 * @throws CoreException
	 *             in case loading project goes wrong
	 */
	private static IProject importTestProject(N4JSProjectName name) throws CoreException {
		IProject project = ProjectTestsUtils.importProject(new File("probands/sourcemaps"), name);
		// addNature(project, XtextProjectHelper.NATURE_ID);
		IResourcesSetupUtil.waitForBuild();
		return project;
	}

	/**
	 * If this test fails, all other tests will fail as well. This test defines the precondition of all other tests.
	 */
	@Test
	public void testSrcGenMapExist() {
		IProject project = ProjectTestsUtils.getProjectByName(new EclipseProjectName("SVDemo"));
		IFile fileSrc = project.getFile("src/pac/SVDemo.n4js");
		Assert.isTrue(fileSrc.exists());
		IFile fileGen = project.getFile("src-gen/pac/SVDemo.js");
		Assert.isTrue(fileGen.exists());
		IFile fileMap = project.getFile("src-gen/pac/SVDemo.map");
		Assert.isTrue(fileMap.exists());

		Assert.isNotNull(sourceMapFileLocator);
	}

	/**
	 * Ensures that the map file can be retrieved from a given source file within the current workspace.
	 */
	@Test
	public void testMapFromSrc() throws Exception {
		IProject project = ProjectTestsUtils.getProjectByName(new EclipseProjectName("SVDemo"));
		IFile fileSrc = project.getFile("src/pac/SVDemo.n4js");
		Path path = Paths.get(fileSrc.getLocationURI());
		File fileMap = sourceMapFileLocator.resolveSourceMapFromSrc(path);
		Assert.isTrue(fileMap.exists());
	}

	/**
	 * Ensures that the map file can be retrieved from a given generated file within the current workspace.
	 */
	@Test
	public void testMapFromGen() throws Exception {
		IProject project = ProjectTestsUtils.getProjectByName(new EclipseProjectName("SVDemo"));
		IFile fileGen = project.getFile("src-gen/pac/SVDemo.js");
		Path path = Paths.get(fileGen.getLocationURI());
		File fileMap = sourceMapFileLocator.resolveSourceMapFromGen(path);
		Assert.isTrue(fileMap.exists());
	}

}
