/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts

import com.google.inject.Provider
import org.eclipse.n4js.ts.conversions.TypesValueConverterService
import org.eclipse.n4js.ts.naming.N4TSQualifiedNameConverter
import org.eclipse.n4js.ts.resource.TypesResourceDescriptionStrategy
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypesGlobalScopeProvider
import org.eclipse.n4js.ts.scoping.builtin.ConfiguredResourceSetProvider
import org.eclipse.xtext.conversion.IValueConverterService
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet
import org.eclipse.xtext.scoping.IGlobalScopeProvider
import org.eclipse.xtext.service.DefaultRuntimeModule

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class TypesRuntimeModule extends AbstractTypesRuntimeModule {

	/**
	 * We add a special {@link TypesValueConverterService} here.
	 *
	 * @see DefaultRuntimeModule#bindIValueConverterService()
	 */
	override Class<? extends IValueConverterService> bindIValueConverterService() {
		return TypesValueConverterService;
	}

	/**
	 * We only want to export top-level types.
	 *
	 * @see TypesResourceDescriptionStrategy
	 */
	def Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return TypesResourceDescriptionStrategy;
	}

	override Class<? extends IGlobalScopeProvider> bindIGlobalScopeProvider() {
		return BuiltInTypesGlobalScopeProvider;
	}

	/**
	 * Bind a resource set that knows about the builtin scheme.
	 */
	def Class<? extends Provider<? extends SynchronizedXtextResourceSet>> provideConfiguredXtextResourceSet() {
		return ConfiguredResourceSetProvider;
	}

	/**
	 * Binds a custom qualified name converter changing the delimiter to "/".
	 */
	def Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return N4TSQualifiedNameConverter;
	}

	override Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return N4TSQualifiedNameProvider;
	}
}
