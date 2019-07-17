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
package org.eclipse.n4js.tester;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.valueOf;
import static java.util.UUID.randomUUID;
import static org.eclipse.n4js.AnnotationDefinition.TEST_METHOD;
import static org.eclipse.xtext.EcoreUtil2.getContainerOfType;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.fileextensions.FileExtensionType;
import org.eclipse.n4js.fileextensions.FileExtensionsRegistry;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.tester.domain.ID;
import org.eclipse.n4js.tester.domain.TestCase;
import org.eclipse.n4js.tester.domain.TestSuite;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.inject.Inject;

/**
 * Helper to collect all tests in a given N4JS project, sub-folder, or file.
 */
public class TestDiscoveryHelper {

	@Inject
	private FileExtensionsRegistry fileExtensionRegistry;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	private static final EClass T_CLASS = TypesPackage.eINSTANCE.getTClass();

	/**
	 * Comparator to ensure URIs without a fragment comes before than URIs with fragments. Otherwise falls back simply
	 * to hash code.
	 */
	private static final Comparator<URI> URI_COMPARATOR = (left, right) -> {
		if (left.hasFragment()) {
			return right.hasFragment() ? left.hashCode() - right.hashCode() : -1;
		}
		if (right.hasFragment()) {
			return left.hasFragment() ? left.hashCode() - right.hashCode() : 1;
		}
		return left.hashCode() - right.hashCode();
	};

	/**
	 * Creates a new, globally unique ID for a test session (the ID value stored in a {@link TestTree}).
	 */
	public ID createTestSessionId() {
		return new ID(randomUUID().toString());
	}

	/**
	 * Checks if the resource at the given location can be executed as a test. The location may point to an N4JS project
	 * or a folder or file within an N4JS project.
	 * <p>
	 * This method is intended as a very first check to decide if the user should be given the option to launch a test
	 * or not; for example, to enable or disable a "Run as test" context menu item). This method does not check all
	 * details because many checks are better handled at later stages when meaningful error messages can be provided to
	 * the user.
	 */
	public boolean isTestable(final URI location) {

		if (null == location) {
			return false;
		}

		if (isProject(location)) {
			// location points an N4JS project
			// --> must contain at least 1 source container of type "test"
			// (do *not* check if the folder contains test classes (for performance reasons and
			// to show better error messages; same behavior as in JUnit support in Eclipse))
			final IN4JSProject p = n4jsCore.create(location);
			return p.getSourceContainers().stream().anyMatch(IN4JSSourceContainer::isTest);
		} else {

			// otherwise:
			// (1) if the location URI contains the special test method fragment
			// then extract the fragment and check the location for the module.
			if (location.hasFragment()) {
				return isTestable(location.trimFragment());
			}

			// (2) location must lie in a source container of type "test"
			final IN4JSSourceContainer c = n4jsCore.findN4JSSourceContainer(location).orNull();
			if (c == null || !c.isTest())
				return false;
			// (3) if the location points to an n4js-file, it must contain at least one test class
			final ResourceSet resourceSet = n4jsCore.createResourceSet(Optional.of(c.getProject()));
			final IResourceDescriptions index = n4jsCore.getXtextIndex(resourceSet);
			final IResourceDescription rdesc = index.getResourceDescription(location);
			if (rdesc != null) {
				return stream(rdesc.getExportedObjectsByType(T_CLASS))
						.anyMatch(desc -> hasTestMethods(resourceSet, desc));
			} else {
				// we did not find the nestedLocation in the index but test (1) above passed, so
				// we assume that nestedLocation points to a folder within a test source container
				// (do *not* check if the folder contains test classes (for performance reasons and
				// to show better error messages; same behavior as in JUnit support in Eclipse))
				return true;
			}
		}
	}

	/**
	 * Collects all test methods available at the given location and returns a {@link TestTree} in which each test
	 * method is represented by a {@link TestCase}. The location may point to an N4JS project or a folder or file within
	 * an N4JS project.
	 *
	 * @param uris
	 *            the locations referencing to a resource. Can be an N4JS project, or a folder or a file within an N4JS
	 *            project.
	 * @return The test tree representing one or more test cases. Never returns with {@code null}, but the returned test
	 *         tree may be empty.
	 */
	public TestTree collectTests(final List<URI> uris) {
		final ResourceSet resSet = n4jsCore.createResourceSet(Optional.absent());
		final IResourceDescriptions index = n4jsCore.getXtextIndex(resSet);
		return collectTests(resSet, index, uris).sort();
	}

