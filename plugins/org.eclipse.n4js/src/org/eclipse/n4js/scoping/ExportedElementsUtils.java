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
 * Utility methods related to helper {@link ExportedElementsCollector}.
 */
public class ExportedElementsUtils {

	/**
	 * Collect the elements exported from the given .d.ts module via syntax {@code export = <identifier>;}. Returns
	 * {@link Optional#absent() absent} if the given module does not have such an export.
	 * <p>
	 * In the special case of exporting a variable (or constant) of a certain type <em>T</em>, type <em>T</em> and the
	 * members of type <em>T</em> won't be returned by this method. For example:
	 *
	 * <pre>
	 * interface Ifc {
	 *   m(): void;
	 * }
	 * const value: Ifc;
	 * export = value;
	 * </pre>
	 *
	 * This method will return only const variable "value", neither interface "Ifc" nor its members.
	 */
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
