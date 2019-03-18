package org.eclipse.n4js.ui.refactoring;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.utils.languages.N4LanguageUtils;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.service.GrammarProvider;
import org.eclipse.xtext.ui.refactoring.IRefactoringUpdateAcceptor;
import org.eclipse.xtext.ui.refactoring.impl.DefaultRenameStrategy;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/*** Custom Rename strategy */

@SuppressWarnings("restriction")
public class N4JSRenameStrategy extends DefaultRenameStrategy {

	/** Target element to be renamed */
	protected EObject targetElement;

	@Inject
	private GrammarProvider grammarProvider;

	private final List<URI> targetElementNewURIs = new ArrayList<>();

	/**
	 * We override this method to store the reference to targetElement
	 */
	@Override
	public boolean initialize(@SuppressWarnings("hiding") EObject targetElement, IRenameElementContext context) {
		this.targetElement = targetElement;
		return super.initialize(targetElement, context);
	}

	/**
	 * Override this method to handle composed element when creating text edits for definitions
	 */
	@Override
	public void createDeclarationUpdates(String newName, ResourceSet resourceSet,
			IRefactoringUpdateAcceptor updateAcceptor) {

		// Special handling for composed element definitions of a composed element
		if (TypeUtils.isComposedTElement(targetElement)) {
			List<TMember> constituentMembers = ((TMember) targetElement).getConstituentMembers();
			for (TMember constituentMember : constituentMembers) {
				String text = newName;
				EAttribute att = getNameAttribute(constituentMember);

				ITextRegion nameRegion = getOriginalNameRegion(constituentMember, att);
				ReplaceEdit replaceEdit = new ReplaceEdit(nameRegion.getOffset(), nameRegion.getLength(), text);
				updateAcceptor.accept(EcoreUtil.getURI(constituentMember).trimFragment(),
						replaceEdit);
			}
		} else {
			updateAcceptor.accept(getTargetElementOriginalURI().trimFragment(), getDeclarationTextEdit(newName));
		}
	}

	/**
	 * Override this method to handle composed element when applying declaration changes to AST/TModule
	 */
	@Override
	public void applyDeclarationChange(String newName, ResourceSet resourceSet) {
		if (TypeUtils.isComposedTElement(targetElement)) {
			targetElementNewURIs.clear();
			List<TMember> constituentMembers = ((TMember) targetElement).getConstituentMembers();
			for (TMember constituentMember : constituentMembers) {
				URI constituentMemberURI = EcoreUtil.getURI(constituentMember);
				EObject renamedElement = setName(constituentMemberURI, newName, resourceSet);
				targetElementNewURIs.add(EcoreUtil.getURI(renamedElement));
			}
		}
		super.applyDeclarationChange(getNameAsValue(newName), resourceSet);
	}

	@Override
	public void revertDeclarationChange(ResourceSet resourceSet) {
		if ((targetElement instanceof TMember) && ((TMember) targetElement).isComposed()) {
			for (URI targetElementNewURI : targetElementNewURIs) {
				setName(targetElementNewURI, getOriginalName(), resourceSet);
			}
		}
		super.revertDeclarationChange(resourceSet);
	}

	/**
	 * Override this method to handle the case in which PropertyNameOwner has a LiteralOrComputedPropertyName child node
	 * that contains the name to be renamed
	 */
	@Override
	protected ITextRegion getOriginalNameRegion(@SuppressWarnings("hiding") final EObject targetElement,
			EAttribute nameAttribute) {
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

	@Override
	public RefactoringStatus validateNewName(String newName) {
		// N4JS already contains N4JSX grammar
		IParser parser = N4LanguageUtils.getServiceForContext("n4js", IParser.class).get();
		Grammar grammar = this.internalFindGrammar();
		ParserRule parserRule = (ParserRule) GrammarUtil.findRuleForName(grammar,
				"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");

		// Parse the new name using the IdentifierName rule of the parser
		IParseResult parseResult = parser.parse(parserRule, new StringReader(newName));
		if (parseResult.hasSyntaxErrors()) {
			String parseErrorMessages = Joiner.on("\n").join(Iterables.transform(parseResult.getSyntaxErrors(),
					(node) -> node.getSyntaxErrorMessage().getMessage()));
			return RefactoringStatus.createFatalErrorStatus(parseErrorMessages);
		}

		RefactoringStatus status = new RefactoringStatus();
		return status;
	}

	/**
	 * Returns the TypeExpressions grammar
	 */
	protected Grammar internalFindGrammar() {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.n4js.ts.TypeExpressions".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
}
