package org.eclipse.n4js.xtext.ide.server.contentassist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.InsertTextFormat;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.xtext.ide.editor.contentassist.N4JSContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author kosyakov - Initial contribution and API
 * @author Dennis Huebner - additionalTextEdits support
 *
 * @since 2.11
 */
@Singleton
public class XContentAssistService {

	@Inject
	private Provider<ContentAssistContextFactory> contextFactoryProvider;

	@Inject
	private ExecutorService executorService;

	@Inject
	private IdeContentProposalProvider proposalProvider;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * Create the completion proposals.
	 */
	public CompletionList createCompletionList(Document document, XtextResource resource,
			TextDocumentPositionParams params, CancelIndicator cancelIndicator) {
		CompletionList result = new CompletionList();
		result.setIsIncomplete(false);
		// we set isInComplete to true, so we get asked always, which is the best match to the expected behavior in
		// Xtext
		XIdeContentProposalAcceptor acceptor = new XIdeContentProposalAcceptor(cancelIndicator);
		int caretOffset = document.getOffSet(params.getPosition());
		Position caretPosition = params.getPosition();
		TextRegion position = new TextRegion(caretOffset, 0);
		try {
			createProposals(document.getContents(), position, caretOffset, resource, acceptor);
		} catch (Throwable t) {
			if (!operationCanceledManager.isOperationCanceledException(t)) {
				throw t;
			}
		}
		operationCanceledManager.checkCanceled(cancelIndicator);
		{
			int idx = 0;
			for (ContentAssistEntry cae : acceptor.getEntries()) {
				CompletionItem item = toCompletionItem(cae, caretOffset, caretPosition, document);
				item.setSortText(Strings.padStart(Integer.toString(idx), 5, '0'));
				result.getItems().add(item);
				idx++;
			}
		}
		return result;
	}

	/**
	 * Create a single proposal.
	 */
	protected void createProposals(String document, TextRegion selection, int caretOffset,
			XtextResource resource, IIdeContentProposalAcceptor acceptor) {
		if (caretOffset > document.length()) {
			return;
		}
		ContentAssistContextFactory contextFactory = contextFactoryProvider.get();
		contextFactory.setPool(executorService);
		ContentAssistContext[] contexts = contextFactory.create(document, selection, caretOffset, resource);
		proposalProvider.createProposals(Arrays.asList(contexts), acceptor);
	}

	/**
	 * Convert to a completion item.
	 */
	protected CompletionItem toCompletionItem(ContentAssistEntry entry, int caretOffset,
			Position caretPosition, Document document) {

		CompletionItem result = new CompletionItem();
		String label = null;
		if (entry.getLabel() != null) {
			label = entry.getLabel();
		} else {
			label = entry.getProposal();
		}
		result.setLabel(label);
		result.setDetail(entry.getDescription());
		result.setDocumentation(entry.getDocumentation());
		String prefix = null;
		if (entry.getPrefix() != null) {
			prefix = entry.getPrefix();
		} else {
			prefix = "";
		}
		int prefixOffset = caretOffset - prefix.length();
		Position prefixPosition = document.getPosition(prefixOffset);
		if (prefixPosition.getLine() < caretPosition.getLine()) {
			// LSP spec: The text edit's range as well as both ranges from an insert
			// replace edit must be a [single line] and they must contain the position
			// at which completion has been requested.

			prefixPosition.setLine(caretPosition.getLine());
			prefixPosition.setCharacter(0);
			int length = prefix.length() - caretPosition.getCharacter();
			ReplaceRegion rr = new ReplaceRegion(new TextRegion(prefixOffset, length), "");
			entry.getTextReplacements().add(rr);
		}
		result.setTextEdit(Either.forLeft(new TextEdit(new Range(prefixPosition, caretPosition), entry.getProposal())));
		if (!entry.getTextReplacements().isEmpty()) {
			if (result.getAdditionalTextEdits() == null) {
				result.setAdditionalTextEdits(new ArrayList<>(entry.getTextReplacements().size()));
			}
			entry.getTextReplacements().forEach(it -> {
				result.getAdditionalTextEdits().add(toTextEdit(it, document));
			});
		}
		if (entry.getKind().startsWith(ContentAssistEntry.KIND_SNIPPET + ":")) {
			result.setInsertTextFormat(InsertTextFormat.Snippet);
			entry.setKind(entry.getKind().substring(ContentAssistEntry.KIND_SNIPPET.length() + 1));
		} else if (Objects.equal(entry.getKind(), ContentAssistEntry.KIND_SNIPPET)) {
			result.setInsertTextFormat(InsertTextFormat.Snippet);
		}
		result.setKind(translateKind(entry));
		if (entry instanceof N4JSContentAssistEntry) {
			N4JSContentAssistEntry n4cae = (N4JSContentAssistEntry) entry;
			result.setFilterText(n4cae.getFilterText());
		}
		return result;
	}

