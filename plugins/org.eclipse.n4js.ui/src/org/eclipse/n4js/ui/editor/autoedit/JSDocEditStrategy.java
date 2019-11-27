/**
 * Copyright (c) 2019 HAW Hamburg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Rodrigo Ehlers
 *   Thanh Tung Ngo
 *   Eduard Veit
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
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.impl.VoidTypeImpl;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.ui.editor.N4JSDocument;
import org.eclipse.n4js.ui.organize.imports.XtextResourceUtils;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.autoedit.CommandInfo;
import org.eclipse.xtext.ui.editor.autoedit.MultiLineTerminalsEditStrategy;

/**
 * AutoEdit strategy which auto generates JSDoc comments.
 */
public class JSDocEditStrategy extends MultiLineTerminalsEditStrategy {
	private static final String OPENING_STR = "/**";
	private static final String INDENTATION_STR = " * ";
	private static final String CLOSING_STR = " */";
	private static final String PARAM_STR = "@param ";
	private static final String RETURN_STR = "@return ";

	/**
	 * Call super with specified opening, closing and indentation string. JSDoc strategy is not nested.
	 */
	public JSDocEditStrategy() {
		super(OPENING_STR, INDENTATION_STR, CLOSING_STR, false);
	}

	/**
	 * Expects the cursor to be in the same line as the start terminal puts any text between start terminal and cursor
	 * into a separate newline before the cursor. puts any text between cursor and end terminal into a separate newline
	 * after the cursor. puts the closing terminal into a separate line at the end. adds a closing terminal if not
	 * existent. If the next astElement is a method with parameters or return the JSDoc-tags will be added as an
	 * addition.
	 */
	@Override
	protected CommandInfo handleCursorInFirstLine(IDocument document, DocumentCommand command, IRegion startTerminal,
			IRegion stopTerminal) throws BadLocationException {
		CommandInfo newC = new CommandInfo();
		List<String> returnTypeAndParameterNames = getReturnTypeAndParameterNames(document, startTerminal);
		String paramString = "";
		String returnString = "";
		if ((returnTypeAndParameterNames.size() > 0) && returnTypeAndParameterNames.get(0).equals("return")) {
			returnString = INDENTATION_STR + RETURN_STR + command.text;
		}
		if (returnTypeAndParameterNames.size() > 1) {
			for (int i = 1; i < returnTypeAndParameterNames.size(); i += 1) {
				paramString += command.text + INDENTATION_STR + PARAM_STR + returnTypeAndParameterNames.get(i);
			}
		}
		newC.isChange = true;
		newC.offset = command.offset;
		newC.text += command.text + INDENTATION_STR;
		newC.cursorOffset = command.offset + newC.text.length();
		if (stopTerminal == null && atEndOfLineInput(document, command.offset)) {
			newC.text += command.text + getRightTerminal();
		}
		if (stopTerminal != null && stopTerminal.getOffset() >= command.offset
				&& util.isSameLine(document, stopTerminal.getOffset(), command.offset)) {
			String string = document.get(command.offset, stopTerminal.getOffset() - command.offset);
			if (string.trim().length() > 0)
				newC.text += string.trim();
			if (!(returnTypeAndParameterNames.size() == 0)) {
				newC.text += paramString + command.text + returnString;
			} else {
				newC.text += command.text;
			}

			newC.length += string.length();
		}
		return newC;
	}

	/**
	 *
	 * @returns List<String> - The first item of the list (index 0) is the return type, the parameter names follow at
	 *          index 1. In case the document is not N4JSDocument or something breaks it returns null.
	 */
	private List<String> getReturnTypeAndParameterNames(IDocument document, IRegion startTerminal) {
		List<String> retAndfparNames = null;
		if (document instanceof N4JSDocument) {
			retAndfparNames = ((N4JSDocument) document).tryReadOnly((xtextResource) -> {
				Script script = XtextResourceUtils.getScript(xtextResource);
				ILeafNode node = NodeModelUtils.findLeafNodeAtOffset(NodeModelUtils.findActualNodeFor(script),
						startTerminal.getOffset());
				EObject astElement = node.getSemanticElement();
				List<String> retAndFPars = new ArrayList<>();
				if (astElement instanceof LiteralOrComputedPropertyName) {
					if (astElement.eContainer() instanceof N4MethodDeclaration) {
						N4MethodDeclaration methodDecl = (N4MethodDeclaration) astElement.eContainer();
						getRetAndFPars(retAndFPars, methodDecl);
					}
				} else if (astElement instanceof N4MethodDeclaration) {
					N4MethodDeclaration methodDecl = (N4MethodDeclaration) astElement;
					getRetAndFPars(retAndFPars, methodDecl);
				}
				return retAndFPars;
			});
		}
		return retAndfparNames;
	}

	/**
	 *
	 * @param retAndFPars
	 *            - The first element of the list (index 0) marks whether the method has a result (return type) or not.
	 *            In the first case, "return" is found at index 0, otherwise "void". The parameter names are added after
	 *            that, i.e. starting at index 1.
	 * @param methodDecl
	 *            - The N4MethodDeclaration of the next astElement in relation to the cursor position is expected here.
	 */
	private void getRetAndFPars(List<String> retAndFPars, N4MethodDeclaration methodDecl) {
		if (methodDecl.getReturnTypeRef() != null) {
			if ((methodDecl.getReturnTypeRef().getDeclaredType() instanceof VoidTypeImpl)) {
				retAndFPars.add("void");
			} else {
				// e.g. ThisTypeRefNominal or ParameterizedTypeRef
				retAndFPars.add("return");
			}
		} else {
			TFunction tfunction = methodDecl.getDefinedFunction();
			if (tfunction != null) {
				TypeRef returnTypeRef = tfunction.getReturnTypeRef();
				if ((returnTypeRef != null) && !TypeUtils.isVoid(returnTypeRef)
						&& !(returnTypeRef instanceof DeferredTypeRef)) {
					retAndFPars.add("return");
				} else {
					// e.g. constructor
					retAndFPars.add("noReturn");
				}
			} else {
				retAndFPars.add("noReturn");
			}
		}
		for (FormalParameter fpar : methodDecl.getFpars()) {
			retAndFPars.add(fpar.getName());
		}
	}
}
