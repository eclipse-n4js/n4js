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
package org.eclipse.n4js.resource;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.DescriptionUtils;
import org.eclipse.xtext.resource.IResourceDescription;

/**
 */
public class N4JSDescriptionUtils extends DescriptionUtils {

	@Override
	public Set<URI> collectOutgoingReferences(IResourceDescription description) {
		return new HashSet<>();
	}
}
