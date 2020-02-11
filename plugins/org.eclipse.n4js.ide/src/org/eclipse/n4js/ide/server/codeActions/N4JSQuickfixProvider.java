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
package org.eclipse.n4js.ide.server.codeActions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.ide.editor.contentassist.imports.ImportUtil;
import org.eclipse.n4js.ide.editor.contentassist.imports.ImportsAwareReferenceProposalCreator;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2.Options;
import org.eclipse.xtext.util.ReplaceRegion;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * N4JS quick fixes.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#quickfixes
 */
@SuppressWarnings("restriction")
@Singleton
public class N4JSQuickfixProvider implements IQuickfixProvider {

	class QuickfixContext {
		final String code;
		final Options options;

		public QuickfixContext(String code, Options options) {
			this.code = code;
			this.options = options;
		}
	}

	final Class<?>[] quickfixProviders = { N4JSQuickfixProvider.class };

	Multimap<String, BiConsumer<QuickfixContext, CodeActionAcceptor>> quickfixMap = HashMultimap.create();

	// @Inject
	// private StatusHelper statusHelper;
	// @Inject
	// private IChangeSerializer serializer;
	// @Inject
	// private JavaScriptVariantHelper jsVariantHelper;

	@Inject
	private ImportUtil importUtil;

	/** Retrieve annotation constants from AnnotationDefinition */
	static final String INTERNAL_ANNOTATION = AnnotationDefinition.INTERNAL.name;
	static final String OVERRIDE_ANNOTATION = AnnotationDefinition.OVERRIDE.name;
	static final String FINAL_ANNOTATION = AnnotationDefinition.FINAL.name;

	/**
	 * Using reflection, all classes of {@link #quickfixProviders} are inspected to find all methods that are annotated
	 * with {@link Fix}.
	 */
	@Inject
	public void init(Injector injector) {
		for (Class<?> potQP : quickfixProviders) {
			if (IQuickfixProvider.class.isAssignableFrom(potQP)) {
				IQuickfixProvider qpInstance = (IQuickfixProvider) injector.getInstance(potQP);

				Method[] methods = potQP.getMethods();
				for (Method method : methods) {
					Fix fixAnnotation = method.getAnnotation(Fix.class);
					if (fixAnnotation != null) {
						String issueCode = fixAnnotation.issueCode();

						BiConsumer<QuickfixContext, CodeActionAcceptor> quickfixMethod = new BiConsumer<>() {
							@Override
							public void accept(QuickfixContext qc, CodeActionAcceptor caa) {
								try {
									method.invoke(qpInstance, qc, caa);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						};

						quickfixMap.put(issueCode, quickfixMethod);
					}
				}
			}
		}
	}

	/** Finds quick-fixes for the given issue code and adds these quick-fixes to the acceptor iff available. */
	public void addQuickfix(String code, Options options, CodeActionAcceptor acceptor) {
		for (BiConsumer<QuickfixContext, CodeActionAcceptor> quickfix : quickfixMap.get(code)) {
			QuickfixContext qc = new QuickfixContext(code, options);
			quickfix.accept(qc, acceptor);
		}
	}

	/**
	 * Resolves missing import statements by re-using content assist and {@link ImportsAwareReferenceProposalCreator}
	 */
	@Fix(issueCode = Diagnostic.LINKING_DIAGNOSTIC)
	public void addImportForUnresolvedReference(QuickfixContext context, CodeActionAcceptor acceptor) {
		Document doc = context.options.getDocument();
		Set<ContentAssistEntry> caEntries = importUtil.findImportCandidates(context.options);

		for (ContentAssistEntry cae : caEntries) {
			ArrayList<ReplaceRegion> replacements = cae.getTextReplacements();
			if (replacements != null && !replacements.isEmpty()) {
				String description = cae.getDescription();
				List<TextEdit> textEdits = new ArrayList<>();
				for (ReplaceRegion replaceRegion : replacements) {
					Position posStart = doc.getPosition(replaceRegion.getOffset());
					Position posEnd = doc.getPosition(replaceRegion.getOffset() + replaceRegion.getLength());
					Range rangeOfImport = new Range(posStart, posEnd);
					TextEdit textEdit = new TextEdit(rangeOfImport, replaceRegion.getText());
					textEdits.add(textEdit);
				}

				acceptor.acceptQuickfixCodeAction(context, "Add import from module " + description, textEdits);
			}
		}
	}

}
