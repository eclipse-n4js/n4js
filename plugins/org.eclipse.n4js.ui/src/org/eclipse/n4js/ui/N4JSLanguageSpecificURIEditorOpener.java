/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ui.editor.N4JSLocationInFileProvider;
import org.eclipse.n4js.utils.languages.N4LanguageUtils;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.XtextEditorInfo;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;

/**
 * This class open the right editor when Cmd + click in case the target is primitive type or is located in another
 * resource.
 */
public class N4JSLanguageSpecificURIEditorOpener extends LanguageSpecificURIEditorOpener {
	@Inject
	private N4JSLocationInFileProvider locationProvider;

	@Inject
	private IStorage2UriMapper mapper;

	@Inject
	private XtextEditorInfo editorInfo;

	@Inject(optional = true)
	private IWorkbench workbench;

	@Override
	protected void selectAndReveal(IEditorPart openEditor, final URI uri, final EReference crossReference,
			final int indexInList,
			final boolean select) {
		XtextEditor xtextEditor = EditorUtils.getXtextEditor(openEditor);
		if (xtextEditor != null && select) {
			boolean success = false;
			int tries = 0;
			while (!success || tries >= 5) {
				try {
					xtextEditor.getDocument().readOnly(new IUnitOfWork.Void<XtextResource>() {
						@Override
						public void process(XtextResource resource) throws Exception {
							if (resource != null) {
								EObject object = findEObjectByURI(uri, resource);
								boolean isComposedMemberCase = (object instanceof TMember)
										&& ((TMember) object).isComposed();

								if (object != null) {
									final ITextRegion location = (crossReference != null)
											? locationProvider.getSignificantTextRegion(object, crossReference,
													indexInList)
											: locationProvider.getSignificantTextRegion(object);
									// Recalculate the xtext editor in case of composed member!
									if (isComposedMemberCase) {
										EObject source = locationProvider.getCachedSource();
										Iterator<Pair<IStorage, IProject>> storages = mapper
												.getStorages(source.eResource().getURI())
												.iterator();
										if (storages != null && storages.hasNext()) {
											IStorage storage = storages.next().getFirst();
											IEditorInput editorInput = EditorUtils.createEditorInput(storage);
											IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow()
													.getActivePage();
											// We need to get the correct editor info instance corresponding to the
											// current language!
											XtextEditorInfo editorInfoCorrect = N4LanguageUtils
													.getServiceForContext(source, XtextEditorInfo.class)
													.orElse(editorInfo);
											final String editorId = editorInfoCorrect.getEditorId();

											final IEditorPart editor = IDE.openEditor(activePage, editorInput,
													editorId);

											XtextEditor editorToOpen = EditorUtils.getXtextEditor(editor);
											editorToOpen.selectAndReveal(location.getOffset(), location.getLength());
										}
									} else {
										xtextEditor.selectAndReveal(location.getOffset(), location.getLength());
									}

								}
							}
						}
					});
					success = true;
				} catch (OperationCanceledException e) {
				} catch (OperationCanceledError e) {
				} finally {
					tries++;
				}
			}
		}
	}

}
