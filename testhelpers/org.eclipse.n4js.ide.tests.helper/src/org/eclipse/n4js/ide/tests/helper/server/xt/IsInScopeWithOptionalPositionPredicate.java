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
package org.eclipse.n4js.ide.tests.helper.server.xt;

import static org.eclipse.n4js.ide.tests.helper.server.xt.EObjectDescriptionToNameWithPositionMapper.descriptionToNameWithPosition;
import static org.eclipse.n4js.ide.tests.helper.server.xt.EObjectDescriptionToNameWithPositionMapper.getNameFromNameWithPosition;
import static org.eclipse.n4js.ide.tests.helper.server.xt.EObjectDescriptionToNameWithPositionMapper.getPositionFromNameWithPosition;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.scoping.utils.UnresolvableObjectDescription;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;

/**
 * Internally used by {@code ScopeXpectMethod}, replaces internal {@code ScopingTest.IsInScope} class.
 */
public class IsInScopeWithOptionalPositionPredicate implements Predicate<String> {
	private final IQualifiedNameConverter converter;
	private final IScope scope;
	private final URI currentURI;
	private final boolean withLineNumber;

	/** Constructor */
	public IsInScopeWithOptionalPositionPredicate(IQualifiedNameConverter converter, URI currentURI,
			boolean withLineNumber, IScope scope) {
		super();
		this.converter = converter;
		this.scope = scope;
		this.currentURI = currentURI;
		this.withLineNumber = withLineNumber;
	}

	@Override
	public boolean apply(String nameWithPosition) {
		String name = getNameFromNameWithPosition(nameWithPosition);
		String position = getPositionFromNameWithPosition(nameWithPosition);
		QualifiedName qualifiedName = converter.toQualifiedName(name);
		IEObjectDescription desc = scope.getSingleElement(qualifiedName);
		if (desc != null
				&& !(desc instanceof IEObjectDescriptionWithError)
				&& !(desc instanceof UnresolvableObjectDescription)) {
			if (!Strings.isNullOrEmpty(position)) {
				String nameWithPositionOfScopeELement = descriptionToNameWithPosition(currentURI, withLineNumber, desc);
				String positionOfScopeElement = getPositionFromNameWithPosition(nameWithPositionOfScopeELement);
				if (position.equals(positionOfScopeElement)) {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}
}
