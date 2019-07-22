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
package org.eclipse.n4js.runner.tests

import com.google.common.base.Throwables
import com.google.inject.Inject
import com.google.inject.Injector
import java.io.File
import java.io.IOException
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSStandaloneSetup
import org.eclipse.n4js.internal.FileBasedWorkspace
import org.eclipse.n4js.packagejson.PackageJsonBuilder
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.runner.RunnerHelper
import org.eclipse.n4js.runner.RuntimeEnvironmentsHelper
import org.eclipse.n4js.runner.exceptions.DependencyCycleDetectedException
import org.eclipse.n4js.runner.exceptions.InsolvableRuntimeEnvironmentException
import org.eclipse.n4js.runner.^extension.RuntimeEnvironment
import org.eclipse.xtext.testing.GlobalRegistries
import org.eclipse.xtext.testing.GlobalRegistries.GlobalStateMemento
import org.eclipse.xtext.testing.IInjectorProvider
import org.eclipse.xtext.testing.IRegistryConfigurator
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.rules.TestName
import org.junit.runner.RunWith

import static java.nio.file.Files.createDirectory
import static java.nio.file.Files.createFile
import static java.nio.file.Files.write
import static java.nio.file.Paths.get
import static org.apache.log4j.Level.*
import static org.apache.log4j.Logger.getLogger
import static org.apache.log4j.Logger.getRootLogger
import static org.eclipse.n4js.runner.^extension.RuntimeEnvironment.*
import static org.hamcrest.core.IsCollectionContaining.*
import static org.hamcrest.core.IsNot.not
import static org.junit.Assert.*
import org.eclipse.n4js.projectModel.locations.SafeURI
import org.eclipse.n4js.projectModel.locations.FileURI
import org.eclipse.n4js.projectModel.names.N4JSProjectName

/**
 * Class for testing the the runtime environment resolution for the N4 runners in standalone JUnit mode.
 */
@RunWith(XtextRunner)
@InjectWith(SimpleInjectorProvider)
class RuntimeEnvironmentResolutionTest {

	public static class SimpleInjectorProvider implements IInjectorProvider, IRegistryConfigurator {
		protected GlobalStateMemento stateBeforeInjectorCreation
		protected GlobalStateMemento stateAfterInjectorCreation
		protected Injector injector
		
		override Injector getInjector() {
			if (injector === null) {
				GlobalRegistries.initializeDefaults()
				stateBeforeInjectorCreation=GlobalRegistries.makeCopyOfGlobalState() 
				this.injector=internalCreateInjector() 
				stateAfterInjectorCreation=GlobalRegistries.makeCopyOfGlobalState() 
			}
			return injector 
		}
		def protected Injector internalCreateInjector() {
			return new N4JSStandaloneSetup().createInjectorAndDoEMFRegistration() 
		}
		override void restoreRegistry() {
			stateBeforeInjectorCreation.restoreGlobalState() 
		}
		override void setupRegistry() {
			getInjector() 
			stateAfterInjectorCreation.restoreGlobalState() 
		}
	}

	private static Logger LOGGER = getLogger(RuntimeEnvironmentResolutionTest)

	/** Rule to make the current test name available in the test method. */
	@Rule
	public final TestName testName = new TestName();

	@Inject
	private extension RuntimeEnvironmentsHelper

	@Inject
	private extension RunnerHelper

	@Inject
	private IN4JSCore core
	
	@Inject
	private FileBasedWorkspace workspace
	
	@Inject
	@Rule
	public TemporaryFolder temporaryFolder

	private File workingDirectory

