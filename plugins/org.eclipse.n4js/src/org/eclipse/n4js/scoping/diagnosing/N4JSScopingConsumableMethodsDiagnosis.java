/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.diagnosing;

import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;

/**
 * A scoping diagnosis for the case that the user tries to refer to an interface default implementation using the super
 * keyword.
 */
public class N4JSScopingConsumableMethodsDiagnosis extends ScopingDiagnosis<ParameterizedPropertyAccessExpression> {

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	@Override
	DiagnosticMessage diagnose(QualifiedName name, ParameterizedPropertyAccessExpression propertyAccess) {
		// determine containing member declaration and classifier definition
		N4MemberDeclaration containingMemberDeclaration = EcoreUtil2.getContainerOfType(propertyAccess,
				N4MemberDeclaration.class);
		N4ClassifierDefinition classifierDefinition = EcoreUtil2.getContainerOfType(containingMemberDeclaration,
				N4ClassifierDefinition.class);

		// if ancestors present and non-static context (no super in static context)
		if (containingMemberDeclaration != null &&
				!containingMemberDeclaration.isStatic() &&
				classifierDefinition != null) {

			// Get candidate methods
			MemberList<TMember>.MemberIterable<TMethod> methods = containerTypesHelper.fromContext(propertyAccess)
					.membersOfImplementedInterfacesForConsumption((TClassifier) classifierDefinition.getDefinedType())
					.methods();

			boolean hasMethod = methods.stream()
					.filter(m -> !m.isHasNoBody() && !m.isStatic()) // Filter for non-static non-abstract methods
					.anyMatch(m -> m.getName().equals(name.toString()));

			if (hasMethod) {
				return createMessage(IssueCodes.CLF_CANNOT_REFER_TO_DEFAULT_METHOD_WITH_SUPER.name(),
						IssueCodes.CLF_CANNOT_REFER_TO_DEFAULT_METHOD_WITH_SUPER.getMessage());
			}
		}
		return null;
	}
}
