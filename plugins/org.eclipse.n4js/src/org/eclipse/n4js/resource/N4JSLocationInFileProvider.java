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
package org.eclipse.n4js.resource;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.resource.XITextRegionWithLineInformation;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.DefaultLocationInFileProvider;
import org.eclipse.xtext.resource.ILocationInFileProviderExtension;
import org.eclipse.xtext.util.ITextRegion;

/**
 * A location in file provider that is aware of inferred types. The location of the referenced AST element is used
 * instead.
 */
public class N4JSLocationInFileProvider extends DefaultLocationInFileProvider {

	@Override
	public ITextRegion getFullTextRegion(EObject element) {
		EObject srcObj = convertToSource(element);
		if (srcObj == null) {
			return null;
		}
		return super.getFullTextRegion(srcObj);
	}

	@Override
	public ITextRegion getFullTextRegion(EObject owner, EStructuralFeature feature, int indexInList) {
		EObject srcObj = convertToSource(owner);
		if (srcObj == null) {
			return null;
		}
		return super.getFullTextRegion(srcObj, feature, indexInList);
	}

	@Override
	public ITextRegion getSignificantTextRegion(EObject element) {
		if (element instanceof NamedImportSpecifier) {
			NamedImportSpecifier nis = (NamedImportSpecifier) element;
			if (!nis.isDefaultImport()) {
				if (nis.getAlias() != null) {
					return super.getSignificantTextRegion(element,
							N4JSPackage.eINSTANCE.getNamedImportSpecifier_Alias(), -1);
				}
			}
		} else if (element instanceof NamespaceImportSpecifier) {
			NamespaceImportSpecifier nis = (NamespaceImportSpecifier) element;
			if (nis.getAlias() != null) {
				return super.getSignificantTextRegion(element,
						N4JSPackage.eINSTANCE.getNamespaceImportSpecifier_Alias(), -1);
			}
		}
		EObject srcObj = convertToSource(element);
		if (srcObj == null) {
			return null;
		}
		return super.getSignificantTextRegion(srcObj);
	}

	@Override
	public ITextRegion getSignificantTextRegion(EObject owner, EStructuralFeature feature, int indexInList) {
		EObject srcObj = convertToSource(owner);
		if (srcObj == null) {
			return null;
		}
		return super.getSignificantTextRegion(srcObj, feature, indexInList);
	}

	@Override
	public ITextRegion getTextRegion(EObject object, EStructuralFeature feature, int indexInList,
			ILocationInFileProviderExtension.RegionDescription query) {

		EObject srcObj = convertToSource(object);
		if (srcObj == null) {
			return null;
		}
		return super.getTextRegion(srcObj, feature, indexInList, query);
	}

	@Override
	public ITextRegion getTextRegion(EObject object, ILocationInFileProviderExtension.RegionDescription query) {
		EObject srcObj = convertToSource(object);
		if (srcObj == null) {
			return null;
		}
		return super.getTextRegion(srcObj, query);
	}

	@Override
	protected ITextRegion doGetTextRegion(EObject obj, /* @NonNull */ RegionDescription query) {
		if (Objects.equals(N4JSGlobals.DTS_FILE_EXTENSION,
				URIUtils.fileExtension(obj.eResource() == null ? null : obj.eResource().getURI()))) {
			for (Adapter adapter : obj.eAdapters()) {
				if (adapter instanceof XITextRegionWithLineInformation) {
					return toZeroBasedRegion((XITextRegionWithLineInformation) adapter);
				}
			}
		} else if (obj instanceof TMember && !((TMember) obj).getConstituentMembers().isEmpty()) {
			TMember tMember = (TMember) obj;
			TMember fst = tMember.getConstituentMembers().get(0);
			return super.doGetTextRegion(fst.getAstElement(), query);
		} else if (obj instanceof PropertyNameValuePair && ((PropertyNameValuePair) obj).getProperty() != null) {
			return super.getFullTextRegion(obj, N4JSPackage.eINSTANCE.getPropertyNameValuePair_Property(), 0);
		}
		return super.doGetTextRegion(obj, query);
	}

	// TODO use N4JSASTUtils#getCorrespondingASTNode() instead, if possible
	/***/
	public EObject convertToSource(EObject element) {
		if (element == null || element.eIsProxy()) {
			return null;
		}

		EObject astElem = (element instanceof SyntaxRelatedTElement)
				? ((SyntaxRelatedTElement) element).getAstElement()
				: null;

		if (element instanceof TypeVariable) {
			EObject parentAST = convertToSource(element.eContainer());
			if (parentAST instanceof GenericDeclaration || parentAST instanceof TStructMethod
					|| parentAST instanceof FunctionTypeExpression) {
				String typeVarName = ((TypeVariable) element).getName();
				if (typeVarName != null && typeVarName.trim().length() > 0) {
					EObject correspondingTypeVarInAST = null;
					if (parentAST instanceof GenericDeclaration) {
						correspondingTypeVarInAST = findFirst(((GenericDeclaration) parentAST)
								.getTypeVars(), tv -> Objects.equals(tv.getName(), typeVarName));
					} else if (parentAST instanceof TStructMethod) {
						correspondingTypeVarInAST = findFirst(((TStructMethod) parentAST)
								.getTypeVars(), tv -> Objects.equals(tv.getName(), typeVarName));
					} else if (parentAST instanceof FunctionTypeExpression) {
						correspondingTypeVarInAST = findFirst(((FunctionTypeExpression) parentAST)
								.getOwnedTypeVars(), tv -> Objects.equals(tv.getName(), typeVarName));
					}
					if (correspondingTypeVarInAST != null) {
						return correspondingTypeVarInAST;
					}
				}
			}
			return element;

		} else if (element instanceof TFormalParameter && astElem == null) {
			return element;
		} else if (element instanceof TStructMember && astElem == null) {
			return element;
		} else if (element instanceof TMember && ((TMember) element).isComposed()) {
			return element;
		} else if (element instanceof TStructuralType) {
			if (((TStructuralType) element).getName() == null && astElem == null) {
				EObject parent = element.eContainer();
				return convertToSource(parent);
			}
		} else if (element instanceof SyntaxRelatedTElement) {
			if (astElem == null) {
				if (NodeModelUtils.getNode(element) != null) {
					return element;
				}
				throw new IllegalStateException();
			}
			return astElem;
		}
		return element;
	}

	@Override
	protected EStructuralFeature getIdentifierFeature(EObject obj) {
		if (obj instanceof PropertyNameOwner) {
			return N4JSPackage.eINSTANCE.getPropertyNameOwner_DeclaredName();
		}
		return super.getIdentifierFeature(obj);
	}

	@Override
	protected ITextRegion getLocationOfContainmentReference(EObject owner, EReference feature,
			int indexInList, RegionDescription query) {

		EObject referencedElement = null;
		if (feature.isMany()) {
			List<?> values = (List<?>) owner.eGet(feature);
			if (indexInList >= values.size()) {
				referencedElement = owner;
			} else if (indexInList >= 0) {
				referencedElement = (EObject) values.get(indexInList);
			}
		} else {
			referencedElement = (EObject) owner.eGet(feature);
		}
		return getTextRegion(referencedElement, query);
	}
}
