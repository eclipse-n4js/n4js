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
package org.eclipse.n4js.n4mf

import org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionStrategy
import org.eclipse.n4js.n4mf.validation.N4MFIssueSeveritiesProvider
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.validation.IssueSeveritiesProvider
import org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionManager
import org.eclipse.n4js.n4mf.utils.IPathProvider
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.n4js.n4mf.utils.RuntimePathProvider
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class N4MFRuntimeModule extends AbstractN4MFRuntimeModule {

	override Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return QualifiedNameProvider;
	}

	/**
	 * @return binds project path provider in headless mode
	 */
	def Class<? extends IPathProvider> bindIPathProvider() {
		return RuntimePathProvider;
	}

	/**
	 * Bind an implementation that use custom issue severities, that uses the default severity provided in
	 * org.eclipse.n4js/n4mf/validation/messages.properties for a given issue code, when there is no other severity
	 * configured by the {@link ConfigurableIssueCodesProvider}.
	 */
	def Class<? extends IssueSeveritiesProvider> bindIssueSeveritiesProvider() {
		return N4MFIssueSeveritiesProvider;
	}

	/**
	 * Returns type {@link N4MFResourceDescriptionStrategy}, creates EObjectDescriptions in index.
	 */
	def Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return N4MFResourceDescriptionStrategy;
	}

	/**
	 * Customized so that it produces {@link N4MFResourceDescriptionManager}.
	 *
	 * @return Class<{@link N4MFResourceDescriptionManager}>
	 */
	def Class<? extends IResourceDescription.Manager> bindIResourceDescriptionManager() {
		return N4MFResourceDescriptionManager;
	}
}