	/**
	 * Collects all test cases from the underlying (N4JS core-based) workspace and returns with a new test tree instance
	 * representing those test cases.
	 *
	 * @return a test tree representing all test cases in the workspace.
	 */
	public TestTree collectAllTestsFromWorkspace() {
		List<URI> testableProjectURIs = new LinkedList<>();
		for (IN4JSProject project : n4jsCore.findAllProjects()) {
			URI location = project.getLocation().toURI();
			if (project.exists() && isTestable(location)) {
				testableProjectURIs.add(location);
			}
		}
		TestTree collectedTests = collectTests(testableProjectURIs);
		return collectedTests;
	}

	private ID createTestCaseId(final String testClassFqn, final TMethod testMethod) {
		return new ID(testClassFqn + "#" + testMethod.getName());
	}

	/**
	 * Most clients should use method {@link #collectTests(List)} instead.
	 * <p>
	 * Low-level method to collect all test modules, i.e. N4JS files containing classes containing at least one method
	 * annotated with &#64;Test, as {@link IResourceDescription}s.
	 */
	private List<URI> collectDistinctTestLocations(final IResourceDescriptions index, final ResourceSet resSet,
			List<URI> locations) {
		return newArrayList(
				newHashSet(locations.stream().flatMap(loc -> collectTestLocations(index, resSet, loc)).iterator()));
	}

	/**
	 * Most clients should use method {@link #collectTests(List)} instead!
	 * <p>
	 * Low-level method to collect all test modules, i.e. N4JS files containing classes containing at least one method
	 * annotated with &#64;Test, as {@link IResourceDescription}s.
	 */
	private Stream<URI> collectTestLocations(final IResourceDescriptions index, final ResourceSet resSet,
			URI location) {

		if (null == location) {
			return Stream.empty();
		}

		// does location point to an N4JS project?
		if (isProject(location)) {
			// yes
			// --> collect all test modules (files containing test classes) located in source containers of type "test"
			final IN4JSProject p = n4jsCore.create(location);
			return p.getSourceContainers()
					.stream()
					.filter(IN4JSSourceContainer::isTest)
					.flatMap(TestDiscoveryHelper::stream) // note: IN4JSSourceContainer is an Iterable<URI>
					// filter out everything but N4JS files.
					.filter(uri -> isTestFile(uri) && isTestModule(resSet, index.getResourceDescription(uri)));
		}

		// does location point to an n4js file?
		final IResourceDescription resDesc = index.getResourceDescription(location.trimFragment());
		if (resDesc != null) {
			// yes --> is it a test module? (i.e. does it contain test classes and the class is not abstract?)
			if (isTestModule(resSet, resDesc)) {
				// yes --> is it contained in a source container of type "test"?
				final IN4JSSourceContainer srcContainer = n4jsCore.findN4JSSourceContainer(location.trimFragment())
						.orNull();
				if (srcContainer != null && srcContainer.isTest()) {
					// yes --> return it!
					return Stream.of(location); // return location with fragment! (if any)
				}
			}
			return Stream.empty();
		}

		// does location point to a source container (or sub-folder)?
		final IN4JSSourceContainer srcContainer = n4jsCore.findN4JSSourceContainer(location).orNull();
		if (srcContainer != null) {
			// yes --> is this a source container of type "test"?
			if (srcContainer.isTest()) {
				// yes --> collect all test modules (files containing test classes) in this source container
				final String locationStr = location.toString();
				return stream(srcContainer) // note: IN4JSSourceContainer is an Iterable<URI>
						// TODO improve?
						.filter(uri -> uri.toString().startsWith(locationStr)
								&& isTestModule(resSet, index.getResourceDescription(uri)));
			}
			return Stream.empty();
		}
		// invalid location URI
		return Stream.empty();
	}

