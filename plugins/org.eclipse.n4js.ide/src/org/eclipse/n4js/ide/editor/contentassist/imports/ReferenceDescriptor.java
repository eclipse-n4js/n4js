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
package org.eclipse.n4js.ide.editor.contentassist.imports;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.nodemodel.INode;

/**
 * Describes a reference to an identifiable element given in the N4JS source code. The reference may be resolved or
 * unresolved.
 */
public class ReferenceDescriptor {

	/**
	 * The text of the reference as given in the source code. Could be the complete name of the element being referenced
	 * or just a prefix.
	 */
	public final String prefix;
	/**
	 * The context AST node of this reference. In case of content assist in a file with syntax errors, this may be a
	 * best effort approximation only.
	 */
	public final EObject model;
	/**
	 * The corresponding EMF reference in the AST. In case of content assist in a file with syntax errors, this may be a
	 * best effort approximation only.
	 */
	public final EReference eReference;
	/**
	 * The parse tree node corresponding to {@link #model}.
	 */
	public final INode node;

	/** Creates a new {@link ReferenceDescriptor}. */
	public ReferenceDescriptor(String prefix, EObject model, EReference eReference, INode node) {
		Objects.requireNonNull(prefix);
		Objects.requireNonNull(model);
		Objects.requireNonNull(eReference);
		Objects.requireNonNull(node);
		this.prefix = prefix;
		this.model = model;
		this.eReference = eReference;
		this.node = node;
	}
}
