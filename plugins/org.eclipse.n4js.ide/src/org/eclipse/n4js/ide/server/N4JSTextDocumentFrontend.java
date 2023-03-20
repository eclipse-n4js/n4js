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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
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
import org.eclipse.lsp4j.ProgressParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TypeHierarchyItem;
import org.eclipse.lsp4j.TypeHierarchyPrepareParams;
import org.eclipse.lsp4j.TypeHierarchySubtypesParams;
import org.eclipse.lsp4j.TypeHierarchySupertypesParams;
import org.eclipse.lsp4j.WorkDoneProgressBegin;
import org.eclipse.lsp4j.WorkDoneProgressEnd;
import org.eclipse.lsp4j.WorkDoneProgressNotification;
import org.eclipse.lsp4j.WorkDoneProgressReport;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.editor.contentassist.ContentAssistDataCollectors;
import org.eclipse.n4js.ide.server.util.SymbolKindUtil;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.smith.CollectedDataAccess;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectorUtils;
import org.eclipse.n4js.smith.DataPoint;
import org.eclipse.n4js.smith.DataSeries;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.tooling.findReferences.SimpleResourceAccess;
import org.eclipse.n4js.transpiler.sourcemap.MappingEntry;
import org.eclipse.n4js.transpiler.sourcemap.SourceMap;
import org.eclipse.n4js.transpiler.sourcemap.SourceMapFileLocator;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.xtext.ide.server.ResourceTaskContext;
import org.eclipse.n4js.xtext.ide.server.TextDocumentFrontend;
import org.eclipse.n4js.xtext.ide.server.XDocument;
import org.eclipse.n4js.xtext.ide.server.util.ServerIncidentLogger;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.TargetURICollector;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.ITextRegion;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Extends {@link N4JSTextDocumentFrontend} to implement N4JS server capabilities.
 */
