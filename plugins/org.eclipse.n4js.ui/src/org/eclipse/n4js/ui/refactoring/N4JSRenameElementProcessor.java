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

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.ui.refactoring.impl.RenameElementProcessor;

import com.google.inject.Inject;

/**
 * We check additional conditions of rename refactoring in this class.
 *
 */
@SuppressWarnings("restriction")
public class N4JSRenameElementProcessor extends RenameElementProcessor {

	@Inject
	private N4JSScopeProvider scopeProvider;

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (this.getTargetElement() == null) {
			return RefactoringStatus.createFatalErrorStatus("Rename an element of external library is not allowed.");
		}
		return super.checkInitialConditions(pm);
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor monitor, CheckConditionsContext context)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		status.merge(super.checkFinalConditions(monitor, context));

		if (status.hasFatalError()) {
			return status;
		}

		String newName = this.getNewName();
		EObject targetElement = this.getTargetElement();

		status.merge(checkDuplicateName(targetElement, newName));
		return status;
	}

	private RefactoringStatus checkDuplicateName(EObject context, String newName) {
		// Check name conflicts of TMembers
		if (context instanceof TMember) {
			TMember member = (TMember) context;
			return checkDuplicateMember(member, newName);
		}

		if (context instanceof FormalParameter) {
			FormalParameter fpar = (FormalParameter) context;
			FunctionDefinition method = (FunctionDefinition) fpar.eContainer();
			return checkDuplicateFormalParam(fpar, method.getFpars(), newName);
		}

		// Check name conflicts in variable environment scope
		RefactoringStatus status = new RefactoringStatus();
		EObject astContext = null;
		if (context instanceof SyntaxRelatedTElement) {
			astContext = ((SyntaxRelatedTElement) context).getAstElement();
		} else {
			astContext = context;
		}

		IScope scope = scopeProvider.getScopeForContentAssist(astContext,
				N4JSPackage.Literals.IDENTIFIER_REF__ID);
		for (IEObjectDescription desc : scope.getAllElements()) {
			if (desc.getName().toString().equals(newName)) {
				status.merge(RefactoringStatus.createFatalErrorStatus(
						"Problem in " + trimPlatformPart(desc.getEObjectURI().trimFragment().toString())
								+ ": Another element in the same scope with name '"
								+ newName + "' already exists"));
			}
		}

		return status;
	}

	/**
	 * Check duplicate formal parameters
	 */
	private RefactoringStatus checkDuplicateFormalParam(FormalParameter fpar, List<FormalParameter> fpars,
			String newName) {
		List<FormalParameter> fparsWithName = fpars.stream().filter(fp -> fp != fpar).collect(Collectors.toList());
		if (fparsWithName.size() > 0) {
			return RefactoringStatus
					.createFatalErrorStatus("Another formal parameter with name '" + newName + "' already exists.");
		}
		return new RefactoringStatus();
	}

	private RefactoringStatus checkDuplicateMember(TMember member, String newName) {
		ContainerType<? extends TMember> container = member.getContainingType();
		// TODO Deal with composed member
		if (container != null) {
			MemberCollector memberCollector = this.containerTypesHelper.fromContext(container);
			List<TMember> membersWithName = memberCollector.members(container).stream()
					.filter(m -> m != member && m.getName().equals(newName))
					.collect(Collectors.toList());

			if (membersWithName.size() > 0) {
				return RefactoringStatus
						.createFatalErrorStatus(
								"Problem in " + trimPlatformPart(member.eResource().getURI().toString())
										+ ": Another member with name '"
										+ newName + "' already exists");
			}
		}

		return new RefactoringStatus();
	}

	private String trimPlatformPart(String URI) {
		String result = URI.replaceFirst(Pattern.quote("platform:/resource/"), "");
		return result;
	}
}
