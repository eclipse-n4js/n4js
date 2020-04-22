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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.scoping.smith.MeasurableScope;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractScope;

/**
 * A scope implementation that uses a parent scope and a second delegate scope. The delegate scope is asked whenever the
 * local elements are queried.
 */
public class MergedScope extends AbstractScope implements MeasurableScope {

	private final IScope delegate;

	/**
	 * Merges both scopes.
	 */
	public MergedScope(IScope parent, IScope delegate) {
		super(parent, false);
		this.delegate = delegate;
	}

	@Override
	public IScope decorate(DataCollector dataCollector) {
		return new MergedScope(MeasurableScope.decorate(getParent(), dataCollector),
				MeasurableScope.decorate(delegate, dataCollector));
	}

	@Override
	protected Iterable<IEObjectDescription> getAllLocalElements() {
		return delegate.getAllElements();
	}

	@Override
	protected Iterable<IEObjectDescription> getLocalElementsByEObject(EObject object, URI uri) {
		return delegate.getElements(object);
	}

	@Override
	protected Iterable<IEObjectDescription> getLocalElementsByName(QualifiedName name) {
		return delegate.getElements(name);
	}

}