	@Before
	def void before() {
		getRootLogger.level = INFO;
		if (LOGGER.debugEnabled) {
			LOGGER.debug('-----------------------------------------------------------')
			LOGGER.debug('''| Executing «testName.methodName»''')
			LOGGER.debug('-----------------------------------------------------------')
		}
		try {
			workingDirectory = temporaryFolder.newFolder
		} catch (IOException e) {
			LOGGER.error('Error while creating temporary working directory for tests.', e)
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e)
		}
	}
	
	@After
	def void clearFileBasedWorkspace() {
		workspace.clear
	}

	/**
	 * When project has no dependencies and there is no REs available then there is no REs on which project can be executed.
	 */
	@Test
	def void testPositiveSingleDoesNotHaveRE() {
		val project = newBuilder.createProject('lib.project')

		assertTrue(project.findCompatibleRuntimeEnvironments.empty)
	}

	/**
	 * When project has no dependencies and there are some REs available then it is assumed project can be executed on all available REs.
	 */
	@Test
	def void testNegativeSingleProjectDoesNotHaveRE() {
		newBuilderForRE.createProject(ES5)
		newBuilderForRE.createProject(NODEJS)
		newBuilderForRE.createProject(CHROME)
		val project = newBuilder.createProject('lib.project')
		val res = project.findCompatibleRuntimeEnvironments;

		assertFalse(res.empty)
		assertTrue(res.contains(ES5))
		assertTrue(res.contains(NODEJS))
		assertTrue(res.contains(CHROME))
	}

	/**
	 * Resolving execution environment throws exception when called on project of type RE.
	 */
	@Test(expected = InsolvableRuntimeEnvironmentException)
	def void testCannotResolveExecutionEnvironmentForRuntimeEnvironmentProjectType() {
		newBuilderForRE.createProject(V8).findCompatibleRuntimeEnvironments;
	}
	
	/**
	 * Resolving execution environment throws exception when called on project of type RL.
	 */
	@Test(expected = InsolvableRuntimeEnvironmentException)
	def void testCannotResolveExecutionEnvironmentForRuntimeLibraryProjectType() {
		newBuilderForRL.createProject('v8.re.lib').findCompatibleRuntimeEnvironments;
	}

	/**
	 * Resolving the RE should fail when the project depends itself causing invalid package.json.
	 */
	@Test(expected = DependencyCycleDetectedException)
	def void testCannotResolveExecutionEnvironmentForProjectWithDependencyToItself() {
		newBuilder.withDependency(new N4JSProjectName('cycle.lib.project')).createProject('cycle.lib.project').findCompatibleRuntimeEnvironments;
	}

	/**
	 * Resolving the RE should fail when there is a cycle dependency among projects.
	 */
	@Test(expected = DependencyCycleDetectedException)
	def void testCannotResolveExecutionEnvironmentForProjectWithCycleDependency() {
		newBuilder.withDependency(new N4JSProjectName('cycle.lib.project.b')).createProject('cycle.lib.project.a').findCompatibleRuntimeEnvironments;
		newBuilder.withDependency(new N4JSProjectName('cycle.lib.project.c')).createProject('cycle.lib.project.b').findCompatibleRuntimeEnvironments;
		newBuilder.withDependency(new N4JSProjectName('cycle.lib.project.a')).createProject('cycle.lib.project.c').findCompatibleRuntimeEnvironments;
	}

	/**
	 * When project requires RL that is not provided by any RE, there is no RE it can execute on.
	 */
	@Test
	def void testNegativeSingleProjectWithRLAndRE() {
		newBuilderForRL.createProject('some.re.lib')
		newBuilderForRE.withProvidedRL('some.re.lib').createProject(CHROME)
		newBuilderForRL.createProject('another.different.re.lib')
		val project = newBuilder.withRequiredRL('another.different.re.lib').createProject('lib.project')

		assertTrue(project.findCompatibleRuntimeEnvironments.empty)
	}

	/**
	 * When there are two RLs provided by two REs, and project requires one of those RLs, than it can
	 * be executed on providing RE.
	 */
	@Test
	def void testPositiveSingleProjectUsesExactlyOneFromTwoIndependentREs() {
		newBuilderForRL.createProject('v8.re.lib')
		newBuilderForRE.withProvidedRL('v8.re.lib').createProject(V8)
		newBuilderForRL.createProject('some.re.lib')
		newBuilderForRE.withProvidedRL('chrome.re.lib').createProject(CHROME)
		val project = newBuilder.withRequiredRL('v8.re.lib').createProject('lib.project')

		assertTrue(project.findCompatibleRuntimeEnvironments.contains(V8))
		assertFalse('Chrome RE should not be available for V8 module.',
			project.findCompatibleRuntimeEnvironments.contains(CHROME)
		)
		assertEquals(1, project.findCompatibleRuntimeEnvironments.size)
	}

	/**
	 * Resolving execution environment for project which requires runtime library provided by available runtime environment
	 * resolves to that environment.
	 */
	@Test
	def void testPositiveSingleProjectUsesOneSingleRLFromOneSingleRE() {
		newBuilderForRL.createProject('iojs.re.lib')
		newBuilderForRE.withProvidedRL('iojs.re.lib').createProject(IOJS)
		val project = newBuilder.withRequiredRL('iojs.re.lib').createProject('lib.project')

		assertTrue(project.findCompatibleRuntimeEnvironments.contains(IOJS))
		assertEquals(1, project.findCompatibleRuntimeEnvironments.size)
	}

	/**
	 * When project requires one RL from one RE, and another RL from another RE,
	 * then there are zero REs that provide both RLs.
	 */
	@Test
	def void testNegativeSingleProjectUsesOneSingleRLFromOneSingleRE() {
		newBuilderForRL.createProject('iojs.re.lib')
		newBuilderForRE.withProvidedRL('iojs.re.lib').createProject(IOJS)
		newBuilderForRL.createProject('v8.re.lib')
		newBuilderForRE.withProvidedRL('v8.re.lib').createProject(V8)
		val project = newBuilder.withRequiredRL('iojs.re.lib').withRequiredRL('v8.re.lib').createProject('lib.project')

		assertFalse(project.findCompatibleRuntimeEnvironments.contains(IOJS))
		assertFalse(project.findCompatibleRuntimeEnvironments.contains(V8))
		assertTrue(project.findCompatibleRuntimeEnvironments.empty)
	}

	/**
	 * Two different REs with two different provided RLs, two different projects each requiring
	 * different RL, resolved REs are according to provided RL relation.
	 */
	@Test
	def void testPositiveIndependentProjectsHaveTheirMatchingRE() {
		newBuilderForRL.createProject('iojs.re.lib')
		newBuilderForRE.withProvidedRL('iojs.re.lib').createProject(IOJS)
		newBuilderForRL.createProject('v8.re.lib')
		newBuilderForRE.withProvidedRL('v8.re.lib').createProject(V8)
		val iojsProject = newBuilder.withRequiredRL('iojs.re.lib').createProject('iojs.lib.project')
		val v8Project = newBuilder.withRequiredRL('v8.re.lib').createProject('v8.lib.project')

		assertTrue(iojsProject.findCompatibleRuntimeEnvironments.contains(IOJS))
		assertFalse(iojsProject.findCompatibleRuntimeEnvironments.contains(V8))
		assertEquals(1, iojsProject.findCompatibleRuntimeEnvironments.size)
		assertTrue(v8Project.findCompatibleRuntimeEnvironments.contains(V8))
		assertFalse(v8Project.findCompatibleRuntimeEnvironments.contains(IOJS))
		assertEquals(1, v8Project.findCompatibleRuntimeEnvironments.size)
	}

	@Test
	def void testNegativeIndependentProjectsHaveTheirMatchingRE() {
		newBuilderForRL.createProject('iojs.re.lib')
		newBuilderForRE.withProvidedRL('iojs.re.lib').createProject(IOJS)
		newBuilderForRL.createProject('v8.re.lib')
		val iojsProject = newBuilder.withRequiredRL('iojs.re.lib').createProject('iojs.lib.project')
		val v8Project = newBuilder.withRequiredRL('v8.re.lib').createProject('v8.lib.project')

		assertTrue(iojsProject.findCompatibleRuntimeEnvironments.contains(IOJS))
		assertFalse(iojsProject.findCompatibleRuntimeEnvironments.contains(V8))
		assertEquals(1, iojsProject.findCompatibleRuntimeEnvironments.size)
		assertFalse(v8Project.findCompatibleRuntimeEnvironments.contains(V8))
		assertFalse('V8 module should not resolve IOJS RE as a possible environment.',
			v8Project.findCompatibleRuntimeEnvironments.contains(IOJS)
		)
		assertTrue(v8Project.findCompatibleRuntimeEnvironments.empty)
	}

	@Test
	def void testPositiveSingleProjectFindsREAndItsREExtension() {
		newBuilderForRL.createProject('es5.re.lib')
		newBuilderForRE.withProvidedRL('es5.re.lib').createProject(ES5)
		newBuilderForRL.createProject('nodejs.re.lib')
		newBuilderForRE.withExtendedRE(ES5.projectName.rawName).withProvidedRL('nodejs.re.lib').createProject(NODEJS)
		val es5Project = newBuilder.withRequiredRL('es5.re.lib').createProject('es5.lib.project')
		val nodeJsProject = newBuilder.withRequiredRL('nodejs.re.lib').createProject('nodejs.lib.project')

		assertEquals(2, es5Project.findCompatibleRuntimeEnvironments.size)
		assertTrue(es5Project.findCompatibleRuntimeEnvironments.contains(ES5))
		assertTrue(es5Project.findCompatibleRuntimeEnvironments.contains(NODEJS))
		assertEquals('Expecting both NodeJS RE and ES5 RuntimeEnvironemnt.',
					2, nodeJsProject.findCompatibleRuntimeEnvironments.size)
		assertTrue(nodeJsProject.findCompatibleRuntimeEnvironments.contains(NODEJS))
		assertTrue(nodeJsProject.findCompatibleRuntimeEnvironments.contains(ES5))
	}

	@Test
	def void testNegativeSingleProjectFindsREAndItsREExtension() {
		newBuilderForRL.createProject('es5.re.lib')
		newBuilderForRE.withProvidedRL('es5.re.lib').createProject(ES5)
		newBuilderForRL.createProject('nodejs.re.lib')
		newBuilderForRE.withProvidedRL('nodejs.re.lib').createProject(NODEJS)
		val es5Project = newBuilder.withRequiredRL('es5.re.lib').createProject('es5.lib.project')
		val nodeJsProject = newBuilder.withRequiredRL('nodejs.re.lib').createProject('nodejs.lib.project')

		assertEquals('Node.js RE should not be available for ES5 module. There are no association between the two REs. Expecting only the ES5 RE.',
			1, es5Project.findCompatibleRuntimeEnvironments.size
		)
		assertTrue(es5Project.findCompatibleRuntimeEnvironments.contains(ES5))
		assertFalse(es5Project.findCompatibleRuntimeEnvironments.contains(NODEJS))
		assertEquals(1, nodeJsProject.findCompatibleRuntimeEnvironments.size)
		assertTrue(nodeJsProject.findCompatibleRuntimeEnvironments.contains(NODEJS))
		assertFalse(nodeJsProject.findCompatibleRuntimeEnvironments.contains(ES5))
	}

	@Test
	def void testPositiveSingleProjectCanResolveREViaProjectDependencies() {
		newBuilderForRL.createProject('v8.re.lib')
		newBuilderForRE.withProvidedRL('v8.re.lib').createProject(V8)
		val rootProject = newBuilder.withRequiredRL('v8.re.lib').createProject('v8.root.lib.project')
		val subProject = newBuilder.withDependency(new N4JSProjectName('v8.root.lib.project')).createProject('v8.sub.lib.project')

		assertEquals(1, rootProject.findCompatibleRuntimeEnvironments.size)
		assertTrue(rootProject.findCompatibleRuntimeEnvironments.contains(V8))
		assertEquals(1, subProject.findCompatibleRuntimeEnvironments.size)
		assertTrue(subProject.findCompatibleRuntimeEnvironments.contains(V8))
	}

	@Test
	def void testNegativeSingleProjectCanResolveREViaProjectDependencies() {
		newBuilderForRL.createProject('v8.re.lib')
		newBuilderForRE.withProvidedRL('v8.re.lib').createProject(V8)
		val rootProject = newBuilder.withRequiredRL('v8.re.lib').createProject('v8.root.lib.project')
		newBuilderForRL.createProject('iojs.re.lib')
		newBuilderForRE.withProvidedRL('iojs.re.lib').createProject(IOJS)
		newBuilder.withRequiredRL('iojs.re.lib').createProject('some.other.project')
		val subProject = newBuilder.withDependency(new N4JSProjectName('some.other.project')).createProject('v8.sub.lib.project')

		assertEquals(1, rootProject.findCompatibleRuntimeEnvironments.size)
		assertTrue(rootProject.findCompatibleRuntimeEnvironments.contains(V8))
		assertEquals(1, subProject.findCompatibleRuntimeEnvironments.size)
		assertTrue(subProject.findCompatibleRuntimeEnvironments.contains(IOJS))
		assertFalse(subProject.findCompatibleRuntimeEnvironments.contains(V8))
	}

	@Test
	def void testPositiveSingleProjectFindsBothREWhenUsingLibsFromBaseREAndExtensionRE() {
		newBuilderForRL.createProject('es5.re.lib')
		newBuilderForRE.withProvidedRL('es5.re.lib').createProject(ES5)
		newBuilderForRL.createProject('nodejs.re.lib')
		newBuilderForRE.withExtendedRE(ES5.projectName.rawName).withProvidedRL('nodejs.re.lib').createProject(NODEJS)
		val project = newBuilder.withRequiredRL('es5.re.lib').withRequiredRL('nodejs.re.lib').createProject('lib.project')

		assertEquals('Expecting both NodeJS RE and ES5 RuntimeEnvironemnt.',
					2, project.findCompatibleRuntimeEnvironments.size)
		assertTrue(project.findCompatibleRuntimeEnvironments.contains(NODEJS))
		assertTrue(project.findCompatibleRuntimeEnvironments.contains(ES5))
	}

	@Test
	def void testNegativeSingleProjectFindsOnlyExtensionREWhenUsingLibsFromBaseREAndExtensionRE() {
		newBuilderForRL.createProject('es5.re.lib')
		newBuilderForRE.withProvidedRL('es5.re.lib').createProject(ES5)
		newBuilderForRE.withExtendedRE(ES5.projectName.rawName).withProvidedRL('nodejs.re.lib').createProject(NODEJS)
		newBuilderForRL.createProject('some.not.existing.lib')
		newBuilderForRL.createProject('another.not.existing.lib')
		val project = newBuilder.withRequiredRL('some.not.existing.lib').withRequiredRL('another.not.existing.lib').createProject('lib.project')

		assertTrue('No REs should be available for the module.',
			project.findCompatibleRuntimeEnvironments.empty
		)
		assertFalse(project.findCompatibleRuntimeEnvironments.contains(NODEJS))
		assertFalse(project.findCompatibleRuntimeEnvironments.contains(ES5))
	}

	@Test
	def void testPositiveSingleProjectCanResolveREViaAggregatedRL() {
		newBuilderForRL.createProject('iojs.re.lib')
		newBuilderForRE.withProvidedRL('iojs.re.lib').createProject(IOJS)
		newBuilderForRL.createProject('a.v8.re.lib')
		newBuilderForRL.createProject('b.v8.re.lib')
		newBuilderForRL.withProvidedRL('a.v8.re.lib').withProvidedRL('b.v8.re.lib').createProject('c.v8.re.lib')
		newBuilderForRE.withProvidedRL('c.v8.re.lib').createProject(V8)
		val project = newBuilder.withRequiredRL('a.v8.re.lib').createProject('lib.project')

		assertEquals(1, project.findCompatibleRuntimeEnvironments.size)
		assertTrue(project.findCompatibleRuntimeEnvironments.contains(V8))
		assertFalse(project.findCompatibleRuntimeEnvironments.contains(IOJS))
	}

	@Test
	def void testNegativeSingleProjectCanResolveREViaAggregatedRL() {
		newBuilderForRL.createProject('iojs.re.lib')
		newBuilderForRE.withProvidedRL('iojs.re.lib').createProject(IOJS)
		newBuilderForRL.createProject('a.v8.re.lib')
		newBuilderForRL.createProject('b.v8.re.lib')
		newBuilderForRL.withProvidedRL('a.v8.re.lib').withProvidedRL('b.v8.re.lib').createProject('some.name.but.not.the.proper.c.v8.re.lib')
		newBuilderForRE.withProvidedRL('c.v8.re.lib').createProject(V8)
		val project = newBuilder.withRequiredRL('a.v8.re.lib').createProject('lib.project')

		assertTrue(project.findCompatibleRuntimeEnvironments.empty)
	}

	/** IDEBUG 506, transitive dependencies for implementations-project are required. */
	@Test
	def void testRunnerFrontEnd_transitiveApiImplResolution_IDEBUG_506() {
		/*- // Do not auto-format. (http://stackoverflow.com/a/23016146/4886510)
		 *
		 * 	 Client
		 *    |
		 *    |
		 *    v
		 *   Aapi  --->  Bapi <...('id2').. BImpl2 ---> z
		 *    ^           ^
		 *    :           :
		 *    :           :
		 *   Aimpl       BImpl
		 *    |           |
		 *    |           |
		 *    v           v
		 *    x           y
		 *
		 * ---> = depends on
		 * ...> = implements  (implid), if not given its 'id1'
		 *
		 * Client should have Client,Aimpl,x,Bimpl,y on dependency path.
		 */

		val x = newBuilder.createProject('x');
		val y = newBuilder.createProject('y');
		val z = newBuilder.createProject('z');

		val bapi = newBuilder.createProject('Bapi');
		val bimpl = newBuilder.withImplementationId("id1").withImplementedProject('Bapi').withDependency(new N4JSProjectName('y')).createProject('Bimpl');
		val bimpl2 = newBuilder.withImplementationId("id2").withImplementedProject('Bapi').withDependency(new N4JSProjectName('z')).createProject('Bimpl2');

		val aapi = newBuilder.withDependency(new N4JSProjectName('Bapi')).createProject('Aapi');
		val aimpl = newBuilder.withImplementationId("id1").withImplementedProject('Aapi').withDependency(new N4JSProjectName('x')).createProject('Aimpl');

		val client = newBuilder.withDependency(new N4JSProjectName('Aapi')).createProject('Client');

		val v8rl = newBuilderForRL.createProject('v8.re.lib')
		val v8re = newBuilderForRE.withProvidedRL('v8.re.lib').createProject(V8)

		// URI to a concrete Module to run
		val URI clientModule = client.sourceContainers.get(0).location.appendSegment("ClientA.n4js").toURI;

		val dep_map = V8.getProjectExtendedDepsAndApiImplMapping(clientModule,new N4JSProjectName('id1'),true)
		val deps = dep_map.projects
		val extendedMap = dep_map.concreteApiImplProjectMapping

		// ensure right Mapping
		assertEquals( aimpl, extendedMap.get(aapi));
		assertEquals( bimpl, extendedMap.get(bapi));

		// ensure that all dependencies are there
		assertThat( deps, hasItems(client,aimpl,bimpl,x,y,v8rl,v8re,aapi,bapi));

		// but not with wrong id.
		assertThat("Should not have a dependency to z from different implID", deps, not(hasItem(z)))
		assertThat("Should not have a dependency to bimpl2 with different implID", deps, not(hasItem(bimpl2)))
	}


	@Test(expected=IllegalStateException)
	def void testRunnerFrontEnd_transitiveApiImplResolution_IDEBUG_506_negative() {
		/*- // Do not auto-format. (http://stackoverflow.com/a/23016146/4886510)
		 *
		 * 	 Client
		 *    |
		 *    |
		 *    v
		 *   Aapi  --->  Bapi <...('id2').. BImpl2 ---> z
		 *    ^
		 *    :       Missing-link-here
		 *    :
		 *   Aimpl       BImpl
		 *    |           |
		 *    |           |
		 *    v           v
		 *    x           y
		 *
		 * ---> = depends on
		 * ...> = implements  (implid), if not given its 'id1'
		 *
		 * Client should have Client,Aimpl,x,Bimpl,y on dependency path.
		 */

		newBuilder.createProject('x');
		newBuilder.createProject('y');
		newBuilder.createProject('z');

		newBuilder.createProject('Bapi');
		newBuilder.withImplementationId("id1")./*withImplementedAPI('Bapi').*/withDependency(new N4JSProjectName('y')).createProject('Bimpl');
		newBuilder.withImplementationId("id2").withImplementedProject('Bapi').withDependency(new N4JSProjectName('z')).createProject('Bimpl2');

		newBuilder.withDependency(new N4JSProjectName('Bapi')).createProject('Aapi');
		newBuilder.withImplementationId("id1").withImplementedProject('Aapi').withDependency(new N4JSProjectName('x')).createProject('Aimpl');

		val client = newBuilder.withDependency(new N4JSProjectName('Aapi')).createProject('Client');

		newBuilderForRL.createProject('v8.re.lib')
		newBuilderForRE.withProvidedRL('v8.re.lib').createProject(V8)

		// URI to a concrete Module to run
		val URI clientModule = client.sourceContainers.get(0).location.appendSegment("ClientA.n4js").toURI;


		V8.getProjectExtendedDepsAndApiImplMapping(clientModule, new N4JSProjectName('id1'),true)

	}

	@Test()
	def void testRunnerFrontEnd_transitiveApiImplResolution_IDEBUG_506_negative2() {
		/*- // Do not auto-format. (http://stackoverflow.com/a/23016146/4886510)
		 *
		 * 	 Client
		 *    |
		 *    |
		 *    v
		 *   Aapi  --->  Bapi <...('id2').. BImpl2 ---> z
		 *    ^
		 *    :       Missing-link-here
		 *    :
		 *   Aimpl       BImpl
		 *    |           |
		 *    |           |
		 *    v           v
		 *    x           y
		 *
		 * ---> = depends on
		 * ...> = implements  (implid), if not given its 'id1'
		 *
		 * Client should have Client,Aimpl,x,Bimpl,y on dependency path.
		 */

		val x = newBuilder.createProject('x');
		val y = newBuilder.createProject('y');
		val z = newBuilder.createProject('z');

		val bapi = newBuilder.createProject('Bapi');
		val bimpl = newBuilder.withImplementationId("id1")./*withImplementedAPI('Bapi').*/withDependency(new N4JSProjectName('y')).createProject('Bimpl');
		val bimpl2 = newBuilder.withImplementationId("id2").withImplementedProject('Bapi').withDependency(new N4JSProjectName('z')).createProject('Bimpl2');

		val aapi = newBuilder.withDependency(new N4JSProjectName('Bapi')).createProject('Aapi');
		val aimpl = newBuilder.withImplementationId("id1").withImplementedProject('Aapi').withDependency(new N4JSProjectName('x')).createProject('Aimpl');

		val client = newBuilder.withDependency(new N4JSProjectName('Aapi')).createProject('Client');

		val v8rl = newBuilderForRL.createProject('v8.re.lib')
		val v8re = newBuilderForRE.withProvidedRL('v8.re.lib').createProject(V8)

		// URI to a concrete Module to run
		val URI clientModule = client.sourceContainers.get(0).location.appendSegment("ClientA.n4js").toURI;



		val apiUsage = V8.getProjectExtendedDepsAndApiImplMapping(clientModule, new N4JSProjectName('id1'),false)
		val deps = apiUsage.projects
		val extendedMap = apiUsage.concreteApiImplProjectMapping

		assertEquals(true, apiUsage.isInErrorState)
		assertEquals(true, apiUsage.missingImplementationIds.contains(new N4JSProjectName('Bapi')))
		assertEquals(1, apiUsage.missingImplementationIds.size)


		// ensure right Mapping
		assertEquals(aimpl, extendedMap.get(aapi));
		assertNotEquals(bimpl2, extendedMap.get(bapi));

		// ensure that all dependencies are there
		assertThat(deps, hasItems(client,aimpl,/*bimpl,*/x,/*y,*/v8rl,v8re,aapi,bapi));
		assertThat(deps, not(hasItems(bimpl,y)));

		// but not with wrong id.
		assertThat("Should not have a dependency to z from different implID", deps, not(hasItem(z)))
		assertThat("Should not have a dependency to bimpl2 with different implID", deps, not(hasItem(bimpl2)))

	}

	@Test()
	def void testRunnerFrontEnd_transitiveApiImplResolution_IDEBUG_506_cyclic() {
		/*- // Do not auto-format. (http://stackoverflow.com/a/23016146/4886510)
		 *
		 *    Client
		 *      |
		 *      |
		 *      v
		 *  +->Aapi +---->Bapi <...('id2').. BImpl2 ---> z
		 *  |   ^   |       ^
		 *  |   :   |       :
		 *  |   :   |       :
		 *  |  Aimpl|      BImpl
		 *  |   |   |       |
		 *  |   |   |       |
		 *  |   v   |       v
		 *  |   x --+       y
		 *  |               |
		 *  +---------------+
		 *
		 *   ---> = depends on
		 * ...> = implements  (implid), if not given its 'id1'
		 *
		 * Client should have Client,Aimpl,x,Bimpl,y on dependency path.
		 */
		val x = newBuilder.withDependency(new N4JSProjectName('Bapi')).createProject('x');
		val y = newBuilder.withDependency(new N4JSProjectName('Aapi')).createProject('y');
		val z = newBuilder.createProject('z');

		val bapi = newBuilder.createProject('Bapi');
		val bimpl = newBuilder.withImplementationId("id1").withImplementedProject('Bapi').withDependency(new N4JSProjectName('y')).createProject('Bimpl');
		val bimpl2 = newBuilder.withImplementationId("id2").withImplementedProject('Bapi').withDependency(new N4JSProjectName('z')).createProject('Bimpl2');

		val aapi = newBuilder.withDependency(new N4JSProjectName('Bapi')).createProject('Aapi');
		val aimpl = newBuilder.withImplementationId("id1").withImplementedProject('Aapi').withDependency(new N4JSProjectName('x')).createProject('Aimpl');

		val client = newBuilder.withDependency(new N4JSProjectName('Aapi')).createProject('Client');

		val v8rl = newBuilderForRL.createProject('v8.re.lib')
		val v8re = newBuilderForRE.withProvidedRL('v8.re.lib').createProject(V8)

		// URI to a concrete Module to run
		val URI clientModule = client.sourceContainers.get(0).location.appendSegment("ClientA.n4js").toURI;


		val apiUsage = V8.getProjectExtendedDepsAndApiImplMapping(clientModule, new N4JSProjectName('id1'),true)
		val deps = apiUsage.projects
		val extendedMap = apiUsage.concreteApiImplProjectMapping

		assertEquals(false,apiUsage.isInErrorState)
		assertEquals(0,apiUsage.missingImplementationIds.size)


		// ensure right Mapping
		assertEquals( aimpl, extendedMap.get(aapi));
		assertNotEquals( bimpl2, extendedMap.get(bapi));

		// ensure that all dependencies are there
		assertThat( deps, hasItems(client,aimpl,bimpl,x,y,v8rl,v8re,aapi,bapi));

		// but not with wrong id.
		assertThat("Should not have a dependency to z from different implID", deps, not(hasItem(z)))
		assertThat("Should not have a dependency to bimpl2 with different implID", deps, not(hasItem(bimpl2)))

	}

	/**
	 * This method creates a new project in the workspace then generates a N4JS package.json file in the brand new project's root
	 * with the given content.
	 *
	 * @param projectName the unique projectName of the new project.
	 * @param packageJsonContent the content of the new package.json file.
	 * @return returns with the URI referencing the brand new project.
	 */
	protected def SafeURI<?> createProjectWithPackageJson(String projectName, String packageJsonContent) {
		val projectFolder = createProjectFolder(projectName)
		writePackageJsonContent(createPackageJsonFile(projectFolder), packageJsonContent)
		val result = new FileURI(projectFolder);
		workspace.registerProject(result) // Registers the project.
		return result
	}


	private def IN4JSProject createProject(PackageJsonBuilder builder, RuntimeEnvironment re) {
		createProject(builder, '''«re.getProjectName»''')
	}

	private def IN4JSProject createProject(PackageJsonBuilder builder, String projectName) {
		val content = builder.withName(projectName).build();
		if (LOGGER.debugEnabled) {
			LOGGER.debug('------------------------NEW PROJECT------------------------')
			LOGGER.debug('''New project: «projectName»''')
			LOGGER.debug('''Package.json contents:«'\n'»«content»''')
			LOGGER.debug('--------------------END OF NEW PROJECT---------------------')
		}
		val uri = createProjectWithPackageJson(projectName, content)
		val project = core.create(uri.toURI)
		assertTrue(project.exists)
		assertTrue(core.findProject(project.getLocation.toURI).present)
		return project
	}

	private def File createProjectFolder(String folderName) {
		try {
			val path = get(workingDirectory.toPath.toString, folderName)
			assertDirectoryAccessable(createDirectory(path).toFile.doDeleteOnExit)
		} catch (IOException e) {
			LOGGER.error('''Error while creating new temporary folder with name: '«folderName»'.''', e)
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e)
		}
	}

	private def File createPackageJsonFile(File projectFolder) {
		try {
			val path = get(projectFolder.toPath.toString, IN4JSProject.PACKAGE_JSON)
			assertAccessable(createFile(path).toFile.doDeleteOnExit)
		} catch (IOException e) {
			LOGGER.error('''Error while creating package.json file in folder '«projectFolder»'.''', e)
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e)
		}
	}

	private def File writePackageJsonContent(File packageJsonFile, String content) {
		try {
			return write(assertFileAccessable(packageJsonFile).toPath, content.getBytes).toFile
		} catch (IOException e) {
			LOGGER.error('Error while writing package.json file content.', e)
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e)
		}
	}

	private def assertFileAccessable(File file) {
		assertTrue(file.assertAccessable.file)
		file
	}

	private def assertDirectoryAccessable(File directory) {
		assertTrue(directory.assertAccessable.directory)
		directory
	}

	private def assertAccessable(File file) {
		assertNotNull(file)
		assertTrue(file.exists)
		assertTrue(file.canWrite)
		file
	}

	private def doDeleteOnExit(File file) {
		file.deleteOnExit
		file
	}
	
	/** 
	 * Convenience access to a pre-configured {@link PackageJsonBuilder} instances 
	 * with project type {@link ProjectType#RUNTIME_ENVIRONMENT}.
	 */
	private def PackageJsonBuilder newBuilderForRE() {
		return PackageJsonBuilder.newBuilder.withType(ProjectType.RUNTIME_ENVIRONMENT);
	}
	
	/** 
	 * Convenience access to a pre-configured {@link PackageJsonBuilder} instances 
	 * with project type {@link ProjectType#RUNTIME_LIBRARY}.
	 */
	private def PackageJsonBuilder newBuilderForRL() {
		return PackageJsonBuilder.newBuilder.withType(ProjectType.RUNTIME_LIBRARY);
	}
	
	/** Convenience access to a plain {@link PackageJsonBuilder} instances. */
	private def PackageJsonBuilder newBuilder() {
		return PackageJsonBuilder.newBuilder;
	}

}
