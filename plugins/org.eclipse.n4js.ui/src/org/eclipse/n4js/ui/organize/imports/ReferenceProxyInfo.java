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
package org.eclipse.n4js.ui.organize.imports;

import org.eclipse.emf.ecore.EObject;

/**
 * Value object for meta information about unresolved proxy cross reference.
 */
public class ReferenceProxyInfo {

	/** Object holding proxified cross ref. */
	public final EObject eobject;

	/**
	 * Name used in cross ref. It can be null, e.g. {@code ParemterizedTypeRefs} coming from TypesComputer have no
	 * {@code name} in the source code
	 */
	public final String name;

	/**  */
	public ReferenceProxyInfo(EObject eobject, String name) {
		this.eobject = eobject;
		this.name = name;
	}
}
