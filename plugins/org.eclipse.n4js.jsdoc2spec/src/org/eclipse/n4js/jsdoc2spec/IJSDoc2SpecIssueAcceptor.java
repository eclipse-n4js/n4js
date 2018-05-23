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
package org.eclipse.n4js.jsdoc2spec;

import org.eclipse.emf.ecore.EObject;

/**
 *
 */
public interface IJSDoc2SpecIssueAcceptor {

	/**
	 * Default acceptor ignoring everything.
	 */
	public final IJSDoc2SpecIssueAcceptor NULL_ACCEPTOR = new IJSDoc2SpecIssueAcceptor() {

		@Override
		public void addWarning(String message, EObject astElement) {
			// ignore
		}

		@Override
		public String warnings() {
			return "";
		}

		@Override
		public void reset() {
			// ignore
		}
	};

	/**
	 * Accepts a warning, astElement defines the position.
	 */
	public void addWarning(String message, EObject astElement);

	/**
	 * Returns a dump of all warnings with locations.
	 */
	String warnings();

	/**
	 * Clears all warnings
	 */
	public void reset();

}
