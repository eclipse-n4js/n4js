package org.eclipse.n4js.ui.refactoring;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.xtext.ui.refactoring.impl.DefaultRenameStrategy;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.SimpleAttributeResolver;

/*** Custom Rename strategy */

@SuppressWarnings("restriction")
public class N4JSRenameStrategy extends DefaultRenameStrategy {

	@Override
	public void applyDeclarationChange(String newName, ResourceSet resourceSet) {
		super.applyDeclarationChange(getNameAsValue(newName), resourceSet);
	}

	/** Extract the text region of the AST node that contains the name to be renamed */
	@Override
	protected ITextRegion getOriginalNameRegion(final EObject targetElement, EAttribute nameAttribute) {
		if (targetElement instanceof SyntaxRelatedTElement) {
			EObject nameElement = ((SyntaxRelatedTElement) targetElement).getAstElement();

			// PropertyNameOwner has a LiteralOrComputedPropertyName child node that contains the name to be renamed
			if (nameElement instanceof PropertyNameOwner) {
				nameElement = ((PropertyNameOwner) nameElement).getDeclaredName();
				EAttribute nAttribute = SimpleAttributeResolver.newResolver(String.class, "literalName")
						.getAttribute(nameElement);
				return this.getOriginalNameRegion(nameElement, nAttribute);
			}
		}
		return super.getOriginalNameRegion(targetElement, nameAttribute);
	}
}
