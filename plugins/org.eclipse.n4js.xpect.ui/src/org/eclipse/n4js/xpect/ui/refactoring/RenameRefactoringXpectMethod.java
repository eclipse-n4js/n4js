/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.ui.refactoring;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusEntry;
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.resource.InferredElements;
import org.eclipse.n4js.tests.util.EclipseGracefulUIShutdownEnabler;
import org.eclipse.n4js.tests.util.EditorsUtil;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.utils.StaticPolyfillHelper;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.IEObjectCoveringRegion;
import org.eclipse.n4js.xpect.common.XpectCommentRemovalUtil;
import org.eclipse.n4js.xpect.config.Config;
import org.eclipse.n4js.xpect.config.VarDef;
import org.eclipse.n4js.xpect.config.XpEnvironmentData;
import org.eclipse.n4js.xpect.methods.scoping.IN4JSCommaSeparatedValuesExpectation;
import org.eclipse.n4js.xpect.methods.scoping.N4JSCommaSeparatedValuesExpectation;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.expectation.IStringDiffExpectation;
import org.eclipse.xpect.expectation.StringDiffExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup.ConsumedIssues;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.refactoring.ui.IRenameContextFactory;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;
import org.eclipse.xtext.ui.refactoring.ui.IRenameSupport;
import org.eclipse.xtext.ui.refactoring.ui.RenameRefactoringController;

import com.google.inject.Inject;

/**
 * Provides XPEXT test methods for rename refactoring
 */
@XpectImport({ N4JSOffsetAdapter.class, XpEnvironmentData.class, VarDef.class, Config.class,
		ValidationTestModuleSetup.class })
@SuppressWarnings("restriction")
public class RenameRefactoringXpectMethod {

	@Inject
	private StaticPolyfillHelper staticPolyfillHelper;

	@Inject
	private InferredElements inferredElements;

	@Inject
	private IRenameSupport.Factory renameSupportFactory;

	@Inject
	protected RenameRefactoringController renameRefactoringController;

	@Inject
	private EObjectAtOffsetHelper offsetHelper;

	@Inject
	protected IRenameContextFactory renameContextFactory;

	static {
		EclipseGracefulUIShutdownEnabler.enableOnce();
	}

	private static Logger logger = Logger.getLogger(RenameRefactoringXpectMethod.class);

	private static class ExecutionResult {
		public String result;
	}

	/**
	 * Rename refactoring
	 *
	 */
	@ParameterParser(syntax = "('at' arg2=OFFSET 'to' arg3=STRING) ('resource' arg4=STRING)?") // arg1=OFFSET makes the
																								// 'offset' parameter
																								// contain
	// the right offset value
	@Xpect
	@ConsumedIssues({ Severity.INFO, Severity.ERROR, Severity.WARNING })
	public void renameRefactoring(
			@StringDiffExpectation(whitespaceSensitive = false) IStringDiffExpectation expectation, // arg0
			@ThisResource XtextResource resource, // arg1
			IEObjectCoveringRegion offset, // arg2
			String newName, // arg3
			String specifiedResourcePath, // arg4
			@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectedResult)
			throws Exception {

		EObject context = offset.getEObject();
		EObject selectedElement = offsetHelper.resolveElementAt((XtextResource) context.eResource(),
				offset.getOffset());

		// EObject typeSelectedElement = null;
		// inferredElements.collectInferredElements(selectedElement, (object) -> {
		// typeSelectedElement = object;
		// }, staticPolyfillHelper);
		// TODO use InferredElements here
		if (selectedElement instanceof LiteralOrComputedPropertyName) {
			selectedElement = selectedElement.eContainer();
		}

		if (selectedElement instanceof N4FieldDeclaration) {
			selectedElement = ((N4FieldDeclaration) selectedElement).getDefinedField();
		}

		if (selectedElement instanceof FieldAccessor) {
			selectedElement = ((FieldAccessor) selectedElement).getDefinedAccessor();
		}

		if (selectedElement instanceof TypeDefiningElement) {
			selectedElement = ((TypeDefiningElement) selectedElement).getDefinedType();
		}

		if ((selectedElement instanceof TStructField)
				&& (((TStructField) selectedElement).getDefinedMember() != null)) {
			selectedElement = ((TStructField) selectedElement).getDefinedMember();
		}

		if ((selectedElement instanceof TypeVariable)
				&& ((TypeVariable) selectedElement).getDefinedTypeVariable() != null) {
			selectedElement = ((TypeVariable) selectedElement).getDefinedTypeVariable();
		}

		URI targetResourceUri = context.eResource().getURI();
		// XtextResource resource = (XtextResource) context.eResource();

		N4JSGlobals.myGlobalResourceSet = context.eResource().getResourceSet();

		Optional<XtextEditor> editorOp = EditorsUtil.openXtextEditor(targetResourceUri,
				N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
		XtextEditor editor = editorOp.get();

		final ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();

		IRenameElementContext renameElementContext = renameContextFactory
				.createRenameElementContext(
						selectedElement, editor, selection, resource);

		IRenameSupport renameSupport = renameSupportFactory.create(renameElementContext, newName);

		// HACK, use reflection to obtain the private field 'renameRefactoring' since we need it to verify the
		// conditions
		Field field = renameSupport.getClass().getDeclaredField("renameRefactoring");
		field.setAccessible(true);
		ProcessorBasedRefactoring refactoring = (ProcessorBasedRefactoring) field.get(renameSupport);
		RefactoringStatus status = refactoring.checkAllConditions(new NullProgressMonitor());

		// If rename refactoring's conditions are not satisfied, validate the error message
		if (status.hasError()) {
			RefactoringStatusEntry[] entries = status.getEntries();
			List<String> errorMessages = Arrays.stream(entries).map(statusEntry -> statusEntry.getMessage())
					.collect(Collectors.toList());

			expectedResult.assertEquals(errorMessages);
		} else {
			String beforeRenameContent = getResourceContentWithoutXpectComment(specifiedResourcePath, resource);
			renameSupport.startDirectRefactoring();
			String afterRenameContent = getResourceContentWithoutXpectComment(specifiedResourcePath, resource);

			expectation.assertDiffEquals(beforeRenameContent, afterRenameContent);
		}
	}

	private String getResourceContentWithoutXpectComment(String specifiedResourcePath, XtextResource resource)
			throws Exception {
		if (specifiedResourcePath != null && !specifiedResourcePath.isEmpty()) {
			URI specifiedURI = resource.getURI().trimSegments(1).appendSegments(specifiedResourcePath.split("/"));
			return XpectCommentRemovalUtil
					.removeAllXpectComments(getContentForResourceUri(specifiedURI));
		} else {
			return XpectCommentRemovalUtil
					.removeAllXpectComments(getContentForResourceUri(resource.getURI()));
		}
	}

	/**
	 * Helper method to get the content of an resource at uri. Takes care of the encoding.
	 *
	 * @param uri
	 *            URI to resource
	 * @return content as string
	 * @throws Exception
	 *             in case of io or uri issues
	 */
	private String getContentForResourceUri(URI uri)
			throws Exception {

		String platformStr = uri.toString().replace("platform:/resource/", "");
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformStr));

		java.io.InputStream fileStream = file.getContents();

		java.util.Scanner s = new java.util.Scanner(fileStream, file.getCharset());
		s.useDelimiter("\\A");

		String content = s.hasNext() ? s.next() : "";

		fileStream.close();
		s.close();

		return content;
	}
}
