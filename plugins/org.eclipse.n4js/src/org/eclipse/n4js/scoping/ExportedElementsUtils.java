/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TAnnotationArgument;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

/**
 *
 */
public class ExportedElementsUtils {

	public static Optional<List<IdentifiableElement>> getElementsExportedViaExportEquals(TModule module) {
		TAnnotation ann = AnnotationDefinition.EXPORT_EQUALS.getAnnotation(module);
		TAnnotationArgument arg = ann != null ? IterableExtensions.head(ann.getArgs()) : null;
		String exportEqualsIdentifier = arg != null ? arg.getArgAsString() : null;
		if (exportEqualsIdentifier == null || exportEqualsIdentifier.isEmpty()) {
			return Optional.absent();
		}
		List<IdentifiableElement> result = new ArrayList<>();
		Iterable<IdentifiableElement> candidates = Iterables.concat(
				module.getFunctions(),
				module.getExportedVariables(),
				module.getNamespaces());
		for (IdentifiableElement elem : candidates) {
			if (exportEqualsIdentifier.equals(elem.getName())) {
				result.add(elem);
			}
		}
		return Optional.of(result);
	}
}
