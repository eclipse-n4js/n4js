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
package org.eclipse.n4js.ui.contentassist.jsdoc;

import static org.eclipse.n4js.jsdoc.JSDocCompletionHint.CompletionKind.MODULESPEC;
import static org.eclipse.n4js.jsdoc.JSDocCompletionHint.CompletionKind.NOCOMPLETION;

import java.util.ArrayList;
import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import org.eclipse.n4js.jsdoc.ITagDefinition;
import org.eclipse.n4js.jsdoc.JSDocCharScanner;
import org.eclipse.n4js.jsdoc.JSDocCharScanner.ScannerState;
import org.eclipse.n4js.jsdoc.JSDocCompletionHint;
import org.eclipse.n4js.jsdoc.JSDocletUtils;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.FullMemberReference;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.tags.LineTagWithFullElementReference;
import org.eclipse.n4js.jsdoc.N4JSDocletParser;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.scoping.IContentAssistScopeProvider;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;

/**
 */
public class JSDocCompletionProposalComputer implements IUnitOfWork<ICompletionProposal[], XtextResource>,
		ICompletionProposalAcceptor {

	private final JSDocContentAssistProcessor processor;
	private final ITextViewer viewer;
	private final int offset;

	/**
	 * Creates this computer.
	 */
	public JSDocCompletionProposalComputer(JSDocContentAssistProcessor jsDocContentAssistProcessor, ITextViewer viewer,
			int offset) {
		this.processor = jsDocContentAssistProcessor;
		this.viewer = viewer;
		this.offset = offset;
	}

	@Override
	public void accept(ICompletionProposal proposal) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canAcceptMoreProposals() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ICompletionProposal[] exec(XtextResource xtextResource) throws Exception {
		ContentAssistContext[] contexts = processor.getContextFactory().create(viewer, offset, xtextResource);
		if (contexts.length > 0) {

			ArrayList<ICompletionProposal> proposals = new ArrayList<>();

			ContentAssistContext contentAssistContext = contexts[contexts.length - 1];

			INode currentNode = contentAssistContext.getCurrentNode();
			String content = currentNode.getText();
			int offsetInNode = contentAssistContext.getOffset() - currentNode.getOffset()
					- contentAssistContext.getPrefix().length();
			// String textInFront = content.substring(0, offsetInNode);
			// System.out.println(textInFront);

			N4JSDocletParser docletParser = processor.getDocletParser();
			Doclet doclet = docletParser.parse(content);
			Optional<String> lineTagPrefix = getLineTagTitlePrefix(content, offsetInNode);
			if (lineTagPrefix.isPresent()) {
				createLineTagProposal(lineTagPrefix.get(), docletParser, proposals);
			}
			Tag tag = JSDocletUtils.getTagAtOffset(doclet, offsetInNode);
			if (tag != null) {
				ITagDefinition tagDef = tag.getTagDefinition();

				JSDocCharScanner scanner = new JSDocCharScanner(content);
				ScannerState state = scanner.saveState();
				scanner.setNextOffset(offsetInNode);
				JSDocCompletionHint completionHint = tagDef.completionHint(scanner);
				scanner.restoreState(state);

				if (completionHint.kind != NOCOMPLETION) {
					int replacementOffset = offset - completionHint.prefix.length();

					if (completionHint.isTypeModelRef()) {

						// get reference as far as it can be parsed:
						FullMemberReference ref = completionHint.nodeAsFullMemberReference();

						IContentAssistScopeProvider scopeProvider = (IContentAssistScopeProvider) processor
								.getScopeProvider();
						IScope moduleSpecScope = scopeProvider.getScopeForContentAssist(
								xtextResource.getContents().get(0), // context for finding modules
								N4JSPackage.Literals.IMPORT_DECLARATION__MODULE);

						// complete module or type
						if (!completionHint.isModuleNameCompleted()) {
							for (IEObjectDescription moduleDescr : moduleSpecScope.getAllElements()) {
								String moduleSpec = moduleDescr.getName().toString("/");
								String moduleSimpleName = moduleDescr.getName().getLastSegment();
								if (!moduleSpec.startsWith("#")) {

									if (moduleSpec.startsWith(completionHint.prefix)
											|| moduleSimpleName.startsWith(completionHint.prefix)) {
										if (moduleSpec.length() == completionHint.prefix.length()) {
											int replacementLength = 0;
											int cursorPosition = 1;
											ICompletionProposal proposal = new CompletionProposal(".",
													replacementOffset + completionHint.prefix.length(),
													replacementLength, cursorPosition);
											proposals.add(proposal);
										} else {
											int replacementLength = completionHint.prefix.length();
											int cursorPosition = moduleSpec.length();
											ICompletionProposal proposal = new CompletionProposal(moduleSpec,
													replacementOffset,
													replacementLength, cursorPosition);
											proposals.add(proposal);
										}
									}

								}
							}
						} else {
							QualifiedName moduleQN = QualifiedName.create(ref.getModuleName().split("/"));
							IEObjectDescription descr = moduleSpecScope.getSingleElement(moduleQN);
							if (descr != null && descr.getEObjectOrProxy() instanceof TModule) {
								TModule module = (TModule) descr.getEObjectOrProxy();
								if (module.eIsProxy())
									module = (TModule) EcoreUtil.resolve(module, xtextResource);
								if (!completionHint.isTypeNameCompleted(false) && completionHint.kind != MODULESPEC) {
									String typePrefix = ref.getTypeName();
									for (Type t : module.getTopLevelTypes()) {
										String typeName = t.getName();
										if (typeName.startsWith(typePrefix)) {
											String completion = module.getModuleSpecifier() + "." + typeName;
											int replacementLength = completionHint.prefix.length();
											int cursorPosition = completion.length();
											ICompletionProposal proposal = new CompletionProposal(
													completion,
													replacementOffset,
													replacementLength, cursorPosition);
											proposals.add(proposal);
										}
									}
								} else { // completionHint.kind == MEMBER
									Optional<Type> optType = module.getTopLevelTypes().stream()
											.filter(t -> t.getName().equals(ref.getTypeName())).findAny();
									if (optType.isPresent()) {
										Type t = optType.get();
										if (t instanceof ContainerType) {
											String memberPrefix = ref.getMemberName();
											for (TMember m : ((ContainerType<? extends TMember>) t).getOwnedMembers()) {
												String memberName = m.getName();
												if (memberName.startsWith(memberPrefix)) {
													String completion = LineTagWithFullElementReference
															.createReferenceFromType(m).toString();
													int replacementLength = completionHint.prefix.length();
													int cursorPosition = completion.length();
													ICompletionProposal proposal = new CompletionProposal(completion,
															replacementOffset,
															replacementLength, cursorPosition);
													proposals.add(proposal);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

			ICompletionProposal[] result = new ICompletionProposal[proposals.size()];
			proposals.toArray(result);
			return result;
		}
		return null;
	}

	private void createLineTagProposal(String prefix, N4JSDocletParser docletParser,
			ArrayList<ICompletionProposal> proposals) {
		int replacementOffset = offset - prefix.length();
		// create line tag proposals
		for (ITagDefinition td : docletParser.getLineTagDictionary().getTagDefinitions()) {
			String tagString = '@' + td.getTitle() + ' ';
			if (tagString.startsWith(prefix)) {

				int replacementLength = prefix.length();
				int cursorPosition = tagString.length();
				ICompletionProposal proposal = new CompletionProposal(tagString, replacementOffset,
						replacementLength, cursorPosition);
				proposals.add(proposal);
			}
		}
	}

	private Optional<String> getLineTagTitlePrefix(String content, int offsetInNode) {
		int p = offsetInNode - 1;
		if (p <= 0) {
			return Optional.of("");
		}
		char c = 0;
		do {
			c = content.charAt(p);
			if (c == '@') {
				p--;
				break;
			}
			if (Character.isLetter(c)) {
				p--;
			} else {
				break;
			}
		} while (p > 0);

		String prefix = content.substring(p + 1, offsetInNode);
		if (!(prefix.isEmpty() || c == '@')) {
			return Optional.empty();
		}
		do {
			c = content.charAt(p);
			if (c == '\n' || c == '\r') {
				break;
			}
			if (Character.isWhitespace(c) || c == '*') {
				p--;
			} else {
				break;
			}
		} while (p > 0);
		if (p == 0 || c == '\n' || c == '\r') {
			return Optional.of(prefix);
		}
		return Optional.empty();

	}

}
