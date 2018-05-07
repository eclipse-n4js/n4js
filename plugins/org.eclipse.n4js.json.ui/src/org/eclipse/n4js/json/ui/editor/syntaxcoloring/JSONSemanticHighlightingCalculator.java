package org.eclipse.n4js.json.ui.editor.syntaxcoloring;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;

/**
 * JSON specific semantic highlighting.
 */
public class JSONSemanticHighlightingCalculator implements ISemanticHighlightingCalculator {

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
			
			// special handling of the names of name-value-pairs in order to differentiate keys and values
			if (grammarElement instanceof RuleCall && grammarElement.eContainer() instanceof Assignment) {
				final Assignment assignment = ((Assignment) grammarElement.eContainer());
				if (assignment.getFeature().equals("name")) {
					acceptor.addPosition(node.getOffset(), node.getLength(), JSONHighlightingConfiguration.PROPERTY_NAME_ID);
				}
			}
		}
	}

}
