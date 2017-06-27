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
package org.eclipse.n4js.ts;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;

import org.eclipse.n4js.ts.conversions.TypesValueConverterService;
import org.eclipse.n4js.ts.findReferences.ConcreteSyntaxAwareReferenceFinder;
import org.eclipse.n4js.ts.naming.N4TSQualifiedNameConverter;
import org.eclipse.n4js.ts.resource.BuiltInSchemeAwareResource;
import org.eclipse.n4js.ts.resource.TypesResourceDescriptionStrategy;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypesGlobalScopeProvider;
import org.eclipse.n4js.ts.scoping.builtin.ResourceSetWithBuiltInScheme;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
@SuppressWarnings("restriction")
public class TypesRuntimeModule extends org.eclipse.n4js.ts.AbstractTypesRuntimeModule {

	/**
	 * We add a special {@link TypesValueConverterService} here.
	 *
	 * @see org.eclipse.xtext.service.DefaultRuntimeModule#bindIValueConverterService()
	 */
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return TypesValueConverterService.class;
	}

	/**
	 * We only want to export top-level types.
	 *
	 * @see TypesResourceDescriptionStrategy
	 */
	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return TypesResourceDescriptionStrategy.class;
	}

	@Override
	public Class<? extends IGlobalScopeProvider> bindIGlobalScopeProvider() {
		return BuiltInTypesGlobalScopeProvider.class;
	}

	/**
	 * Bind a resource set that knows about the builtin scheme.
	 */
	@Override
	public Class<? extends XtextResourceSet> bindXtextResourceSet() {
		return ResourceSetWithBuiltInScheme.class;
	}

	/**
	 * Binds a custom qualified name converter changing the delimiter to "/".
	 */
	public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return N4TSQualifiedNameConverter.class;
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return N4TSQualifiedNameProvider.class;
	}

	/***/
	public Class<? extends IReferenceFinder> bindReferenceFinder() {
		return ConcreteSyntaxAwareReferenceFinder.class;
	}

	/***/
	public Class<? extends LazyLinkingResource> bindBuiltInSchemeAwareResource() {
		return BuiltInSchemeAwareResource.class;
	}
}
