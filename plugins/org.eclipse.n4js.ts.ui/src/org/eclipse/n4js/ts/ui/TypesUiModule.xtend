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

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider
import org.eclipse.n4js.ts.findReferences.TargetURIKey
import org.eclipse.n4js.ts.ui.navigation.URIBasedStorageEditorInputFactory
import org.eclipse.n4js.ts.ui.search.LabellingReferenceFinder
import org.eclipse.n4js.ts.ui.labeling.TypesDocumentationProvider
import org.eclipse.n4js.ts.ui.search.BuiltinSchemeAwareTargetURIKey
import org.eclipse.n4js.ts.ui.search.LabellingReferenceQueryExecutor
import org.eclipse.n4js.ts.ui.labeling.TypesHoverProvider
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider
import org.eclipse.xtext.resource.XtextResourceSet

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

	/**
	 * Bind the {@link org.eclipse.xtext.ui.editor.findrefs.ReferenceQueryExecutor} that maps to types.
	 */
	@SuppressWarnings("restriction")
	def Class<? extends org.eclipse.xtext.ui.editor.findrefs.ReferenceQueryExecutor> bindReferenceQueryExecutor() {
		return LabellingReferenceQueryExecutor;
	}

	/**
	 * Bind the {@link org.eclipse.xtext.ui.editor.findrefs.IReferenceFinder} that find references solely to types (and
	 * TVariables, IdentifiableElement and TEnumLiterals).
	 */
	@SuppressWarnings("restriction")
	def Class<? extends org.eclipse.xtext.ui.editor.findrefs.IReferenceFinder> bindIReferenceFinder() {
		return LabellingReferenceFinder;
	}

	override Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return URIBasedStorageEditorInputFactory;
	}
}
