package org.eclipse.n4js.ui.refactoring;

import java.io.StringReader;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.utils.languages.N4LanguageUtils;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.service.GrammarProvider;
import org.eclipse.xtext.ui.refactoring.impl.DefaultRenameStrategy;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.inject.Inject;

/*** Custom Rename strategy */

@SuppressWarnings("restriction")
public class N4JSRenameStrategy extends DefaultRenameStrategy {

	protected EObject targetElement;

	@Inject
	private GrammarProvider grammarProvider;

	@Override
	public void applyDeclarationChange(String newName, ResourceSet resourceSet) {
		super.applyDeclarationChange(getNameAsValue(newName), resourceSet);
	}

	/** Extract the text region of the AST node that contains the name to be renamed */
	@Override
	protected ITextRegion getOriginalNameRegion(final EObject targetElement, EAttribute nameAttribute) {
		this.targetElement = targetElement;

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
		// TODO: Handle n4jsx extension
		IParser parser = N4LanguageUtils.getServiceForContext("n4js", IParser.class).get();
		Grammar grammar = this.internalFindGrammar(grammarProvider);

		ParserRule parserRule = (ParserRule) GrammarUtil.findRuleForName(grammar,
				"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");

		IParseResult parseResult = parser.parse(parserRule, new StringReader(newName));
		if (parseResult.hasSyntaxErrors()) {
			StringBuilder sb = new StringBuilder();
			for (INode node : parseResult.getSyntaxErrors()) {
				sb.append(node.getSyntaxErrorMessage().getMessage() + "\n");
			}
			return RefactoringStatus.createFatalErrorStatus(sb.toString());
		}

		RefactoringStatus status = new RefactoringStatus();
		return status;
	}

	/**
	 * Returns the TypeExpressions grammar
	 */
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
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
