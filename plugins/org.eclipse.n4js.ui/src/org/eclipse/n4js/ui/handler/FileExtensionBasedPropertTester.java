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
package org.eclipse.n4js.ui.handler;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.ImmutableSet.builder;
import static com.google.common.collect.ImmutableSet.copyOf;
import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Iterables.isEmpty;
import static org.eclipse.n4js.ui.utils.HandlerServiceUtils.getActiveEditor;
import static java.lang.String.valueOf;
import static java.util.Collections.emptyList;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet.Builder;

/**
 * Property tester for producing {@code true} test result only and if only the receiver is an {@link Iterable} or
 * {@link Array} which contains at least one instance of {@link IFile} and the receiver's first {@link IFile} element's
 * file extension matches with the expected value of the tester. Clients should be aware that the receiver can be the
 * {@link IFile} instance itself or a {@link IFileEditorInput file editor input} pointing to the {@link IFile file}.
 */
public class FileExtensionBasedPropertTester extends PropertyTester {

	/**
	 * <b>NOTE:&nbsp;</b>Arguments {@code property} and {@code args} are ignored. The file extension can be specified
	 * with the {@code expectedValue} argument as string without any leading dots. Like: <i>n4js</i> for files with
	 * {@code .n4js} extension. Also multiple expected file extensions can be given with as a CSV: {@code n4js, js}.
	 * Furthermore the file extension can be given as an array of values or an iterable of values.
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {

		final AtomicReference<IFile> fileRef = new AtomicReference<>();
		if (receiver instanceof Iterable) {

			final Iterable<?> itr = (Iterable<?>) receiver;
			if (!isEmpty(itr)) {
				final Object element = get(itr, 0);
				if (element instanceof IFile) {
					fileRef.set((IFile) element);
				} else if (element instanceof ITextSelection) {
					fileRef.set(tryGetFileFromActiveEditor());
				}
			}

		} else if (receiver.getClass().isArray()) {

			final Object[] array = (Object[]) receiver;
			if (array.length > 0 && array[0] instanceof IFile) {
				final Object element = array[0];
				if (element instanceof IFile) {
					fileRef.set((IFile) element);
				} else if (element instanceof ITextSelection) {
					fileRef.set(tryGetFileFromActiveEditor());
				}
			}

		} else if (receiver instanceof IFile) {
			fileRef.set((IFile) receiver);
		} else if (receiver instanceof IFileEditorInput) {
			fileRef.set(((IFileEditorInput) receiver).getFile());
		} else if (receiver instanceof ITextSelection) {
			fileRef.set(tryGetFileFromActiveEditor());
		}

		final Collection<String> expectedFileExtensions = getExpectedFileExtensions(expectedValue);

		if (null != fileRef.get()) {
			final IFile file = fileRef.get();
			if (file.exists()) {
				final String uri = valueOf(file.getLocationURI());
				if (uri.contains(".")) {
					return expectedFileExtensions.contains(uri.substring(uri.lastIndexOf(".") + 1));
				}
			}
		}

		return false;
	}

	private Collection<String> getExpectedFileExtensions(final Object expectedValue) {
		if (null == expectedValue) {
			return emptyList();
		} else if (expectedValue instanceof String) {
			return copyOf(Splitter.on(",").trimResults().split(valueOf(expectedValue)));
		} else if (expectedValue.getClass().isArray()) {
			final Object[] values = (Object[]) expectedValue;
			final Builder<String> builder = builder();
			for (int i = 0; i < values.length; i++) {
				if (values[i] instanceof String) {
					builder.addAll(getExpectedFileExtensions(values[i]));
				}
			}
			return builder.build();
		} else if (expectedValue instanceof Iterable) {
			final Builder<String> builder = builder();
			from((Iterable<?>) expectedValue).filter(String.class).transform(str -> getExpectedFileExtensions(str))
					.forEach(t -> builder.addAll(t));
			return builder.build();
		}
		return emptyList();
	}

	private IFile tryGetFileFromActiveEditor() {
		final Optional<IEditorPart> editor = getActiveEditor();
		if (editor.isPresent() && editor.get().getEditorInput() instanceof IFileEditorInput) {
			return ((IFileEditorInput) editor.get().getEditorInput()).getFile();
		}
		return null;

	}
}
