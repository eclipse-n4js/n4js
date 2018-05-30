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
package org.eclipse.n4js.ui.containers;

import static com.google.common.collect.FluentIterable.from;
import static org.eclipse.core.runtime.Status.OK_STATUS;
import static org.eclipse.core.runtime.jobs.Job.INTERACTIVE;
import static org.eclipse.n4js.projectModel.IN4JSProject.N4MF_MANIFEST;
import static org.eclipse.ui.PlatformUI.getWorkbench;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;
import static org.eclipse.xtext.validation.CheckMode.ALL;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.fileextensions.FileExtensionTypeHelper;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.internal.OwnResourceValidatorAwareValidatingEditorCallback;
import org.eclipse.n4js.ui.internal.ResourceUIValidatorExtension;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.xtext.ui.containers.AbstractAllContainersState;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * N4JSAllContainersState returns the visible elements for global scoping. The implementation is basically separated in
 * {@link N4JSProjectsStateHelper}.
 */
@Singleton
public class N4JSAllContainersState extends AbstractAllContainersState {

	private static final Logger LOGGER = Logger.getLogger(N4JSAllContainersState.class);

	private static final String PLATFORM_RESOURCE_SCHEME = "platform:/resource";

	@Inject
	private N4JSProjectsStateHelper projectsHelper;

	@Inject
	private FileExtensionTypeHelper fileExtensionTypeHelper;

	@Inject
	private IN4JSCore core;

	@Inject
	private OwnResourceValidatorAwareValidatingEditorCallback editorCallback;

	@Inject
	private ResourceUIValidatorExtension validatorExtension;

	@Override
	protected String doInitHandle(URI uri) {
		String handle = projectsHelper.initHandle(uri);
		return handle;
	}

	@Override
	protected Collection<URI> doInitContainedURIs(String containerHandle) {
		Collection<URI> initContainedURIs = projectsHelper.initContainedURIs(containerHandle);
		return initContainedURIs;
	}

	@Override
	protected List<String> doInitVisibleHandles(String handle) {
		List<String> visibleHandles = projectsHelper.initVisibleHandles(handle);
		return visibleHandles;
	}

