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
package org.eclipse.n4js.n4mf;

import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;
import org.eclipse.xtext.validation.IssueSeveritiesProvider;

import org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionManager;
import org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionStrategy;
import org.eclipse.n4js.n4mf.utils.IPathProvider;
import org.eclipse.n4js.n4mf.utils.RuntimePathProvider;
import org.eclipse.n4js.n4mf.validation.N4MFIssueSeveritiesProvider;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class N4MFRuntimeModule extends org.eclipse.n4js.n4mf.AbstractN4MFRuntimeModule {

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return QualifiedNameProvider.class;
	}

	/**
	 * @return binds project path provider in headless mode
	 */
	public Class<? extends IPathProvider> bindIPathProvider() {
		return RuntimePathProvider.class;
	}

	/**
	 * Bind an implementation that use custom issue severities, that uses the default severity provided in
	 * org.eclipse.n4js/n4mf/validation/messages.properties for a given issue code, when there is no other severity
	 * configured by the {@link ConfigurableIssueCodesProvider}.
	 */
	public Class<? extends IssueSeveritiesProvider> bindIssueSeveritiesProvider() {
		return N4MFIssueSeveritiesProvider.class;
	}

	/**
	 * Returns type {@link N4MFResourceDescriptionStrategy}, creates EObjectDescriptions in index.
	 */
	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return N4MFResourceDescriptionStrategy.class;
	}

	/**
	 * Customized so that it produces {@link N4MFResourceDescriptionManager}.
	 *
	 * @return Class<{@link N4MFResourceDescriptionManager}>
	 */
	public Class<? extends IResourceDescription.Manager> bindIResourceDescriptionManager() {
		return N4MFResourceDescriptionManager.class;
	}
}
