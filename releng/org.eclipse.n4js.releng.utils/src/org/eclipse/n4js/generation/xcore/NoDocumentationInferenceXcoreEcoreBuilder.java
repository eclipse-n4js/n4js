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
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.xcore.XModelElement;
import org.eclipse.emf.ecore.xcore.util.XcoreEcoreBuilder;

/**
 * Custom {@link XcoreEcoreBuilder} that disables the automatic inference of a copyright header in favor of the correct
 * processing of the {@code @GenModel} annotation.
 *
 * More generally, this {@link XcoreEcoreBuilder} aligns the behavior of a MWE2-workflow-triggered model generation with
 * that of the incremental builder.
 *
 * For more details see GH-841.
 *
 * TODO This custom implementation can be removed, once we switch to EMF 2.14 which fixes the issue. See
 * https://github.com/eclipse/emf/commit/072118d768f5b7c5aa70bdf3319db57000fa2d9d?diff=unified#diff-0b61973482906a41d26c04fc93cb44f3L247
 * for how this will be fixed in the newer EMF version.
 */
class NoDocumentationInferenceXcoreEcoreBuilder extends XcoreEcoreBuilder {
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

		// delete @Ecore annotation as it is not present in an incremental build
		Optional<EAnnotation> ecoreAnnotation = eModelElement.getEAnnotations().stream()
				.filter(a -> a.getSource().equals(EcorePackage.eNS_URI))
				.findFirst();

		// removes annotation from container
		inferredAnnotations.ifPresent(a -> eModelElement.getEAnnotations().remove(a));
		ecoreAnnotation.ifPresent(a -> eModelElement.getEAnnotations().remove(a));
	}
}
