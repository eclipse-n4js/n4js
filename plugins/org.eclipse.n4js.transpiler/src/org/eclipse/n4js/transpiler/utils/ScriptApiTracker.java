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
package org.eclipse.n4js.transpiler.utils;

import static java.util.Collections.emptyList;
import static org.eclipse.n4js.AnnotationDefinition.PROVIDES_DEFAULT_IMPLEMENTATION;
import static org.eclipse.n4js.AnnotationDefinition.PROVIDES_INITIALZER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.compare.ProjectCompareHelper;
import org.eclipse.n4js.compare.ProjectComparisonEntry;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.impl.TFieldImpl;
import org.eclipse.n4js.ts.types.impl.TGetterImpl;
import org.eclipse.n4js.ts.types.impl.TMethodImpl;
import org.eclipse.n4js.ts.types.impl.TSetterImpl;
import org.eclipse.n4js.ts.types.util.AccessorTuple;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.LinkedHashMultimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Class for storing Api-related information about missing implementations. For a Module (Script) the API-compare will
 * be executed and temporarily stored here.
 */
@Singleton
public class ScriptApiTracker {

	/**
	 * Method representation with no AST-connection. Used to distinguish missing Api-Methods.
	 */
	public static class VirtualApiTMethod extends TMethodImpl {
		private final TMethod apiRef;

		/** access the original API-definition. */
		public TMethod getApiRef() {
			return apiRef;
		}

		/**
		 *
		 */
		public VirtualApiTMethod(String name, TMethod apiRef) {
			super();
			this.name = name;
			this.apiRef = apiRef;
			this.declaredStatic = apiRef.isStatic();
		}
	}

	/** Type wrapping missing field implementations from API. */
	public static class VirtualApiTField extends TFieldImpl {
		private final TField apiRef;

		/** access the original api-definition. */
		public TField getApiRef() {
			return apiRef;
		}

		/**  */
		public VirtualApiTField(String name, TField apiRef) {
			this.name = name;
			this.apiRef = apiRef;
			this.declaredStatic = apiRef.isStatic();
		}
	}

	/** Type wrapping missing field implementations from API in an special AccessorTupel instance. */
	public static class VirtualApiAccessorTuple extends AccessorTuple {
		/** */
		public VirtualApiAccessorTuple(String name, boolean isStatic) {
			super(name, isStatic);
		}
	}

	/** Type wrapping missing field implementations from API in an special AccessorTupel instance. */
	public static class VirtualApiMissingFieldAccessorTuple extends AccessorTuple {
		private final AccessorTuple apiRef;

		/** access the original api-definition. */
		public AccessorTuple getApiRef() {
			return apiRef;
		}

		/**  */
		public VirtualApiMissingFieldAccessorTuple(AccessorTuple apiRef) {
			super(apiRef.getName(), apiRef.isStatic());
			this.apiRef = apiRef;
		}
	}

	/** Type wrapping a TGetter implementation */
	public static class VirtualApiTGetter extends TGetterImpl {
		private final TGetter apiGetter;

		/** */
		public VirtualApiTGetter(String name, TGetter apiGetter) {
			this.name = name;
			this.apiGetter = apiGetter;
			this.declaredStatic = apiGetter.isStatic();
		}

		/** */
		public TGetter getApiRef() {
			return apiGetter;
		}
	}

	/** Type wrapping a TSetter implementation */
	public static class VirtualApiTSetter extends TSetterImpl {
		private final TSetter apiSetter;

		/** */
		public VirtualApiTSetter(String name, TSetter apiSetter) {
			this.name = name;
			this.apiSetter = apiSetter;
			this.declaredStatic = apiSetter.isStatic();
		}

		/** */
		public TSetter getApiRef() {
			return apiSetter;
		}
	}

	/**
	 * Adapter holding comparison information about API implementations.
	 */
	public class ProjectComparisonAdapter extends AdapterImpl {

		/** values can be null --> no api compare created. */
		private final HashMap<String, ProjectComparisonEntry> hmModuleToPCE;
		private final TModule tModule;
		/**
		 * concrete implementation to compare against, if null this adapter doesn't deliver any compare-results
		 */
		private final N4JSProjectName implementationID;

