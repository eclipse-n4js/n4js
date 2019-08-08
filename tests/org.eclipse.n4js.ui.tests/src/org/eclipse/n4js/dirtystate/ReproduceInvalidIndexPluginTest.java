package org.eclipse.n4js.dirtystate;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSUiInjectorProvider;
import org.eclipse.n4js.XtextParametrizedRunner;
import org.eclipse.n4js.XtextParametrizedRunner.Parameters;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.resource.UserdataMapper;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.RepeatedTest;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * Yet another test that ensures a valid index after the build was run in consequence of certain simulated user
 * interaction.
 */
@RunWith(XtextParametrizedRunner.class)
@InjectWith(N4JSUiInjectorProvider.class)
@SuppressWarnings("javadoc")
@RepeatedTest(times = ReproduceInvalidIndexPluginTest.REPETITIONS)
public class ReproduceInvalidIndexPluginTest extends AbstractBuilderParticipantTest {

	static final int REPETITIONS = 3;

	private static final String PROBANDS = "probands";
	private static final String PROBANDS_SUBFOLDER = "reproduce-invalid-index";

	@Rule
	public RepeatedTest.Rule repeatedTestRule = new RepeatedTest.Rule(false);
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

	@Test
	public void tryToCorruptIndexWithIncrementalBuild() throws Exception {
		importProjects(true);

		waitForAutoBuild();

		assertIndexState();
	}

	@Test
	public void tryToCorruptIndexIncrementallyWithoutSubsequentAutobuild() throws Exception {
		importProjects(true);

		assertXtextIndexIsValid();

		assertIndexState();
	}

	@Test
	public void tryToCorruptIndexWithFullBuild() throws Exception {
		importProjects(false);

		waitForAutoBuild();

		assertIndexState();
	}

	@Test
	public void tryToCorruptIndexWithoutSubsequentAutobuild() throws Exception {
		importProjects(false);

		assertXtextIndexIsValid();

		assertIndexState();
	}

	private void assertIndexState() throws Exception, IOException, CoreException {
		assertCientDescriptionUpToDate();
		assertAllDescriptionsHaveModuleData();

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("Client");
		IFile projectDescriptionFile = project.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));

		// line 5: Project depends on workspace project n4js-runtime which is missing in the node_modules folder.
		// Either install project Impl or introduce a yarn workspace of both of the projects.
		// line 6: Project depends on workspace project Impl which is missing in the node_modules folder.
		// Either install project Impl or introduce a yarn workspace of both of the projects.
		// line 7: Project depends on workspace project Def which is missing in the node_modules folder.
		// Either install project Impl or introduce a yarn workspace of both of the projects.
		assertMarkers("package.json should have 3 markers", projectDescriptionFile, 3);
		assertMarkers("workspace should have 3 markers", ResourcesPlugin.getWorkspace().getRoot(), 3);
	}

	private void importProjects(boolean incremental) throws CoreException {
		final File testdataLocation = new File(getResourceUri(PROBANDS, PROBANDS_SUBFOLDER));
		// not executing anything, so a dummy n4js-runtime is sufficient:
		ProjectTestsUtils.importProject(testdataLocation, N4JSGlobals.N4JS_RUNTIME);
		for (String projectName : projectsToImport) {
			ProjectTestsUtils.importProject(testdataLocation, new N4JSProjectName(projectName));
		}
		if (incremental) {
			// This should really be called incrementalBuild
			IResourcesSetupUtil.waitForBuild();
		} else {
			IResourcesSetupUtil.fullBuild();
		}
	}

	/**
	 * Assert that all descriptions in the index do have user data in their modules.
	 */
	private void assertAllDescriptionsHaveModuleData() throws IOException {
		Iterable<IResourceDescription> descriptions = getXtextIndex().getAllResourceDescriptions();
		for (IResourceDescription description : descriptions) {
			if (N4JSGlobals.ALL_N4_FILE_EXTENSIONS.contains(description.getURI().fileExtension())) {
				IEObjectDescription moduleDescription = Iterables
						.getOnlyElement(description.getExportedObjectsByType(TypesPackage.Literals.TMODULE));
				Assert.assertNotNull(description.getURI().toString(), moduleDescription);
				String moduleAsString = UserdataMapper.getDeserializedModuleFromDescriptionAsString(moduleDescription,
						description.getURI());
				Assert.assertNotNull(description.getURI().toString(), moduleAsString);
			}
		}
	}

	/**
	 * Assert that the resource description of the Client.n4js resource does contain TModule data
	 */
	private void assertCientDescriptionUpToDate() throws Exception {
		// import {A} from "A";
		// import * as B+ from "B";
		//
		// export export const a = new A();
		// B;a;

		IResourceDescription description = getXtextIndex()
				.getResourceDescription(URI.createPlatformResourceURI("Client/src/Client.n4js", false));
		Assert.assertNotNull(description);
		IEObjectDescription moduleDescription = Iterables
				.getOnlyElement(description.getExportedObjectsByType(TypesPackage.Literals.TMODULE));
		Assert.assertNotNull(moduleDescription);
		String moduleAsString = UserdataMapper.getDeserializedModuleFromDescriptionAsString(moduleDescription,
				description.getURI());
		Assert.assertNotNull(moduleAsString);
		Assert.assertEquals("<?xml version=\"1.0\" encoding=\"ASCII\"?>\n" +
				"<types:TModule xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:typeRefs=\"http://www.eclipse.org/n4js/ts/TypeRefs\" xmlns:types=\"http://www.eclipse.org/n4js/ts/Types\" qualifiedName=\"Client\" projectName=\"Client\" vendorID=\"org.eclipse.n4js\">\n"
				+
				"  <astElement href=\"#/0\"/>\n" +
				"  <variables name=\"a\" exportedName=\"a\" const=\"true\" newExpression=\"true\">\n" +
				"    <astElement href=\"#/0/@scriptElements.2/@exportedElement/@varDeclsOrBindings.0\"/>\n" +
				"    <typeRef xsi:type=\"typeRefs:ParameterizedTypeRef\">\n" +
				"      <declaredType href=\"../../Def/src/A.n4jsd#/1/@topLevelTypes.0\"/>\n" +
				"    </typeRef>\n" +
				"  </variables>\n" +
				"</types:TModule>\n" +
				"", moduleAsString);
	}

}
