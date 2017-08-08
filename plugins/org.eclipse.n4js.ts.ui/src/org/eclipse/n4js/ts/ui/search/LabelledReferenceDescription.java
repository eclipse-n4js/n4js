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
package org.eclipse.n4js.ts.ui.search;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.resource.impl.DefaultReferenceDescription;
import org.eclipse.xtext.ui.editor.findrefs.DelegatingReferenceFinder;

/**
 * Customized {@link DefaultReferenceDescription} that is only created within the {@link DelegatingReferenceFinder} for
 * displaying found references in the Eclipse Search UI view. So it is not used within the builder.
 */
@SuppressWarnings("restriction")
public class LabelledReferenceDescription extends DefaultReferenceDescription {
	private final String label;
	private final EObject source;
	private final EObject target;
	private final EObject displayEObject;
	private final int line;

	/**
	 * Return the line number of the found reference.
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Return the display EObject.
	 */
	public EObject getDisplayEObject() {
		return displayEObject;
	}

	/**
	 * Return the target.
	 */
	public EObject getTarget() {
		return target;
	}

	/**
	 * Return the source EObject.
	 */
	public EObject getSource() {
		return source;
	}

	/**
	 * @param fromURI
	 *            the EMF URI to the referencing element
	 * @param toURI
	 *            the EMF URI to the referenced element
	 * @param eReference
	 *            the reference meta type between referencing element and referenced element
	 * @param index
	 *            the index that addresses the reference instance of eReference between referencing element and
	 *            referenced element, 0 is used for toOne references
	 * @param label
	 *            the label to use in the find references result view
	 * @param line
	 *            the line number of the found reference
	 */
	public LabelledReferenceDescription(EObject source, EObject displayEObject, URI fromURI, EObject target, URI toURI,
			EReference eReference,
			int index,
			String label, int line) {
		super(fromURI, toURI, eReference, index, null /* bug in Xtext, may not pass the real value here */);
		this.label = label;
		this.source = source;
		this.target = target;
		this.displayEObject = displayEObject;
		this.line = line;
	}

	/**
	 * Returns the label that should be used in the find references result view.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return String.format("%s:\n%s\n->\n %s", getLabel(), getSourceEObjectUri(), getTargetEObjectUri());
	}

}
