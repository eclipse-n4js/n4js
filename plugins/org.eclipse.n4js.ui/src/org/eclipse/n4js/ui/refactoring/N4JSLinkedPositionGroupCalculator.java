/*******************************************************************************
 * Copyright (c) 2011, 2017 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.ui.refactoring;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.xtext.util.Strings.notNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.text.link.LinkedPositionGroup;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.TargetURIConverter;
import org.eclipse.xtext.resource.IGlobalServiceProvider;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.DefaultReferenceDescription;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.refactoring.ElementRenameArguments;
import org.eclipse.xtext.ui.refactoring.IDependentElementsCalculator;
import org.eclipse.xtext.ui.refactoring.IReferenceUpdater;
import org.eclipse.xtext.ui.refactoring.IRenameStrategy;
import org.eclipse.xtext.ui.refactoring.IRenameStrategy.Provider.NoSuchStrategyException;
import org.eclipse.xtext.ui.refactoring.IRenamedElementTracker;
import org.eclipse.xtext.ui.refactoring.impl.CachingResourceSetProvider;
import org.eclipse.xtext.ui.refactoring.impl.ProjectUtil;
import org.eclipse.xtext.ui.refactoring.impl.RefactoringResourceSetProvider;
import org.eclipse.xtext.ui.refactoring.ui.DefaultLinkedPositionGroupCalculator;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Calculates the linked positions for simultaneous editing when a refactoring is triggered in linked mode.
 *
 * @author Jan Koehnlein - Initial contribution and API
 */
@SuppressWarnings("restriction")
public class N4JSLinkedPositionGroupCalculator extends DefaultLinkedPositionGroupCalculator {

	@Inject
	private ProjectUtil projectUtil;

	@Inject
	private RefactoringResourceSetProvider resourceSetProvider;

	@Inject
	private IGlobalServiceProvider globalServiceProvider;

	@Inject
	private IRenamedElementTracker renamedElementTracker;

	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry;

	@Inject
	private IReferenceFinder referenceFinder;

	@Inject
	private IReferenceUpdater referenceUpdater;

	@Inject
	private TargetURIConverter targetURIConverter;

	@Inject
	private Provider<LocalResourceRefactoringUpdateAcceptor> updateAcceptorProvider;

	@Override
	public Provider<LinkedPositionGroup> getLinkedPositionGroup(
			IRenameElementContext renameElementContext,
			IProgressMonitor monitor) {
		final SubMonitor progress = SubMonitor.convert(monitor, 100);
		final XtextEditor editor = (XtextEditor) renameElementContext.getTriggeringEditor();
		IProject project = projectUtil.getProject(renameElementContext.getContextResourceURI());
		if (project == null)
			throw new IllegalStateException("Could not determine project for context resource "
					+ renameElementContext.getContextResourceURI());

		RefactoringResourceSetProvider resourceSetProvider = new CachingResourceSetProvider(
				N4JSLinkedPositionGroupCalculator.this.resourceSetProvider);

		ResourceSet resourceSet = resourceSetProvider.get(project);
		EObject targetElement = resourceSet.getEObject(renameElementContext.getTargetElementURI(), true);

		if (targetElement == null)
			throw new IllegalStateException("Target element could not be loaded");
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}

		List<EObject> realTargetElements = TypeUtils.getRealElements(targetElement);

		LocalResourceRefactoringUpdateAcceptor updateAcceptor = updateAcceptorProvider.get();
		updateAcceptor.setLocalResourceURI(renameElementContext.getContextResourceURI());
		IRenameStrategy renameStrategy2 = null;

