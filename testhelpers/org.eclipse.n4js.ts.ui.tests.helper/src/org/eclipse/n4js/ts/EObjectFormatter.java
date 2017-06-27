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
package org.eclipse.n4js.ts;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.collect.Lists;

/**
 * A formatter that prints the features in alphabetic order rather than sorted by feature IDs.
 *
 * This was already filed as a pull request against Xpect.
 */
public class EObjectFormatter extends org.xpect.xtext.lib.util.EObjectFormatter {

	@Override
	public String format(EObject object) {
		if (object == null)
			return "null";
		StringBuilder result = new StringBuilder();
		result.append(object.eClass().getName());
		result.append(" {");
		for (EStructuralFeature feature : getAllFeatures(object))
			if (shouldFormat(object, feature))
				result.append(indent("\n" + format(object, feature)));
		result.append("\n}");
		return result.toString();
	}

	@Override
	protected List<EStructuralFeature> getAllFeatures(EObject object) {
		List<EStructuralFeature> result = Lists.newArrayList(object.eClass().getEAllStructuralFeatures());
		Collections.sort(result, new Comparator<EStructuralFeature>() {
			@Override
			public int compare(EStructuralFeature o1, EStructuralFeature o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return result;
	}

	@Override
	public EObjectFormatter resolveCrossReferences() {
		return (EObjectFormatter) super.resolveCrossReferences();
	}

	@Override
	public EObjectFormatter showIndex() {
		return (EObjectFormatter) super.showIndex();
	}

}
