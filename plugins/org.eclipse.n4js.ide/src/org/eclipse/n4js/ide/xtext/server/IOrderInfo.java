/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.Collection;
import java.util.List;

import org.eclipse.xtext.resource.IResourceDescription;

/**
 *
 */
public interface IOrderInfo<T> extends Iterable<T> {

	public void visitAffected(List<IResourceDescription.Delta> changes);

	public void visit(Collection<T> projectDescriptions);

	public void visitAll();

}
