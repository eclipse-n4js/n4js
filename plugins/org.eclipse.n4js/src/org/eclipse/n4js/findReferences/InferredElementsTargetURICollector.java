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
package org.eclipse.n4js.findReferences;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.utils.StaticPolyfillHelper;
import org.eclipse.xtext.findReferences.TargetURICollector;
import org.eclipse.xtext.findReferences.TargetURIs;

import com.google.inject.Inject;

/**
 * Collector of target URIs when find references is triggered. Collects the URI of the given object and of all objects
 * that have been inferred from that.
 */
@SuppressWarnings("restriction")
public class InferredElementsTargetURICollector extends TargetURICollector {

	@Inject
	private StaticPolyfillHelper staticPolyfillHelper;

	@Override
	protected void doAdd(EObject primaryTarget, TargetURIs targetURIsAddHere) {
		Resource resource = primaryTarget.eResource();
		// If the target is not contained in a resource, we cannot do anything but return.
		if (resource == null)
			return;
		EcoreUtil.resolveAll(primaryTarget.eResource());

		if (primaryTarget instanceof LiteralOrComputedPropertyName) {
			primaryTarget = primaryTarget.eContainer();
		}

		// Special handling for composed members and TStructMember
		if (primaryTarget instanceof TMember && ((TMember) primaryTarget).isComposed()) {
			// In case of composed member, add the constituent members instead.
			List<TMember> constituentMembers = ((TMember) primaryTarget).getConstituentMembers();
			for (TMember constituentMember : constituentMembers) {
				super.doAdd(constituentMember, targetURIsAddHere);
			}
		} else {
			if (primaryTarget instanceof TStructMember) {
				TStructMember crossRefStructMember = ((TStructMember) primaryTarget).getDefinedMember();
				if (crossRefStructMember != null)
					// If this TStructMember is an AST, also add the defined member located in the TModule
					super.doAdd(((TStructMember) primaryTarget).getDefinedMember(), targetURIsAddHere);
			}
			super.doAdd(primaryTarget, targetURIsAddHere);
		}

		EObject targetInTypeModel = N4JSASTUtils.getCorrespondingTypeModelElement(primaryTarget);
		if (targetInTypeModel != null && targetInTypeModel != primaryTarget) {
			super.doAdd(targetInTypeModel, targetURIsAddHere);
		}
		if (targetInTypeModel instanceof TMember) {
			TMember filledMember = getPolyfilledMember((TMember) targetInTypeModel);
			if (filledMember != null) {
				super.doAdd(filledMember, targetURIsAddHere);
			}
		}
	}

	private TMember getPolyfilledMember(TMember tMember) {
		// If this member is replaced by a polyfill's member, we collect that polyfill's member as well.
		// Note that we enable this for static polyfill. We may want to extend this to runtime polyfill as well.
		if (tMember != null && tMember.getContainingModule().isStaticPolyfillAware()) {
			TClassifier tClassFilled = (TClassifier) tMember.getContainingType();
			N4ClassDeclaration filler = staticPolyfillHelper.getStaticPolyfill(tClassFilled);
			// Search for the polyfill's member
			String name = tMember.getName();
			EClass memberKind;
			if (tMember instanceof TGetter) {
				memberKind = N4JSPackage.eINSTANCE.getGetterDeclaration();
			} else if (tMember instanceof TSetter) {
				memberKind = N4JSPackage.eINSTANCE.getSetterDeclaration();
			} else {
				memberKind = N4JSPackage.eINSTANCE.getN4MemberDeclaration();
			}
			Optional<N4MemberDeclaration> fillerMember = filler.getOwnedMembers().stream()
					.filter(mem -> memberKind.isInstance(mem) && mem.getName().equals(name))
					.findFirst();
			if (fillerMember.isPresent()) {
				EObject fillerMemberInTypeModel = N4JSASTUtils.getCorrespondingTypeModelElement(fillerMember.get());
				if (fillerMemberInTypeModel instanceof TMember) {
					return (TMember) fillerMemberInTypeModel;
				}
			}
		}
		return null;
	}
}
