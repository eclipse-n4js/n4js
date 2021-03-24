/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterType;
import org.eclipse.xtext.util.LineAndColumn;

import com.google.common.base.Optional;

/**
 * This exception will be thrown by the transpiler if the resource being transpiled contains unresolved proxies, usually
 * caused by unresolved references in the N4JS source code.
 * <p>
 * Since the Xtext framework creates Issues/Diagnostics/IMarkers for unresolved references automatically and resources
 * with errors are usually not transpiled, the transpiler should never run into this issue. However, resources matched
 * by a {@link ModuleFilterType#NO_VALIDATE noValidate filter} will be transpiled even in case of unresolved references,
 * because no Issues/Diagnostics/IMarkers are created for them.
 */
public class UnresolvedProxyInSubGeneratorException extends IllegalStateException {

	/**
	 * Creates a new instance. See {@link UnresolvedProxyInSubGeneratorException}.
	 */
	public UnresolvedProxyInSubGeneratorException(Resource resource, Optional<LineAndColumn> pos) {
		super("unable to transpile file " + resource.getURI().lastSegment() + " due to an unresolved reference"
				+ " in line " + (pos.isPresent() ? pos.get().getLine() : "<unknown>")
				+ " at column " + (pos.isPresent() ? pos.get().getColumn() : "<unknown>"));
	}
}
