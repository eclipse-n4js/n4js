/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.refactoring;

import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.xtext.ui.refactoring.impl.RenameElementProcessor;

import com.google.inject.Inject;

/**
 * We need a custom N4JS RenameElementProcessor for checking additional conditions.
 *
 */
@SuppressWarnings("restriction")
public class N4JSRenameElementProcessor extends RenameElementProcessor {

	@Inject
	private N4JSScopeProvider scopeProvider;

	// @Override
	// public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException,
	// OperationCanceledException {
	// if (this.getTargetElement() == null) {
	// return RefactoringStatus.createFatalErrorStatus("Renaming this element is not possible");
	// }
	// return super.checkInitialConditions(pm);
	// }

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor monitor, CheckConditionsContext context)
			throws CoreException, OperationCanceledException {
		String newName = this.getNewName();
		EObject targetElement = this.getTargetElement();
		// if (targetElement instanceof SyntaxRelatedTElement) {
		// targetElement = ((SyntaxRelatedTElement) targetElement).getAstElement();
		// }
		//
		// IScope scope = scopeProvider.getScope(targetElement, N4JSPackage.Literals.IDENTIFIER_REF__ID);
		// for (IEObjectDescription desc : scope.getAllElements()) {
		// if (desc.getName().toString().contains(newName)) {
		// System.out.println(desc);
		// }
		// }

		if (targetElement instanceof TMember) {
			TMember member = (TMember) targetElement;
			ContainerType<? extends TMember> container = member.getContainingType();
			if (container.getOwnedMembers().stream()
					.filter(m -> m != member && m.getName().equals(newName))
					.collect(Collectors.toList()).size() > 0) {
				return RefactoringStatus.createFatalErrorStatus("Name conflicts");
			}

		}
		return super.checkFinalConditions(monitor, context);
	}

}
