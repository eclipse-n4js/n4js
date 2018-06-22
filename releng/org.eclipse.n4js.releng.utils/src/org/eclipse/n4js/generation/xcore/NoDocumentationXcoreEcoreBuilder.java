/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.generation.xcore;

import java.util.Optional;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.xcore.XModelElement;
import org.eclipse.emf.ecore.xcore.util.XcoreEcoreBuilder;

/**
 * Custom {@link XcoreEcoreBuilder} that disable the automatic inference of a copyright header, in favor of the correct
 * processing of a {@code @GenModel} annotation.
 *
 * See GH-841.
 *
 * TODO This custom implementation can be removed, once we switch to EMF 2.14 which fixes the issue.
 */
class NoDocumentationXcoreEcoreBuilder extends XcoreEcoreBuilder {
	@Override
	protected void handleAnnotations(XModelElement xModelElement, EModelElement eModelElement) {
		super.handleAnnotations(xModelElement, eModelElement);

		// the following special handling only applies to EPackages
		if (!(eModelElement instanceof EPackage)) {
			return;
		}

		// obtain annotation that was created based on the automatic inference of copyright headers
		Optional<EAnnotation> inferredAnnotations = eModelElement.getEAnnotations().stream()
				.filter(a -> a.getSource().equals(GenModelPackage.eNS_URI))
				// find annotation that is added automatically by inferring copyright header
				.filter(a -> a.getDetails().containsKey("documentation") && a.getDetails().size() == 1)
				.findFirst();

		// removes annotation from container
		inferredAnnotations.ifPresent(a -> eModelElement.getEAnnotations().remove(a));
	}
}
