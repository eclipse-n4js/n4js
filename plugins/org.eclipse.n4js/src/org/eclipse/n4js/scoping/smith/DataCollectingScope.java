/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.smith;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * A scope delegate that will trace the times of the called methods.
 */
class DataCollectingScope implements IScope {

	private final IScope delegate;
	final DataCollector dataCollector;
	private final String prefix;

	/**
	 * Constructor
	 */
	DataCollectingScope(IScope delegate, DynamicDataCollector dataCollector) {
		this.delegate = delegate;
		this.prefix = delegate.getClass().getSimpleName();
		this.dataCollector = dataCollector;
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		try (Measurement m = dataCollector.getMeasurement(prefix + ".getSingleElement(name)")) {
			return delegate.getSingleElement(name);
		}
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		try (Measurement m = dataCollector.getMeasurement(prefix + ".getElements(name)")) {
			return delegate.getElements(name);
		}
	}

	@Override
	public IEObjectDescription getSingleElement(EObject object) {
		try (Measurement m = dataCollector.getMeasurement(prefix + ".getAllElements(object)")) {
			return delegate.getSingleElement(object);
		}
	}

	@Override
	public Iterable<IEObjectDescription> getElements(EObject object) {
		try (Measurement m = dataCollector.getMeasurement(prefix + ".getAllElements(object)")) {
			return delegate.getElements(object);
		}
	}

	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		try (Measurement m = dataCollector.getMeasurement(prefix + ".getAllElements(object)")) {
			return delegate.getAllElements();
		}
	}

}