		/**
		 * This CompareAdapter will be initialized with an TModule from an concrete API-implementation. It then reads
		 * out the implementation-ID an does all future works in the realm of this implementation ID.
		 *
		 * If no implementation-ID can be obtained this adapter will always return null as a result.
		 *
		 * @param tModule
		 *            implementation module, reference to hold.
		 */
		public ProjectComparisonAdapter(TModule tModule) {
			this.tModule = tModule;
			hmModuleToPCE = new HashMap<>();
			implementationID = projectCompareHelper.getImplementationID(tModule).orNull();
			getEntryFor(tModule); // trigger early.
		}

		/**
		 * @param module
		 *            which script is compared?
		 * @return return the compare result or null of nothing was found.
		 */
		public ProjectComparisonEntry getEntryFor(TModule module) {
			String qname = module.getQualifiedName();

			if (!hmModuleToPCE.containsKey(qname)) {
				// lazy initialization:
				ProjectComparisonEntry comparator = projectCompareHelper.compareModules(module, implementationID, true);
				hmModuleToPCE.put(qname, comparator);
			}
			ProjectComparisonEntry ret = hmModuleToPCE.get(qname);
			return ret;
		}

		/**
		 * @return true if there was a compare Result for the script at all.
		 */
		public boolean hasOriginalCompareResult() {
			return implementationID != null && getEntryFor(tModule) != null;
		}

	}

	/** Helper DS to collect getter and setter definitions. */
	public static class GetSetGroup {
		final boolean staticCases;
		final String name;
		VirtualApiTGetter getter;
		VirtualApiTSetter setter;
		boolean getterIsInAST;
		boolean setterIsInAST;

		/** */
		public GetSetGroup(String name, boolean staticCase) {
			this.name = name;
			this.staticCases = staticCase;
		}

		boolean hasGetter() {
			return getter != null;
		}

		boolean hasSetter() {
			return setter != null;
		}
	}

	// ///////////////////////////////////////////////////////////////////////////
	// ///// END OF INTERNAL CLASS DEFINITION ////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	private static Logger logger = Logger.getLogger(ScriptApiTracker.class);

	@Inject
	ProjectCompareHelper projectCompareHelper;

	/** Check if API-compare was already performed. If not then calls {@link #doApiCompare(Script)} */
	public void initApiCompare(Script script) {
		if (!firstProjectComparisonAdapter(script.eResource()).isPresent()) {
			doApiCompare(script);
		}
	}

	/**
	 * Calculates comparison between ApiImpl an API and installs the comparison-result as an adapter on the resource of
	 * the script. Users should be sure to remove this adapter by calling {@link #cleanApiCompare(Script)}
	 *
	 * @param script
	 *            script to analyze
	 */
	public void doApiCompare(Script script) {

		ProjectComparisonAdapter projectComparisonAdapter = new ProjectComparisonAdapter(script.getModule());

		if (projectComparisonAdapter.hasOriginalCompareResult()) {
			// guard against parallel processing
			if (script.eResource().eAdapters()
					.stream()
					.anyMatch(a -> (a instanceof ProjectComparisonAdapter))) {
				IllegalStateException toThrow = new IllegalStateException(
						"Already have a ProjectComparisonAdapter attached.");
				logger.warn(toThrow.getMessage(), toThrow);
				throw toThrow;
			}
			script.eResource().eAdapters().add(projectComparisonAdapter);
		}
	}

	/**
	 * Remove the adapter-entry for passed-in script from it's containing resource. cleanup after call to
	 * {@link #doApiCompare(Script)}
	 *
	 * @param script
	 *            script contained in a resource, for which the comparison adapter(s) should be removed.
	 */
	public void cleanApiCompare(Script script) {
		script.eResource().eAdapters().removeIf(a -> a instanceof ProjectComparisonAdapter);
	}

	/**
	 * Search first ProjectComparisonAdapter on Resource.
	 */
	public static Optional<ProjectComparisonAdapter> firstProjectComparisonAdapter(Resource eResource) {
		if (eResource == null)
			return Optional.empty();

		return eResource.eAdapters()
				.stream()
				.filter(a -> a instanceof ProjectComparisonAdapter)
				.map(a -> (ProjectComparisonAdapter) a)
				.findFirst();
	}

