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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.n4js.scoping.smith.MeasurableScope;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportScope;

/** Custom import scope that does not trigger resolving imported elements. */
class NonResolvingImportScope extends ImportScope implements MeasurableScope {

	private List<ImportNormalizer> myNormalizers;
	private final EClass myType;

	public NonResolvingImportScope(List<ImportNormalizer> namespaceResolvers, IScope parent, ISelectable importFrom,
			EClass type, boolean ignoreCase) {
		super(namespaceResolvers, parent, importFrom, type, ignoreCase);
		this.myType = type;
	}

	@Override
	public IScope decorate(DataCollector dataCollector) {
		return new NonResolvingImportScope(myNormalizers, MeasurableScope.decorate(getParent(), dataCollector),
				getImportFrom(), myType, isIgnoreCase());
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
		return result;
	}
}
