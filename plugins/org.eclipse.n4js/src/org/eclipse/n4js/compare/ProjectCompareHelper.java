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
package org.eclipse.n4js.compare;

import static org.eclipse.n4js.AnnotationDefinition.PROVIDES_DEFAULT_IMPLEMENTATION;
import static org.eclipse.n4js.AnnotationDefinition.PROVIDES_INITIALZER;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.internal.MultiCleartriggerCache;
import org.eclipse.n4js.internal.MultiCleartriggerCache.CleartriggerSupplier;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.AccessModifiers;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.ITypeReplacementProvider;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.FindArtifactHelper;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

/**
 * Helper for comparing API and implementation projects to check if the implementation complies to the API. For more
 * info see main method {@link #createComparison(boolean, List)}.
 */
public class ProjectCompareHelper {

	@Inject
	private IN4JSCore n4jsCore;
	@Inject
	private FindArtifactHelper artifactHelper;
	@Inject
	private ContainerTypesHelper containerTypesHelper;
	@Inject
	private N4JSTypeSystem typeSystem;
	@Inject
	private MultiCleartriggerCache cache;

	private static Logger logger = Logger.getLogger(ProjectCompareHelper.class);

	/**
	 * First step in comparing API and implementation projects. This method will collect all API projects, the types
	 * declared there and their members and relate them to the corresponding types and members in all available
	 * implementation projects.
	 * <p>
	 * The second step of comparison is to compare an individual type/member to a corresponding type/member of a
	 * particular implementation via method {@link #compareApiImpl(ProjectComparisonEntry, int)}.
	 * <p>
	 * Usually, this second step is performed lazily when method <code>#compareApiImpl()</code> is invoked; however, by
	 * setting <code>fullCompare</code> to <code>true</code>, this is performed up-front here causing all results to be
	 * stored in a cache in the entries, and then subsequent calls to <code>#compareApiImpl()</code> will just read from
	 * that cache.
	 * <p>
	 * Returns <code>null</code> in case of error and adds human-readable error messages to the given list.
	 */
	public ProjectComparison createComparison(boolean fullCompare, List<String> addErrorMessagesHere) {
		final ResourceSet resourceSet = n4jsCore.createResourceSet(Optional.absent());
		final IResourceDescriptions index = n4jsCore.getXtextIndex(resourceSet);

		final ApiImplMapping mapping = ApiImplMapping.of(n4jsCore);
		if (mapping.hasErrors()) {
			if (addErrorMessagesHere != null)
				addErrorMessagesHere.addAll(mapping.getErrorMessages());
			return null;
		}

		final List<N4JSProjectName> allImplIds = mapping.getAllImplIds();
		final int implCount = allImplIds.size();

		final ProjectComparison root = new ProjectComparison(allImplIds.toArray(new N4JSProjectName[implCount]));

		for (N4JSProjectName currApiId : mapping.getApiIds()) {
			final IN4JSProject currApi = mapping.getApi(currApiId);
			final IN4JSProject[] currImpls = new IN4JSProject[implCount];
			for (int idx = 0; idx < implCount; idx++) {
				final N4JSProjectName currImplId = allImplIds.get(idx);
				currImpls[idx] = mapping.getImpl(currApiId, currImplId);
			}
			createEntries(root, currApi, currImpls, resourceSet, index);
		}

		// compare all implementations in all entries and store in cache (if requested)
		if (fullCompare) {
			root.getAllEntries().forEach(currE -> {
				for (int implIdx = 0; implIdx < implCount; implIdx++)
					compareApiImpl(currE, implIdx);
			});
		}

		return root;
	}

