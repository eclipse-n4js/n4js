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
package org.eclipse.n4js.resource;

import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescription;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.IResourceScopeCache;

import com.google.common.collect.Sets;

import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.utils.TypeHelper;

/**
 * A description for N4JS resources. It enriches the list of imported names with the names of the super types of
 * referenced instances. That is, the transitive closure of dependencies is calculated.
 */
public class N4JSResourceDescription extends DefaultResourceDescription {

	private final static Logger log = Logger.getLogger(N4JSResourceDescription.class);

	private final IQualifiedNameProvider qualifiedNameProvider;

	private final N4JSCrossReferenceComputer crossReferenceComputer;

	private final TypeHelper typeHelper;

	private final IDefaultResourceDescriptionStrategy strategy;

	private Iterable<QualifiedName> lazyImportedNames;

	/**
	 * Creates a new description for the given resource.
	 */
	public N4JSResourceDescription(N4JSCrossReferenceComputer crossReferenceComputer,
			TypeHelper typeHelper,
			IQualifiedNameProvider qualifiedNameProvider,
			Resource resource,
			N4JSResourceDescriptionStrategy strategy,
			IResourceScopeCache cache) {
		super(resource, strategy, cache);
		this.crossReferenceComputer = crossReferenceComputer;
		this.qualifiedNameProvider = qualifiedNameProvider;
		this.typeHelper = typeHelper;
		this.strategy = strategy;
	}

	@Override
	protected List<IEObjectDescription> computeExportedObjects() {
		final N4JSResource res = getResource() instanceof N4JSResource ? (N4JSResource) getResource() : null;
		if (res == null || !res.isLoadedFromDescription()) {
			// default behavior
			return super.computeExportedObjects();
		} else {
			// we have an N4JSResource that is loaded from the Xtext index (AST is proxy but TModule in place)

			// ORIGINAL CODE FROM SUPER-CLASS:
			if (!getResource().isLoaded()) {
				try {
					getResource().load(null);
				} catch (IOException e) {
					log.error(e.getMessage(), e);
					return Collections.<IEObjectDescription> emptyList();
				}
			}
			final List<IEObjectDescription> exportedEObjects = newArrayList();
			IAcceptor<IEObjectDescription> acceptor = new IAcceptor<IEObjectDescription>() {
				@Override
				public void accept(IEObjectDescription eObjectDescription) {
					exportedEObjects.add(eObjectDescription);
				}
			};
			// ADJUSTED:
			strategy.createEObjectDescriptions(res.getModule(), acceptor);
			// ORIGINAL CODE FROM SUPER-CLASS:
// @formatter:off
//			TreeIterator<EObject> allProperContents = EcoreUtil.getAllProperContents(getResource(), false);
//			while (allProperContents.hasNext()) {
//				EObject content = allProperContents.next(); // <=== this would trigger demand-load of AST!
//				if (!strategy.createEObjectDescriptions(content, acceptor))
//					allProperContents.prune();
//			}
// @formatter:on
			return exportedEObjects;
		}
	}

	// disable reference descriptions
	@Override
	protected List<IReferenceDescription> computeReferenceDescriptions() {
		return Collections.emptyList();
	}

	@Override
	public Iterable<QualifiedName> getImportedNames() {

		if (null == lazyImportedNames) {
			synchronized (this) {
				if (null == lazyImportedNames) {

					// System.out.println("######\t" + getURI());

					// get imported names collected during global scoping
					// the scope provider registers every request in scoping so that by this
					// also all names are collected that cannot be resolved
					Iterable<QualifiedName> superImportedNames = super.getImportedNames();
					Set<QualifiedName> importedNames = Sets.newHashSet();
					if (superImportedNames != null) {
						importedNames = Sets.newHashSet(superImportedNames);
					} else {
						importedNames = Sets.<QualifiedName> newHashSet();
					}
					// import our own module name to get a proper change notification
					Resource resource = getResource();
					List<EObject> contents = resource.getContents();
					if (contents.size() > 1) {
						TModule module = (TModule) contents.get(1);
						importedNames.add(qualifiedNameProvider.getFullyQualifiedName(module));
					}
					final Set<EObject> crossRefTypes = Sets.newHashSet();
					IAcceptor<EObject> acceptor = getCrossRefTypeAcceptor(crossRefTypes);
					crossReferenceComputer.computeCrossRefs(resource, acceptor);
					for (EObject type : crossRefTypes) {
						// TODO TS handle also generics (later with working type system)
						if (type instanceof TFunction) {
							// TODO TS work around for not yet inferred return types of methods and functions
							// later for all expressions the types have to be calculated and for those types
							// all super types have to be collected as well
							// for methods and functions: -> declaredType != returnType (as returnType is
							// inferred at runtime)
							TypeRef returnTypeRef = ((TFunction) type).getReturnTypeRef();
							if (returnTypeRef != null) {
								handleType(importedNames, returnTypeRef.getDeclaredType());
							}
						} else if (type instanceof Type) {
							handleType(importedNames, type);
						} else if (type instanceof TVariable) {
							handleTVariable(importedNames, (TVariable) type);
						} else if (type instanceof TEnumLiteral) {
							handleTEnumLiteral(importedNames, (TEnumLiteral) type);
						}
					}

					this.lazyImportedNames = importedNames;

				}
			}
		}

		return lazyImportedNames;
	}

	private void handleTEnumLiteral(Set<QualifiedName> importedNames, TEnumLiteral literal) {
		TEnum tEnum = (TEnum) literal.eContainer();
		if (tEnum != null) {
			handleType(importedNames, tEnum);
		}
	}

	private void handleTVariable(Set<QualifiedName> importedNames, TVariable var) {
		TypeRef ref = var.getTypeRef();
		if (ref != null) {
			Type declType = ref.getDeclaredType();
			if (declType != null) {
				handleType(importedNames, declType);
			}
		}
	}

	private void handleType(Set<QualifiedName> importedNames, EObject type) {
		Collection<Type> collected = typeHelper.collectAllDeclaredSuperTypes((Type) type, true);
		for (Type collectedType : collected) {
			collectAsImportedName(importedNames, collectedType);
		}
	}

	private void collectAsImportedName(Set<QualifiedName> importedNames, EObject eObject) {
		QualifiedName importedName = qualifiedNameProvider.getFullyQualifiedName(eObject);
		importedNames.add(importedName);
	}

	private IAcceptor<EObject> getCrossRefTypeAcceptor(final Set<EObject> crossRefTypes) {
		IAcceptor<EObject> acceptor = new IAcceptor<EObject>() {

			@Override
			public void accept(EObject t) {
				// TODO TS later use the type inferencer here (and do not work with types but with type references)
				if (t instanceof Type || t instanceof TVariable || t instanceof TEnumLiteral) {
					crossRefTypes.add(t);
				}
			}
		};
		return acceptor;
	}
}
