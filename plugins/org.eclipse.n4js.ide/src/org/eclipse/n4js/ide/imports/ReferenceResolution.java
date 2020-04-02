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
package org.eclipse.n4js.ide.imports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.util.ReplaceRegion;

/**
 * Each instance of this class represents one possible resolution of a {@link ReferenceDescriptor reference given in the
 * N4JS source code}. There may be many such possible resolutions for a single reference in the code. Class
 * {@link ReferenceResolutionHelper} can be used to obtain all possible resolutions for a given reference.
 */
public class ReferenceResolution {

	/** The element denoted by the reference according to this resolution. */
	public final IEObjectDescription referencedElement;

	/** The proposed text to be inserted at the location of the reference. */
	public final String proposal;

	/** A label to be shown to the user when offering this resolution. */
	public final String label;

	/** An additional description to be shown to the user when offering this resolution. May be <code>null</code>. */
	public final String description;

	/** An import that has to be added in case the user chooses this resolution. May be <code>null</code>. */
	public final ImportDescriptor importToBeAdded;

	/** Text replacements that have to be applied in case the user chooses this resolution. May be empty. */
	public final List<ReplaceRegion> textReplacements;

	/** Creates an instance. See {@link ReferenceResolution}. */
	public ReferenceResolution(IEObjectDescription referencedElement, String proposal, String label, String description,
			ImportDescriptor importToBeAdded, Collection<ReplaceRegion> textReplacements) {
		this.referencedElement = referencedElement;
		this.proposal = proposal;
		this.label = label;
		this.description = description;
		this.importToBeAdded = importToBeAdded;
		this.textReplacements = Collections.unmodifiableList(new ArrayList<>(textReplacements));
	}
}
