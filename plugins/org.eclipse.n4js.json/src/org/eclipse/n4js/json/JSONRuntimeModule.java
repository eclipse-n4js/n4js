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
package org.eclipse.n4js.json;

import org.eclipse.n4js.json.conversion.JSONValueConverterService;
import org.eclipse.n4js.json.generator.NeverGenerate;
import org.eclipse.n4js.json.naming.JSONQualifiedNameProvider;
import org.eclipse.n4js.json.parser.JSONHiddenTokenHelper;
import org.eclipse.n4js.json.resource.JSONResourceDescriptionManager;
import org.eclipse.n4js.json.resource.JSONResourceDescriptionStrategy;
import org.eclipse.n4js.json.validation.JSONIssueCodes;
import org.eclipse.n4js.json.validation.JSONIssueSeveritiesProvider;
import org.eclipse.n4js.xtext.serializer.SerializerPatchModule;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.generator.IShouldGenerate;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parsetree.reconstr.IHiddenTokenHelper;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.validation.IssueSeveritiesProvider;

import com.google.inject.Binder;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class JSONRuntimeModule extends AbstractJSONRuntimeModule {
	/** Bind custom value converter for JSON-specific literals */
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return JSONValueConverterService.class;
	}

	/** Bind {@link IQualifiedNameProvider} to {@link JSONQualifiedNameProvider}. */
	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return JSONQualifiedNameProvider.class;
	}

	/** Bind custom JSON issue severities provider that operates based on {@link JSONIssueCodes}. */
	public Class<? extends IssueSeveritiesProvider> bindIssueSeveritiesProvider() {
		return JSONIssueSeveritiesProvider.class;
	}

	/** Bind extension based resource description manager for JSON resources. */
	public Class<? extends IResourceDescription.Manager> bindIResourceDescriptionManager() {
		return JSONResourceDescriptionManager.class;
	}

	/** Bind extension based resource description strategy for JSON resources. */
	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return JSONResourceDescriptionStrategy.class;
	}

	/** Bind custom hidden token helper. */
	public Class<? extends IHiddenTokenHelper> bindIHiddenTokenHelper() {
		return JSONHiddenTokenHelper.class;
	}

	/** Avoid races in internal serializer caches */
	public void configureSerializerPatches(Binder binder) {
		new SerializerPatchModule().configure(binder);
	}

	/** Never generate something from .json files */
	public Class<? extends IShouldGenerate> bindIShouldGenerate() {
		return NeverGenerate.class;
	}
}