	/**
	 * @param type
	 *            type to search for APIs.
	 * @param context
	 *            context holding the comparison-adapter
	 * @return List of {@link VirtualApiTMethod}
	 */
	public List<TMethod> computeMissingApiMethods(TClass type, EObject context) {

		Optional<ProjectComparisonAdapter> optAdapt = firstProjectComparisonAdapter(context.eResource());
		if (optAdapt.isPresent()) {
			ProjectComparisonEntry compareEntry = optAdapt.get().getEntryFor(
					EcoreUtil2.getContainerOfType(type, TModule.class));
			ProjectComparisonEntry typeCompare = compareEntry.getChildForElementImpl(type);

			if (typeCompare != null) {
				return typeCompare.allChildren()
						.filter(pce -> pce.getElementAPI() instanceof TMethod)
						.filter(pce -> pce.getElementImpl()[0] == null)
						// only go for empty impl.
						.map(pce -> {
							TMethod apiMethod = (TMethod) pce.getElementAPI();
							TMethod copy = TypeUtils.copyPartial(apiMethod,
									TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT);

							TMethod ret = new VirtualApiTMethod(apiMethod.getName(), copy);
							return ret;
						})
						.collect(Collectors.toList());
			}
		}

		return emptyList();
	}

	/**
	 * Looking for at.ProvidesDefaultImplementation on Methods. Normal method declarations are not taken into account,
	 * since they would not be executed on the interface level.
	 *
	 * Beware: also the inheritance in the original API will be taken into account since compiled client code will link
	 * against that.
	 *
	 * @param type
	 *            type to search for apis.
	 * @return List of {@link VirtualApiTMethod}
	 */
	public List<TMethod> computeMissingApiMethods(TInterface type, EObject context) {

		Optional<ProjectComparisonAdapter> optAdapt = firstProjectComparisonAdapter(context.eResource());
		if (optAdapt.isPresent()) {
			ProjectComparisonAdapter projectComparisonAdapter = optAdapt.get();
			ProjectComparisonEntry compareEntry = projectComparisonAdapter.getEntryFor(
					EcoreUtil2.getContainerOfType(type, TModule.class));
			ProjectComparisonEntry typeCompare = compareEntry.getChildForElementImpl(type);
			// compute real super-types and API-missing super-types then add result for each going through
			// computeMissingApiMethodsPlain.

			if (typeCompare == null) {
				// are we in a completely missing API implementation (super-interfaces not implemented ?)
				typeCompare = compareEntry.getChildForElementAPI(type);
			}
			if (typeCompare == null) {
				if (logger.isDebugEnabled()) {
					logger.debug(
							" want to throw new IllegalstateException() --> comparison for implementation not found type="
									+ type.getTypeAsString()
									+ " in Implementation " + compareEntry.getElementImpl()[0]);
				}
				return emptyList();
			}

			LinkedHashMultimap<TMethod, TInterface> lhmmMehodInterface = LinkedHashMultimap
					.<TMethod, TInterface> create();

			Predicate<ProjectComparisonEntry> filter = (pce -> pce.getElementAPI() instanceof TMethod);
			filter = filter
					.and(pce -> pce.getElementImpl()[0] == null)
					.and(pce -> PROVIDES_DEFAULT_IMPLEMENTATION.hasAnnotation((TMethod) pce.getElementAPI()));

			Function<TInterface, Consumer<? super ProjectComparisonEntry>> actionProvider = pivot -> pce -> {
				TMethod method = ((TMethod) pce.getElementAPI());
				lhmmMehodInterface.put(method, pivot);
			};

			if (!checkInterfaceImplementsInterface(type, typeCompare.getElementAPI())) {
				return emptyList();
			}

			// Call the supertype iterations scaffolding:
			interfaceApiSupertypeWalker(filter, actionProvider, projectComparisonAdapter,
					(TInterface) typeCompare.getElementAPI(), TInterface.class);

			// now that we know about expected mixed-in methods we need to filter out
			// given concrete implementations overriding these mix-ins ... mmmhhh this should be
			// done when calculating the outer concrete super-member computation. --> the result we found here should be
			// filtered
			// out in the caller when processing our results...
			return lhmmMehodInterface
					.keySet()
					.stream()
					.map(m -> new VirtualApiTMethod(m.getName(), TypeUtils.copyPartial(m,
							TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT)))
					.collect(Collectors.toList());

		}
		return emptyList();

	}

