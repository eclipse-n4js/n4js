/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import static org.eclipse.n4js.N4JSGlobals.JS_FILE_EXTENSION;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.CallHierarchyIncomingCall;
import org.eclipse.lsp4j.CallHierarchyIncomingCallsParams;
import org.eclipse.lsp4j.CallHierarchyItem;
import org.eclipse.lsp4j.CallHierarchyOutgoingCall;
import org.eclipse.lsp4j.CallHierarchyOutgoingCallsParams;
import org.eclipse.lsp4j.CallHierarchyPrepareParams;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.ImplementationParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.editor.contentassist.ContentAssistDataCollectors;
import org.eclipse.n4js.ide.server.util.SymbolKindUtil;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.smith.CollectedDataAccess;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectorUtils;
import org.eclipse.n4js.smith.DataPoint;
import org.eclipse.n4js.smith.DataSeries;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.transpiler.sourcemap.MappingEntry;
import org.eclipse.n4js.transpiler.sourcemap.SourceMap;
import org.eclipse.n4js.transpiler.sourcemap.SourceMapFileLocator;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskContext;
import org.eclipse.n4js.xtext.ide.server.TextDocumentFrontend;
import org.eclipse.n4js.xtext.ide.server.XDocument;
import org.eclipse.n4js.xtext.ide.server.util.ServerIncidentLogger;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.ITextRegion;

import com.google.common.base.Strings;
import com.google.inject.Inject;

/**
 * Extends {@link N4JSTextDocumentFrontend} to implement N4JS server capabilities.
 */
public class N4JSTextDocumentFrontend extends TextDocumentFrontend {
	private static Logger LOG = Logger.getLogger(N4JSTextDocumentFrontend.class);

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	@Inject
	private ServerIncidentLogger serverIncidentLogger;

	@Inject
	private ContentAssistDataCollectors contentAssistDataCollectors;

	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;

	@Inject
	private ILocationInFileProvider locationInFileProvider;

	@Override
	protected Either<List<CompletionItem>, CompletionList> completion(ResourceTaskContext rtc, CompletionParams params,
			CancelIndicator originalCancelIndicator) {

		DataCollector dCollector = contentAssistDataCollectors.dcCreateCompletionsRoot();

		try (Measurement m = dCollector.getMeasurement()) {
			dCollector.resetData();
			dCollector.setPaused(false);
			return super.completion(rtc, params, originalCancelIndicator);
		} finally {
			dCollector.setPaused(true);
			List<DataPoint> data = dCollector.getData();
			if (!data.isEmpty()) {
				DataPoint dataPoint = data.get(0);
				int elapsedSeconds = (int) (dataPoint.nanos / 1000000000);
				if (elapsedSeconds > 2) {
					DataSeries dataSeries = CollectedDataAccess.getDataSeries(dCollector);
					String collectorString = DataCollectorUtils.dataToString(dataSeries, " ");
					String msg = "Slow content assist\nPARAMS:\n" + params.toString();
					msg += "\nTime measurements:\n" + collectorString;
					serverIncidentLogger.reportWithFileBaseName("slow-ca", msg, true);
				}
			}
		}
	}

	@Override
	protected Either<List<? extends Location>, List<? extends LocationLink>> implementation(ResourceTaskContext rtc,
			ImplementationParams params, CancelIndicator cancelIndicator) {

		URI uri = rtc.getURI();
		N4JSProjectConfigSnapshot project = workspaceAccess.findProjectContaining(rtc.getResource());
		String targetFileName = resourceNameComputer.generateFileDescriptor(rtc.getResource(), uri, JS_FILE_EXTENSION);
		List<Location> locations = new ArrayList<>();
		if (project != null && !Strings.isNullOrEmpty(targetFileName)) {
			String outputPath = project.getOutputPath();
			Path projectLocation = project.getPathAsFileURI().toFileSystemPath();
			Path genFilePath = projectLocation.resolve(outputPath + "/" + targetFileName);

			Range range = findRange(params, genFilePath);
			Location location = new Location();
			location.setUri(new FileURI(genFilePath.toFile()).toString());
			location.setRange(range);
			locations.add(location);
		}
		return Either.forLeft(locations);
	}

	private Range findRange(TextDocumentPositionParams positionParams, Path genFilePath) {
		try {
			File sourceMapFile = SourceMapFileLocator.resolveSourceMapFromGen(genFilePath);
			if (sourceMapFile != null) {
				SourceMap sourceMap = SourceMap.loadAndResolve(sourceMapFile.toPath());
				Position position = positionParams.getPosition();
				MappingEntry mappingEntry = sourceMap.findMappingForSrcPosition(0, position.getLine(),
						position.getCharacter());

				if (mappingEntry != null) {
					Position startPos = new Position(mappingEntry.genLine, mappingEntry.genColumn);
					Position endPos = new Position(mappingEntry.genLine, mappingEntry.genColumn);
					return new Range(startPos, endPos);
				}
			}
		} catch (IOException e) {
			LOG.error(e);
		}
		return new Range(new Position(), new Position());
	}

