/***Copyright(c)2019 NumberFour AG.*All rights reserved.This program and the accompanying materials*are made available under the terms of the Eclipse Public License v1.0*which accompanies this distribution,and is available at*http://www.eclipse.org/legal/epl-v10.html
**Contributors:*NumberFour AG-Initial API and implementation*/
package org.eclipse.n4js.ui.refactoring;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.xtext.ui.refactoring.impl.DefaultRenameStrategy;

/*** Custom Rename strategy */

@SuppressWarnings("restriction")
public class N4JSRenameStrategy extends DefaultRenameStrategy {

	@Override
	public void applyDeclarationChange(String newName, ResourceSet resourceSet) {
		org.eclipse.emf.common.util.URI targetElementOriginalURI = getTargetElementOriginalURI();
		EObject targetElement = resourceSet.getEObject(targetElementOriginalURI, false);

		if (targetElement instanceof SyntaxRelatedTElement) {
			EObject astElement = ((SyntaxRelatedTElement) targetElement).getAstElement();
			org.eclipse.emf.common.util.URI astURI = EcoreUtil.getURI(astElement);

			EAttribute astnameAttribute = getNameAttribute(astElement);
			astElement.eSet(astnameAttribute, newName);
			System.out.println(astElement);
		}
		super.applyDeclarationChange(getNameAsValue(newName), resourceSet);
	}
}
