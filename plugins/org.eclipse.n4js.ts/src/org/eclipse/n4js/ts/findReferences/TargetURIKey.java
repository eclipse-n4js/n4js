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
package org.eclipse.n4js.ts.findReferences;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.utils.N4TSGlobals;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Access to the n4 specific cached data during a find references operation.
 */
@SuppressWarnings("restriction")
public class TargetURIKey {

	/**
	 * The key that is used to obtain the cache.
	 */
	public static final TargetURIs.Key<Data> KEY = TargetURIs.Key.from(
			"org.eclipse.n4js.ts.findReferences.TargetURIKey",
			Data.class);

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	/**
	 * Cache that is stored as user data on an instance of the {@link TargetURIs}.
	 */
	public static class Data {
		private final Set<String> valueStrings;
		private final Set<EClass> applicableTypes;
		// Use sorted set to ensure the order of elements. Will be used for optimizing find references in {@link
		// ConcreteSyntaxAwareReferenceFinder}
		private final SortedSet<QualifiedName> typesOrModulesToFind;
		private final IQualifiedNameProvider qualifiedNameProvider;

		/**
		 * Create an empty instance that will accept EObjects to keep information that is reusable.
		 */
		public Data(IQualifiedNameProvider qualifiedNameProvider) {
			this.qualifiedNameProvider = qualifiedNameProvider;
			this.valueStrings = Sets.newHashSet();
			this.applicableTypes = Sets.newHashSet();
			this.typesOrModulesToFind = Sets.newTreeSet();
		}

		/**
		 * Add information about the given object to this cache.
		 *
		 * @param object
		 *            the object to handle
		 */
		public void add(EObject object) {
			this.applicableTypes.add(object.eClass());
			this.valueStrings.add(SimpleAttributeResolver.NAME_RESOLVER.apply(object));

			// Handle composed members
			if (object instanceof TMember && ((TMember) object).isComposed()) {
				List<TMember> constituentMembers = ((TMember) object).getConstituentMembers();
				for (TMember constituentMember : constituentMembers) {
					addFQNs(constituentMember);
				}
			} else {
				addFQNs(object);
			}
		}

		private void addFQNs(EObject object) {
			if (object instanceof TMember || object instanceof TEnumLiteral) {
				Type t = EcoreUtil2.getContainerOfType(object.eContainer(), Type.class);
				typesOrModulesToFind.add(qualifiedNameProvider.getFullyQualifiedName(t));
			} else if (object instanceof Type) {
				typesOrModulesToFind.add(qualifiedNameProvider.getFullyQualifiedName(object));
			} else if (object instanceof TModule) {
				typesOrModulesToFind.add(qualifiedNameProvider.getFullyQualifiedName(object));
			}

			if (object instanceof IdentifiableElement) {
				TModule containingModule = ((IdentifiableElement) object).getContainingModule();
				QualifiedName fullyQualifiedName = qualifiedNameProvider.getFullyQualifiedName(containingModule);
				if (fullyQualifiedName != null) { // remove this when GH-733 is fixed
					typesOrModulesToFind.add(fullyQualifiedName);
				}
			}
		}

		/**
		 * @param eReferenceType
		 *            the type of the reference that is inspected.
		 * @return <code>true</code> if this set contains a subtype of the given eReferenceType.
		 */
		public boolean isEReferenceTypeApplicable(EClass eReferenceType) {
			if (applicableTypes.isEmpty())
				return true;
			for (EClass applicableType : applicableTypes) {
				if (EcoreUtil2.isAssignableFrom(eReferenceType, applicableType)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Returns the names that would be used globally if one of the included elements was referenced.
		 *
		 * @return the names to find.
		 */
		public Set<QualifiedName> getTypesOrModulesToFind() {
			return typesOrModulesToFind;
		}

		/**
		 * Shortcut to decide whether a given concrete syntax is potentially matching the contained objects.
		 */
		public boolean isMatchingConcreteSyntax(String valueString) {
			if (valueStrings.isEmpty() || valueStrings.contains(valueString)) {
				return true;
			}
			// HERE we need to make sure that we use a proper NS delimiter
			int idx = valueString.lastIndexOf(N4TSGlobals.NAMESPACE_ACCESS_DELIMITER);
			if (idx > 0) {
				if (valueStrings.contains(valueString.substring(idx + 1))) {
					return true;
				}
			}
			return false;
		}

	}

	/**
	 * Get or create a data cache in the user data space of the given target URIs.
	 *
	 * @return a new or existing data cache.
	 */
	public Data getData(TargetURIs targetURIs, IReferenceFinder.IResourceAccess resourceAccess) {
		Data result = targetURIs.getUserData(KEY);
		if (result != null) {
			return result;
		}
		return initData(targetURIs, resourceAccess);
	}

	private Data initData(TargetURIs targetURIs, IReferenceFinder.IResourceAccess resourceAccess) {
		Data result = new Data(qualifiedNameProvider);
		init(result, resourceAccess, targetURIs);
		targetURIs.putUserData(KEY, result);
		return result;
	}

	private void init(Data result, IResourceAccess resourceAccess, TargetURIs targetURIs) {
		targetURIs.getTargetResourceURIs().forEach((resourceURI) -> {
			resourceAccess.readOnly(resourceURI, (resourceSet) -> {
				targetURIs.getEObjectURIs(resourceURI).forEach((objectURI) -> {
					try {
						EObject object = resourceSet.getEObject(objectURI, true);
						if (object != null) {
							result.add(object);
						}
					} catch (RuntimeException e) {
						// ignore
					}
				});
				return null;
			});
		});
	}

	/**
	 * Public for testing purpose
	 */
	public void setQualifiedNameProvider(IQualifiedNameProvider qualifiedNameProvider) {
		this.qualifiedNameProvider = qualifiedNameProvider;
	}
}
