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
package org.eclipse.n4js.n4idl.scoping

import java.util.Objects
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.naming.QualifiedNameComputer
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TVersionable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope

/**
 * An implementation of {@link IScope} that considers versioned objects.
 *
 */
class N4IDLVersionAwareScope implements IScope {

	private final IScope delegate;
	private final N4IDLVersionableFilter filter;
	private final QualifiedNameComputer qualifiedNameComputer

	/**
	 * Creates a new instance that filters the elements from the given delegate scope using the given context version.
	 *
	 * @param delegate
	 *            the delegate to query for elements
	 * @param contextVersion
	 * 			  the context version to consider
	 * @param qualifiedNameComputer
	 * 			.. the QualifiedNameComputer implementatio to use
	 */
	new(IScope delegate, int contextVersion, QualifiedNameComputer qualifiedNameComputer) {
		if (contextVersion <= 0)
			throw new IllegalArgumentException("Context version must be a positive integer");
		this.delegate = Objects.requireNonNull(delegate);
		this.filter = new N4IDLVersionableFilter(contextVersion);

		this.qualifiedNameComputer = qualifiedNameComputer;
	}

	override getAllElements() {
		return delegate.getAllElements();
	}

	override getElements(QualifiedName name) {
		return filter.filterElements(delegate.getElements(name));
	}

	override getElements(EObject object) {
		return filter.filterElements(delegate.getElements(object));
	}

	override getSingleElement(QualifiedName name) {
		return selectElement(delegate.getElements(name));
	}

	override getSingleElement(EObject object) {
		return selectElement(delegate.getElements(object));
	}

	/**
	 * Selects the first description of an element that satisfies either of the following conditions:
	 *
	 * <ul>
	 * <li>The element is not an instance of {@link TClassifier}, i.e., it is not versionable.</li>
	 * <li>The element is an instance of {@link TClassifier} and its version is equal to the upper limit of the
	 * requested version range.</li>
	 * </ul>
	 * <p>
	 * If no element satisfies these conditions, then the given iterable only contains descriptions for instances of
	 * {@link TClassifier}. In that case, the element with the maximal version number that is still contained in the
	 * requested version range is selected and its description is returned.
	 * </p>
	 * <p>
	 * If no element is contained in the requested version range, then <code>null</code> is returned.
	 * </p>
	 */
	private def IEObjectDescription selectElement(Iterable<IEObjectDescription> descriptions) {
		val description = filter.filterElements(descriptions).head();

		if (null === description) {
			return null;
		}

		// Special handling for virtual versions to warn users about
		// the use of virtual versions, if there are concrete versions that are
		// not imported.
		val element = description.EObjectOrProxy;
		if (element instanceof TVersionable) {
			if (element.version != filter.contextVersion) {
				return wrapVirtualVersion(description);
			}
		}

		return description;
	}

	private def IEObjectDescription wrapVirtualVersion(IEObjectDescription virtualVersionDescription) {
		val allElements = this.delegate.allElements;
		val element = virtualVersionDescription.EObjectOrProxy;

		if (element instanceof TVersionable && element instanceof Type) {
			val virtualElementVersion = (element as TVersionable).version;
			val virtualElementFQN = getScopingFullyQualifiedName(element as Type);
			// If the global scope contains a better-fitting (less or equal than but closer to contextVersion)
			// version of the type, return an error-description.
			// Be aware that this query for allElement is expensive
			val matchingFQNVersions = allElements
				.filter[d | d.qualifiedName.equals(virtualElementFQN)]
				.filter[d | d.EObjectOrProxy instanceof TVersionable]
				.map[d | (d.EObjectOrProxy as TVersionable).version]
				.filter[v | v <= filter.contextVersion];

			val higherVersionExists = matchingFQNVersions
				.exists[v | v > virtualElementVersion]
			if (higherVersionExists) {
				return new RequiredVersionNotImportedDescription(virtualVersionDescription.name, virtualVersionDescription);
			}
		}

		return virtualVersionDescription;
	}

	private def QualifiedName getScopingFullyQualifiedName(Type type) {
		// compute '/'-delimited FQN
		val fullyQualifiedName = qualifiedNameComputer.getFullyQualifiedTypeName_WITH_LEGACY_SUPPORT(type);
		// convert to QualifiedName
		return QualifiedName.create(fullyQualifiedName.split("\\.").toList);
	}

	override toString() {
		return "N4IDLVersionAwareScope[contextVersion = " + this.filter.contextVersion + "] -> " + delegate.toString;
	}

}