	// may be made public later
	private ProjectComparisonEntry createEntries(
			ProjectComparison root,
			IN4JSProject api, IN4JSProject[] impls,
			ResourceSet resourceSet, IResourceDescriptions index) {

		final ProjectComparisonEntry entry = new ProjectComparisonEntry(root, api, impls);

		for (IN4JSSourceContainer currSrcConti : api.getSourceContainers()) {
			for (URI uri : currSrcConti) {
				final String uriStr = uri.toString();
				if (uriStr.endsWith("." + N4JSGlobals.N4JS_FILE_EXTENSION)
						|| uriStr.endsWith("." + N4JSGlobals.N4JSD_FILE_EXTENSION)) {
					final IResourceDescription resDesc = index.getResourceDescription(uri);
					final TModule moduleApi = getModuleFrom(resourceSet, resDesc);
					if (moduleApi != null) {
						final TModule[] moduleImpls = new TModule[impls.length];
						for (int idx = 0; idx < impls.length; idx++) {
							final IN4JSProject projectImpl = impls[idx];
							if (projectImpl != null)
								moduleImpls[idx] = findImplementation(moduleApi, projectImpl, resourceSet, index);
							else
								moduleImpls[idx] = null;
						}
						createEntries(entry, -1, moduleApi, moduleImpls, false);
					}
				}
			}
		}

		return entry;
	}

	/**
	 * Retrieves the implementation ID for an implementor of some API
	 *
	 * @param apiImplModule
	 *            the implementor
	 * @return implementation ID if applicable - unset if the parameter is not implementing an API or is itself from an
	 *         API definition.
	 */
	public Optional<N4JSProjectName> getImplementationID(TModule apiImplModule) {

		Optional<? extends IN4JSProject> opt = n4jsCore.findProject(apiImplModule.eResource().getURI());
		IN4JSProject implProject = opt.get();
		return implProject.getImplementationId();
	}

	/**
	 * Compare API-implementations against definition.
	 *
	 * @param apiImplModule
	 *            module to check
	 * @return data-structure holding the compare-result.
	 */
	public ProjectComparisonEntry compareModules(TModule apiImplModule) {
		Optional<N4JSProjectName> implID = getImplementationID(apiImplModule);
		if (implID.isPresent()) {
			return compareModules(apiImplModule, implID.get());
		}
		return null;
	}

	/**
	 * Get the comparison for a module in a specific implementation specified by it's ID.
	 *
	 * @param module
	 *            can be either Implementation or API
	 * @param implementationID
	 *            the current context to compare with
	 * @return a comparison between API and implementation with implementationID, or <code>null</code> if no project
	 *         could be found, the module is not part of an API/IMPL ...
	 */
	public ProjectComparisonEntry compareModules(TModule module, N4JSProjectName implementationID) {
		return compareModules(module, implementationID, false);
	}

	static private class ApiImplMappingSupplier implements CleartriggerSupplier<ApiImplMapping> {
		final private IN4JSCore n4jsCore;

		private ApiImplMappingSupplier(IN4JSCore n4jsCore) {
			this.n4jsCore = n4jsCore;
		}

		@Override
		public ApiImplMapping get() {
			return ApiImplMapping.of(n4jsCore);
		}

		@Override
		public Collection<URI> getCleartriggers() {
			Collection<URI> allPckjsons = new LinkedList<>();
			for (IN4JSProject prj : n4jsCore.findAllProjects()) {
				URI pckjsonUri = prj.getLocation().toURI();
				if (pckjsonUri != null) {
					allPckjsons.add(pckjsonUri);
				}
			}
			return allPckjsons;
		}
	}

