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
package org.eclipse.n4js.scoping.utils;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.scoping.smith.MeasurableScope;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.xtext.scoping.EmptyScope;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.collect.Iterables;

/**
 * Pseudo member scope used for dynamic types or in case of plain JavaScript files (in which everything is dynamic
 * implicity)
 */
public class DynamicPseudoScope extends EmptyScope implements MeasurableScope {

	/**
	 * @param parent
	 *            scope to wrap be this member scope
	 */
	public DynamicPseudoScope(IScope parent) {
		super(parent);
	}

	/**
	 * Creates unresolvable member scope with null scope
	 */
	public DynamicPseudoScope() {
		this(IScope.NULLSCOPE);
	}

	@Override
	public IScope decorate(DataCollector dataCollector) {
		return new DynamicPseudoScope(MeasurableScope.decorate(parent, dataCollector));
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		Iterable<IEObjectDescription> parentResult = parent.getElements(name);
		if (Iterables.isEmpty(parentResult)) {
			return Collections.<IEObjectDescription> singletonList(new UnresolvableObjectDescription(name));
		}
		return parentResult;
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		IEObjectDescription result = parent.getSingleElement(name);
		if (result == null) {
			return new UnresolvableObjectDescription(name);
		}
		return result;
	}

	@Override
	public IEObjectDescription getSingleElement(EObject object) {
		IEObjectDescription result = parent.getSingleElement(object);
		if (result == null) {
			if (object instanceof IdentifiableElement) {
				return EObjectDescription
						.create(QualifiedName.create(((IdentifiableElement) object).getName()), object);
			}
		}
		return result;
	}

	@Override
	public Iterable<IEObjectDescription> getElements(EObject object) {
		Iterable<IEObjectDescription> result = parent.getElements(object);
		if (Iterables.isEmpty(result)) {
			if (object instanceof IdentifiableElement) {
				return Collections.singletonList(EObjectDescription.create(
						QualifiedName.create(((IdentifiableElement) object).getName()), object));
			}
		}
		return result;
	}

}
