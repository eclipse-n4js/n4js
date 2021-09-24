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
package org.eclipse.n4js.ts.scoping.builtin;

import static java.util.Collections.unmodifiableList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ts.scoping.BetterScope;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * An enumerable scope contains a well known set of elements. Their names are usually known at compile time.
 */
public abstract class EnumerableScope2 extends BetterScope {

	/** Instantiate a new enumerable scope without a parent scope. */
	protected EnumerableScope2(String name, String[] fileNames, ExecutionEnvironmentDescriptor descriptor,
			BiConsumer<Resource, Map<QualifiedName, IEObjectDescription>> buildMap) {

		this(name, ImmutableMap.copyOf(createElements(fileNames, descriptor, buildMap)));

	}

	/** Constructor */
	protected EnumerableScope2(String name, ImmutableMap<QualifiedName, IEObjectDescription> m1) {
		this(name, buildUriMap(m1), m1);
	}

	/** Constructor */
	protected EnumerableScope2(String name, ImmutableMap<URI, IEObjectDescription> m1,
			ImmutableMap<QualifiedName, IEObjectDescription> m2) {

		super(name, null, IScope.NULLSCOPE, m1, m2, false);
	}

	/** Create the map of descriptions that make up this scope. */
	static Map<QualifiedName, IEObjectDescription> createElements(String[] fileNames,
			ExecutionEnvironmentDescriptor descriptor,
			BiConsumer<Resource, Map<QualifiedName, IEObjectDescription>> buildMap) {

		Map<QualifiedName, IEObjectDescription> result = Maps.newLinkedHashMap();
		List<Resource> resources = descriptor.processResources(fileNames);
		for (Resource res : resources) {
			buildMap.accept(res, result);
		}
		return result;
	}

	static ImmutableMap<URI, IEObjectDescription> buildUriMap(Map<QualifiedName, IEObjectDescription> mapByQN) {
		Map<URI, IEObjectDescription> map = new HashMap<>();
		for (IEObjectDescription descr : mapByQN.values()) {
			map.put(descr.getEObjectURI(), descr);
		}
		return ImmutableMap.copyOf(map);
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
		if (description == null) {
			throw new IllegalStateException(qn + " is not contained in this scope");
		}
		@SuppressWarnings("unchecked")
		T result = (T) description.getEObjectOrProxy();
		return result;
	}
}