	/**
	 * Get the comparison for a module in a specific implementation specified by it's ID.
	 *
	 * @param module
	 *            can be either Implementation or API
	 * @param implementationID
	 *            the current context to compare with
	 *
	 * @param includePolyfills
	 *            {@code true} if polyfills should be considered as the part of the API and the implementation.
	 *            Otherwise {@code false}.
	 * @return a comparison between API and implementation with implementationID, or <code>null</code> if no project
	 *         could be found, the module is not part of an API/IMPL ...
	 */
	public ProjectComparisonEntry compareModules(TModule module, N4JSProjectName implementationID,
			final boolean includePolyfills) {

		Optional<? extends IN4JSProject> opt = n4jsCore.findProject(module.eResource().getURI());

		if (!opt.isPresent()) {
			return null;
		}

		IN4JSProject project = opt.get();

		IN4JSProject implProject = null;
		IN4JSProject apiProject = null;
		TModule apiModule = null;
		TModule apiImplModule = null;

		if (!project.getImplementationId().isPresent()) {
			// this is NOT an implementation project, so assume we have the api
			// need to load the correct implementation. Since there might be multiple implementors

			Supplier<ApiImplMapping> supplier = new ApiImplMappingSupplier(n4jsCore);
			ApiImplMapping mapping = cache.get(supplier, MultiCleartriggerCache.CACHE_KEY_API_IMPL_MAPPING);

			implProject = mapping.getImpl(project.getProjectName(), implementationID);
			if (implProject == null) {
				return null; // no implementation found.
			}
			apiProject = project;
			apiModule = module;
			URI impUri = artifactHelper.findArtifact(implProject, apiModule.getQualifiedName(),
					Optional.of(N4JSGlobals.N4JS_FILE_EXTENSION));
			if (impUri != null) {
				IResourceDescriptions xtextIndex = n4jsCore.getXtextIndex(module.eResource()
						.getResourceSet());
				IResourceDescription resourceDescription = xtextIndex.getResourceDescription(impUri);
				if (resourceDescription != null) {
					apiImplModule = n4jsCore.loadModuleFromIndex(module.eResource().getResourceSet(),
							resourceDescription,
							false);
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("...ouch nothing in index for " + impUri);
					}
					Resource implResource = module.eResource().getResourceSet().getResource(impUri, true);
					apiImplModule = (TModule) implResource.getContents().get(1);
					// apiImplModule = (TModule)
					// module.eResource().getResourceSet().createResource(impUri).getContents()
					// .get(1);
					if (logger.isDebugEnabled()) {
						logger.debug("TModule loaded from Resource: " + apiImplModule);
					}
				}
			} else {
				// No implementation present.
				if (logger.isDebugEnabled()) {
					logger.debug("No implementation given. For " + apiModule.getQualifiedName());
				}
			}

		} else {
			// it is an implementations project
			// check that the implementation ID matches.
			if (implementationID.equals(project.getImplementationId().get())) {
				implProject = project;
				apiImplModule = module;

				ImmutableList<? extends IN4JSProject> apiProjects = implProject.getImplementedProjects();
				// should only find one relevant API-project:

				labelA: for (IN4JSProject ap : apiProjects) {
					URI apiURI = artifactHelper.findArtifact(ap, apiImplModule.getQualifiedName(),
							Optional.of(N4JSGlobals.N4JSD_FILE_EXTENSION));
					if (apiURI != null) {
						IResourceDescriptions xtextIndex = n4jsCore.getXtextIndex(apiImplModule.eResource()
								.getResourceSet());
						IResourceDescription resourceDescription = xtextIndex.getResourceDescription(apiURI);
						if (resourceDescription != null) {
							apiModule = n4jsCore.loadModuleFromIndex(apiImplModule.eResource().getResourceSet(),
									resourceDescription, false);
							if (apiModule != null)
								break labelA;
						}
					}
				}

			} else {
				return null;
			}
		}

