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
package org.eclipse.n4js.ui.editor

import javax.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.n4js.n4JS.GenericDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.PropertyNameOwner
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.types.ComposedMemberCache
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TStructMember
import org.eclipse.n4js.ts.types.TStructMethod
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.xtext.resource.DefaultLocationInFileProvider
import org.eclipse.xtext.resource.ILocationInFileProviderExtension

/**
 */
class N4JSLocationInFileProvider extends DefaultLocationInFileProvider {

	@Inject
	private ChooseConstituentMemberHelper chooseConstituentMemberHelper;

	private EObject cachedSource;

	/** Return the cached real source. Needed to open the right editor when Cmd + click. */
	def EObject getCachedSource() {
		return cachedSource;
	}

	override getFullTextRegion(EObject element) {
		cachedSource = convertToSource(element);
		return super.getFullTextRegion(cachedSource);
	}

	override getFullTextRegion(EObject owner, EStructuralFeature feature, int indexInList) {
		cachedSource = convertToSource(owner);
		return super.getFullTextRegion(cachedSource, feature, indexInList);
	}

	override getSignificantTextRegion(EObject element) {
		cachedSource = convertToSource(element);
		return super.getSignificantTextRegion(cachedSource);
	}

	override getSignificantTextRegion(EObject owner, EStructuralFeature feature, int indexInList) {
		cachedSource = convertToSource(owner);
		return super.getSignificantTextRegion(cachedSource, feature, indexInList);
	}

	override getTextRegion(EObject object, EStructuralFeature feature, int indexInList,
		ILocationInFileProviderExtension.RegionDescription query) {
		cachedSource = convertToSource(object);
		return super.getTextRegion(cachedSource, feature, indexInList, query);
	}

	override getTextRegion(EObject object, ILocationInFileProviderExtension.RegionDescription query) {
		cachedSource = convertToSource(object);
		return super.getTextRegion(cachedSource, query);
	}

	def protected EObject convertToSource(EObject element) {
		if (element === null)
			return null;
		val ret = switch (element) {
			TypeVariable: {
				val parentAST = convertToSource(element.eContainer);
				if(parentAST instanceof GenericDeclaration || parentAST instanceof TStructMethod || parentAST instanceof FunctionTypeExpression) {
					val typeVarName = element.name;
					if(typeVarName!==null && typeVarName.trim.length>0) {
						val typeVars = switch parentAST {
							GenericDeclaration: 	parentAST.typeVars
							TStructMethod:			parentAST.typeVars
							FunctionTypeExpression:	parentAST.ownedTypeVars
						};
						val correspondingTypeVarInAST = typeVars.findFirst[name==typeVarName];
						if(correspondingTypeVarInAST!==null)
							return correspondingTypeVarInAST;
					}
				}
				return element;
			}
			TFormalParameter case element.astElement === null:
				element
			TStructMember case element.astElement === null:
				element
			TMember case element.eContainer instanceof ComposedMemberCache: {
				// In case of composed member, the user can choose the constituent member she wants to jump to.
				val chosenMember = chooseMember(element);
				return chosenMember;
			}
			SyntaxRelatedTElement: {
				if (element.astElement === null)
					throw new IllegalStateException()
				element.astElement
			}
			default:
				element
		}
		ret;
	}

	def private EObject chooseMember (TMember member) {
		if (member.constituentMembers.size < 2) {
			 return member.constituentMembers.get(0).memberInASTOrTModule
		} else
		{
			val chosenMember = chooseConstituentMemberHelper.chooseConstituentMemberDialogIfRequired(member, member.constituentMembers.map[memberInASTOrTModule]) 
			return chosenMember;
		}
	}

	def private EObject getMemberInASTOrTModule(TMember member) {
		if (member.astElement instanceof N4MemberDeclaration)
			member.astElement as N4MemberDeclaration
		else member
	}

	override protected EStructuralFeature getIdentifierFeature(EObject obj) {
		if(obj instanceof PropertyNameOwner) {
			return N4JSPackage.eINSTANCE.propertyNameOwner_DeclaredName;
		}
		return super.getIdentifierFeature(obj);
	}
}
