package org.eclipse.n4js.tests.misc;

import java.io.File;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.n4js.N4JSUiInjectorProvider;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Temporary test class intended only to reproduce and investigate a problem with the Xtext index. For details, see
 * {@link #reproduceInvalidIndex()}.
 */
@Ignore // this test is only intended for local testing -> ignore by default (e.g. on Jenkins)
@RunWith(XtextRunner.class)
@InjectWith(N4JSUiInjectorProvider.class)
@SuppressWarnings("javadoc")
public class ReproduceInvalidIndexPluginTest extends AbstractBuilderParticipantTest {

	private static final String PROBANDS = "probands";
	private static final String PROBANDS_SUBFOLDER = "reproduce-invalid-index";

	// SUCCESS: 6
	// FAILURE: 34
	// WEIRD : 0
	@Test
	public void testOftenBad() throws Exception {
		runTestOften(this::reproduceInvalidIndex, 40);
	}

	// *** 40 runs with waitForIdleState:
	// SUCCESS: 24
	// FAILURE: 16
	// WEIRD : 0
	// *** 40 runs with waitLongForIdleState, using attempts == 5:
	// SUCCESS: 40
	// FAILURE: 0
	// WEIRD : 0
	// *** 40 runs with waitLongForIdleState, using attempts == 1:
	// SUCCESS: 40
	// FAILURE: 0
	// WEIRD : 0
	// *** same, but without the call to ProjectTestsUtils#waitForAllJobs() in waitForIdleState:
	// SUCCESS: 40
	// FAILURE: 0
	// WEIRD : 0
	// *** same, but without the call to UIUtils#waitForUiThread() in waitForIdleState:
	// SUCCESS: 40
	// FAILURE: 0
	// WEIRD : 0
	// *** same, but neither calling #waitForAllJobs() nor #waitForUiThread():
	// SUCCESS: 25
	// FAILURE: 15
	// WEIRD : 0
	@Test
	public void testOftenWithWorkaround() throws Exception {
		runTestOften(this::avoidInvalidIndexWithWorkaround, 40);
	}

	@Test
	public void reproduceInvalidIndex() throws CoreException {
		final File testdataLocation = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER));

		ProjectTestsUtils.importProject(testdataLocation, "Client");
		ProjectTestsUtils.importProject(testdataLocation, "Def");
		ProjectTestsUtils.importProject(testdataLocation, "Impl");

		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertMarkers("workspace should have not markers", ResourcesPlugin.getWorkspace().getRoot(), 0);
	}

	@Test
	public void avoidInvalidIndexWithWorkaround() throws CoreException {
		final File testdataLocation = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER));

		ProjectTestsUtils.importProject(testdataLocation, "Client");
		ProjectTestsUtils.importProject(testdataLocation, "Def");
		ProjectTestsUtils.importProject(testdataLocation, "Impl");

		waitLongForIdleState(); // <---- the work-around
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertMarkers("workspace should have not markers", ResourcesPlugin.getWorkspace().getRoot(), 0);
	}

	@FunctionalInterface
	interface TestMethod {
		void test() throws Exception;
	}

	private void runTestOften(TestMethod testMethod, int count) throws Exception {
		int succCount = 0;
		int failCount = 0;
		int weirdCount = 0;
		for (int i = 0; i < count; i++) {
			System.out.print("Performing run #" + (i + 1) + " of " + count + " ...");
			try {
				testMethod.test();
				++succCount;
			} catch (AssertionError e) {
				if (e.getMessage().contains(
						"IResourceDescription in index must not be an instance of ResourceDescriptionWithoutModuleUserData")) {
					++failCount;
				} else {
					++weirdCount;
				}
			}
			System.out.println(" " + succCount + "/" + failCount + "/" + weirdCount);
			sleep(500);
			waitForIdleState();
			tearDown();
			sleep(500);
			waitForIdleState();
			sleep(500);
			waitForIdleState();
		}

		System.out.println("SUCCESS: " + succCount);
		System.out.println("FAILURE: " + failCount);
		System.out.println("WEIRD  : " + weirdCount);
	}

	private void waitLongForIdleState() {
		int attempts = 1;
		for (int i = 0; i < attempts; i++) {
			sleep(100);
			waitForIdleState();
		}
	}

	private void waitForIdleState() {
		ProjectTestsUtils.waitForAllJobs(Long.MAX_VALUE);
		UIUtils.waitForUiThread();
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new WrappedException(e);
		}
	}
}
