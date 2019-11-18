/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.editor.autoedit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ui.editor.N4JSDocument;
import org.eclipse.n4js.ui.organize.imports.XtextResourceUtils;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.autoedit.CommandInfo;
import org.eclipse.xtext.ui.editor.autoedit.MultiLineTerminalsEditStrategy;

public class JSDocEditStrategy extends MultiLineTerminalsEditStrategy {
	private final String indentationString;
	private final String paramString = "@param ";
	private final String returnString = "@return ";

	public JSDocEditStrategy(String leftTerminal, String indentationString, String rightTerminal) {
		super(leftTerminal, indentationString, rightTerminal, false);
		this.indentationString = indentationString;
	}

	/**
	 * Expects the cursor to be in the same line as the start terminal puts any text between start terminal and cursor
	 * into a separate newline before the cursor. puts any text between cursor and end terminal into a separate newline
	 * after the cursor. puts the closing terminal into a separate line at the end. adds a closing terminal if not
	 * existent.
	 */
	@Override
	protected CommandInfo handleCursorInFirstLine(IDocument document, DocumentCommand command, IRegion startTerminal,
			IRegion stopTerminal) throws BadLocationException {
		CommandInfo newC = new CommandInfo();
		newC.isChange = true;
		List<String> retAndfparNames = null;
		if (document instanceof N4JSDocument) {
			// index 0: if set to "void", the method has no return value; fpars start at index 1
			retAndfparNames = ((N4JSDocument) document).tryReadOnly((xtextResource) -> {
				Script script = XtextResourceUtils.getScript(xtextResource);
				ILeafNode node = NodeModelUtils.findLeafNodeAtOffset(NodeModelUtils.findActualNodeFor(script),
						startTerminal.getOffset());
				EObject astElement = node.getSemanticElement();
				List<String> retAndFPars = new ArrayList<>();
				if (astElement instanceof N4MethodDeclaration) {
					N4MethodDeclaration methodDecl = (N4MethodDeclaration) astElement;
					if (methodDecl.getReturnTypeRef() != null) { // TODO evtl. void!
						retAndFPars.add(methodDecl.getReturnTypeRef().getDeclaredType().getName());
					} else {
						retAndFPars.add("void");
					}
					for (FormalParameter fpar : methodDecl.getFpars()) {
						retAndFPars.add(fpar.getName());
					}
				}
				return retAndFPars;
			});
		}

		String paramName = retAndfparNames.get(1);
		String returnValue = retAndfparNames.get(0);
		newC.offset = command.offset;
		newC.text += command.text + indentationString + command.text + indentationString + paramString + paramName
				+ command.text
				+ indentationString + returnString + returnValue;
		newC.cursorOffset = command.offset + newC.text.length();
		if (stopTerminal == null && atEndOfLineInput(document, command.offset)) {
			newC.text += command.text + getRightTerminal();
		}
		if (stopTerminal != null && stopTerminal.getOffset() >= command.offset
				&& util.isSameLine(document, stopTerminal.getOffset(), command.offset)) {
			String string = document.get(command.offset, stopTerminal.getOffset() - command.offset);
			if (string.trim().length() > 0)
				newC.text += string.trim();
			newC.text += command.text;
			newC.length += string.length();
		}
		return newC;
	}
}