	/**
	 * @param implType
	 *            the implType that gets compiled.
	 * @param elementAPI
	 *            the API type from comparison or <code>null</code>
	 * @return true if elemeentAPI is an TInterface
	 */
	private boolean checkInterfaceImplementsInterface(TInterface implType, EObject elementAPI) {

		if (elementAPI instanceof TInterface) {
			return true;
		}

		// Someone is trying to implement a API-class with an interface ?
		if (elementAPI != null && logger.isDebugEnabled())
			logger.debug("implementation uses interface to implement some API that is not an interface Impl="
					+ implType.getTypeAsString() + "  API=" + elementAPI);

		return false;
	}

	/**
	 * This methods takes an API-Element and pulls the projectComparisons along the suptertype chain.
	 *
	 * Does nothing if apiElement is null.
	 *
	 * @param filter
	 *            applied to each encountered projectComparison
	 * @param actionProvider
	 *            function to apply on filtered ProjectComparinsonEntries.
	 * @param apiElement
	 *            concrete API element to start from.
	 */
	public <T extends TClassifier> void interfaceApiSupertypeWalker(Predicate<? super ProjectComparisonEntry> filter,
			Function<T, Consumer<? super ProjectComparisonEntry>> actionProvider,
			ProjectComparisonAdapter projectComparisonAdapter, /* ProjectComparisonEntry compareEntry, */
			T apiElement, Class<T> castGuard) {
		if (apiElement == null) {
			// no apiElemnt means no one can directly call the concrete implementation
			// from projects
			return;
		}

		LinkedHashSet<T> toBeProcessedSuperInterfaces = new LinkedHashSet<>();
		LinkedHashSet<T> processedSuperInterfaces = new LinkedHashSet<>();
		// Yes it is correct ~Not correct~, since we need VirtualMethods for the direct missing parts:: //
		toBeProcessedSuperInterfaces.add(apiElement);
		// includeAdditionsSuperInterfaces(toBeProcessedSuperInterfaces, processedSuperInterfaces, apiInterface);

		while (!toBeProcessedSuperInterfaces.isEmpty()) {

			Iterator<T> iter = toBeProcessedSuperInterfaces.iterator();
			T pivot = iter.next();
			iter.remove();
			// do not process built-in types
			if (TypeUtils.isBuiltIn(pivot)) {
				continue;
			}
			// collect to be processed:
			includeAdditionsSuperInterfaces2(toBeProcessedSuperInterfaces, processedSuperInterfaces, pivot, castGuard);
			// go over methods.
			// Is the superInterface from the same Project ? If not it cannot be an API problem of this
			// implementation.
			TModule superModule = pivot.getContainingModule();

			if (superModule != null) {
				ProjectComparisonEntry useCompareEntry = projectComparisonAdapter.getEntryFor(superModule);
				if (useCompareEntry == null) {
					if (logger.isDebugEnabled()) {
						logger.debug("No comparison found for pivot = " + pivot.getName());
					}
				} else {
					// Is there an API entry at all ? --> If not it was just a normal implementation of some library ?
					ProjectComparisonEntry superInterfaceCompareEntry = useCompareEntry.getChildForElementAPI(pivot);
					if (superInterfaceCompareEntry != null) {
						// Is there a difference between this API and the implementations ?
						if (superInterfaceCompareEntry.hasChildren()) {
							// get the ones which are missing implementations; others are real errors and will not be
							// touched!
							superInterfaceCompareEntry.allChildren()
									.filter(filter)
									.forEach(actionProvider.apply(pivot));
						}
					} // end if superInterfaceCompareEntry != null
				}
			} // end if null-check for module...
			else {
				if (logger.isDebugEnabled()) {
					logger.debug("-#- could not get module for super-classifier: " + pivot.getName() + " of type "
							+ pivot.getTypeAsString() + " providedByRuntime=" + pivot.isProvidedByRuntime());
				}
			}
		}

	}

