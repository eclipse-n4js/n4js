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
package org.eclipse.n4js.ts.ui

import com.google.inject.Provider
import org.eclipse.n4js.ts.findReferences.TargetURIKey
import org.eclipse.n4js.ts.ui.labeling.TypesDocumentationProvider
import org.eclipse.n4js.ts.ui.labeling.TypesHoverProvider
import org.eclipse.n4js.ts.ui.navigation.URIBasedStorageEditorInputFactory
import org.eclipse.n4js.ts.ui.search.BuiltinSchemeAwareTargetURIKey
import org.eclipse.n4js.ts.validation.TypesKeywordProvider
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory
import org.eclipse.xtext.ui.shared.Access
import org.eclipse.n4js.ts.scoping.builtin.BasicResourceSetProvider

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
@FinalFieldsConstructor
class TypesUiModule extends AbstractTypesUiModule {

	/**
	 * De-configure the hard coded built in scheme from the runtime bundle.
	 */
	def Class<? extends XtextResourceSet> bindXtextResourceSet() {
		return SynchronizedXtextResourceSet;
	}

	/** 
	 * Bind a resource set that knows about the builtin scheme.
	 */
	def Class<? extends Provider<? extends SynchronizedXtextResourceSet>> provideXtextResourceSet() {
		return BasicResourceSetProvider
	}

	/**
	 * Bind custom IEObjectHoverProvider.
	 */
	def Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return TypesHoverProvider;
	}

	/**
	 * Bind custom IEObjectHoverProvider.
	 */
	def Class<? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider() {
		return TypesDocumentationProvider;
	}

	/**
	 * Bind an implementation can handle find references to builtin types.
	 */
	def Class<? extends TargetURIKey> bindTargetURIKey() {
		return BuiltinSchemeAwareTargetURIKey;
	}

	override Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return URIBasedStorageEditorInputFactory;
	}

	/** Delegate to shared injector */
	def Provider<? extends TypesKeywordProvider> provideTypesKeywordProvider() {
		return Access.contributedProvider(TypesKeywordProvider);
	}

}
