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
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.ide.editor.contentassist.ContentAssistDataCollectors;
import org.eclipse.n4js.ide.server.util.SymbolKindUtil;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
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
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.utils.AllSuperTypesCollector;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.n4js.utils.URIUtils;
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

import com.google.common.base.Objects;
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

	@Inject
	private DeclMergingHelper declMergingHelper;

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

		try {
			int offset = rtc.getDocument().getOffSet(params.getPosition());
			EObject element = eObjectAtOffsetHelper.resolveElementAt(rtc.getResource(), offset);
			EObject type = N4JSASTUtils.getCorrespondingTypeModelElement(element);
			if (type != null) {
				element = type;
			}

			if (element instanceof FunctionTypeExprOrRef) {
				FunctionTypeExprOrRef fRef = (FunctionTypeExprOrRef) element;
				element = fRef.getFunctionType();
			}
			if (element instanceof TFunction) {
				CallHierarchyItem item = toCallHierarchyItem((TFunction) element);
				return List.of(item);
			}
		} catch (IndexOutOfBoundsException e) {
			LOG.error(e);
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
			FunctionReferenceAcceptor referenceAcceptor = new FunctionReferenceAcceptor();

			TFunction tFun = (TFunction) element;
			EObject funDef = tFun.getAstElement();

			if (funDef instanceof FunctionDefinition
					&& AnnotationDefinition.OVERRIDE.hasAnnotation((FunctionDefinition) funDef)) {

				// case 1: Method has annotation @Override

				TMethod method = (TMethod) element;
				ContainerType<?> parent = method.getContainingType();
				// Reflective: also returns the given container type
				List<TClassifier> superTypes = AllSuperTypesCollector.collect(parent, declMergingHelper);

				for (TClassifier superType : superTypes) {
					TMember superMethod = superType.findOwnedMember(method.getName());
					if (superMethod instanceof TMethod) {
						TargetURIs targetURIs = targetURIProvider.get();
						targetURICollector.add(superMethod, targetURIs);
						referenceFinder.findAllReferences(targetURIs, new SimpleResourceAccess(resSet), index,
								referenceAcceptor,
								new SubTypeProgressMonitor(langServerAccess, params.getWorkDoneToken()));
					}
				}
			} else if (funDef instanceof FunctionDefinition
					&& ((FunctionDefinition) funDef).getBody() == null) {

				// case 2: Method has no body
				TMethod method = (TMethod) element;
				ContainerType<?> parent = method.getContainingType();
				Collection<ContainerType<?>> subtypes = findSubtypes(rtc, params.getWorkDoneToken(), parent, true);

				for (ContainerType<?> subtype : subtypes) {
					for (EObject member : subtype.getOwnedMembers()) {
						if (member instanceof TMethod) {
							TMethod subMethod = (TMethod) member;
							if (Objects.equal(method.getName(), subMethod.getName())) {
								referenceAcceptor.results.add(subMethod);
							}
						}
					}
				}

			} else {

				// case 3: default
				TargetURIs targetURIs = targetURIProvider.get();
				targetURICollector.add(element, targetURIs);
				referenceFinder.findAllReferences(targetURIs, new SimpleResourceAccess(resSet), index,
						referenceAcceptor,
						new SubTypeProgressMonitor(langServerAccess, params.getWorkDoneToken()));
			}

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

		List<FunctionDefinition> funDefs = new ArrayList<>();
		if (element instanceof TMethod) {
			TMethod method = (TMethod) element;
			EObject parentContainer = element.eContainer();
			Collection<ContainerType<?>> subtypes = findSubtypes(rtc, params.getWorkDoneToken(), parentContainer, true);
			if (parentContainer instanceof ContainerType<?>) {
				subtypes.add((ContainerType<?>) parentContainer);
			}

			for (ContainerType<?> subtype : subtypes) {
				for (EObject member : subtype.getOwnedMembers()) {
					if (member instanceof TMethod) {
						TMethod subMethod = (TMethod) member;
						if (Objects.equal(method.getName(), subMethod.getName())) {
							EObject funDef = subMethod.getAstElement();
							if (funDef instanceof FunctionDefinition) {
								funDefs.add((FunctionDefinition) funDef);
							}
						}
					}
				}
			}

		} else if (element instanceof TFunction) {
			TFunction tFun = (TFunction) element;
			EObject funDef = tFun.getAstElement();
			if (funDef instanceof FunctionDefinition) {
				funDefs.add((FunctionDefinition) funDef);
			}
		}

		List<CallHierarchyOutgoingCall> result = new ArrayList<>();
		for (FunctionDefinition funDef : funDefs) {
			List<ParameterizedCallExpression> calls = EcoreUtilN4.getAllContentsOfTypeStopAt(funDef.getBody(),
					ParameterizedCallExpression.class,
					N4JSPackage.Literals.FUNCTION_OR_FIELD_ACCESSOR__BODY);

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
					Range selectionRange = getRangeOrDefault(docCall,
							locationInFileProvider.getSignificantTextRegion(call));
					List<Range> fromRanges = List.of(selectionRange);
					CallHierarchyItem item = toCallHierarchyItem(targetFunDef);
					result.add(new CallHierarchyOutgoingCall(item, fromRanges));
				}
			}
		}
		return result;
	}

	private CallHierarchyItem toCallHierarchyItem(TFunction fun) {
		XtextResource resource = (XtextResource) fun.eResource();
		XDocument doc = new XDocument(1, resource.getParseResult().getRootNode().getText());
		SymbolKind symbolKind = SymbolKindUtil.getSymbolKind(fun.eClass());
		String uri = resource.getURI().toString();

		Range range = getRangeOrDefault(doc, locationInFileProvider.getFullTextRegion(fun));
		Range selectionRange = getRangeOrDefault(doc, locationInFileProvider.getSignificantTextRegion(fun));
		CallHierarchyItem item = new CallHierarchyItem(fun.getName(), symbolKind, uri, range, selectionRange);
		return item;
	}

	@Override
	protected List<TypeHierarchyItem> prepareTypeHierarchy(ResourceTaskContext rtc, TypeHierarchyPrepareParams params,
			CancelIndicator ci) {

		try {
			int offset = rtc.getDocument().getOffSet(params.getPosition());
			EObject element = eObjectAtOffsetHelper.resolveElementAt(rtc.getResource(), offset);
			EObject type = N4JSASTUtils.getCorrespondingTypeModelElement(element);
			if (type != null) {
				element = type;
			}

			if (element instanceof TypeRef) {
				TypeRef tRef = (TypeRef) element;
				element = tRef.getDeclaredType();
			}
			if (element instanceof Type) {
				TypeHierarchyItem item = toTypeHierarchyItem((Type) element);
				return List.of(item);
			}
		} catch (IndexOutOfBoundsException e) {
			LOG.error(e);
		}
		return Collections.emptyList();
	}

	@Override
	protected List<TypeHierarchyItem> typeHierarchySubtypes(ResourceTaskContext rtc, TypeHierarchySubtypesParams params,
			CancelIndicator ci) {

		EObject element = resolveElement(rtc, params.getItem().getSelectionRange());

		List<TypeHierarchyItem> subTypesTHI = new ArrayList<>();
		if (element instanceof TClassifier) {
			Collection<ContainerType<?>> subtypes = findSubtypes(rtc, params.getWorkDoneToken(), element, false);

			for (Type referredType : subtypes) {
				subTypesTHI.add(toTypeHierarchyItem(referredType));
			}
		}

		return subTypesTHI;
	}

	private Collection<ContainerType<?>> findSubtypes(ResourceTaskContext rtc, Either<String, Integer> workDoneToken,
			EObject element, boolean transitive) {

		XtextResourceSet resSet = rtc.getResourceSet();
		IResourceDescriptions index = workspaceAccess.getXtextIndex(resSet).get();
		TargetURIs targetURIs = targetURIProvider.get();
		targetURICollector.add(element, targetURIs);

		Set<ContainerType<?>> allResults = new LinkedHashSet<>();
		while (!targetURIs.isEmpty()) {
			DeclaredTypeReferenceAcceptor referenceAcceptor = new DeclaredTypeReferenceAcceptor();
			referenceFinder.findAllReferences(targetURIs, new SimpleResourceAccess(resSet), index, referenceAcceptor,
					new SubTypeProgressMonitor(langServerAccess, workDoneToken));

			allResults.addAll(referenceAcceptor.results);
			targetURIs = targetURIProvider.get();

			if (transitive) {
				for (EObject result : referenceAcceptor.results) {
					targetURICollector.add(result, targetURIs);
				}
			}
		}

		return allResults;
	}

	@Override
	protected List<TypeHierarchyItem> typeHierarchySupertypes(ResourceTaskContext rtc,
			TypeHierarchySupertypesParams params, CancelIndicator ci) {

		EObject element = resolveElement(rtc, params.getItem().getSelectionRange());

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
		EObject element = null;
		try {
			int offset = rtc.getDocument().getOffSet(selRange.getStart());
			element = eObjectAtOffsetHelper.resolveElementAt(resource, offset);
			EObject type = N4JSASTUtils.getCorrespondingTypeModelElement(element);
			if (type != null) {
				element = type;
			}
		} catch (IndexOutOfBoundsException e) {
			LOG.error(e);
		}
		return element;
	}

	private TypeHierarchyItem toTypeHierarchyItem(Type type) {
		XtextResource resource = (XtextResource) type.eResource();
		XDocument doc = new XDocument(1, resource.getParseResult().getRootNode().getText());
		SymbolKind symbolKind = SymbolKindUtil.getSymbolKind(type.eClass());
		String uri = URIUtils.getBaseOfVirtualResourceURI(type.eResource().getURI()).toString();

		Range range = getRangeOrDefault(doc, locationInFileProvider.getFullTextRegion(type));
		Range selectionRange = getRangeOrDefault(doc, locationInFileProvider.getSignificantTextRegion(type));
		TypeHierarchyItem item = new TypeHierarchyItem(type.getName(), symbolKind, uri, range, selectionRange);
		return item;
	}

	private Range getRangeOrDefault(XDocument doc, ITextRegion region) {
		try {
			Position sgnfStartPos = doc.getPosition(region.getOffset());
			Position sgnfEndPos = doc.getPosition(region.getOffset() + region.getLength());
			return new Range(sgnfStartPos, sgnfEndPos);
		} catch (IndexOutOfBoundsException e) {
			LOG.error(e);
			return new Range(new Position(), new Position());
		}
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

			if ((eRef == N4JSPackage.Literals.IDENTIFIER_REF__ID
					|| eRef == N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY)
					&& src.eContainer() instanceof ParameterizedCallExpression) {

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
		final Set<ContainerType<?>> results = new LinkedHashSet<>();

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
					Type definedType = n4classifierDecl.getDefinedType();
					if (definedType instanceof ContainerType<?>) {
						results.add((ContainerType<?>) definedType);
					}
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