	/**
	 * Adds all super-interfaces of pivot to acceptor except those listed in exclude
	 *
	 * @param acceptor
	 *            list to add to
	 * @param exclude
	 *            exclusion set
	 * @param pivot
	 *            interface providing super-interfaces
	 */
	private <T extends TClassifier> void includeAdditionsSuperInterfaces2(LinkedHashSet<T> acceptor,
			LinkedHashSet<T> exclude, T pivot, Class<T> castGuard) {
		for (ParameterizedTypeRef superApiClassifier : pivot.getSuperClassifierRefs()) {
			Type superApiDeclaredType = superApiClassifier.getDeclaredType();
			if (castGuard.isAssignableFrom(superApiDeclaredType.getClass())) {
				@SuppressWarnings("unchecked")
				T superInterface = (T) superApiClassifier.getDeclaredType();
				if (!exclude.contains(superInterface)) {
					acceptor.add(superInterface);
				}
			} else {
				// should we handle this or gracefully skip for broken models?
				if (logger.isDebugEnabled()) {
					logger.debug("Oopss ... Casting could not be performed Guard = " + castGuard.getName()
							+ " DeclaredType of superApiClassifier '" + superApiDeclaredType.getClass().getName()
							+ "' ");
				}
			}
		}
	}

	/**
	 * Compares projectName and vendorId
	 */
	boolean isSameProject(TModule m1, TModule m2) {
		return m1 != null && m1.getProjectName().equals(m2.getProjectName())
				&& m1.getVendorID().equals(m2.getVendorID());
	}

	/**
	 * Find last missing methods, which the implType would have if it would follow the inheritance as defined in the API
	 *
	 * @param implType
	 *            Type of implementation project, calculated from AST
	 * @param collector
	 *            member collector for the project.
	 * @param ownedAndMixedInConcreteMember
	 *            already computed for implType
	 * @param missingApiMethods2
	 *            already computed for implType
	 * @return list of virtual Methods
	 */
	public List<VirtualApiTMethod> computeMethodDiff(TClass implType, MemberCollector collector,
			List<TMember> ownedAndMixedInConcreteMember, MemberList<TMethod> missingApiMethods2) {
		Optional<ProjectComparisonAdapter> optAdapt = firstProjectComparisonAdapter(implType.eResource());
		if (optAdapt.isPresent()) {
			ProjectComparisonEntry compareEntry = optAdapt.get().getEntryFor(
					EcoreUtil2.getContainerOfType(implType, TModule.class));
			ProjectComparisonEntry typeCompare = compareEntry.getChildForElementImpl(implType);

			if (typeCompare != null && typeCompare.getElementAPI() != null) {
				TClass apiType = (TClass) typeCompare.getElementAPI();

				MemberList<TMember> implMembers = collector.allMembers(implType, false, true);
				MemberList<TMember> apiMembers = collector.allMembers(apiType, false, true);

				final HashSet<String> methodNamesAlreadyDefined = new HashSet<>();
				Stream.concat(implMembers.stream(),
						Stream.concat(ownedAndMixedInConcreteMember.stream(), missingApiMethods2.stream())).forEach(
								m -> {
									if (m instanceof TMethod) {
										methodNamesAlreadyDefined.add(m.getName());
									}
								});
				return apiMembers.stream()
						.filter(t -> t instanceof TMethod)
						.filter(m -> !methodNamesAlreadyDefined.contains(((TMethod) m).getName()))
						.map(m2 -> {
							TMethod m = (TMethod) m2;
							VirtualApiTMethod vMethod = new VirtualApiTMethod(m.getName(), TypeUtils.copyPartial(m,
									TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT));
							return vMethod;
						})
						.collect(Collectors.toList());

			}
		}
		return emptyList();
	}

	/**
	 * Computes the list of virtual AccessorTuples for missing fields. Dispatches depending on declaration to
	 * {@link #computeMissingApiFields(TClass)} or {@link #computeMissingApiFields(TInterface)}
	 *
	 * @return List of {@link VirtualApiTField}
	 */
	public List<AccessorTuple> computeMissingApiFields(TN4Classifier declaration) {
		if (declaration instanceof TClass) {
			return computeMissingApiFields((TClass) declaration);
		} else if (declaration instanceof TInterface) {
			return computeMissingApiFields((TInterface) declaration);
		}
		throw new IllegalStateException("Called for unhandled type.");
	}