		for (EObject realTargetElement : realTargetElements) {
			IRenameStrategy.Provider strategyProvider = globalServiceProvider.findService(realTargetElement,
					IRenameStrategy.Provider.class);
			IRenameStrategy renameStrategy = null;
			try {
				renameStrategy = strategyProvider.get(realTargetElement, renameElementContext);
			} catch (NoSuchStrategyException exc) {
				// handle in next line
			}
			if (renameStrategy == null)
				throw new IllegalArgumentException("Cannot find a rename strategy for "
						+ notNull(renameElementContext.getTargetElementURI()));
			renameStrategy2 = renameStrategy;

			String newName = renameStrategy.getOriginalName();
			IResourceServiceProvider resourceServiceProvider = resourceServiceProviderRegistry
					.getResourceServiceProvider(renameElementContext.getTargetElementURI());
			IDependentElementsCalculator dependentElementsCalculator = resourceServiceProvider
					.get(IDependentElementsCalculator.class);
			Iterable<URI> dependentElementURIs = dependentElementsCalculator.getDependentElementURIs(realTargetElement,
					progress.newChild(10));
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			renameStrategy.createDeclarationUpdates(newName, resourceSet, updateAcceptor);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			Iterable<URI> renameElems;

			if (TypeUtils.isComposedTElement(targetElement)) {
				renameElems = dependentElementURIs;
			} else {
				renameElems = concat(Collections.singleton(renameElementContext.getTargetElementURI()),
						dependentElementURIs);
			}

			Map<URI, URI> original2newEObjectURI = renamedElementTracker.renameAndTrack(
					renameElems,
					newName, resourceSet, renameStrategy, progress.newChild(10));
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			ElementRenameArguments elementRenameArguments = new ElementRenameArguments(
					renameElementContext.getTargetElementURI(), newName, renameStrategy, original2newEObjectURI,
					resourceSetProvider);
			final List<IReferenceDescription> referenceDescriptions = newArrayList();
			IReferenceFinder.Acceptor referenceAcceptor = new IReferenceFinder.Acceptor() {
				@Override
				public void accept(IReferenceDescription referenceDescription) {
					referenceDescriptions.add(referenceDescription);
				}

				@Override
				public void accept(EObject source, URI sourceURI, EReference eReference, int index,
						EObject targetOrProxy,
						URI targetURI) {
					referenceDescriptions.add(new DefaultReferenceDescription(EcoreUtil2.getFragmentPathURI(source),
							targetURI, eReference, index, null));
				}
			};
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			referenceFinder.findReferences(
					targetURIConverter.fromIterable(elementRenameArguments.getRenamedElementURIs()),
					resourceSet.getResource(renameElementContext.getContextResourceURI(), true),
					referenceAcceptor, progress.newChild(10));
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			// Ignore composed members
			// TODO find a better way to do this!
			final List<IReferenceDescription> referenceDescriptionsWithoutComposedMember;
			if (TypeUtils.isComposedTElement(targetElement)) {
				referenceDescriptionsWithoutComposedMember = referenceDescriptions;
			} else {
				referenceDescriptionsWithoutComposedMember = referenceDescriptions
						.stream()
						.filter(resDesc -> !resDesc.getTargetEObjectUri().fragment()
								.contains("@cachedComposedMembers"))
						.collect(Collectors.toList());
			}

			referenceUpdater.createReferenceUpdates(elementRenameArguments, referenceDescriptionsWithoutComposedMember,
					updateAcceptor,
					progress.newChild(60));
		}

		final List<ReplaceEdit> textEdits = updateAcceptor.getTextEdits();

		final List<ReplaceEdit> textEditsWithoutDuplicates = new ArrayList<>();
		for (ReplaceEdit edit : textEdits) {
			if (!textEditsWithoutDuplicates.stream()
					.filter(e -> ((e.getOffset() == edit.getOffset()) && e.getLength() == edit.getLength())).findFirst()
					.isPresent()) {
				textEditsWithoutDuplicates.add(edit);
			}
		}

		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}

		final String originalName = renameStrategy2.getOriginalName();
		return new Provider<LinkedPositionGroup>() {

			@Override
			public LinkedPositionGroup get() {
				LinkedPositionGroup linkedGroup = createLinkedGroupFromReplaceEdits(textEditsWithoutDuplicates, editor,
						originalName, progress.newChild(10));
				return linkedGroup;
			}
		};
	}

}
