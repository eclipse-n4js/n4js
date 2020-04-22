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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.scoping.smith.MeasurableScope;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.collect.Iterables;

/**
 * A scope that is composed of other scopes. Erroneous descriptions are filtered out if (at least) one other scope
 * contains a non-erroneous description for the same name/object in case of single element access.
 * <p>
 * IMPORTANT:<br>
 * Usually, scopes should *not* be composed with this class, instead nesting should be used (one scope being a parent of
 * the other). This class is intended only for some tweaks related to content assist.
 */
public final class CompositeScope implements MeasurableScope {

	/**
	 * The child scopes that together constitute this scope.
	 */
	protected final IScope[] childScopes;

	/**
	 * Creates a new {@link CompositeScope}; if no scopes are given, {@link IScope#NULLSCOPE} is returned.
	 */
	public static final IScope create(IScope... scopes) {
		if (scopes.length == 0) {
			return IScope.NULLSCOPE;
		}
		if (scopes.length == 1) {
			return scopes[0];
		}
		return new CompositeScope(scopes);
	}

	/**
	 * Creates a new {@link CompositeScope}; if no scopes are given, {@link IScope#NULLSCOPE} is returned.
	 */
	public static final IScope create(List<IScope> scopes) {
		if (scopes == null || scopes.isEmpty()) {
			return IScope.NULLSCOPE;
		}
		if (scopes.size() == 1) {
			return scopes.get(0);
		}
		IScope[] arrayScopes = new IScope[scopes.size()];
		scopes.toArray(arrayScopes);
		return new CompositeScope(arrayScopes);
	}

	/**
	 * @see #create(IScope...)
	 */
	protected CompositeScope(IScope... scopes) {
		childScopes = scopes;
	}

	@Override
	public IScope decorate(DataCollector dataCollector) {
		IScope[] scopes = new IScope[childScopes.length];
		for (int i = 0; i < scopes.length; i++) {
			scopes[i] = MeasurableScope.decorate(childScopes[i], dataCollector);
		}
		return new CompositeScope(scopes);
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		IEObjectDescription result = null;
		for (IScope currScope : childScopes) {
			final IEObjectDescription currResult = currScope.getSingleElement(name);
			if (currResult != null) {
				if (!(IEObjectDescriptionWithError.isErrorDescription(currResult))) {
					return currResult; // no error, use scope order as precedence (first one wins) and return
				}
				if (result == null) {
					result = currResult; // with error, maybe we find something w/o error in following scope, do not
											// return yet
				}
			}
		}
		return result; // return null or (first) description with error
	}

	@Override
	public IEObjectDescription getSingleElement(EObject object) {
		IEObjectDescription result = null;
		for (IScope currScope : childScopes) {
			final IEObjectDescription currResult = currScope.getSingleElement(object);
			if (currResult != null) {
				if (!(IEObjectDescriptionWithError.isErrorDescription(currResult))) {
					return currResult; // no error, use scope order as precedence (first one wins) and return
				}
				if (result == null) {
					result = currResult; // with error, maybe we find something w/o error in following scope, do not
											// return yet
				}
			}
		}
		return result; // return null or (first) description with error
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		return Iterables.concat(Arrays.stream(childScopes).map(currScope -> currScope.getElements(name))
				.collect(Collectors.toList()));
	}

	@Override
	public Iterable<IEObjectDescription> getElements(EObject object) {
		return Iterables.concat(Arrays.stream(childScopes).map(currScope -> currScope.getElements(object))
				.collect(Collectors.toList()));
	}

	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		return Iterables.concat(Arrays.stream(childScopes).map(currScope -> currScope.getAllElements())
				.collect(Collectors.toList()));
	}
}