		if (apiModule != null) {
			return compareModules(apiProject, apiModule, implProject, apiImplModule, includePolyfills);
		} else {
			// no apiModule --> this is not an implementation of API.
			return null;
		}
	}

	/**
	 * Specific Comparison between two modules.
	 *
	 * @param apiProject
	 *            API project
	 * @param api
	 *            module containing the definition
	 * @param implProject
	 *            API-impl project
	 * @param impl
	 *            implementation to
	 * @param includePolyfills
	 *            {@code true} if polyfills should be considered as the part of the API and the implementation.
	 *            Otherwise {@code false}.
	 * @return data containing the comparison (implementation is in slot 0)
	 */
	public ProjectComparisonEntry compareModules(IN4JSProject apiProject, TModule api, IN4JSProject implProject,
			TModule impl, final boolean includePolyfills) {
		ProjectComparison projectComparison = new ProjectComparison(new N4JSProjectName[] {
				implProject.getImplementationId().orNull()
		});
		ProjectComparisonEntry dummyParent = new ProjectComparisonEntry(projectComparison, apiProject, implProject);
		ProjectComparisonEntry ret = createEntries(dummyParent, -1, api, new EObject[] { impl },
				includePolyfills);
		return ret;
	}

	// may be made public later
	private ProjectComparisonEntry createEntries(ProjectComparisonEntry parent, int index, EObject api,
			EObject[] impls, final boolean includePolyfills) {
		final ProjectComparisonEntry entry = new ProjectComparisonEntry(parent, index, api, impls);

		final EObject[] childrenOfApi = computeChildren(api, false, false, includePolyfills);
		final EObject[][] childrenOfImpls = computeChildren(impls, false, true, includePolyfills);

		// STEP 1: create entries for children of the API
		final Set<EObject> doneImpl = new HashSet<>();
		for (EObject childApi : childrenOfApi) {
			final EObject[] implsForChildApi = computeMatches(childApi, childrenOfImpls, doneImpl);
			// special: for missing members on implementation side, try to find matches among inherited members
			if (childApi instanceof TMember) {
				for (int idxImpl = 0; idxImpl < impls.length; idxImpl++) {
					if (implsForChildApi[idxImpl] == null) {
						final EObject currImpl = impls[idxImpl];
						if (currImpl instanceof ContainerType<?>) {
							implsForChildApi[idxImpl] = computeMatch(childApi,
									computeChildren(currImpl, true, true, includePolyfills),
									doneImpl);
						}
					}
				}
			}
			createEntries(entry, -1, childApi, implsForChildApi, includePolyfills);
		}

		// STEP 2: create missing entries for children of implementations that are not represented in the API
		for (int idxImpl = 0; idxImpl < impls.length; idxImpl++) {
			final EObject[] childrenOfCurrImpl = childrenOfImpls[idxImpl];
			for (int idxChild = 0; idxChild < childrenOfCurrImpl.length; idxChild++) {
				final EObject childImpl = childrenOfCurrImpl[idxChild];
				if (!doneImpl.contains(childImpl)) {
					// collect matches in other implementations
					final EObject[] implsForChildImpl = computeMatches(childImpl, childrenOfImpls, doneImpl);
					// look for neighbor EObject in childrenOfCurrImpl
					final int idxSib = idxChild - 1;
					final EObject siblingImpl = idxSib >= 0 ? childrenOfCurrImpl[idxSib] : null;
					// look up neighbor Entry (i.e. entry with elementImpl == siblingImpl)
					final ProjectComparisonEntry siblingEntry;
					if (siblingImpl != null)
						siblingEntry = entry.getChildForElementImpl(siblingImpl);
					else
						siblingEntry = null;
					// add after neighbor OR at beginning if no neighbor found
					final int insertIdx = siblingEntry != null ? entry.getChildIndex(siblingEntry) + 1 : 0;
					createEntries(entry, insertIdx, null, implsForChildImpl, includePolyfills); // note: no API
																								// element here!
				}
			}
		}

		return entry;
	}

	private TModule findImplementation(TModule moduleApi, IN4JSProject projectImpl,
			ResourceSet resourceSet, IResourceDescriptions index) {
		final String fqnStr = moduleApi.getQualifiedName();
		final URI uri = artifactHelper.findArtifact(projectImpl, fqnStr, Optional.of(N4JSGlobals.N4JS_FILE_EXTENSION));
		if (uri == null)
			return null;
		final IResourceDescription resDesc = index.getResourceDescription(uri);
		if (resDesc == null)
			return null;
		final TModule moduleImpl = getModuleFrom(resourceSet, resDesc);
		return moduleImpl;
	}

	private EObject[][] computeChildren(EObject[] elems, boolean includeInheritedMembers, boolean includeNonPublic,
			boolean includePolyfills) {
		final int len = elems.length;
		final EObject[][] result = new EObject[elems.length][];
		for (int idx = 0; idx < len; idx++)
			result[idx] = computeChildren(elems[idx], includeInheritedMembers, includeNonPublic,
					includePolyfills);
		return result;
	}

	private EObject[] computeChildren(EObject elem, boolean includeInheritedMembers, boolean includeNonPublic,
			final boolean includePolyfills) {
		if (elem instanceof TModule) {
			return ((TModule) elem).getTopLevelTypes().stream()
					.filter(e -> includeNonPublic || isPublicOrPublicInternal(e))
					.toArray(n -> new EObject[n]);
		} else if (elem instanceof ContainerType<?>) {
			final ContainerType<?> elemCasted = (ContainerType<?>) elem;
			return containerTypesHelper.fromContext(elem)
					.allMembers(elemCasted, false, includePolyfills, includeInheritedMembers).stream()
					.filter(e -> includeNonPublic || isPublicOrPublicInternal(e))
					.toArray(n -> new EObject[n]);
		} else if (elem instanceof TEnum) {
			final EList<TEnumLiteral> literals = ((TEnum) elem).getLiterals();
			return literals.toArray(new EObject[literals.size()]);
		}
		return new EObject[0];
	}

	private EObject[] computeMatches(EObject elemToMatch, EObject[][] candidates, Set<EObject> ignore) {
		final EObject[] result = new EObject[candidates.length];
		for (int idx = 0; idx < candidates.length; idx++) {
			final EObject match = computeMatch(elemToMatch, candidates[idx], ignore);
			result[idx] = match;
			if (match != null)
				ignore.add(match); // do not revisit 'match'
		}
		return result;
	}

	private EObject computeMatch(EObject elemToMatch, EObject[] candidates, Set<EObject> ignore) {
		final String elemName = computeName(elemToMatch);
		if (elemName == null || elemName.trim().isEmpty())
			return null; // elem not identifiable -> no match
		final int elemKind = accessorKind(elemToMatch);
		return Stream.of(candidates)
				.filter(obj -> !ignore.contains(obj)
						&& elemName.equals(computeName(obj))
						&& elemKind == accessorKind(obj))
				.findFirst().orElse(null);
	}

	private static final int accessorKind(EObject obj) {
		if (obj instanceof TSetter)
			return 1;
		if (obj instanceof TGetter)
			return 2;
		return 0;
	}

	private static final String computeName(EObject elem) {
		if (elem instanceof IdentifiableElement)
			return ((IdentifiableElement) elem).getName();
		// if(elem instanceof NamedElement)
		// return ((NamedElement) elem).getName();
		return null;
	}

	private TModule getModuleFrom(ResourceSet resourceSet, IResourceDescription resDesc) {
		if (resDesc != null) {
			return n4jsCore.loadModuleFromIndex(resourceSet, resDesc, false);
		}
		return null;
	}

	/**
	 * Second step in comparing API and implementation projects: comparing individual types/members. Compares the API
	 * element and implementation element of the implementation with index <code>implIdx</code> from the given project
	 * comparison entry.
	 * <p>
	 * The compare result returned by this method is also cached in the given entry and subsequent calls to this method
	 * will read from the cache. To clear this cache, use methods
	 * {@link ProjectComparison#clearAllCachedCompareResults()} and
	 * {@link ProjectComparisonEntry#clearCachedCompareResults()}.
	 */
	public ProjectCompareResult compareApiImpl(ProjectComparisonEntry entry, int implIdx) {
		final ProjectCompareResult cached = entry.getCachedCompareResult(implIdx);
		if (cached != null)
			return cached;
		final ProjectCompareResult result = internalCompareApiImpl(entry, implIdx);
		entry.storeCachedCompareResult(implIdx, result);
		return result;
	}

	private ProjectCompareResult internalCompareApiImpl(ProjectComparisonEntry entry, int implIdx) {
		if (!entry.isElementEntry())
			return ProjectCompareResult.equal(); // not an entry for an EObject element -> never report differences
		final int implCount = entry.getImplCount();
		if (implIdx < 0 || implIdx >= implCount)
			return ProjectCompareResult.equal(); // implementation index out of range -> never report differences

		// compare implementation to API
		final EObject api = entry.getElementAPI();
		final EObject impl = entry.getElementImpl()[implIdx];

		// special case: no API
		if (api == null) {
			if (impl != null)
				return ProjectCompareResult.compliant();
			else
				return ProjectCompareResult.equal();
		}

		// special case: no impl
		if (impl == null) {
			// note: we know api!=null because of above check
			return ProjectCompareResult.error("missing implementation");
		}

		// accessibility-based compare:
		if (api instanceof TMember && impl instanceof TMember) { // order important: check for member before type!
			if (AccessModifiers.less((TMember) impl, (TMember) api))
				return ProjectCompareResult.error("reduced visibility");
		} else if (api instanceof Type && impl instanceof Type) {
			final MemberAccessModifier apiAcc = AccessModifiers.toMemberModifier((Type) api);
			final MemberAccessModifier implAcc = AccessModifiers.toMemberModifier((Type) impl);
			if (AccessModifiers.less(implAcc, apiAcc))
				return ProjectCompareResult.error("reduced visibility");
		}

		ImplToApiReplacementProvider typeReplacementProvider = new ImplToApiReplacementProvider(entry.getRoot());

		// subtype-based compare:
		if (api instanceof TMember && impl instanceof TMember) {
			final TMember apiMember = (TMember) api;
			final TMember implMember = (TMember) impl;

			if (apiMember instanceof TField) {
				boolean bAPIProvidesInitializer = PROVIDES_INITIALZER.hasAnnotation(apiMember);
				if (bAPIProvidesInitializer && !hasInitializer(impl)) {
					if (bAPIProvidesInitializer) {
						return ProjectCompareResult
								.error("no initializer in implementation but @" + PROVIDES_INITIALZER.name + " in API");
					} else {
						return ProjectCompareResult
								.error("initializer in implementation but no @" + PROVIDES_INITIALZER.name
										+ " in API)");
					}
				}
			} else { // Method or accessor
				boolean bAPIProvidesDefImpl = PROVIDES_DEFAULT_IMPLEMENTATION.hasAnnotation(apiMember);
				if ((bAPIProvidesDefImpl != hasBody(impl))
						&&
						apiMember.eContainer() instanceof TInterface) {
					if (bAPIProvidesDefImpl) {
						return ProjectCompareResult
								.error("no body in implementation but @" + PROVIDES_DEFAULT_IMPLEMENTATION.name
										+ " in API");
					} else {
						return ProjectCompareResult
								.error("body in implementation but no @" + PROVIDES_DEFAULT_IMPLEMENTATION.name
										+ " in API");
					}
				}
			}

			final TypeRef context = TypeUtils.createTypeRef((Type) api.eContainer());
			final TypeRef typeApi = typeSystem.tau(apiMember, context);
			final TypeRef typeImpl = typeSystem.tau(implMember, context);

			final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(api);
			RuleEnvironmentExtensions.setTypeReplacement(G, typeReplacementProvider);
			final Result implSubtypeApi = typeSystem.subtype(G, typeImpl, typeApi);
			final Result apiSubtypeImpl = typeSystem.subtype(G, typeApi, typeImpl);
			final boolean isImplSubtypeApi = !implSubtypeApi.isFailure();
			final boolean isApiSubtypeImpl = !apiSubtypeImpl.isFailure();
			final boolean isEqualType = isImplSubtypeApi && isApiSubtypeImpl;

			if (!isEqualType) {
				if (isImplSubtypeApi)
					return ProjectCompareResult.compliant(); // not equal but at least compliant
				else {
					final String msg = implSubtypeApi.getFailureMessage();
					return ProjectCompareResult.error(msg); // not even compliant
				}
			}

			if (isSpecialCaseOfHiddenMethodDiff(api, impl)) {
				return ProjectCompareResult.compliant(); // not equal but at least compliant
			}

			return ProjectCompareResult.equal(); // all fine
		}

		// classifier compare
		if (api instanceof TClassifier && impl instanceof TClassifier) {
			TClassifier apiClassifier = (TClassifier) api;
			TClassifier implClassifier = (TClassifier) impl;

			EList<TypeVariable> apiTypeVars = apiClassifier.getTypeVars();
			EList<TypeVariable> implTypeVars = implClassifier.getTypeVars();

			// check for number of type variables
			if (apiTypeVars.size() != implTypeVars.size()) {
				return ProjectCompareResult.error("the number of type variables doesn't match");
			}

			final RuleEnvironment ruleEnvironment = RuleEnvironmentExtensions.newRuleEnvironment(api);
			RuleEnvironmentExtensions.setTypeReplacement(ruleEnvironment, typeReplacementProvider);

			// check for upper bound compatibility
			for (int i = 0; i < apiTypeVars.size(); i++) {
				TypeVariable apiTypeVar = apiTypeVars.get(i);
				TypeVariable implTypeVar = implTypeVars.get(i);

				TypeRef apiDeclaredUpperBound = apiTypeVar.getDeclaredUpperBound();
				TypeRef implDeclaredUpperBound = implTypeVar.getDeclaredUpperBound();

				if ((apiDeclaredUpperBound != null) != (implDeclaredUpperBound != null)
						|| (apiDeclaredUpperBound != null
								&& implDeclaredUpperBound != null
								&& !typeSystem.equaltypeSucceeded(ruleEnvironment, apiDeclaredUpperBound,
										implDeclaredUpperBound))) {
					return ProjectCompareResult.error(
							String.format(
									"the upper bound of type variable %s isn't compatible with the API",
									implTypeVar.getName()));
				}
			}

			return ProjectCompareResult.equal();

		}

		// text-based compare:
		final String textApi = entry.getTextAPI(); // always compare with API
		final String textImpl = entry.getTextImpl(implIdx);
		final boolean isEqual = textApi != null ? textApi.equals(textImpl) : textImpl == null;
		if (!isEqual)
			return ProjectCompareResult.error(textImpl + " is not equal to " + textApi);

		return ProjectCompareResult.equal();
	}

	/**
	 * The logic in {@link #internalCompareApiImpl(ProjectComparisonEntry, int)}, above, assumes that if api<:impl and
	 * impl<:api, then both are equal. However, in case of methods there is a special case where this is not true:
	 *
	 * <pre>
	 * api = method(A p1, B? p2) {}
	 * impl = method(A p1) {}
	 * </pre>
	 *
	 * Here we have api<:impl and impl<:api, but still we must show a diff. This method returns true for these cases.
	 */
	private static boolean isSpecialCaseOfHiddenMethodDiff(EObject api, EObject impl) {
		if (api instanceof TMethod && impl instanceof TMethod) {
			final List<TFormalParameter> apiFpars = ((TMethod) api).getFpars();
			final List<TFormalParameter> implFpars = ((TMethod) impl).getFpars();
			final int apiSize = apiFpars.size();
			final int implSize = implFpars.size();
			// must have different number of fpars
			if (implSize != apiSize) {
				final int start = Math.min(apiSize, implSize);
				final int end = Math.max(apiSize, implSize);
				final List<TFormalParameter> fpars = implSize < apiSize ? apiFpars : implFpars;
				// all surplus fpars must be optional
				for (int i = start; i < end; i++) {
					if (!fpars.get(i).isOptional())
						return false;
				}
				return true;
			}
		}
		return false;
	}

	private static boolean isPublicOrPublicInternal(EObject elem) {
		if (elem instanceof TMemberWithAccessModifier) { // order important: check for member before type!
			final MemberAccessModifier acc = ((TMemberWithAccessModifier) elem).getMemberAccessModifier();
			return acc == MemberAccessModifier.PUBLIC || acc == MemberAccessModifier.PUBLIC_INTERNAL;
		} else if (elem instanceof Type) {
			final TypeAccessModifier acc = ((Type) elem).getTypeAccessModifier();
			return acc == TypeAccessModifier.PUBLIC || acc == TypeAccessModifier.PUBLIC_INTERNAL;
		}
		return true; // n.b.: for other elements we return true
	}

	private static final boolean hasBody(EObject element) {
		return ((element instanceof TMethod || element instanceof FieldAccessor)
				&& !((TMemberWithAccessModifier) element).isHasNoBody());
	}

	private boolean hasInitializer(EObject element) {
		return (element instanceof TField) && ((TField) element).isHasExpression();
	}

	private static final class ImplToApiReplacementProvider implements ITypeReplacementProvider {

		private final ProjectComparison comparison;

		public ImplToApiReplacementProvider(ProjectComparison comparison) {
			this.comparison = comparison;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T extends Type> T getReplacement(T type) {
			if (type instanceof TypeVariable) {
				// special case: type variables
				final EObject parent = type.eContainer();
				if (parent instanceof ContainerType<?>) {
					final ContainerType<?> parentCasted = (ContainerType<?>) parent;
					final ContainerType<?> parentReplacement = getReplacement(parentCasted);
					if (parentReplacement != null) {
						final int idxOfTypeInParent = parentCasted.getTypeVars().indexOf(type);
						if (idxOfTypeInParent >= 0 && idxOfTypeInParent < parentReplacement.getTypeVars().size())
							return (T) parentReplacement.getTypeVars().get(idxOfTypeInParent);
					}
				}
				return null; // no replacement found
			}
			// find API element for implementation element 'type'
			final ProjectComparisonEntry entry = comparison.getEntryForObject(type);
			return entry != null ? (T) entry.getElementAPI() : null;
		}
	}
}
