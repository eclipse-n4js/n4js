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
package org.eclipse.n4js.ui.internal;

import static org.eclipse.xtext.validation.CheckMode.ALL;

import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.editor.IXtextEditorCallback;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;
import org.eclipse.xtext.ui.editor.validation.AnnotationIssueProcessor;
import org.eclipse.xtext.ui.editor.validation.IValidationIssueProcessor;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.editor.validation.MarkerIssueProcessor;
import org.eclipse.xtext.ui.editor.validation.ValidatingEditorCallback;
import org.eclipse.xtext.ui.editor.validation.ValidationJob;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;
import org.eclipse.xtext.validation.IResourceValidator;

/**
 * Unlike {@link ValidatingEditorCallback} this editor callback is using all services from the
 * {@link IResourceServiceProvider} form the resource itself instead of an injected ones.
 */
public class OwnResourceValidatorAwareValidatingEditorCallback extends IXtextEditorCallback.NullImpl {

	@Override
	public void afterCreatePartControl(final XtextEditor editor) {
		super.afterCreatePartControl(editor);
		if (editor.isEditable()) {
			newValidationJob(editor).schedule();
		}
	}

	@Override
	public void afterSave(final XtextEditor editor) {
		super.afterSave(editor);
		if (editor.isEditable()) {
			newValidationJob(editor).schedule();
		}
	}

	private ValidationJob newValidationJob(final XtextEditor editor) {

		final IXtextDocument document = editor.getDocument();
		final IAnnotationModel annotationModel = editor.getInternalSourceViewer().getAnnotationModel();

		final IssueResolutionProvider issueResolutionProvider = getService(editor, IssueResolutionProvider.class);
		final MarkerTypeProvider markerTypeProvider = getService(editor, MarkerTypeProvider.class);
		final MarkerCreator markerCreator = getService(editor, MarkerCreator.class);

		final IValidationIssueProcessor issueProcessor = new CompositeValidationIssueProcessor(
				new AnnotationIssueProcessor(document, annotationModel, issueResolutionProvider),
				new MarkerIssueProcessor(editor.getResource(), annotationModel, markerCreator, markerTypeProvider));

		return editor.getDocument().modify(resource -> {
			final IResourceServiceProvider serviceProvider = resource.getResourceServiceProvider();
			final IResourceValidator resourceValidator = serviceProvider.getResourceValidator();
			return new ValidationJob(resourceValidator, editor.getDocument(), issueProcessor, ALL);
		});
	}

	private <T> T getService(final XtextEditor editor, final Class<T> clazz) {
		return editor.getDocument().modify(resource -> {
			final IResourceServiceProvider serviceProvider = resource.getResourceServiceProvider();
			return serviceProvider.get(clazz);
		});
	}

}