	@Override
	protected boolean isAffectingContainerState(IResourceDelta delta) {
		if (delta.getKind() == IResourceDelta.ADDED || delta.getKind() == IResourceDelta.REMOVED) {
			String fileExtension = delta.getFullPath().getFileExtension();
			if (null != fileExtension && fileExtensionTypeHelper.isTypable(fileExtension)) {
				return true;
			}
			if (IN4JSProject.PACKAGE_JSON.equals(delta.getFullPath().lastSegment())) {
				clearManifestCache();
				return true;
			}
			if (IN4JSProject.N4MF_MANIFEST.equals(delta.getFullPath().lastSegment())) {
				clearManifestCache();
				return true;
			}
			if (IN4JSArchive.NFAR_FILE_EXTENSION.equals(fileExtension)) {
				clearManifestCache(delta);
				return true;
			}
			if (delta.getResource() instanceof IProject) {
				clearManifestCache();
				return true;
			}
			if (delta.getResource() instanceof IFolder) {
				if (isSourceContainerModification(delta)) {
					tryValidateManifest(delta);
					tryValidateManifestInEditor(delta);
					clearManifestCache(delta);
					return true;
				}
			}
			return false;
		} else if (delta.getKind() == IResourceDelta.CHANGED && delta.getResource() instanceof IProject) {
			if ((delta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
				return true;
			}
			if ((delta.getFlags() & IResourceDelta.OPEN) != 0) {
				clearManifestCache();
				return true;
			}
			return false;
		}
		if (n4mfFileHasBeenChanged(delta) || nfarHasBeenChanged(delta) || packageJSONFileHasBeenChanged(delta)) {
			clearManifestCache(delta);
			return true;
		}
		return false;

	}

	static int i = 0;

	private void tryValidateManifest(final IResourceDelta delta) {
		final String fullPath = delta.getFullPath().toString();
		final URI folderUri = URI.createPlatformResourceURI(fullPath, true);
		final IN4JSProject project = core.findProject(folderUri).orNull();
		if (null != project && project.exists()) {
			final URI manifestLocation = project.getProjectDescriptionLocation().orNull();
			if (null != manifestLocation) {
				final IFile manifest = delta.getResource().getProject().getFile(N4MF_MANIFEST);
				final ResourceSet resourceSet = core.createResourceSet(Optional.of(project));
				final Resource resource = resourceSet.getResource(manifestLocation, true);
				final Job job = Job.create("", monitor -> {
					validatorExtension.updateValidationMarkers(manifest, resource, ALL, monitor);
					return OK_STATUS;
				});
				job.setPriority(INTERACTIVE);
				job.schedule();
			}
		}
	}

	private void tryValidateManifestInEditor(final IResourceDelta delta) {
		if (isWorkbenchRunning()) {
			Display.getDefault().asyncExec(() -> {
				final IWorkbenchWindow window = getWorkbench().getActiveWorkbenchWindow();
				if (null != window) {
					final IWorkbenchPage page = window.getActivePage();
					for (final IEditorReference editorRef : page.getEditorReferences()) {
						if (isEditorForResource(editorRef, delta.getResource())) {
							final IWorkbenchPart part = editorRef.getPart(true);
							if (part instanceof XtextEditor) {
								editorCallback.afterSave((XtextEditor) part);
								return;
							}
						}
					}
				}
			});
		}
	}

	private boolean isEditorForResource(IEditorReference editorRef, IResource resource) {
		final IFile manifest = resource.getProject().getFile(N4MF_MANIFEST);
		if (!manifest.exists()) {
			return false;
		}
		try {
			final IEditorInput input = editorRef.getEditorInput();
			if (input instanceof IFileEditorInput) {
				return ((IFileEditorInput) input).getFile().equals(manifest);
			}
		} catch (final PartInitException e) {
			LOGGER.warn("Error while trying to get editor input for editor reference: " + editorRef, e);
		}
		return false;
	}

	private boolean isSourceContainerModification(final IResourceDelta delta) {
		final String fullPath = delta.getFullPath().toString();
		final URI folderUri = URI.createPlatformResourceURI(fullPath, true);
		final IN4JSProject project = core.findProject(folderUri).orNull();
		if (null != project && project.exists()) {
			return from(project.getSourceContainers())
					.transform(container -> container.getLocation())
					.filter(uri -> uri.isPlatformResource())
					.transform(uri -> uri.toString())
					.transform(uri -> uri.replaceFirst(PLATFORM_RESOURCE_SCHEME, ""))
					.firstMatch(uri -> uri.equals(fullPath))
					.isPresent();
		}
		return false;
	}

	private void clearManifestCache() {
		projectsHelper.clearProjectCache();
	}

	private boolean nfarHasBeenChanged(IResourceDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED
				&& delta.getResource().getType() == IResource.FILE
				&& IN4JSArchive.NFAR_FILE_EXTENSION.equalsIgnoreCase(delta.getFullPath().getFileExtension());
	}

	private boolean n4mfFileHasBeenChanged(IResourceDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED
				&& delta.getResource().getType() == IResource.FILE
				&& IN4JSProject.N4MF_MANIFEST.equalsIgnoreCase(delta.getFullPath().lastSegment());
	}

	private boolean packageJSONFileHasBeenChanged(IResourceDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED
				&& delta.getResource().getType() == IResource.FILE
				&& IN4JSProject.PACKAGE_JSON.equalsIgnoreCase(delta.getFullPath().lastSegment());
	}

	private void clearManifestCache(IResourceDelta delta) {
		IProject project = delta.getResource().getProject();
		projectsHelper.clearProjectCache(project);
	}
}