@SuppressWarnings("restriction")
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

	@Inject
	private IReferenceFinder referenceFinder;

	@Inject
	private TargetURICollector targetURICollector;

	@Inject
	private Provider<TargetURIs> targetURIProvider;

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

		EObject element = resolveElement(rtc, params.getItem().getSelectionRange());

		List<CallHierarchyIncomingCall> incomingCalls = new ArrayList<>();
		if (element instanceof TFunction) {
			XtextResourceSet resSet = rtc.getResourceSet();
			IResourceDescriptions index = workspaceAccess.getXtextIndex(resSet).get();
			TargetURIs targetURIs = targetURIProvider.get();
			targetURICollector.add(element, targetURIs);
			FunctionReferenceAcceptor referenceAcceptor = new FunctionReferenceAcceptor();
			referenceFinder.findAllReferences(targetURIs, new SimpleResourceAccess(resSet), index, referenceAcceptor,
					new SubTypeProgressMonitor(langServerAccess, params.getWorkDoneToken()));

			for (EObject ref : referenceAcceptor.results) {
				if (ref instanceof TFunction) {
					TFunction referredFunction = (TFunction) ref;
					CallHierarchyItem item = toCallHierarchyItem(referredFunction);
					List<Range> fromRanges = List.of();
					incomingCalls.add(new CallHierarchyIncomingCall(item, fromRanges));
				}
			}
		}

		return incomingCalls;
	}

	@Override
	protected List<CallHierarchyOutgoingCall> callHierarchyOutgoingCalls(ResourceTaskContext rtc,
			CallHierarchyOutgoingCallsParams params, CancelIndicator ci) {

		EObject element = resolveElement(rtc, params.getItem().getSelectionRange());

		if (element instanceof TFunction) {
			TFunction tFun = (TFunction) element;
			element = tFun.getAstElement();
		}

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

	@Override
	protected List<TypeHierarchyItem> prepareTypeHierarchy(ResourceTaskContext rtc, TypeHierarchyPrepareParams params,
			CancelIndicator ci) {

		int offset = rtc.getDocument().getOffSet(params.getPosition());
		EObject element = eObjectAtOffsetHelper.resolveElementAt(rtc.getResource(), offset);
		if (element instanceof TypeRef) {
			TypeRef tRef = (TypeRef) element;
			element = tRef.getDeclaredType();
		}
		if (element instanceof Type) {
			TypeHierarchyItem item = toTypeHierarchyItem((Type) element);
			return List.of(item);
		}
		return Collections.emptyList();
	}

	@Override
	protected List<TypeHierarchyItem> typeHierarchySubtypes(ResourceTaskContext rtc, TypeHierarchySubtypesParams params,
			CancelIndicator ci) {

		EObject element = resolveElement(rtc, params.getItem().getSelectionRange());

		List<TypeHierarchyItem> superTypesTHI = new ArrayList<>();
		if (element instanceof TClassifier) {
			XtextResourceSet resSet = rtc.getResourceSet();
			IResourceDescriptions index = workspaceAccess.getXtextIndex(resSet).get();
			TargetURIs targetURIs = targetURIProvider.get();
			targetURICollector.add(element, targetURIs);
			DeclaredTypeReferenceAcceptor referenceAcceptor = new DeclaredTypeReferenceAcceptor();
			referenceFinder.findAllReferences(targetURIs, new SimpleResourceAccess(resSet), index, referenceAcceptor,
					new SubTypeProgressMonitor(langServerAccess, params.getWorkDoneToken()));

			for (EObject ref : referenceAcceptor.results) {
				if (ref instanceof Type) {
					Type referredType = (Type) ref;
					superTypesTHI.add(toTypeHierarchyItem(referredType));
				}
			}
		}

		return superTypesTHI;
	}

	@Override
	protected List<TypeHierarchyItem> typeHierarchySupertypes(ResourceTaskContext rtc,
			TypeHierarchySupertypesParams params, CancelIndicator ci) {

		EObject element = resolveElement(rtc, params.getItem().getSelectionRange());

		if (element instanceof N4TypeDeclaration) {
			N4TypeDeclaration typeDecl = (N4TypeDeclaration) element;
			element = typeDecl.getDefinedType();
		}
		List<TypeHierarchyItem> superTypesTHI = new ArrayList<>();
		if (element instanceof TClassifier) {
			TClassifier tClassifier = (TClassifier) element;
			for (TClassifier superClassifier : tClassifier.getSuperClassifiers()) {
				superTypesTHI.add(toTypeHierarchyItem(superClassifier));
			}
		}

		return superTypesTHI;
	}

	private EObject resolveElement(ResourceTaskContext rtc, Range selRange) {
		XtextResourceSet resSet = rtc.getResourceSet();
		N4JSResource resource = (N4JSResource) rtc.getResource();
		IResourceDescriptions index = workspaceAccess.getXtextIndex(resSet).get();

		IResourceDescription resDesc = index.getResourceDescription(rtc.getURI());
		if (resDesc != null) {
			// ensures that we get an TModele element (instead of an AST element)
			workspaceAccess.loadModuleFromIndex(resSet, resDesc, true);
		}

		int offset = rtc.getDocument().getOffSet(selRange.getStart());
		EObject element = eObjectAtOffsetHelper.resolveElementAt(resource, offset);
		return element;
	}

	private TypeHierarchyItem toTypeHierarchyItem(Type type) {
		XtextResource resource = (XtextResource) type.eResource();
		XDocument doc = new XDocument(1, resource.getParseResult().getRootNode().getText());
		SymbolKind symbolKind = SymbolKindUtil.getSymbolKind(type.eClass());
		String uri = type.eResource().getURI().toString();

		ITextRegion fullRegion = locationInFileProvider.getFullTextRegion(type);
		Position fullStartPos = doc.getPosition(fullRegion.getOffset());
		Position fullEndPos = doc.getPosition(fullRegion.getOffset() + fullRegion.getLength());
		ITextRegion sgnfRegion = locationInFileProvider.getSignificantTextRegion(type);
		Position sgnfStartPos = doc.getPosition(sgnfRegion.getOffset());
		Position sgnfEndPos = doc.getPosition(sgnfRegion.getOffset() + sgnfRegion.getLength());
		Range range = new Range(fullStartPos, fullEndPos);
		Range selectionRange = new Range(sgnfStartPos, sgnfEndPos);

		TypeHierarchyItem item = new TypeHierarchyItem(type.getName(), symbolKind, uri, range, selectionRange);
		return item;
	}

	static class FunctionReferenceAcceptor implements IReferenceFinder.Acceptor {
		final ArrayList<EObject> results = Lists.newArrayList();

		@Override
		public void accept(EObject src, URI srcURI, EReference eRef, int idx, EObject tgtOrProxy, URI tgtURI) {
			Resource res = src != null ? src.eResource() : null;
			URI resURI = res != null ? res.getURI() : null;
			if (src == null || res == null || resURI == null) {
				return;
			}

			if (eRef == N4JSPackage.Literals.IDENTIFIER_REF__ID
					&& src instanceof IdentifierRef && src.eContainer() instanceof ParameterizedCallExpression) {

				FunctionDefinition fDefinition = EcoreUtil2.getContainerOfType(src, FunctionDefinition.class);
				if (fDefinition != null) {
					results.add(fDefinition.getDefinedType());
				}
			}
		}

		@Override
		public void accept(IReferenceDescription description) {
			// This method is only called in case of finding refs for primitives.
			// For instance, the method is called when a reference to a primitive type (e.g. string)
			// is found in primitives.n4jsd
		}
	}

	static class DeclaredTypeReferenceAcceptor implements IReferenceFinder.Acceptor {
		final ArrayList<EObject> results = Lists.newArrayList();

		@Override
		public void accept(EObject src, URI srcURI, EReference eRef, int idx, EObject tgtOrProxy, URI tgtURI) {
			Resource res = src != null ? src.eResource() : null;
			URI resURI = res != null ? res.getURI() : null;
			if (src == null || res == null || resURI == null) {
				return;
			}

			if (eRef == TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE
					&& src instanceof ParameterizedTypeRef && src.eContainer() instanceof TypeReferenceNode<?>) {

				EObject parentType = src.eContainer().eContainer();
				if (parentType instanceof N4ClassifierDeclaration) {
					N4ClassifierDeclaration n4classifierDecl = (N4ClassifierDeclaration) parentType;
					results.add(n4classifierDecl.getDefinedType());
				}
			}
		}

		@Override
		public void accept(IReferenceDescription description) {
			// This method is only called in case of finding refs for primitives.
			// For instance, the method is called when a reference to a primitive type (e.g. string)
			// is found in primitives.n4jsd
		}
	}

	static class SubTypeProgressMonitor implements IProgressMonitor {
		final ILanguageServerAccess langServerAccess;
		final Either<String, Integer> workDoneToken;

		private int totalWork;
		private String name;
		private boolean isCancelled = false;

		/** Constructor */
		public SubTypeProgressMonitor(ILanguageServerAccess langServerAccess, Either<String, Integer> workDoneToken) {
			this.langServerAccess = langServerAccess;
			this.workDoneToken = workDoneToken;
		}

		@Override
		public boolean isCanceled() {
			return isCancelled;
		}

		@Override
		public void setCanceled(boolean value) {
			isCancelled = value;
		}

		@Override
		public void internalWorked(double work) {
			// ignore
		}

		@Override
		public void setTaskName(String name) {
			this.name = name;
		}

		@Override
		public void subTask(String newName) {
			this.name = newName;
		}

		@Override
		public void beginTask(String newName, int newTotalWork) {
			if (workDoneToken == null) {
				return;
			}
			this.name = newName;
			this.totalWork = Math.max(1, newTotalWork);
			WorkDoneProgressBegin report = new WorkDoneProgressBegin();
			report.setCancellable(false);
			Either<WorkDoneProgressNotification, Object> notification = Either.forLeft(report);
			ProgressParams pp = new ProgressParams(workDoneToken, notification);
			langServerAccess.getLanguageClient().notifyProgress(pp);
		}

		@Override
		public void done() {
			if (workDoneToken == null) {
				return;
			}
			this.totalWork = Math.max(1, totalWork);
			WorkDoneProgressEnd report = new WorkDoneProgressEnd();
			Either<WorkDoneProgressNotification, Object> notification = Either.forLeft(report);
			ProgressParams pp = new ProgressParams(workDoneToken, notification);
			langServerAccess.getLanguageClient().notifyProgress(pp);
		}

		@Override
		public void worked(int work) {
			if (workDoneToken == null) {
				return;
			}
			int percent = (work * 100) / totalWork;
			WorkDoneProgressReport report = new WorkDoneProgressReport();
			report.setCancellable(false);
			report.setPercentage(percent);
			report.setMessage(name);
			Either<WorkDoneProgressNotification, Object> notification = Either.forLeft(report);
			ProgressParams pp = new ProgressParams(workDoneToken, notification);
			langServerAccess.getLanguageClient().notifyProgress(pp);
		}
	}
}