	/**
	 * Computes the list of virtual AccessorTuples for missing fields.
	 *
	 * @return List of {@link VirtualApiTField}
	 */
	private List<AccessorTuple> computeMissingApiFields(TClass declaration) {
		Optional<ProjectComparisonAdapter> optAdapt = firstProjectComparisonAdapter(declaration.eResource());
		if (optAdapt.isPresent()) {
			ProjectComparisonEntry compareEntry = optAdapt.get().getEntryFor(
					EcoreUtil2.getContainerOfType(declaration, TModule.class));
			ProjectComparisonEntry typeCompare = compareEntry.getChildForElementImpl(declaration);

			if (typeCompare != null) {
				return typeCompare.allChildren()
						.filter(pce -> pce.getElementAPI() instanceof TField)
						.filter(pce -> pce.getElementImpl()[0] == null) // only go for empty impl.
						.map(pce -> {
							TField apiField = (TField) pce.getElementAPI();
							VirtualApiMissingFieldAccessorTuple ret = createVirtFieldAccessorTuple(apiField);
							return ret;
						})
						.collect(Collectors.toList());
			}
		}
		return emptyList();
	}

	private VirtualApiMissingFieldAccessorTuple createVirtFieldAccessorTuple(TField apiField) {
		boolean statiC = apiField.isStatic();
		String name = apiField.getName();

		AccessorTuple copy = new AccessorTuple(name, statiC);

		TSetter tset = TypesFactory.eINSTANCE.createTSetter();
		tset.setName(name);
		tset.setDeclaredStatic(statiC);
		copy.setSetter(tset);

		TGetter tget = TypesFactory.eINSTANCE.createTGetter();
		tget.setName(name);
		tget.setDeclaredStatic(statiC);
		copy.setGetter(tget);

		VirtualApiMissingFieldAccessorTuple ret = new VirtualApiMissingFieldAccessorTuple(copy);
		ret.setGetter(new VirtualApiTGetter(name, tget));
		ret.setSetter(new VirtualApiTSetter(name, tset));
		return ret;
	}

	/**
	 * Computes the list of virtual AccessorTuples for missing static fields on Interfaces
	 *
	 * @return List of {@link VirtualApiTField}
	 */
	private List<AccessorTuple> computeMissingApiFields(TInterface declaration) {
		Optional<ProjectComparisonAdapter> optAdapt = firstProjectComparisonAdapter(declaration.eResource());
		if (optAdapt.isPresent()) {
			ProjectComparisonAdapter projectComparisonAdapter = optAdapt.get();
			ProjectComparisonEntry compareEntry = projectComparisonAdapter.getEntryFor(
					EcoreUtil2.getContainerOfType(declaration, TModule.class));
			ProjectComparisonEntry typeCompare = compareEntry.getChildForElementImpl(declaration);

			if (typeCompare != null) {

				ArrayList<AccessorTuple> collectedMissingFields = new ArrayList<>();

				Predicate<ProjectComparisonEntry> filter = (pce -> pce.getElementAPI() instanceof TField);
				filter = filter
						.and(pce -> pce.getElementImpl()[0] == null)
						.and(pce -> PROVIDES_INITIALZER.hasAnnotation((TField) pce.getElementAPI()));

				Function<TInterface, Consumer<? super ProjectComparisonEntry>> actionProvider = pivot -> pce -> {
					TField apiField = ((TField) pce.getElementAPI());
					VirtualApiMissingFieldAccessorTuple ret = createVirtFieldAccessorTuple(apiField);
					collectedMissingFields.add(ret);
				};

				if (!checkInterfaceImplementsInterface(declaration, typeCompare.getElementAPI())) {
					return emptyList();
				}

				// Call the supertype iterations scaffolding:
				interfaceApiSupertypeWalker(filter, actionProvider, projectComparisonAdapter,
						(TInterface) typeCompare.getElementAPI(), TInterface.class);

				return collectedMissingFields;
			}
		}
		return emptyList();
	}

	/**
	 * Computes the list of virtual AccessorTuples. Routes depending on the declaration to
	 * {@link #computeMissingApiGetterSetter(TClass, List)} or {@link #computeMissingApiGetterSetter(TInterface, List)}
	 *
	 * @param concreteAccessorTuples
	 *            already computed given accessor tuples, possibly to be enriched.
	 *
	 * @return List of {@link VirtualApiTField}
	 */
	public List<AccessorTuple> computeMissingApiGetterSetter(TN4Classifier declaration,
			List<AccessorTuple> concreteAccessorTuples) {
		if (declaration instanceof TClass) {
			return computeMissingApiGetterSetter((TClass) declaration, concreteAccessorTuples);
		} else if (declaration instanceof TInterface) {
			return computeMissingApiGetterSetter((TInterface) declaration, concreteAccessorTuples);
		} else {
			throw new IllegalStateException("Called for unhandled type.");
		}
	}

