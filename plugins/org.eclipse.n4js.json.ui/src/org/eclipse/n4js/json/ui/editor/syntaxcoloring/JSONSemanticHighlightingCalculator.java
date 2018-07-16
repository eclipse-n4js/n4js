package org.eclipse.n4js.json.ui.editor.syntaxcoloring;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.services.JSONGrammarAccess;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;

/**
 * JSON specific semantic highlighting.
 */
public class JSONSemanticHighlightingCalculator implements ISemanticHighlightingCalculator {

	@Inject
	private JSONGrammarAccess grammarAccess;

	@Override
	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor,
			CancelIndicator cancelIndicator) {
		if (resource == null || resource.getParseResult() == null) {
			// skip invalid resources
			return;
		}

		// obtain root node
		INode root = resource.getParseResult().getRootNode();

		for (INode node : root.getAsTreeIterable()) {
			EObject grammarElement = node.getGrammarElement();

			// special handling of the names of name-value-pairs in order to differentiate
			// keys and values
			if (grammarElement instanceof RuleCall && grammarElement.eContainer() instanceof Assignment
					&& ((RuleCall) grammarElement).getRule() == grammarAccess.getSTRINGRule()) {
				final Assignment assignment = ((Assignment) grammarElement.eContainer());

				// if the STRING value is assigned to the feature 'name' of NameValuePair
				if (assignment.getFeature().equals(JSONPackage.Literals.NAME_VALUE_PAIR__NAME.getName())) {
					// enable PROPERTY_NAME highlighting
					acceptor.addPosition(node.getOffset(), node.getLength(),
							JSONHighlightingConfiguration.PROPERTY_NAME_ID);
				} else {
					// otherwise enable string literal highlighting
					acceptor.addPosition(node.getOffset(), node.getLength(), JSONHighlightingConfiguration.STRING_ID);
				}
			}
		}
	}

}
