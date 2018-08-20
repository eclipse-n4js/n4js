package org.eclipse.n4js.tests.misc;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.n4js.N4JSUiInjectorProvider;
import org.eclipse.n4js.XtextParametrizedRunner;
import org.eclipse.n4js.XtextParametrizedRunner.Parameters;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.repeat.RepeatTest;
import org.eclipse.n4js.tests.repeat.RepeatedTestRule;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.ImmutableList;

/**
 * Temporary test class intended only to reproduce and investigate a problem with the Xtext index. For details, see
 * {@link #reproduceInvalidIndex()}.
 */
@RunWith(XtextParametrizedRunner.class)
@InjectWith(N4JSUiInjectorProvider.class)
@SuppressWarnings("javadoc")
public class ReproduceInvalidIndexPluginTest extends AbstractBuilderParticipantTest {

	private static final int REPITITIONS = 20;

	private static final String PROBANDS = "probands";
	private static final String PROBANDS_SUBFOLDER = "reproduce-invalid-index";

	@Rule
	public RepeatedTestRule repeatedTestRule = new RepeatedTestRule(false);
	private final List<String> projectsToImport;

	@Parameters(name = "{0}")
	public static List<List<String>> projectsToImport() {
		return ImmutableList.of(
				ImmutableList.of("Client", "Def", "Impl"),
				ImmutableList.of("Client", "Impl", "Def"),
				ImmutableList.of("Def", "Client", "Impl"),
				ImmutableList.of("Def", "Impl", "Client"),
				ImmutableList.of("Impl", "Client", "Def"),
				ImmutableList.of("Impl", "Def", "Client"));
	}

	public ReproduceInvalidIndexPluginTest(List<String> projectsToImport) {
		this.projectsToImport = projectsToImport;
	}

	@RepeatTest(times = REPITITIONS)
	@Test
	public void tryToCorruptIndex() throws CoreException {
		final File testdataLocation = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER));

		for (String projectName : projectsToImport) {
			ProjectTestsUtils.importProject(testdataLocation, projectName);
		}
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertMarkers("workspace should have not markers", ResourcesPlugin.getWorkspace().getRoot(), 0);
	}

	@RepeatTest(times = REPITITIONS)
	@Test
	public void tryToCorruptIndexWithoutSubsequentAutobuild() throws CoreException {
		final File testdataLocation = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER));

		for (String projectName : projectsToImport) {
			ProjectTestsUtils.importProject(testdataLocation, projectName);
		}
		IResourcesSetupUtil.fullBuild();
		assertXtextIndexIsValid();

		assertMarkers("workspace should have not markers", ResourcesPlugin.getWorkspace().getRoot(), 0);
	}

}