	/**
	 * Computes the list of virtual AccessorTuples.
	 *
	 * @param concreteAccessorTuples
	 *            already computed given accessor tuples, possibly to be enriched.
	 *
	 * @return List of {@link VirtualApiTField}
	 */
	private List<AccessorTuple> computeMissingApiGetterSetter(TClass declaration,
			List<AccessorTuple> concreteAccessorTuples) {
		return _computeMissingApiGetterSetter(declaration, concreteAccessorTuples, pce -> true // Identity: do not
																								// filter!
				, true);
	}

	/**
	 * Compute the list of virtual AccessorTuples for Types annotated with <code>@ProvidesInitialiser</code> on API-side
	 *
	 * @param declaration
	 *            interface
	 * @param concreteAccessorTuples
	 *            existing tuples to modify.
	 * @return List of newly created AccessorTuples.
	 */
	private List<AccessorTuple> computeMissingApiGetterSetter(TInterface declaration,
			List<AccessorTuple> concreteAccessorTuples) {
		return _computeMissingApiGetterSetter(declaration, concreteAccessorTuples,
				// additional filter for Annotated API only:
				pce -> {
					TAnnotableElement ta = (TAnnotableElement) pce.getElementAPI();
					return PROVIDES_DEFAULT_IMPLEMENTATION.hasAnnotation(ta);

				}, true);
	}