	private TestTree collectTests(final ResourceSet resSet, final IResourceDescriptions index,
			final List<URI> locations) {

		// create test cases (aggregated in test suites)
		final Map<String, TestSuite> suites = new LinkedHashMap<>();

		// create MultiMap from trimmed(!) URI -> original URIs for this prefix URI
		final List<URI> testLocations = collectDistinctTestLocations(index, resSet, locations);
		// module URI --> test case URIs
		final HashMultimap<URI, URI> testLocationMapping = createTestLocationMapping(testLocations);
		// module URI --> module
		final Map<URI, TModule> moduleUri2Modules = loadModules(testLocationMapping.asMap().keySet(), index, resSet);

		for (final URI moduleLocation : testLocationMapping.keySet()) {

			final TModule module = moduleUri2Modules.get(moduleLocation);
			if (null == module) {
				continue; // Assuming broken AST.
			}

			// make sure trimmed URIs processed before non-trimmed ones, in other words we collects all tests for
			// test modules first, then later we can skip individual test collecting for test methods.
			// so we can make sure, iff there are more than one elements in the list, and the first one equals to
			// the module location URI (with any fragments), we can skip the processing, since the whole module
			// contains the individual test locations
			final List<URI> testLocationsForModule = newArrayList(testLocationMapping.get(moduleLocation));
			testLocationsForModule.sort(URI_COMPARATOR);

			if (testLocationsForModule.get(0).equals(moduleLocation)) {
				// The first item is referencing the entire module
				// --> collect all test methods in module and ignore remaining URIs
				collectTestCasesAndSuitesForModule(module, suites);
			} else {
				// we have URIs pointing to individual test methods -> collect all URIs
				for (final URI uri : testLocationsForModule) {
					collectTestSuiteAndTestCaseForMethod(uri, module, suites);
				}
			}
		}

		// if test cases are selected, name of tree is first test method and number of more tests
		final ID sessionId = createTestSessionId();
		String name = valueOf(sessionId);
		if (!locations.isEmpty()) {
			final URI uri = locations.get(0);
			name = valueOf(uri.trimFragment()).replaceFirst("platform:/resource/", "");
			if (name.lastIndexOf('.') > 0) {
				name = name.substring(0, name.lastIndexOf('.'));
			}
			// Assuming one single test case.
			if (uri.hasFragment() && !suites.isEmpty() && !suites.values().iterator().next().getTestCases().isEmpty()) {
				name += "#" + suites.values().iterator().next().getTestCases().get(0).getDisplayName();
			}
			if (locations.size() > 1) {
				name += " and " + (locations.size() - 1) + " more";
			}
		}
		return new TestTree(sessionId, suites.values(), name);
	}

	private void collectTestSuiteAndTestCaseForMethod(final URI uri, final TModule module,
			Map<String, TestSuite> suites) {
		final EObject object = module.eResource().getResourceSet().getEObject(uri, true);
		final Type type = (object instanceof N4MethodDeclaration) ? ((N4MethodDeclaration) object).getDefinedType()
				: (object instanceof TMethod) ? (TMethod) object : null;
		if (type instanceof TMethod) {
			final TMethod method = (TMethod) type;
			TestSuite testSuite = addOrCreateSuite((TClass) method.getContainingType(), suites);
			testSuite.add(createTestCase(method, module));
		}
	}

	/**
	 * Collect test cases for all all top level non-abstract exported classes, exclude everything else.
	 */
	private void collectTestCasesAndSuitesForModule(final TModule module, Map<String, TestSuite> suites) {
		for (final TClass clazz : from(module.getTopLevelTypes()).filter(TClass.class)
				.filter(c -> !c.isAbstract() && c.isExported())) {
			Iterable<TMethod> testMethods = getAllTestMethodsOfClass(clazz);
			if (testMethods.iterator().hasNext()) {
				TestSuite testSuite = addOrCreateSuite(clazz, suites);
				for (final TMethod method : testMethods) {
					final TestCase testCase = createTestCase(method, module, getTestCatalogNameFor(clazz));
					testSuite.add(testCase);
				}
			}
		}
	}

	private TestSuite addOrCreateSuite(TClass clazz, Map<String, TestSuite> suites) {
		String suiteKey = getTestCatalogNameFor(clazz);
		TestSuite suite = suites.get(suiteKey);
		if (suite == null) {
			suite = new TestSuite(suiteKey);
			suites.put(suiteKey, suite);
		}
		return suite;
	}

	private TestCase createTestCase(final TMethod method, final TModule module) {
		return createTestCase(method, module, getClassName(method));
	}

	private TestCase createTestCase(final TMethod method, final TModule module, final String clazzFqnStr) {
		final TestCase testCase = new TestCase(createTestCaseId(clazzFqnStr, method), clazzFqnStr,
				module.getProjectName(), method.getName(), method.getName(), EcoreUtil.getURI(method));
		return testCase;
	}

	private String getClassName(final TMethod method) {
		return getTestCatalogNameFor(getContainerOfType(method, TClass.class));
	}