	@Override
	protected List<CallHierarchyItem> prepareCallHierarchy(ResourceTaskContext rtc,
			CallHierarchyPrepareParams params, CancelIndicator ci) {

		int offset = rtc.getDocument().getOffSet(params.getPosition());
		EObject element = eObjectAtOffsetHelper.resolveElementAt(rtc.getResource(), offset);
		if (element instanceof FunctionTypeExprOrRef) {
			FunctionTypeExprOrRef fRef = (FunctionTypeExprOrRef) element;
			element = fRef.getFunctionType();
		}
		if (element instanceof TFunction) {
			CallHierarchyItem item = toCallHierarchyItem((TFunction) element);
			return List.of(item);
		}
		return Collections.emptyList();
	}

	@Override
	protected List<CallHierarchyIncomingCall> callHierarchyIncomingCalls(ResourceTaskContext rtc,
			CallHierarchyIncomingCallsParams params, CancelIndicator ci) {

		// TODO
		return Collections.emptyList();
	}

	@Override
	protected List<CallHierarchyOutgoingCall> callHierarchyOutgoingCalls(ResourceTaskContext rtc,
			CallHierarchyOutgoingCallsParams params, CancelIndicator ci) {

		int offset = rtc.getDocument().getOffSet(params.getItem().getSelectionRange().getStart());
		EObject element = eObjectAtOffsetHelper.resolveElementAt(rtc.getResource(), offset);
		if (element instanceof FunctionDefinition) {
			FunctionDefinition funDef = (FunctionDefinition) element;
			List<ParameterizedCallExpression> calls = EcoreUtilN4.getAllContentsOfTypeStopAt(funDef.getBody(),
					ParameterizedCallExpression.class,
					N4JSPackage.Literals.FUNCTION_OR_FIELD_ACCESSOR__BODY);

			List<CallHierarchyOutgoingCall> result = new ArrayList<>();
			for (ParameterizedCallExpression call : calls) {
				Expression target = call.getTarget();
				IdentifiableElement targetTFunction = null;
				if (target instanceof ParameterizedPropertyAccessExpression) {
					ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) target;
					targetTFunction = ppae.getProperty();
				}
				if (target instanceof IdentifierRef) {
					IdentifierRef idRef = (IdentifierRef) target;
					targetTFunction = idRef.getTargetElement();
				}
				if (targetTFunction instanceof TFunction) {
					TFunction targetFunDef = (TFunction) targetTFunction;
					XtextResource resCall = (XtextResource) call.eResource();
					XDocument docCall = new XDocument(1, resCall.getParseResult().getRootNode().getText());
					ITextRegion sgnfRegion = locationInFileProvider.getSignificantTextRegion(call);
					Position sgnfStartPos = docCall.getPosition(sgnfRegion.getOffset());
					Position sgnfEndPos = docCall.getPosition(sgnfRegion.getOffset() + sgnfRegion.getLength());
					List<Range> fromRanges = List.of(new Range(sgnfStartPos, sgnfEndPos));
					CallHierarchyItem item = toCallHierarchyItem(targetFunDef);
					result.add(new CallHierarchyOutgoingCall(item, fromRanges));
				}
			}
			return result;
		}
		return Collections.emptyList();
	}

	private CallHierarchyItem toCallHierarchyItem(TFunction fun) {
		XtextResource resource = (XtextResource) fun.eResource();
		XDocument doc = new XDocument(1, resource.getParseResult().getRootNode().getText());
		SymbolKind symbolKind = SymbolKindUtil.getSymbolKind(fun.eClass());
		String uri = resource.getURI().toString();

		ITextRegion fullRegion = locationInFileProvider.getFullTextRegion(fun);
		Position fullStartPos = doc.getPosition(fullRegion.getOffset());
		Position fullEndPos = doc.getPosition(fullRegion.getOffset() + fullRegion.getLength());
		ITextRegion sgnfRegion = locationInFileProvider.getSignificantTextRegion(fun);
		Position sgnfStartPos = doc.getPosition(sgnfRegion.getOffset());
		Position sgnfEndPos = doc.getPosition(sgnfRegion.getOffset() + sgnfRegion.getLength());
		Range range = new Range(fullStartPos, fullEndPos);
		Range selectionRange = new Range(sgnfStartPos, sgnfEndPos);

		CallHierarchyItem item = new CallHierarchyItem(fun.getName(), symbolKind, uri, range, selectionRange);
		return item;
	}
}