	/**
	 * Internal algorithm.
	 */
	private List<AccessorTuple> _computeMissingApiGetterSetter(TN4Classifier declaration,
			List<AccessorTuple> concreteAccessorTuples,
			Predicate<ProjectComparisonEntry> filterPredicate, boolean recursive) {
		Optional<ProjectComparisonAdapter> optAdapt = firstProjectComparisonAdapter(declaration.eResource());
		if (optAdapt.isPresent()) {
			ProjectComparisonAdapter projectComparisonAdapter = optAdapt.get();
			ProjectComparisonEntry compareEntry = projectComparisonAdapter.getEntryFor(
					EcoreUtil2.getContainerOfType(declaration, TModule.class));
			ProjectComparisonEntry typeCompare = compareEntry.getChildForElementImpl(declaration);
			if (typeCompare == null) {
				return Collections.emptyList();
			}

			Predicate<ProjectComparisonEntry> filter = (pce -> (pce.getElementAPI() instanceof TGetter)
					|| (pce.getElementAPI() instanceof TSetter));
			filter = filter
					.and(pce -> pce.getElementImpl()[0] == null)
					.and(filterPredicate /* usually the annotation filter for ProvidesDefaultImpl on Interfaces. */);

			ArrayList<ProjectComparisonEntry> collectedPCEofGetterOrSetter = new ArrayList<>();

			Function<TN4Classifier, Consumer<? super ProjectComparisonEntry>> actionProvider = pivot -> pce -> {
				// Get or Set ??
				collectedPCEofGetterOrSetter.add(pce);
			};

			// ----
			// recursive Extension will generate a stream of compareEntries.
			if (recursive)
				interfaceApiSupertypeWalker(filter, actionProvider, projectComparisonAdapter,
						(TN4Classifier) typeCompare.getElementAPI(), TN4Classifier.class);
			// ----

			/*-
				Cases of the Implementation: A getter or setter can
				- be given as AST (x)
				- be missing (m)
				- were not required by API (/)
				So we have 3*3=9 cases:
				get set
				(x) (x) --> all fine, pair will be transpiled
				(x) (m) --> code for getter will be transpiled, need to inject virtual setter code into existing tuple.
				(x) (/) --> all fine, getter will be transpiled
				(m) (x) --> code for setter will be transpiled, need to inject virtual getter code into existing tuple.
				(m) (m) --> need to create virtual accessor tuple (similar to missing field) with setter & getter
				(m) (/) --> need to create virtual accessor tuple with getter only
				(/) (x) --> all fine
				(/) (m) --> need to create virtual accessor tuple with setter only
				(/) (/) --> all fine nothing to be done.
			 */

			List<ProjectComparisonEntry> getSetList;
			if (recursive)
				getSetList = collectedPCEofGetterOrSetter;
			else
				getSetList = typeCompare
						.allChildren()
						.filter(pce -> (pce.getElementAPI() instanceof TGetter)
								|| (pce.getElementAPI() instanceof TSetter))
						.filter(filterPredicate).collect(Collectors.toList());

			HashMap<Pair<String, Boolean>, GetSetGroup> hmName2getset = new HashMap<>();
			for (ProjectComparisonEntry pce : getSetList) {
				TMember apiAsMember = ((TMember) pce.getElementAPI());
				String name = apiAsMember.getName();
				boolean staticCase = apiAsMember.isStatic();
				Pair<String, Boolean> key = Pair.of(name, staticCase);
				GetSetGroup group = hmName2getset.get(key);
				if (group == null) {
					group = new GetSetGroup(name, staticCase);
					hmName2getset.put(key, group);
				}
				if (pce.getElementAPI() instanceof TGetter) {
					// case getter:
					TGetter apiGetter = (TGetter) pce.getElementAPI();
					if (pce.getElementImpl(0) != null) {
						// case (x) for getter-
						group.getterIsInAST = true;
					} else {
						// case (m) for getter-
						group.getterIsInAST = false;
						group.getter = new VirtualApiTGetter(name, apiGetter);
					}
				} else if (pce.getElementAPI() instanceof TSetter) {
					// case setter:
					TSetter apiSetter = (TSetter) pce.getElementAPI();
					if (pce.getElementImpl(0) != null) {
						// case (x) for setter -
						group.setterIsInAST = true;
					} else {
						// case (m) for setter:
						group.setterIsInAST = false;
						group.setter = new VirtualApiTSetter(name, apiSetter);
					}
				}
			}
			// go over the list of known AccessorTupels and enhance them by adding virtual things.
			for (AccessorTuple conAccTupel : concreteAccessorTuples) {
				GetSetGroup getset = hmName2getset.remove(Pair.of(conAccTupel.getName(), conAccTupel.isStatic()));
				if (getset != null) {
					// some missings found:
					if (getset.hasGetter() && !getset.getterIsInAST
							&& conAccTupel.getGetter() == null // could be mixed in by interface-default-impl different
																// to the intended API-path c.f. GHOLD-212
					) {
						conAccTupel.setGetter(getset.getter);
					}
					if (getset.hasSetter() && !getset.setterIsInAST
							&& conAccTupel.getSetter() == null // could be mixed in by interface-default-impl different
																// to the intended API-path c.f. GHOLD-212
					) {
						conAccTupel.setSetter(getset.setter);
					}
				}
			}
			// remaining entries in hmName2getset need to translated into VirtualApiAccessors.
			List<AccessorTuple> ret = new ArrayList<>();
			for (GetSetGroup getset : hmName2getset.values()) {
				VirtualApiAccessorTuple vAccessTupel = new VirtualApiAccessorTuple(getset.name, getset.staticCases);
				if (getset.getter != null)
					vAccessTupel.setGetter(getset.getter);
				if (getset.setter != null)
					vAccessTupel.setSetter(getset.setter);
				ret.add(vAccessTupel);
			}

			return ret;

		}
		return emptyList();
	}

	/**
	 * Computes the list of virtual fields.
	 *
	 * @return List of {@link VirtualApiTField}
	 */
	public List<TField> computeMissingApiFields(N4InterfaceDeclaration declaration) {
		Optional<ProjectComparisonAdapter> optAdapt = firstProjectComparisonAdapter(declaration.eResource());
		if (optAdapt.isPresent()) {
			ProjectComparisonEntry compareEntry = optAdapt.get().getEntryFor(
					EcoreUtil2.getContainerOfType(declaration.getDefinedType(), TModule.class));
			ProjectComparisonEntry typeCompare = compareEntry.getChildForElementImpl(declaration
					.getDefinedTypeAsInterface());
			if (typeCompare != null) {
				return typeCompare.allChildren()
						.filter(pce -> pce.getElementAPI() instanceof TField)
						.filter(pce -> pce.getElementImpl()[0] == null)
						// only go for empty implementations
						.map(pce -> {
							TField apiMethod = (TField) pce.getElementAPI();
							// do not touch AST on copy!:
							TField copy = TypeUtils.copyPartial(apiMethod,
									TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT);
							TField ret = new VirtualApiTField(apiMethod.getName(), copy);
							return ret;
						})
						.collect(Collectors.toList());
			}
		}
		return emptyList();
	}

}
