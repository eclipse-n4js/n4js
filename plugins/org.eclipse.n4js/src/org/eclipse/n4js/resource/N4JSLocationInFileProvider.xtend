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
package org.eclipse.n4js.resource

import java.util.Objects
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.n4JS.GenericDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.PropertyNameOwner
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TStructMember
import org.eclipse.n4js.ts.types.TStructMethod
import org.eclipse.n4js.ts.types.TStructuralType
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.utils.URIUtils
import org.eclipse.n4js.xtext.resource.XITextRegionWithLineInformation
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.DefaultLocationInFileProvider
import org.eclipse.xtext.resource.ILocationInFileProviderExtension
import org.eclipse.xtext.util.ITextRegion
import org.eclipse.xtext.resource.ILocationInFileProviderExtension.RegionDescription
import java.util.List
import org.eclipse.emf.ecore.EReference

/**
 * A location in file provider that is aware of inferred types. The location
 * of the referenced AST element is used instead.
 */
class N4JSLocationInFileProvider extends DefaultLocationInFileProvider {

	override getFullTextRegion(EObject element) {
		val srcObj = convertToSource(element);
		if (srcObj === null) {
			return null;
		}
		return super.getFullTextRegion(srcObj);
	}

	override getFullTextRegion(EObject owner, EStructuralFeature feature, int indexInList) {
		val srcObj = convertToSource(owner);
		if (srcObj === null) {
			return null;
		}
		return super.getFullTextRegion(srcObj, feature, indexInList);
	}

	override getSignificantTextRegion(EObject element) {
		if(element instanceof NamedImportSpecifier) {
			if(!element.isDefaultImport()) {
				if(element.getAlias() !== null) {
					return super.getSignificantTextRegion(element, N4JSPackage.eINSTANCE.getNamedImportSpecifier_Alias(), -1);
				}
			}
		} else if(element instanceof NamespaceImportSpecifier) {
			if (element.getAlias() !== null) {
				return super.getSignificantTextRegion(element, N4JSPackage.eINSTANCE.getNamespaceImportSpecifier_Alias(), -1);
			}
		}
		val srcObj = convertToSource(element);
		if (srcObj === null) {
			return null;
		}
		return super.getSignificantTextRegion(srcObj);
	}

	override getSignificantTextRegion(EObject owner, EStructuralFeature feature, int indexInList) {
		val srcObj = convertToSource(owner);
		if (srcObj === null) {
			return null;
		}
		return super.getSignificantTextRegion(srcObj, feature, indexInList);
	}

	override getTextRegion(EObject object, EStructuralFeature feature, int indexInList,
		ILocationInFileProviderExtension.RegionDescription query) {
			
		val srcObj = convertToSource(object);
		if (srcObj === null) {
			return null;
		}
		return super.getTextRegion(srcObj, feature, indexInList, query);
	}

	override getTextRegion(EObject object, ILocationInFileProviderExtension.RegionDescription query) {
		val srcObj = convertToSource(object);
		if (srcObj === null) {
			return null;
		}
		return super.getTextRegion(srcObj, query);
	}
	
	override protected ITextRegion doGetTextRegion(EObject obj, /* @NonNull */ RegionDescription query) {
		if (Objects.equals(N4JSGlobals.DTS_FILE_EXTENSION, URIUtils.fileExtension(obj.eResource?.URI))) {
			for (adapter : obj.eAdapters) {
				if (adapter instanceof XITextRegionWithLineInformation) {
					return toZeroBasedRegion(adapter);
				}
			}
		} else if (obj instanceof TMember && !(obj as TMember).constituentMembers.empty) {
			val tMember = obj as TMember;
			val fst = tMember.constituentMembers.get(0);
			return super.doGetTextRegion(fst.astElement, query);
		} else if (obj instanceof PropertyNameValuePair && (obj as PropertyNameValuePair).property !== null) {
			return super.getFullTextRegion(obj, N4JSPackage.eINSTANCE.propertyNameValuePair_Property, 0);
		} else {
			return super.doGetTextRegion(obj, query);
		}
	}
	

	// TODO use N4JSASTUtils#getCorrespondingASTNode() instead, if possible
	def public EObject convertToSource(EObject element) {
		if (element === null || element.eIsProxy)
			return null;
		switch (element) {
			TypeVariable: {
				val parentAST = convertToSource(element.eContainer);
				if(parentAST instanceof GenericDeclaration || parentAST instanceof TStructMethod || parentAST instanceof FunctionTypeExpression) {
					val typeVarName = element.name;
					if(typeVarName!==null && typeVarName.trim.length>0) {
						val correspondingTypeVarInAST = switch parentAST {
							GenericDeclaration: 	parentAST.typeVars.findFirst[name==typeVarName]
							TStructMethod:			parentAST.typeVars.findFirst[name==typeVarName]
							FunctionTypeExpression:	parentAST.ownedTypeVars.findFirst[name==typeVarName]
						};
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
			TMember case element.composed:
				element
			TStructuralType case element.name === null && element.astElement === null: {
				val parent = element.eContainer
				return convertToSource(parent)
			}
			SyntaxRelatedTElement: {
				if (element.astElement === null) {
					if (NodeModelUtils.getNode(element) !== null) {
						return element;
					}			
					throw new IllegalStateException()
				}
				element.astElement
			}
			default:
				element
		}
	}

	override protected EStructuralFeature getIdentifierFeature(EObject obj) {
		if(obj instanceof PropertyNameOwner) {
			return N4JSPackage.eINSTANCE.propertyNameOwner_DeclaredName;
		}
		return super.getIdentifierFeature(obj);
	}
	
	
	override protected ITextRegion getLocationOfContainmentReference(EObject owner, EReference feature,
			int indexInList, RegionDescription query) {
				
		var EObject referencedElement = null;
		if (feature.isMany()) {
			val List<?> values = owner.eGet(feature) as List<?>;
			if (indexInList >= values.size()) {
				referencedElement = owner;
			} else if (indexInList >= 0) {
				referencedElement = values.get(indexInList) as EObject;
			}
		} else {
			referencedElement = owner.eGet(feature) as EObject;
		}
		return getTextRegion(referencedElement, query);
	}
}
