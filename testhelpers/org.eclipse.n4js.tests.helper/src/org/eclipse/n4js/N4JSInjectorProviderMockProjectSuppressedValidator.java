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
package org.eclipse.n4js;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression.IssueSuppressionModule;
import org.eclipse.n4js.N4JSInjectorProviderWithMockProject.MockProjectModule;

import com.google.inject.util.Modules;

/**
 * An injector provider which combines {@link N4JSInjectorProviderWithMockProject} and
 * {@link N4JSInjectorProviderWithIssueSuppression}
 */
public class N4JSInjectorProviderMockProjectSuppressedValidator extends N4JSInjectorProvider {
	/** */
	public N4JSInjectorProviderMockProjectSuppressedValidator() {
		super(Modules.override(new IssueSuppressionModule()).with(new MockProjectModule()));
	}
}
