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
package org.eclipse.n4js.scoping.builtin;

import static java.util.Collections.unmodifiableList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractScope;

import com.google.common.collect.Maps;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.types.utils.TypeUtils;

/**
 * An enumerable scope contains a well known set of elements. Their names are usually known at compile time.
 */
public abstract class EnumerableScope extends AbstractScope {

	private Map<QualifiedName, IEObjectDescription> elements;

	private final ExecutionEnvironmentDescriptor descriptor;

	/**
	 * Instantiate a new enumerable scope without a parent scope.
	 */
	protected EnumerableScope(ExecutionEnvironmentDescriptor descriptor) {
		super(IScope.NULLSCOPE, false);
		this.descriptor = descriptor;
	}

	/**
	 * Create the map of descriptions that make up this scope.
	 */
	protected final Map<QualifiedName, IEObjectDescription> createElements() {
		Map<QualifiedName, IEObjectDescription> result = Maps.newLinkedHashMap();
		descriptor.processResources(getFileNames(), (r) -> buildMap(r, result));
		return result;
	}

	/**
	 * Process the given resource and add everything which is important for this scope into the given map of result
	 * elements.
	 */
	protected abstract void buildMap(Resource resource, Map<QualifiedName, IEObjectDescription> result);

	/**
	 * Return the file names that should be used when this scope is initialized.
	 */
	protected abstract String[] getFileNames();

	@Override
	protected Iterable<IEObjectDescription> getAllLocalElements() {
		if (elements == null) {
			elements = createElements();
		}
		return elements.values();
	}

	@Override
	protected Iterable<IEObjectDescription> getLocalElementsByName(QualifiedName name) {
		if (elements == null) {
			elements = createElements();
		}
		IEObjectDescription result = null;
		if (isIgnoreCase()) {
			result = elements.get(name.toLowerCase());
		} else {
			result = elements.get(name);
		}
		if (result == null)
			return Collections.emptyList();
		return Collections.singleton(result);
	}

	@Override
	protected boolean isShadowed(IEObjectDescription fromParent) {
		if (elements == null) {
			elements = createElements();
		}
		if (isIgnoreCase()) {
			boolean result = elements.containsKey(fromParent.getName().toLowerCase());
			return result;
		} else {
			boolean result = elements.containsKey(fromParent.getName());
			return result;
		}
	}

	/**
	 * Converts a bunch of optional types to type references skipped the absent types.
	 */
	@SafeVarargs
	protected final List<ParameterizedTypeRef> toTypeReferences(Type... types) {
		return unmodifiableList(Arrays.stream(types).map(TypeUtils::createTypeRef).collect(Collectors.toList()));
	}

	/**
	 * Returns a reference to the instance with the given qualified name.
	 *
	 * @return an optional reference.
	 */
	protected <T extends EObject> T getEObjectOrProxy(QualifiedName qn) {
		IEObjectDescription description = getSingleElement(qn);
		if (description == null)
			throw new IllegalStateException(qn + " is not contained in this scope");
		@SuppressWarnings("unchecked")
		T result = (T) description.getEObjectOrProxy();
		return result;
	}
}
