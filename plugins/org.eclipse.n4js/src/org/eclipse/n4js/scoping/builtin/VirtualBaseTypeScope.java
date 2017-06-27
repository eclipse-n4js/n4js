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

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;

import org.eclipse.n4js.ts.scoping.builtin.EnumerableScope;
import org.eclipse.n4js.ts.scoping.builtin.ExecutionEnvironmentDescriptor;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeDefs;
import org.eclipse.n4js.ts.types.VirtualBaseType;

/**
 * This scope provides access to virtual base types, only available to the type system.
 */
public final class VirtualBaseTypeScope extends EnumerableScope {

	private static final String[] FILE_NAMES = { "builtin_js.n4ts" /* pull only virtualBase stuff. */};

	/**
	 * Obtains an instance in the context of the given resourceSet.
	 */
	public static VirtualBaseTypeScope get(ResourceSet resourceSet) {
		VirtualBaseTypeScopeAccess result = (VirtualBaseTypeScopeAccess) EcoreUtil.getAdapter(resourceSet.eAdapters(),
				VirtualBaseTypeScope.class);
		if (result == null) {
			throw new IllegalStateException("Missing adapter for VirtualBaseTypeScope");
		}
		return result.getScope();
	}

	/**
	 * Creates a new scope that loads its content by using the given descriptor.
	 */
	public VirtualBaseTypeScope(ExecutionEnvironmentDescriptor descriptor) {
		super(descriptor);
	}

	/**
	 * The built-in name {@code "ArgumentsType"}
	 */
	public static final QualifiedName QN_VBT_ARGUMENTS = QualifiedName.create("ArgumentsType");

	/**
	 * Returns the built-in arguments type.
	 */
	public final VirtualBaseType getArgumentsType() {
		return getEObjectOrProxy(QN_VBT_ARGUMENTS);
	}

	@Override
	protected String[] getFileNames() {
		return FILE_NAMES;
	}

	@Override
	protected void buildMap(Resource resource, Map<QualifiedName, IEObjectDescription> elements) {
		TypeDefs typeDefinitions = (TypeDefs) resource.getContents().get(0);
		for (Type type : typeDefinitions.getTypes()) {
			if (type instanceof VirtualBaseType) { // only virtualBase children.
				IEObjectDescription description = EObjectDescription.create(type.getName(), type);
				elements.put(description.getName(), description);
			}
		}
	}

}
