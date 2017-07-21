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
package org.eclipse.n4js.scoping.members;

import java.util.List;

import org.eclipse.n4js.ts.types.TMember;

/**
 * Interface for creating composed members.
 */
public interface MemberFactory {

	/** Returns true iff the composition of members is valid. */
	public boolean isValid();

	/** Returns a new composed {@link TMember}. */
	public TMember create(String name);

	/** Returns the constituent members */
	public List<TMember> getConstituentMembers();

}
