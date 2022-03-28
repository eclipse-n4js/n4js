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
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractScope;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * An enumerable scope contains a well known set of elements. Their names are usually known at compile time.
 */
public abstract class EnumerableScope extends AbstractScope {

	/** The elements in this scope. Non-<code>null</code> only after element creation has <em>completed</em>. */
	private ImmutableMap<QualifiedName, IEObjectDescription> elements;
	/** The elements in this scope. Non-<code>null</code> only while element creation is <em>in progress</em>. */
	private Map<QualifiedName, IEObjectDescription> elementsBeingCreated;

	private final ExecutionEnvironmentDescriptor descriptor;

	/**
	 * Instantiate a new enumerable scope without a parent scope.
	 */
	protected EnumerableScope(ExecutionEnvironmentDescriptor descriptor) {
		super(IScope.NULLSCOPE, false);
		this.descriptor = descriptor;
	}

	/**
	 * Get all elements in this scope.
	 */
	protected Map<QualifiedName, IEObjectDescription> getElements() {
		if (elements != null) {
			return elements;
		}
		synchronized (EnumerableScope.class) {
			if (elements != null) {
				return elements;
			}
			if (elementsBeingCreated != null) {
				// note: the semantics of Java's synchronized block guarantees that we only ever get to this point when
				// during the creation of elements (i.e. #createElements()) the thread creating the elements recursively
				// invokes this method (i.e. #getElements()); in other words, we never return map 'elementsBeingCreated'
				// to a different thread than the one currently executing method #createElements()
				return Collections.unmodifiableMap(elementsBeingCreated);
			}
			try {
				elementsBeingCreated = Maps.newLinkedHashMap();
				createElements(elementsBeingCreated);
			} finally {
				// even in case of exceptions we do not want to retry creating elements
				elements = ImmutableMap.copyOf(elementsBeingCreated);
				elementsBeingCreated = null;
			}
			return elements;
		}
	}

	/**
	 * Create the map of descriptions that make up this scope.
	 */
	protected void createElements(Map<QualifiedName, IEObjectDescription> result) {
		descriptor.processResources(getFileNames(), (r) -> buildMap(r, result));
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
		return getElements().values();
	}

	@Override
	protected Iterable<IEObjectDescription> getLocalElementsByName(QualifiedName name) {
		IEObjectDescription result = getElements().get(isIgnoreCase() ? name.toLowerCase() : name);
		if (result == null)
			return Collections.emptyList();
		return Collections.singleton(result);
	}

	@Override
	protected boolean isShadowed(IEObjectDescription fromParent) {
		QualifiedName name = fromParent.getName();
		boolean result = getElements().containsKey(isIgnoreCase() ? name.toLowerCase() : name);
		return result;
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
			throw new IllegalStateException(qn + " is not contained in scope " + this.getClass().getSimpleName());
		@SuppressWarnings("unchecked")
		T result = (T) description.getEObjectOrProxy();
		return result;
	}
}