	/**
	 * Returns the name to be used in the test catalog for the given {@link TClass}. We use the fully qualified name for
	 * this purpose.
	 */
	private String getTestCatalogNameFor(TClass clazz) {
		String classStr = "";
		if (clazz.getDeclaredVersion() > 0) {
			classStr = resourceNameComputer.getFullyQualifiedTypeName(clazz) + N4IDLGlobals.COMPILED_VERSION_SEPARATOR
					+ clazz.getDeclaredVersion();
		} else {
			classStr = resourceNameComputer.getFullyQualifiedTypeName(clazz);
		}

		IN4JSProject project = n4jsCore.findProject(clazz.eResource().getURI()).orNull();
		if (project != null) {
			String output = project.getOutputPath();
			if (Strings.isNullOrEmpty(output) == false && output != ".") {
				if (output.endsWith("/")) {
					classStr = output + classStr;
				} else {
					classStr = output + "/" + classStr;
				}
			}
		}

		return classStr;
	}

	private Map<URI, TModule> loadModules(final Iterable<URI> moduleUris, final IResourceDescriptions index,
			final ResourceSet resSet) {

		final Map<URI, TModule> uri2Modules = newHashMap();
		for (final URI moduleUri : moduleUris) {
			final IResourceDescription resDesc = index.getResourceDescription(moduleUri);
			uri2Modules.put(moduleUri, n4jsCore.loadModuleFromIndex(resSet, resDesc, false));
		}
		return uri2Modules;
	}

	/**
	 * Creates a mapping between the container module URI and the actual URIs. Consider cases when the list contains two
	 * locations URIs from two methods in the same class Then this method will return with multimap where the single key
	 * is the location to the container module and the value is the two method URIs.
	 */
	private HashMultimap<URI, URI> createTestLocationMapping(List<URI> testLocations) {
		HashMultimap<URI, URI> moduleUris2testUris = HashMultimap.create();
		for (final URI testLocation : testLocations) {
			moduleUris2testUris.put(testLocation.trimFragment(), testLocation);
		}
		return moduleUris2testUris;
	}

	private Iterable<TMethod> getAllTestMethodsOfClass(final TClass cls) {
		final TModule module = N4JSResource.getModule(cls.eResource());
		return from(containerTypesHelper.fromContext(module).allMembers(cls, false, false)).filter(TMethod.class)
				.filter(m -> isTestMethod(m));
	}

	private boolean isProject(final URI location) {
		// TODO change implementation!!!
		try {
			return n4jsCore.create(location).exists();
		} catch (final Exception e) {
			return false;
		}
	}

	private boolean isTestFile(final URI uri) {
		return fileExtensionRegistry.getFileExtensions(FileExtensionType.TESTABLE_FILE_EXTENSION)
				.contains(uri.fileExtension());
	}

	private boolean isTestModule(final ResourceSet resourceSet, final IResourceDescription module) {
		if (null == module) {
			return false;
		}
		final Iterable<IEObjectDescription> classes = module.getExportedObjectsByType(T_CLASS);
		return stream(classes).anyMatch(desc -> hasTestMethods(resourceSet, desc));
	}

	private static boolean isAbstractClass(final IEObjectDescription objDesc) {
		boolean isAbstract = N4JSResourceDescriptionStrategy.getAbstract(objDesc);
		return isAbstract;
	}

	private static boolean isExportedTestClass(final IEObjectDescription objDesc) {
		boolean isTestClass = N4JSResourceDescriptionStrategy.getTestClass(objDesc);
		boolean isExported = N4JSResourceDescriptionStrategy.getExported(objDesc);
		return isTestClass && isExported;
	}

	private boolean hasTestMethods(final ResourceSet resSet, final IEObjectDescription objDesc) {

		// If class is abstract for sure it cannot be run as a test.
		if (isAbstractClass(objDesc)) {
			return false;
		}

		// If class has own test methods and exported
		if (isExportedTestClass(objDesc)) {
			return true;
		}

		// Otherwise load class and all its inherited methods from supertypes as well.
		final TClass clazz = loadTClass(resSet, objDesc).orNull();
		// Exported and has at least one test method (including the ancestor types)
		return null != clazz && clazz.isExported() && getAllTestMethodsOfClass(clazz).iterator().hasNext();
	}

	private Optional<TClass> loadTClass(final ResourceSet resSet, final IEObjectDescription objDesc) {
		if (T_CLASS.isSuperTypeOf(objDesc.getEClass())) {
			final EObject objectOrProxy = objDesc.getEObjectOrProxy();
			final EObject object = objectOrProxy.eIsProxy() ? EcoreUtil.resolve(objectOrProxy, resSet) : objectOrProxy;
			if (!object.eIsProxy()) {
				return fromNullable((TClass) object);
			}
		}
		return absent();
	}

	private static boolean isTestMethod(final TMember member) {
		return member instanceof TMethod && TEST_METHOD.hasAnnotation(member);
	}

	private static final <T> Stream<T> stream(final Iterable<T> in) {
		return StreamSupport.stream(in.spliterator(), false);
	}

}