	/**
	 * Translate to a completion item kind.
	 */
	protected CompletionItemKind translateKind(ContentAssistEntry entry) {
		CompletionItemKind result = null;
		if (entry.getKind() != null) {
			switch (entry.getKind()) {
			case ContentAssistEntry.KIND_CLASS:
				result = CompletionItemKind.Class;
				break;
			case ContentAssistEntry.KIND_COLOR:
				result = CompletionItemKind.Color;
				break;
			case ContentAssistEntry.KIND_CONSTRUCTOR:
				result = CompletionItemKind.Constructor;
				break;
			case ContentAssistEntry.KIND_ENUM:
				result = CompletionItemKind.Enum;
				break;
			case ContentAssistEntry.KIND_FIELD:
				result = CompletionItemKind.Field;
				break;
			case ContentAssistEntry.KIND_FILE:
				result = CompletionItemKind.File;
				break;
			case ContentAssistEntry.KIND_FUNCTION:
				result = CompletionItemKind.Function;
				break;
			case ContentAssistEntry.KIND_INTERFACE:
				result = CompletionItemKind.Interface;
				break;
			case ContentAssistEntry.KIND_KEYWORD:
				result = CompletionItemKind.Keyword;
				break;
			case ContentAssistEntry.KIND_METHOD:
				result = CompletionItemKind.Method;
				break;
			case ContentAssistEntry.KIND_MODULE:
				result = CompletionItemKind.Module;
				break;
			case ContentAssistEntry.KIND_PROPERTY:
				result = CompletionItemKind.Property;
				break;
			case ContentAssistEntry.KIND_REFERENCE:
				result = CompletionItemKind.Reference;
				break;
			case ContentAssistEntry.KIND_SNIPPET:
				result = CompletionItemKind.Snippet;
				break;
			case ContentAssistEntry.KIND_TEXT:
				result = CompletionItemKind.Text;
				break;
			case ContentAssistEntry.KIND_UNIT:
				result = CompletionItemKind.Unit;
				break;
			case ContentAssistEntry.KIND_VALUE:
				result = CompletionItemKind.Value;
				break;
			case ContentAssistEntry.KIND_VARIABLE:
				result = CompletionItemKind.Variable;
				break;
			default:
				result = CompletionItemKind.Value;
				break;
			}
		} else {
			result = CompletionItemKind.Value;
		}
		return result;
	}

	/**
	 * Translate to a text edit.
	 */
	protected TextEdit toTextEdit(ReplaceRegion region, Document doc) {
		Position start;
		if (region.getOffset() > doc.getContents().length()) {
			Position docEnd = doc.getPosition(doc.getContents().length());
			start = new Position(docEnd.getLine(), docEnd.getCharacter() + region.getLength());
		} else {
			start = doc.getPosition(region.getOffset());
		}
		Position end;
		if (region.getEndOffset() > doc.getContents().length()) {
			end = new Position(start.getLine(), start.getCharacter() + region.getLength());
		} else {
			end = doc.getPosition(region.getEndOffset());
		}
		return new TextEdit(new Range(start, end), region.getText());
	}
}
