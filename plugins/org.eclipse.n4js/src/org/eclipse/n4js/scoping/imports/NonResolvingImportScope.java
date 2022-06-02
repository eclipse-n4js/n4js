/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.imports;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.DeclMergingUtils;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.utils.collections.Iterables2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportScope;

import com.google.common.base.Optional;

/** Custom import scope that does not trigger resolving imported elements. */
class NonResolvingImportScope extends ImportScope {

	private List<ImportNormalizer> myNormalizers;
	private final EClass myType;
	private final DeclMergingHelper declMergingHelper;
	private final Optional<BuiltInTypeScope> builtInTypeScope;

	public NonResolvingImportScope(List<ImportNormalizer> namespaceResolvers, IScope parent, ISelectable importFrom,
			EClass type, boolean ignoreCase, DeclMergingHelper declMergingHelper,
			Optional<BuiltInTypeScope> builtInTypeScope) {
		super(namespaceResolvers, parent, importFrom, type, ignoreCase);
		this.myType = type;
		this.declMergingHelper = declMergingHelper;
		this.builtInTypeScope = builtInTypeScope;
	}

	@Override
	protected List<ImportNormalizer> removeDuplicates(List<ImportNormalizer> namespaceResolvers) {
		List<ImportNormalizer> result = super.removeDuplicates(namespaceResolvers);
		myNormalizers = result;
		return result;
	}

	@Override
	protected Iterable<IEObjectDescription> getLocalElementsByName(QualifiedName name) {
		List<IEObjectDescription> result = newArrayList();
		QualifiedName resolvedQualifiedName = null;
		ISelectable importFrom = getImportFrom();
		for (ImportNormalizer normalizer : myNormalizers) {
			final QualifiedName resolvedName = normalizer.resolve(name);
			if (resolvedName != null) {
				Iterable<IEObjectDescription> resolvedElements = importFrom.getExportedObjects(myType, resolvedName,
						isIgnoreCase());
				for (IEObjectDescription resolvedElement : resolvedElements) {
					if (resolvedQualifiedName == null)
						resolvedQualifiedName = resolvedName;
					else if (!resolvedQualifiedName.equals(resolvedName)) {
						// change is here
						if (result.get(0).getEObjectURI().equals(resolvedElement.getEObjectURI())) {
							return emptyList();
						}
						// change is above
					}
					QualifiedName alias = normalizer.deresolve(resolvedElement.getName());
					if (alias == null)
						throw new IllegalStateException("Couldn't deresolve " + resolvedElement.getName()
								+ " with import " + normalizer);
					final AliasedEObjectDescription aliasedEObjectDescription = new AliasedEObjectDescription(alias,
							resolvedElement);
					result.add(aliasedEObjectDescription);
				}
			}
		}
		result = removeGlobalDtsElementsClashingWithBuiltInTypes(result);
		result = declMergingHelper.chooseRepresentatives(result);
		return result;
	}

	/**
	 * To give built-in types priority over custom global elements in .d.ts files, we remove global elements defined in
	 * .d.ts files with a name that conflicts with a built-in type.
	 */
	private List<IEObjectDescription> removeGlobalDtsElementsClashingWithBuiltInTypes(List<IEObjectDescription> descs) {
		if (builtInTypeScope.isPresent()) {
			Set<QualifiedName> builtInTypeNames = builtInTypeScope.get().getAllElementNames();
			descs.removeIf(desc -> DeclMergingUtils.isGlobal(desc)
					&& ResourceType.getResourceType(desc.getEObjectURI()) == ResourceType.DTS
					&& builtInTypeNames.contains(desc.getName()));
		}
		return descs;
	}

	/*
	 * Overridden to avoid eager iteration over all elements.
	 */
	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		final Iterable<IEObjectDescription> globalElements = getParent().getAllElements();
		final Iterable<IEObjectDescription> aliased = getAllLocalElements();
		return Iterables2.skipDuplicates(this::getIgnoreCaseAwareQualifiedName, aliased, globalElements);
	}

	// TODO GH-2099 deactivated for now; needs further investigation
	/*
	 * Overridden to avoid eager iteration over all candidates.
	 *
	 * WARNING: this changes the behavior! The implementation of the super class checks for name conflicts across all
	 * candidates whereas this override checks only on a per-candidate basis.
	 */
// @formatter:off
//	@Override
//	protected Iterable<IEObjectDescription> getAliasedElements(Iterable<IEObjectDescription> candidates) {
//		Multimap<QualifiedName, IEObjectDescription> keyToDescription = LinkedHashMultimap.create();
//		Multimap<QualifiedName, ImportNormalizer> keyToNormalizer = HashMultimap.create();
//		return IterableExtensions.flatMap(candidates, new Function1<>() {
//			@Override
//			public Iterable<IEObjectDescription> apply(IEObjectDescription imported) {
//				keyToDescription.clear();
//				keyToNormalizer.clear();
//				QualifiedName fullyQualifiedName = imported.getName();
//				for (ImportNormalizer normalizer : myNormalizers) {
//					QualifiedName alias = normalizer.deresolve(fullyQualifiedName);
//					if (alias != null) {
//						QualifiedName key = alias;
//						if (isIgnoreCase()) {
//							key = key.toLowerCase();
//						}
//						keyToDescription.put(key, new AliasedEObjectDescription(alias, imported));
//						keyToNormalizer.put(key, normalizer);
//					}
//				}
//				for (QualifiedName name : keyToNormalizer.keySet()) {
//					if (keyToNormalizer.get(name).size() > 1)
//						keyToDescription.removeAll(name);
//				}
//				return keyToDescription.values();
//			}
//		});
//	}
// @formatter:on
}
