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
package org.eclipse.n4js.tester.ui;

import static java.lang.Thread.currentThread;
import static org.eclipse.n4js.AnnotationDefinition.TEST_METHOD;
import static org.eclipse.n4js.ui.utils.HandlerServiceUtils.getActiveEditor;
import static org.eclipse.swt.widgets.Display.getDefault;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.impl.LiteralOrComputedPropertyNameImpl;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.tester.TestDiscoveryHelper;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.editor.XtextEditor;

/**
 * Some UI-related utility methods for working with {@link TestDiscoveryHelper}.
 */
public class TestDiscoveryUIUtils {

	/**
	 * Derives a location URI as expected by {@link TestDiscoveryHelper#collectTests(List)} from a UI object such as a
	 * IProject, IFolder, IFile, IEditorPart, etc.
	 */
	public static final URI getLocationForSelectedObject(Object selectedObject) {
		if (selectedObject instanceof ISelection)
			return getLocationForSelection((ISelection) selectedObject);
		if (selectedObject instanceof IResource)
			return getLocationForResource((IResource) selectedObject);
		if (selectedObject instanceof IEditorPart)
			return getLocationForEditor((IEditorPart) selectedObject);
		if (selectedObject instanceof IFileEditorInput)
			return getLocationForEditorInput((IFileEditorInput) selectedObject);
		return null;
	}

	/**
	 * Derives a location URI as expected by {@link TestDiscoveryHelper#collectTests(List)} from a selection.
	 */
	public static final URI getLocationForSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final Object selObj = ((IStructuredSelection) selection).getFirstElement();
			if (selObj instanceof IResource)
				return getLocationForResource((IResource) selObj);
		}
		return null;
	}

	/**
	 * Derives a location URI as expected by {@link TestDiscoveryHelper#collectTests(List)} from a workspace resource.
	 */
	public static final URI getLocationForResource(IResource resource) {
		final String pathName = resource.getFullPath().toString();
		return URI.createPlatformResourceURI(pathName, true);
	}

	/**
	 * Derives a location URI as expected by {@link TestDiscoveryHelper#collectTests(List)} from an editor.
	 */
	public static final URI getLocationForEditor(IEditorPart editor) {
		final IEditorInput input = editor.getEditorInput();
		if (input instanceof IFileEditorInput)
			return getLocationForEditorInput((IFileEditorInput) input);
		return null;
	}

	/**
	 * Derives a location URI as expected by {@link TestDiscoveryHelper#collectTests(List)} from an editor input.
	 */
	public static final URI getLocationForEditorInput(IFileEditorInput fileEditorInput) {

		if (null == fileEditorInput) {
			return null;
		}

		final XtextEditor editor = getActiveEditor(XtextEditor.class).orNull();
		if (null != editor && fileEditorInput.equals(editor.getEditorInput())) {
			if (currentThread() == getDefault().getThread()) { // Iff accessed from the UI thread
				if (editor.getSelectionProvider().getSelection() instanceof ITextSelection) {
					final ITextSelection textSelection = (ITextSelection) editor.getSelectionProvider().getSelection();
					final EObject selectedElement = getSelectedElement(editor, textSelection);
					if (selectedElement instanceof LiteralOrComputedPropertyNameImpl
							&& selectedElement.eContainer() instanceof N4MethodDeclaration) {
						final N4MethodDeclaration method = (N4MethodDeclaration) (selectedElement.eContainer());
						if (!method.eIsProxy() && TEST_METHOD.hasAnnotation(method)) {
							final TModule module = N4JSResource.getModule(method.eResource());
							if (null != module) {
								URI uri = EcoreUtil.getURI(method);
								return uri;
							}
						}
					}
				}
			}
		}

		return getFileURI(fileEditorInput);
	}

	private static EObject getSelectedElement(final XtextEditor editor, final ITextSelection textSelection) {
		return editor.getDocument().modify(
				resource -> {
					final IResourceServiceProvider serviceProvider = resource.getResourceServiceProvider();
					final EObjectAtOffsetHelper offsetHelper = serviceProvider.get(EObjectAtOffsetHelper.class);
					return offsetHelper.resolveElementAt(resource, textSelection.getOffset());
				});
	}

	private static URI getFileURI(final IFileEditorInput fileEditorInput) {
		final IFile originalFileToRun = fileEditorInput.getFile();
		final String pathName = originalFileToRun.getFullPath().toString();
		return URI.createPlatformResourceURI(pathName, true);
	}
}
